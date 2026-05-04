package br.edu.ifrn.agendamento.controller;

import br.edu.ifrn.agendamento.dto.AgendamentoRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AgendamentoController {

    
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> obterInfoSistema() {
        return ResponseEntity.ok(Map.of("status", "Ativo", "versao", "1.1-SECURE"));
    }

    
    @GetMapping("/agendamentos")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR', 'ALUNO')")
    public ResponseEntity<?> listarAgendamentos() {
        return ResponseEntity.ok().build();
    }

    
    @PutMapping("/agendamentos/{id}")
    @PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
    public ResponseEntity<?> atualizarAgendamento(@PathVariable Long id, @RequestBody @Valid AgendamentoRequestDTO dto) {
        // Substituir pela lógica do Service. Lançar exceção (404 Not Found) se ID não existir[cite: 1]
        return ResponseEntity.ok().build();
    }

    
    @PostMapping("/agendamentos")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<?> criarAgendamento(@RequestBody @Valid AgendamentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    
    @DeleteMapping("/agendamentos/{id}")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<Void> excluirAgendamento(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}