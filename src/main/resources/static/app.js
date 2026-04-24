const API_URL = 'http://localhost:8080/api/agendamentos';

// Carregar agendamentos ao abrir a página
document.addEventListener('DOMContentLoaded', carregarAgendamentos);

// Lidar com o envio do formulário
document.getElementById('agendamentoForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const mensagemDiv = document.getElementById('mensagem');
    mensagemDiv.innerHTML = ''; // Limpar mensagens anteriores

    // 1. Coletar o ID do Aluno (Convertido para Inteiro)
    const alunoId = parseInt(document.getElementById('alunoId').value);

    // 2. Coletar os IDs das Categorias marcadas (Convertidos para Inteiros)
    const categoriasIds = Array.from(document.querySelectorAll('.categoria-checkbox:checked'))
                               .map(cb => parseInt(cb.value));

    // 3. Coletar a Data e Hora
    const dataHora = document.getElementById('dataHora').value;

    // Validação no frontend: garantir que escolheu pelo menos uma categoria
    if (categoriasIds.length === 0) {
        mostrarMensagem('Selecione pelo menos uma categoria.', 'danger');
        return;
    }

    // Montar o objeto DTO
    const requestDTO = {
        alunoId: alunoId,
        categoriasIds: categoriasIds,
        dataHora: dataHora
    };

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestDTO)
        });

        if (response.ok) {
            mostrarMensagem('Agendamento criado com sucesso! (Salvo no BD Principal e log gerado na Auditoria)', 'success');
            document.getElementById('agendamentoForm').reset();
            carregarAgendamentos(); // Atualizar tabela
        } else {
            // Tentar capturar a mensagem de erro da nossa RegraNegocioException
            const errorText = await response.text();
            mostrarMensagem(`Erro ao criar: ${errorText || response.statusText}`, 'danger');
        }
    } catch (error) {
        mostrarMensagem('Erro de conexão com o servidor.', 'danger');
        console.error(error);
    }
});

// Função para buscar e renderizar a tabela
async function carregarAgendamentos() {
    try {
        const response = await fetch(API_URL);
        const agendamentos = await response.json();
        
        const tbody = document.getElementById('tabelaCorpo');
        tbody.innerHTML = '';

        agendamentos.forEach(ag => {
            const tr = document.createElement('tr');
            
            // Formatando a data para visualização PT-BR
            const dataFormatada = new Date(ag.dataHora).toLocaleString('pt-BR');
            
            // Juntando as categorias com vírgula
            const categoriasFormatadas = ag.nomesCategorias ? ag.nomesCategorias.join(', ') : '-';

            tr.innerHTML = `
                <td>${ag.id}</td>
                <td><strong>${ag.nomeAluno}</strong></td>
                <td>${ag.matriculaAluno}</td>
                <td><span class="badge bg-secondary">${categoriasFormatadas}</span></td>
                <td>${dataFormatada}</td>
                <td><span class="badge bg-info text-dark">${ag.status}</span></td>
                <td>
                    <button class="btn btn-sm btn-danger" onclick="deletarAgendamento(${ag.id})">Excluir</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error("Erro ao carregar agendamentos:", error);
    }
}

// Função para deletar agendamento
async function deletarAgendamento(id) {
    if (confirm('Tem certeza que deseja excluir este agendamento?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                carregarAgendamentos();
            } else {
                alert('Erro ao excluir agendamento.');
            }
        } catch (error) {
            console.error("Erro ao deletar:", error);
        }
    }
}

// Utilitário para exibir mensagens na tela
function mostrarMensagem(texto, tipo) {
    const mensagemDiv = document.getElementById('mensagem');
    mensagemDiv.innerHTML = `<div class="alert alert-${tipo}" role="alert">${texto}</div>`;
}