import java.util.ArrayList;
import java.util.Scanner;


public class Acesso{
	// Classe que será instanciada quando o usuário tentar entrar em uma conta.

	public Acesso(Cliente usuario, Id cad, ArrayList<Cliente> clienteCad, String senhaDada, String idDado){
		// Verificação se os dados inseridos conferem.

		usuario.entrar(idDado, senhaDada);
		if (usuario.entrou()){
			login(usuario, cad, clienteCad);
		}
	}

	public static double qualValor(Scanner stdin, Verificador teste){
		// Solicita um valor ao cliente (reutilização).

		System.out.print("Indique a quantia.\n > R$ ");
		String quantia = stdin.nextLine();
		while (!(teste.okReal(quantia))){
			System.out.print("Valor inválido, digite outro.\n - R$ ");
			quantia = stdin.nextLine();
		}
		return Double.parseDouble(quantia);
	}

	private void login(Cliente cliente, Id cad, ArrayList<Cliente> clienteCad){
		// Método (principal) a ser executado caso as informações sejam confirmadas.

		String escolha = "";
		double valor;

		Verificador erro = new Verificador();
		Scanner input = new Scanner(System.in);
		while (!(escolha.equals("5"))){
			System.out.println("\033[H\033[2J  Bem vindo(a) " + cliente.getAdministrador() +
					   "          SALDO: " + String.format("R$ %.2f", cliente.getSaldo()));
			System.out.print("\nQue ações tomarás?\n1 - Depósito.\n2 - Saque.\n" +
					   "3 - Transferência.\n4 - Extrato.\n5 - Sair.\n > ");
			escolha = input.nextLine();
			System.out.println();
			switch (escolha){
			case "1":
				cliente.deposito(qualValor(input, erro), true);
				System.out.println("Depósito realizado com sucesso.");
				break;

			case "2":
				valor = qualValor(input, erro);
				cliente.retirada(valor, true);
				if (valor <= cliente.getSaldo()){
				System.out.println("Saque realizado com sucesso.");
				}
				break;

			case "3":
				System.out.print("Indique o ID da conta para quem queres transferir.\n > ");
				escolha = input.nextLine();
				if (!(erro.okGeral(escolha, "0123456789", 3))) {
					System.out.println("ID não endereça conta alguma.");
					break;
				}
				valor = cliente.getSaldo();
				if (Integer.parseInt(escolha) - 1 >= 0 && Integer.parseInt(escolha) - 1 < cad.lastId()){
					cliente.transferencia(qualValor(input, erro), clienteCad.get(Integer.parseInt(escolha) - 1));
					if (valor == cliente.getSaldo()) {
						System.out.println("Saldo insuficiente.");
					} else {
						System.out.println("Transferência realizada com sucesso.");
					}
				} else {
					System.out.println("ID não endereça conta alguma.");
					break;
				}
				break;

			case "4":
				cliente.historia();
				break;

			}
			if (!(escolha.equals("5"))) {
				System.out.println("\nPressione 'Enter' para continuar");
				input.nextLine();
			}
		}
		System.out.println("Até mais " + cliente.getAdministrador());
		cliente.sair();
	}
}
