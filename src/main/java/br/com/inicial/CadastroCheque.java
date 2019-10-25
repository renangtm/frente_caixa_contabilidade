package br.com.inicial;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.base.ET;
import br.com.base.Masks;
import br.com.base.Resources;
import br.com.cheque.Cheque;
import br.com.cheque.ChequeListener;
import br.com.cheque.ChequeService;
import br.com.cheque.RepresentadorCheque;
import br.com.conversores.ConversorCalendar;
import br.com.conversores.ConversorDate;
import br.com.empresa.Empresa;
import br.com.movimento_financeiro.Movimento;
import br.com.movimento_financeiro.MovimentoService;
import br.com.movimento_financeiro.RepresentadorMovimento;
import br.com.nota.FormaPagamentoNota;
import br.com.pessoa.Pessoa;
import br.com.pessoa.PessoaService;
import br.com.pessoa.RepresentadorPessoa;
import br.com.usuario.Usuario;
import br.com.utilidades.GerenciadorLista;

import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;

public class CadastroCheque extends Modulo {

	private GerenciadorLista<Cheque> lstCheque;

	private Cheque cheque;

	private Movimento movimento;

	private Pessoa pessoa;

	private List<Pagamento> pagamentos;

	private SelecaoFormaPagamento selecaoFP;

	private FrenteCaixa fc;

	private JFormattedTextField txtValorCheque;

	private JFormattedTextField txtDataCheque;

	private JCheckBox chkRecebido;

	private ChequeListener listener;

	public static void main(String[] args) {

		EntityManager et = ET.nova();

		Usuario usu = et.find(Usuario.class, 1);

		CadastroCheque cc = new CadastroCheque();
		cc.init(usu);

		cc.setVisible(true);

	}

	public ChequeListener getListener() {
		return listener;
	}

	public void setListener(ChequeListener listener) {
		this.listener = listener;
	}

	public CadastroCheque(List<Pagamento> pagamentos, SelecaoFormaPagamento este, FrenteCaixa fc, EntityManager et) {

		this();

		this.btMovimento.setEnabled(false);
		this.btNovoCheque.setEnabled(false);

		this.et.close();
		this.et = et;

		this.pagamentos = pagamentos;
		this.selecaoFP = este;
		this.fc = fc;

	}

	public CadastroCheque(Movimento movimento) {

		this();

		this.btMovimento.setEnabled(false);
		this.btNovoCheque.setEnabled(false);

		this.movimento = movimento;

		this.pessoa = movimento.getVencimento().getNota().getDestinatario();

	}

