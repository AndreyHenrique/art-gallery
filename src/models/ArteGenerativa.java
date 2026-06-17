package models;

public class ArteGenerativa extends Obra {
    private String algoritmo;
    private Long seed;

    public ArteGenerativa(String titulo, String autor, String algoritmo, Long seed) {
        super(titulo, autor);
        this.algoritmo = algoritmo;
        this.seed = seed;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + getTitulo() + "\n" +
                "Autor: " + getAutor() + "\n" +
                "Tipo: Modelagem 3D\n" +
                "Algoritmo: " + this.algoritmo + "\n" +
                "Seed: " + this.seed;
    }
}
