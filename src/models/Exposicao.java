package models;

import java.util.Vector;

public class Exposicao {
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(String nome) {
        this.obras = new Vector<>();
        this.nome = nome;
    }

    public String getNome() { return this.nome; }

    public void adicionarObra(Obra obra){
        obras.add(obra);
    }
    public Vector<Obra> listarObras(){
        return obras;
    }
}
