package util;

public class CalculoJuros {
	
	public static double JurosCompostos(double valor, double juros, int tempo) {
		double valorTotal = valor*(Math.pow(juros, tempo));
		System.out.println(valorTotal);
		return valorTotal;
	}
}
