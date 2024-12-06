package servico;

import dao.ContaDAO;
import entidade.Conta;

public class ContaServico {

    ContaDAO dao = new ContaDAO();

    public Conta inserir(Conta conta) {
    
        return dao.inserir(conta);
    }
    
    public void excluir(Conta conta) {
    	if(dao.buscarPorId(conta.getId())!= null) {
    		dao.excluir(conta.getId());
    	}
    }
    public Conta buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
    
    //limita 3 contas diferentes por cliente
    public boolean adicionarConta(Long id) {
        int totalContas = dao.contarPorConta(id);
        if (totalContas >= 3) {
        	throw new IllegalArgumentException("O cliente já possui o número máximo de 3 contas!");
        }
        return true;
    }
}