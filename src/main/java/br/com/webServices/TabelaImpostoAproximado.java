package br.com.webServices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.empresa.Empresa;
import br.com.impostos.icms.OrigemMercadoria;
import br.com.produto.Produto;
import br.com.produto.ProdutoService;
import br.com.usuario.Usuario;
import br.com.utilidades.JSON;

public class TabelaImpostoAproximado {


	private String urlProduto = "https://apidoni.ibpt.org.br/api/v1/produtos?token=:token&cnpj=:cnpj&codigo=:ncm&uf=:estado&ex=0&codigoInterno=:codigoInterno&descricao=:descricao&unidadeMedida=:unidade&valor=:valor&gtin=1234";
	
	public static void main(String[] args) {
		
		EntityManager et = ET.nova();
		
		Usuario usu  = et.find(Usuario.class, 1);
		
		Empresa empresa = usu.getPf().getEmpresa();
		
		ProdutoService ps = new ProdutoService(et);
		ps.setEmpresa(empresa);
		
		Produto prod = ps.getPeloCodigo("7896005803721");
		
		System.out.println(new TabelaImpostoAproximado().getImposto(prod, 60)+"======");
		
	}
	
	public double getImposto(Produto produto, double valor) {
		
		String url = urlProduto
				.replaceAll(":token", produto.getEmpresa().getTokenAPIImpostosAproximados())
				.replaceAll(":ncm", produto.getNcm().getNumero())
				.replaceAll(":cnpj", produto.getEmpresa().getPj().getCnpj().replaceAll("\\.", "").replaceAll("-", "").replaceAll("\\/", ""))
				.replaceAll(":estado", produto.getEmpresa().getPj().getEndereco().getCidade().getEstado().getSigla())
				.replaceAll(":descricao", produto.getNome().replaceAll(" ", "%20"))
				.replaceAll(":unidade", produto.getEstoque().getTipo().toString())
				.replaceAll(":valor",((int)valor)+"")
				.replaceAll(":codigoInterno", produto.getId()+"");
				
	
		try {
			
			HttpsURLConnection con = (HttpsURLConnection)new URL(url).openConnection();
			con.setRequestMethod("GET");
			
			StringBuilder sb = new StringBuilder();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			
			String linha = "";
			
			while((linha=br.readLine()) != null)
				sb.append(linha);
			
			
			JSON json = new JSON(sb.toString());

			double valorImposto = 0;
			
			if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.ESTRANGEIRA_IMPORTACAO_DIRETA) {
				
				valorImposto = json.getDouble("ValorTributoImportado");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.ESTRANGEIRA_IMPORTACAO_DIRETA_SEM_SIMILAR) {
				
				valorImposto = json.getDouble("ValorTributoImportado");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.ESTRANGEIRA_MERCADO_INTERNO) {
				
				valorImposto = json.getDouble("ValorTributoImportado");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.ESTRANGEIRA_MERCADO_INTERNO_SEM_SIMILAR) {
				
				valorImposto = json.getDouble("ValorTributoImportado");
				
			}
			
			if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.NACIONAL) {
				
				valorImposto = json.getDouble("ValorTributoNacional");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.NACIONAL_CONFORMIDADE_PROCESSOS_PRODUTIVOS) {
				
				valorImposto = json.getDouble("ValorTributoNacional");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.NACIONAL_CONTEUDO_IMPORTACAO_INFERIOR_40) {
				
				valorImposto = json.getDouble("ValorTributoNacional");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.NACIONAL_CONTEUDO_IMPORTACAO_SUPERIOR_40) {
				
				valorImposto = json.getDouble("ValorTributoNacional");
				
			}else if(produto.getCategoria().getIcms().getOrigem() == OrigemMercadoria.NACIONAL_IMPORTACAO_SUPERIOR_70) {
				
				valorImposto = json.getDouble("ValorTributoNacional");
				
			}
			
			return valorImposto;
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
		}
		
		return 0;
		
	}
	
}
