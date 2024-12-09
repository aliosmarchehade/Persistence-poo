package servico;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import dao.ContaDAO;
import entidade.Conta;
import entidade.ContaTipo;
import util.CalculoJuros;
import util.Data;

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
    
    public double calcularContapoupanca(Conta conta) {
		if(conta.getContaTipo() != ContaTipo.CONTA_POUPANCA) {
			throw new Error ("Não foi inserido uma conta poupança");
		}
		String data = Data.formatarAnoMes(new Date(0));
		double saldo = dao.buscarSaldoContaPoupanca(conta.getId(),data);
		if(saldo<=0) {
			throw new Error ("Saldo zerado ou negativo. Não é possível calcular rendimento");
		}
		LocalDate inicio = LocalDate.parse(Data.formatarAnoMesDia((Date) conta.getDataAbertura()));
		int meses = Period.between(inicio, LocalDate.now()).getMonths();
		return CalculoJuros.JurosCompostos(saldo, 0.002, meses);
	}
}