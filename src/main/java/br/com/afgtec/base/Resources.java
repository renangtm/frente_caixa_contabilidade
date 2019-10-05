package br.com.afgtec.base;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resources {
	
	public static ImageIcon getCfg() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/cfg.png"));
	}
	public static ImageIcon getListaPreco() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/lista_preco.png"));
	}
	
	public static ImageIcon getLotes() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/lotes.png"));
	}
	
	public static ImageIcon getArquivos() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/arquivos.png"));
	}
	
	public static BufferedImage getFundo() throws IOException{
		try{
			return ImageIO.read(Resources.class.getResource("/icones/fundo.png"));
		}catch(Exception e){
			return null;
		}
	}
	
	
	public static ImageIcon getCompraParceiros() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/compra_parceiros.png"));
	}
	
	public static ImageIcon getConfig() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/config.png"));
	}
	
	public static InputStream getIconeSistema() throws IOException{
		return Resources.class.getClassLoader().getResource("icones/logo_icone.ico").openStream();
	}
	
	public static ImageIcon getEmpilhadeira() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/estoque.png"));
	}
	public static ImageIcon getCliente() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/cliente.png"));
	}
	
	public static ImageIcon getCaminhao() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/caminhao.png"));
	}
	
	public static ImageIcon getPedido() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/pedido.png"));
	}
	
	public static ImageIcon getCaixas() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/caixas.png"));
	}
	
	public static ImageIcon getChave() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/chave.png"));
	}
	
	public static ImageIcon getOlho() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/olho.png"));
	}
	
	public static ImageIcon getPalet() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/palet.png"));
	}
	
	public static ImageIcon getConsultarPalet() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/consultar_palet.png"));
	}
	
	public static ImageIcon getArvore() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/arvore.png"));
	}
	
	public static ImageIcon getEngrenagem() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/engrenagem.png"));
	}
	
	public static ImageIcon getSetaEsquerda() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/seta_esquerda.png"));
	}
	
	public static ImageIcon getSetaCima() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/seta_cima.png"));
	}
	
	public static ImageIcon getSetaBaixo() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/seta_baixo.png"));
	}
	
	public static ImageIcon getSetaDireita() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/seta_direita.png"));
	}
	
	public static ImageIcon getPaletMais() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/palet_mais.png"));
	}
	
	public static ImageIcon getRoboMais() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/robo_mais.png"));
	}
	
	public static ImageIcon getPapel() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/papel.png"));
	}

	public static ImageIcon getSair() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/sair.png"));
	}
	
	public static ImageIcon getDiagrama() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/diagrama.png"));
	}
	
	public static ImageIcon getRosaVentos() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/rosa_ventos.png"));
	}
	
	public static ImageIcon getImprimir() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/imprimir.png"));
	}
	
	
	public static BufferedImage getLogo(){
		try{
			return ImageIO.read(Resources.class.getResource("/icones/logo_afgtec.png"));
		}catch(Exception e){
			return null;
		}
	}
	
	public static ImageIcon getMovimentacao() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/movimentacao.png"));
	}
	
	public static ImageIcon getProdutos() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/produtos.png"));
	}
	
	public static ImageIcon getFornecedores() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/fornecedor.png"));
	}
	
	public static ImageIcon getCotacoes() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/cotacoes.png"));
	}
	
	public static BufferedImage getLogo2(){
		try{
			return ImageIO.read(Resources.class.getResource("/icones/logo2.png"));
		}catch(Exception e){
			return null;
		}
	}
	
	public static ImageIcon getMapa() throws IOException{
		return new ImageIcon(Resources.class.getClassLoader().getResource("icones/mapa.png"));
	}

	private Resources(){
		
	}
}
