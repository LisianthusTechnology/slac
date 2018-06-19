package br.lisianthus.controle;


import br.lisianthus.dao.DAOCoordenador;
import br.lisianthus.modelo.Coordenador;
import br.lisianthus.utils.Retorno;

public class ControladorCoordenador {

	private DAOCoordenador daocoord;

	public ControladorCoordenador() {
		daocoord = DAOCoordenador.getInstance();
	}

	public Retorno inserir(Coordenador coord) {

		Retorno ret = new Retorno();
		ret = daocoord.inserir(coord);
		return ret;
	}

	public Coordenador obter(Coordenador coord) {
		Coordenador coordenador = new Coordenador();
		coordenador = daocoord.obter(coord);
		return coordenador;
		}
}


