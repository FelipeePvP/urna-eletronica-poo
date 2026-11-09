package br.edu.urna.dados;

import br.edu.urna.modelo.Voto;

public interface RepositorioVotos {
    void salvar(Voto voto) throws Exception;
}
