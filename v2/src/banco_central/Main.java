package banco_central;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import banco_central.contas.ContaBancaria;
import banco_central.contas.ContaCorrente;
import banco_central.contas.ContaPoupanca;
import banco_central.contas.Sessao;

public class Main {

	public static void fazerDeposito(Scanner stdin, Map<String, ContaBancaria> contas) {
		System.out.print("Indique o número da para fazer o depósito.\n ?>");
		ContaBancaria conta = contas.get(stdin.nextLine());
		if (conta == null) {
			System.out.println("Conta inexistente.");
			return;
		}
		System.out.print("Indique o valor a ser depositado.\nNota: em caso de ser posto mais " +
			"de duas\ncasas decimais apenas uma será considerada.\n ?>"
		);
		try {
			conta.depositar(Double.parseDouble(stdin.nextLine()));
		} catch (NumberFormatException e) {
			System.out.println(
				"Valor inválido.\nRegras:\nNão use pontos para separar ordem\n" +
				"Para separar os decimais use pontos, não vírgulas.\nDepósito " +
				"cancelado."
			);
			return;
		}
		System.out.print("Depósito feito com sucesso.");
	}

	public static void criarConta(Scanner stdin, Map<String, ContaBancaria> contas) {
		System.out.print("Indique o tipo da conta.\n1. Poupança\n2. Corrente\n ?>");
		String tipo = stdin.nextLine();
		if (!(tipo.equals("1") || tipo.equals("2"))) {
			System.out.println("Opção inválida.\nCriação cancelada.");
			return;
		}
		System.out.print("Indique o nome de quem controlará a conta.\n ?>");
		String nome = stdin.nextLine().toUpperCase();
		System.out.print("Indique o valor do depósito inicial.\n ?>");
		double depositoInicial;
		try {
			depositoInicial = Double.parseDouble(stdin.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(
				"Valor inválido.\nRegras:\nNão use pontos para separar ordem\n" +
				"Para separar os decimais use pontos, não vírgulas.\nCriação d" +
				"e conta cancelada."
			);
			return;
		}
		System.out.print("Indique a senha para a sua conta.\n ?>");
		String senha = stdin.nextLine();
		ContaBancaria conta = tipo.equals("2") ? new ContaCorrente(nome, depositoInicial, senha) : new ContaPoupanca(nome, depositoInicial, senha);
		String numeroDaConta = conta.getNumeroDaConta();
		contas.put(numeroDaConta, conta);
		System.out.printf(
			"Conta criada com sucesso.\nO número da sua conta é: %s\n Guarde-o, ele" +
			"é a forma que interagirás com a sua conta.\n", numeroDaConta
		);
	}

	public static void entrarNaConta(Scanner stdin, Map<String, ContaBancaria> contas) {
		System.out.print("Indique o número da conta que queres entrar.\n ?>");
		ContaBancaria conta = contas.get(stdin.nextLine());
		if (conta == null) {
			System.out.println("Número não endereça conta alguma.\nEntrada cancelada.");
			return;
		}
		System.out.print("Indique a senha desta.\n ?>");
		new Sessao(conta, stdin.nextLine(), stdin, contas);
	}

	public static String mainloop(Scanner stdin, Map<String, ContaBancaria> contas) {
		System.out.print(
			" Bem vindo ao sistema do banco central, que ação inicialmente fará?" +
			"\n1. Depósito\n2. Criar conta\n3. Entrar na conta\n4. Sair do sist" +
			"ema\n ?>"
		);
		switch (stdin.nextLine()) {
			case "1":
			fazerDeposito(stdin, contas);
			break;

			case "2":
			criarConta(stdin, contas);
			break;

			case "3":
			entrarNaConta(stdin, contas);
			break;

			case "4":
			System.out.println("Obrigado pela presença, volte sempre.");
			return "4";

			default:
			System.out.println(
				"Valor não indexado a ação alguma.\nRegras:\n A ação deve ser ender" +
				"eçada apenas com seu valor, sem pontos\n,espaços, símbolos ou letras."
			);
			break;
		}
		return "";
	}

	public static void main(String[] parametros) {
		Scanner stdin = new Scanner(System.in);
		Map<String, ContaBancaria> contas = new HashMap<String, ContaBancaria>();
		System.out.println("-- BANCO CENTRAL --\n");
		do {
			;
		} while (!"4".equalsIgnoreCase(mainloop(stdin, contas)));
		stdin.close();
	}
}
