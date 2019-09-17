package br.com.inicial;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;

public class Categoria extends JFrame {

	private JPanel contentPane;
	private JTextField txtNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Categoria frame = new Categoria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Categoria() {
		setTitle("Categorias");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCategorias = new JLabel("Categorias de Produto");
		lblCategorias.setFont(new Font("Arial", Font.PLAIN, 22));
		lblCategorias.setBounds(10, 11, 377, 38);
		contentPane.add(lblCategorias);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(76, 60, 311, -11);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 57, 377, 2);
		contentPane.add(separator_1);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(10, 84, 46, 14);
		contentPane.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(57, 81, 377, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Configura\u00E7\u00E3o de ICMS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 109, 669, 187);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JTabbedPane tpIcms = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tpIcms);
		
		JPanel panel_1 = new JPanel();
		tpIcms.addTab("ICMS00", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblOrigemMercadoria = new JLabel("Origem Mercadoria:");
		lblOrigemMercadoria.setBounds(10, 25, 107, 14);
		panel_1.add(lblOrigemMercadoria);
		
		JLabel lblModalidadeBc = new JLabel("Modalidade BC:");
		lblModalidadeBc.setBounds(10, 50, 107, 14);
		panel_1.add(lblModalidadeBc);
		
		JLabel lblPorcentagemBc = new JLabel("Porcentagem BC(%):");
		lblPorcentagemBc.setBounds(10, 86, 107, 14);
		panel_1.add(lblPorcentagemBc);
		
		JLabel lblPorcentagemIcms = new JLabel("Porcentagem ICMS(%):");
		lblPorcentagemIcms.setBounds(10, 111, 123, 14);
		panel_1.add(lblPorcentagemIcms);
		
		JComboBox cboOrigemMercadoria00 = new JComboBox();
		cboOrigemMercadoria00.setBounds(110, 22, 260, 20);
		panel_1.add(cboOrigemMercadoria00);
		
		JComboBox cboModalidadeBC00 = new JComboBox();
		cboModalidadeBC00.setBounds(110, 50, 213, 20);
		panel_1.add(cboModalidadeBC00);
		
		JFormattedTextField txtPorcentagemBC00 = new JFormattedTextField();
		txtPorcentagemBC00.setBounds(127, 83, 123, 20);
		panel_1.add(txtPorcentagemBC00);
		
		JFormattedTextField txtPorcentagemIcms00 = new JFormattedTextField();
		txtPorcentagemIcms00.setBounds(127, 108, 107, 20);
		panel_1.add(txtPorcentagemIcms00);
		
		JCheckBox chkTabelaIcms00 = new JCheckBox("Tabela de ICMS");
		chkTabelaIcms00.setBounds(516, 21, 130, 23);
		panel_1.add(chkTabelaIcms00);
		
		JPanel panel_2 = new JPanel();
		tpIcms.addTab("ICMS10", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("Origem Mercadoria:");
		label.setBounds(10, 14, 107, 14);
		panel_2.add(label);
		
		JComboBox cboOrigemMercadoria10 = new JComboBox();
		cboOrigemMercadoria10.setBounds(110, 11, 176, 20);
		panel_2.add(cboOrigemMercadoria10);
		
		JComboBox cboModalidadeBC10 = new JComboBox();
		cboModalidadeBC10.setBounds(110, 39, 132, 20);
		panel_2.add(cboModalidadeBC10);
		
		JLabel label_1 = new JLabel("Modalidade BC:");
		label_1.setBounds(10, 39, 107, 14);
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("Porcentagem BC(%):");
		label_2.setBounds(10, 75, 107, 14);
		panel_2.add(label_2);
		
		JFormattedTextField txtPorcentagemBC10 = new JFormattedTextField();
		txtPorcentagemBC10.setBounds(127, 72, 132, 20);
		panel_2.add(txtPorcentagemBC10);
		
		JFormattedTextField txtPorcentagemIcms10 = new JFormattedTextField();
		txtPorcentagemIcms10.setBounds(127, 97, 123, 20);
		panel_2.add(txtPorcentagemIcms10);
		
		JLabel label_3 = new JLabel("Porcentagem ICMS(%):");
		label_3.setBounds(10, 100, 123, 14);
		panel_2.add(label_3);
		
		JCheckBox chkTabelaIcms10 = new JCheckBox("Tabela de ICMS");
		chkTabelaIcms10.setBounds(510, 38, 132, 23);
		panel_2.add(chkTabelaIcms10);
		
		JCheckBox chkIcmsSt10 = new JCheckBox("ICMS ST");
		chkIcmsSt10.setBounds(510, 10, 132, 23);
		panel_2.add(chkIcmsSt10);
		
		JLabel label_4 = new JLabel("Percentual Margem Adcionada ICMS ST:");
		label_4.setBounds(312, 75, 192, 14);
		panel_2.add(label_4);
		
		JFormattedTextField txtReducaoBCIcmsSt10 = new JFormattedTextField();
		txtReducaoBCIcmsSt10.setBounds(448, 97, 140, 20);
		panel_2.add(txtReducaoBCIcmsSt10);
		
		JLabel label_5 = new JLabel("Reducao BC ICMS ST(%):");
		label_5.setBounds(312, 100, 126, 14);
		panel_2.add(label_5);
		
		JFormattedTextField txtPercentualMargemAdicionada10 = new JFormattedTextField();
		txtPercentualMargemAdicionada10.setBounds(520, 72, 122, 20);
		panel_2.add(txtPercentualMargemAdicionada10);
		
		JLabel lblModalidadeBcSt = new JLabel("Modalidade BC ST:");
		lblModalidadeBcSt.setBounds(252, 42, 107, 14);
		panel_2.add(lblModalidadeBcSt);
		
		JComboBox cboModalidadeBCSt10 = new JComboBox();
		cboModalidadeBCSt10.setBounds(346, 39, 149, 20);
		panel_2.add(cboModalidadeBCSt10);
		
		JPanel panel_3 = new JPanel();
		tpIcms.addTab("ICMS20", null, panel_3, null);
		panel_3.setLayout(null);
		
		JFormattedTextField txtPorcentagemIcms20 = new JFormattedTextField();
		txtPorcentagemIcms20.setBounds(127, 105, 123, 20);
		panel_3.add(txtPorcentagemIcms20);
		
		JLabel label_7 = new JLabel("Porcentagem ICMS(%):");
		label_7.setBounds(10, 108, 123, 14);
		panel_3.add(label_7);
		
		JLabel lblReducaoDaBc = new JLabel("Porcentagem Reducao da BC(%):");
		lblReducaoDaBc.setBounds(10, 83, 178, 14);
		panel_3.add(lblReducaoDaBc);
		
		JFormattedTextField txtPorcentagemReducaoBC20 = new JFormattedTextField();
		txtPorcentagemReducaoBC20.setBounds(198, 80, 132, 20);
		panel_3.add(txtPorcentagemReducaoBC20);
		
		JCheckBox chkTabelaIcms20 = new JCheckBox("Tabela de ICMS");
		chkTabelaIcms20.setBounds(510, 18, 132, 23);
		panel_3.add(chkTabelaIcms20);
		
		JComboBox cboModalidadeBC20 = new JComboBox();
		cboModalidadeBC20.setBounds(110, 47, 213, 20);
		panel_3.add(cboModalidadeBC20);
		
		JComboBox cboOrigemMercadoria20 = new JComboBox();
		cboOrigemMercadoria20.setBounds(110, 19, 176, 20);
		panel_3.add(cboOrigemMercadoria20);
		
		JLabel label_10 = new JLabel("Modalidade BC:");
		label_10.setBounds(10, 47, 107, 14);
		panel_3.add(label_10);
		
		JLabel label_11 = new JLabel("Origem Mercadoria:");
		label_11.setBounds(10, 22, 107, 14);
		panel_3.add(label_11);
		
		JPanel panel_4 = new JPanel();
		tpIcms.addTab("ICMS30", null, panel_4, null);
		panel_4.setLayout(null);
		
		JFormattedTextField txtReducaoBCIcmsSt30 = new JFormattedTextField();
		txtReducaoBCIcmsSt30.setBounds(146, 72, 140, 20);
		panel_4.add(txtReducaoBCIcmsSt30);
		
		JLabel label_6 = new JLabel("Reducao BC ICMS ST(%):");
		label_6.setBounds(10, 75, 126, 14);
		panel_4.add(label_6);
		
		JFormattedTextField txtPercentualMargemAdicionadaIcmsSt30 = new JFormattedTextField();
		txtPercentualMargemAdicionadaIcmsSt30.setBounds(468, 97, 122, 20);
		panel_4.add(txtPercentualMargemAdicionadaIcmsSt30);
		
		JLabel label_8 = new JLabel("Percentual Margem Adcionada ICMS ST:");
		label_8.setBounds(260, 100, 192, 14);
		panel_4.add(label_8);
		
		JCheckBox cboTabelaIcms30 = new JCheckBox("Tabela de ICMS");
		cboTabelaIcms30.setBounds(510, 10, 132, 23);
		panel_4.add(cboTabelaIcms30);
		
		JComboBox cboModalidadeBCSt30 = new JComboBox();
		cboModalidadeBCSt30.setBounds(104, 38, 149, 20);
		panel_4.add(cboModalidadeBCSt30);
		
		JLabel label_9 = new JLabel("Modalidade BC ST:");
		label_9.setBounds(10, 41, 107, 14);
		panel_4.add(label_9);
		
		JComboBox cboOrigemMercadoria30 = new JComboBox();
		cboOrigemMercadoria30.setBounds(110, 11, 176, 20);
		panel_4.add(cboOrigemMercadoria30);
		
		JLabel label_12 = new JLabel("Origem Mercadoria:");
		label_12.setBounds(10, 14, 107, 14);
		panel_4.add(label_12);
		
		JFormattedTextField txtPorcentagemIcms30 = new JFormattedTextField();
		txtPorcentagemIcms30.setBounds(127, 97, 123, 20);
		panel_4.add(txtPorcentagemIcms30);
		
		JLabel label_13 = new JLabel("Porcentagem ICMS(%):");
		label_13.setBounds(10, 100, 123, 14);
		panel_4.add(label_13);
		
		JPanel panel_5 = new JPanel();
		tpIcms.addTab("ICMS40", null, panel_5, null);
		panel_5.setLayout(null);
		
		JLabel label_15 = new JLabel("Origem Mercadoria:");
		label_15.setBounds(10, 14, 107, 14);
		panel_5.add(label_15);
		
		JComboBox cboOrigemMercadoria40 = new JComboBox();
		cboOrigemMercadoria40.setBounds(110, 11, 176, 20);
		panel_5.add(cboOrigemMercadoria40);
		
		JPanel panel_6 = new JPanel();
		tpIcms.addTab("ICMS41", null, panel_6, null);
		panel_6.setLayout(null);
		
		JComboBox cboOrigemMercadoria41 = new JComboBox();
		cboOrigemMercadoria41.setBounds(110, 11, 176, 20);
		panel_6.add(cboOrigemMercadoria41);
		
		JLabel label_14 = new JLabel("Origem Mercadoria:");
		label_14.setBounds(10, 14, 107, 14);
		panel_6.add(label_14);
		
		JPanel panel_7 = new JPanel();
		tpIcms.addTab("ICMS50", null, panel_7, null);
		panel_7.setLayout(null);
		
		JComboBox cboOrigemMercadoria50 = new JComboBox();
		cboOrigemMercadoria50.setBounds(110, 11, 176, 20);
		panel_7.add(cboOrigemMercadoria50);
		
		JLabel label_16 = new JLabel("Origem Mercadoria:");
		label_16.setBounds(10, 14, 107, 14);
		panel_7.add(label_16);
		
		JPanel panel_8 = new JPanel();
		tpIcms.addTab("ICMS51", null, panel_8, null);
		panel_8.setLayout(null);
		
		JLabel label_17 = new JLabel("Porcentagem ICMS(%):");
		label_17.setBounds(10, 103, 123, 14);
		panel_8.add(label_17);
		
		JFormattedTextField txtPorcentagemIcms51 = new JFormattedTextField();
		txtPorcentagemIcms51.setBounds(138, 100, 123, 20);
		panel_8.add(txtPorcentagemIcms51);
		
		JFormattedTextField txtPorcentagemReducaoBC51 = new JFormattedTextField();
		txtPorcentagemReducaoBC51.setBounds(198, 69, 132, 20);
		panel_8.add(txtPorcentagemReducaoBC51);
		
		JLabel label_18 = new JLabel("Porcentagem Reducao da BC(%):");
		label_18.setBounds(10, 72, 178, 14);
		panel_8.add(label_18);
		
		JComboBox cboModalidadeBC51 = new JComboBox();
		cboModalidadeBC51.setBounds(110, 36, 213, 20);
		panel_8.add(cboModalidadeBC51);
		
		JLabel label_19 = new JLabel("Modalidade BC:");
		label_19.setBounds(10, 36, 107, 14);
		panel_8.add(label_19);
		
		JLabel lblOrigemMercadoria_1 = new JLabel("Origem Mercadoria:");
		lblOrigemMercadoria_1.setBounds(10, 11, 107, 14);
		panel_8.add(lblOrigemMercadoria_1);
		
		JComboBox cboOrigemMercadoria51 = new JComboBox();
		cboOrigemMercadoria51.setBounds(110, 8, 176, 20);
		panel_8.add(cboOrigemMercadoria51);
		
		JCheckBox chkTabelaIcms51 = new JCheckBox("Tabela de ICMS");
		chkTabelaIcms51.setBounds(510, 7, 132, 23);
		panel_8.add(chkTabelaIcms51);
		
		JFormattedTextField txtPorcentagemDiferimento51 = new JFormattedTextField();
		txtPorcentagemDiferimento51.setBounds(462, 100, 123, 20);
		panel_8.add(txtPorcentagemDiferimento51);
		
		JLabel label_21 = new JLabel("Porcentagem do Diferimento(%):");
		label_21.setBounds(284, 103, 181, 14);
		panel_8.add(label_21);
		
		JPanel panel_9 = new JPanel();
		tpIcms.addTab("ICMS60", null, panel_9, null);
		panel_9.setLayout(null);
		
		JComboBox cboOrigemMercadoria60 = new JComboBox();
		cboOrigemMercadoria60.setBounds(110, 11, 176, 20);
		panel_9.add(cboOrigemMercadoria60);
		
		JLabel label_20 = new JLabel("Origem Mercadoria:");
		label_20.setBounds(10, 14, 107, 14);
		panel_9.add(label_20);
		
		JPanel panel_10 = new JPanel();
		tpIcms.addTab("ICMS70", null, panel_10, null);
		panel_10.setLayout(null);
		
		JFormattedTextField txtPorcentagemIcms70 = new JFormattedTextField();
		txtPorcentagemIcms70.setBounds(127, 94, 123, 20);
		panel_10.add(txtPorcentagemIcms70);
		
		JLabel label_22 = new JLabel("Porcentagem ICMS(%):");
		label_22.setBounds(10, 97, 123, 14);
		panel_10.add(label_22);
		
		JLabel label_23 = new JLabel("Porcentagem BC(%):");
		label_23.setBounds(10, 72, 107, 14);
		panel_10.add(label_23);
		
		JLabel label_24 = new JLabel("Modalidade BC:");
		label_24.setBounds(10, 36, 107, 14);
		panel_10.add(label_24);
		
		JLabel label_25 = new JLabel("Origem Mercadoria:");
		label_25.setBounds(10, 11, 107, 14);
		panel_10.add(label_25);
		
		JComboBox cboOrigemMercadoria70 = new JComboBox();
		cboOrigemMercadoria70.setBounds(110, 8, 176, 20);
		panel_10.add(cboOrigemMercadoria70);
		
		JComboBox cboModalidadeBC70 = new JComboBox();
		cboModalidadeBC70.setBounds(110, 36, 132, 20);
		panel_10.add(cboModalidadeBC70);
		
		JFormattedTextField txtPorcentagemBC70 = new JFormattedTextField();
		txtPorcentagemBC70.setBounds(127, 69, 132, 20);
		panel_10.add(txtPorcentagemBC70);
		
		JLabel label_26 = new JLabel("Modalidade BC ST:");
		label_26.setBounds(252, 39, 107, 14);
		panel_10.add(label_26);
		
		JComboBox cboModalidadeBCSt70 = new JComboBox();
		cboModalidadeBCSt70.setBounds(346, 36, 149, 20);
		panel_10.add(cboModalidadeBCSt70);
		
		JLabel label_27 = new JLabel("Percentual Margem Adcionada ICMS ST:");
		label_27.setBounds(312, 72, 192, 14);
		panel_10.add(label_27);
		
		JLabel label_28 = new JLabel("Reducao BC ICMS ST(%):");
		label_28.setBounds(312, 97, 126, 14);
		panel_10.add(label_28);
		
		JFormattedTextField txtReducaoBCIcmsSt70 = new JFormattedTextField();
		txtReducaoBCIcmsSt70.setBounds(448, 94, 140, 20);
		panel_10.add(txtReducaoBCIcmsSt70);
		
		JFormattedTextField txtPercentualMargemAdicionadaIcmsST70 = new JFormattedTextField();
		txtPercentualMargemAdicionadaIcmsST70.setBounds(520, 69, 122, 20);
		panel_10.add(txtPercentualMargemAdicionadaIcmsST70);
		
		JCheckBox cboTabelaIcms70 = new JCheckBox("Tabela de ICMS");
		cboTabelaIcms70.setBounds(510, 35, 132, 23);
		panel_10.add(cboTabelaIcms70);
		
		JCheckBox cboIcmsST70 = new JCheckBox("ICMS ST");
		cboIcmsST70.setBounds(510, 7, 132, 23);
		panel_10.add(cboIcmsST70);
		
		JPanel panel_11 = new JPanel();
		tpIcms.addTab("ICMS90", null, panel_11, null);
		panel_11.setLayout(null);
		
		JFormattedTextField txtReducaoBcIcmsSt90 = new JFormattedTextField();
		txtReducaoBcIcmsSt90.setBounds(448, 94, 140, 20);
		panel_11.add(txtReducaoBcIcmsSt90);
		
		JLabel label_29 = new JLabel("Reducao BC ICMS ST(%):");
		label_29.setBounds(312, 97, 126, 14);
		panel_11.add(label_29);
		
		JFormattedTextField txtPorcentagemIcms90 = new JFormattedTextField();
		txtPorcentagemIcms90.setBounds(127, 94, 123, 20);
		panel_11.add(txtPorcentagemIcms90);
		
		JLabel label_30 = new JLabel("Porcentagem ICMS(%):");
		label_30.setBounds(10, 97, 123, 14);
		panel_11.add(label_30);
		
		JLabel label_31 = new JLabel("Porcentagem BC(%):");
		label_31.setBounds(10, 72, 107, 14);
		panel_11.add(label_31);
		
		JFormattedTextField txtPorcentagemBc90 = new JFormattedTextField();
		txtPorcentagemBc90.setBounds(127, 69, 132, 20);
		panel_11.add(txtPorcentagemBc90);
		
		JLabel label_32 = new JLabel("Percentual Margem Adcionada ICMS ST:");
		label_32.setBounds(312, 72, 192, 14);
		panel_11.add(label_32);
		
		JFormattedTextField txtMargemAdicionadaIcmsSt90 = new JFormattedTextField();
		txtMargemAdicionadaIcmsSt90.setBounds(520, 69, 122, 20);
		panel_11.add(txtMargemAdicionadaIcmsSt90);
		
		JCheckBox chkTabelaIcms90 = new JCheckBox("Tabela de ICMS");
		chkTabelaIcms90.setBounds(510, 35, 132, 23);
		panel_11.add(chkTabelaIcms90);
		
		JCheckBox chkIcmsSt90 = new JCheckBox("ICMS ST");
		chkIcmsSt90.setBounds(510, 7, 132, 23);
		panel_11.add(chkIcmsSt90);
		
		JComboBox cboModalidadeBCSt90 = new JComboBox();
		cboModalidadeBCSt90.setBounds(346, 36, 149, 20);
		panel_11.add(cboModalidadeBCSt90);
		
		JLabel label_33 = new JLabel("Modalidade BC ST:");
		label_33.setBounds(252, 39, 107, 14);
		panel_11.add(label_33);
		
		JComboBox cboModalidadeBC90 = new JComboBox();
		cboModalidadeBC90.setBounds(110, 36, 132, 20);
		panel_11.add(cboModalidadeBC90);
		
		JLabel label_34 = new JLabel("Modalidade BC:");
		label_34.setBounds(10, 36, 107, 14);
		panel_11.add(label_34);
		
		JLabel label_35 = new JLabel("Origem Mercadoria:");
		label_35.setBounds(10, 11, 107, 14);
		panel_11.add(label_35);
		
		JComboBox cboOrigemMercadoria90 = new JComboBox();
		cboOrigemMercadoria90.setBounds(110, 8, 176, 20);
		panel_11.add(cboOrigemMercadoria90);
		
		JCheckBox chkLojaVirtual = new JCheckBox("Loja Virtual ?");
		chkLojaVirtual.setBounds(440, 80, 239, 23);
		contentPane.add(chkLojaVirtual);
	}
}
