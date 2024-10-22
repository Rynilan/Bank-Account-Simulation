package banco_central.contas;
import java.time.LocalDate;
import banco_central.contas.auxiliares.RegistroDeMovimento;

public class ContaPoupanca extends ContaBancaria {
	private final double RENDIMENTO = 0.007;

	public ContaPoupanca(String nome, double depositoInicial, String senha) {
		super(nome, depositoInicial, senha);
	}

	protected boolean movimentoBancario() {
		if (this.pegarIntervaloEntreAtualizacoes() != 0) {
			double rendimento = this.saldo * RENDIMENTO;
			this.saldo += rendimento;
			this.historico.add(new RegistroDeMovimento("BANCO CENTRAL", this.usuario, rendimento, "RENDIMENTO"));
			this.ultimaAtualizacao = LocalDate.now();
			return true;
		}
		return false;
	}
}
