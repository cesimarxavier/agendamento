package br.edu.ifrn.agendamento.service;

import br.edu.ifrn.agendamento.audit.model.LogAuditoria;
import br.edu.ifrn.agendamento.audit.repository.LogAuditoriaRepository;
import br.edu.ifrn.agendamento.dto.AgendamentoRequestDTO;
import br.edu.ifrn.agendamento.dto.AgendamentoResponseDTO;
import br.edu.ifrn.agendamento.exception.RegraNegocioException;
import br.edu.ifrn.agendamento.model.Agendamento;
import br.edu.ifrn.agendamento.model.Aluno;
import br.edu.ifrn.agendamento.model.Categoria;
import br.edu.ifrn.agendamento.repository.AgendamentoRepository;
import br.edu.ifrn.agendamento.repository.AlunoRepository;
import br.edu.ifrn.agendamento.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    // banco do dominio
    private final AgendamentoRepository agendamentoRepository;
    private final AlunoRepository alunoRepository;
    private final CategoriaRepository categoriaRepository;

    // banco de auditoria
    private final LogAuditoriaRepository logAuditoriaRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, 
                              AlunoRepository alunoRepository, 
                              CategoriaRepository categoriaRepository,
                              LogAuditoriaRepository logAuditoriaRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.alunoRepository = alunoRepository;
        this.categoriaRepository = categoriaRepository;
        this.logAuditoriaRepository = logAuditoriaRepository;
    }

    @Transactional // Garante que tudo é revertido se algo falhar
    public AgendamentoResponseDTO registrarAgendamento(AgendamentoRequestDTO dto) {
        
        // 1. Busca as entidades relacionadas baseadas nos IDs enviados no DTO
        Aluno aluno = alunoRepository.findById(dto.getAlunoId())
                .orElseThrow(() -> new RegraNegocioException("Aluno não encontrado no sistema."));

        List<Categoria> categorias = categoriaRepository.findAllById(dto.getCategoriasIds());
        if (categorias.isEmpty()) {
            throw new RegraNegocioException("Pelo menos uma categoria válida deve ser informada.");
        }

        // 2. Regra de Negócio Específica (Exemplo: Impedir agendamentos colados)
        if (ChronoUnit.HOURS.between(LocalDateTime.now(), dto.getDataHora()) < 24) {
             throw new RegraNegocioException("O agendamento deve ser feito com pelo menos 24h de antecedência.");
        }

        // 3. Monta a Entidade Agendamento
        Agendamento agendamento = new Agendamento();
        agendamento.setAluno(aluno);
        agendamento.setCategorias(categorias);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setStatus("SOLICITADO");

        // 4. Salva no Banco Principal (Base A)
        Agendamento salvo = agendamentoRepository.save(agendamento);

        // 5. Gera Log no Banco de Auditoria (Base B)
        LogAuditoria log = new LogAuditoria(salvo.getId(), LocalDateTime.now(), "CRIACAO_AGENDAMENTO");
        logAuditoriaRepository.save(log);

        // 6. Converte para DTO de Resposta e Retorna
        return converterParaResponseDTO(salvo);
    }

    public List<AgendamentoResponseDTO> listarTodos() {
        return agendamentoRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        // Exemplo de uso do JOIN FETCH para evitar N+1
        Agendamento a = agendamentoRepository.buscarComCategoriasEager(id);
        if (a == null) {
            throw new RegraNegocioException("Agendamento não encontrado.");
        }
        return converterParaResponseDTO(a);
    }

    @Transactional
    public void deletarAgendamento(Long id) {
        Optional<Agendamento> existente = agendamentoRepository.findById(id);
        if (existente.isPresent()) {
            agendamentoRepository.deleteById(id);
            // Registra auditoria da exclusão
            logAuditoriaRepository.save(new LogAuditoria(id, LocalDateTime.now(), "EXCLUSAO_AGENDAMENTO"));
        } else {
             throw new RegraNegocioException("Agendamento não encontrado para exclusão.");
        }
    }

    // --- Métodos Utilitários de Conversão ---

    private AgendamentoResponseDTO converterParaResponseDTO(Agendamento a) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(a.getId());
        dto.setDataHora(a.getDataHora());
        dto.setStatus(a.getStatus());
        
        if (a.getAluno() != null) {
            dto.setNomeAluno(a.getAluno().getNome());
            dto.setMatriculaAluno(a.getAluno().getMatricula());
        }

        if (a.getCategorias() != null) {
            dto.setNomesCategorias(a.getCategorias().stream()
                                    .map(Categoria::getNome)
                                    .collect(Collectors.toList()));
        }
        return dto;
    }
}