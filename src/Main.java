import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        String mes = escolherMes(entrada);

        String[] nome = new String[50];
        double[] valores = new double[50];
        double renda = 0;
        double total = 0;
        int contador = 0;
        double saldo = 0;
        Cofre cofre = new Cofre();

        boolean continuar = true;
        while (continuar) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("              CONTROLE FINANCEIRO");
            System.out.println("==================================================");
            System.out.println("1 - Cadastrar Renda");
            System.out.println("2 - Cadastrar Despesas");
            System.out.println("3 - Ver Resumo");
            System.out.println("4 - Ver Saldo");
            System.out.println("5 - Cofre");
            System.out.println("6 - Sair");
            System.out.println("==================================================");
            System.out.println("Escolha uma opção:");

            int opcao;
            try {
                opcao = Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número de 1 a 6.");
                continue;
            }

            switch (opcao) {

                case 1:
                    if (renda > 0) {
                        System.out.printf("Você já tem uma renda cadastrada de R$ %.2f.%n", renda);
                        System.out.println("Deseja substituí-la? (sim/não)");
                        if (!lerSimNao(entrada)) {
                            break;
                        }
                    }
                    renda = calcularRenda(entrada);
                    saldo = calcularSaldo(renda, total, cofre);
                    break;

                case 2:
                    double[] resultadoDespesas = calcularDespesas(nome, valores, mes, contador, total, entrada);

                    total = resultadoDespesas[0];
                    contador = (int) resultadoDespesas[1];
                    saldo = calcularSaldo(renda, total, cofre);
                    break;

                case 3:
                    exibirResumo(mes, renda, contador, total, nome, valores, saldo, cofre);
                    break;

                case 4:
                    if (saldo < 0) {
                        System.out.printf("Você está em déficit de R$ %.2f%n", Math.abs(saldo));
                    } else {
                        System.out.printf("Seu saldo disponível é: R$ %.2f%n", saldo);
                    }
                    break;

                case 5:
                    saldo = menuCofre(saldo, cofre, entrada);
                    break;

                case 6:
                    System.out.println("Saindo...");
                    continuar = false;
                    break;

                default:
                    System.out.println("Opção inválida! Digite um número de 1 a 6.");
            }
        }
    }


    public static String escolherMes(Scanner entrada) {

        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        System.out.println("========================================================");
        System.out.println("     Seja Bem Vindo ao seu Controle Financeiro 1.0");
        System.out.println("========================================================");
        System.out.println("               Informe o Mês desejado:");
        System.out.println("========================================================");
        System.out.println("  1- Janeiro         2- Fevereiro         3- Março");
        System.out.println("  4- Abril           5- Maio              6- Junho");
        System.out.println("  7- Julho           8- Agosto            9- Setembro");
        System.out.println("  10- Outubro        11- Novembro         12- Dezembro");
        System.out.println("========================================================");

        while (true) {
            int escolha;
            try {
                escolha = Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número de 1 a 12.");
                continue;
            }

            if (escolha >= 1 && escolha <= 12) {
                return meses[escolha - 1];
            }
            System.out.println("Opção inválida! Digite um número de 1 a 12.");
        }
    }

    public static double lerValor(Scanner entrada, boolean aceitaZero) {
        while (true) {
            String texto = entrada.nextLine().trim().replace(",", ".");

            try {
                double valor = Double.parseDouble(texto);

                if (!Double.isFinite(valor)) {
                    System.out.println("Entrada inválida! Digite um valor numérico:");
                } else if (valor < 0 || (valor == 0 && !aceitaZero)) {
                    if (aceitaZero) {
                        System.out.println("Valor inválido! Digite zero ou um valor positivo:");
                    } else {
                        System.out.println("Valor inválido! Digite um valor maior que zero:");
                    }
                } else {
                    return valor;
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um valor numérico:");
            }
        }
    }

    public static boolean lerSimNao(Scanner entrada) {
        while (true) {
            String resposta = entrada.nextLine().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            }
            if (resposta.equals("n") || resposta.equals("nao") || resposta.equals("não")) {
                return false;
            }
            System.out.println("Resposta inválida! Digite sim ou não:");
        }
    }

    public static double calcularRenda(Scanner entrada) {

        System.out.println("Informe seu salário:");
        double salario = lerValor(entrada, false);

        double rendaVariavel = 0;
        System.out.println("Possui renda variável? (sim/não)");
        if (lerSimNao(entrada)) {
            System.out.println("Informe a renda variável:");
            rendaVariavel = lerValor(entrada, true);
        }

        double empresa = 0;
        System.out.println("Possui empresa? (sim/não)");
        if (lerSimNao(entrada)) {
            System.out.println("Informe o lucro da sua empresa:");
            empresa = lerValor(entrada, true);
        }

        double renda = salario + rendaVariavel + empresa;

        System.out.println();
        System.out.printf("Parabéns! Sua renda é: R$ %.2f%n", renda);
        System.out.println();

        return renda;
    }

    public static double[] calcularDespesas(String[] nome, double[] valores, String mes, int contador, double total, Scanner entrada) {

        boolean maisDespesas = true;

        while (maisDespesas) {

            if (contador >= nome.length) {
                System.out.println("Limite de despesas atingido!");
                break;
            }

            System.out.println("Digite o Nome da Despesa " + (contador + 1) + ":");
            String nomeDespesa = entrada.nextLine().trim();

            while (nomeDespesa.isEmpty()) {
                System.out.println("O nome não pode ficar vazio! Digite novamente:");
                nomeDespesa = entrada.nextLine().trim();
            }
            nome[contador] = nomeDespesa;

            System.out.println("Digite o Valor Gasto no mês de " + mes + " com " + nome[contador] + ":");
            valores[contador] = lerValor(entrada, false);

            total = total + valores[contador];
            contador++;

            System.out.println("Muito bom! Deseja adicionar mais despesas? (sim/não)");
            maisDespesas = lerSimNao(entrada);
        }

        return new double[]{total, contador};
    }

    public static double calcularSaldo(double renda, double total, Cofre cofre) {

        return renda - total - cofre.valor;
    }

    public static void exibirResumo(String mes, double renda, int contador, double total, String[] nome, double[] valores, double saldo, Cofre cofre) {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("              CONTROLE FINANCEIRO");
        System.out.println("                    " + mes.toUpperCase());
        System.out.println("==================================================");

        System.out.printf("RENDA TOTAL: R$ %.2f%n", renda);

        System.out.println("==================================================");
        System.out.println();
        System.out.println("DESPESAS");

        for (int i = 0; i < contador; i++) {
            System.out.printf("%s - R$ %.2f%n", nome[i], valores[i]);
        }

        System.out.println("--------------------------------------------------");

        System.out.printf("TOTAL DE DESPESAS:   R$ %.2f%n", total);

        System.out.println("--------------------------------------------------");

        if (saldo < 0) {
            System.out.printf("DÉFICIT:             R$ %.2f%n", Math.abs(saldo));
        } else {
            System.out.printf("SALDO DISPONÍVEL:    R$ %.2f%n", saldo);
        }
        System.out.printf("VALOR NO COFRE:      R$ %.2f%n", cofre.valor);

        System.out.println("==================================================");
        System.out.println();
    }

    public static boolean validarSenha(Cofre cofre, Scanner entrada) {

        int tentativas = 0;

        while (tentativas < 3) {
            System.out.println("Insira a senha do cofre:");
            String tentativa = entrada.nextLine().trim();

            if (tentativa.equals(cofre.senha)) {
                return true;
            }

            tentativas++;
            int restantes = 3 - tentativas;

            if (restantes > 0) {
                System.out.println("Senha incorreta! Tentativas restantes: " + restantes);
            }
        }

        System.out.println("Número máximo de tentativas atingido. Operação cancelada.");
        return false;
    }

    public static double depositoCofre(double saldo, Cofre cofre, Scanner entrada) {

        if (saldo <= 0) {
            System.out.println("Saldo insuficiente para realizar depósitos no cofre.");
            return saldo;
        }

        if (cofre.trancado && !validarSenha(cofre, entrada)) {
            return saldo;
        }

        System.out.printf("Seu saldo disponível é: R$ %.2f%n", saldo);
        System.out.println("Informe o valor a ser depositado no cofre:");
        double deposito = lerValor(entrada, false);

        if (deposito > saldo) {
            System.out.println("Saldo insuficiente!");
            return saldo;
        }

        // Cadastro só na primeira vez e só depois de o depósito ser válido.
        if (cofre.senha == null) {
            System.out.println("Digite a finalidade do seu cofre:");
            String finalidade = entrada.nextLine().trim();

            while (finalidade.isEmpty()) {
                System.out.println("A finalidade não pode ficar vazia! Digite novamente:");
                finalidade = entrada.nextLine().trim();
            }
            cofre.finalidade = finalidade;

            System.out.println("Cadastre sua senha de acesso ao cofre.");
            System.out.println("Ela deve conter quatro dígitos:");
            String senha = entrada.nextLine().trim();

            while (!senha.matches("[0-9]{4}")) {
                System.out.println("Senha inválida! Digite exatamente quatro números:");
                senha = entrada.nextLine().trim();
            }
            cofre.senha = senha;
        }

        saldo = saldo - deposito;
        cofre.valor = cofre.valor + deposito;
        System.out.printf("Ótimo! R$ %.2f armazenado com sucesso!%n", deposito);

        if (!cofre.trancado) {
            System.out.println("Deseja trancar seu cofre? (sim/não)");
            if (lerSimNao(entrada)) {
                cofre.trancado = true;
                System.out.println("Cofre trancado com sucesso!");
            }
        }

        return saldo;
    }

    public static double retiradaCofre(double saldo, Cofre cofre, Scanner entrada) {

        if (cofre.valor <= 0) {
            System.out.println("Seu cofre está vazio.");
            return saldo;
        }

        if (cofre.trancado && !validarSenha(cofre, entrada)) {
            return saldo;
        }

        System.out.printf("Valor disponível no cofre: R$ %.2f%n", cofre.valor);
        System.out.println("Informe o valor a ser retirado:");
        double retirada = lerValor(entrada, false);

        while (retirada > cofre.valor) {
            System.out.println("Valor maior que o disponível no cofre! Digite novamente:");
            retirada = lerValor(entrada, false);
        }

        cofre.valor = cofre.valor - retirada;
        saldo = saldo + retirada;
        System.out.printf("R$ %.2f retirado com sucesso!%n", retirada);

        return saldo;
    }

    public static void verCofre(Cofre cofre) {
        System.out.printf("Valor no cofre: R$ %.2f%n", cofre.valor);

        if (cofre.finalidade != null) {
            System.out.println("Finalidade: " + cofre.finalidade);
        }
        if (cofre.trancado) {
            System.out.println("Situação: trancado");
        } else {
            System.out.println("Situação: destrancado");
        }
    }

    public static void alternarTranca(Cofre cofre, Scanner entrada) {

        if (cofre.trancado) {
            if (validarSenha(cofre, entrada)) {
                cofre.trancado = false;
                System.out.println("Cofre destrancado com sucesso!");
            }
        } else if (cofre.senha == null) {
            System.out.println("Faça um depósito primeiro para cadastrar a senha do cofre.");
        } else {
            cofre.trancado = true;
            System.out.println("Cofre trancado com sucesso!");
        }
    }

    public static double menuCofre(double saldo, Cofre cofre, Scanner entrada) {

        boolean voltar = false;
        while (!voltar) {

            System.out.println();
            System.out.println("==================================================");
            System.out.println("             BEM VINDO AO SEU COFRE");
            System.out.println("==================================================");
            System.out.println("1 - Depósito");
            System.out.println("2 - Retirada");
            System.out.println("3 - Ver Cofre");
            System.out.println("4 - Trancar / Destrancar");
            System.out.println("5 - Voltar");
            System.out.println("==================================================");
            System.out.println("Digite a opção desejada:");

            int opcao;
            try {
                opcao = Integer.parseInt(entrada.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite um número de 1 a 5.");
                continue;
            }

            switch (opcao) {

                case 1:
                    saldo = depositoCofre(saldo, cofre, entrada);
                    break;

                case 2:
                    saldo = retiradaCofre(saldo, cofre, entrada);
                    break;

                case 3:
                    verCofre(cofre);
                    break;

                case 4:
                    alternarTranca(cofre, entrada);
                    break;

                case 5:
                    voltar = true;
                    break;

                default:
                    System.out.println("Opção inválida! Digite um número de 1 a 5.");
            }
        }

        return saldo;
    }
}