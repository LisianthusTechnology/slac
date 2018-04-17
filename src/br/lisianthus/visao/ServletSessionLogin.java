package br.lisianthus.visao;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.lisianthus.modelo.Aluno;

public class ServletSessionLogin extends HttpServlet{
	
	  public void doGet(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, IOException {
	 
	    sendLoginForm(request, response, false);
	  }

	  public void doPost(HttpServletRequest request, HttpServletResponse
	    response) throws ServletException, IOException {

	    String userName = request.getParameter("loginaluno");
	    String password = request.getParameter("senha");
	    if(userName.contains("^[a-Z]")){
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
		}else{
	    
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
		}
	  }

	  private void sendLoginForm(HttpServletRequest request, HttpServletResponse response, boolean
	    withErrorMessage)
	    throws ServletException, IOException {

	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<HTML>");
	    out.println("<HEAD>");
	    out.println("<TITLE>Login</TITLE>");
	    out.println("<link rel=stylesheet type=text/css href=estilo.css>");
	    out.println("</HEAD>");
	    out.println("<BODY>");
	    out.println("<CENTER>");
	    out.println("<div>");
	    if (withErrorMessage) {
	      out.println("Falha ao fazer login. Tente novamente.<BR>");
	    }
	    out.println("<BR>");
	    out.println("<BR><H2>Login</H2>");
	    out.println("<BR>");
	    out.println("<BR>Por favor insira login e senha.");
	    out.println("<BR>");
	    out.println("<BR><FORM METHOD=POST>");
	    out.println("<input type='hidden' name='id' value='"+request.getParameter("id")+"'>");
	    out.println("<TABLE>");
	    out.println("<TR>");
	    out.println("<TD>Nome de usuário:</TD>");
	    out.println("<TD><INPUT TYPE=TEXT NAME=userName></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD>Senha:</TD>");
	    out.println("<TD><INPUT TYPE=PASSWORD NAME=password></TD>");
	    out.println("</TR>");
	    out.println("<TR>");
	    out.println("<TD ALIGN=RIGHT COLSPAN=2>");
	    out.println("<a class=buttonWhite href=search>Voltar</a>");
	    out.println("<INPUT class=buttonColor TYPE=SUBMIT VALUE=Login></TD>");
	    out.println("</TR>");
	    out.println("</TABLE>");
	    out.println("</FORM>");
	    out.println("</div>");
	    out.println("</CENTER>");
	    out.println("</BODY>");
	    out.println("</HTML>");
	  }

	  public static boolean login(String userName, String password) {

		  String usuario = userName;

		  if(usuario.contains("^[a-Z]")){
			  if(userName != null && userName.equals("joilson")&&
					  password != null && password.equals("senha")){
				  return true;
			  }
			  else{
				  return false;
			  }
		  }else{
			  if(userName != null && userName.equals("70327896175")&&
					  password != null && password.equals("senha")){
				  return true;
			  }else{
				  return false;
			  }
		  }
	  }
}

