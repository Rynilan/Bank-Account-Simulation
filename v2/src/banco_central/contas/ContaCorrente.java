package banco_central.contas;
import banco_central.contas.auxiliares.RegistroDeMovimento;
import java.time.LocalDate;

public class ContaCorrente extends ContaBancaria {
	private final double TARIFA = 22;
	
	public ContaCorrente(String nome, double depositoInicial, String senha) {
		super(nome, depositoInicial, senha);
	}

	protected boolean movimentoBancario() {
		if (this.pegarIntervaloEntreAtualizacoes() != 0) {
			this.saldo -= this.TARIFA;
			this.historico.add(new RegistroDeMovimento("BANCO CENTRAL", this.usuario, TARIFA, "TAXA BANCÁRIA"));
			this.ultimaAtualizacao = LocalDate.now();
			return true;
		}
		return false;
	}
}
