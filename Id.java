public class Id{
	// Classe feita unicamente para definir os id, identificadores, das contas que serão feitas.
	
	private int next = 0;

	public String nextId(){
		// Retorna o id para a próxima conta.

		next += 1;
		String retorno = String.valueOf(next);
		if (retorno.length() == 1) {
			retorno = "00" + retorno;
		} else if (retorno.length() == 2) {
			retorno = "0" + retorno;
		}
		return retorno;
	}

	public int lastId(){
		// Retorna o último id gerado em formato inteiro.
		
		return next;
	}

	public String lastIdStr(){
		//Retorna o último id gerado em formato de string.

		return String.format("%03d", next);
	}
}
