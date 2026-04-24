package br.edu.ifrn.agendamento.audit.repository;

import br.edu.ifrn.agendamento.audit.model.LogAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAuditoriaRepository extends JpaRepository<LogAuditoria, Long> {
}