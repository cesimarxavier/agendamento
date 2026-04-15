package br.edu.ifrn.agendamento.service;

import br.edu.ifrn.agendamento.model.Agendamento;

public interface NotificacaoService {
    void notificar(Agendamento agendamento);
}