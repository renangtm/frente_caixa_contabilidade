package br.com.impressao;

import java.util.Calendar;

public enum AgrupadoresData {

	DIA(new AgrupadorData() {

		@Override
		public boolean agrupa(Calendar c1, Calendar c2) {

			return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
					&& c1.get(Calendar.DATE) == c2.get(Calendar.DATE);

		}

		@Override
		public String dateFormat() {
			// TODO Auto-generated method stub
			return "dd/MM/yyyy";
		}
		
		

	}), MES(new AgrupadorData() {

		@Override
		public boolean agrupa(Calendar c1, Calendar c2) {

			return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH);

		}

		@Override
		public String dateFormat() {
			// TODO Auto-generated method stub
			return "MM/yyyy";
		}
		
	}), ANO(new AgrupadorData() {

		@Override
		public boolean agrupa(Calendar c1, Calendar c2) {
			// TODO Auto-generated method stub
			return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR);
		}
		
		@Override
		public String dateFormat() {
			// TODO Auto-generated method stub
			return "yyyy";
		}

	});

	private AgrupadorData agrupador;

	private AgrupadoresData(AgrupadorData agp) {

		this.agrupador = agp;

	}

	public AgrupadorData getAgrupador() {
		return agrupador;
	}

}
