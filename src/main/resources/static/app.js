document.addEventListener("DOMContentLoaded", () => {
    if (document.getElementById('tabela-corpo')) {
        carregarAgendamentos();
    }
    if (document.getElementById('lista-professor')) {
        carregarListaProfessor();
    }
});

// ==========================================
// Controle do Modal
// ==========================================
function abrirModal() {
    const modal = document.getElementById('modal-overlay');
    if (modal) modal.classList.remove('hidden');
}

function fecharModal() {
    const modal = document.getElementById('modal-overlay');
    if (modal) modal.classList.add('hidden');
    
    const form = document.getElementById('form-agendamento');
    if (form) form.reset();
}

// ==========================================
// Processamento do Formulário (POST)
// ==========================================
const form = document.getElementById('form-agendamento');
if (form) {
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const payload = {
            nomeAluno: document.getElementById('nomeAluno').value,
            matricula: document.getElementById('matricula').value,
            assunto: document.getElementById('assunto').value,
            dataHora: document.getElementById('dataHora').value
        };

        try {
            const response = await fetch('/api/agendamentos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                fecharModal();
                carregarAgendamentos(); // Atualiza a tabela imediatamente após inserção
            } else {
                const erro = await response.json();
                alert('Erro: ' + (erro.erro || 'Falha na validação dos dados.'));
            }
        } catch (error) {
            console.error('Falha de rede:', error);
        }
    });
}

// ==========================================
// Carga e Renderização (Visão Principal)
// ==========================================
function carregarAgendamentos() {
    fetch('/api/agendamentos')
        .then(response => response.json())
        .then(dados => {
            const tbody = document.getElementById('tabela-corpo');
            if (!tbody) return;
            
            tbody.innerHTML = '';

            dados.forEach(a => {
                const tr = document.createElement('tr');
                tr.className = 'border-b border-gh-border hover:bg-gray-50 transition-colors';

                tr.innerHTML = `
                    <td class="px-4 py-3 text-gh-muted">#${a.id}</td>
                    <td class="px-4 py-3 font-medium text-gh-text">${a.nomeAluno}</td>
                    <td class="px-4 py-3 text-gh-muted">${formatarData(a.dataHora)}</td>
                    <td class="px-4 py-3">
                        <span class="bg-gray-100 text-gh-muted border border-gray-200 px-2 py-0.5 rounded-full text-xs">${a.assunto}</span>
                    </td>
                    <td class="px-4 py-3">
                        <span class="text-[#1a7f37] border border-[#a3dcaf] bg-[#e6ffec] px-2 py-0.5 rounded-full text-xs font-medium">${a.status}</span>
                    </td>
                    <td class="px-4 py-3 text-right">
                        <button onclick="deletarAgendamento(${a.id})" class="text-gh-danger hover:underline cursor-pointer">Excluir</button>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        })
        .catch(erro => console.error('Falha na Carga dos dados:', erro));
}

// ==========================================
// Carregameto e Renderização - Visão do professor
// ==========================================
async function carregarListaProfessor() {
    try {
        const res = await fetch('/api/agendamentos');
        const dados = await res.json();
        const corpo = document.getElementById('lista-professor');
        if (!corpo) return;

        corpo.innerHTML = dados.map(a => `
            <tr class="border-b border-gh-border hover:bg-gray-50">
                <td class="px-4 py-3 text-gh-muted">#${a.id}</td>
                <td class="px-4 py-3 font-medium">${a.nomeAluno}</td>
                <td class="px-4 py-3 text-xs">${a.matricula || 'N/A'}</td>
                <td class="px-4 py-3"><span class="bg-gray-100 px-2 py-1 rounded border border-gh-border text-xs">${a.assunto}</span></td>
                <td class="px-4 py-3 text-gh-muted">${formatarData(a.dataHora)}</td>
                <td class="px-4 py-3"><span class="text-green-700 font-semibold">${a.status}</span></td>
            </tr>
        `).join('');
    } catch (erro) {
        console.error('Falha na Carga dos dados do professor:', erro);
    }
}

// ==========================================
// Funções Utilitárias e Exclusão (DELETE)
// ==========================================
function formatarData(dataIso) {
    if(!dataIso) return '-';
    return new Date(dataIso).toLocaleString('pt-BR');
}

function deletarAgendamento(id) {
    if(confirm('Confirma a exclusão deste agendamento?')) {
        fetch(`/api/agendamentos/${id}`, { method: 'DELETE' })
            .then(response => {
                if(response.ok) {
                    carregarAgendamentos();
                }
            });
    }
}