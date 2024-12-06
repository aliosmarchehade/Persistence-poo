package controle;

import java.sql.Date;
import java.util.List;

import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;
import servico.MovimentacaoServico;

public class MovimentacaoControle {
	
	MovimentacaoServico servico = new MovimentacaoServico();
		
	public Movimentacao inserir(Movimentacao movimentacao) {
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
