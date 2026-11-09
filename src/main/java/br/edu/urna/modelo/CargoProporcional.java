package br.edu.urna.modelo;

import java.util.List;

public class CargoProporcional extends Cargo {
    public CargoProporcional(String nome, List<Candidato> candidatos) {
        super(nome, candidatos);
    }

    @Override
    public String getTipo() {
        return "PROPORCIONAL";
    }
}
