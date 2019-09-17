package br.com.inicial;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.afgtec.pessoa.Usuario;
import br.com.codigo.CodigoBarra;
import br.com.entidades.Icones;
import br.com.venda.ProdutoVenda;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;

public class FrenteCaixa extends Modulo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario operador;
	
	private JLabel logo;
	
	public JTextField txtCodigo;
	
	private JLabel lblIQtd;
	private JLabel lblQtd;
	
	private JLabel lblX;
	
	private JLabel lblIValor;
	private JLabel lblValor;
	
	private JLabel lblIgual;
	
	private JLabel lblITotal;
	private JLabel lblTotal;
	
	public JLabel lblNome;
	private JLabel lblINome;
	
	private JEditorPane txtLog;
	
	private Venda venda;
	
	public boolean focus = true;
	
	public void novaVenda() {
		
		this.venda = new Venda();
		
		this.venda.setData(Calendar.getInstance());
		this.venda.setOperador(this.operador);
		this.venda.setEmpresa(this.operador.getEmpresa());
		this.venda.setStatus(StatusVenda.EM_EXECUCAO);
		
		this.printVenda();
		
	}
	
	public void bipe(String str) {
		
		
		if(str.isEmpty() && this.venda.getTotal() > 0) {
			
			this.focus = false;
			
			new FinalizarCompra(this.venda,this);
			
			return;
			
		}
		
		this.txtCodigo.setText("");
		
		CodigoBarra codigo = null;
		
		try{
		
			codigo = new CodigoBarra(str,this.operador.getEmpresa().getPadroesCodigo());
		
		}catch(Exception ex){
			
			ex.printStackTrace();
			return;
			
		}
		
		
		int i = 0;
		if((i=this.venda.getProdutos().stream().map(x->x.getProduto().getId()).collect(Collectors.toList()).indexOf(codigo.getProduto().getId()))>=0) {
			
			this.venda.getProdutos().get(i).setQuantidade(this.venda.getProdutos().get(i).getQuantidade()+codigo.getQuantidade());
			
		}else {
		
			ProdutoVenda pv = new ProdutoVenda();
			pv.setProduto(codigo.getProduto());
			pv.setQuantidade(codigo.getQuantidade());
			pv.setValor(codigo.getProduto().getValor());
			pv.setVenda(this.venda);
			
			this.venda.getProdutos().add(pv);
			
		}
		
		this.printVenda();
		
		this.lblQtd.setText(codigo.getQuantidade()+" "+codigo.getProduto().getEstoque().getTipo().name());
		
		NumberFormat cifra = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
		
		this.lblValor.setText(cifra.format(codigo.getProduto().getValor()));
		
		this.lblTotal.setText(cifra.format(codigo.getProduto().getValor()*codigo.getQuantidade()));
		
		this.lblNome.setText(codigo.getProduto().getNome().toUpperCase());
		
	}
	
	public FrenteCaixa() {
		
		super("Frente de Caixa",0,0,100,100,false);
		
		
		this.setVisible(true);
		
	}
	
	public void init(Usuario operador){

		this.setTitle(operador.getEmpresa().getNome()+" - Operador: "+operador.getNome());
		
		this.operador = operador;
		
		this.logo = new JLabel();
		this.logo.setIcon(new ImageIcon(Icones.getLogo()));
		this.add(this.logo);
		this.lr.setDimensoesComponente(this.logo, 80, 90, 17, 7);
		
		this.txtCodigo = new JTextField();
		this.add(this.txtCodigo);
		this.lr.setDimensoesComponente(this.txtCodigo, 3, 3, 84, 6);
		
		this.txtCodigo.setFont(new Font("Arial",Font.BOLD,17));
		
		this.txtCodigo.setBorder(BorderFactory.createCompoundBorder(
				this.txtCodigo.getBorder(), 
		        BorderFactory.createEmptyBorder(5, 10, 5, 5)));
		
		this.txtCodigo.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
				if(focus)
				txtCodigo.requestFocus();
				
			}
			
			
			
		});
		
		this.txtCodigo.requestFocus();
		
		this.txtCodigo.addActionListener(e->bipe(txtCodigo.getText()));
		
		this.lblINome = new JLabel("Produto:");
		this.add(this.lblINome);
		this.lr.setDimensoesComponente(this.lblINome, 3, 12, 70, 5);
		
		this.lblNome = new JLabel();
		this.add(this.lblNome);
		this.lblNome.setFont(new Font("Arial",Font.BOLD,25));
		this.lblNome.setOpaque(true);
		this.lblNome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		this.lblNome.setBackground(Color.white);
		this.lr.setDimensoesComponente(this.lblNome, 3, 16, 70, 7);
		
		this.lblIQtd = new JLabel("Quantidade:");
		this.add(this.lblIQtd);
		this.lr.setDimensoesComponente(this.lblIQtd, 3,25, 15, 5);
		
		this.lblIValor = new JLabel("Valor:");
		this.add(this.lblIValor);
		this.lr.setDimensoesComponente(this.lblIValor, 31, 25, 20, 5);
		
		this.lblITotal = new JLabel("Sub Total:");
		this.add(this.lblITotal);
		this.lr.setDimensoesComponente(this.lblITotal, 61, 25, 35, 5);
		
		this.lblQtd = new JLabel();
		this.lblQtd.setFont(new Font("Arial",Font.BOLD,25));
		this.lblQtd.setOpaque(true);
		this.lblQtd.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		this.lblQtd.setBackground(Color.white);
		this.add(this.lblQtd);
		this.lr.setDimensoesComponente(this.lblQtd, 3, 32, 15, 10);
		
		this.lblX = new JLabel("X");
		this.add(this.lblX);
		this.lblX.setHorizontalAlignment(JLabel.CENTER);
		this.lblX.setFont(new Font("Arial",Font.BOLD,30));
		this.lr.setDimensoesComponente(this.lblX, 18, 32, 12, 10);
		
		this.lblValor = new JLabel();
		this.lblValor.setFont(new Font("Arial",Font.BOLD,25));
		this.lblValor.setOpaque(true);
		this.lblValor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		this.lblValor.setBackground(Color.white);
		this.add(this.lblValor);
		this.lr.setDimensoesComponente(this.lblValor, 31, 32, 20, 10);
		
		this.lblIgual = new JLabel("=");
		this.add(this.lblIgual);
		this.lblIgual.setHorizontalAlignment(JLabel.CENTER);
		this.lblIgual.setFont(new Font("Arial",Font.BOLD,30));
		this.lr.setDimensoesComponente(this.lblIgual, 52, 32, 9, 10);
		
		this.lblTotal = new JLabel();
		this.lblTotal.setFont(new Font("Arial",Font.BOLD,25));
		this.lblTotal.setOpaque(true);
		this.lblTotal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		this.lblTotal.setBackground(Color.white);
		this.add(this.lblTotal);
		this.lr.setDimensoesComponente(this.lblTotal, 61, 32, 35, 10);
		
		
		this.txtLog = new JEditorPane();
		this.txtLog.setContentType("text/html");
		this.txtLog.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
		this.add(this.txtLog);
		
		this.lr.setDimensoesComponente(this.txtLog, 3, 45, 94, 40);
		
		this.novaVenda();
		
		this.printVenda();
		
	}
	
	public void printVenda() {
		
		NumberFormat cifra = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
	
		String html = "<table style='width:100%'>"
				+ "<tr>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:20px;background-color:SteelBlue;border:1px solid;text-align:center'>Codigo</td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:20px;background-color:SteelBlue;border:1px solid;text-align:center'>Nome</td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:20px;background-color:SteelBlue;border:1px solid;text-align:center'>Qtd</td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:20px;background-color:SteelBlue;border:1px solid;text-align:center'>Valor</td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:20px;background-color:SteelBlue;border:1px solid;text-align:center'>Sub Total</td>"
				+ "</tr>";
		
		for(ProdutoVenda pv:this.venda.getProdutos()) {
			
			String linha = "<tr>"
					+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>codigo</td>"
					+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>nome</td>"
					+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>quantidade</td>"
					+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>valor</td>"
					+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>subtotal</td>"
					+ "</tr>";
			
			linha = linha
					.replaceAll("codigo", pv.getProduto().getId()+"")
					.replaceAll("nome", pv.getProduto().getNome().toUpperCase())
					.replaceAll("quantidade", pv.getQuantidade()+""+pv.getProduto().getEstoque().getTipo().name())
					.replaceAll("valor", cifra.format(pv.getValor()).replace('$', 'S'))
					.replaceAll("subtotal", cifra.format(pv.getValor()*pv.getQuantidade()).replace('$', 'S'));
			
			html += linha;
			
		}
		
		html += "<tr>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'></td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'></td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'></td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>Total: </td>"
				+ "<td style='padding:10px;font-weigth:bold;font-size:15px;background-color:SteelBlue;text-align:center'>"+cifra.format(this.venda.getTotal())+"</td>"
				+ "</tr>";
		
		html += "</table>";
		
		this.txtLog.setText(html);
		
	}
	
	public static ImageIcon logo() {

		try {
			return Icones.getDiagrama();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Frente de caixa";

	}
	
}
