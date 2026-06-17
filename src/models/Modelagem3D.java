package models;

public class Modelagem3D extends Obra{
    private final int numeroPolignos;
    private final String engine;

    public Modelagem3D(String titulo, String autor, int numeroPolignos, String engine) {
        super(titulo, autor);
        this.numeroPolignos = numeroPolignos;
        this.engine = engine;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + getTitulo() + "\n" +
                "Autor: " + getAutor() + "\n" +
                "Tipo: Modelagem 3D\n" +
                "Polígnos: " + this.numeroPolignos + "\n" +
                "Engine: " + this.engine;
    }
}
