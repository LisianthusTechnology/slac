package br.lisianthus.visao;

import java.io.IOException;

import com.google.gson.Gson;


import java.io.PrintWriter;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorAtividadeComplementar;
import br.lisianthus.controle.ControladorModalidade;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Modalidade;
import br.lisianthus.utils.ManipulaString;

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

	private Integer preparaIdModalidade(HttpServletRequest req) {
		String id = req.getParameter("id");
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	private Integer preparaId(String id) {
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

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
		//String idModSelectedAux = tpl.getVariables().get("modalidade.id_mod");
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

}
