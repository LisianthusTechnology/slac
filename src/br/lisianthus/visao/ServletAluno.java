package br.lisianthus.visao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.google.gson.Gson;

import java.io.PrintWriter;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorCadastroAluno;
import br.lisianthus.controle.ControladorParticipacao;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.Participacao;
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
	
		String caminhoAlunoIndex = realPath + separador + "WEB-INF" + separador + "aluno" + separador + "aluno_" + op
				+ ".html";
		
		
		System.out.println("CaminhoAlunoIndex" + caminhoAlunoIndex);
		
	
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

			if (op.equalsIgnoreCase("inserir")) {
				out.println("vey eu to aqui");
			} else {
				
				out.println(tpa.generateOutput());
				
			}
		} else {
			
			  out.println("E pior ainda estou aqui");

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
		//Participacao participacao = new Participacao();
		cadastraAluno(req);
	}
	
	
    private void cadastraAluno(HttpServletRequest req){
		
		Aluno aluno = new Aluno();
		Retorno ret;// = new Retorno();
		ControladorCadastroAluno controle = new ControladorCadastroAluno();
	
        
		try{
			
		//	List<?> items = upload.parseRequest(req);
		//Iterator<?> itr = items.iterator();
			
			aluno.setId_aluno(1);
		//	aluno.setCpf(2); //- Resolver 
			aluno.setNome_aluno("");// Verificar como Colocar aqui para inserir 
		    aluno.setSenha("");//-----
			aluno.setEmail(""); // Depois do Aluno e o msm Aqui
			aluno.setMatricula(6);
			aluno.setAno_ingresso(7);
            aluno.setPermissao(true);//Verificar como colocar aqui tbm 
            aluno.setCoord_ac_id(1);
            
		
        	
//			while (itr.hasNext()) {
//               
                 
//                    
//                }else {
//                	System.out.println("Senhor me ilumina");
//                }
		//	}
			
			if(aluno != null){
				System.out.println("Aluno:" + aluno.getId_aluno() + ", "+ aluno.getCpf() + "," + 
			    aluno.getNome_aluno() +"," +aluno.getSenha()+"," +aluno.getEmail() +","+aluno.getMatricula()+"," +
			    aluno.getAno_ingresso()+"," +aluno.isPermissao()+"," +aluno.getCoord_ac_id());
							
				ret = controle.inserir(aluno);
				System.out.println("Retorno:" + ret.getMensagem());
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("Erro:"+e.getMessage());
		}
		
	}

}
