package br.com.inicial;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DefaultFormatterFactory;

import br.com.afgtec.arquivos.ActionPosUpload;
import br.com.afgtec.arquivos.ArquivoUtils;
import br.com.afgtec.arquivos.ImageLoader;
import br.com.afgtec.arquivos.ImageLoaderListener;
import br.com.afgtec.arquivos.UploaderArquivo;
import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.notas.NCM;
import br.com.afgtec.notas.NCMService;
import br.com.afgtec.pessoa.Empresa;
import br.com.afgtec.produto.Categoria;
import br.com.afgtec.produto.Produto;
import br.com.afgtec.produto.ProdutoService;
import br.com.afgtec.produto.RepresentadorProdutoCompleto;
import br.com.afgtec.unidades.TipoQuantidade;
import br.com.afgtec.unidades.UnidadePeso;
import br.com.afgtec.unidades.UnidadeVolume;
import br.com.afgtec.usuario.Usuario;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.agrofauna.utilidades.ProvedorDeEventos;
import br.com.entidades.Icones;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.persistence.EntityManager;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

public class Produtos extends Modulo {

	private static final long serialVersionUID = 1L;

	public Usuario operador;

	private GerenciadorLista<Produto> grProdutos;

	private Produto produto;

	private void setProduto(Produto produto) {
		
		if(this.produto != null){
			
			try{
				
				this.et.refresh(this.produto);
				
			}catch(Exception ex){
				
				
			}
			
		}

		this.produto = produto;
		
		if(this.produto.getCategoria() == null) {
			
			this.produto.setCategoria(new Categoria());
			
		}

		this.btNovoProduto.setEnabled(produto.getId() > 0);

		this.chkApareceLoja.setSelected(produto.isApareceLoja());
		
		this.txtPorcentagemLoja.setValue(produto.getPorcentagemLoja());
		
		this.txtNome.setText(produto.getNome());
		this.txtCodigoBarra.setText(produto.getCodigo_barra());
		this.txtValor.setValue(produto.getValor());
		this.txtCusto.setValue(produto.getCusto());
		this.txtPeso.setValue(produto.getPeso());
		this.txtVolume.setValue(produto.getVolume());

		if (produto.getNcm() != null)
			this.txtNcm.setText(produto.getNcm().getNumero());
		else
			this.txtNcm.setText("");

		lblImagem.setIcon(null);

		new Thread(new ImageLoader(produto.getImagem(), new ImageLoaderListener() {

			@Override
			public void onLoad(ImageIcon imagem) {

				lblImagem.setIcon(imagem);

			}

			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}

		}, this.lblImagem.getWidth(), this.lblImagem.getHeight())).start();

		this.txtEstoque.setValue(produto.getEstoque().getQuantidade());
		this.txtDisp.setValue(produto.getEstoque().getDisponivel());


		ComboBoxModel<UnidadePeso> cboU = new DefaultComboBoxModel<UnidadePeso>(UnidadePeso.values());
		this.cboUnidadePeso.setModel(cboU);
		this.cboUnidadePeso.setSelectedItem(produto.getUnidade_peso());

		ComboBoxModel<UnidadeVolume> cboV = new DefaultComboBoxModel<UnidadeVolume>(UnidadeVolume.values());
		this.cboUnidadeVolume.setModel(cboV);
		this.cboUnidadeVolume.setSelectedItem(produto.getUnidade_volume());

		ComboBoxModel<TipoQuantidade> cboT = new DefaultComboBoxModel<TipoQuantidade>(TipoQuantidade.values());
		this.cboUnidadeEstoque.setModel(cboT);
		this.cboUnidadeEstoque.setSelectedItem(produto.getEstoque().getTipo());

		this.txtNome.requestFocus();

	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		EntityManager et = ET.nova();

		Usuario u = et.find(Usuario.class, 1);

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Produtos frame = new Produtos();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Empresa empresa;
	
