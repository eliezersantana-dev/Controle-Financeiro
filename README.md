# 💰 Controle Financeiro 1.0

Aplicação em Java, executada no console, para organizar a vida financeira mensal: cadastrar renda e despesas, acompanhar o saldo e guardar dinheiro em um cofre protegido por senha.

## Funcionalidades

- Cadastro de renda e de despesas
- Resumo financeiro e cálculo de saldo (o valor guardado no cofre é descontado do saldo)
- Escolha do mês para consultar e registrar movimentações
- Cofre com depósito, retirada e consulta de valor
  - Finalidade e senha de 4 dígitos definidas na primeira utilização
  - Senha exigida em depósito e retirada quando o cofre está trancado
  - Limite de 3 tentativas de senha

## Tecnologias e conceitos

- Java 25
- Programação Orientada a Objetos (classe `Cofre`)
- Vetores (arrays) para armazenar nomes e valores
- Métodos separados por responsabilidade (ex.: `menuCofre`)

## Como executar

1. Clone o repositório:
   ```
   git clone https://github.com/elisantana-dev/Controle-Financeiro.git
   ```
2. Abra o projeto no IntelliJ IDEA
3. Execute a classe `Main`

## O que aprendi

Desenvolver este projeto me permitiu praticar, dentro e fora da sala de aula, o que fui estudando:

- **Laços de repetição e métodos:** usei no menu, na validação de senha e na separação das funcionalidades em métodos.
- **Programação Orientada a Objetos:** ao criar a classe `Cofre`, entendi na prática como classes e objetos organizam o código. Avancei nesse tema por conta própria, com estudo extra além do curso.
- **Depuração:** aprendi a ler e interpretar erros e bugs, principalmente os ligados à leitura e ao uso de variáveis.
- **Boas práticas:** passei a separar responsabilidades, nomear melhor métodos e variáveis e implementar novas funcionalidades de forma organizada.

## Próximos passos

- [ ] Transformar em aplicação web
- [ ] Backend com Spring Boot
- [ ] Front-end com Angular, com dashboard
- [ ] Trocar os vetores por `ArrayList` e objetos

## Autor

**Eliezer Santana** · Estudante de Análise e Desenvolvimento de Sistemas (Senac) · [LinkedIn](https://www.linkedin.com/in/eliezer-santana-1b026b258/)
