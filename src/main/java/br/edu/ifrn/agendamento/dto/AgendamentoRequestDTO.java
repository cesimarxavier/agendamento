package br.edu.ifrn.agendamento.dto;

import br.edu.ifrn.agendamento.model.TipoAssunto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AgendamentoRequestDTO {

    @NotBlank(message = "O nome do aluno é obrigatório.")
    private String nomeAluno;

    @NotBlank(message = "A matrícula é obrigatória.")
    private String matricula;

    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "A data do agendamento deve ser no futuro.")
    private LocalDateTime dataHora;

    @NotNull(message = "O assunto é obrigatório.")
    private TipoAssunto assunto;

    // Métodos Getters e Setters omitidos para brevidade (devem ser gerados na IDE)
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public TipoAssunto getAssunto() { return assunto; }
    public void setAssunto(TipoAssunto assunto) { this.assunto = assunto; }
}