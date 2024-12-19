package visao;

import java.util.Date;

import controle.ClienteControle;
import controle.ContaControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.ContaTipo;
import java.time.LocalDate;
import java.time.ZoneId;

public class ContaTela {

	public static void main(String[] args) {
		
		ContaControle controle = new ContaControle();
		ClienteControle controleCliente = new ClienteControle();
		Cliente cliente = controleCliente.buscarPorId(1L);
		Conta conta = new Conta();
		
//		conta.setDataAbertura(new Date());
//		conta.setContaTipo(ContaTipo.CONTA_POUPANCA);
//		conta.setCliente(cliente);
//		controle.adicionarConta(cliente.getId());
//		
//		controle.inserir(conta);
//		System.out.println("Conta criada com sucesso!");
		
		LocalDate dataInicio = LocalDate.of(2024, 12, 1); 
        LocalDate dataFim = LocalDate.of(2024, 12, 19);
		
        Date dateInicio = Date.from(dataInicio.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateFim = Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant());
        
		controle.consultaDoExtrato(1L, dateInicio , dateFim);
		
	}

}
