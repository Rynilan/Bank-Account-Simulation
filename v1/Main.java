import java.util.ArrayList;
import java.util.Scanner; 


public class Main{
	// Classe principal do banco.
	
	public static void main(String[] args){
		// Método principal.

		Scanner leia = new Scanner(System.in);
		String acao = "";
		ArrayList<Cliente> cadastrados = new ArrayList<Cliente>();

		String nome;
		double valor;
		String senha;
		Id numeracao = new Id();

		Acesso login;
		Cliente cadastro;
		Verificador ver = new Verificador();

		while (!acao.equals("4")){
			System.out.print(
				"\033[H\033[2J" + " ----- I, u and, Money -----\n\n" +
				"Bem vindo ao banco 'I u and money',\no que farás?\n" +
				"\n1 - Entrar na conta\n2 - Criar conta.\n3 - Fazer depó" +
				"sito.\n4 - Sair.\n > "
			);
			acao = leia.nextLine();
			System.out.println();
			switch (acao){
				case "1":
				System.out.print("Indique o ID de sua conta.\n > ");
				nome = leia.nextLine();
				if (!(ver.okGeral(nome, "1234567890", 3))){
					System.out.println("ID não indexado a uma conta.");
					break;
				}
				if (!(Integer.parseInt(nome) - 1 < cadastrados.size() && Integer.parseInt(nome) - 1 >= 0)) {
					System.out.println("ID não indexado a uma conta.");
					break;
				} else {
					System.out.print("Informe a senha.\n > ");
					senha = leia.nextLine();
					login = new Acesso(cadastrados.get(Integer.parseInt(nome) - 1), numeracao, cadastrados, senha, nome);
					break;
				}

				case "2":
				System.out.print("Diga-nos o nome de quem administrará a conta,\nsem acentos ou símbolos.\n > ");
				nome = leia.nextLine().toUpperCase();
				if (!(ver.okGeral(nome, "ABCDEFGHIJKLMNOPQRSTUVWXYZ ", nome.length()))){
					System.out.println("Nome inválido, cadastro cancelado.");
					break;
				}
				System.out.print("Quanto depositarás para iniciar?\nOBS: o delimitador decimal é o ponto e,\n" +
						 "o valor mínimo é de R$ 50.00.\n R$ ");
				acao = leia.nextLine();
				if (!(ver.okReal(acao))){
					System.out.println("Valor inválido, cadastro cancelado.");
					break;
				} else if (Double.parseDouble(acao) < 50.00){
					System.out.println("Valor abaixo do esperado, cadastro cancelado.");
					break;
				}
				valor = Double.parseDouble(acao);
				acao = "2";
				System.out.print("Crie uma senha, 4 caracteres e numérica.\n > ");
				senha = leia.nextLine();
				if (!(ver.okGeral(senha, "0123456789", 4))){
					System.out.println("Senha inválida, cadastro cancelado.");
					break;
				}
				cadastrados.add(new Cliente(valor, nome, senha, numeracao));
				System.out.println("\nCadastro executado com sucesso.");
				System.out.println("ID: " + numeracao.lastIdStr());
				break;

				case "3":
				System.out.print("Indique o valor.\n R$ ");
				acao = leia.nextLine();
				if (!(ver.okReal(acao))){
					System.out.println("Valor inválido, depósito cancelado.");
					break;
				}
				valor = Double.parseDouble(acao);
				acao = "3";
				System.out.print("Indique o ID da conta que receberá o depósito.\n > ");
				nome = leia.nextLine();
				if (!(ver.okGeral(nome, "1234567890", 3))){
					System.out.println("ID não endereçado a uma conta, depósito cancelado.");
					break;
				} else if (!(Integer.parseInt(nome) > 0 && Integer.parseInt(nome) <= cadastrados.size())){
					System.out.println("ID não endereçado a uma conta, depósito cancelado.");
					break;
				}
				cadastrados.get(Integer.parseInt(nome) - 1).deposito(valor, true);
				System.out.println("Depósito realizado com sucesso.");
				break;

			}
			if (!(acao.equals("4"))){
				System.out.println("\nPressione 'Enter' para continuar.");
				leia.nextLine();
			}
		
		}
		System.out.println("Obrigado pela presença.");
		leia.close();
	}
}
