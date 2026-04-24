package br.edu.ifrn.agendamento.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AgendamentoResponseDTO {

    private Long id;
    private String nomeAluno;
    private String matriculaAluno;
    private LocalDateTime dataHora;
    private String status;
    private List<String> nomesCategorias;

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getMatriculaAluno() { return matriculaAluno; }
    public void setMatriculaAluno(String matriculaAluno) { this.matriculaAluno = matriculaAluno; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getNomesCategorias() { return nomesCategorias; }
    public void setNomesCategorias(List<String> nomesCategorias) { this.nomesCategorias = nomesCategorias; }
}