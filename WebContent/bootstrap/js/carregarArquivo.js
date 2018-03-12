function enviarCertificado(){
	var arquivoCertificado = document.getElementById("certificado");
	$.post('http://localhost:8080/CadastroEventos/eventos?op=salvar', {
		certificadoServlet:arquivoCertificado
	});
}

