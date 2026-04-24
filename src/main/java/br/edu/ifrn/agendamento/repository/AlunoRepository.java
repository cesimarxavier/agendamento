package br.edu.ifrn.agendamento.repository;

import br.edu.ifrn.agendamento.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Criação de interace para herdar os métodos fundamentais de persistência
*/

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}