package br.com.inicial;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.notas.Nota;
import br.com.afgtec.notas.NotaService;
import br.com.afgtec.notas.ProdutoNota;
import br.com.afgtec.notas.RepresentadorNotaCompleto;
import br.com.afgtec.notas.RepresentadorProdutoNota;
import br.com.afgtec.notas.RepresentadorVencimento;
import br.com.afgtec.notas.StatusNota;
import br.com.afgtec.notas.Vencimento;
import br.com.afgtec.pessoa.Pessoa;
import br.com.afgtec.pessoa.Transportadora;
import br.com.afgtec.pessoa.Usuario;
import br.com.afgtec.produto.Produto;
import br.com.afgtec.produto.ProdutoService;
import br.com.agrofauna.conversores.ConversorDate;
import br.com.agrofauna.utilidades.GerenciadorLista;
import br.com.agrofauna.utilidades.ListModelGenerica;
import br.com.entidades.Icones;

public class Notas extends Modulo {

	private static final long serialVersionUID = 1L;

	public Usuario operador;

	private GerenciadorLista<Nota> grNotas;

	private Nota nota;

	private Produto selecionado;

	private ListModelGenerica<Vencimento> mdlVencimento;

	private ListModelGenerica<ProdutoNota> mdlProdutos;

	public Notas() {

		super();

		this.inicializarComponentes();

	}

	private void setNotaEntrada(Nota ne) {

		EntityManager et = ET.nova();

		this.nota = ne;

		this.btNovaEntrada.setEnabled(this.nota.getId() > 0);

		et.detach(this.nota);

		this.txtNumeroNota.setText(ne.getNumero() + "");

		this.txtBaseCalculo.setEnabled(false);
		this.txtBaseCalculo.setText(ne.getBaseCalculo() + "");

		this.txtCofinsNota.setEnabled(false);
		this.txtCofinsNota.setText(ne.getCofins() + "");

		this.txtEBairro.setText(ne.getEmitente().getEndereco().getBairro());
		this.txtECep.setText(ne.getEmitente().getEndereco().getCep());
		this.txtECidade.setText(ne.getEmitente().getEndereco().getCidade());
		this.txtECnpj.setText(ne.getEmitente().getCnpj());
		this.txtEComplemento.setText(ne.getEmitente().getEndereco().getComplemento());
		this.txtEemail.setText(ne.getEmitente().getEmail());
		this.txtEestado.setText(ne.getEmitente().getEndereco().getEstado());
		this.txtEie.setText(ne.getEmitente().getInscricaoEstadual());
		this.txtENumero.setText(ne.getEmitente().getEndereco().getNumero() + "");
		this.txtERazaoSocial.setText(ne.getEmitente().getRazao_social());
		this.txtERua.setText(ne.getEmitente().getEndereco().getRua());
		this.txtETel.setText(ne.getEmitente().getTelefone());

		this.txtIcmsDeson.setText(ne.getIcmsDesonerado() + "");
		this.txtIcmsDeson.setEnabled(false);

		this.txtInfoAdic.setText(ne.getObservacoes());

		this.txtIpi.setText(ne.getIpi() + "");
		this.txtIpi.setEnabled(false);

		this.txtPisNota.setText(ne.getPis() + "");
		this.txtPisNota.setEnabled(false);

		this.txtIcmsNota.setText(ne.getIcms() + "");
		this.txtIcmsNota.setEnabled(false);

		this.txtTotal.setText(ne.getTotal() + "");
		this.txtTotal.setEnabled(false);

		this.txtNumeroNota1.setText(ne.getSerie() + "");
		this.txtNumeroNota2.setText(ne.getChave());
		this.txtNumeroNota3.setText(ne.getProtocolo());
		this.txtNumeroNota4.setText(ne.getNatureza_operacao());

		this.jFormattedTextField1
				.setValue(new ConversorDate().paraString(new java.sql.Date(ne.getData_emissao().getTimeInMillis())));

		this.txtTBairro.setText(ne.getTransportadora().getEndereco().getBairro());
		this.txtTCep.setText(ne.getTransportadora().getEndereco().getCep());
		this.txtTCidade.setText(ne.getTransportadora().getEndereco().getCidade());
		this.txtTCnpj.setText(ne.getTransportadora().getCnpj());
		this.txtTComplemento.setText(ne.getTransportadora().getEndereco().getComplemento());
		this.txtTemail.setText(ne.getTransportadora().getEmail());
		this.txtTestado.setText(ne.getTransportadora().getEndereco().getEstado());
		this.txtTie.setText(ne.getTransportadora().getInscricao_estadual());
		this.txtTNumero.setText(ne.getTransportadora().getEndereco().getNumero() + "");
		this.txtTRazaoSocial.setText(ne.getTransportadora().getRazao_social());
		this.txtTRua.setText(ne.getTransportadora().getEndereco().getRua());
		this.txtTTel.setText(ne.getTransportadora().getTelefone());

		this.mdlVencimento = new ListModelGenerica<Vencimento>(new ArrayList<Vencimento>(ne.getVencimentos()),
				Vencimento.class, RepresentadorVencimento.class, new Vencimento());
		this.tblVencimentos.setModel(this.mdlVencimento);

		this.mdlProdutos = new ListModelGenerica<ProdutoNota>(new ArrayList<ProdutoNota>(ne.getProdutos()),
				ProdutoNota.class, RepresentadorProdutoNota.class, new ProdutoNota());
		this.tblProdutos.setModel(this.mdlProdutos);

		ComboBoxModel<StatusNota> cboStatus = new DefaultComboBoxModel<StatusNota>(StatusNota.values());
		this.jComboBox1.setModel(cboStatus);

		this.jComboBox1.setSelectedItem(this.nota.getStatus());

	}

