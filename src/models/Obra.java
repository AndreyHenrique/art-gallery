package models;

import java.util.Vector;

public abstract class Obra {

    // atributos
    private String titulo;
    private String autor;
    private boolean ativa; // disponível no sistema
    private final Vector<Avaliacao> avaliacoes;

    // construtor
    Obra(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        setAtiva(true);
        this.avaliacoes = new Vector<>();
    }

    // getters
    public String getTitulo() {
        return titulo;
    }
    public String getAutor() {
        return autor;
    }
    public boolean isAtiva() {
        return ativa;
    }

    // setters
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    public void setAutor(String autor) { this.autor = autor; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    // metodos
    public void adicionarAvaliacao(Avaliacao avaliacao){
        avaliacoes.add(avaliacao);
    }

    public double mediaAvaliacoes(){
        if (avaliacoes.isEmpty()){
            return 0;
        }

        double soma = 0;
        for (Avaliacao avaliacao : avaliacoes){
            soma += avaliacao.getNota();
        }
        return soma/avaliacoes.size();
    }

    public Vector<Avaliacao> getAvaliacoes() { return new  Vector<>(avaliacoes); }

    public abstract String exibirDetalhes();
}