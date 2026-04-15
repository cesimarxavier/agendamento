package br.edu.ifrn.agendamento.service;

import br.edu.ifrn.agendamento.exception.RegraNegocioException;
import br.edu.ifrn.agendamento.model.Agendamento;
import br.edu.ifrn.agendamento.model.StatusAgendamento;
import br.edu.ifrn.agendamento.model.TipoAssunto;
import br.edu.ifrn.agendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final NotificacaoService notificacaoService;

    public AgendamentoService(AgendamentoRepository repository, 
                              @Qualifier("emailNotificacao") NotificacaoService notificacaoService) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }

    public Agendamento registrarAgendamento(Agendamento agendamento) {
        // Regra de Negócio Condicional baseada no atributo
        if (agendamento.getAssunto() == TipoAssunto.REVISAO_PROVA) {
            long horasAntecedencia = ChronoUnit.HOURS.between(LocalDateTime.now(), agendamento.getDataHora());
            if (horasAntecedencia < 48) {
                throw new RegraNegocioException("Agendamentos para Revisão de Prova exigem antecedência mínima de 48 horas.");
            }
        }

        agendamento.setStatus(StatusAgendamento.SOLICITADO);
        Agendamento salvo = repository.salvar(agendamento);
        
        notificacaoService.notificar(salvo);
        return salvo;
    }
}