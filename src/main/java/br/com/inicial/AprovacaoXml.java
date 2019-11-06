package br.com.inicial;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;

import br.com.base.CFG;
import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.capturaXML.RepresentadorXML;
import br.com.capturaXML.TipoVisto;
import br.com.capturaXML.XML;
import br.com.capturaXML.XMLService;
import br.com.empresa.Empresa;
import br.com.endereco.Cidade;
import br.com.endereco.Endereco;
import br.com.endereco.Estado;
import br.com.fornecedor.Fornecedor;
import br.com.nota.CodigoRegimeTributario;
import br.com.nota.ModeloNota;
import br.com.nota.Nota;
import br.com.nota.ProdutoNota;
import br.com.nota.SaidaEntrada;
import br.com.nota.StatusNota;
import br.com.nota.TipoNota;
import br.com.nota.Vencimento;
import br.com.pessoa.PessoaJuridica;
import br.com.procNFe.TNFe.InfNFe.Cobr.Dup;
import br.com.procNFe.TNFe.InfNFe.Det;
import br.com.procNFe.TNfeProc;
import br.com.transportadora.Transportadora;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class AprovacaoXml extends Modulo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tblNcm;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Aprovacao de XML";

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

					AprovacaoXml frame = new AprovacaoXml();
					frame.setVisible(true);
					frame.init(u);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("unused")
	private Usuario operador;

	private Empresa empresa;

	private JButton btManifestar;

	private ListModelGenerica<XML> model;
	private JButton btnOperacaoNaoRealizada;
	private JButton btnDesconhecimentoDaOperacao;

	@Override
	public void init(Usuario u) {

		this.operador = et.merge(u);
		this.empresa = et.merge(CFG.empresa);

		this.btManifestar.setEnabled(false);
		this.btnDesconhecimentoDaOperacao.setEnabled(false);
		this.btnOperacaoNaoRealizada.setEnabled(false);

		this.tblNcm.getSelectionModel().addListSelectionListener(a -> {

			if (!a.getValueIsAdjusting()) {

				if (this.tblNcm.getSelectedRow() >= 0) {

					this.btManifestar.setEnabled(true);
					this.btnDesconhecimentoDaOperacao.setEnabled(true);
					this.btnOperacaoNaoRealizada.setEnabled(true);

				}

			}

		});

		this.btManifestar.addActionListener(a -> {

			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());
			/*
			try {

				Object obj = xml.getObjetoReferente();

				TNfeProc nota = (TNfeProc) obj;

				@SuppressWarnings("unchecked")
				Object fornecedorObj = et.createQuery("SELECT pj FROM PessoaJuridica pj WHERE cnpj = :cnpj")
						.setParameter("cnpj", Masks.cnpj().valueToString(nota.getNFe().getInfNFe().getEmit().getCNPJ()))
						.getResultStream().findFirst().orElse(null);

				if (fornecedorObj == null) {

					PessoaJuridica f = new PessoaJuridica();
					f.setInscricao_estadual(nota.getNFe().getInfNFe().getEmit().getIE());
					f.setCnpj(Masks.cnpj().valueToString(nota.getNFe().getInfNFe().getEmit().getCNPJ()));
					f.setNome(nota.getNFe().getInfNFe().getEmit().getXNome());
					f.setEmpresa(this.empresa);
					f.setCrt(CodigoRegimeTributario.REGIME_NORMAL);

					Endereco e = new Endereco();
					e.setBairro(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro());
					e.setCep(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP());
					e.setComplemento(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXCpl());
					e.setNumero(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getNro());
					e.setRua(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr());

					@SuppressWarnings("unchecked")
					Optional<Cidade> cid = et.createQuery("SELECT c FROM Cidade c WHERE c.nome like '%:nome%'")
							.setParameter("nome", nota.getNFe().getInfNFe().getEmit().getEnderEmit().getCMun())
							.getResultStream().findFirst();

					Cidade c = null;

					if (cid.isPresent()) {

						c = cid.get();

					} else {

						Estado est = (Estado) et.createQuery("SELECT e FROM Estado e WHERE e.sigla = :sigla")
								.setParameter("sigla",
										nota.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().toString())
								.getResultList().get(0);

						c = new Cidade();
						c.setEstado(est);
						c.setNome(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun());

						et.persist(c);

					}

					e.setCidade(c);

					et.persist(e);

					f.setEndereco(e);

					et.persist(f);

					fornecedorObj = f;

				}

				PessoaJuridica fornecedor = (PessoaJuridica) fornecedorObj;

				if (fornecedor.getFornecedor() == null) {

					Fornecedor f = new Fornecedor();
					f.setPj(fornecedor);

					fornecedor.setFornecedor(f);

					et.persist(f);

				}

				Fornecedor f = fornecedor.getFornecedor();

				Nota n = new Nota();

				n.setChave(nota.getNFe().getInfNFe().getId().split("e")[1]);
				n.setData_saida_entrada(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(nota.getNFe().getInfNFe().getIde().getDhSaiEnt())
						.toGregorianCalendar());
				n.setData_emissao(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(nota.getNFe().getInfNFe().getIde().getDhSaiEnt())
						.toGregorianCalendar());
				n.setDestinatario(this.empresa.getPj());
				n.setEmitente(f.getPJ());
				n.setModelo(ModeloNota.NFE);
				n.setNatureza_operacao(nota.getNFe().getInfNFe().getIde().getNatOp());
				n.setNumero(Integer.parseInt(nota.getNFe().getInfNFe().getIde().getNNF()));
				n.setObservacoes(nota.getNFe().getInfNFe().getInfAdic().getInfCpl());
				n.setOperacao(SaidaEntrada.ENTRADA);
				n.setProtocolo(nota.getNFe().getInfNFe().getIde().getProcEmi());
				n.setSerie(Integer.parseInt(nota.getNFe().getInfNFe().getIde().getSerie()));
				n.setTipo(TipoNota.NORMAL);
				n.setStatus(StatusNota.ATIVA);

				@SuppressWarnings("unchecked")
				Object transportadoraObj = et.createQuery("SELECT pj FROM PessoaJuridica pj WHERE cnpj = :cnpj")
						.setParameter("cnpj",
								Masks.cnpj()
										.valueToString(nota.getNFe().getInfNFe().getTransp().getTransporta().getCNPJ()))
						.getResultStream().findFirst().orElse(null);

				if (transportadoraObj == null) {

					try {

						PessoaJuridica t = new PessoaJuridica();
						t.setInscricao_estadual(nota.getNFe().getInfNFe().getTransp().getTransporta().getIE());
						t.setCnpj(Masks.cnpj()
								.valueToString(nota.getNFe().getInfNFe().getTransp().getTransporta().getCNPJ()));
						t.setNome(nota.getNFe().getInfNFe().getTransp().getTransporta().getXNome());
						t.setEmpresa(this.empresa);
						t.setCrt(CodigoRegimeTributario.REGIME_NORMAL);

						Endereco e = new Endereco();
						e.setBairro(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro());
						e.setCep(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP());
						e.setComplemento(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXCpl());
						e.setNumero(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getNro());
						e.setRua(nota.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr());

						@SuppressWarnings("unchecked")
						Optional<Cidade> cid = et.createQuery("SELECT c FROM Cidade c WHERE c.nome like '%:nome%'")
								.setParameter("nome",
										nota.getNFe())
								.getResultStream().findFirst();

						Cidade c = null;

						if (cid.isPresent()) {

							c = cid.get();

						} else {

							Estado est = (Estado) et.createQuery("SELECT e FROM Estado e WHERE e.sigla = :sigla")
									.setParameter("sigla",
											nota.getNFe().getInfNFe().getTransp().getTransporta().getUF().toString())
									.getResultList().get(0);

							c = new Cidade();
							c.setEstado(est);
							c.setNome(nota.getNFe().getInfNFe().getTransp().getTransporta().getXMun());

							et.persist(c);

						}

						e.setCidade(c);

						et.persist(e);

						t.setEndereco(e);

						et.persist(t);

						transportadoraObj = f;

					} catch (Exception exx) {

						exx.printStackTrace();

					}

				}

				if (transportadoraObj != null) {

					PessoaJuridica transportadora = (PessoaJuridica) transportadoraObj;

					if (fornecedor.getTransportadora() == null) {

						Transportadora t = new Transportadora();
						t.setPj(fornecedor);

						transportadora.setTransportadora(t);

						et.persist(f);

					}

					Transportadora t = transportadora.getTransportadora();

					n.setTransportadora(t);

				}
				
				List<Vencimento> vencimentos = new ArrayList<Vencimento>();
				
				for(Dup dup:nota.getNFe().getInfNFe().getCobr().getDup()){
					
					Vencimento v = new Vencimento();
					v.setData(DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(dup.getDVenc())
						.toGregorianCalendar());
					
					v.setNota(n);
					v.setValor(Double.parseDouble(dup.getVDup()));
					
					vencimentos.add(v);
					
				}
				
				n.setVencimentos(vencimentos);
				
				List<ProdutoNota> pn = new ArrayList<ProdutoNota>();
				
				for(Det det:nota.getNFe().getInfNFe().getDet()){
					
					
				}
				
				

			} catch (JAXBException | ParseException | DatatypeConfigurationException e) {

				erro("Esse xml esta com problema fale com o fornecedor");
				return;

			}
			
			*/

			xml.setTipoVisto(TipoVisto.IMPORTADA);
			xml.setVisto(true);

			new XMLService(et).merge(xml);

			et.getTransaction().begin();
			et.getTransaction().commit();

			this.model.remover(this.model.getListaBase().indexOf(xml));

		});

		this.btnOperacaoNaoRealizada.addActionListener(a -> {

			alerta("Erro esse documento ja foi manifestado");

			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());

			xml.setVisto(true);

			new XMLService(et).merge(xml);

			et.getTransaction().begin();
			et.getTransaction().commit();

			this.model.remover(this.model.getListaBase().indexOf(xml));

		});

		this.btnDesconhecimentoDaOperacao.addActionListener(a -> {

			alerta("Erro esse documento ja foi manifestado");

			XML xml = this.model.getListaBase().get(this.tblNcm.getSelectedRow());

			xml.setVisto(true);

			new XMLService(et).merge(xml);

			et.getTransaction().begin();
			et.getTransaction().commit();

			this.model.remover(this.model.getListaBase().indexOf(xml));

		});

		List<XML> xmls = new XMLService(et).getXMLsNaoVistadosUsuario(this.empresa);

		for (XML xml : xmls) {

			if (xml.getChaveDocumentoReferenciado() == null)
				continue;

			for (XML xml2 : xmls) {

				if (xml2.getChave() == null)
					continue;

				if (xml2.getChave().replaceAll("&asp", "")
						.equals(xml.getChaveDocumentoReferenciado().replaceAll("&asp", ""))) {

					xml2.setAssociado(xml);

				}

			}

		}

		this.model = new ListModelGenerica<XML>(xmls, XML.class, RepresentadorXML.class);

		this.tblNcm.setModel(this.model);

	}

	/**
	 * Create the frame.
	 */

	public AprovacaoXml() {
		setTitle("Aprovacao de XML");
		setResizable(false);
		setBounds(100, 100, 661, 422);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Aprovacao de XML");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 33);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 635, 2);
		contentPane.add(separator);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 635, 288);
		contentPane.add(scrollPane);

		tblNcm = new JTable();
		scrollPane.setViewportView(tblNcm);

		btManifestar = new JButton("Confirmacao da Operacao");
		btManifestar.setBounds(468, 359, 177, 23);
		contentPane.add(btManifestar);

		btnOperacaoNaoRealizada = new JButton("Operacao nao Realizada");
		btnOperacaoNaoRealizada.setBounds(281, 359, 177, 23);
		contentPane.add(btnOperacaoNaoRealizada);

		btnDesconhecimentoDaOperacao = new JButton("Desconhecimento da Operacao");
		btnDesconhecimentoDaOperacao.setBounds(47, 359, 224, 23);
		contentPane.add(btnDesconhecimentoDaOperacao);

	}
}
