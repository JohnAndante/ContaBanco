import java.util.Locale;
import java.util.Scanner;

public class ContaTerminal {
    private Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    private int qtdContas = 0;
    private int[] numerosContas = new int[qtdContas];
    private String[] nomesAgencias = new String[qtdContas];
    private String[] nomesClientes = new String[qtdContas];
    private double[] saldos = new double[qtdContas];

    private void mainLoop() {
        while (true) {
            limpaTela();
            imprimeCabecalho();
            System.out.println("Digite uma das opções abaixo:");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Acessar conta");
            System.out.println("0 - Sair");
            System.out.print("\n> ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    acessarConta();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    pausar();
            }

        }
    }

    private void criarConta() {
        while (true) {
            limpaTela();
            imprimeCabecalho();
            System.out.println(">>> Criar Conta <<<\n");

            System.out.println("Digite o número da conta:");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o nome da agência:");
            String nomeAgencia = scanner.nextLine();

            System.out.println("Digite o nome do cliente:");
            String nomeCliente = scanner.nextLine();

            System.out.println("Digite o saldo inicial - use ponto para separar os centavos:");
            double saldo = scanner.nextDouble();
            scanner.nextLine();

            if (verificaContaExiste(numeroConta)) {
                System.out.println("Conta já existe!");
                pausar();
                return;
            }

            adicionaConta(numeroConta, nomeAgencia, nomeCliente, saldo);

            limpaTela();
            System.out.println("> Conta criada com sucesso! <\n");

            System.out.println("Olá " + nomeCliente + "!");
            System.out.println("Obrigado por criar uma conta em nosso banco!");
            System.out.println("Sua agência é a " + nomeAgencia);
            System.out.println("Sua conta é " + numeroConta);
            System.out.printf("E seu saldo de R$ " + formataSaldo(saldo) + " já está disponível para saque!\n");

            System.out.println("\nAcesse sua conta para realizar operações!\n");

            pausar();
            return;
        }
    }

    private void acessarConta() {
        while (true) {

            limpaTela();
            imprimeCabecalho();
            System.out.println(">>> Acessar Conta <<<\n");

            System.out.println("Digite o número da conta:");
            int numeroConta = scanner.nextInt();
            scanner.nextLine();

            if (!verificaContaExiste(numeroConta)) {
                System.out.println("Conta não encontrada!");
                pausar();
                return;
            }

            int indiceConta = 0;
            for (int i = 0; i < qtdContas; i++) {
                if (numerosContas[i] == numeroConta) {
                    indiceConta = i;
                    break;
                }
            }

            while (true) {
                limpaTela();
                imprimeCabecalho();
                System.out.println(">>> Acessar Conta <<<\n");

                System.out.println("> Bem vindo, " + nomesClientes[indiceConta] + "! <");
                System.out.println("| Número da conta: " + numerosContas[indiceConta]);
                System.out.println("| Agência: " + nomesAgencias[indiceConta]);
                System.out.println("| Saldo: R$ " + formataSaldo(saldos[indiceConta]));

                System.out.println("\nDigite uma das opções abaixo:");
                System.out.println("1 - Depositar");
                System.out.println("2 - Sacar");
                System.out.println("0 - Voltar");
                System.out.print("\n> ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        depositar(indiceConta);
                        break;
                    case 2:
                        sacar(indiceConta);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida!");
                        pausar();
                }
            }

        }
    }

    private void depositar(int indiceConta) {
        limpaTela();
        imprimeCabecalho();
        System.out.println(">>> Depositar <<<\n");

        System.out.println("Digite o valor do depósito - use ponto para separar os centavos:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        saldos[indiceConta] += valor;
        System.out.println("\nDepósito realizado com sucesso!");
        System.out.println("Novo saldo: R$" + formataSaldo(saldos[indiceConta]));
        System.out.println();

        pausar();
    }

    private void sacar(int indiceConta) {
        limpaTela();
        imprimeCabecalho();
        System.out.println(">>> Sacar <<<\n");

        System.out.println("Digite o valor do saque - use ponto para separar os centavos:");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (saldos[indiceConta] < valor) {
            System.out.println("\n> Saldo insuficiente! <\n");
            pausar();
            return;
        }

        saldos[indiceConta] -= valor;
        System.out.println("\nSaque realizado com sucesso!");
        System.out.println("Novo saldo: R$ " + formataSaldo(saldos[indiceConta]));
        System.out.println();

        pausar();
    }

    private boolean verificaContaExiste(int numeroConta) {
        for (int i = 0; i < qtdContas; i++) {
            if (numerosContas[i] == numeroConta) {
                return true;
            }
        }

        return false;
    }

    private void adicionaConta(int numeroConta, String nomeAgencia, String nomeCliente, double saldo) {
        int[] numerosContasTemp = this.numerosContas;
        String[] nomesAgenciasTemp = this.nomesAgencias;
        String[] nomesClientesTemp = this.nomesClientes;
        double[] saldosTemp = saldos;

        this.numerosContas = new int[qtdContas + 1];
        this.nomesAgencias = new String[qtdContas + 1];
        this.nomesClientes = new String[qtdContas + 1];
        this.saldos = new double[qtdContas + 1];

        for (int i = 0; i < qtdContas; i++) {
            this.numerosContas[i] = numerosContasTemp[i];
            this.nomesAgencias[i] = nomesAgenciasTemp[i];
            this.nomesClientes[i] = nomesClientesTemp[i];
            this.saldos[i] = saldosTemp[i];
        }

        this.numerosContas[qtdContas] = numeroConta;
        this.nomesAgencias[qtdContas] = nomeAgencia;
        this.nomesClientes[qtdContas] = nomeCliente;
        this.saldos[qtdContas] = saldo;
        this.qtdContas++;

    }

    private void imprimeCabecalho() {
        System.out.println(">>> Banco do Programador <<<\n");
    }

    private String formataSaldo(double saldo) {
        String saldoString = String.format("%.2f", saldo);
        String valorReal = saldoString.substring(0, saldoString.indexOf(','));
        String valorCentavos = saldoString.substring(saldoString.indexOf(','));

        String saldoFormatado = "";
        int contador = 0;

        for (int i = valorReal.length() - 1; i >= 0; i--) {
            saldoFormatado = valorReal.charAt(i) + saldoFormatado;
            contador++;

            if (contador == 3 && i != 0) {
                saldoFormatado = "." + saldoFormatado;
                contador = 0;
            }
        }

        return saldoFormatado + valorCentavos;
    }

    private void limpaTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void pausar() {
        System.out.println("> Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        ContaTerminal conta = new ContaTerminal();
        conta.mainLoop();
    }

}
