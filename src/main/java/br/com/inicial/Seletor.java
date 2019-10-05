package br.com.inicial;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Service;
import br.com.agrofauna.utilidades.ListModelGenerica;
import br.com.agrofauna.utilidades.Representador;
import br.com.entidades.nota.Nota;
import br.com.entidades.nota.NotaService;
import br.com.entidades.nota.RepresentadorNotaSimples;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Seletor<T> extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtFiltro;
	private JTable tblDados;

	private Class<T> classe;

	private Class<? extends Representador<T>> representador;

	@SuppressWarnings("unused")
	private OnSelect<T> os;

	private Service<T> buscador;

	private ListModelGenerica<T> model;
	private JButton btOk;
	private JButton btCancelar;

	private synchronized void atualizar() {

		if (this.representador != null) {
			this.model = new ListModelGenerica<T>(this.buscador.getElementos(0, 30, this.txtFiltro.getText(), ""),
					this.classe, this.representador, null);
		} else {
			this.model = new ListModelGenerica<T>(this.buscador.getElementos(0, 30, this.txtFiltro.getText(), ""),
					this.classe);
		}

		this.tblDados.setModel(this.model);

	}

	private synchronized void atualizarCodigo() {

		T e = this.buscador.getPeloCodigo(this.txtCodigo.getText());

		if (e == null)
			return;

		if (this.representador != null) {
			this.model = new ListModelGenerica<T>(Arrays.asList(e), this.classe, this.representador, null);
		} else {
			this.model = new ListModelGenerica<T>(Arrays.asList(e), this.classe);
		}
		this.tblDados.setModel(this.model);

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {

			EntityManager et = ET.nova();
			
			Seletor<Nota> dialog = new Seletor<Nota>(Nota.class, new NotaService(et), RepresentadorNotaSimples.class,
					new OnSelect<Nota>() {

						@Override
						public void onSelect(Nota entidade) {
							// TODO Auto-generated method stub

						}

					}, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Seletor(Class<T> classe, Service<T> buscador, Class<? extends Representador<T>> rep, OnSelect<T> os,
			JFrame owner) {
		super(owner);
		setTitle("Selecao de");
		setResizable(false);
		setBounds(100, 100, 315, 300);

		if (owner != null) {

			this.setLocation(
					(int) (owner.getLocation().getX() + owner.getSize().getWidth() / 2 - this.getSize().getWidth() / 2),
					(int) (owner.getLocation().getY() + owner.getSize().getHeight() / 2
							- this.getSize().getHeight() / 2));

		}

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtCodigo = new JTextField();
			txtCodigo.setBackground(Color.LIGHT_GRAY);
			txtCodigo.setBounds(10, 37, 86, 20);
			contentPanel.add(txtCodigo);
			txtCodigo.setColumns(10);
		}
		{
			JLabel lblCdigo = new JLabel("C\u00F3digo");
			lblCdigo.setBounds(10, 12, 46, 14);
			contentPanel.add(lblCdigo);
		}
		{
			txtFiltro = new JTextField();
			txtFiltro.setBounds(106, 37, 194, 20);
			contentPanel.add(txtFiltro);
			txtFiltro.setColumns(10);
		}
		{
			JLabel lblDescricao = new JLabel("Descricao");
			lblDescricao.setBounds(106, 12, 46, 14);
			contentPanel.add(lblDescricao);
		}

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 68, 290, 160);
		contentPanel.add(scrollPane);

		tblDados = new JTable();
		scrollPane.setViewportView(tblDados);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btOk = new JButton("OK");
				btOk.setActionCommand("OK");
				buttonPane.add(btOk);
				getRootPane().setDefaultButton(btOk);

			}
			{
				btCancelar = new JButton("Cancel");
				btCancelar.setActionCommand("Cancel");
				buttonPane.add(btCancelar);
			}
		}

		this.btOk.setEnabled(false);

		this.btCancelar.addActionListener(e -> this.dispose());

		this.classe = classe;

		this.os = os;

		this.buscador = buscador;

		this.representador = rep;

		this.txtFiltro.requestFocus();

		this.txtFiltro.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						atualizar();
					}

				}).start();

			}

		});

		this.txtCodigo.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent arg0) {

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						atualizarCodigo();
					}

				}).start();

			}

		});

		this.txtFiltro.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (model.getListaBase().size() > 0) {

					os.onSelect(model.getListaBase().get(0));
					dispose();

				}

			}

		});

		this.txtCodigo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (model.getListaBase().size() > 0) {

					os.onSelect(model.getListaBase().get(0));
					dispose();

				}

			}

		});

		this.tblDados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {

					btOk.setEnabled(tblDados.getSelectedRow() >= 0);

				}

			}

		});

		this.btOk.addActionListener(e -> {

			T s = model.getListaBase().get(tblDados.getSelectedRow());

			os.onSelect(s);

			dispose();

		});

		this.tblDados.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (arg0.getClickCount() == 2 && tblDados.getSelectedRow() >= 0) {

					T e = model.getListaBase().get(tblDados.getSelectedRow());

					os.onSelect(e);

					dispose();

				}

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.atualizar();

	}
}
