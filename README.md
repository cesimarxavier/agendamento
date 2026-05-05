# Sistema de Agendamento - Atividade 03  

## 1. Permissões
A aplicação foi protegida utilizando Spring Security com base em 3 níveis hierárquicos:

| Endpoint                 | Método | Acesso Restrito a:                           | Retorno Esperado |
| ------------------------ | ------ | -------------------------------------------- | ---------------- |
| `/api/info`              | GET    | Público (PermitAll)                          | 200 OK           |
| `/api/agendamentos`      | GET    | ROLE_COORDENADOR, ROLE_PROFESSOR, ROLE_ALUNO | 200 OK           |
| `/api/agendamentos/{id}` | PUT    | ROLE_COORDENADOR, ROLE_PROFESSOR             | 200 OK / 404     |
| `/api/agendamentos`      | POST   | ROLE_COORDENADOR                             | 201 Created      |
| `/api/agendamentos/{id}` | DELETE | ROLE_COORDENADOR                             | 204 No Content   |

*Observação:* Tentativas de acesso a endpoints sem a Role necessária são interceptadas pelo SecurityFilterChain, retornando o status `403 Forbidden`.

## 2. Desacoplamento
O sistema demonstra desacoplamento total entre o banco de dados e a API.

**Entidade:**
A entidade `Agendamento` armazena referências diretas aos objetos `Aluno` e `Categoria`, além de dados internos de controle de persistência.

**DTO de Entrada - Exemplo de Payload:**
Utilizado no POST e PUT. Recebe apenas IDs de relacionamento e aplica validações estruturais (`@NotBlank`, `@Size`).
```json
{
  "alunoId": 1,
  "categoriasIds": [1, 2],
  "motivo": "Revisão detalhada do conteúdo da unidade II.",
  "dataHora": "2026-05-20T14:30:00"
}