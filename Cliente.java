import java.util.ArrayList;


public class Cliente{
	// Classe que irá armazenar as informações do cliente.

	// Atributos do cliente.
	private double saldo;
	private ArrayList<String[]> extrato = new ArrayList<String[]>(); // Armazena as ações tomadas na conta.
	private String administrador;
	private String id; // Equivalente ao número da conta (identificador).
	private String senha;
	private boolean logado = false; // Avalia se o cliente entrou na conta (isso limita suas ações caso não esteja).

	public Cliente(double inicio, String nome, String passe, Id conta){
		//Inicialização.
		
		saldo = inicio;
		administrador = nome.toUpperCase();
		String[] acao =  {"INICIO", String.format("R$ %.2f", inicio), administrador, administrador};
		extrato.add(acao);
		id = conta.nextId();
		senha = passe;
	}

	public boolean retirada(double valor, boolean coincide){
		// Saca uma determinada quantia, caso o cliente tenha entrado em sua conta.

		if (entrou()){
			if (saldo >= valor){
				saldo -= valor;
				if (coincide) {
				String[] acao = {"SAQUE",  String.format("R$ %.2f", valor), administrador, administrador};
				extrato.add(acao);
				}
				return true;
			} else{
				if (coincide) {
					System.out.println("Saldo insuficiente.");
				}
				return false;
			}
		}
		return false;
	}

	public void deposito(double valor, boolean coincide){
		// Deposita uma determinada quantia.
		
		saldo += valor;
		if (coincide){
			if (!(entrou())){ String[] acao = {"DEPÓSITO", String.format("R$ %.2f", valor), "EXTERNO", administrador};
					extrato.add(acao);}
			else{
				String[] acao = {"DEPÓSITO", String.format("R$ %.2f", valor), administrador, administrador};
				extrato.add(acao);}
		}
	}

	public void transferencia(double valor, Cliente destinatario){
		// Transfere um determinado valor para destinatário, caso o cliente possua saldo menor ou igual a ele e,
		// tenha entrado em sua conta.

		if (entrou()){
			if (retirada(valor, false)){
				destinatario.deposito(valor, false);
				String[] acao = {"TRANSFERÊNCIA", String.format("R$ %.2f", valor), administrador, destinatario.administrador};
				extrato.add(acao);
				destinatario.extrato.add(acao);
			}
		}
	}

	public void historia(){
		// Mostra todo o histórico da conta do usuario, caso ele tenha entrado em sua conta.
		if (entrou()){
			System.out.printf("%-15s | %-15s | %-15s | %-15s%n", "AÇÃO", "VALOR", "REALIZADOR(A)", "RECEPTOR(A)");
			for (String[] acao: extrato) {
				System.out.printf("%-15s | %-15s | %-15s | %-15s%n", acao[0] ,acao[1] , acao[2] , acao[3]);
			}
		}
	}

	public void sair(){
		// Sai da conta.

		logado = false;
	}
	
	public void entrar(String Id, String senha){
		// Verifica os dados oferecidos, em caso de concordância acede à conta.
		
		if (comparaSenha(senha) && comparaId(Id)){
			logado = true;
		} else {
			System.out.println("Senha dada não corresponde ao id ofertado.");
		}
	}

	private boolean comparaSenha(String tentativa){
		// Compara uma determinada string (tentativa) com a senha do cliente e retorna se ressoam.

		if (tentativa.equals(senha)) return true;
		return false;
	}

	private boolean comparaId(String tentativa){
		// Compara um determinada String (tentativa) com o id do cliente e retorno se ressoam.

		if (tentativa.equals(id)) return true;
		return false;
	}

	public boolean entrou(){
		// Retorna o estado da conta, logado, true, não logado, false.

		return logado;
	}

	public String getAdministrador(){
		// Retorna o nome do administrador caso o cliente tenha entrado em sua conta.

		if (entrou()){
			return administrador;
		}
		return "";
	}

	public double getSaldo(){
		// Retorna o saldo do cliente, caso este tenha entrado em sua conta.

		if (entrou()){
			return saldo;
		}
		return 0;
	}
}
