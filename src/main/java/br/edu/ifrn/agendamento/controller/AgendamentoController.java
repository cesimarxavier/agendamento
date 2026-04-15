package br.edu.ifrn.agendamento.controller;

import br.edu.ifrn.agendamento.dto.AgendamentoRequestDTO;
import br.edu.ifrn.agendamento.dto.AgendamentoResponseDTO;
import br.edu.ifrn.agendamento.exception.RegraNegocioException;
import br.edu.ifrn.agendamento.model.Agendamento;
import br.edu.ifrn.agendamento.repository.AgendamentoRepository;
import br.edu.ifrn.agendamento.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;
    private final AgendamentoRepository repository;

    public AgendamentoController(AgendamentoService service, AgendamentoRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO dto) {
        Agendamento entidade = converterParaEntidade(dto);
        Agendamento salvo = service.registrarAgendamento(entidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
        List<AgendamentoResponseDTO> lista = repository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Agendamento> agendamento = repository.findById(id);
        return agendamento.map(a -> ResponseEntity.ok(converterParaResponseDTO(a)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoRequestDTO dto) {
        Optional<Agendamento> existente = repository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Agendamento entidade = converterParaEntidade(dto);
        entidade.setId(id);
        Agendamento salvo = repository.salvar(entidade);
        return ResponseEntity.ok(converterParaResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Captura da exceção de regra de negócio
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> tratarRegraNegocio(RegraNegocioException ex) {
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Captura de falhas de validação dos DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            erros.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    // Processos manuais de conversão
    private Agendamento converterParaEntidade(AgendamentoRequestDTO dto) {
        Agendamento a = new Agendamento();
        a.setNomeAluno(dto.getNomeAluno());
        a.setMatricula(dto.getMatricula());
        a.setDataHora(dto.getDataHora());
        a.setAssunto(dto.getAssunto());
        return a;
    }

    private AgendamentoResponseDTO converterParaResponseDTO(Agendamento a) {
        AgendamentoResponseDTO dto = new AgendamentoResponseDTO();
        dto.setId(a.getId());
        dto.setNomeAluno(a.getNomeAluno());
        dto.setDataHora(a.getDataHora());
        dto.setMatricula(a.getMatricula());
        dto.setAssunto(a.getAssunto());
        dto.setStatus(a.getStatus());
        return dto;
    }
}