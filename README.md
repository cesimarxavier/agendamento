### Justificativa do projeto
O domínio "Sistema de Agendamento de Atendimento" foi escolhido por atender de forma direta e prática aos requisitos da disciplina:

1. Separação de Dados: A entidade Agendamento fica oculta. A comunicação ocorre apenas pelos objetos AgendamentoRequestDTO e AgendamentoResponseDTO. Os tipos de assunto e status utilizam Enums.
2. Regra Condicional: O comportamento do sistema muda de acordo com o atributo "assunto". Solicitações para "Revisão de Prova", por exemplo, recebem um tempo de atendimento e prioridade diferentes de "Dúvida Geral". Falhas nessa regra acionam uma exceção customizada.
3. Múltiplas Implementações: O sistema possui uma interface NotificacaoService com duas implementações (Email e SMS). A anotação @Qualifier é utilizada no controlador para definir qual delas será executada.
4. Respostas e Persistência: Os dados são guardados em memória na camada de repositório. O controlador gerencia as requisições HTTP retornando os status exatos via ResponseEntity (200, 201 para sucesso; 400, 404 para erros).