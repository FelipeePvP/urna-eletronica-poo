package br.edu.urna.modelo;

import java.util.List;

public abstract class Cargo {
    private final String nome;
    private final List<Candidato> candidatos;

    protected Cargo(String nome, List<Candidato> candidatos) {
        this.nome = nome;
        this.candidatos = List.copyOf(candidatos);
    }

    public String getNome() {
        return nome;
    }

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public abstract String getTipo();
}