	public void init(Usuario operador) {

		this.setTitle(operador.getEmpresa().getNome() + " - Operador: " + operador.getNome() + " Notas");

		this.operador = operador;

		NotaService ns = new NotaService();
		ns.setEmpresa(operador.getEmpresa());

		this.grNotas = new GerenciadorLista<Nota>(Nota.class, this.tblNotas, ns, null, new Nota(),
				RepresentadorNotaCompleto.class);

		this.grNotas.setLblPagina(this.lblPagina);
		this.grNotas.setLblSlider(this.slPaginacao);

		this.grNotas.atualizar();

		this.btNovaEntrada.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				Nota ne = new Nota();
				ne.setEmpresa(operador.getEmpresa());
				setNotaEntrada(ne);

			}

		});

		this.tblNotas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {

				if (!arg0.getValueIsAdjusting() && tblNotas.getSelectedRow() >= 0) {

					setNotaEntrada(grNotas.getModel().getListaBase().get(tblNotas.getSelectedRow()));

				}

			}

		});

		this.btInserirVencimento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (!vc(txtValorVencimento) || !vc(txtDataVencimento))
					return;

				Vencimento v = new Vencimento();

				v.setValor(((Number) txtValorVencimento.getValue()).doubleValue());
				v.setData(Masks.getData(txtDataVencimento.getText()));

				mdlVencimento.add(v);

				txtValorVencimento.setValue(0.0);
				txtDataVencimento.setValue("");

			}

		});

		this.btBuscarProduto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ProdutoService ps = new ProdutoService();
				ps.setEmpresa(operador.getEmpresa());

				new Selecao<Produto>(Produto.class, ps, new OnSelect<Produto>() {

					@Override
					public void onSelect(Produto entidade) {

						selecionado = entidade;

						txtNomeProduto.setText(selecionado.getNome());

					}

				}).setVisible(true);

			}

		});

		this.btAcrescentar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (selecionado == null) {
					erro("Selecione um produto primeiro.");
					return;
				}

				try {

					if (!vc(txtValorProduto) || !vc(txtQuantidadeProduto))
						return;

					ProdutoNota pn = new ProdutoNota();
					pn.setNota(nota);
					pn.setQuantidade(Double.parseDouble(txtQuantidadeProduto.getText()));
					pn.setValor(Double.parseDouble(txtValorProduto.getText()));
					pn.setProduto(selecionado);

					mdlProdutos.add(pn);

				} catch (Exception ex) {

					erro("Preencha os dados adequadamente");

				}

			}

		});

		this.btNovaEntrada.doClick();

		this.txtQuantidadeProduto.setText("");
		this.txtValorProduto.setText("");
		this.txtNomeProduto.setText("");

		ActionListener confirma = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (!vc(txtNumeroNota1) || !vc(txtNumeroNota) || !vc(txtNumeroNota2) || !vc(txtNumeroNota3)
						|| !vc(txtNumeroNota4) || !vc(txtInfoAdic) || !vc(jFormattedTextField1) || !vc(txtECnpj)
						|| !vc(txtERazaoSocial) || !vc(txtETel) || !vc(txtEie) || !vc(txtEemail) || !vc(txtERua)
						|| !vc(txtEBairro) || !vc(txtECidade) || !vc(txtECep) || !vc(txtENumero) || !vc(txtTCnpj)
						|| !vc(txtTRazaoSocial) || !vc(txtTTel) || !vc(txtTie) || !vc(txtTemail) || !vc(txtTRua)
						|| !vc(txtTBairro) || !vc(txtTCidade) || !vc(txtTCep) || !vc(txtTNumero) || !vc(txtInfoAdic))
					return;

				try {

					nota.setProdutos(mdlProdutos.getListaCompleta());
					nota.setVencimentos(mdlVencimento.getListaCompleta());
					nota.setNumero(Integer.parseInt(txtNumeroNota.getText()));
					nota.setSerie(Integer.parseInt(txtNumeroNota1.getText()));
					nota.setChave(txtNumeroNota2.getText());
					nota.setProtocolo(txtNumeroNota3.getText());
					nota.setNatureza_operacao(txtNumeroNota4.getText());
					nota.setObservacoes(txtInfoAdic.getText());

					nota.setData_emissao(Masks.getData(jFormattedTextField1.getText()));

					Pessoa emit = new Pessoa();
					emit.setCnpj(txtECnpj.getText());
					emit.setRazao_social(txtERazaoSocial.getText());
					emit.setTelefone(txtETel.getText());
					emit.setInscricaoEstadual(txtEie.getText());
					emit.setEmail(txtEemail.getText());

					emit.getEndereco().setRua(txtERua.getText());
					emit.getEndereco().setBairro(txtEBairro.getText());
					emit.getEndereco().setCidade(txtECidade.getText());
					emit.getEndereco().setCep(txtECep.getText());
					emit.getEndereco().setNumero(txtENumero.getText());
					emit.getEndereco().setComplemento(txtEComplemento.getText());

					nota.setEmitente(emit);

					Transportadora trans = new Transportadora();
					trans.setCnpj(txtTCnpj.getText());
					trans.setRazao_social(txtTRazaoSocial.getText());
					trans.setTelefone(txtTTel.getText());
					trans.setInscricao_estadual(txtTie.getText());
					trans.setEmail(txtTemail.getText());

					trans.getEndereco().setRua(txtTRua.getText());
					trans.getEndereco().setBairro(txtTBairro.getText());
					trans.getEndereco().setCidade(txtTCidade.getText());
					trans.getEndereco().setCep(txtTCep.getText());
					trans.getEndereco().setNumero(txtTNumero.getText());
					trans.getEndereco().setComplemento(txtTComplemento.getText());

					

					EntityManager et = ET.nova();

					et.getTransaction().begin();

					nota = et.merge(nota);

					nota.setEmitente(et.merge(emit));
					nota.setTransportadora(et.merge(trans));
					
					et.getTransaction().commit();

					btNovaEntrada.doClick();

					grNotas.atualizar();

					info("Operacao executada com sucesso");

				} catch (Exception ec) {

					ec.printStackTrace();

					erro("Ocorreu um problema");

				}

			}

		};

		this.btConfirmarEmitente.addActionListener(confirma);
		this.btConfirmarNota.addActionListener(confirma);
		this.btConfirmarTransportadora.addActionListener(confirma);
		this.btConfirmarProduto.addActionListener(confirma);

	}

	public static ImageIcon logo() {

		try {
			return Icones.getArquivos();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Notas de Entrada";

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
		lblTitulo = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane3 = new javax.swing.JScrollPane();
		tblProdutos = new javax.swing.JTable();
		jPanel11 = new javax.swing.JPanel();
		jLabel42 = new javax.swing.JLabel();
		jLabel43 = new javax.swing.JLabel();
		txtQuantidadeProduto = new javax.swing.JTextField();
		txtValorProduto = new javax.swing.JTextField();
		txtNomeProduto = new javax.swing.JTextField();
		jLabel44 = new javax.swing.JLabel();
		btBuscarProduto = new javax.swing.JButton();
		btAcrescentar = new javax.swing.JButton();
		btConfirmarProduto = new javax.swing.JButton();
		jPanel4 = new javax.swing.JPanel();
		jLabel27 = new javax.swing.JLabel();
		jLabel28 = new javax.swing.JLabel();
		jLabel29 = new javax.swing.JLabel();
		jLabel30 = new javax.swing.JLabel();
		jLabel31 = new javax.swing.JLabel();
		jLabel32 = new javax.swing.JLabel();
		jLabel33 = new javax.swing.JLabel();
		jLabel34 = new javax.swing.JLabel();
		jLabel35 = new javax.swing.JLabel();
		jLabel36 = new javax.swing.JLabel();
		jLabel37 = new javax.swing.JLabel();
		jLabel38 = new javax.swing.JLabel();
		jLabel39 = new javax.swing.JLabel();
		jLabel40 = new javax.swing.JLabel();
		jLabel41 = new javax.swing.JLabel();
		txtNumeroNota = new javax.swing.JTextField();
		txtNumeroNota1 = new javax.swing.JTextField();
		jFormattedTextField1 = new javax.swing.JFormattedTextField();
		txtNumeroNota2 = new javax.swing.JTextField();
		txtNumeroNota3 = new javax.swing.JTextField();
		jComboBox1 = new javax.swing.JComboBox<>();
		txtNumeroNota4 = new javax.swing.JTextField();
		jScrollPane2 = new javax.swing.JScrollPane();
		txtInfoAdic = new javax.swing.JTextArea();
		txtTotal = new javax.swing.JTextField();
		txtIcmsNota = new javax.swing.JTextField();
		txtPisNota = new javax.swing.JTextField();
		txtCofinsNota = new javax.swing.JTextField();
		txtBaseCalculo = new javax.swing.JTextField();
		txtIpi = new javax.swing.JTextField();
		txtIcmsDeson = new javax.swing.JTextField();
		btConfirmarNota = new javax.swing.JButton();
		jPanel5 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblVencimentos = new javax.swing.JTable();
		jPanel2 = new javax.swing.JPanel();
		jLabel25 = new javax.swing.JLabel();
		jLabel26 = new javax.swing.JLabel();
		txtDataVencimento = new javax.swing.JFormattedTextField();
		txtValorVencimento = new javax.swing.JFormattedTextField();
		btInserirVencimento = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jPanel6 = new javax.swing.JPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		txtERua = new javax.swing.JTextField();
		txtENumero = new javax.swing.JTextField();
		txtEComplemento = new javax.swing.JTextField();
		txtEBairro = new javax.swing.JTextField();
		txtECidade = new javax.swing.JTextField();
		txtEestado = new javax.swing.JTextField();
		txtECep = new javax.swing.JTextField();
		jPanel7 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		txtERazaoSocial = new javax.swing.JTextField();
		txtECnpj = new javax.swing.JFormattedTextField();
		txtEie = new javax.swing.JFormattedTextField();
		txtETel = new javax.swing.JFormattedTextField();
		txtEemail = new javax.swing.JTextField();
		btConfirmarEmitente = new javax.swing.JButton();
		jPanel8 = new javax.swing.JPanel();
		jPanel9 = new javax.swing.JPanel();
		jLabel13 = new javax.swing.JLabel();
		jLabel14 = new javax.swing.JLabel();
		jLabel15 = new javax.swing.JLabel();
		jLabel16 = new javax.swing.JLabel();
		jLabel17 = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		jLabel19 = new javax.swing.JLabel();
		txtTRua = new javax.swing.JTextField();
		txtTNumero = new javax.swing.JTextField();
		txtTComplemento = new javax.swing.JTextField();
		txtTBairro = new javax.swing.JTextField();
		txtTCidade = new javax.swing.JTextField();
		txtTestado = new javax.swing.JTextField();
		txtTCep = new javax.swing.JTextField();
		jPanel10 = new javax.swing.JPanel();
		jLabel20 = new javax.swing.JLabel();
		jLabel21 = new javax.swing.JLabel();
		jLabel22 = new javax.swing.JLabel();
		jLabel23 = new javax.swing.JLabel();
		jLabel24 = new javax.swing.JLabel();
		txtTRazaoSocial = new javax.swing.JTextField();
		txtTCnpj = new javax.swing.JFormattedTextField();
		txtTie = new javax.swing.JFormattedTextField();
		txtTTel = new javax.swing.JFormattedTextField();
		txtTemail = new javax.swing.JTextField();
		btConfirmarTransportadora = new javax.swing.JButton();
		btNovaEntrada = new javax.swing.JButton();
		jScrollPane9 = new javax.swing.JScrollPane();
		tblNotas = new javax.swing.JTable();
		lblTitulo1 = new javax.swing.JLabel();
		jLabel45 = new javax.swing.JLabel();
		slPaginacao = new javax.swing.JSlider();
		lblPagina = new javax.swing.JLabel();

		setBounds(new java.awt.Rectangle(0, 0, 870, 500));
		setMinimumSize(new java.awt.Dimension(870, 500));

		lblTitulo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
		lblTitulo.setText("Notas de Entrada");

		jTabbedPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Nota"));
		jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
		jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		tblProdutos
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane3.setViewportView(tblProdutos);

		jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Novo Produto"));

		jLabel42.setText("Quantidade:");

		jLabel43.setText("Valor:");

		txtQuantidadeProduto.setText("jTextField1");

		txtValorProduto.setText("jTextField1");

		txtNomeProduto.setText("jTextField3");
		txtNomeProduto.setEnabled(false);

		jLabel44.setText("Buscar Produto:");

		btBuscarProduto.setText("jButton1");

		btAcrescentar.setText("Acrescentar");
		btAcrescentar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout.setHorizontalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addGroup(jPanel11Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel11Layout
								.createSequentialGroup()
								.addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(btAcrescentar, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGroup(jPanel11Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addGroup(jPanel11Layout.createSequentialGroup().addComponent(jLabel42)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(txtQuantidadeProduto))
												.addGroup(jPanel11Layout.createSequentialGroup()
														.addComponent(txtNomeProduto,
																javax.swing.GroupLayout.PREFERRED_SIZE, 127,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(btBuscarProduto,
																javax.swing.GroupLayout.PREFERRED_SIZE, 41,
																javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(javax.swing.GroupLayout.Alignment.LEADING,
														jPanel11Layout.createSequentialGroup().addComponent(jLabel43)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(txtValorProduto))))
								.addGap(44, 44, 44)))));
		jPanel11Layout.setVerticalGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel11Layout.createSequentialGroup().addContainerGap().addComponent(jLabel44)
						.addGap(1, 1, 1)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btBuscarProduto))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel42).addComponent(txtQuantidadeProduto,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel43).addComponent(txtValorProduto,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(btAcrescentar)
						.addContainerGap(12, Short.MAX_VALUE)));

		btConfirmarProduto.setText("Confirmar");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup().addContainerGap()
						.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 514,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 201, Short.MAX_VALUE)
								.addGroup(jPanel3Layout.createSequentialGroup().addGap(0, 79, Short.MAX_VALUE)
										.addComponent(btConfirmarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 122,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addGroup(jPanel3Layout.createSequentialGroup()
										.addComponent(btConfirmarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33,
												Short.MAX_VALUE)
										.addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));

		jTabbedPane1.addTab("Produtos e Serviços", jPanel3);

		jLabel27.setText("Número:");

		jLabel28.setText("Série:");

		jLabel29.setText("Data de Emissão:");

		jLabel30.setText("Chave:");

		jLabel31.setText("Protocolo de Autorização de uso:");

		jLabel32.setText("Situação da nota:");

		jLabel33.setText("Natureza da Operação:");

		jLabel34.setText("Total:");

		jLabel35.setText("ICMS:");

		jLabel36.setText("PIS:");

		jLabel37.setText("COFINS:");

		jLabel38.setText("Base Calculo:");

		jLabel39.setText("IPI:");

		jLabel40.setText("ICMS Desonerado:");

		jLabel41.setText("Informações Adicionais:");

		txtNumeroNota.setText("jTextField1");

		txtNumeroNota.setPreferredSize(new Dimension(50, 20));

		txtNumeroNota1.setText("jTextField1");

		jFormattedTextField1.setText("jFormattedTextField1");

		txtNumeroNota2.setText("jTextField1");

		txtNumeroNota3.setText("jTextField1");

		txtNumeroNota4.setText("jTextField1");

		txtInfoAdic.setColumns(20);
		txtInfoAdic.setRows(5);
		jScrollPane2.setViewportView(txtInfoAdic);

		txtTotal.setText("jTextField1");

		txtIcmsNota.setText("jTextField1");

		txtPisNota.setText("jTextField1");

		txtCofinsNota.setText("jTextField1");

		txtBaseCalculo.setText("txtBS");
		txtBaseCalculo.setEditable(true);

		btConfirmarNota.setText("Confirmar");

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap().addGroup(jPanel4Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout
								.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel4Layout
												.createSequentialGroup().addGroup(jPanel4Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addGroup(jPanel4Layout.createSequentialGroup()
																.addComponent(jLabel27)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(txtNumeroNota,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(jPanel4Layout.createSequentialGroup()
																.addComponent(jLabel28)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(txtNumeroNota1))
														.addComponent(jLabel31))
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtNumeroNota3, javax.swing.GroupLayout.PREFERRED_SIZE,
														182, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel29)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jFormattedTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel30)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtNumeroNota2, javax.swing.GroupLayout.PREFERRED_SIZE,
														289, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel32)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel33)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtNumeroNota4, javax.swing.GroupLayout.PREFERRED_SIZE,
														182, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel4Layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel36)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(txtPisNota))
												.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel37)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(txtCofinsNota,
																javax.swing.GroupLayout.DEFAULT_SIZE, 127,
																Short.MAX_VALUE))
												.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel38)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(txtBaseCalculo,
																javax.swing.GroupLayout.DEFAULT_SIZE, 105,
																Short.MAX_VALUE))
												.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel39)
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(txtIpi, javax.swing.GroupLayout.PREFERRED_SIZE,
																114, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jLabel34).addComponent(jLabel35))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addGroup(jPanel4Layout
																.createParallelGroup(
																		javax.swing.GroupLayout.Alignment.LEADING,
																		false)
																.addComponent(txtTotal,
																		javax.swing.GroupLayout.DEFAULT_SIZE, 118,
																		Short.MAX_VALUE)
																.addComponent(txtIcmsNota))))
										.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel40)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtIcmsDeson, javax.swing.GroupLayout.PREFERRED_SIZE, 114,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btConfirmarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 100,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGroup(jPanel4Layout.createSequentialGroup().addComponent(jLabel41).addGap(18, 18, 18)
								.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 578,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 11, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel4Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel27).addComponent(jLabel34)
								.addComponent(txtNumeroNota, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btConfirmarNota, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel28).addComponent(jLabel35)
								.addComponent(txtNumeroNota1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtIcmsNota, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel29).addComponent(jLabel36)
								.addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtPisNota, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel30).addComponent(jLabel37).addComponent(
										txtNumeroNota2, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtCofinsNota, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel31).addComponent(jLabel38)
								.addComponent(txtNumeroNota3, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtBaseCalculo, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel32).addComponent(jLabel39).addComponent(
										jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtIpi, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel33).addComponent(jLabel40).addComponent(
										txtNumeroNota4, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtIcmsDeson, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jLabel41).addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE, 44,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Informações da Nota", jPanel4);

		tblVencimentos
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(tblVencimentos);

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Novo Vencimento"));

		jLabel25.setText("Data:");

		jLabel26.setText("Valor:");

		btInserirVencimento.setText("Inserir");
		btInserirVencimento.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);

		txtValorVencimento.setPreferredSize(new Dimension(50, 20));
		txtValorVencimento.setSize(new Dimension(50, 20));

		txtEestado.setPreferredSize(new Dimension(50, 20));
		txtEestado.setSize(new Dimension(50, 20));

		txtTestado.setPreferredSize(new Dimension(50, 20));
		txtTestado.setSize(new Dimension(50, 20));

		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout
						.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(
										jPanel2Layout.createSequentialGroup().addComponent(jLabel25)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtDataVencimento))
								.addGroup(jPanel2Layout.createSequentialGroup().addComponent(jLabel26)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtValorVencimento, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(85, 85, 85)))
						.addContainerGap(85, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel2Layout.createSequentialGroup()
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btInserirVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 181,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel25).addComponent(txtDataVencimento,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel26).addComponent(txtValorVencimento,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(btInserirVencimento, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
						.addGap(6, 6, 6)));

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout
				.setHorizontalGroup(
						jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel5Layout.createSequentialGroup().addContainerGap()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addContainerGap()));
		jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
								.addGroup(jPanel5Layout.createSequentialGroup()
										.addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));

		jTabbedPane1.addTab("Vencimentos", jPanel5);

		jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereco"));

		jLabel6.setText("Rua:");

		jLabel7.setText("Número:");

		jLabel8.setText("Bairro:");

		jLabel9.setText("Cidade:");

		jLabel10.setText("Estado:");

		jLabel11.setText("Complemento:");

		jLabel12.setText("CEP:");

		javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
		jPanel6.setLayout(jPanel6Layout);
		jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel6)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtERua, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel7)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtENumero, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(22, 22, 22)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel8).addGap(18, 18, 18)
										.addComponent(txtEBairro))
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel11)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtEComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 123,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(18, 18, 18)
						.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel10)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtEestado, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(jPanel6Layout.createSequentialGroup().addComponent(jLabel9)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtECidade, javax.swing.GroupLayout.DEFAULT_SIZE, 103,
												Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel12)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtECep, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		jPanel6Layout
				.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6)
								.addComponent(jLabel8).addComponent(jLabel9).addComponent(jLabel12)
								.addComponent(txtERua, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtEBairro, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtECidade, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtECep,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel7).addComponent(jLabel10).addComponent(jLabel11)
										.addComponent(txtENumero, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtEComplemento, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtEestado, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Gerais"));

		jLabel1.setText("Razão Social: ");

		jLabel2.setText("CNPJ:");

		jLabel3.setText("Inscrição Estadual:");

		jLabel4.setText("Telefone:");

		jLabel5.setText("E-mail:");

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap().addGroup(jPanel7Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel7Layout.createSequentialGroup().addGroup(jPanel7Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel1)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtERazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel2).addGap(18, 18, 18)
										.addComponent(txtECnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 142,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19,
										Short.MAX_VALUE)
								.addGroup(jPanel7Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel5)
												.addGap(18, 18, 18).addComponent(txtEemail))
										.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel4)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtETel, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addGroup(jPanel7Layout.createSequentialGroup().addComponent(jLabel3)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(txtEie, javax.swing.GroupLayout.PREFERRED_SIZE, 142,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel7Layout.setVerticalGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel7Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1).addComponent(jLabel4)
								.addComponent(txtERazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtETel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2).addComponent(jLabel5)
								.addComponent(txtECnpj, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtEemail, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel3).addComponent(txtEie, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		btConfirmarEmitente.setText("Confirmar");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel1Layout.createSequentialGroup()
								.addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(84, 84, 84).addComponent(btConfirmarEmitente,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel1Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 113,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btConfirmarEmitente, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(46, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Emitente", jPanel1);

		jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereco"));

		jLabel13.setText("Rua:");

		jLabel14.setText("Número:");

		jLabel15.setText("Bairro:");

		jLabel16.setText("Cidade:");

		jLabel17.setText("Estado:");

		jLabel18.setText("Complemento:");

		jLabel19.setText("CEP:");

		txtTRua.setText("jTextField1");

		txtTNumero.setText("jTextField1");

		txtTComplemento.setText("jTextField1");

		txtTBairro.setText("jTextField1");

		txtTCidade.setText("jTextField1");

		txtTestado.setText("jTextField1");

		txtTCep.setText("jTextField1");

		javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
		jPanel9.setLayout(jPanel9Layout);
		jPanel9Layout.setHorizontalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel9Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel13)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTRua, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel14)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 103,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(22, 22, 22)
						.addGroup(
								jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel15)
												.addGap(18, 18, 18).addComponent(txtTBairro))
										.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel18)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtTComplemento, javax.swing.GroupLayout.PREFERRED_SIZE,
														123, javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(18, 18, 18)
						.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel17)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTestado, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(0, 0, Short.MAX_VALUE))
								.addGroup(jPanel9Layout.createSequentialGroup().addComponent(jLabel16)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTCidade, javax.swing.GroupLayout.DEFAULT_SIZE, 103,
												Short.MAX_VALUE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jLabel19)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(txtTCep, javax.swing.GroupLayout.PREFERRED_SIZE, 94,
												javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		jPanel9Layout
				.setVerticalGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel9Layout.createSequentialGroup().addContainerGap().addGroup(jPanel9Layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel13)
								.addComponent(jLabel15).addComponent(jLabel16).addComponent(jLabel19)
								.addComponent(txtTRua, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTBairro, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTCidade, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTCep,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(jLabel14).addComponent(jLabel17).addComponent(jLabel18)
										.addComponent(txtTNumero, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtTComplemento, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(txtTestado, javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Gerais"));

		jLabel20.setText("Razão Social: ");

		jLabel21.setText("CNPJ:");

		jLabel22.setText("Inscrição Estadual:");

		jLabel23.setText("Telefone:");

		jLabel24.setText("E-mail:");

		txtTRazaoSocial.setText("jTextField1");

		txtTCnpj.setText("jFormattedTextField1");

		txtTie.setText("jFormattedTextField1");

		txtTTel.setText("jFormattedTextField1");

		txtTemail.setText("jTextField1");

		javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
		jPanel10.setLayout(jPanel10Layout);
		jPanel10Layout.setHorizontalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap().addGroup(jPanel10Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jPanel10Layout.createSequentialGroup()
								.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel20)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtTRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE,
														151, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel21)
												.addGap(18, 18, 18).addComponent(txtTCnpj,
														javax.swing.GroupLayout.PREFERRED_SIZE, 142,
														javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19,
										Short.MAX_VALUE)
								.addGroup(jPanel10Layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
										.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel24)
												.addGap(18, 18, 18).addComponent(txtTemail))
										.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel23)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(txtTTel, javax.swing.GroupLayout.PREFERRED_SIZE, 151,
														javax.swing.GroupLayout.PREFERRED_SIZE))))
						.addGroup(jPanel10Layout.createSequentialGroup().addComponent(jLabel22)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(txtTie, javax.swing.GroupLayout.PREFERRED_SIZE, 142,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel10Layout.setVerticalGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel10Layout.createSequentialGroup().addContainerGap()
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel20).addComponent(jLabel23)
								.addComponent(txtTRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTTel, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel21).addComponent(jLabel24)
								.addComponent(txtTCnpj, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(txtTemail, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel22).addComponent(txtTie, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));

		btConfirmarTransportadora.setText("Confirmar");

		javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
		jPanel8.setLayout(jPanel8Layout);
		jPanel8Layout.setHorizontalGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel8Layout.createSequentialGroup().addContainerGap().addGroup(jPanel8Layout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jPanel8Layout.createSequentialGroup()
								.addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(84, 84, 84).addComponent(btConfirmarTransportadora,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
						.addContainerGap()));
		jPanel8Layout.setVerticalGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jPanel8Layout.createSequentialGroup().addContainerGap()
								.addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 113,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(btConfirmarTransportadora, javax.swing.GroupLayout.PREFERRED_SIZE,
												36, javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(46, Short.MAX_VALUE)));

		jTabbedPane1.addTab("Transportadora", jPanel8);

		btNovaEntrada.setText("Nova Entrada Manual");

		tblNotas.setModel(
				new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane9.setViewportView(tblNotas);

		lblTitulo1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
		lblTitulo1.setText("Selecione uma entrada abaixo para visualizar");

		jLabel45.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		jLabel45.setText("Altere as pagina através do seletor ao lado");

		lblPagina.setText("Pg. 10");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
						.createSequentialGroup().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
										false)
								.addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(jScrollPane9)
										.addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 469,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING,
												javax.swing.GroupLayout.PREFERRED_SIZE, 358,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(jTabbedPane1)
										.addGroup(layout.createSequentialGroup().addGap(11, 11, 11)
												.addComponent(lblTitulo1, javax.swing.GroupLayout.PREFERRED_SIZE, 469,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btNovaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE,
														144, javax.swing.GroupLayout.PREFERRED_SIZE))))
								.addGroup(layout.createSequentialGroup().addGap(167, 167, 167).addComponent(jLabel45)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(slPaginacao, javax.swing.GroupLayout.PREFERRED_SIZE, 289,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(lblPagina, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addContainerGap(32, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap().addComponent(lblTitulo).addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
										.addComponent(btNovaEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 39,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(lblTitulo1))
								.addGap(8, 8, 8)
								.addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 141,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup().addGap(12, 12, 12).addComponent(
												lblPagina, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(slPaginacao,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));

		setSize(new java.awt.Dimension(816, 645));
		setLocationRelativeTo(null);

		Masks.data().install(this.txtDataVencimento);
		Masks.data().install(this.jFormattedTextField1);
		Masks.telefone().install(this.txtTTel);
		Masks.telefone().install(this.txtETel);
		Masks.cnpj().install(this.txtECnpj);
		Masks.cnpj().install(this.txtTCnpj);

		this.txtValorVencimento.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

	}

	javax.swing.JButton btAcrescentar;
	javax.swing.JButton btBuscarProduto;
	javax.swing.JButton btConfirmarEmitente;
	javax.swing.JButton btConfirmarNota;
	javax.swing.JButton btConfirmarProduto;
	javax.swing.JButton btConfirmarTransportadora;
	javax.swing.JButton btInserirVencimento;
	javax.swing.ButtonGroup buttonGroup1;
	javax.swing.ButtonGroup buttonGroup2;
	javax.swing.JButton btNovaEntrada;
	javax.swing.JComboBox<StatusNota> jComboBox1;
	javax.swing.JFormattedTextField jFormattedTextField1;
	javax.swing.JLabel jLabel1;
	javax.swing.JLabel jLabel10;
	javax.swing.JLabel jLabel11;
	javax.swing.JLabel jLabel12;
	javax.swing.JLabel jLabel13;
	javax.swing.JLabel jLabel14;
	javax.swing.JLabel jLabel15;
	javax.swing.JLabel jLabel16;
	javax.swing.JLabel jLabel17;
	javax.swing.JLabel jLabel18;
	javax.swing.JLabel jLabel19;
	javax.swing.JLabel jLabel2;
	javax.swing.JLabel jLabel20;
	javax.swing.JLabel jLabel21;
	javax.swing.JLabel jLabel22;
	javax.swing.JLabel jLabel23;
	javax.swing.JLabel jLabel24;
	javax.swing.JLabel jLabel25;
	javax.swing.JLabel jLabel26;
	javax.swing.JLabel jLabel27;
	javax.swing.JLabel jLabel28;
	javax.swing.JLabel jLabel29;
	javax.swing.JLabel jLabel3;
	javax.swing.JLabel jLabel30;
	javax.swing.JLabel jLabel31;
	javax.swing.JLabel jLabel32;
	javax.swing.JLabel jLabel33;
	javax.swing.JLabel jLabel34;
	javax.swing.JLabel jLabel35;
	javax.swing.JLabel jLabel36;
	javax.swing.JLabel jLabel37;
	javax.swing.JLabel jLabel38;
	javax.swing.JLabel jLabel39;
	javax.swing.JLabel jLabel4;
	javax.swing.JLabel jLabel40;
	javax.swing.JLabel jLabel41;
	javax.swing.JLabel jLabel42;
	javax.swing.JLabel jLabel43;
	javax.swing.JLabel jLabel44;
	javax.swing.JLabel jLabel45;
	javax.swing.JLabel jLabel5;
	javax.swing.JLabel jLabel6;
	javax.swing.JLabel jLabel7;
	javax.swing.JLabel jLabel8;
	javax.swing.JLabel jLabel9;
	javax.swing.JPanel jPanel1;
	javax.swing.JPanel jPanel10;
	javax.swing.JPanel jPanel11;
	javax.swing.JPanel jPanel2;
	javax.swing.JPanel jPanel3;
	javax.swing.JPanel jPanel4;
	javax.swing.JPanel jPanel5;
	javax.swing.JPanel jPanel6;
	javax.swing.JPanel jPanel7;
	javax.swing.JPanel jPanel8;
	javax.swing.JPanel jPanel9;
	javax.swing.JScrollPane jScrollPane1;
	javax.swing.JScrollPane jScrollPane2;
	javax.swing.JScrollPane jScrollPane3;
	javax.swing.JScrollPane jScrollPane4;
	javax.swing.JScrollPane jScrollPane5;
	javax.swing.JScrollPane jScrollPane6;
	javax.swing.JScrollPane jScrollPane7;
	javax.swing.JScrollPane jScrollPane8;
	javax.swing.JScrollPane jScrollPane9;
	javax.swing.JSeparator jSeparator1;
	javax.swing.JSeparator jSeparator2;
	javax.swing.JTabbedPane jTabbedPane1;
	javax.swing.JTable jTable3;
	javax.swing.JTable jTable4;
	javax.swing.JTable jTable5;
	javax.swing.JTable jTable6;
	javax.swing.JTable jTable7;
	javax.swing.JLabel lblPagina;
	javax.swing.JLabel lblTitulo;
	javax.swing.JLabel lblTitulo1;
	javax.swing.JSlider slPaginacao;
	javax.swing.JTable tblNotas;
	javax.swing.JTable tblProdutos;
	javax.swing.JTable tblVencimentos;
	javax.swing.JTextField txtBaseCalculo;
	javax.swing.JTextField txtCofinsNota;
	javax.swing.JFormattedTextField txtDataVencimento;
	javax.swing.JTextField txtEBairro;
	javax.swing.JTextField txtECep;
	javax.swing.JTextField txtECidade;
	javax.swing.JFormattedTextField txtECnpj;
	javax.swing.JTextField txtEComplemento;
	javax.swing.JTextField txtENumero;
	javax.swing.JTextField txtERazaoSocial;
	javax.swing.JTextField txtERua;
	javax.swing.JFormattedTextField txtETel;
	javax.swing.JTextField txtEemail;
	javax.swing.JTextField txtEestado;
	javax.swing.JFormattedTextField txtEie;
	javax.swing.JTextField txtIcmsDeson;
	javax.swing.JTextField txtIcmsNota;
	javax.swing.JTextArea txtInfoAdic;
	javax.swing.JTextField txtIpi;
	javax.swing.JTextField txtNomeProduto;
	javax.swing.JTextField txtNumeroNota;
	javax.swing.JTextField txtNumeroNota1;
	javax.swing.JTextField txtNumeroNota2;
	javax.swing.JTextField txtNumeroNota3;
	javax.swing.JTextField txtNumeroNota4;
	javax.swing.JTextField txtPisNota;
	javax.swing.JTextField txtQuantidadeProduto;
	javax.swing.JTextField txtTBairro;
	javax.swing.JTextField txtTCep;
	javax.swing.JTextField txtTCidade;
	javax.swing.JFormattedTextField txtTCnpj;
	javax.swing.JTextField txtTComplemento;
	javax.swing.JTextField txtTNumero;
	javax.swing.JTextField txtTRazaoSocial;
	javax.swing.JTextField txtTRua;
	javax.swing.JFormattedTextField txtTTel;
	javax.swing.JTextField txtTemail;
	javax.swing.JTextField txtTestado;
	javax.swing.JFormattedTextField txtTie;
	javax.swing.JTextField txtTotal;
	javax.swing.JTextField txtValorProduto;
	javax.swing.JFormattedTextField txtValorVencimento;
	// End of variables declaration

}
