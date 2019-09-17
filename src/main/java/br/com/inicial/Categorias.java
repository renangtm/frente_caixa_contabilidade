package br.com.inicial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultFormatterFactory;

import br.com.afgtec.base.ET;
import br.com.afgtec.base.Masks;
import br.com.afgtec.pessoa.Usuario;
import br.com.afgtec.produto.Categoria;
import br.com.agrofauna.utilidades.ListModelGenerica;
import br.com.agrofauna.utilidades.ProvedorDeEventos;
import br.com.entidades.Icones;

public class Categorias extends Modulo{


	private static final long serialVersionUID = 1L;

	public Usuario operador;
	
	private Categoria categoria;
	
	private ListModelGenerica<Categoria> mdlCategorias;
	
	public Categorias() {
		
		super();
		
		this.inicializarComponentes();
		
		
	}
	
	private void setCategoria(Categoria cat) {
		
		EntityManager et = ET.nova();
		
		this.categoria = cat;
		
		et.detach(cat);
		
		this.txtCofins.setValue(cat.getCofins());
		this.txtNome.setText(cat.getNome());
		this.txtPis.setValue(cat.getPis());
		this.txtReducaoBaseCalculo1.setValue(cat.getBase_calculo());
		this.cboApareceLoja.setSelected(cat.isLoja_virtual());
		this.txtIpi.setValue(cat.getIpi());
		this.txtIcms.setValue(cat.getIcms());
		this.lblCodigo.setText(cat.getId()+"");
		
		this.txtNome.requestFocus();
		
		if(cat.getId() == 0) {
			
			this.btNova.setEnabled(false);
			
		}else {
			
			this.btNova.setEnabled(true);
			
		}
		
	}
	
	private void refreshModel() {
		
		EntityManager et = ET.nova();
	
		et.refresh(operador.getEmpresa());
		
		this.mdlCategorias = new ListModelGenerica<Categoria>(
				operador.getEmpresa().getCategorias(),
				Categoria.class,
				new ProvedorDeEventos<Categoria>() {

			@Override
			public void atualizar(List<Categoria> lista, Categoria elemento) throws Exception {
				
				EntityManager et = ET.nova();
				et.getTransaction().begin();
				et.merge(elemento);
				et.getTransaction().commit();
				
			}

			@Override
			public void deletar(List<Categoria> lista, Categoria elemento) throws Exception {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void cadastrar(List<Categoria> lista, Categoria elemento) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.tblCategorias.setModel(mdlCategorias);
		
	}
	
	public void init(Usuario operador){

		this.setTitle(operador.getEmpresa().getNome()+" - Operador: "+operador.getNome()+" - Categorias");
		
		this.operador = operador;

		this.refreshModel();
		
		
		
		this.tblCategorias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
				if(!arg0.getValueIsAdjusting() && tblCategorias.getSelectedRow()>=0) {
					
					Categoria cat = mdlCategorias.getListaBase().get(tblCategorias.getSelectedRow());
					
					setCategoria(cat);
					
				}
				
			}
			
		});
		
		this.btNova.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Categoria cat = new Categoria();
				cat.setEmpresa(operador.getEmpresa());
				
				setCategoria(cat);
				
			}
			
		});
		
		this.btAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!validarFormulario())return;
				
				EntityManager em = ET.nova();
				
				em.getTransaction().begin();
				
				boolean insercao = categoria.getId()==0;
				
				categoria = em.merge(categoria);
				
				categoria.setCofins(Masks.moeda(txtCofins.getText()));
				categoria.setNome(txtNome.getText());
				categoria.setPis(Masks.moeda(txtPis.getText()));
				categoria.setIcms(Masks.moeda(txtIcms.getText()));
				categoria.setBase_calculo(Masks.moeda(txtReducaoBaseCalculo1.getText()));
				categoria.setLoja_virtual(cboApareceLoja.isSelected());
				categoria.setIpi(Masks.moeda(txtIpi.getText()));
				
				em.getTransaction().commit();
				
				if(insercao) {
					
					mdlCategorias.add(categoria);
					setCategoria(categoria);
					
				}else {
					
					refreshModel();
					
				}
				
				info("Confirmado com sucesso");
				
			}
			
			
		});

		this.btNova.doClick();
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
				if(arg0.getKeyCode() == KeyEvent.VK_F5) {
					
					refreshModel();
					
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	public static ImageIcon logo() {

		try {
			return Icones.getLotes();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Categorias";

	}
	
	public void inicializarComponentes(){

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
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategorias = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        cboApareceLoja = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtIcms = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCofins = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        txtIpi = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtReducaoBaseCalculo1 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPis = new javax.swing.JFormattedTextField();
        btAlterar = new javax.swing.JToggleButton();
        btNova = new javax.swing.JToggleButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        setBounds(new java.awt.Rectangle(0, 0, 490, 500));
        setMinimumSize(new java.awt.Dimension(490, 500));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Categorias de Produto");

        tblCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblCategorias);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Categoria"));

        jLabel2.setText("Código:");

        lblCodigo.setText("0");

        jLabel3.setText("Nome:");

        cboApareceLoja.setText("Loja Virtual ?");
        cboApareceLoja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cboApareceLoja, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(lblCodigo))
                    .addComponent(cboApareceLoja, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações físcais"));

        jLabel4.setText("Icms(%):");

        jLabel5.setText("Reducao base calculo (%):");

        jLabel6.setText("Ipi  (%):");

        jLabel7.setText("Cofins(%):");

        jLabel8.setText("Pis(%):");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIcms, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(txtReducaoBaseCalculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIpi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCofins, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPis, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(190, Short.MAX_VALUE)
                    .addGap(139, 139, 139)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtIcms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCofins, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtReducaoBaseCalculo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtPis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addContainerGap(51, Short.MAX_VALUE)))
        );

        btAlterar.setText("Confirmar");

        btNova.setText("Nova");
        btNova.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
               
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btNova, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btNova, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(442, 438));
        setLocationRelativeTo(null);

        
        
        this.txtCofins.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
        this.txtIcms.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
        this.txtPis.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
        this.txtIpi.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
        this.txtReducaoBaseCalculo1.setFormatterFactory(new DefaultFormatterFactory(Masks.moeda()));
        
        
    }
	
	javax.swing.JToggleButton btAlterar;
    javax.swing.JToggleButton btNova;
    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.ButtonGroup buttonGroup2;
    javax.swing.JCheckBox cboApareceLoja;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel5;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JScrollPane jScrollPane4;
    javax.swing.JScrollPane jScrollPane5;
    javax.swing.JScrollPane jScrollPane6;
    javax.swing.JScrollPane jScrollPane7;
    javax.swing.JScrollPane jScrollPane8;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JSeparator jSeparator2;
    javax.swing.JTable tblCategorias;
    javax.swing.JTable jTable3;
    javax.swing.JTable jTable4;
    javax.swing.JTable jTable5;
    javax.swing.JTable jTable6;
    javax.swing.JTable jTable7;
    javax.swing.JLabel lblCodigo;
    javax.swing.JFormattedTextField txtCofins;
    javax.swing.JFormattedTextField txtIcms;
    javax.swing.JFormattedTextField txtIpi;
    javax.swing.JTextField txtNome;
    javax.swing.JFormattedTextField txtPis;
    javax.swing.JFormattedTextField txtReducaoBaseCalculo1;
	
}
