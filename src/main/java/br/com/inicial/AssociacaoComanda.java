package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;

import br.com.base.ET;
import br.com.base.Resources;
import br.com.codigo_barra.CodigoBarra;
import br.com.empresa.Empresa;
import br.com.produto.Produto;
import br.com.produto.ProdutoService;
import br.com.usuario.Usuario;
import br.com.venda.ProdutoVenda;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import br.com.venda.VendaService;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.Color;

public class AssociacaoComanda extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtCodigo;

	private JTabbedPane tabbedPane;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Associacao Comanda";

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					AssociacaoComanda frame = new AssociacaoComanda();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Venda selecionada;

	private Usuario operador;

	private Empresa empresa;

	private JLabel lblStatus;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = this.operador.getPf().getEmpresa();
		this.empresa = et.merge(this.empresa);

		this.tabbedPane.setEnabledAt(0, false);
		this.tabbedPane.setEnabledAt(1, false);

		new Thread(() -> {

			while (true) {

				this.txtCodigo.requestFocus();

				try {

					Thread.sleep(1000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

		this.txtCodigo.addActionListener(a -> {

			this.lblStatus.setText("");

			if (this.selecionada == null) {

				VendaService vs = new VendaService(et);

				String comanda = this.txtCodigo.getText();

				this.selecionada = vs.getVendaPorComanda(comanda);

				if (this.selecionada == null) {

					Venda v = new Venda();
					v.setEmpresa(this.empresa);
					v.setCliente(null);
					v.setComandaRepresentada(comanda);
					v.setData(Calendar.getInstance());
					v.setOperador(this.operador);
					v.setStatus(StatusVenda.EM_EXECUCAO);

					this.selecionada = v;

				}

				this.tabbedPane.setSelectedIndex(1);

			} else {

				ProdutoService ps = new ProdutoService(et);
				ps.setEmpresa(this.empresa);

				String codigo = this.txtCodigo.getText();

				try {

					CodigoBarra cb = new CodigoBarra(codigo, this.empresa.getPadroesCodigo(), ps);

					Produto produto = cb.getProduto();
				
					ProdutoVenda pv = new ProdutoVenda();
					pv.setVenda(this.selecionada);
					pv.setProduto(produto);
					pv.setQuantidade(cb.getQuantidade());
					pv.setValor(produto.getValor());

					this.selecionada.getProdutos().add(pv);

					VendaService vs = new VendaService(et);

					if (!vs.verificacaoPersistencia(this.selecionada)) {

						this.selecionada.getProdutos().remove(pv);

						this.lblStatus.setText("PRODUTO RESERVADO");
						this.txtCodigo.setText("");
						return;

					}

					vs.merge(this.selecionada);

					et.getTransaction().begin();
					et.getTransaction().commit();

					this.selecionada = null;

					this.lblStatus.setText(cb.getProduto().getNome());

					this.tabbedPane.setSelectedIndex(0);

				} catch (Exception ex) {
					
					this.lblStatus.setText("PRODUTO NAO ENCONTRADO");
					this.txtCodigo.setText("");
					return;

				}

			}

			this.txtCodigo.setText("");

		});

	}

	/**
	 * Create the frame.
	 */

	public AssociacaoComanda() {
		setTitle("Associacao Comanda");
		setResizable(false);
		setBounds(100, 100, 577, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtCodigo = new JTextField();
		txtCodigo.setBackground(SystemColor.activeCaption);
		txtCodigo.setBounds(10, 224, 551, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 41, 551, 172);
		contentPane.add(tabbedPane);

		JPanel pnlCodigoComanda = new JPanel();
		tabbedPane.addTab("Codigo da Comanda", null, pnlCodigoComanda, null);
		pnlCodigoComanda.setLayout(null);

		JLabel lblNewLabel = new JLabel("PASSE O C\u00D3DIGO DA COMANDA\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblNewLabel.setForeground(SystemColor.activeCaptionBorder);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 526, 122);
		pnlCodigoComanda.add(lblNewLabel);

		JPanel pnlCodigoProduto = new JPanel();
		tabbedPane.addTab("Codigo do Produto", null, pnlCodigoProduto, null);
		pnlCodigoProduto.setLayout(null);

		JLabel lblPasseOCodigo = new JLabel("PASSE O C\u00D3DIGO DO PRODUTO");
		lblPasseOCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasseOCodigo.setForeground(SystemColor.textHighlight);
		lblPasseOCodigo.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblPasseOCodigo.setBounds(10, 11, 526, 122);
		pnlCodigoProduto.add(lblPasseOCodigo);

		lblStatus = new JLabel("");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatus.setForeground(Color.BLACK);
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setBounds(329, 11, 232, 45);
		contentPane.add(lblStatus);

	}
}
