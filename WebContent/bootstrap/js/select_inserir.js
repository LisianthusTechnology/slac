function carregarAtividades(idModalidade){
	if(idModalidade === ""){
		
	}else{
		$.post('http://localhost:8080/CadastroEventos/eventos?op=json', {
			idModalidadeServlet:idModalidade
		}, function(response){
			
			 var combo = document.getElementById("descricaoAtividadeEvento");
			
			 combo.options.length = 0;
			 
			 Object.keys(response).forEach(function (item) {
			        var descricao_atividade = response[item]['descricao_ac'];
			        var id = response[item]['id_atividade'];
			        
			        var option = document.createElement('option');
			        option.value = id;
			        option.text = descricao_atividade;
			        
			        combo.add(option);
			    });

		}, 'json').fail(function(error){
			console.log("Error de listar atividades: " + error.responseText);
		});
	}
}