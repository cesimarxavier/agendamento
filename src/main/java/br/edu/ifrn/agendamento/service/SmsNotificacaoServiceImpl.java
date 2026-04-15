package br.edu.ifrn.agendamento.service;

import br.edu.ifrn.agendamento.model.Agendamento;
import org.springframework.stereotype.Service;

@Service("smsNotificacao")
public class SmsNotificacaoServiceImpl implements NotificacaoService {
    @Override
    public void notificar(Agendamento agendamento) {
        // Simulação do envio de SMS
        
        System.out.println("--------------------------------      ----------------------------");
        System.out.println("------------------ Notificação via SMS enviada  ------------------");
        System.out.println("--------------------------------      ----------------------------");
        System.out.println("Aluno: " + agendamento.getNomeAluno());
        System.out.println("Assunto: " + agendamento.getAssunto());
        System.out.println("Enviado em: " + agendamento.getDataHora());
    }
}