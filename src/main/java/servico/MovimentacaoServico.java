package servico;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dao.GenericoDAO;
import dao.MovimentacaoDAO;
import entidade.Cliente;
import entidade.Conta;
import entidade.Movimentacao;
import entidade.TransacaoTipo;
import util.ValidacaoCPF;

public class MovimentacaoServico implements ServicoBase<Movimentacao> {
	
	MovimentacaoDAO dao = new MovimentacaoDAO();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");
	
		
	public Movimentacao sacar(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		if (cliente.getCpf() == null || !ValidacaoCPF.validarCPF(cliente.getCpf())) {
	        throw new IllegalArgumentException("CPF inválido!");
	    }
	    
	    String cpfFormatado = cliente.getCpf().replaceAll("\\D", "");
	    cliente.setCpf(cpfFormatado); // Atribui o CPF formatado ao objeto cliente
	    
		
		double saldo = dao.calcularSaldo(conta.getId());
		adicionarTarifa(movimentacao, 2.00);
		
		if(!limiteDiarioSaque(movimentacao)) {
			throw new IllegalArgumentException("** Limite diario de 5 mil atingido! **");
		}
		else if(!validarSaldoNegativo(saldo,movimentacao)) {
			throw new IllegalArgumentException("\"Operação inválida!! Saldo negativo.\"");
			
		}else if(detectacaoDeFraude(movimentacao)) {
			System.out.println("");
			return null;
		}
		else {
			return inserir(movimentacao);
		}
		
	}
	
	public Movimentacao depositar(Movimentacao movimentacao, Cliente cliente) {
		if (cliente.getCpf() == null || !ValidacaoCPF.validarCPF(cliente.getCpf())) {
	        throw new IllegalArgumentException("CPF inválido!");
	    }
		if(detectacaoDeFraude(movimentacao)) {
			System.out.println("");
			return null;
		};

		return inserir(movimentacao);
	}
	
	public void depositarContapoupanca(Movimentacao movimentacao, double lucro) {
		Movimentacao rendimento = new Movimentacao();
		rendimento.setValorOperacao(lucro);
		rendimento.setConta(movimentacao.getConta());
		rendimento.setDataTransacao(new Date());
		rendimento.setDescricao("Rendimento dos juros compostos da Conta Poupança");
		rendimento.setTipoTransacao(TransacaoTipo.DEPOSITO);
		System.out.println("Valor do rendimento: R$ "+rendimento.getValorOperacao());
		//return dao.inserir(movimentacao);
	}
	
	public Movimentacao pagamento(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidacaoCPF.validarCPF(cliente.getCpf());
		double saldo = dao.calcularSaldo(conta.getId());
		verificandoSaldoBaixo(saldo);
		adicionarTarifa(movimentacao, 5.00);
		if(detectacaoDeFraude(movimentacao)) {
			System.out.println("");
			return null;
		}else if(!validarSaldoNegativo(saldo,movimentacao)) {
			throw new IllegalArgumentException("\"Operação inválida!! Saldo negativo.\"");
		} 
		return inserir(movimentacao);
	}
			
	
	public Movimentacao pagamentoPIX(Movimentacao movimentacao, Conta conta, Cliente cliente) {
		ValidacaoCPF.validarCPF(cliente.getCpf());
		
		 if (!horarioPix()) {
			 	throw new NullPointerException("** Operação PIX fora do horário permitido (6h - 22h)! **");
		    }
		 
		detectacaoDeFraude(movimentacao);
		double saldo = dao.calcularSaldo(conta.getId());
		adicionarTarifa(movimentacao, 5.00);
		verificandoSaldoBaixo(saldo);
		if(validarPix(movimentacao)) {
			System.out.println("Tarifa extra de 5,00 aplicada pela operação!");
			return inserir(movimentacao);
		}	
			System.out.println("*** Operação inválida por ultrapassar o limite permitido em pix! ***");
			return null;
	}
	
	public double consultaSaldo(Long id) {
		return dao.calcularSaldo(id);
	}
	
		// Validando se o saldo for negativo
		public boolean validarSaldoNegativo(double saldo, Movimentacao movimentacao) {
			if (saldo < movimentacao.getValorOperacao()) {
				return false;
			}
			else{
				return true;
			}
		}
		
