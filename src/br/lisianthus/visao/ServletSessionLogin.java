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
import br.lisianthus.modelo.Aluno;

public class ServletSessionLogin extends HttpServlet{
	
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
	
	  public void doGet(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, IOException {
	 
		
	  }

	  public void doPost(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, IOException {

	    String userName = request.getParameter("loginaluno");
	    String password = request.getParameter("senha");
	    //if(userName.contains("^[a-Z]")){
		    if (login(userName, password)) {
			      //send HttpSession Object to the browser
			      HttpSession session = request.getSession(true);
			      session.setAttribute("loggedIn", new String("true"));
			      String url = (String) session.getAttribute("UltimaURL");//se aluno mostrar uma tela, se coordd mostrar outra
			      url = url==null ? "eventos?op=opcoes_coord" : url;//se url for igual a nulo vai pra pagina principal se nao entra no update
			      response.sendRedirect(url);
			    }
			    else {
			      sendLoginForm(request, response, true);
			    }
		/*}else{
	    
	    if (login(userName, password)) {
	      //send HttpSession Object to the browser
	      HttpSession session = request.getSession(true);
	      session.setAttribute("loggedIn", new String("true"));
	      String url = (String) session.getAttribute("UltimaURL");//se aluno mostrar uma tela, se coordd mostrar outra
	      url = url==null ? "eventos?op=index" : url;//se url for igual a nulo vai pra pagina principal se nao entra no update
	      response.sendRedirect(url);
	    }
	    else {
	      sendLoginForm(request, response, true);
	    }
		}*/
	  }

	  private void sendLoginForm(HttpServletRequest request, HttpServletResponse response, boolean
	    withErrorMessage)
	    throws ServletException, IOException {

		  PrintWriter out = response.getWriter();
		
		    MiniTemplator tpl = getMiniTemplator("index");
		    if (withErrorMessage) {
			      out.println("Falha ao fazer login. Tente novamente.<BR>");
			    }
		    out.println(tpl.generateOutput());
	    	  }

	  public static boolean login(String userName, String password) {

		  String usuario = userName;

		 /* if(usuario.contains("^[a-Z]")){
			  if(userName != null && userName.equals("joilson")&&
					  password != null && password.equals("senha")){
				  return true;
			  }
			  else{
				  return false;
			  }
		  }else{*/
			  if(userName != null && userName.equals("70327896175")&&
					  password != null && password.equals("senha")){
				  return true;
			  }else{
				  return false;
			  }
		  }
	 // }
	  public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
			String caminhoAmigoIndex = realPath + separador + "WEB-INF" + separador + "aluno" + separador + "aluno_" + op
					+ ".html";
			System.out.println("CaminhoAmigoIndex:" + caminhoAmigoIndex);
			MiniTemplator tpl = new MiniTemplator(caminhoAmigoIndex);
			return tpl;
		}
}

