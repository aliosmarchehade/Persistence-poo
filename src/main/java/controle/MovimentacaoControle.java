package controle;

import java.sql.Date;
import java.util.List;

import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;
import servico.MovimentacaoServico;
import servico.ContaServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
	ContaServico contaServico = new ContaServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
//		switch (movimentacao.getTipoTransacao()) {
//	    case SAQUE:
//	        return servico.sacar(movimentacao, conta, cliente);
//	    case DEPOSITO:
//	        return servico.depositar(movimentacao, cliente);
//	    case PAGAMENTO:
//	    	return servico.pagamento(movimentacao, conta, cliente);
//	    case PIX:
//	    	System.out.println("Tarifa extra de 5,00 aplicada pela operação!");
//	        return servico.pagamentoPIX(movimentacao, conta, cliente);
//	    case CARTAO_DE_DEBITO:
//	        return servico.debito(movimentacao, conta);
//	    default:
//	        System.out.println("Transação desconhecida: " + movimentacao.getTipoTransacao());
//	        	break;
//	}
		return servico.inserir(movimentacao);
	}
	
	public Movimentacao sacar(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.sacar(movimentacao, conta, cliente);
	}
	
	public Movimentacao depositar (Movimentacao movimentacao, Cliente cliente) {
		return servico.depositar(movimentacao, cliente);
	}
	
	public Movimentacao pagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.pagamento(movimentacao, conta, cliente); 
	}
	
	public Movimentacao pagamentoPIX(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		return servico.pagamentoPIX(movimentacao, conta, cliente);
	}
	
	public Movimentacao debito(Movimentacao movimentacao, Conta conta){
		return servico.debito(movimentacao, conta);
	}
	
	public List <Movimentacao> consultaDoExtrato(Long id, java.util.Date inicio, java.util.Date fim){
		return servico.consultaDoExtrato(id, inicio, fim);
	}
	
	public double consultaSaldo(Long id) {
		return servico.consultaSaldo(id);
	}
	
	

}
