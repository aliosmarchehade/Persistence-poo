package visao;

import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {

	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		ClienteControle controle = new ClienteControle();
		
		cliente.setCpf("11995714909");
		cliente.setNome("Diego");
		
		controle.inserir(cliente);
	}

}
