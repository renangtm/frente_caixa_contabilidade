package br.com.quantificacao;

interface TODEG{
	
	public double toGrama(double v,ItemQuantificavel it);
	public double deGrama(double v,ItemQuantificavel it);
	
}

public enum TipoQuantidade {

	UN(0, new TODEG(){

		@Override
		public double toGrama(double v, ItemQuantificavel it) {
			
			return it.getUnidadePeso().para(UnidadePeso.GR, v*it.getPeso());
			
		}

		@Override
		public double deGrama(double v, ItemQuantificavel it) {
			
			return UnidadePeso.GR.para(it.getUnidadePeso(), v)/it.getPeso();
			
		}

	}),
	LT(1, new TODEG(){
		
		@Override
		public double toGrama(double v, ItemQuantificavel it) {
			
			double densidade = 
					it.getUnidadePeso().para(UnidadePeso.GR,it.getPeso())
					/it.getUnidadeVolume().para(UnidadeVolume.LT,it.getVolume());
			
			return v*densidade;
			
		}

		@Override
		public double deGrama(double v, ItemQuantificavel it) {
			
			double densidade =
					it.getUnidadeVolume().para(UnidadeVolume.LT,it.getVolume())
					/it.getUnidadePeso().para(UnidadePeso.GR,it.getPeso());
			
			return v*densidade;
			
		}
		
	}),
	ML(2, new TODEG(){
		
		@Override
		public double toGrama(double v, ItemQuantificavel it) {
			
			double densidade = 
					it.getUnidadePeso().para(UnidadePeso.GR,it.getPeso())
					/it.getUnidadeVolume().para(UnidadeVolume.ML,it.getVolume());
			
			return v*densidade;
			
		}

		@Override
		public double deGrama(double v, ItemQuantificavel it) {
			
			double densidade =
					it.getUnidadeVolume().para(UnidadeVolume.ML,it.getVolume())
					/it.getUnidadePeso().para(UnidadePeso.GR,it.getPeso());
			
			return v*densidade;
			
		}
		
	}),
	KG(3, new TODEG(){
		
		@Override
		public double toGrama(double v, ItemQuantificavel it) {
			
			return UnidadePeso.KG.para(UnidadePeso.GR,v);
			
		}

		@Override
		public double deGrama(double v, ItemQuantificavel it) {
			
			return UnidadePeso.GR.para(UnidadePeso.KG,v);
			
		}
		
	}),
	GR(4, new TODEG(){
		
		@Override
		public double toGrama(double v, ItemQuantificavel it) {
			
			return v;
			
		}

		@Override
		public double deGrama(double v, ItemQuantificavel it) {
			
			return v;
			
		}
		
	});
	
	private int id;
	private TODEG todeg;
	
	private TipoQuantidade(int id,TODEG td) {
		
		this.id = id;
		this.todeg = td;
		
	}
	
	public int getId() {
		
		return this.id;
		
	}
	
	public double para(TipoQuantidade tq,ItemQuantificavel it, double valor){
		
		return tq.todeg.deGrama(this.todeg.toGrama(valor, it), it);
		
	}
	
}
