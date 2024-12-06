package visao;

import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {

	public static void main(String[] args) {
		Cliente cliente = new Cliente();
		ClienteControle controle = new ClienteControle();
		
		cliente.setCpf("111.111.111-11");
		cliente.setNome("Diego");
		
		controle.inserir(cliente);
	}

}
