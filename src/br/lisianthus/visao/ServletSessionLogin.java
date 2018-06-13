package br.lisianthus.visao;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorAluno;
import br.lisianthus.controle.ControladorCoordenador;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.utils.Retorno;

public class ServletSessionLogin extends HttpServlet {

	ServletContext servletContext;
	String separador;
	String realPath;
	String contextPath;

	public void init() {
		servletContext = getServletContext();
		separador = System.getProperty("file.separator");
		realPath = servletContext.getRealPath("/");
		contextPath = servletContext.getContextPath();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendLoginForm(request, response, false);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		realizaLogin(request, response);
	}

	private void realizaLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String userName = request.getParameter("loginaluno");
		String password = request.getParameter("senha");
		Retorno ret = new Retorno();

		if (userName.matches("[a-zA-Z]*")) {
			Coordenador coord = loginCoord(userName, password);
			if(coord != null){
				ret.setSucesso();
			}
			if (ret.isSucesso()) {
				// send HttpSession Object to the browser
				HttpSession session = request.getSession(true);
				session.setAttribute("loggedIn", new String("true"));
				session.setAttribute("Coordenador", coord);
				String url = (String) session.getAttribute("UltimaURL");
				url = url == null ? "eventos?op=opcoescoord" : url;
				response.sendRedirect(url);
			} else {
				sendLoginForm(request, response, true);
			}
		} else if (userName.matches("^[0-9]*")){
			Aluno aluno = loginAluno(userName, password);
			if(aluno != null){
				ret.setSucesso();
			}
			if (ret.isSucesso()) {
				// send HttpSession Object to the browser
				HttpSession session = request.getSession(true);
				session.setAttribute("loggedIn", new String("true"));
				session.setAttribute("Aluno", aluno);
				String url = (String) session.getAttribute("UltimaURL");
				url = url == null ? "eventos?op=index" : url;
				response.sendRedirect(url);
			} else {
				sendLoginForm(request, response, true);
			}
		}else{
			sendLoginForm(request, response, true);
		}
	}

	private void sendLoginForm(HttpServletRequest request, HttpServletResponse response, boolean withErrorMessage)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		PrintWriter out = response.getWriter();

		MiniTemplator tpl = getMiniTemplator("index");
		if (withErrorMessage) {
			tpl.setVariable("mensagem", "Erro ao realizar login");
		}

		out.println(tpl.generateOutput());
	}


	public static Coordenador loginCoord(String userName, String password) {

			ControladorCoordenador cont = new ControladorCoordenador();
			Coordenador coord = new Coordenador(userName, password);
			Coordenador coordenador = cont.obter(coord);
			if (coordenador != null) {
				return coordenador;
			}
		return null;
	}

	public static Aluno loginAluno(String userName, String password){
			
			ControladorAluno contA = new ControladorAluno();
			Aluno a = new Aluno(new Long(userName), password);
			Aluno aluno = contA.obter(a);
			if(aluno != null){
				return aluno;
			}
		return null;
	}

	public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
		String caminhoAmigoIndex = realPath + separador + "WEB-INF" + separador + "login" + separador + "login_" + op
				+ ".html";
		System.out.println("CaminhoAmigoIndex:" + caminhoAmigoIndex);
		MiniTemplator tpl = new MiniTemplator(caminhoAmigoIndex);
		return tpl;
	}
}
