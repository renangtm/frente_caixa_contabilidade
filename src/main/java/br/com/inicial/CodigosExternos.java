package br.com.inicial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.base.Resources;
import br.com.codigo_barra.PadraoCodigo;
import br.com.codigo_barra.PadraoCodigoService;
import br.com.empresa.Empresa;
import br.com.quantificacao.TipoQuantidade;
import br.com.usuario.Usuario;
import br.com.utilidades.ListModelGenerica;
import br.com.utilidades.ProvedorDeEventos;

import java.awt.Dimension;

public class CodigosExternos extends Modulo{


	private static final long serialVersionUID = 1L;

	public Usuario operador;
	
	private PadraoCodigo padrao;
	
	private ListModelGenerica<PadraoCodigo> model;
	
	private void setPadraoCodigo(PadraoCodigo p){
		
		this.padrao = p;
		
		this.btNovo.setEnabled(p.getId() > 0);
		
		this.lblCodigo.setText(padrao.getId()+"");
		this.txtDigitosCodigoProduto.setText(padrao.getDigitosCodigoProduto()+"");
		this.txtDigitosUnidade.setText(padrao.getDigitosUnidade()+"");
		this.txtCasasDecimais.setText(padrao.getCasasDecimais()+"");
		this.txtDigitosIniciais.setText(padrao.getDigitoInicial());
		this.txtNome.setText(padrao.getNome());
		
		this.cboTipoUnidade.setSelectedItem(padrao.getTipo());
		
		this.txtNome.requestFocus();
		
	}
	 
	public CodigosExternos() {
	
		setResizable(false);
		
		this.inicializarComponentes();
		
	}
	