	public void init(Usuario operador) {

		this.setTitle(operador.getPf().getEmpresa().getPj().getNome() + " - Operador: " + operador.getPf().getNome()
				+ " - Produtos");

		this.operador = et.merge(operador);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(operador);
		this.empresa = et.merge(empresa);
		
		//============

		ProdutoService ps = new ProdutoService(et);
		ps.setEmpresa(operador.getPf().getEmpresa());

		this.grProdutos = new GerenciadorLista<Produto>(Produto.class, this.tblProdutos, ps,
				new ProvedorDeEventos<Produto>() {

					@Override
					public void atualizar(List<Produto> lista, Produto elemento) throws Exception {

						et.getTransaction().begin();
						et.getTransaction().commit();

					}

					@Override
					public void deletar(List<Produto> lista, Produto elemento) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void cadastrar(List<Produto> lista, Produto elemento) throws Exception {
						// TODO Auto-generated method stub

					}

				}, new Produto(), RepresentadorProdutoCompleto.class);

		this.grProdutos.setLblSlider(this.sliderPagina);
		this.grProdutos.setLblPagina(this.lblNumeroPagina);

		this.grProdutos.atualizar();

		this.btnBtncm.addActionListener(a -> {

			if (produto == null)
				return;

			Seletor<NCM> sel = new Seletor<NCM>(NCM.class, new NCMService(et), null, ncm -> {

				produto.setNcm(ncm);
				txtNcm.setText(ncm.getNumero());

			},this);
			sel.setVisible(true);

		});

		this.tblProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting() && tblProdutos.getSelectedRow() >= 0) {

					setProduto(grProdutos.getModel().getListaBase().get(tblProdutos.getSelectedRow()));

				}

			}

		});

		this.cboUnidadeEstoque.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (produto != null) {
					produto.getEstoque().setTipo((TipoQuantidade) cboUnidadeEstoque.getSelectedItem());
				}
			}
		});

		this.cboUnidadePeso.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (produto != null) {
					produto.setUnidade_peso((UnidadePeso) cboUnidadePeso.getSelectedItem());
				}
			}
		});

		this.cboUnidadeVolume.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (produto != null) {
					produto.setUnidade_volume((UnidadeVolume) cboUnidadeVolume.getSelectedItem());
				}
			}
		});

		final Produtos este = this;

		this.lblImagem.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				if (produto == null) {

					alerta("Selecione um produto primeiro para colocar a imagem");
					return;

				}

				ArrayList<String> extensoes = new ArrayList<String>();
				extensoes.add("png");
				extensoes.add("jpg");
				extensoes.add("jpeg");

				JFileChooser jfc = new JFileChooser();
				jfc.showOpenDialog(este);
				File arquivo = jfc.getSelectedFile();

				if (arquivo == null)
					return;

				String extensao = ArquivoUtils.getExtensao(arquivo);

				if (!extensoes.contains(extensao)) {

					erro("So sao aceitos os arquivos do tipo " + extensoes);

					return;

				}

				UploaderArquivo.upload(arquivo, new ActionPosUpload() {

					@Override
					public void fim(String endereco) {

						produto.setImagem(endereco);
						setProduto(produto);

					}

				});

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

		this.btNovoProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Produto p = new Produto();
				p.setEmpresa(empresa);
				setProduto(p);

			}

		});

		this.btNovoProduto.doClick();

		this.btDadosContabeis.addActionListener(x->{
			
			
			Categorias c = new Categorias(this.produto.getCategoria(),this.et);
			c.init(this.operador);
			c.setVisible(true);
			c.centralizar();
			
		});
		
		this.btConfirmar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (produto.getNcm() == null) {
					erro("Selecione o NCM");
					return;
				}

				if (!validarFormulario())
					return;

				try {

					produto.getEstoque().setQuantidades(((Number) txtDisp.getValue()).doubleValue(),
							((Number) txtEstoque.getValue()).doubleValue());

				} catch (Exception e1) {

					erro("Valores de estoque incorretos");

					txtEstoque.requestFocus();

					return;

				}

				produto.setNome(txtNome.getText());
				produto.setCodigo_barra(txtCodigoBarra.getText());
				produto.setValor(((Number) txtValor.getValue()).doubleValue());
				produto.setCusto(((Number) txtCusto.getValue()).doubleValue());
				produto.setPeso(((Number) txtPeso.getValue()).doubleValue());
				produto.setVolume(((Number) txtVolume.getValue()).doubleValue());
				produto.setApareceLoja(chkApareceLoja.isSelected());
				produto.setPorcentagemLoja(((Number) txtPorcentagemLoja.getValue()).doubleValue());
				
				if(produto.getId() == 0) {
					
					et.persist(produto);
					
				}else {

					et.merge(produto);
					
				}
				
				et.getTransaction().begin();
				et.getTransaction().commit();

				grProdutos.atualizar();

				Produto p = new Produto();
				p.setEmpresa(empresa);
				setProduto(p);

				info("Operacao efetuada com sucesso");

			}

		});

	}

	public static ImageIcon logo() {

		try {
			return Icones.getProdutos();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Produtos";

	}

	/**
	 * Create the frame.
	 */
	public Produtos() {
		
		this.inicializarComponentes();
	}

	public void inicializarComponentes() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		buttonGroup2 = new javax.swing.ButtonGroup();
		jScrollPane4 = new javax.swing.JScrollPane();
		jTable3 = new javax.swing.JTable();
		jScrollPane5 = new javax.swing.JScrollPane();
		jTable4 = new javax.swing.JTable();
		jScrollPane6 = new javax.swing.JScrollPane();
		jTable5 = new javax.swing.JTable();
		jScrollPane7 = new javax.swing.JScrollPane();
		jTable6 = new javax.swing.JTable();
		jScrollPane8 = new javax.swing.JScrollPane();
		jTable7 = new javax.swing.JTable();
		jSeparator1 = new javax.swing.JSeparator();
		jLabel1 = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblProdutos = new javax.swing.JTable();
		sliderPagina = new javax.swing.JSlider();
		lblNumeroPagina = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jPanel1 = new javax.swing.JPanel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		txtNome = new javax.swing.JTextField();
		txtNcm = new javax.swing.JTextField();
		txtNcm.setEnabled(false);
		txtCodigoBarra = new javax.swing.JTextField();
		txtCusto = new javax.swing.JFormattedTextField();
		txtValor = new javax.swing.JFormattedTextField();
		jPanel2 = new javax.swing.JPanel();
		lblImagem = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		jLabel13 = new javax.swing.JLabel();
		txtPeso = new javax.swing.JFormattedTextField();
		txtVolume = new javax.swing.JFormattedTextField();
		cboUnidadeVolume = new javax.swing.JComboBox<>();
		cboUnidadePeso = new javax.swing.JComboBox<>();
		jPanel4 = new javax.swing.JPanel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		cboUnidadeEstoque = new javax.swing.JComboBox<>();
		txtEstoque = new javax.swing.JFormattedTextField();
		jLabel16 = new javax.swing.JLabel();
		txtDisp = new javax.swing.JFormattedTextField();
		btConfirmar = new javax.swing.JButton();
		btNovoProduto = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Pedidos de Compra");
		setBounds(new java.awt.Rectangle(0, 0, 900, 570));
		setMinimumSize(new java.awt.Dimension(900, 570));

		jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
		jLabel1.setText("Cadastro de Produtos");

		this.tblProdutos
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(this.tblProdutos);

		lblNumeroPagina.setText("Pg 10");

		jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel3.setText("Altere a pagina deslizando");

		jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Produto"));

		jLabel4.setText("Nome:");

		jLabel5.setText("NCM:");

		jLabel6.setText("Codigo de barras:");

		jLabel7.setText("Valor(R$):");

		jLabel8.setText("Custo(R$):");

		btnBtncm = new JButton("...");
		
		btDadosContabeis = new JButton("Dados Contabeis");
		
		lblPorcentagemLoja = new JLabel();
		lblPorcentagemLoja.setText("Porcentagem Loja:");
		
		chkApareceLoja = new JCheckBox("Aparece na loja ?");
		
		txtPorcentagemLoja = new JFormattedTextField();

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1Layout.setHorizontalGroup(
			jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup()
							.addComponent(jLabel4)
							.addGap(18)
							.addComponent(txtNome, 257, 257, 257)
							.addContainerGap())
						.addGroup(jPanel1Layout.createSequentialGroup()
							.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
								.addGroup(jPanel1Layout.createSequentialGroup()
									.addComponent(jLabel6)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtCodigoBarra, 216, 216, 216))
								.addGroup(jPanel1Layout.createSequentialGroup()
									.addComponent(jLabel7)
									.addGap(6)
									.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(jLabel8)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtCusto, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)))
							.addGap(10))
						.addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
							.addGroup(jPanel1Layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(jPanel1Layout.createSequentialGroup()
									.addComponent(btDadosContabeis)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
										.addGroup(jPanel1Layout.createSequentialGroup()
											.addComponent(lblPorcentagemLoja, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
											.addComponent(txtPorcentagemLoja, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel1Layout.createSequentialGroup()
											.addComponent(chkApareceLoja)
											.addPreferredGap(ComponentPlacement.RELATED, 0, Short.MAX_VALUE))))
								.addGroup(jPanel1Layout.createSequentialGroup()
									.addComponent(jLabel5)
									.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
									.addComponent(txtNcm, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnBtncm, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
							.addGap(28))))
		);
		jPanel1Layout.setVerticalGroup(
			jPanel1Layout.createParallelGroup(Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel4)
						.addComponent(txtNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel5)
						.addComponent(txtNcm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBtncm, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel6)
						.addComponent(txtCodigoBarra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(jLabel7)
						.addComponent(txtCusto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jLabel8)
						.addComponent(txtValor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
						.addComponent(btDadosContabeis, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createSequentialGroup()
							.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPorcentagemLoja)
								.addComponent(txtPorcentagemLoja, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(chkApareceLoja)))
					.addContainerGap())
		);
		jPanel1.setLayout(jPanel1Layout);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Imagem"));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addComponent(lblImagem, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
						.addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addComponent(lblImagem,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));

		jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Medidas"));

		jLabel10.setText("Peso:");

		jLabel11.setText("Unid. Peso");

		jLabel12.setText("Unid. Volume");

		jLabel13.setText("Volume:");

		txtPeso.setToolTipText("");

		txtVolume.setToolTipText("");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel10)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtPeso))
								.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel11)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cboUnidadePeso, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel13)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtVolume))
								.addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel12)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(cboUnidadeVolume, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel11).addComponent(cboUnidadePeso,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel10).addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel12).addComponent(cboUnidadeVolume,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel13).addComponent(txtVolume, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(12, Short.MAX_VALUE)));

		jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Estoque"));

		jLabel14.setText("Unid. Estq:");

		jLabel15.setText("Estq:");

		txtEstoque.setToolTipText("");

		jLabel16.setText("Disp:");

		txtDisp.setToolTipText("");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel4Layout
				.createSequentialGroup().addContainerGap()
				.addGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel14).addGap(2)
								.addComponent(cboUnidadeEstoque, 0, 199, Short.MAX_VALUE))
						.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel15)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtEstoque, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE).addComponent(jLabel16)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(txtDisp, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout.createParallelGroup(Alignment.LEADING)
						.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel14)
										.addComponent(cboUnidadeEstoque, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(jPanel4Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel15)
										.addComponent(jLabel16)
										.addComponent(txtDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(txtEstoque, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addContainerGap(13, Short.MAX_VALUE)));
		jPanel4.setLayout(jPanel4Layout);

		btConfirmar.setText("Confirmar");

		btNovoProduto.setText("Novo Produto");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1)
						.addGroup(layout.createSequentialGroup().addGap(4, 4, 4)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup()
												.addComponent(btNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE,
														153, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 240,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 469,
												Short.MAX_VALUE)
										.addComponent(jSeparator2))
								.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(sliderPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 420,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(lblNumeroPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 54,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
						.addComponent(lblNumeroPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup().addComponent(jLabel1)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addGroup(layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(btNovoProduto,
																javax.swing.GroupLayout.DEFAULT_SIZE, 35,
																Short.MAX_VALUE)
														.addComponent(btConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(sliderPagina, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		setSize(new java.awt.Dimension(816, 538));
		setLocationRelativeTo(null);

		this.txtCusto.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtValor.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtPorcentagemLoja.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtVolume.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtEstoque.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtDisp.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
		this.txtPeso.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

	}

	javax.swing.JButton btConfirmar;
	javax.swing.JButton btNovoProduto;
	javax.swing.ButtonGroup buttonGroup1;
	javax.swing.ButtonGroup buttonGroup2;
	javax.swing.JComboBox<TipoQuantidade> cboUnidadeEstoque;
	javax.swing.JComboBox<UnidadePeso> cboUnidadePeso;
	javax.swing.JComboBox<UnidadeVolume> cboUnidadeVolume;
	javax.swing.JLabel jLabel1;
	javax.swing.JLabel jLabel10;
	javax.swing.JLabel jLabel11;
	javax.swing.JLabel jLabel12;
	javax.swing.JLabel jLabel13;
	javax.swing.JLabel jLabel14;
	javax.swing.JLabel jLabel15;
	javax.swing.JLabel jLabel16;
	javax.swing.JLabel jLabel3;
	javax.swing.JLabel jLabel4;
	javax.swing.JLabel jLabel5;
	javax.swing.JLabel jLabel6;
	javax.swing.JLabel jLabel7;
	javax.swing.JLabel jLabel8;
	javax.swing.JPanel jPanel1;
	javax.swing.JPanel jPanel2;
	javax.swing.JPanel jPanel3;
	javax.swing.JPanel jPanel4;
	javax.swing.JScrollPane jScrollPane1;
	javax.swing.JScrollPane jScrollPane4;
	javax.swing.JScrollPane jScrollPane5;
	javax.swing.JScrollPane jScrollPane6;
	javax.swing.JScrollPane jScrollPane7;
	javax.swing.JScrollPane jScrollPane8;
	javax.swing.JSeparator jSeparator1;
	javax.swing.JSeparator jSeparator2;
	javax.swing.JTable tblProdutos;
	javax.swing.JTable jTable3;
	javax.swing.JTable jTable4;
	javax.swing.JTable jTable5;
	javax.swing.JTable jTable6;
	javax.swing.JTable jTable7;
	javax.swing.JLabel lblImagem;
	javax.swing.JLabel lblNumeroPagina;
	javax.swing.JSlider sliderPagina;
	javax.swing.JTextField txtCodigoBarra;
	javax.swing.JFormattedTextField txtCusto;
	javax.swing.JFormattedTextField txtDisp;
	javax.swing.JFormattedTextField txtEstoque;
	javax.swing.JTextField txtNcm;
	javax.swing.JTextField txtNome;
	javax.swing.JFormattedTextField txtPeso;
	javax.swing.JFormattedTextField txtValor;
	javax.swing.JFormattedTextField txtVolume;
	private JButton btnBtncm;
	private JButton btDadosContabeis;
	private JLabel lblPorcentagemLoja;
	private JCheckBox chkApareceLoja;
	private JFormattedTextField txtPorcentagemLoja;

}
