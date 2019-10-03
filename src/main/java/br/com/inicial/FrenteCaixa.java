package br.com.inicial;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.afgtec.base.CFG;
import br.com.afgtec.base.ET;
import br.com.afgtec.notas.Nota;
import br.com.afgtec.notas.NotaFactory;
import br.com.afgtec.notas.NotaService;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.pessoa.PessoaService;
import br.com.afgtec.pessoa.RepresentadorPessoa;
import br.com.afgtec.produto.Produto;
import br.com.afgtec.produto.ProdutoService;
import br.com.afgtec.unidades.TipoQuantidade;
import br.com.afgtec.usuario.Usuario;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.agrofauna.utilidades.ListModelGenerica;
import br.com.codigo.CodigoBarra;
import br.com.entidades.FormaPagamento;
import br.com.entidades.FormaPagamentoVendaService;
import br.com.entidades.Icones;
import br.com.venda.ProdutoVenda;
import br.com.venda.StatusVenda;
import br.com.venda.Venda;
import br.com.venda.VendaService;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import afgtec.emissao.ValidadorDocumento;
import afgtec.geradoresCupom.GeradorCupomSATModelo1;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JSlider;

public class FrenteCaixa extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario operador;

	private Empresa empresa;
	private JTextField txtCliente;
	private JTextField txtValorUnitario;
	private JTextField txtQuantidade;
	private JTextField txtSubTotal;
	private JTextField txtTotal;
	private JTextField txtDinheiro;
	private JTextField txtTroco;
	private JTextField txtBipe;
	private JTextField txtPesquisa;
	private JTable tblProdutos;
	private JTable tblVenda;

	private JTabbedPane tbpBP;

	private JComboBox<FormaPagamento> cboFormaPagto;

	private JLabel lblPg;

	private JSlider slider;

	private GerenciadorLista<Produto> grProdutos;

	private ListModelGenerica<ProdutoVenda> lstProdutoVenda;

	private ProdutoVenda produtoSelecionado;

	private Venda venda;

	private JButton btCliente;
	
	private FormaPagamento formaPagamento;

	private JLabel txtDescricaoProduto;

	protected void setDestinatario(Pessoa pessoa) {

		this.venda.setCliente(pessoa);
		this.mostrarVenda();

	}

	protected void finalizarVenda() {
		
		this.venda.setStatus(StatusVenda.FECHADA);
		this.venda.setData(Calendar.getInstance());
		
		EntityManager et = ET.nova();
		
		VendaService vs = new VendaService(et);
		
		try {
			
			NotaFactory nf = new NotaFactory();
			
			nf.setFp(this.formaPagamento);
			nf.setParcelas(1);
			nf.setPrazoPagamento(0);
			nf.setTransportadora(null);
			
			if(this.formaPagamento.getFormaPagamento().equals(br.com.afgtec.notas.FormaPagamento.DINHEIRO)){
			
				nf.setValorMeioPagamento(Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", ".")));
				
			}
			
			nf.setVenda(this.venda);
			
			List<Nota> notas = nf.getNotas();
			
			this.venda = vs.persistirVenda(this.venda);
			
			this.venda.setNotas(notas);
			
			NotaService ns = new NotaService(et);
			ns.setEmpresa(this.empresa);
			
			notas.forEach(t -> {
				
				try {
					
					ns.mergeNota(t);
					
				} catch (Exception e) {
					
					ns.reverter();
					
					e.printStackTrace();
					throw new RuntimeException(e);
					
				}
				
			});
			
			ValidadorDocumento vd = new ValidadorDocumento(
					ns,
					CFG.moduloSat,
					new GeradorCupomSATModelo1());
			
			
			
			notas.forEach(n->{
				
				try{
				
					vd.validarFiscalmente(n);
				
				}catch(Exception ex){
					
					ns.reverter();
					vs.reverterPersistencia();
					
					ex.printStackTrace();
					
					throw new RuntimeException(ex);
					
				}
				
			});
			
			et.getTransaction().begin();
			et.getTransaction().commit();
			
		} catch (Exception e) {
			
			et.clear();
			
			e.printStackTrace();
			
			erro("Ocorreu um problema");
			
			return;
			
		}
		
		
	}

	protected void setFormaPagamento(FormaPagamento fp) {
		
		this.formaPagamento = fp;
		
	}

	protected void novaVenda() {

		if (this.venda != null) {

			et.detach(this.venda);

		}
		
		this.venda = et.merge(new Venda());

		this.venda.setOperador(this.operador);

		this.venda.setEmpresa(this.empresa);
		
		this.formaPagamento = this.cboFormaPagto.getItemAt(0);

		this.lstProdutoVenda = new ListModelGenerica<ProdutoVenda>(this.venda.getProdutos(),ProdutoVenda.class);
		this.lstProdutoVenda.getFiltrosObjeto().add(f->{
			
			ProdutoVenda p = (ProdutoVenda)f;
			
			return p.getQuantidade()>0;
			
		});
		
		this.produtoSelecionado = null;
		
		this.tblVenda.setModel(this.lstProdutoVenda);
		
		this.mostrarVenda();

	}

	protected void addProduto(Produto produto, TipoQuantidade tipo, double quantidade) {

		ProdutoVenda pv = new ProdutoVenda();
		
		pv.setProduto(produto);
		
		pv.setVenda(venda);
		
		pv.setQuantidade(tipo.para(pv.getTipoQuantidade(), produto, quantidade));
		
		pv.setValor(pv.getTipoQuantidade().para(produto.getEstoque().getTipo(), produto, produto.getValor()));
		
		this.venda.getProdutos().add(pv);
		
		this.produtoSelecionado = pv;
		
		this.txtQuantidade.requestFocus();

	}

	protected void addProduto(Produto produto) {

		this.addProduto(produto, produto.getEstoque().getTipo(),
				TipoQuantidade.UN.para(produto.getEstoque().getTipo(), produto, 1));

	}

	protected void removerProduto(ProdutoVenda pv) {

		pv.setQuantidade(0);
		
		this.mostrarVenda();
		
	}

	protected void mostrarVenda() {
		
		this.txtCliente.setText("");
		if(this.venda.getCliente() != null){
			this.txtCliente.setText(this.venda.getCliente().getNome());
		}
		
		this.txtDescricaoProduto.setText("");
		this.txtQuantidade.setText("0 UN");
		this.txtValorUnitario.setText("0,00");
		
		if(this.produtoSelecionado != null){
			
			this.txtDescricaoProduto.setText(this.produtoSelecionado.getProduto().getNome());
			this.txtQuantidade.setText((this.produtoSelecionado.getQuantidade()+"").replaceAll("\\.", ",")+" "+this.produtoSelecionado.getTipoQuantidade().toString());
			this.txtValorUnitario.setText((this.produtoSelecionado.getValor()+"").replaceAll("\\.", ","));
			
			this.txtSubTotal.setText(((this.produtoSelecionado.getValor()*this.produtoSelecionado.getQuantidade())+"").replaceAll("\\.", ","));
			
		}
		
		this.txtTotal.setText((this.venda.getTotal()+"").replaceAll("\\.", ","));
		
		if(this.formaPagamento.getFormaPagamento().equals(br.com.afgtec.notas.FormaPagamento.DINHEIRO)){
			try{
				this.txtTroco.setText((Math.max(0,Double.parseDouble(this.txtDinheiro.getText().replaceAll(",", "."))-this.venda.getTotal())+"").replaceAll("\\.", ","));
			}catch(Exception ex){
				
			}
		}
		
		this.lstProdutoVenda.setLista(this.venda.getProdutos());
		this.lstProdutoVenda.atualizaListaBaseConformeFiltros();
		
	}

	public void init(Usuario operador) {

		this.operador = et.merge(operador);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa = et.merge(this.empresa);

		this.setTitle(operador.getPf().getEmpresa().getPj().getNome() + " - Operador: " + operador.getPf().getNome());

		ProdutoService ps = new ProdutoService(et);
		ps.setEmpresa(this.empresa);

		this.grProdutos = new GerenciadorLista<Produto>(Produto.class, this.tblProdutos, ps, 30, null, null, null);
		this.grProdutos.setLblPagina(this.lblPg);
		this.grProdutos.setFiltro(this.txtPesquisa);
		this.grProdutos.atualizar();

		this.txtBipe.addActionListener(e -> {

			try {

				CodigoBarra cb = new CodigoBarra(this.txtBipe.getText(), this.empresa.getPadroesCodigo(), ps);

				addProduto(cb.getProduto(), cb.getProduto().getEstoque().getTipo(), cb.getQuantidade());

			} catch (Exception ex) {

			}

			this.txtBipe.setText("");

		});

		this.txtQuantidade.addActionListener(x -> {

			if (this.produtoSelecionado != null) {

				double quantidade = Double.parseDouble(this.txtQuantidade.getText().replaceAll(",", ".").split(" ")[0]);
				this.produtoSelecionado.setQuantidade(quantidade);

				this.mostrarVenda();

				this.txtPesquisa.requestFocus();

			}

		});

		this.txtValorUnitario.addActionListener(x -> {

			if (this.produtoSelecionado != null) {

				double valor = Double.parseDouble(this.txtValorUnitario.getText().replaceAll(",", ".").split(" ")[0]);
				this.produtoSelecionado.setValor(valor);

				this.mostrarVenda();

			}

		});

		this.tblVenda.getSelectionModel().addListSelectionListener(x -> {

			if (!x.getValueIsAdjusting()) {

				if (this.tblVenda.getSelectedRow() >= 0) {

					this.produtoSelecionado = this.lstProdutoVenda.getListaBase().get(this.tblVenda.getSelectedRow());
					this.mostrarVenda();

				}

			}

		});

		FormaPagamentoVendaService fps = new FormaPagamentoVendaService();

		List<FormaPagamento> fp = fps.getFormasPagamento();

		this.cboFormaPagto
				.setModel(new DefaultComboBoxModel<FormaPagamento>(fp.toArray(new FormaPagamento[fp.size()])));

		this.cboFormaPagto.addActionListener(x -> {

			FormaPagamento f = (FormaPagamento) this.cboFormaPagto.getSelectedItem();

			if (f.getFormaPagamento().equals(br.com.afgtec.notas.FormaPagamento.DINHEIRO)) {

				this.txtDinheiro.setEnabled(false);
				this.txtTroco.setEnabled(false);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");
				this.txtDinheiro.setEnabled(false);
				this.txtTroco.setEnabled(false);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");

			} else {

				this.txtDinheiro.setEnabled(true);
				this.txtTroco.setEnabled(true);

				this.txtDinheiro.setText("0,00");
				this.txtTroco.setText("0,00");

			}

			this.setFormaPagamento(f);

		});

		this.tblProdutos.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					if (tblProdutos.getSelectedRow() >= 0) {

						Produto produto = grProdutos.getModel().getListaBase().get(tblProdutos.getSelectedRow());
						addProduto(produto);

					}

				}

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});

		this.txtPesquisa.addActionListener(e -> {

			List<Produto> produtos = this.grProdutos.getModel().getListaBase();

			if (produtos.size() > 0) {

				addProduto(produtos.get(0));

			}

		});

		new Thread(() -> {

			while (true) {

				if (this.tbpBP.getSelectedIndex() == 0) {

					this.txtBipe.requestFocus();

				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_F1) {

					tbpBP.setSelectedIndex(0);
					txtPesquisa.setText("");

				} else if (e.getKeyCode() == KeyEvent.VK_F2) {

					txtBipe.setText("");
					tbpBP.setSelectedIndex(1);

				} else if (e.getKeyCode() == KeyEvent.VK_F3) {

					novaVenda();

				} else if (e.getKeyCode() == KeyEvent.VK_F4) {

					finalizarVenda();

				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

		});

		this.btCliente.addActionListener(x -> {

			PessoaService pes = new PessoaService(et);
			pes.setEmpresa(this.empresa);

			Seletor<Pessoa> s = new Seletor<Pessoa>(Pessoa.class, pes, RepresentadorPessoa.class, this::setDestinatario,
					this);

			s.setVisible(true);

		});

		this.novaVenda();

	}

	public FrenteCaixa() {
		setResizable(false);

		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		this.setBounds(0, 0, 908, 700);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 323, 532);
		getContentPane().add(scrollPane);

		tblProdutos = new JTable();
		scrollPane.setViewportView(tblProdutos);

		JLabel lblDescricao = new JLabel("Descricao:");
		lblDescricao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescricao.setBounds(359, 67, 88, 14);
		getContentPane().add(lblDescricao);

		txtDescricaoProduto = new JLabel("");
		txtDescricaoProduto.setFont(new Font("Tahoma", Font.BOLD, 24));
		txtDescricaoProduto.setBackground(new Color(0, 153, 255));
		txtDescricaoProduto.setForeground(Color.WHITE);
		txtDescricaoProduto.setOpaque(true);
		txtDescricaoProduto.setHorizontalAlignment(SwingConstants.CENTER);
		txtDescricaoProduto.setBounds(359, 92, 525, 45);
		getContentPane().add(txtDescricaoProduto);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCliente.setBounds(471, 11, 88, 14);
		getContentPane().add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEnabled(false);
		txtCliente.setColumns(10);
		txtCliente.setBounds(471, 36, 360, 20);
		getContentPane().add(txtCliente);

		btCliente = new JButton("...");
		btCliente.setBounds(841, 35, 43, 23);
		getContentPane().add(btCliente);

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

		txtValorUnitario = new JTextField();
		txtValorUnitario.setBounds(359, 173, 146, 55);
		getContentPane().add(txtValorUnitario);
		txtValorUnitario.setColumns(10);

		txtQuantidade = new JTextField();
		txtQuantidade.setColumns(10);
		txtQuantidade.setBounds(546, 173, 146, 55);
		getContentPane().add(txtQuantidade);

		txtSubTotal = new JTextField();
		txtSubTotal.setEnabled(false);
		txtSubTotal.setColumns(10);
		txtSubTotal.setBounds(718, 173, 166, 55);
		getContentPane().add(txtSubTotal);

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
		scrollPane_1.setBounds(359, 239, 525, 252);
		getContentPane().add(scrollPane_1);

		tblVenda = new JTable();
		scrollPane_1.setViewportView(tblVenda);

		txtTotal = new JTextField();
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 33));
		txtTotal.setEditable(false);
		txtTotal.setForeground(new Color(124, 252, 0));
		txtTotal.setHorizontalAlignment(SwingConstants.CENTER);
		txtTotal.setColumns(10);
		txtTotal.setBounds(652, 518, 232, 87);
		getContentPane().add(txtTotal);

		JLabel lblTotal_1 = new JLabel("Total");
		lblTotal_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal_1.setBounds(654, 502, 88, 14);
		getContentPane().add(lblTotal_1);

		JLabel lblFormaDePagamento = new JLabel("Forma de Pagamento");
		lblFormaDePagamento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFormaDePagamento.setBounds(359, 502, 196, 14);
		getContentPane().add(lblFormaDePagamento);

		cboFormaPagto = new JComboBox<FormaPagamento>();
		cboFormaPagto.setBounds(359, 518, 275, 20);
		getContentPane().add(cboFormaPagto);

		txtDinheiro = new JTextField();
		txtDinheiro.setForeground(new Color(0, 0, 255));
		txtDinheiro.setFont(new Font("Tahoma", Font.BOLD, 19));
		txtDinheiro.setHorizontalAlignment(SwingConstants.CENTER);
		txtDinheiro.setColumns(10);
		txtDinheiro.setBounds(359, 568, 118, 37);
		getContentPane().add(txtDinheiro);

		txtTroco = new JTextField();
		txtTroco.setForeground(new Color(255, 0, 0));
		txtTroco.setFont(new Font("Tahoma", Font.BOLD, 19));
		txtTroco.setHorizontalAlignment(SwingConstants.CENTER);
		txtTroco.setColumns(10);
		txtTroco.setBounds(487, 568, 147, 37);
		getContentPane().add(txtTroco);

		JLabel lblDinheiro = new JLabel("Dinheiro");
		lblDinheiro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDinheiro.setBounds(359, 549, 118, 14);
		getContentPane().add(lblDinheiro);

		JLabel lblTroco = new JLabel("Troco");
		lblTroco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTroco.setBounds(487, 549, 118, 14);
		getContentPane().add(lblTroco);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 635, 874, 2);
		getContentPane().add(separator);

		JLabel lblFFinalizar = new JLabel("F4 - Finalizar pedido");
		lblFFinalizar.setForeground(new Color(30, 144, 255));
		lblFFinalizar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFFinalizar.setBounds(10, 648, 118, 14);
		getContentPane().add(lblFFinalizar);

		JLabel lblFnovoPedido = new JLabel("F3 -Nova Venda");
		lblFnovoPedido.setForeground(new Color(255, 165, 0));
		lblFnovoPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFnovoPedido.setBounds(159, 648, 118, 14);
		getContentPane().add(lblFnovoPedido);

		tbpBP = new JTabbedPane(JTabbedPane.LEFT);
		tbpBP.setBounds(10, 11, 451, 47);
		getContentPane().add(tbpBP);

		JPanel panel = new JPanel();
		tbpBP.addTab("Bipe (F1)", null, panel, null);
		panel.setLayout(null);

		txtBipe = new JTextField();
		txtBipe.setBackground(new Color(173, 216, 230));
		txtBipe.setBounds(10, 11, 345, 20);
		panel.add(txtBipe);
		txtBipe.setColumns(10);

		JPanel panel_1 = new JPanel();
		tbpBP.addTab("Pesquisa (F2)", null, panel_1, null);
		panel_1.setLayout(null);

		txtPesquisa = new JTextField();
		txtPesquisa.setColumns(10);
		txtPesquisa.setBounds(10, 11, 345, 20);
		panel_1.add(txtPesquisa);

		lblPg = new JLabel("New label");
		lblPg.setBounds(287, 610, 46, 14);
		getContentPane().add(lblPg);

		slider = new JSlider();
		slider.setBounds(10, 610, 267, 23);
		getContentPane().add(slider);

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
