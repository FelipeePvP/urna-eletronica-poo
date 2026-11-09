package br.edu.urna.modelo;

import java.time.Instant;
import java.util.UUID;

public class Voto {
    private final String id;
    private final String cargo;
    private final String tipoCargo;
    private final String numeroCandidato;
    private final String nomeCandidato;
    private final String sessaoId;
    private final Instant dataHora;

    public Voto(String cargo, String tipoCargo, String numeroCandidato,
                String nomeCandidato, String sessaoId) {
        this.id = UUID.randomUUID().toString();
        this.cargo = cargo;
        this.tipoCargo = tipoCargo;
        this.numeroCandidato = numeroCandidato;
        this.nomeCandidato = nomeCandidato;
        this.sessaoId = sessaoId;
        this.dataHora = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getCargo() {
        return cargo;
    }

    public String getTipoCargo() {
        return tipoCargo;
    }

    public String getNumeroCandidato() {
        return numeroCandidato;
    }

    public String getNomeCandidato() {
        return nomeCandidato;
    }

    public String getSessaoId() {
        return sessaoId;
    }

    public Instant getDataHora() {
        return dataHora;
    }

}