	public static ImageIcon logo() {

		try {

			return Resources.getCotacoes();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public static String nome() {

		return "Cheques";

	}

	private void exibirCheque() {

		this.txtNumero.setText("");
		this.txtDataCheque.setText("");
		this.txtResponsavel.setText("");
		this.txtValorCheque.setText("");
		this.txtMovimento.setText("");
		this.chkRecebido.setSelected(false);

		if (this.cheque != null) {

			this.txtNumero.setText(this.cheque.getNumero());
			System.out.println(new ConversorDate().paraString(this.cheque.getData())+"--------------");
			this.txtDataCheque.setText(new ConversorDate().paraString(this.cheque.getData()));
			this.chkRecebido.setSelected(this.cheque.isRecebido());
			this.txtValorCheque.setValue(this.cheque.getValor());

		}

		if (this.pessoa != null) {

			this.txtResponsavel.setText(this.pessoa.getNome());

		}

		if (this.movimento != null) {

			this.txtMovimento.setText("Doc " + this.movimento.getVencimento().getNota().getNumero() + " R$ "
					+ this.movimento.getVencimento().getValor());

		}

	}

	private Usuario operador;
	private Empresa empresa;

	private ChequeService serv;

	private JSlider slPg;

	private JLabel lblPg;

	private JButton btNovoCheque;

	private JButton btConfirmar;

	private JButton btMovimento;

	private JButton btResponsavel;

	public void init(Usuario usu) {

		this.operador = this.et.merge(usu);
		this.empresa = this.et.merge(this.operador.getPf().getEmpresa());

		this.serv = new ChequeService(this.et);
		this.serv.setEmpresa(this.empresa);

		this.lstCheque = new GerenciadorLista<Cheque>(Cheque.class, this.tblCheques, this.serv, null, new Cheque(),
				RepresentadorCheque.class);
		this.lstCheque.setLblSlider(this.slPg);
		this.lstCheque.setLblPagina(this.lblPg);
		this.lstCheque.atualizar();

		this.lstCheque.setFiltro(this.txtPesquisar);

		this.exibirCheque();

		this.tblCheques.getSelectionModel().addListSelectionListener(a -> {

			if (!a.getValueIsAdjusting()) {

				if (this.tblCheques.getSelectedRow() >= 0) {

					Cheque cheque = this.lstCheque.getModel().getListaBase().get(this.tblCheques.getSelectedRow());

					this.cheque = cheque;
					this.movimento = cheque.getMovimento();
					this.pessoa = cheque.getPessoa();

					this.exibirCheque();

				}

			}

		});

		this.btNovoCheque.addActionListener(a -> {

			Cheque c = new Cheque();
			c.setData(Calendar.getInstance());

			this.movimento = null;
			this.pessoa = null;
			this.cheque = c;

			this.exibirCheque();

		});

		if (this.movimento == null && this.cheque == null && this.pessoa == null && this.fc == null) {
			
			this.btNovoCheque.doClick();
			
		}else if(this.fc != null) {
			
			this.cheque = new Cheque();
			
		}

		this.btMovimento.addActionListener(a -> {

			MovimentoService ms = new MovimentoService(this.et);
			ms.setEmpresa(this.empresa);
			ms.setFormaPagamentoNota(FormaPagamentoNota.CHEQUE);

			Seletor<Movimento> sm = new Seletor<Movimento>(Movimento.class, ms, RepresentadorMovimento.class, m -> {

				this.movimento = m;
				this.txtMovimento.setText(m.getId() + " -- " + m.getValor());

			}, this);

			sm.setVisible(true);

		});

		this.btResponsavel.addActionListener(a -> {

			PessoaService ps = new PessoaService(this.et);
			ps.setEmpresa(this.empresa);

			Seletor<Pessoa> sp = new Seletor<Pessoa>(Pessoa.class, ps, RepresentadorPessoa.class, p -> {

				this.pessoa = p;
				this.txtResponsavel.setText(p.getNome());

			}, this);

			sp.setVisible(true);

		});

		this.btConfirmar.addActionListener(a -> {

			if (this.movimento == null && this.fc == null) {

				erro("Selecione um movimento");
				return;

			}

			if (this.pessoa == null) {

				erro("Selecione uma pessoa");
				return;

			}

			if (!vc(this.txtDataCheque) || !vc(this.txtNumero) || !vc(this.txtValorCheque)) {

				return;

			}

			try {

				this.cheque.setData(new ConversorCalendar().paraObjeto(this.txtDataCheque.getText()));

			} catch (Exception e) {

				e.printStackTrace();
				erro("Preencha a data corretamente");
				this.txtDataCheque.requestFocus();
				return;

			}

			this.cheque.setNumero(this.txtNumero.getText());

			this.cheque.setValor(((Number) this.txtValorCheque.getValue()).doubleValue());

			this.cheque.setRecebido(this.chkRecebido.isSelected());

			this.cheque.setPessoa(pessoa);

			this.cheque.setMovimento(movimento);

			if (this.fc != null) {

				Pagamento pg = new Pagamento();
				pg.cheque = this.cheque;
				pg.valor = this.cheque.getValor();
				pg.data = this.cheque.getData();
				pg.formaPagamento = FormaPagamentoNota.CHEQUE;

				this.pagamentos.add(pg);

				this.selecaoFP.atualizar();
				this.fc.setVisible(true);
				this.selecaoFP.setVisible(true);

				dispose();
				return;

			}

			this.cheque = new ChequeService(this.et).merge(this.cheque);

			this.et.getTransaction().begin();
			this.et.getTransaction().commit();

			if (this.listener != null) {

				this.listener.onSelect(this.cheque);
				this.dispose();
				return;
				
			}
			
			info("Cadastrado com sucesso");
			this.lstCheque.atualizar();
			
		});

		this.setVisible(true);

	}

	public CadastroCheque() {

		setTitle("Cadastro de Cheques");
		setResizable(false);

		this.setBounds(0, 0, 652, 505);

		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Cheque");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblNewLabel.setBounds(10, 11, 371, 31);
		getContentPane().add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 53, 540, 2);
		getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Dados do Cheque", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 66, 614, 195);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("N\u00BA do cheque:");
		lblNewLabel_1.setBounds(10, 25, 79, 14);
		panel.add(lblNewLabel_1);

		txtNumero = new JTextField();
		txtNumero.setBounds(95, 22, 127, 20);
		panel.add(txtNumero);
		txtNumero.setColumns(10);

		JLabel lblDataDoCheque = new JLabel("Data:");
		lblDataDoCheque.setBounds(10, 61, 40, 14);
		panel.add(lblDataDoCheque);

		txtDataCheque = new JFormattedTextField();
		txtDataCheque.setBounds(60, 58, 162, 20);
		panel.add(txtDataCheque);

		Masks.data().install(txtDataCheque);

		JLabel lblResponsavel = new JLabel("Responsavel:");
		lblResponsavel.setBounds(10, 98, 72, 14);
		panel.add(lblResponsavel);

		txtResponsavel = new JTextField();
		txtResponsavel.setBackground(Color.LIGHT_GRAY);
		txtResponsavel.setEnabled(false);
		txtResponsavel.setEditable(false);
		txtResponsavel.setColumns(10);
		txtResponsavel.setBounds(82, 95, 167, 20);
		panel.add(txtResponsavel);

		btResponsavel = new JButton("...");
		btResponsavel.setBounds(259, 94, 40, 23);
		panel.add(btResponsavel);

		txtValorCheque = new JFormattedTextField();
		txtValorCheque.setBounds(373, 22, 139, 20);
		panel.add(txtValorCheque);
		txtValorCheque.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(323, 25, 40, 14);
		panel.add(lblValor);

		chkRecebido = new JCheckBox("Recebido");
		chkRecebido.setBounds(323, 57, 238, 23);
		panel.add(chkRecebido);

		JLabel lblMovimento = new JLabel("Movimento:");
		lblMovimento.setBounds(323, 98, 72, 14);
		panel.add(lblMovimento);

		txtMovimento = new JTextField();
		txtMovimento.setEnabled(false);
		txtMovimento.setEditable(false);
		txtMovimento.setColumns(10);
		txtMovimento.setBackground(Color.LIGHT_GRAY);
		txtMovimento.setBounds(414, 95, 139, 20);
		panel.add(txtMovimento);

		btMovimento = new JButton("...");
		btMovimento.setBounds(563, 94, 40, 23);
		panel.add(btMovimento);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(448, 146, 155, 35);
		panel.add(btConfirmar);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 135, 593, 2);
		panel.add(separator_1);

		btNovoCheque = new JButton("Novo Cheque");
		btNovoCheque.setBounds(283, 146, 155, 35);
		panel.add(btNovoCheque);

		JLabel lblPesquisar = new JLabel("Pesquisar:");
		lblPesquisar.setBounds(10, 272, 72, 14);
		getContentPane().add(lblPesquisar);

		txtPesquisar = new JTextField();
		txtPesquisar.setBounds(92, 269, 335, 20);
		getContentPane().add(txtPesquisar);
		txtPesquisar.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 297, 614, 132);
		getContentPane().add(scrollPane);

		tblCheques = new JTable();
		scrollPane.setViewportView(tblCheques);

		lblPg = new JLabel("Pg. 0");
		lblPg.setBounds(552, 446, 72, 14);
		getContentPane().add(lblPg);

		slPg = new JSlider();
		slPg.setBounds(180, 440, 362, 23);
		getContentPane().add(slPg);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNumero;
	private JTextField txtResponsavel;
	private JTextField txtMovimento;
	private JTextField txtPesquisar;
	private JTable tblCheques;
}
