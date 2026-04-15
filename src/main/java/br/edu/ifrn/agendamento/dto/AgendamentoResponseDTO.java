package br.edu.ifrn.agendamento.dto;

import br.edu.ifrn.agendamento.model.StatusAgendamento;
import br.edu.ifrn.agendamento.model.TipoAssunto;
import java.time.LocalDateTime;

public class AgendamentoResponseDTO {

    private Long id;
    private String nomeAluno;
    private String matricula;
    private LocalDateTime dataHora;
    private TipoAssunto assunto;
    private StatusAgendamento status;

    // Métodos Getters e Setters omitidos para brevidade (devem ser gerados na IDE)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    // todo: crie get and set para getMatricula
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public TipoAssunto getAssunto() { return assunto; }
    public void setAssunto(TipoAssunto assunto) { this.assunto = assunto; }
    public StatusAgendamento getStatus() { return status; }
    public void setStatus(StatusAgendamento status) { this.status = status; }
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}