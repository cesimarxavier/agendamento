/**
 * Foi criada separada para ser processada pelo arquivo de configuracao da segunda base, focada em auditoria
*/
package br.edu.ifrn.agendamento.audit.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LogAuditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long agendamentoId;
    private LocalDateTime dataHoraOperacao;
    private String acao;

    
    public LogAuditoria() {}

    public LogAuditoria(Long agendamentoId, LocalDateTime dataHoraOperacao, String acao) {
        this.agendamentoId = agendamentoId;
        this.dataHoraOperacao = dataHoraOperacao;
        this.acao = acao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getAgendamentoId() { return agendamentoId; }
    public void setAgendamentoId(Long agendamentoId) { this.agendamentoId = agendamentoId; }
    public LocalDateTime getDataHoraOperacao() { return dataHoraOperacao; }
    public void setDataHoraOperacao(LocalDateTime dataHoraOperacao) { this.dataHoraOperacao = dataHoraOperacao; }
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
}