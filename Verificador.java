class Verificador{
	// Classe que contém os métodos de verificação de dados.

	public boolean okReal(String numero){
		// Verifica se o valor dado é realmente real.

		int n = 0;
		int p = 0;
		for (int x = 0; x < numero.length(); x++){
			if ("0123456789".indexOf(numero.charAt(x)) != -1) n += 1;
			else if (".".indexOf(numero.charAt(x)) != -1) p += 1;
		}
		if (p != 0){
			if (numero.length() - numero.indexOf('.') - 1> 2) {
				return false;
			}
		}
		if (n + p == numero.length() && n > 0){
			if (p == 0 || p == 1) return true;
		}
		return false;
	}

	public boolean okGeral(String nome, String valoresEsperados, int dimensao){
		// Verifica se os caracteres de nome encontram-se em valoresEsperados, tendo uma dimensão específica.
		if (nome.length() != dimensao || nome.isEmpty() || nome.isBlank()){
			return false;
		}
		int caracter = 0;
		for (int indice = 0; indice < nome.length(); indice++){
			if (valoresEsperados.indexOf(nome.charAt(indice)) != -1) caracter += 1;
		}
		if (caracter == nome.length()) return true;
		else return false;
	}
}
