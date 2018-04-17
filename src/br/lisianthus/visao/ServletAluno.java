package br.lisianthus.visao;

import java.io.IOException;


import java.io.PrintWriter;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorAluno;

import br.lisianthus.modelo.Aluno;
import br.lisianthus.utils.Retorno;

@SuppressWarnings("serial")

public class ServletAluno extends HttpServlet{
	
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

	public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
	
		String caminhoAlunoIndex = realPath + separador + "WEB-INF" + separador + "aluno" + separador + "aluno_" 
		+ op + ".html";
		MiniTemplator tpa = new MiniTemplator(caminhoAlunoIndex);
		return tpa;
	}
	
	private void executaPagina(HttpServletRequest req, HttpServletResponse resp)
			throws TemplateSyntaxException, IOException {

		String nomeMetodo = null;
		String op = req.getParameter("op");
		PrintWriter out = resp.getWriter();
		op = op == null ? "index" : op;

		if (op.equalsIgnoreCase("index") || op.equalsIgnoreCase("cadastro")) {
			
			MiniTemplator tpa = getMiniTemplator(op);

			if (op.equalsIgnoreCase("cadastrar")) {
			//	salvarAluno(req, out);
			} else {
				out.println(tpa.generateOutput());
			}
			
		} else {
			
			  nomeMetodo = op + "Aluno";
			  
			
			  System.out.println("E pior ainda estou aqui");

			try {
				Class<?> cls;

				cls = Class.forName("br.lisianthus.visao.ServletAluno");
				Class[] parameterTypes = new Class[2];
				parameterTypes[0] = HttpServletRequest.class;
				parameterTypes[1] = PrintWriter.class;
				// HttpServletRequest request = req;
				PrintWriter print = new PrintWriter(out);

				Object[] obj = new Object[2];
				obj[0] = req;
				obj[1] = print;

				Method mt = cls.getMethod(nomeMetodo, parameterTypes);

				System.out.println("Metodo:" + nomeMetodo);
				if (mt != null) {
					mt.invoke(this, obj);
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

	private Integer preparaId(String id) {
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	public void salvarAluno(HttpServletRequest req, PrintWriter out) throws IOException {
		MiniTemplator t = getMiniTemplator("message");
		//Aluno aluno = new Aluno();
		Retorno ret = new Retorno();
		ret = cadastroAluno(req);
		t.setVariable("message", ret.getMensagem());
		out.println(t.generateOutput());
	}
	
	
    private Retorno cadastroAluno (HttpServletRequest req){

		Aluno aluno = new Aluno();
		Retorno ret = new Retorno();
		ControladorAluno controlealuno = new ControladorAluno();

		try{
			
			//aluno.setId_aluno(1);
			aluno.setCpf(new Long(req.getParameter("cpf"))); //- Resolver 
			aluno.setNome_aluno(req.getParameter("nome")); 
		    aluno.setSenha(req.getParameter("senha"));//-----
			aluno.setEmail(req.getParameter("email")); // Depois do Aluno e o msm Aqui
			aluno.setMatricula(preparaId(req.getParameter("matricula")));
			aluno.setAno_ingresso(preparaId(req.getParameter("anocurso")));
            aluno.setPermissao(false);//Verificar como colocar aqui tbm 
            aluno.setCoord_ac_id(1);
         
            ret = controlealuno.inserir(aluno);
        	
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro:"+e.getMessage());
		}
		
		return ret;
	}
}
