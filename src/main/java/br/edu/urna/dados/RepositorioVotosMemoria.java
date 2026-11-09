package br.edu.urna.dados;

import br.edu.urna.modelo.Voto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioVotosMemoria implements RepositorioVotos {
    private final List<Voto> votos = new ArrayList<>();

    @Override
    public synchronized void salvar(Voto voto) {
        votos.add(voto);
    }

    public synchronized List<Voto> getVotos() {
        return Collections.unmodifiableList(new ArrayList<>(votos));
    }
}
