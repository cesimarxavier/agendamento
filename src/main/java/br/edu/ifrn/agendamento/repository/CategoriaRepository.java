package br.edu.ifrn.agendamento.repository;

import br.edu.ifrn.agendamento.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Criação de interace para herdar os métodos fundamentais de persistência
*/

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}