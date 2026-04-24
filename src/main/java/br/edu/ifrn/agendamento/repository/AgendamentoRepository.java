package br.edu.ifrn.agendamento.repository;

import br.edu.ifrn.agendamento.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    // usa a sintaxe orientada a objetos do Hibernate
    @Query("SELECT a FROM Agendamento a WHERE a.status = :status")
    List<Agendamento> buscarPorStatusJpql(@Param("status") String status);

    // Consulta Nativa: usa SQL do banco de dados relacional
    @Query(value = "SELECT * FROM agendamento WHERE data_hora > CURRENT_TIMESTAMP", nativeQuery = true)
    List<Agendamento> buscarAgendamentosFuturosNativo();

    // realiza consulta EAGER (JOIN FETCH): Carrega os relacionamentos N:N na mesma transação para mitigar o problema N+1
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.categorias WHERE a.id = :id")
    Agendamento buscarComCategoriasEager(@Param("id") Long id);
}