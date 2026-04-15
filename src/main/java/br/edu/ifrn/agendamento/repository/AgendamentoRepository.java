package br.edu.ifrn.agendamento.repository;

import br.edu.ifrn.agendamento.model.Agendamento;
import br.edu.ifrn.agendamento.model.TipoAssunto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AgendamentoRepository {

    private final Map<Long, Agendamento> dados = new HashMap<>();
    private Long idCounter = 1L;

    public Agendamento salvar(Agendamento agendamento) {
        if (agendamento.getId() == null) {
            agendamento.setId(idCounter++);
        }
        dados.put(agendamento.getId(), agendamento);
        return agendamento;
    }

    public List<Agendamento> findAll() {
        return new ArrayList<>(dados.values());
    }

    public Optional<Agendamento> findById(Long id) {
        return Optional.ofNullable(dados.get(id));
    }

    public List<Agendamento> buscarPorTipoAssunto(TipoAssunto assunto) {
        return dados.values().stream()
                .filter(a -> a.getAssunto() == assunto)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        dados.remove(id);
    }
}