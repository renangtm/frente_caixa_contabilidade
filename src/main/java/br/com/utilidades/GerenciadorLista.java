package br.com.utilidades;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;

import br.com.base.Service;

public class GerenciadorLista<T> {

	private Class<T> classe;

	private JTable tabela;

	private Service<T> service;

	private JLabel lblPagina;

	private JSlider slider;

	private int pagina = 0;

	private int numeroPaginas = 0;

	private int porPagina = 40;

	private String filtro = "";

	private boolean nochange = false;

	private ListModelGenerica<T> model;

	private ProvedorDeEventos<T> provedor;
	
	private LinkedList<Thread> ths = new LinkedList<Thread>();
	
	private Object rep;
	private Class<? extends Representador<T>> classe_rep;

	private String ordem = "";

	private boolean evict = true;

	public ListModelGenerica<T> getModel() {

		return this.model;

	}
	
	public synchronized void setFiltro(String f) {

		
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				
				filtro = f;
				
				atualizar();
				
				ths.removeFirst();
				
				if(ths.size() > 0) {
					
					ths.getLast().start();
					
				}
				
			}
			
		});
		
		if(ths.size()>=2) {
			
			ths.removeLast();
			
		}
		
		ths.add(th);
		
		if(ths.getFirst() == th) {
			
			th.start();
			
		}
		
	}

	public void setFiltro(JTextField filtro) {

		filtro.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {

				setFiltro(filtro.getText());

			}

		});

	}

	public void setLblPagina(JLabel lbl) {

		this.lblPagina = lbl;

	}

	public void setLblSlider(JSlider slider) {

		this.slider = slider;

		this.slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {

				if (!nochange) {

					pagina = slider.getValue();
					atualizar();

				}

			}

		});

	}

	public GerenciadorLista(Class<T> classe, JTable tabela, Service<T> service, int porPagina,
			ProvedorDeEventos<T> provedor, Object rep, Class<? extends Representador<T>> classe_rep) {

		this.classe = classe;
		this.service = service;
		this.porPagina = porPagina;
		this.tabela = tabela;

		this.provedor = provedor;
		this.rep = rep;
		this.classe_rep = classe_rep;

	}

	public GerenciadorLista(Class<T> classe, JTable tabela, Service<T> service, ProvedorDeEventos<T> provedor,
			Object rep, Class<? extends Representador<T>> classe_rep) {

		this.classe = classe;
		this.service = service;
		this.tabela = tabela;

		this.provedor = provedor;
		this.rep = rep;
		this.classe_rep = classe_rep;

	}

	public GerenciadorLista(Class<T> classe, JTable tabela, Service<T> service, int porPagina,
			ProvedorDeEventos<T> provedor) {

		this.classe = classe;
		this.service = service;
		this.porPagina = porPagina;
		this.tabela = tabela;

		this.provedor = provedor;

	}

	public GerenciadorLista(Class<T> classe, JTable tabela, Service<T> service, ProvedorDeEventos<T> provedor) {

		this.classe = classe;
		this.service = service;
		this.tabela = tabela;

		this.provedor = provedor;

	}

	public void atualizar() {
		System.out.println("PONTO11111111111111");
		this.numeroPaginas = (int) Math.ceil((double) this.service.getCount(this.filtro) / (double) this.porPagina) - 1;
		this.pagina = Math.max(Math.min(this.pagina, this.numeroPaginas), 0);

		if (this.slider != null) {

			this.nochange = true;

			this.slider.setMaximum(this.numeroPaginas);

			this.slider.setValue(this.pagina);

			this.nochange = false;

		}

		if (this.lblPagina != null) {

			this.lblPagina.setText("Pg. " + this.pagina);

		}

		if (this.model == null) {

			if (this.provedor == null) {
				
				if (this.rep == null) {

					this.model = new ListModelGenerica<T>(this.service.getElementos(this.pagina * this.porPagina,
							(this.pagina + 1) * this.porPagina, filtro, this.ordem), this.classe);

				} else {

					this.model = new ListModelGenerica<T>(this.service.getElementos(this.pagina * this.porPagina,
							(this.pagina + 1) * this.porPagina, filtro, this.ordem), this.classe, this.classe_rep,
							this.rep);


				}

			} else {

				if (this.rep == null) {

					this.model = new ListModelGenerica<T>(this.service.getElementos(this.pagina * this.porPagina,
							(this.pagina + 1) * this.porPagina, filtro, this.ordem), this.classe, this.provedor);

				} else {

					
					this.model = new ListModelGenerica<T>(this.service.getElementos(this.pagina * this.porPagina,
							(this.pagina + 1) * this.porPagina, filtro, this.ordem), this.classe, this.provedor,
							this.classe_rep, this.rep);
					
					
				}

			}
			
			List<Campo> campos = new ArrayList<Campo>();

			for (Field c : ((this.rep == null) ? this.classe.getDeclaredFields()
					: this.classe_rep.getDeclaredFields())) {

				c.setAccessible(true);

				Campo cmp = c.getDeclaredAnnotation(Campo.class);

				if (cmp != null) {
					campos.add(cmp);
				}

			}

			RowSorter<TableModel> sorter = new RowSorter<TableModel>() {

				private List<SortKey> sortKeys = new ArrayList<SortKey>(campos.size());

				@Override
				public TableModel getModel() {
					// TODO Auto-generated method stub
					return model;
				}

				@Override
				public void toggleSortOrder(int column) {

					if (!campos.get(column).ordem().equals("")) {

						if (sortKeys.size() == 0) {
							for (int i = 0; i < campos.size(); i++) {
								sortKeys.add(new SortKey(i, SortOrder.UNSORTED));
							}
						}

						SortKey sk = sortKeys.get(column);

						if (sk.getSortOrder() == SortOrder.UNSORTED) {

							sk = new SortKey(column, SortOrder.ASCENDING);

						} else if (sk.getSortOrder() == SortOrder.ASCENDING) {

							sk = new SortKey(column, SortOrder.DESCENDING);

						} else {

							sk = new SortKey(column, SortOrder.UNSORTED);

						}

						sortKeys = sortKeys.stream().map(x -> new SortKey(x.getColumn(), SortOrder.UNSORTED))
								.collect(Collectors.toList());

						sortKeys.set(column, sk);

						String ord = "";

						int x = 0;
						for (SortKey s : sortKeys) {

							if (s.getSortOrder() != SortOrder.UNSORTED) {

								ord += "," + campos.get(x).ordem() + " "
										+ (s.getSortOrder() == SortOrder.ASCENDING ? "ASC" : "DESC");

							}

							x++;
						}

						if (ord.length() > 0)
							ord = ord.substring(1);

						ordem = ord;

						atualizar();

					}

				}

				@Override
				public int convertRowIndexToModel(int index) {
					// TODO Auto-generated method stub
					return index;
				}

				@Override
				public int convertRowIndexToView(int index) {
					// TODO Auto-generated method stub
					return index;
				}

				@SuppressWarnings("unchecked")
				@Override
				public void setSortKeys(@SuppressWarnings("rawtypes") List keys) {

					sortKeys = keys;

				}

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public List getSortKeys() {
					// TODO Auto-generated method stub
					return sortKeys.stream().filter(x -> x.getSortOrder() != SortOrder.UNSORTED)
							.collect(Collectors.toList());

				}

				@Override
				public int getViewRowCount() {
					// TODO Auto-generated method stub
					return model.getListaBase().size();
				}

				@Override
				public int getModelRowCount() {
					// TODO Auto-generated method stub
					return model.getListaBase().size();
				}

				@Override
				public void modelStructureChanged() {
					// TODO Auto-generated method stub

				}

				@Override
				public void allRowsChanged() {
					// TODO Auto-generated method stub

				}

				@Override
				public void rowsInserted(int firstRow, int endRow) {
					// TODO Auto-generated method stub

				}

				@Override
				public void rowsDeleted(int firstRow, int endRow) {
					// TODO Auto-generated method stub

				}

				@Override
				public void rowsUpdated(int firstRow, int endRow) {
					// TODO Auto-generated method stub

				}

				@Override
				public void rowsUpdated(int firstRow, int endRow, int column) {
					// TODO Auto-generated method stub

				}

			};

			this.tabela.setModel(this.model);
			this.tabela.setRowSorter(sorter);

		} else {

			if (this.evict) {

				this.model.getListaCompleta().forEach(this.service::lixeira);

			}

			this.model.setLista(this.service.getElementos(this.pagina * this.porPagina,
					(this.pagina + 1) * this.porPagina, filtro, this.ordem));

		}

	}

	public boolean isEvict() {

		return this.evict;

	}

	public void setEvict(boolean ev) {

		this.evict = ev;

	}

}
