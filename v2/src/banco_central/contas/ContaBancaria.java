package banco_central.contas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import banco_central.contas.auxiliares.RegistroDeMovimento;

/**
 * Class that ressemble the commom method and attributes of
 * the bank accounts.
 */
public abstract class ContaBancaria {
	protected List<RegistroDeMovimento> historico;
	protected double saldo;
	protected String usuario;
	protected String numeroDaConta;
	protected String senha;
	protected boolean iniciado;
	protected LocalDate criacao;
	protected LocalDate ultimaAtualizacao;
	private boolean chavePega;

	protected ContaBancaria(String usuario, double depositoInicial, String senha) {
		this.usuario = usuario;
		this.saldo = depositoInicial;
		this.numeroDaConta = String.format("%03.0f", Math.random() * 1000);
		this.senha = senha;
		this.historico = new ArrayList<RegistroDeMovimento>();
		this.historico.add(
			new RegistroDeMovimento(usuario, usuario, depositoInicial, "INICIALIZAÇÃO")
		);
		this.iniciado = false;
		this.chavePega = false;
		this.criacao = LocalDate.now();
		this.ultimaAtualizacao = LocalDate.now();
	}

	protected int pegarIntervaloEntreAtualizacoes() {
		return this.ultimaAtualizacao.getYear() == LocalDate.now().getYear() ? LocalDate.now().getMonthValue() - this.ultimaAtualizacao.getMonthValue() : (LocalDate.now().getYear() - this.ultimaAtualizacao.getYear()) * 12 + LocalDate.now().getMonthValue() - this.ultimaAtualizacao.getMonthValue();
	}

	public String getNumeroDaConta() {
		if (!this.chavePega) {
			this.chavePega = true;
			return this.numeroDaConta;
		}
		return null;
	}

	protected abstract boolean movimentoBancario();

	private boolean compararSenha(String senha) {
		this.movimentoBancario();
		return this.senha.equals(senha);
	}

	protected boolean tentarEntrar(String senha) {
		this.movimentoBancario();
		if (this.compararSenha(senha) && !this.iniciado) {
			this.iniciado = true;
			return true;
		}
		return false;
	}

	public boolean sair() {
		if (this.iniciado) {
			this.iniciado = false;
			return true;
		}
		return false;
	}

	public boolean depositar(double valor) {
		this.movimentoBancario();
		this.saldo += valor;
		if (this.iniciado) {
			this.historico.add(new RegistroDeMovimento(this.usuario, this.usuario, valor, "DEPÓSITO"));
		} else {
			this.historico.add(new RegistroDeMovimento("ANÔNIMO", this.usuario, valor, "DEPÓSITO"));
		}
		return true;
	}

	public boolean receberTransferência(double valor, String agente) {
		this.movimentoBancario();
		this.saldo += valor;
		this.historico.add(new RegistroDeMovimento(agente, this.usuario, valor, "TRANSFERÊNCIA"));
		return true;
	}

	public boolean sacar(double valor) {
		this.movimentoBancario();
		if (this.saldo >= valor && this.iniciado) {
			this.saldo -= valor;
			this.historico.add(new RegistroDeMovimento(this.usuario, this.usuario, valor, "SAQUE"));
			return true;
		}
		return false;
	}

	public boolean transferir(double valor, ContaBancaria destinatario) {
		this.movimentoBancario();
		if (this.saldo >= valor && this.iniciado) {
			this.saldo -= valor;
			destinatario.receberTransferência(valor, this.usuario);
			this.historico.add(new RegistroDeMovimento(this.usuario, destinatario.usuario, valor, "TRANFERÊNCIA"));
			return true;
		}
		return false;
	}

	public List<RegistroDeMovimento> getHistorico() {
		this.movimentoBancario();
		return this.iniciado ? this.historico : null;
	}
}
