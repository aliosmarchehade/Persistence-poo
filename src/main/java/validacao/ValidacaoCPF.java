package validacao;

import entidade.Cliente;

public class ValidacaoCPF {
	
	
	public static boolean validarCPF(String cpf) {
		
		//verifica se cpf está nulo
		if (cpf == null || cpf.isEmpty()) {
			System.out.println("Cpf inválido. Nulo!");
		    return false;
		}
		
		//remove tudo que não é numero
		cpf = cpf.replaceAll("\\D", ""); //o "\\D" em maiusculo, indica todo caracere que nao se trata de um numero.
		
		//valida se o cpf tem 11 digitos
		if(cpf.length() > 11 || cpf.length() < 11) {
			System.out.println("Cpf inválido por não ter 11 digitos!");
			return false;
			
		}
		
		//verifica se o cpf possui todos os numeros iguais
		char primeiroDigito = cpf.charAt(0);
		for(int i = 1 ; i < cpf.length() ; i++) {
			if(cpf.charAt(i) != primeiroDigito) {
				break;
			}
			if(i == cpf.length() - 1) {
				System.out.println("Cpf invalido por conter todos os números iguais!");
				return false;
			}
			
		}
		
		int soma = 0;
		for (int i = 0 ; i < 9 ; i++) {
			soma = soma + (cpf.charAt(i) - '0') * (10-i); //multiplica o digito pelo peso (começando em 10 e diminuindo)
		}
		
		int primeiroVerificador; 
		if (soma % 11 < 2) { 
			primeiroVerificador = 0; 
			} else { 
				primeiroVerificador = 11 - (soma % 11); 
				}
		
		soma = 0;
		for (int i = 0 ; i < 10 ; i++) {
			soma = soma + (cpf.charAt(i) - '0') * (11-i); //multiplica o digito pelo peso (começando em 11 e diminuindo, ja com o primeiro digito)
		}
		
		int segundoVerificador;
		if (soma % 11 < 2) {
			segundoVerificador = 0;
		}else {
			segundoVerificador = 11 - (soma % 11);
		}
		
		
		return (cpf.charAt(9) - '0' == primeiroVerificador) && (cpf.charAt(10) - '0' == segundoVerificador);
	
	}
	
	
}
