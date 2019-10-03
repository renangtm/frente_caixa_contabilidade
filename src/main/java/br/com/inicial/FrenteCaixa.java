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

import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.produto.ProdutoService;
import br.com.afgtec.usuario.Usuario;
import br.com.codigo.CodigoBarra;
import br.com.entidades.Icones;
import br.com.venda.ProdutoVenda;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;

public class FrenteCaixa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario operador;

	private Empresa empresa;
	private JTextField txtPesquisar;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	public FrenteCaixa() {

		super("Frente de Caixa", 0, 0, 100, 100, false);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 323, 593);
		getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Pesquisar:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(10, 11, 88, 14);
		getContentPane().add(lblNewLabel);
		
		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(10, 36, 451, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);
		
		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(359, 67, 88, 14);
		getContentPane().add(lblDescricao);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_1.setBackground(new Color(0, 153, 255));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(359, 92, 525, 45);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(471, 11, 88, 14);
		getContentPane().add(lblCliente);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		textField.setBounds(471, 36, 360, 20);
		getContentPane().add(textField);
		
		JButton btnNewButton = new JButton("...");
		btnNewButton.setBounds(841, 35, 43, 23);
		getContentPane().add(btnNewButton);
		
		JLabel lblValorUnitario = new JLabel("Valor Unitario:");
		lblValorUnitario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblValorUnitario.setBounds(359, 148, 88, 14);
		getContentPane().add(lblValorUnitario);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblQuantidade.setBounds(546, 148, 88, 14);
		getContentPane().add(lblQuantidade);
		
		JLabel lblTotal = new JLabel("Sub Total");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(718, 148, 88, 14);
		getContentPane().add(lblTotal);
		
		textField_1 = new JTextField();
		textField_1.setBounds(359, 173, 146, 55);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(546, 173, 146, 55);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBounds(718, 173, 166, 55);
		getContentPane().add(textField_3);
		
		JLabel lblNewLabel_2 = new JLabel("X");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(511, 173, 25, 55);
		getContentPane().add(lblNewLabel_2);
		
		JLabel label = new JLabel("=");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 24));
		label.setBounds(693, 173, 25, 55);
		getContentPane().add(label);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(359, 239, 525, 307);
		getContentPane().add(scrollPane_1);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setColumns(10);
		textField_4.setBounds(652, 573, 232, 87);
		getContentPane().add(textField_4);
		
		JLabel lblTotal_1 = new JLabel("Total");
		lblTotal_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal_1.setBounds(654, 557, 88, 14);
		getContentPane().add(lblTotal_1);
		
		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFormaDePagamento.setBounds(359, 557, 196, 14);
		getContentPane().add(lblFormaDePagamento);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(359, 573, 275, 20);
		getContentPane().add(comboBox);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(359, 623, 118, 37);
		getContentPane().add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(487, 623, 147, 37);
		getContentPane().add(textField_6);
		
		JLabel lblDinheiro = new JLabel("Dinheiro");
		lblDinheiro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDinheiro.setBounds(359, 604, 118, 14);
		getContentPane().add(lblDinheiro);
		
		JLabel lblTroco = new JLabel("Troco");
		lblTroco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTroco.setBounds(487, 604, 118, 14);
		getContentPane().add(lblTroco);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 671, 874, 2);
		getContentPane().add(separator);
		
		JLabel lblFFinalizar = new JLabel("F4 - Finalizar pedido");
		lblFFinalizar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFFinalizar.setBounds(10, 684, 549, 14);
		getContentPane().add(lblFFinalizar);

		this.setVisible(true);

	}

	public void init(Usuario operador) {

		this.operador = et.merge(operador);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa=et.merge(this.empresa);

		this.setTitle(operador.getPf().getEmpresa().getPj().getNome() + " - Operador: " + operador.getPf().getNome());

		
		
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
