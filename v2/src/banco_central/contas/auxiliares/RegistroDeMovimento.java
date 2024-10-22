package banco_central.contas.auxiliares;
import java.time.LocalDate;

/** Class to hold the information about a
* history register of a bank move.
*/
public class RegistroDeMovimento {
	/** The one who made the movement. */
	private String ativo;
	/** The one who received the move. */
	private String passivo;
	/** The value of the move. */
	private double valor;
	/** The action that this move ressembles. */
	private String acao;
	/** The day that the action was made. */
	private LocalDate data;

	/** @param agente the one who makes the move;
	 * @param beneficiario the one who receive the move;
	 * @param valor the value of the move;
	 * @param acao the action that the move ressembles.
	 */
	public RegistroDeMovimento(String agente, String beneficiario, double valor, String acao) {
		this.acao = acao;
		this.valor = valor;
		this.ativo = agente;
		this.passivo = beneficiario;
		this.data = LocalDate.now();
	}

	/** Return the value of the one who made the move. */
	public String getAgente() {
		return this.ativo;
	}

	/** Return the value of the one who received the move. */
	public String getBeneficiario() {
		return this.passivo;
	}

	/** Return the value of the move. */
	public double getValor() {
		return this.valor;
	}

	/** Return the action that this move ressembles. */
	public String getAcao() {
		return this.acao;
	}

	public String getData() {
		return String.format("%02d/%02d/%04d", this.data.getDayOfMonth(), this.data.getMonthValue(), this.data.getYear());
	}
}
