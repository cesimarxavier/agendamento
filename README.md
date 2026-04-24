## Justificativa do projeto
### Domínio da Aplicação
O sistema implementado gerencia Agendamentos Acadêmicos, com foco em agendamento de horários de estudo ou orientação baseados em categorias específicas.

### Mapeamento Objeto-Relacional (JPA):
A refatoração substituiu as estruturas em memória por entidades relacionais, estabelecendo os seguintes vínculos:

**1. Relacionamento 1:N**: Implementado entre Aluno (1) e Agendamento (N). Um aluno pode possuir múltiplos agendamentos, sendo a chave estrangeira gerida na tabela de agendamentos.

**2. Relacionamento N:N**: Implementado entre Agendamento (N) e Categoria (N). Um agendamento pode conter múltiplas categorias (ex: Dúvida, Revisão), gerando automaticamente a tabela associativa agendamento_categoria.


### Arquitetura com diferentes Bancos de Dados:
A aplicação foi configurada para operar com duas instâncias distintas do banco H2 em memória, garantindo a segregação de responsabilidades:

**1. Base Principal**: Exclusiva para o armazenamento das entidades de domínio e regras de negócio.

**2. Base de Auditoria**: Exclusiva para o registro de logs de operações (Criação e Exclusão).

A integridade das operações simultâneas entre as duas bases é assegurada pelo controle transacional (@Transactional) na camada de Serviço. A comunicação com a API foi totalmente isolada utilizando o padrão DTO (Request/Response).