package util;

import java.sql.Date;
import java.text.*; 

public class Data {
	
	public static String formatarAnoMesDia(Date data) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(data);
	}
	public static String formatarAnoMes(Date data) {
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(data);
	}
	
	public static int formatarMes(Date data) {
		DateFormat format = new SimpleDateFormat("MM");
		return (int) (Integer.valueOf(format.format(data)));
	}

	
	public static int formatarHora(Date data) {
		DateFormat format = new SimpleDateFormat("HH");
		return (int) (Integer.valueOf(format.format(data)));
	}
	
	public static Date formatarStringDate(String data) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataFormatada = (Date) format.parse(data);
		return dataFormatada;
	}
}
