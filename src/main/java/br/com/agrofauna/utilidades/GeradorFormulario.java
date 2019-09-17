package br.com.agrofauna.utilidades;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import br.com.agrofauna.conversores.Conversor;
import br.com.agrofauna.conversores.ConversorDate;

public class GeradorFormulario {

	private List<Component> caixasTexto;
	@SuppressWarnings("rawtypes")
	private Class classe;
	private int txtSize;
	@SuppressWarnings("rawtypes")
	private List<Conversor> conversores = new ArrayList<Conversor>();

	private double yFinal;

	private String mask(String pattern, String txt) {

		String str = "";

		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c != '#') {
				txt = txt.replaceAll("\\" + c + "", "");
			}
		}

		for (int i = 0, j = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			if (c == '#') {
				str += ((i - j) < txt.length()) ? txt.charAt(i - j) : ' ';
			} else if (c != (((i - j) < txt.length()) ? txt.charAt(i - j) : ' ')) {
				str += c;
				j++;
			} else {
				str += c;
			}
		}

		return str;
	}

	public GeradorFormulario(Container jframe, @SuppressWarnings("rawtypes") Class classe, double x, double y,
			double width, double height, double espacamento, LayoutRelativo lr, int txtS, int colunas) {

		this.txtSize = txtS;
		this.conversores.add(new ConversorDate());

		this.caixasTexto = new ArrayList<Component>();
		this.classe = classe;

		Field[] atributos = classe.getDeclaredFields();

		double yI = y;
		double quantidade = 0;
		for (Field campo : atributos) {

			campo.setAccessible(true);

			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			quantidade++;
		}

		quantidade /= colunas;

		int j = 0;
		for (Field campo : atributos) {

			campo.setAccessible(true);

			final Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			if (j >= quantidade) {

				y = yI;

				JSeparator separador = new JSeparator(JSeparator.VERTICAL);
				jframe.add(separador);

				lr.setDimensoesComponente(separador, (int) (x + width + espacamento), (int) yI, 1,
						(int) (quantidade * (height + espacamento)));

				x += ((espacamento) * 2) + width;

				j = 0;

			}

			JLabel lbl = new JLabel(anCampo.nome() + ":");
			lbl.setFont(new Font("Arial", Font.BOLD, 11));
			jframe.add(lbl);

			Component txt = new JTextField();

			if (anCampo.password()) {
				txt = new JPasswordField();
			} else if (!anCampo.maskPattern().equals("")) {
				final JTextField th = ((JTextField) txt);
				
				th.addFocusListener(new FocusListener(){

					public void focusGained(FocusEvent arg0) {
						
						th.select(0, th.getText().length());
						
					}

					
					public void focusLost(FocusEvent arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				th.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent arg0) {
						th.setText(mask(anCampo.maskPattern(), th.getText()));
					}
				});
				th.addKeyListener(new KeyListener() {

					
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

					
					public void keyReleased(KeyEvent arg) {
						int caret = th.getCaretPosition();

						char c = th.getText().charAt(caret - 1);

						th.setText(mask(anCampo.maskPattern(), th.getText()));

						while (caret < th.getText().length() && th.getText().charAt(caret - 1) != c)
							caret++;

						th.setCaretPosition(caret);
					}

					
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub

					}

				});
			}

			jframe.add(txt);

			lr.setDimensoesComponente(lbl, (int) x, (int) y, (int) ((txtSize == 0) ? (width * 2 / 5) : width - txtSize),
					(int) height);
			lr.setDimensoesComponente(txt, (int) (x + ((txtSize == 0) ? (width * 2 / 5) : width - txtSize)), (int) y,
					(txtSize == 0) ? (int) (width * 3 / 5) : txtSize, (int) height);

			y += height + espacamento;

			this.caixasTexto.add(txt);
			j++;
		}
		this.yFinal = y;
	}

	public double getYFinal() {
		return this.yFinal;
	}

	private Object converterParaTipo(Field f, Object valor) {

		if (f.getType().equals(Double.class) || f.getType().equals(double.class)) {
			return Double.parseDouble(valor.toString());
		} else if (f.getType().equals(Integer.class) || f.getType().equals(int.class)) {
			return Integer.parseInt(valor.toString());
		} else if (f.getType().equals(Float.class) || f.getType().equals(float.class)) {
			return Float.parseFloat(valor.toString());
		} else if (f.getType().equals(BigDecimal.class)) {
			return new BigDecimal(valor.toString());
		}

		Field campo = f;

		for (@SuppressWarnings("rawtypes")
		Conversor conversor : conversores)
			if (conversor.podeConverter(campo.getType())) {
				try {
					return conversor.paraObjeto(valor.toString());
				} catch (Exception e) {

				}

			}

		return valor.toString();
	}

	public void lerObjeto(Object objeto) {

		Field[] f = this.classe.getDeclaredFields();

		int i = 0;

		for (Field campo : f) {

			campo.setAccessible(true);
			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			try {

				JTextField txt = (JTextField) this.caixasTexto.get(i);
				if (campo.get(objeto) != null) {
					txt.setText(campo.get(objeto).toString());
				} else {
					txt.setText("");
				}
				for (ActionListener act : txt.getActionListeners())
					act.actionPerformed(null);

			} catch (Exception e) {

			}

			i++;
		}

	}

	public void setText(Field ff, String t) {

		Field[] f = this.classe.getDeclaredFields();

		int i = 0;
		int k = -1;
		for (Field campo : f) {

			campo.setAccessible(true);
			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			if (ff.getName().equals(campo.getName())) {
				k = i;
				break;
			}

			i++;
		}

		if (k > -1) {
			try {
				((JTextField) this.caixasTexto.get(k)).setText(t);
			} catch (Exception e) {
				try {
					((JPasswordField) this.caixasTexto.get(k)).setText(t);
				} catch (Exception e2) {

				}
			}
		}
	}

	public Component getComponent(Field ff) {
		Field[] f = this.classe.getDeclaredFields();

		int i = 0;
		int k = -1;
		for (Field campo : f) {

			campo.setAccessible(true);
			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			if (ff.getName().equals(campo.getName())) {
				k = i;
				break;
			}

			i++;
		}

		if (k > -1) {
			return this.caixasTexto.get(k);
		}
		return null;
	}

	public void setEnabled(Field ff, boolean b) {

		try {
			this.getComponent(ff).setEnabled(b);
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("deprecation")
	public void atribuirValores(Object objeto) throws IllegalArgumentException, IllegalAccessException {

		Field[] atributos = this.classe.getDeclaredFields();

		int i = 0;
		for (Field campo : atributos) {
			campo.setAccessible(true);
			Campo anCampo = campo.getDeclaredAnnotation(Campo.class);
			if (anCampo == null)
				continue;

			if (!anCampo.formulario())
				continue;

			if (!anCampo.validador().equals(Validador.class)) {

				try {

					Validador val = anCampo.validador().getConstructor().newInstance();
					if (!val.validar(((JTextField) this.caixasTexto.get(i)).getText())) {
						((JTextField) this.caixasTexto.get(i)).requestFocus();
						throw new RuntimeException(anCampo.nome() + ", incorreto");
					}

				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			try {
				campo.set(objeto, this.converterParaTipo(campo, ((JTextField) this.caixasTexto.get(i)).getText()));
			} catch (Exception e) {
				try {
					campo.set(objeto,
							this.converterParaTipo(campo, ((JPasswordField) this.caixasTexto.get(i)).getText()));
				} catch (Exception e2) {

				}
			}

			i++;
		}

	}

}
