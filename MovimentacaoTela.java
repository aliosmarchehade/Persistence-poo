package visao;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import controle.ClienteControle;
import controle.ContaControle;
import controle.MovimentacaoControle;
import dao.ContaDAO;
import entidade.Cliente;
import entidade.Conta;
import entidade.ContaTipo;
import entidade.Movimentacao;
import entidade.TransacaoTipo;

public class MovimentacaoTela {

	public static void main(String[] args) {
		MovimentacaoControle controle = new MovimentacaoControle();
		ContaControle controle1 = new ContaControle();
		ClienteControle controleCliente = new ClienteControle();
		Cliente cliente = new Cliente();
		ContaDAO contaDAO = new ContaDAO();
		Cliente clientee = controleCliente.buscarPorId(28L);
		Conta conta = controle1.buscarPorId(1L); //CONTA PRA EU VERIFICAR
		Movimentacao movimentacao = new Movimentacao();
	
	     
	
		cliente.setCpf("11995714909");
		
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("depósito de 15,00 no dia 19/12/24");
		movimentacao.setTipoTransacao(TransacaoTipo.DEPOSITO);
		movimentacao.setValorOperacao(15.);
		movimentacao.setConta(conta);

		
		switch (movimentacao.getTipoTransacao()) {
	    case SAQUE:
	        controle.sacar(movimentacao, conta, cliente);
	        break;
	    case DEPOSITO:
	        controle.depositar(movimentacao, cliente);
	        break;
	    case PAGAMENTO:
	        controle.pagamento(movimentacao, conta, cliente);
	        break;
	    case PIX:
	    	System.out.println("Tarifa extra de 5,00 aplicada pela operação!");
	        controle.pagamentoPIX(movimentacao, conta, cliente);
	        break;
	    case CARTAO_DE_DEBITO:
	        controle.debito(movimentacao, conta);
	        break;
	    default:
	        System.out.println("Transação desconhecida: " + movimentacao.getTipoTransacao());
	        	break;
	}
	}	
	}
		
		/* Apresentar no terminal quantas contas do cliente com determinado ID
		int quantidadeContas = contaDAO.contarPorConta(12L); 
		System.out.println("Número de contas do cliente: " + quantidadeContas);
	}*/

