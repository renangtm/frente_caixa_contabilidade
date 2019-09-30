package br.com.inicial;

import java.io.IOException;
import java.sql.SQLException;

public class ScriptInicial {

	public static void main(String[] args) throws IOException, SQLException {
		
		new Login();
		
		/*
		EntityManager et = ET.nova();
		
		
		
		Query q = et.createQuery("SELECT p FROM Produto p WHERE p.id>=8");
		
		
		Usuario u = et.find(Usuario.class, 3);
		Empresa empresa = u.getPf().getEmpresa();
		
		PreparedStatement ps = Conexoes.getConexao().prepareStatement("SELECT p.codigo,p.custo,p.imagem,p.estoque,p.ncm,p.nome,p.peso_bruto,p.valor_base,p.quantidade_unidade,p.id_categoria,p.disponivel FROM novo_rtc.produto p WHERE p.id_empresa=2069 AND p.excluido=false");
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
		
			Produto p = new Produto();
			p.setNome(rs.getString("p.nome"));
			p.setApareceLoja(rs.getInt("p.id_categoria")==143);
			p.setCusto(rs.getDouble("p.custo"));
			p.setCodigo_barra(rs.getString("p.codigo"));
			p.setEmpresa(empresa);
			p.setImagem(rs.getString("p.imagem"));
			p.setPeso(rs.getDouble("p.peso_bruto"));
			p.setValor(rs.getDouble("p.valor_base"));
			
			NCM ncm = new NCM();
			ncm.setDescricao("");
			ncm.setNumero(rs.getString("p.ncm"));
			
			p.setNcm(ncm);
			
			try {
				p.getEstoque().setQuantidades(rs.getDouble("p.disponivel"), rs.getDouble("p.estoque"));
				
				p.setVolume(rs.getDouble("p.quantidade_unidade"));
				
				p.setNcm(et.merge(p.getNcm()));
				
				et.merge(p);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
		
		ps.close();
		
		et.getTransaction().begin();
		et.getTransaction().commit();
		*/
	}
	
}
