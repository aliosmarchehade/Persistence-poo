package visao;

import java.util.Date;

import controle.ClienteControle;
import controle.ContaControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.ContaTipo;

public class ContaTela {

	public static void main(String[] args) {
		
		ContaControle controle = new ContaControle();
		ClienteControle controleCliente = new ClienteControle();
		Cliente cliente = controleCliente.buscarPorId(19L);
		Conta conta = new Conta();
		
		conta.setDataAbertura(new Date());
		conta.setContaTipo(ContaTipo.CONTA_CORRENTE);
		conta.setCliente(cliente);
		controle.adicionarConta(cliente.getId());
		
		controle.inserir(conta);
		System.out.println("Conta criada com sucesso!");
		
	}

}
