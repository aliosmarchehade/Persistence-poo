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
		Conta conta = controle1.buscarPorId(13L); //CONTA PRA EU VERIFICAR
		Movimentacao movimentacao = new Movimentacao();
	
	     
	
		cliente.setCpf("11995714909");
		//cliente.setNome("Ali");
		//	controleCliente.inserir(cliente);
		
		
//		conta.setDataAbertura(new Date());
//		conta.setContaTipo(ContaTipo.CONTA_CORRENTE);
//		conta.setCliente(cliente);
//		controle1.adicionarConta(cliente.getId());
//		controle1.inserir(conta);
		
		movimentacao.setDataTransacao(new Date());
		movimentacao.setDescricao("saque de 15,00 no dia 08/12/24");
		movimentacao.setTipoTransacao(TransacaoTipo.SAQUE);
		movimentacao.setValorOperacao(10.);
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
		//Long id = 15L;
		Scanner sc= new Scanner(System.in);
		
		System.out.print("Digite o ID da conta: ");
	    Long id = sc.nextLong();

	    System.out.print("Digite o mês (1-12): ");
	    int mes = sc.nextInt();

	    System.out.print("Digite o ano (ex: 2024): ");
	    int ano = sc.nextInt();
		
		Double saldo = contaDAO.calculoSaldo(id);
		System.out.println("O saldo da conta " + id + " é de: R$ " + saldo); 
		System.out.println("CPF recebido: " + cliente.getCpf());
		
		Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.YEAR, ano);
	    cal.set(Calendar.MONTH, mes -1);
	    cal.set(Calendar.DAY_OF_MONTH, 1);
	    Date inicio = cal.getTime();

	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	    Date fim = cal.getTime();
	     
	    controle.consultaDoExtrato(id, inicio, fim);
	}	
	}
		
		/* Apresentar no terminal quantas contas do cliente com determinado ID
		int quantidadeContas = contaDAO.contarPorConta(12L); 
		System.out.println("Número de contas do cliente: " + quantidadeContas);
	}*/

