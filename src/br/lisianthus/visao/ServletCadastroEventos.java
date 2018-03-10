package br.lisianthus.visao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
import br.lisianthus.dao.DAOModalidade;
import br.lisianthus.dao.DAOParticipacao;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.modelo.Participacao;
import sun.security.jca.GetInstance.Instance;

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
	 * M�todo respons�vel por executar as a��es das p�ginas, bem como dizer para
	 * o Minitemplator que p�gina deve ser aberta.
	 * 
	 * @param req
	 *            o request � enviado pelo cliente e � atrav�s dele que
	 *            conseguimos pegar a p�gina que o cliente solicitou.
	 * @param resp
	 *            o response � utilizado para responder o cliente atrav�s da
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

		}

		Class<?> cls;
		try {
			cls = Class.forName("br.lisianthus.visao.ServletCadastroEventos");
			Class[] parameterTypes = new Class[2];
			parameterTypes[0] = HttpServletRequest.class;
			parameterTypes[1] = PrintWriter.class;
			// HttpServletRequest request = req;
			PrintWriter print = new PrintWriter(out);

			Object[] obj = new Object[2];
			obj[0] = req;
			obj[1] = print;

			// System.out.println("Metodo: " + nomeMetodo);

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

	public MiniTemplator getMiniTemplator(String op) throws TemplateSyntaxException, IOException {
		String caminhoAmigoIndex = realPath + separador + "WEB-INF" + separador + "eventos" + separador + "evento_" + op
				+ ".html";
		System.out.println("CaminhoAmigoIndex:" + caminhoAmigoIndex);
		MiniTemplator tpl = new MiniTemplator(caminhoAmigoIndex);
		return tpl;
	}

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

	private Modalidade getModalidadeFromRequest(HttpServletRequest req) {

		Integer id_mod = null;
		String erroMessage = "";
		try {
			id_mod = preparaIdModalidade(req);
		} catch (NumberFormatException e) {
			erroMessage = "Campo ID em formato inv�lido, aceita somente n�mero!<BR>\n";
		}

		String nome_mod = req.getParameter("nome");
		nome_mod = nome_mod != null ? nome_mod : "";

		if (!erroMessage.equals("")) {
			throw new RuntimeException(erroMessage);
		}

		Modalidade a = new Modalidade(id_mod, nome_mod);
		return a;
	}

	private Integer preparaIdModalidade(HttpServletRequest req) {
		String id = req.getParameter("id");
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	private Integer preparaId(String id) {
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	//Metodo que tirei da internet pra tentar pegar o arquivo e fazer upload
	private void arquivoParticipacao(HttpServletRequest req, PrintWriter out, MiniTemplator tpl) {

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
	}

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

}
