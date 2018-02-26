package br.lisianthus.visao;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import conexaoBanco.ConexaoBanco;


@SuppressWarnings("serial")
public class ServletCadastroEventos extends HttpServlet {

	ServletContext servletContext;
	String separador;
	String realPath;
	String contextPath;

	public void init() {
		servletContext = getServletContext();
		separador = System.getProperty("file.separator");
		realPath = servletContext.getRealPath("/");
		contextPath = servletContext.getContextPath();
		ConexaoBanco.obterInstancia();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		executaPagina(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * Método responsável por executar as ações das páginas, bem como dizer para
	 * o Minitemplator que página deve ser aberta.
	 * 
	 * @param req
	 *            o request é enviado pelo cliente e é através dele que
	 *            conseguimos pegar a página que o cliente solicitou.
	 * @param resp
	 *            o response é utilizado para responder o cliente através da
	 *            escrita na tela, entre outras formas.
	 * @throws TemplateSyntaxException
	 * @throws IOException
	 */

	private void executaPagina(HttpServletRequest req, HttpServletResponse resp)
			throws TemplateSyntaxException, IOException {
		String nomeMetodo = null;
		String op = req.getParameter("op");
		PrintWriter out = resp.getWriter();
		
		if (op == null || op.equalsIgnoreCase("index")) {
			op = "inserir";
			MiniTemplator tpl = getMiniTemplator(op);
			out.println(tpl.generateOutput());
		} else {
			nomeMetodo = op + "Amigo";

			Class<?> cls;
			try {
				cls = Class.forName("br.lisianthus.visao.ServletCadastroEventos");
				Class[] parameterTypes = new Class[2];
				parameterTypes[0] = HttpServletRequest.class;
				parameterTypes[1] = PrintWriter.class;

				PrintWriter print = new PrintWriter(out);

				Object[] obj = new Object[2];
				obj[0] = req;
				obj[1] = print;

				Method mt = cls.getMethod(nomeMetodo, parameterTypes);
				if (mt != null) {
					mt.invoke(nomeMetodo, obj);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
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
	}

	public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
		String caminhoAmigoIndex = realPath + separador + "WEB-INF" + separador + "eventos" + separador + "evento_" + op
				+ ".html";
		System.out.println("CaminhoAmigoIndex:" + caminhoAmigoIndex);
		MiniTemplator tpl = new MiniTemplator(caminhoAmigoIndex);
		return tpl;
	}

}
