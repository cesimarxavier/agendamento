package br.edu.ifrn.agendamento.model;

import java.time.LocalDateTime;

public class Agendamento {

    private Long id;
    private String nomeAluno;
    private String matricula;
    private LocalDateTime dataHora;
    private TipoAssunto assunto;
    private StatusAgendamento status;

    public Agendamento() {
    }

    public Agendamento(Long id, String nomeAluno, String matricula, LocalDateTime dataHora, TipoAssunto assunto, StatusAgendamento status) {
        this.id = id;
        this.nomeAluno = nomeAluno;
        this.matricula = matricula;
        this.dataHora = dataHora;
        this.assunto = assunto;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public TipoAssunto getAssunto() {
        return assunto;
    }

    public void setAssunto(TipoAssunto assunto) {
        this.assunto = assunto;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }
}