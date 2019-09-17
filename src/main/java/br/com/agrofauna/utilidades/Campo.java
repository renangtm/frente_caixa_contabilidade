package br.com.agrofauna.utilidades;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)

public @interface Campo {
	public String nome();
	public boolean lista() default true;
	public boolean formulario() default true;
	public String ordem() default "";
	public boolean editavel() default true;
	public boolean password() default false;
	public Class<? extends Validador> validador() default Validador.class;
	public String maskPattern() default "";
	public int tamanho() default 0;
}
