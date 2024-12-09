package servico;

import dao.ClienteDAO;
import entidade.Cliente;
import util.ValidacaoCPF;

public class ClienteServico {

    ClienteDAO dao = new ClienteDAO();
    

    public Cliente inserir(Cliente cliente) {
		if(!ValidacaoCPF.validarCPF(cliente.getCpf())) {
			throw new IllegalArgumentException("CPF Inválido!");
		}
		return dao.inserir(cliente);
	}
    
    public void excluir(Cliente cliente){
        dao.excluir(cliente.getId());
    }
    
    public boolean validarCliente(Cliente cliente){
        Cliente clienteValido= dao.buscarPorCpf(cliente.getCpf());	
        if (clienteValido == null || !clienteValido.getCpf().equals(cliente.getCpf())) {
            return false;
        }
        return true;
    }
    public Cliente buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}