		//Validando pix de no máximo 300tão
		public boolean validarPix(Movimentacao movimentacao) {
			if(movimentacao.getValorOperacao() > 300.00) {
			return false;
			}
			return true;
		}
		
		
		//Colocando o limite diário pra 5k
		public boolean limiteDiarioSaque(Movimentacao movimentacao) {
			
			double totalSacadoHoje = consultarTotalSacadoHoje(movimentacao.getConta().getId(), movimentacao.getTipoTransacao());
			double totalAposNovoSaque = totalSacadoHoje + movimentacao.getValorOperacao();
			
			if(totalAposNovoSaque > 5000.00) {
				return false;
			}else {
				return true;
			}
		}
		private double consultarTotalSacadoHoje(Long contaId, TransacaoTipo tipoTransacao) {
		    EntityManager em = emf.createEntityManager();
		    Double total = em.createQuery(
		        "SELECT COALESCE(SUM(m.valorOperacao), 0) FROM Movimentacao m " +
		        "WHERE m.conta.id = :contaId AND m.tipoTransacao = :tipoSaque " +
		        "AND FUNCTION('DATE', m.dataTransacao) = CURRENT_DATE", Double.class)
		        .setParameter("contaId", contaId)
		        .setParameter("tipoSaque", tipoTransacao) 
		        .getSingleResult();
		    em.close();
		    return total;
		}
		
		public void adicionarTarifa(Movimentacao movimentacao, Double tarifa) {
			Double valorTotal = movimentacao.getValorOperacao() + tarifa;
			movimentacao.setValorOperacao(valorTotal); 
	    }
		
		
		public boolean horarioPix() {
			LocalTime now = LocalTime.now();
			if (now.isBefore(LocalTime.of(6, 0)) || now.isAfter(LocalTime.of(22, 0))) {
				return false;
			}
			return true;
		}
		
		//Notificando cliente
		
		public void verificandoSaldoBaixo(double saldo) {
			if (saldo < 100.00) {
				System.out.println("!!ATENÇÃO!!\n Seu saldo está abaixo de 100,00 reais !");
			}
		}
		
		public List <Movimentacao> consultaDoExtrato(Long id, Date inicio, Date fim){
			  List<Movimentacao> movimentacoes = dao.buscarPorData(id, inicio, fim);
			  if (movimentacoes.isEmpty()) {
			        System.out.println("Nenhuma movimentação encontrada no período selecionado.");
			  }else {
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				  System.out.println("** EXTRATO DAS MOVIMENTAÇÕES: ");
				  for(Movimentacao m: movimentacoes) {
					  System.out.println("Data: " + sdf.format(m.getDataTransacao()) +
				                " | Tipo: " + m.getTipoTransacao() +
				                " | Descrição: " + m.getDescricao() +
				                " | Valor: R$ " + String.format("%.2f", m.getValorOperacao())
				            );
				  }
			  }
			  return movimentacoes;
			//return dao.buscarPorData(id , inicio , fim);
			
		}
		
		public Movimentacao debito(Movimentacao movimentacao, Conta conta){
			double saldo = dao.calcularSaldo(conta.getId());
			validarSaldoNegativo(saldo, movimentacao);
			double valorFinal = movimentacao.getValorOperacao();
	        movimentacao.setValorOperacao(valorFinal);
			Movimentacao result = inserir(movimentacao);
	        saldo = dao.calcularSaldo(conta.getId());
			verificandoSaldoBaixo(saldo);
	        return result;
		}
		
		public boolean detectacaoDeFraude(Movimentacao movimentacao) {
	        double calcGastos = dao.calcularGastos(movimentacao.getId());
	        LocalTime horaAtual = LocalTime.now();
	       if (movimentacao.getValorOperacao() > calcGastos * 3) {
	    	   System.out.println("Operação suspeita detectada! Valor muito acima do padrão.");
	            return false;
	            
	        } else if (horaAtual.isBefore(LocalTime.of(6, 0)) || horaAtual.isAfter(LocalTime.of(22, 0))) {
	            System.out.println("Operação em horário incomum.");
	            return false;
	            
	        }  else {
				return true;
			}
	    }

		@Override
		public GenericoDAO<Movimentacao> getDAO() {
			// TODO Auto-generated method stub
			return dao;
		}

		@Override
		public Movimentacao alterar(Movimentacao entidade) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void excluir(Long id) {
			// TODO Auto-generated method stub
			
		}
		
		
}
