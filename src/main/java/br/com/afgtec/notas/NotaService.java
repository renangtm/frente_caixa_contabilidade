package br.com.afgtec.notas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Service;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.pessoa.Usuario;
import br.com.afgtec.usuario.UsuarioService;

public class NotaService implements Service<Nota>{

	private Empresa empresa;
	
	public void setEmpresa(Empresa emp) {
		
		this.empresa = emp;
		
	}
	
	public static void main(String[] args) {
		
		UsuarioService us = new UsuarioService();
		
		Usuario u = us.getUsuario("teste", "teste");
		
		NotaService ps = new NotaService();
		ps.setEmpresa(u.getEmpresa());
		
		System.out.println(ps.getElementos(0, 10, "", "")+"");
		
	}
	
	@Override
	public int getCount(String filtro) {
		
		if(this.empresa == null) {
			
			throw new RuntimeException("Service sem empresa");
			
		}
		
		EntityManager et = ET.nova();
		
		Query qr = et.createQuery("SELECT COUNT(*) FROM NotaEntrada n WHERE n.empresa.id = :empresa AND (n.emitente.razao_social LIKE :nome_c OR n.transportadora.razao_social LIKE :nome_t)");
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome_c", "%"+filtro+"%");
		qr.setParameter("nome_t", "%"+filtro+"%");
		
		
		@SuppressWarnings("unchecked")
		List<Long> lista = (List<Long>)(List<?>)qr.getResultList();
		
		return Integer.parseInt(lista.get(0)+"");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Nota> getElementos(int x1, int x2, String filtro,String ordem) {
		
		if(this.empresa == null) {
			
			throw new RuntimeException("Service sem empresa");
			
		}	
		
		ordem = ordem.replaceAll("\\{\\{et\\}\\}", "n");
		
		EntityManager et = ET.nova();
		
		Query qr = et.createQuery("SELECT n FROM NotaEntrada n WHERE n.empresa.id = :empresa AND (n.emitente.razao_social LIKE :nome_c OR n.transportadora.razao_social LIKE :nome_t)"+(ordem != ""?" ORDER BY "+ordem:""));
		qr.setParameter("empresa", this.empresa.getId());
		qr.setParameter("nome_c", "%"+filtro+"%");
		qr.setParameter("nome_t", "%"+filtro+"%");
		qr.setFirstResult(x1);
		qr.setMaxResults(x2-x1);
		
		return (List<Nota>)(List<?>)qr.getResultList();
		
	}

	@Override
	public Nota getPeloCodigo(String str) {
		
		EntityManager et = ET.nova();
		
		@SuppressWarnings("unchecked")
		List<Nota> notas = (List<Nota>)(List<?>)et.createQuery("SELECT n FROM Nota n WHERE n.id = :codigo")
		.setParameter("codigo", str)
		.getResultList();
		
		if(notas.size() == 0) {
			
			return null;
			
		}
		
		return (Nota)notas.get(0);
		
	}

	
	
	
}


