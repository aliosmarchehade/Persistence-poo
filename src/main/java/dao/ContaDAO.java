package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidade.Conta;
import entidade.Movimentacao;

public class ContaDAO extends GenericoDAO<Conta> {
	
	public ContaDAO() {
        super(Conta.class);
    }
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");
	
	
	
	
	 public int contarPorConta(Long id) {	
	        EntityManager em = emf.createEntityManager();
	        Long count = em.createQuery(
	            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
	            .setParameter("id_cliente", id)
	            .getSingleResult();
	        em.close();
	        return count.intValue();
	    }
	 
	 public Double calculoSaldo(Long id) {
		    EntityManager em = emf.createEntityManager();
		    Double calculo = em.createQuery(
		    	    "SELECT COALESCE(SUM(CASE m.tipoTransacao " +
		    	    "WHEN 'DEPOSITO' THEN m.valorOperacao " +
		    	    "WHEN 'SAQUE' THEN -m.valorOperacao " +
		    	    "WHEN 'PIX' THEN -m.valorOperacao " +
		    	    "ELSE 0 END), 0.0) " +
		    	    "FROM Movimentacao m WHERE m.conta.id = :id_conta", Double.class)
		    	    .setParameter("id_conta", id)
		    	    .getSingleResult();
		    	em.close();
		    
		    return calculo;
		}
	 
	 public int operacoesPorDia(String cpf) {
			EntityManager em = emf.createEntityManager();
			Query query = em.createQuery("from Movimentacao m where m.cpfCorrentista = :cpf and DATE(m.dataTransacao) = CURRENT_DATE");
			query.setParameter("cpf", cpf);
			Long count = (Long) query.getSingleResult();
			em.close();
	    	return count.intValue();
			//SELECT COUNT(m) FROM Movimentacao m WHERE cpfCorrentista = :cpf AND DATE(dataTransacao) = CURRENT_DATE
		}
	 
}