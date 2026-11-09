package br.edu.urna.servico;

import br.edu.urna.dados.RepositorioVotos;
import br.edu.urna.modelo.Candidato;
import br.edu.urna.modelo.Cargo;
import br.edu.urna.modelo.Voto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ServicoVotacao {
    private final List<Cargo> cargos;
    private final RepositorioVotos repositorio;
    private final List<Voto> votosDaSessao = new ArrayList<>();
    private final String sessaoId = UUID.randomUUID().toString();
    private int indiceCargo = 0;

    public ServicoVotacao(List<Cargo> cargos, RepositorioVotos repositorio) {
        this.cargos = List.copyOf(cargos);
        this.repositorio = repositorio;
    }

    public Cargo getCargoAtual() {
        if (finalizada()) return null;
        return cargos.get(indiceCargo);
    }

    public int getIndiceCargo() {
        return indiceCargo;
    }

    public int getTotalCargos() {
        return cargos.size();
    }

    public boolean finalizada() {
        return indiceCargo >= cargos.size();
    }

    public Candidato buscarCandidato(String numero) {
        if (finalizada()) return null;
        return getCargoAtual().getCandidatos().stream()
                .filter(c -> c.getNumero().equals(numero))
                .findFirst().orElse(null);
    }

    public Voto criarVoto(String numero, String nome) {
        Cargo cargo = getCargoAtual();
        return new Voto(cargo.getNome(), cargo.getTipo(), numero, nome, sessaoId);
    }

    public void registrarVoto(Voto voto) throws Exception {
        repositorio.salvar(voto);
        votosDaSessao.add(voto);
        indiceCargo++;
    }

    public Map<String, Map<String, Integer>> resumoDaSessao() {
        Map<String, Map<String, Integer>> resumo = new LinkedHashMap<>();
        for (Voto voto : votosDaSessao) {
            resumo.putIfAbsent(voto.getCargo(), new LinkedHashMap<>());
            Map<String, Integer> porOpcao = resumo.get(voto.getCargo());
            porOpcao.merge(voto.getNomeCandidato(), 1, Integer::sum);
        }
        return resumo;
    }
}
