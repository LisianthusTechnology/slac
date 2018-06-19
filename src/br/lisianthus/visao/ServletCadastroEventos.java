package br.lisianthus.visao;

import java.io.File;

import java.io.IOException;
import com.google.gson.Gson;

import java.io.PrintWriter;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.source_code.miniTemplator.MiniTemplator;
import biz.source_code.miniTemplator.MiniTemplator.TemplateSyntaxException;
import br.lisianthus.controle.ControladorAluno;
import br.lisianthus.controle.ControladorAtividadeComplementar;
import br.lisianthus.controle.ControladorModalidade;
import br.lisianthus.controle.ControladorParticipacao;
import br.lisianthus.modelo.Aluno;
import br.lisianthus.modelo.AtividadeComplementar;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.modelo.Modalidade;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.lisianthus.modelo.Participacao;
import br.lisianthus.utils.Retorno;
import net.sf.jasperreports.engine.JRException;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
		try {
			executaPagina(request, response);
		} catch (TemplateSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @throws JRException
	 */

	private void executaPagina(HttpServletRequest req, HttpServletResponse resp)
			throws TemplateSyntaxException, IOException, JRException {

		String nomeMetodo = null;
		String op = req.getParameter("op");
		PrintWriter out = resp.getWriter();
		op = op == null ? "index" : op;

		if (op.equalsIgnoreCase("index") || op.equalsIgnoreCase("inserir")) {
			MiniTemplator tpl = getMiniTemplator(op);

			if (op.equalsIgnoreCase("inserir")) {
				buscaDadosAluno(tpl, req);
				listarModalidade(req, out, tpl);
			} else if (op.equalsIgnoreCase("index")){
				buscaDadosAluno(tpl, req);
				out.println(tpl.generateOutput());
			}else {
				out.println(tpl.generateOutput());
			}
		}else{			
			nomeMetodo = op + "Participacao";
			System.out.println(nomeMetodo);
			cargahorariatotal();
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
	// }

	private void buscaDadosAluno(MiniTemplator tpl, HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		Aluno aluno = (Aluno) session.getAttribute("Aluno");
		tpl.setVariable("nome_aluno", aluno.getNome_aluno());
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

	private Aluno getAlunoFromRequest(HttpServletRequest req) {
		Integer alunoid = null;

		String erroMessage = "";
		try {
			alunoid = preparaIdAluno(req);
		} catch (NumberFormatException e) {
			erroMessage = "Campo ID em formato inválido, aceita somente número!<BR>\n";
		}

		if (!erroMessage.equals("")) {
			throw new RuntimeException(erroMessage);
		}

		Aluno alu = new Aluno(alunoid);
		return alu;

	}

	public void salvarParticipacao(HttpServletRequest req, PrintWriter out) throws IOException {
		// Participacao participacao = new Participacao();
		// receiveFile(req);
		MiniTemplator t = getMiniTemplator("message");
		Retorno ret = new Retorno();

		ret = receiveFile(req);

		t.setVariable("message", ret.getMensagem());

		out.println(t.generateOutput());
	}

	public void opcoescoordParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
			MiniTemplator tpl = this.getMiniTemplator("opcoes_coord");
			buscaDadoCoord(req, tpl, out);
			out.println(tpl.generateOutput());
		
	}
	
	public void dataRelatorioParticipacao(HttpServletRequest req, PrintWriter out) throws TemplateSyntaxException, IOException{
		MiniTemplator tpl = this.getMiniTemplator("datas_relatorio");
		out.println(tpl.generateOutput());
	}
	
	private void buscaDadoCoord(HttpServletRequest req, MiniTemplator tpl, PrintWriter out){
		HttpSession session = req.getSession(true);
		String logado = (String) session.getAttribute("loggedIn");
		if(logado.equalsIgnoreCase("true")){
			Coordenador coord = (Coordenador) session.getAttribute("Coordenador");	
			tpl.setVariable("nome_coord", coord.getNome());
		}
	}

	public void alunoscoordParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = this.getMiniTemplator("alunos_coord");
		listarAlunosValidacao(req, out, tpl);
		out.println(tpl.generateOutput());

	}

	public void listarativcoordParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = this.getMiniTemplator("listar_ativ_coord");
		listarAtividadesParaCoord(req, out, tpl);
		out.println(tpl.generateOutput());
	}

	public void listarAtividadesParaCoord(HttpServletRequest req, PrintWriter out, MiniTemplator tpl)
			throws IOException {
		ControladorParticipacao controlePart = new ControladorParticipacao();
		Participacao part = new Participacao();
		// String auxPart = req.getParameter("pesquisaPart");
		// part.setNome_ac_part(auxPart);
		List<Participacao> listaPart = controlePart.listarParticipacaoConsulta(part);
		for (Participacao p : listaPart) {
			if (p.getStatus().equalsIgnoreCase("A validar")) {
				tpl.setVariable("id_part", p.getId_participacao());
				tpl.setVariable("chComputada", p.getCh_validada_part());
				tpl.setVariable("chCadastrada", p.getCh_cadastrada_part());
				tpl.setVariable("partCadastrada", p.getNome_ac_part());
				tpl.setVariable("validacao", p.getStatus());
				tpl.addBlock("manterparticipacao");
			}
		}
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
		// listarAtividades(req, out, tpl);
	}

	private void listarAlunosValidacao(HttpServletRequest req, PrintWriter out, MiniTemplator tpl) throws IOException {
		ControladorAluno ctAluno = new ControladorAluno();
		Aluno aluno = new Aluno();

		List<Aluno> listaAlunos = ctAluno.localizar(aluno);

		for (Aluno al : listaAlunos) {
			tpl.setVariable("id_aluno", al.getId_aluno());
			tpl.setVariable("nome", al.getNome_aluno());
			tpl.setVariable("cpf", al.getCpf().toString());
			tpl.setVariable("status", permissao(al.isPermissao()));
			tpl.addBlock("manteraluno");
		}
		// out.println(tpl.generateOutput());
	}

	public String permissao(boolean status) {
		String stt;
		if (status) {
			stt = "VALIDADO";
		} else {
			stt = "INVALIDADO";
		}
		return stt;
	}

	public void validacaoparticipacaoParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		String op_validacao = req.getParameter("part_status");
		System.out.println("op validacao: " + op_validacao);
		ControladorParticipacao ctp = new ControladorParticipacao();
		Participacao part = getParticipacaoFromRequest(req);
		part = ctp.obterParticipacao(part.getId_participacao());
		System.out.println("Id da ativiade: " + part.getId_participacao());
		Retorno ret = null;
		if (part != null) {
			if (op_validacao.equalsIgnoreCase("validar")) {
				System.out.println("Nome da ativiade: " + part.getNome_ac_part());
				part.setStatus("VALIDADO");
			} else {
				part.setStatus("INVALIDADO");// VER COMO COLOCAR A OBSERVAÇÃO DO
												// COORDENADO
			}
		}
		MiniTemplator t = getMiniTemplator("message");
		ret = ctp.alterarParticipacao(part);
		t.setVariable("message", ret.getMensagem());
		System.out.println("Mensagem: " + ret.getMensagem());
		out.println(t.generateOutput());
	}

	private Participacao getParticipacaoFromRequest(HttpServletRequest req) {
		Integer partid = null;

		String erroMessage = "";
		try {
			partid = preparaIdParticipacao(req);
		} catch (NumberFormatException e) {
			erroMessage = "Campo ID em formato inválido, aceita somente número!<BR>\n";
		}

		if (!erroMessage.equals("")) {
			throw new RuntimeException(erroMessage);
		}

		Participacao parti = new Participacao(partid);
		return parti;

	}

	// PEGA AS AÇÕES QUE O COORDENADOR TEM NA LISTA DE ALUNOS PARA VALIDAR OU
	// INVALIDAR
	public void acoesParticipacao(HttpServletRequest req, PrintWriter out) throws TemplateSyntaxException, IOException {
		String op = req.getParameter("op");
		System.out.println("opcao:" + op);

		ControladorAluno ctAluno = new ControladorAluno();
		Aluno aluno = getAlunoFromRequest(req);
		Retorno ret = null;
		String status = req.getParameter("aluno_status");
		System.out.println("status:" + status.toString());
		if (status.equalsIgnoreCase("validar")) {
			aluno.setPermissao(true);
			ret = ctAluno.alterar(aluno);
	
			System.out.println("Retorno:" + ret.getMensagem());
			MiniTemplator t = getMiniTemplator("message");

			t.setVariable("message", ret.getMensagem());

			out.println(t.generateOutput());

		} else {
			aluno.setPermissao(false);
			// System.out.println("Denrto da
			// servlet"+ctAluno.obter(aluno.getId_aluno())+"permissao:"+aluno.getPermissao());
			ret = ctAluno.alterar(aluno);
			// System.out.println(ctAluno.obter(aluno.getId_aluno())+"permissao
			// 2:"+aluno.getPermissao());
			System.out.println("Retorno:" + ret.getMensagem());
			MiniTemplator t = getMiniTemplator("message");

			t.setVariable("message", ret.getMensagem());

			out.println(t.generateOutput());
		}

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

	private Integer preparaIdAluno(HttpServletRequest req) {
		String id = req.getParameter("id_aluno");// colocar type hidden
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	private Integer preparaIdParticipacao(HttpServletRequest req) {
		String id = req.getParameter("id_part");// colocar type hidden
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	private Integer preparaId(String id) {
		Integer idInteger = id != null && !id.equals("") ? Integer.valueOf(id) : null;
		return idInteger;
	}

	// Metodo que tirei da internet pra tentar pegar o arquivo e fazer upload

	private Retorno receiveFile(HttpServletRequest req) {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		File dir = new File(req.getContextPath() + "certificado");
		Participacao part = new Participacao();
		Retorno ret = new Retorno();
		ControladorParticipacao controle = new ControladorParticipacao();

		if (dir.mkdir()) {
			System.out.println("Diretorio criado com sucesso!" + dir.getPath());
		} else {
			System.out.println("Erro ao criar diretorio!");
		}

		try {
			List<?> items = upload.parseRequest(req);

			Iterator<?> itr = items.iterator();

			//part.setAluno_id_aluno(1);
			//part.setCoordenador_ac_id_admin(1);
			//part.setCh_validada_part(30);
			part.setStatus("A VALIDAR");

			while (itr.hasNext()) {

				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					String campo = item.getFieldName();
					String valor = item.getString();

					if (campo.equalsIgnoreCase("nomeEvento")) {
						part.setNome_ac_part(valor);
					} else if (campo.equals("chCertificado")) {
						part.setCh_cadastrada_part(preparaId(valor));
					} else if (campo.equalsIgnoreCase("localEvento")) {
						part.setLocal_ac_part(valor);
					} else if (campo.equals("tipoEvento")) {
						part.setTipo_ac_part(valor);
					} else if (campo.equals("descricaoAC")) {
						part.setAtividade_complementar_id_atividade(preparaId(valor));
					} else if (campo.equals("dataInicioEvento")) {
						String dataEmUmFormato = valor;
						SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy");
						Date data = formato.parse(dataEmUmFormato);
						part.setData_inicio_ac_part(data);
					}

				} else {

					File file = new File(dir, item.getName());
					item.write(file);
					part.setCertificado_part(file.getPath());
					System.out.println("<br/>Arquivo gravado em: " + file.getPath());
				}

			}

			if (part != null) {
				System.out.println("Participacao:" + part.getAtividade_complementar_id_atividade() + ", "
						+ part.getAluno_id_aluno() + "," + part.getId_participacao() + "," + part.getCertificado_part()
						+ "," + part.getCoordenador_ac_id_admin());

				ret = controle.inserir(part);

				System.out.println("Retorno:" + ret.getMensagem());
			}

		} catch (Exception e) {

			System.out.println("Erro:" + e);
		}

		return ret;
	}

	/*
	 * executar este metodo quando executar a aplicação, pq ai quando executar o
	 * código ele ja faz a verificação e bloqueia o aluno impedindo de cadastrar
	 * uma nova atividade
	 */

	public void cargahorariatotal() {

		Aluno aluno = new Aluno();
		Participacao participacao = new Participacao();
		Date data_conclusao_part = new Date();
		Retorno ret_aluno = new Retorno();
		ControladorAluno controle_aluno = new ControladorAluno();
		ControladorParticipacao controle_part = new ControladorParticipacao();

		participacao = controle_part.verifica_carga_horaria();
		aluno = controle_aluno.verifica_se_tem_dataconclusao(participacao.getAluno_id_aluno());
		System.out.println("Dentro da carga horaria:" + participacao.getCh_validada_part());
		
		if (participacao.getCh_validada_part() >= 200 && aluno.getData_conclusao_carga() == null){
			// bloqueio do cadastro da participacao
			System.out.println("ID do ALUNO: " + participacao.getAluno_id_aluno() + " Data: " + data_conclusao_part);

			aluno.setData_carga_total_part(data_conclusao_part);
			aluno.setId_aluno(participacao.getAluno_id_aluno());
			System.out.println("Inserção da data: " + controle_aluno.inserir_data_conclusao(aluno).getMensagem());
		}

	}
	
	public void verificadataParticipacao(HttpServletRequest req, PrintWriter out) throws ParseException, JRException, IOException{
		boolean data;
		String inicio = req.getParameter("dtRelatorioInicio");
		String fim  = req.getParameter("dtRelatorioFinal");
		
		//System.out.println("Teste das datas:"+inicio+", "+fim);
		SimpleDateFormat formatodata = new SimpleDateFormat("yyyy-mm-dd");
		//SimpleDateFormat formatodatafim = new SimpleDateFormat("yyyy-mm-dd");
		Date inicio_data_conversao = formatodata.parse(inicio);
		Date fim_data_conversao = formatodata.parse(fim);
		//System.out.println("Teste das datas conversao:"+formatodata.format(inicio_data_conversao)+", "+formatodata.format(fim_data_conversao));
		
		if (inicio_data_conversao.before(fim_data_conversao)){
			data = true;
		}else if (inicio_data_conversao.after(fim_data_conversao)){
			data = false;
		}else{
			data = true;
		}
		if(data){
			gerarRelatorio(inicio_data_conversao, fim_data_conversao);
			//System.out.println("Teste das datas:"+inicio_data_conversao+", "+fim_data_conversao);
		}
	//	return data;
		
	}


	public void gerarRelatorio(Date inicio, Date fim) throws JRException, IOException {

		String caminhoRelatorio = "C:/Users/Eloisa/Downloads/slac-master/WebContent/jasper/novoRelatorioAlunos.jrxml";
	
		ControladorAluno contraluno = new ControladorAluno();
		
		JasperReport report = JasperCompileManager.compileReport(caminhoRelatorio);

		List<Aluno> relatorio = contraluno.listaRelatorio(inicio, fim);

		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(relatorio));

		JasperExportManager.exportReportToPdfFile(print, "Relatorio_Alunos.pdf");
		
		 JasperViewer.viewReport(print, false);

	}

	public void consultaParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = getMiniTemplator("consulta");
		listarParticipacao(req, out, tpl);
		out.println(tpl.generateOutput());
	}

	private void listarParticipacao(HttpServletRequest req, PrintWriter out, MiniTemplator tpl) {
		ControladorParticipacao controlePart = new ControladorParticipacao();
		Participacao part = new Participacao();
		String auxPart = req.getParameter("pesquisaPart");
		part.setNome_ac_part(auxPart);

		int totalChComputada = 0;
		List<Participacao> listaPart = controlePart.listarParticipacaoConsulta(part);

		for (Participacao p : listaPart) {
			// if(p.getStatus().equalsIgnoreCase("A validar")) NÃO SOMA O VALOR
			// DA CARGA HORÁRIA
			if(p.getStatus().equalsIgnoreCase("VALIDADO")){
				tpl.setVariable("id_part", p.getId_participacao());
				tpl.setVariable("chComputada", p.getCh_validada_part());
				tpl.setVariable("chCadastrada", p.getCh_cadastrada_part());
				tpl.setVariable("partCadastrada", p.getNome_ac_part());
				tpl.setVariable("validacao", p.getStatus());
					totalChComputada += p.getCh_validada_part();
				tpl.addBlock("manterparticipacao");
				
			}else if(p.getStatus().equalsIgnoreCase("A VALIDAR")){
				tpl.setVariable("id_part", p.getId_participacao());
				tpl.setVariable("chComputada", p.getCh_validada_part());
				tpl.setVariable("chCadastrada", p.getCh_cadastrada_part());
				tpl.setVariable("partCadastrada", p.getNome_ac_part());
				tpl.setVariable("validacao", p.getStatus());
				tpl.addBlock("participacaovalidar");
			}else{
				tpl.setVariable("id_part", p.getId_participacao());
				tpl.setVariable("chComputada", p.getCh_validada_part());
				tpl.setVariable("chCadastrada", p.getCh_cadastrada_part());
				tpl.setVariable("partCadastrada", p.getNome_ac_part());
				tpl.setVariable("validacao", p.getStatus());
				
				tpl.addBlock("participacaoinvalidado");
			}
			
			
		}
		tpl.setVariable("totalChComputada", totalChComputada);
		

	}

	public void visualizarcoordParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = getMiniTemplator("validar_ativ_coord");
		buscarParticipacao(req, tpl);
		out.println(tpl.generateOutput());
	}

	public void visualizarParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = getMiniTemplator("visualizar");
		buscarParticipacao(req, tpl);
		out.println(tpl.generateOutput());
	}

	private void buscarParticipacao(HttpServletRequest req, MiniTemplator tpl) {
		ControladorParticipacao controlePart = new ControladorParticipacao();
		ControladorAtividadeComplementar contAc = new ControladorAtividadeComplementar();
		ControladorModalidade contMod = new ControladorModalidade();
		AtividadeComplementar ac = new AtividadeComplementar();
		Modalidade mod = new Modalidade();

		int id = preparaId(req.getParameter("id_part"));
		Participacao part = new Participacao();
		part.setId_participacaoo(id);

		List<Participacao> listaPart = controlePart.buscarParticipacao(part);
		for (Participacao p : listaPart) {
			tpl.setVariable("id_participacao", p.getId_participacao());
			tpl.setVariable("nomeEvento", p.getNome_ac_part());
			tpl.setVariable("chCertificado", p.getCh_cadastrada_part());
			tpl.setVariable("localEvento", p.getLocal_ac_part());
			tpl.setVariable("dataInicio", p.getData_inicio_ac_part().toString());
			ac = contAc.obter(p.getAtividade_complementar_id_atividade());
			mod = contMod.obterMod(ac.getModalidade_id_mod());
			tpl.setVariable("modalidadeEvento", mod.getNome_mod());
			tpl.setVariable("certificado", p.getCertificado_part());
			tpl.setVariable("tipoEvento", p.getTipo_ac_part());
			tpl.setVariable("descricaoEvento", ac.getDescricao_ac());
		}

	}

	public void cadastrarACParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator tpl = getMiniTemplator("cadastrar_atividade_complementar");
		listarModalidade(req, out, tpl);
	}

	public void salvarACParticipacao(HttpServletRequest req, PrintWriter out)
			throws TemplateSyntaxException, IOException {
		MiniTemplator t = getMiniTemplator("message");
		AtividadeComplementar ac = new AtividadeComplementar();
		ControladorAtividadeComplementar controleAC = new ControladorAtividadeComplementar();
		Retorno ret = null;

		if (ac != null) {
			ac.setCh_max_ac(preparaId(req.getParameter("chmaxima")));
			ac.setCh_min_ac(preparaId(req.getParameter("chminima")));
			ac.setDescricao_ac(req.getParameter("descricaoac"));
			ac.setModalidade_id_mod(preparaId(req.getParameter("modalidade")));
			ret = controleAC.inserir(ac);
		}

		System.out.println(ret.getMensagem());
		t.setVariable("message", ret.getMensagem());
		out.println(t.generateOutput());

	}
}
