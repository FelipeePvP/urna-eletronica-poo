package br.edu.urna.modelo;

public class Candidato {
    private final String numero;
    private final String nome;
    private final String partido;

    public Candidato(String numero, String nome, String partido) {
        this.numero = numero;
        this.nome = nome;
        this.partido = partido;
    }

    public String getNumero() { return numero; }
    public String getNome() { return nome; }
    public String getPartido() { return partido; }
}
