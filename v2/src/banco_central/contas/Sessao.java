package banco_central.contas;
import java.util.Map;
import java.lang.NumberFormatException;
import java.util.Scanner;

public class Sessao {
	public Sessao(ContaBancaria conta, String senha, Scanner stdin, Map<String, ContaBancaria> contas) {
		if (conta.tentarEntrar(senha)) {
			this.mainloop(conta, stdin, contas);
			conta.sair();
		}
	}

	public void mainloop(ContaBancaria conta, Scanner stdin, Map<String, ContaBancaria> contas) {
		String acao;
		do {
			System.out.printf("CONTA: %s\nUSUÁRIO: %s\nSALDO: R$ %.2f\n\n", conta.numeroDaConta, conta.usuario, conta.saldo);
			System.out.print("Bem vindo(a) Sr(a) que ação farás?\n1. Depositar\n2. Sacar\n3. Transferir\n4. Ver histórico\n5. Sair\n ?>");
			acao = stdin.nextLine();
			switch (acao) {
				case "1":
				System.out.print("Indique o valor do depósito\n ?>");
				try {
					conta.depositar(Double.parseDouble(stdin.nextLine()));
					System.out.println("Depósito feito com sucesso.");
				} catch (NumberFormatException e) {
					System.out.println("Valor inválido.");
				}
				break;
				case "2":
				System.out.print("Indique o valor do saque\n ?>");
				try {
					if (!conta.sacar(Double.parseDouble(stdin.nextLine()))) {
						System.out.println("Saldo insuficiente.");	
					} else {
						System.out.println("Saque feito com sucesso.");
					}
				} catch (NumberFormatException e) {
					System.out.println("Valor inválido.");
				}
				break;
				case "3":
				System.out.print("Indique o valor da transferência\n ?>");
				double valor;
				try {
					valor = Double.parseDouble(stdin.nextLine());
					if (conta.saldo < valor) {
						System.out.println("Saldo insuficiente.");
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("Valor inválido.");
					break;
				}
				System.out.print("Indique o número da conta para que o dinheiro será transferido.");
				ContaBancaria receptor = contas.get(stdin.nextLine());
				if (receptor == null) {
					System.out.println("Número não endereça conta alguma.\nEntrada cancelada.");
					break;
				}
				conta.transferir(valor, receptor);
				System.out.println("Transferência executada com sucesso.");
				break;
				case "4":
				System.out.printf("%15s | %15s | %10s | %15s | %13s\n", "EMISSOR", "RECEPTOR", "DATA", "AÇÃO", "VALOR");
				conta.getHistorico().stream().forEach(registro -> System.out.printf(
						"%15s | %15s | %10s | %15s | R$ %,8.2f\n",
						registro.getAgente(),
						registro.getBeneficiario(),
						registro.getData(),
						registro.getAcao(),
						registro.getValor()
					)
				);
				break;
				case "5":
				System.out.println("Saindo do ambiente privado de sua conta...");
				break;
				default:
				System.out.println(
				"Valor não indexado a ação alguma.\nRegras:\n A ação deve ser ender" +
				"eçada apenas com seu valor, sem pontos\n,espaços, símbolos ou letras."
				);
				break;
			}
		} while(!acao.equals("5"));
	}
}
