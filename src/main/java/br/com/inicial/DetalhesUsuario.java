package br.com.inicial;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;

import br.com.base.ET;
import br.com.base.Resources;
import br.com.usuario.Permissao;
import br.com.usuario.PresetPermissao;
import br.com.usuario.TipoPermissao;
import br.com.usuario.Usuario;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class DetalhesUsuario extends Tela {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}
	
	public void centralizar() {

		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension size = tk.getScreenSize();

		this.setLocation((int) (size.getWidth() / 2 - this.getWidth() / 2),
				(int) (size.getHeight() / 2 - this.getHeight() / 2));

	}

	public static String nome() {

		return "Detalhes do Usuario";

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

		Usuario usu = et.find(Usuario.class, 1);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					DetalhesUsuario frame = new DetalhesUsuario();
					frame.setVisible(true);
					frame.init(usu);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Map<TipoPermissao, JCheckBox[]> lista;

	private JLabel lblPermissao;

	private JCheckBox chkIncluir;

	private JCheckBox chkAlterar;

	private JCheckBox chkConsultar;

	private JCheckBox chkDeletar;

	private JComboBox<PresetPermissao> cboCargo;

	private List<Permissao> getPermissoes() {

		List<Permissao> prms = new ArrayList<Permissao>();

		for (TipoPermissao t : this.lista.keySet()) {

			Permissao p = new Permissao();
			p.setTipo(t);
			p.setAlterar(this.lista.get(t)[0].isSelected());
			p.setExcluir(this.lista.get(t)[1].isSelected());
			p.setIncluir(this.lista.get(t)[2].isSelected());
			p.setConsultar(this.lista.get(t)[3].isSelected());

			if (p.isAlterar() || p.isConsultar() || p.isExcluir() || p.isIncluir()) {

				prms.add(p);

			}

		}

		return prms;

	}

	private void showGrid(List<Permissao> permis) {

		if (this.lista == null) {

			this.lista = new HashMap<TipoPermissao, JCheckBox[]>();

			int i = 1;
			for (TipoPermissao t : TipoPermissao.values()) {

				System.out.println("===================");

				JLabel lbl = new JLabel();

				System.out.println(t.toString() + "-----");

				Rectangle rect = new Rectangle(this.lblPermissao.getBounds());
				rect.y += rect.height * i;
				lbl.setBounds(rect);

				lbl.setText(t.toString());

				JCheckBox alt = new JCheckBox();

				rect = new Rectangle(this.chkAlterar.getBounds());
				rect.y += rect.height * i;
				alt.setBounds(rect);

				JCheckBox del = new JCheckBox();

				rect = new Rectangle(this.chkDeletar.getBounds());
				rect.y += rect.height * i;
				del.setBounds(rect);

				JCheckBox inc = new JCheckBox();

				rect = new Rectangle(this.chkIncluir.getBounds());
				rect.y += rect.height * i;
				inc.setBounds(rect);

				JCheckBox cons = new JCheckBox();

				rect = new Rectangle(this.chkConsultar.getBounds());
				rect.y += rect.height * i;
				cons.setBounds(rect);

				panel.add(lbl);
				panel.add(alt);
				panel.add(inc);
				panel.add(del);
				panel.add(cons);

				alt.addActionListener(a -> {
					this.comparar(this.getPermissoes());
				});
				del.addActionListener(a -> {
					this.comparar(this.getPermissoes());
				});
				inc.addActionListener(a -> {
					this.comparar(this.getPermissoes());
				});
				cons.addActionListener(a -> {
					this.comparar(this.getPermissoes());
				});

				this.lista.put(t, new JCheckBox[] { alt, del, inc, cons });

				i++;
			}

		}

		this.lista.values().forEach(c -> {
			for (JCheckBox j : c)
				j.setSelected(false);
		});

		for (Permissao p : permis) {

			JCheckBox[] v = this.lista.get(p.getTipo());

			if (v != null) {

				v[0].setSelected(p.isAlterar());
				v[1].setSelected(p.isExcluir());
				v[2].setSelected(p.isIncluir());
				v[3].setSelected(p.isConsultar());

			}

		}

	}

	private boolean evt = true;

	private JPanel panel;
	private JButton btConfirmar;

	private void comparar(List<Permissao> perms) {

		double i = -1;
		PresetPermissao max = null;

		for (PresetPermissao p : PresetPermissao.values()) {

			double tmp = p.encaixe(perms);

			if (tmp > i) {

				i = tmp;
				max = p;

			}

		}

		evt = false;
		this.cboCargo.setSelectedItem(max);
		evt = true;

	}

	public EntityManager et;
	private JPanel panel_1;
	private JLabel lblNewLabel_1;
	private JLabel lblSenha;
	private JTextField txtLogin;
	private JPasswordField txtSenha;

	public void init(Usuario u) {

		this.cboCargo.setModel(new DefaultComboBoxModel<PresetPermissao>(PresetPermissao.values()));

		this.showGrid(u.getPermissoes());

		this.comparar(u.getPermissoes());

		this.txtLogin.setText(u.getUsuario());

		this.txtSenha.setText(u.getSenha());

		this.cboCargo.addActionListener(a -> {

			if (!evt)
				return;

			this.showGrid(((PresetPermissao) this.cboCargo.getSelectedItem()).getPreset());

		});

		final Usuario us = u;
		this.btConfirmar.addActionListener(a -> {

			us.setUsuario(this.txtLogin.getText());
			us.setSenha(new String(this.txtSenha.getPassword()));

			List<Permissao> permissoes = this.getPermissoes();

			lbl: for (int i = 0; i < us.getPermissoes().size(); i++) {

				Permissao p = us.getPermissoes().get(i);

				for (Permissao pp : permissoes) {
					if (pp.getTipo().equals(p.getTipo())) {

						p.setAlterar(pp.isAlterar());
						p.setExcluir(pp.isExcluir());
						p.setConsultar(pp.isConsultar());
						p.setIncluir(pp.isIncluir());

						continue lbl;

					}
				}

				et.remove(p);
				us.getPermissoes().remove(i);
				i--;

			}

			lbl: for (Permissao pp : permissoes) {

				for (Permissao p : us.getPermissoes()) {

					if (p.getTipo().equals(pp.getTipo()))
						continue lbl;

				}

				pp.setUsuario(us);
				us.getPermissoes().add(pp);

			}

			this.dispose();

		});

	}

	/**
	 * Create the frame.
	 */

	public DetalhesUsuario() {
		setTitle("Detalhes do Usuario");
		setResizable(false);
		setBounds(100, 100, 561, 680);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Permissoes do Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 11, 346, 38);
		contentPane.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 285, 2);
		contentPane.add(separator);

		panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Atribuicao de permissoes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 138, 535, 502);
		contentPane.add(panel);
		panel.setLayout(null);

		lblPermissao = new JLabel("Permissao");
		lblPermissao.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPermissao.setBounds(20, 70, 143, 23);
		panel.add(lblPermissao);

		chkIncluir = new JCheckBox("Incluir");
		chkIncluir.setEnabled(false);
		chkIncluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		chkIncluir.setBounds(161, 70, 87, 23);
		panel.add(chkIncluir);

		chkAlterar = new JCheckBox("Alterar");
		chkAlterar.setEnabled(false);
		chkAlterar.setFont(new Font("Tahoma", Font.BOLD, 11));
		chkAlterar.setBounds(250, 70, 87, 23);
		panel.add(chkAlterar);

		chkConsultar = new JCheckBox("Consultar");
		chkConsultar.setEnabled(false);
		chkConsultar.setFont(new Font("Tahoma", Font.BOLD, 11));
		chkConsultar.setBounds(340, 70, 87, 23);
		panel.add(chkConsultar);

		chkDeletar = new JCheckBox("Deletar");
		chkDeletar.setEnabled(false);
		chkDeletar.setFont(new Font("Tahoma", Font.BOLD, 11));
		chkDeletar.setBounds(429, 70, 87, 23);
		panel.add(chkDeletar);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(20, 59, 1, 2);
		panel.add(separator_1);

		cboCargo = new JComboBox<PresetPermissao>();
		cboCargo.setBounds(92, 28, 214, 20);
		panel.add(cboCargo);

		JLabel lblCargo = new JLabel("Cargo:");
		lblCargo.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCargo.setBounds(10, 27, 72, 21);
		panel.add(lblCargo);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(9, 59, 1, 2);
		panel.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(9, 59, 507, 2);
		panel.add(separator_3);

		btConfirmar = new JButton("Confirmar");
		btConfirmar.setBounds(402, 468, 123, 23);
		panel.add(btConfirmar);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Dados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 51, 535, 84);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		lblNewLabel_1 = new JLabel("Login:");
		lblNewLabel_1.setBounds(22, 24, 46, 14);
		panel_1.add(lblNewLabel_1);

		lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(22, 52, 34, 14);
		panel_1.add(lblSenha);

		txtLogin = new JTextField();
		txtLogin.setBounds(61, 21, 170, 20);
		panel_1.add(txtLogin);
		txtLogin.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(62, 49, 222, 20);
		panel_1.add(txtSenha);

	}
}
