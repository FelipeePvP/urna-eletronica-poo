package br.edu.urna.modelo;

import java.util.List;

public class CargoMajoritario extends Cargo {
    public CargoMajoritario(String nome, List<Candidato> candidatos) {
        super(nome, candidatos);
    }

    @Override
    public String getTipo() {
        return "MAJORITARIO";
    }
}