	private void atualizarLista(){
		
		this.model = new ListModelGenerica<PadraoCodigo>(operador.getPf().getEmpresa().getPadroesCodigo(),PadraoCodigo.class,
				new ProvedorDeEventos<PadraoCodigo>(){

					@Override
					public void atualizar(List<PadraoCodigo> lista, PadraoCodigo elemento) throws Exception {
						
						et.getTransaction().begin();
						et.merge(elemento);
						et.getTransaction().commit();
						
					}

					@Override
					public void deletar(List<PadraoCodigo> lista, PadraoCodigo elemento) throws Exception {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void cadastrar(List<PadraoCodigo> lista, PadraoCodigo elemento) throws Exception {
						// TODO Auto-generated method stub
						
					}
			
		});
		this.tblPadroes.setModel(model);
		
	}
	
	private Empresa empresa;
	
	public void init(Usuario operador){

		this.setTitle(operador.getPf().getEmpresa()+" - Operador: "+operador.getPf().getNome()+" - Codigos Externos");
		
		this.operador = et.merge(operador);
		this.empresa = this.operador.getPf().getEmpresa();
		et.detach(this.operador);
		this.empresa = et.merge(this.empresa);
		
		this.atualizarLista();
			
		ComboBoxModel<TipoQuantidade> cboModel = new DefaultComboBoxModel<TipoQuantidade>(TipoQuantidade.values());
		this.cboTipoUnidade.setModel(cboModel);
	
		this.btNovo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				PadraoCodigo pc = new PadraoCodigo();
				pc.setEmpresa(operador.getPf().getEmpresa());
				
				setPadraoCodigo(pc);
				
			}
			
		});
		
		this.btNovo.doClick();
		
		this.btConfirmar.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!validarFormulario())return;
				
				boolean nova = padrao.getId()==0;
				
				try{
					padrao.setCasasDecimais(Integer.parseInt(txtCasasDecimais.getText()));
					padrao.setDigitoInicial(txtDigitosIniciais.getText());
					padrao.setDigitosCodigoProduto(Integer.parseInt(txtDigitosCodigoProduto.getText()));
					padrao.setDigitosUnidade(Integer.parseInt(txtDigitosUnidade.getText()));
					padrao.setTipo((TipoQuantidade)cboTipoUnidade.getSelectedItem());
					padrao.setNome(txtNome.getText());
				}catch(Exception ex){
					erro("Preencha os dados adequadamente");
					return;
				}
				
				padrao = new PadraoCodigoService(et).merge(padrao);
				et.getTransaction().begin();	
				et.getTransaction().commit();
				
				if(nova){
					
					model.add(padrao);
					
				}else{
					
					atualizarLista();
					
				}
				
				info("Operacao efetuada com sucesso");
				
				btNovo.doClick();
				
			}
			
		});
		
		this.tblPadroes.getSelectionModel().addListSelectionListener(
				new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				if(e.getValueIsAdjusting() && tblPadroes.getSelectedRow()>=0){
					
					setPadraoCodigo(model.getListaBase().get(tblPadroes.getSelectedRow()));
					
				}
				
			}
			
		});
		
	}
	
	public static ImageIcon logo() {

		try {
			return Resources.getConfig();
		} catch (IOException e) {
			return null;
		}

	}

	public static String nome() {

		return "Conf. Codigo Externo";

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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPadroes = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtDigitosIniciais = new javax.swing.JTextField();
        txtDigitosCodigoProduto = new javax.swing.JTextField();
        txtDigitosUnidade = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCasasDecimais = new javax.swing.JTextField();
        cboTipoUnidade = new javax.swing.JComboBox<>();
        btConfirmar = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(jTable3);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(jTable4);

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jTable5);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane7.setViewportView(jTable6);

        jTable7.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(jTable7);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 450, 500));
        setMinimumSize(new java.awt.Dimension(450, 500));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Codigos Externos");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Informação"));

        jLabel2.setText("Essa é uma área para fazer configuração de códigos de barra de  ");

        jLabel3.setText("sistemas externos, geralmente balanças de pesagem");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );

        tblPadroes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPadroes);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastro do padrao"));

        jLabel4.setText("Código:");

        lblCodigo.setText("0");

        jLabel5.setText("Nome:");

        jLabel6.setText("Digitos iniciais: ");

        jLabel7.setText("Qtd. digitos codigo produto:  ");

        jLabel8.setText("Qtd. digitos unidade:  ");

        jLabel9.setText("Casas decimais:");

        txtNome.setText("jTextField1");

        txtDigitosIniciais.setText("jTextField1");

        txtDigitosCodigoProduto.setText("jTextField1");

        txtDigitosUnidade.setText("jTextField1");

        jLabel10.setText("Tipo de unidade:");

        txtCasasDecimais.setText("jTextField1");
        txtCasasDecimais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNome))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDigitosIniciais))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDigitosCodigoProduto))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDigitosUnidade))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCasasDecimais))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCodigo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTipoUnidade, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDigitosIniciais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtDigitosCodigoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDigitosUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cboTipoUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCasasDecimais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        btConfirmar.setText("Confirmar");

        btNovo.setText("Novo Padrao");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btConfirmar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new Dimension(340, 538));
        setLocationRelativeTo(null);
	}
	
	 javax.swing.JButton btConfirmar;
	    javax.swing.JButton btNovo;
	    javax.swing.ButtonGroup buttonGroup1;
	    javax.swing.ButtonGroup buttonGroup2;
	    javax.swing.JComboBox<TipoQuantidade> cboTipoUnidade;
	    javax.swing.JLabel jLabel1;
	    javax.swing.JLabel jLabel10;
	    javax.swing.JLabel jLabel2;
	    javax.swing.JLabel jLabel3;
	    javax.swing.JLabel jLabel4;
	    javax.swing.JLabel jLabel5;
	    javax.swing.JLabel jLabel6;
	    javax.swing.JLabel jLabel7;
	    javax.swing.JLabel jLabel8;
	    javax.swing.JLabel jLabel9;
	    javax.swing.JPanel jPanel1;
	    javax.swing.JPanel jPanel2;
	    javax.swing.JScrollPane jScrollPane1;
	    javax.swing.JScrollPane jScrollPane4;
	    javax.swing.JScrollPane jScrollPane5;
	    javax.swing.JScrollPane jScrollPane6;
	    javax.swing.JScrollPane jScrollPane7;
	    javax.swing.JScrollPane jScrollPane8;
	    javax.swing.JSeparator jSeparator1;
	    javax.swing.JSeparator jSeparator2;
	    javax.swing.JTable jTable3;
	    javax.swing.JTable jTable4;
	    javax.swing.JTable jTable5;
	    javax.swing.JTable jTable6;
	    javax.swing.JTable jTable7;
	    javax.swing.JLabel lblCodigo;
	    javax.swing.JTable tblPadroes;
	    javax.swing.JTextField txtCasasDecimais;
	    javax.swing.JTextField txtDigitosCodigoProduto;
	    javax.swing.JTextField txtDigitosIniciais;
	    javax.swing.JTextField txtDigitosUnidade;
	    javax.swing.JTextField txtNome;
	
}
