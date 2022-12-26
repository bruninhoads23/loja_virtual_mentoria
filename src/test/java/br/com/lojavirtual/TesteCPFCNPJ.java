package br.com.lojavirtual;

import br.com.lojavirtual.utils.ValidaCNPJ;
import br.com.lojavirtual.utils.ValidaCPF;

public class TesteCPFCNPJ {
	
public static void main(String[] args) {
	
	boolean isCnpj = ValidaCNPJ.isCNPJ("45.415.621/0001-74");
	System.out.println("Cnpj válido : " + isCnpj);
	
	boolean isCpf = ValidaCPF.isCPF("292.898.140-39");
	System.out.println("Cpf válido : " + isCpf);
	
	
}
}
