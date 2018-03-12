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
import java.util.List;

import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorAtividadeComplementar;
import br.lisianthus.controle.ControladorModalidade;
import br.lisianthus.controle.ControladorParticipacao;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;


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
		op = op == null ? "index" : op;

		if (op.equalsIgnoreCase("index") || op.equalsIgnoreCase("inserir")) {
			MiniTemplator tpl = getMiniTemplator(op);

			if (op.equalsIgnoreCase("inserir")) {
				listarModalidade(req, out, tpl);
			} else {
				out.println(tpl.generateOutput());
			}
		} else {
			nomeMetodo = op + "Participacao";
			
			try {
				Class<?> cls;
				
				cls = Class.forName("br.lisianthus.visao.ServletCadastroEventos");
				Class[] parameterTypes = new Class[2];
				parameterTypes[0] = HttpServletRequest.class;
				parameterTypes[1] = PrintWriter.class;
				// HttpServletRequest request = req;
				PrintWriter print = new PrintWriter(out);

				Object[] obj = new Object[2];
				obj[0] = req;
				obj[1] = print;
				
				Method mt = cls.getMethod(nomeMetodo, parameterTypes);
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

	/*
	 * public void inserirParticipacao(HttpServletRequest req, PrintWriter out)
	 * throws IOException{ MiniTemplator tpl = this.getMiniTemplator("inserir");
	 * localizarModalidade(req, out, tpl); }
	 */



	private Modalidade getModalidadeFromRequest(HttpServletRequest req) {

		Integer id_mod = null;
		String erroMessage = "";
		try {
			id_mod = preparaIdModalidade(req);
		} catch (NumberFormatException e) {
			erroMessage = "Campo ID em formato inválido, aceita somente número!<BR>\n";
		}

		String nome_mod = req.getParameter("nome");
		nome_mod = nome_mod != null ? nome_mod : "";

		if (!erroMessage.equals("")) {
			throw new RuntimeException(erroMessage);
		}

		Modalidade a = new Modalidade(id_mod, nome_mod);
		return a;
	}

	
	public void salvarParticipacao(HttpServletRequest req, PrintWriter out) throws IOException{
		Participacao participacao = new Participacao();
		ControladorParticipacao controle = new ControladorParticipacao();
		Retorno ret = new Retorno();
		
		participacao.setAluno_id_aluno(1);
		participacao.setAtividade_complementar_id_atividade(preparaId(req.getParameter("descricaoAC")));
		participacao.setCertificado_part(preparaArquivo(req));
		participacao.setCh_cadastrada_part(preparaId(req.getParameter("chCertificado")));
		participacao.setCh_validada_part(50);
		participacao.setCoordenador_ac_id_admin(1);
		participacao.setData_inicio_ac_part(new Date(2018-03-11));
		participacao.setLocal_ac_part(req.getParameter("localEvento"));
		participacao.setNome_ac_part(req.getParameter("nomeEvento"));
		participacao.setStatus("pendente");
		participacao.setTipo_ac_part(req.getParameter("tipoEvento"));
		
		ret.setMensagem("Salvar Participacao diz: " + controle.inserir(participacao).getMensagem());
		
		System.out.println(req.getParameter("modalidade"));
	}
	
	private File preparaArquivo(HttpServletRequest req){
		File file = new File(req.getParameter("certificado"));
		return file;
	}
	/**
	 * 
	 * @param req
	 *            request vindo do executapágina com reflexao
	 * @param out
	 *            também vindo do executapágina com reflexao
	 * @param tpl
	 *            é o que vai gerar o html e mmostrar na tela block está no html
	 *            evento_inserir e o set variable serve para colocar o nome da
	 *            modalidade que veio do banco (2 param) na variavel do html (1
	 *            param)
	 * 
	 * @throws IOException
	 */
	// tentar fazer reflexao generico ou qualquer coisa
	public void listarModalidade(HttpServletRequest req, PrintWriter out, MiniTemplator tpl) throws IOException {
		ControladorModalidade ctModalidade = new ControladorModalidade();
		Modalidade mod = this.getModalidadeFromRequest(req);

		List<Modalidade> listModalidade = ctModalidade.localizar(mod);
		for (Modalidade modalidade : listModalidade) {
			tpl.setVariable("modalidade.id_mod", modalidade.getId_mod());
			tpl.setVariable("modalidade.nome_mod", modalidade.getNome_mod());
			tpl.addBlock("nomeModalidade");
		}
		out.println(tpl.generateOutput());

		// chamando o método para listar as atividades
		//listarAtividades(req, out, tpl);
	}

	/**
	 * Na teoria esse método é para listar as atividades, no entanto, não sei em
	 * que hora faço a chamada dele
	 * 
	 * @param req
	 * @param out
	 * @param tpl
	 * @throws IOException
	 */
	public void jsonParticipacao(HttpServletRequest req, PrintWriter out) throws IOException {
		ControladorAtividadeComplementar cont = new ControladorAtividadeComplementar();
		List<AtividadeComplementar> listAtividadeComplementar = null;

		String idModSelectedAux = req.getParameter("idModalidadeServlet");
		Integer idModSelected;
		Modalidade mod = new Modalidade();

		if (!idModSelectedAux.isEmpty()) {
			idModSelected = preparaId(idModSelectedAux);
			mod.setId_mod(idModSelected);
			listAtividadeComplementar = cont.localizar(mod);
			out.println(convertJson(listAtividadeComplementar));
			}
		
	}

	private String convertJson(List<AtividadeComplementar> listAtividadeComplementar) {
		Gson gson = new Gson();
		String jsonAtividade = gson.toJson(listAtividadeComplementar);
		System.out.println("Atividades: " + jsonAtividade);
		return jsonAtividade;
	}

	/*
	 * private void preencherTemplateComObjetoReflexao(MiniTemplator tpl, Object
	 * a) { try { Class<?> cls = a.getClass(); for (Field field :
	 * cls.getDeclaredFields()) { String fieldName = field.getName(); String
	 * fieldValueString = "";
	 * 
	 * String methGet = ManipulaString.capitularizar(fieldName);
	 * 
	 * Method met = cls.getMethod("get" + methGet, null);
	 * 
	 * Object fieldValue = met.invoke(a, null);
	 * 
	 * // if(fieldValue.getClass().isInstance(String.class)){ //
	 * fieldValueString = (String) fieldValue; // } if (fieldValue != null) {
	 * fieldValueString = fieldValue.toString(); } tpl.setVariable(fieldName,
	 * fieldValueString);
	 * 
	 * }
	 * 
	 * } catch (IllegalArgumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (NoSuchMethodException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (SecurityException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch
	 * (InvocationTargetException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
		String caminhoAmigoIndex = realPath + separador + "WEB-INF" + separador + "eventos" + separador + "evento_" + op
				+ ".html";
		System.out.println("CaminhoAmigoIndex:" + caminhoAmigoIndex);
		MiniTemplator tpl = new MiniTemplator(caminhoAmigoIndex);
		return tpl;
	}

	public void listarAtividades(HttpServletRequest req, PrintWriter out, MiniTemplator tpl) throws IOException {
		ControladorAtividadeComplementar cont = new ControladorAtividadeComplementar();
		List<AtividadeComplementar> listAtividadeComplementar;

		String idModSelectedAux = tpl.getVariables().get("modalidade.id_mod");
		Integer idModSelected;
		Modalidade mod = new Modalidade();
		// System.out.println("Modalidade: "+modalidadeSelected);
		if (!idModSelectedAux.isEmpty()) {
 			idModSelected = preparaId(idModSelectedAux);
 			mod.setId_mod(idModSelected);
 			listAtividadeComplementar = cont.localizar(mod);
 
 			for (AtividadeComplementar ac : listAtividadeComplementar) {
 				tpl.setVariable("atividade_complementar.id_atividade", ac.getId_atividade());
 				tpl.setVariable("atividade_complementar.descricao_ac", ac.getDescricao_ac());
				System.out.println(ac.getDescricao_ac());
 				tpl.addBlock("descricaoAC");
				out.println(tpl.generateOutput());
 			}
 		}
	}

	private Integer preparaIdModalidade(HttpServletRequest req) {
		String id = req.getParameter("modalidade");
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	private Integer preparaId(String id) {
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	//Metodo que tirei da internet pra tentar pegar o arquivo e fazer upload
	/*private void arquivoParticipacao(HttpServletRequest req, PrintWriter out) {

		final int SIZE_MAX = 50000 * 1024 * 1024;
		final File repositorio = new File("C:/arquivosteste/");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(SIZE_MAX);
		factory.setRepository(repositorio);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest((RequestContext) req);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					String nomeArquivo = extractFilename(item.getName());
					System.out.println("item.getFieldName(): " + item.getFieldName());
					System.out.println("item.getName(): " + item.getName());
					System.out.println("item.getString(): " + item.getString());
				} else {
					String realName = extractFilename(item.getName());
					int len = 0;
					InputStream is = item.getInputStream();
					File uploadedFile = new File(repositorio + "\\" + realName);
					FileOutputStream fos = new FileOutputStream(uploadedFile);
					ByteOutputStream bos = new ByteOutputStream();
					byte[] buf = new byte[1024];
					while ((len = is.read(buf, 0, 1024)) != -1)
						bos.write(buf, 0, len);
					buf = bos.toByteArray();
					fos.write(bos.getBytes());
					fos.close();
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String extractFilename(String filePathName) {
		if (filePathName == null)
			return null;
		int dotPos = filePathName.lastIndexOf('.');
		int slashPos = filePathName.lastIndexOf('\\');
		if (slashPos == -1)
			slashPos = filePathName.lastIndexOf('/');
		if (dotPos > slashPos) {
			return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
		}
		return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
	}*/
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// private inserirParticipacao(){

	// }

	/*
	 * private Object ciraObjeto(String casoDeUso){ Object objt = casoDeUso;
	 * Class<?>cls = objt.toString().getClass();
	 * 
	 * return cls; }
	 * 
	 * private void inserirParticipacao(MiniTemplator tpl, Object objt) {
	 * Class<?> cls = objt.getClass(); for (Field field :
	 * cls.getDeclaredFields()) { String fieldName = field.getName(); String
	 * fieldValueString = ""; String methGet; if (fieldName == null) { methGet =
	 * null; } else {
	 * 
	 * methGet = fieldName.substring(0, 1).toUpperCase() +
	 * fieldName.substring(1);
	 * 
	 * Method met; Object fieldValue; try { met = cls.getMethod("get" + methGet,
	 * null); fieldValue = met.invoke(objt, null); if (fieldValue != null) {
	 * fieldValueString = fieldValue.toString(); } tpl.setVariable(fieldName,
	 * fieldValueString); } catch (NoSuchMethodException | SecurityException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * catch (IllegalAccessException | IllegalArgumentException |
	 * InvocationTargetException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * // if(fieldValue.getClass().isInstance(String.class)){ //
	 * fieldValueString = (String) fieldValue; // }
	 * 
	 * } } }
	 */
	// private salvarEvento
	// private localizarEvento
	// private excluirEvento
	// private alterarEvento

	// public void fazerAcao(MiniTemplator t, Controle ic, String acao) {

	// }

	/*public boolean insertFile( File f ){
	    Connection c = this.getConnection();//busca uma conexao com o banco
	    try {
	      PreparedStatement ps = c.prepareStatement("INSERT INTO arquivo( id, nome, arquivo ) VALUES ( nextval('seq_arquivo'), ?, ? )");

	        //converte o objeto file em array de bytes
	        InputStream is = new FileInputStream( f );
	        byte[] bytes = new byte[(int)f.length() ];
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length
	               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }

	        ps.setString( 1, f.getName() );
	        ps.setBytes( 2, bytes );
	        ps.execute();
	        ps.close();
	        c.close();
	        return true;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}*/
	
}
