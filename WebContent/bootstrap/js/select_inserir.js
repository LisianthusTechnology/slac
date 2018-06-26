function carregarAtividades(idModalidade) {
	if (idModalidade === "") {

	} else {
		$.post('http://localhost:8080/CadastroEventos/eventos?op=json', {
			idModalidadeServlet : idModalidade
		}, function(response) {

			var combo = document.getElementById("descricaoAtividadeEvento");

			combo.options.length = 0;

			Object.keys(response).forEach(function(item) {
				var descricao_atividade = response[item]['descricao_ac'];
				var id = response[item]['id_atividade'];

				var option = document.createElement('option');
				option.value = id;
				option.text = descricao_atividade;

				combo.add(option);
			});

		}, 'json').fail(function(error) {
			console.log("Error de listar atividades: " + error.responseText);
		});
	}
}

function carregarCHAtividade() {

}

function carregarCertificado() {

	var x = document.getElementById("arquivo");
	var txt = "";

	if ('files' in x) {
		if (x.files.length == 0) {
			txt = "Select one or more files.";
		} else {
			for (var i = 0; i < x.files.length; i++) {
				txt += "<br><strong>" + (i + 1) + ". file</strong><br>";
				var file = x.files[i];
				if ('name' in file) {
					txt = file.name;
				}
			}
			$
					.post(
							'http://localhost:8080/CadastroEventos/eventos?op=certificado',
							{
								arquivoServlet : txt
							});
		}
	}

}

function validar() {
	var descricao = formac.descricaoac.value;

	if (formac.modalidade.selectedIndex == 0) {
		alert('Preencha os campos obrigatórios do formulário abaixo!');
		formac.modalidade.focus();
		return false;
	}
	if (descricao == "") {
		alert('Preencha os campos obrigatórios do formulário abaixo!');
		formac.descricaoac.focus();
		return false;
	}
}

function validarCamposCadastro() {
	if (formcadastro.matricula.value == "" || formcadastro.email.value == ""
			|| formcadastro.anocurso.value == ""
			|| formcadastro.nome.value == "" || formcadastro.cpf.value == ""
			|| formcadastro.senha.value == "") {
		alert('Preencha os campos obrigatórios do formulário abaixo!');
		return false;
	}else{
		cpf = formcadastro.cpf.value;
		return TestaCPF(cpf);
	}
}

function TestaCPF(strCPF) {
	var Soma;
	var Resto;
	Soma = 0;
	if (strCPF == "00000000000"){
		alert('O CPF informado é inválido. Por favor, informe um CPF válido.');
		return false;
		}
	for (i = 1; i <= 9; i++){
		Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (11 - i);
	}
	Resto = (Soma * 10) % 11;
	if ((Resto == 10) || (Resto == 11)){
		Resto = 0;
	}
	if (Resto != parseInt(strCPF.substring(9, 10))){
		alert('O CPF informado é inválido. Por favor, informe um CPF válido.');
		return false;
	}
	Soma = 0;
	for (i = 1; i <= 10; i++){
		Soma = Soma + parseInt(strCPF.substring(i - 1, i)) * (12 - i);
	}
	Resto = (Soma * 10) % 11;
	if ((Resto == 10) || (Resto == 11)){
		Resto = 0;
	}
	if (Resto != parseInt(strCPF.substring(10, 11))){
		alert('O CPF informado é inválido. Por favor, informe um CPF válido.');
		return false;
	}
	return true;
}


function validarCamposEvento() {
	arquivo = formevento.certificado.value;
	idAtividade = formevento.descricaoAC.value;
	extensao = ".pdf";
	if (formevento.nomeEvento.value == ""
			|| formevento.chCertificado.value == ""
			|| formevento.localEvento.value == ""
			|| formevento.dataInicioEvento.value == ""
			|| formevento.modalidade.selectedIndex == 0
			|| formevento.tipoEvento.selectedIndex == 0
			|| formevento.descricaoAC.selectedIndex == 1) {
		alert('Preencha os campos obrigatórios do formulário abaixo!');
		return false;
	}
	if (!arquivo) {
		alert('É necessário inserir o certificado!');
		return false;
	} else {
		extensaoArquivo = (arquivo.substring(arquivo.lastIndexOf(".")))
				.toLowerCase();
		if (extensaoArquivo != extensao) {
			alert('O certificado deve ser um PDF!');
			return false;
		}
	}
	if (idAtividade === "") {

	} else {
		$
				.post(
						'http://localhost:8080/CadastroEventos/eventos?op=buscarCH',
						{
							idAtividadeComplementar : idAtividade
						},
						function(response) {
							if (formevento.chCertificado.value < response['ch_min_ac']) {
								alert('A carga horária mínima desta atividade é de '
										+ response['ch_min_ac']
										+ " horas por favor informe uma carga horária válida!");
								return false;
							}

						}, 'json').fail(
						function(error) {
							console.log("Error ao buscar Carga Horaria: "
									+ error.responseText);
						});
		return false;
	}
}