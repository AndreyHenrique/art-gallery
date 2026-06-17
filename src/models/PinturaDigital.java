package models;

public class PinturaDigital extends Obra{
    private String resolucao;
    private String softwareUtilizado;

    public PinturaDigital(String titulo, String autor, String resolucao, String softwareUtilizado) {
        super(titulo, autor);
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + getTitulo() + "\n" +
                "Autor: " + getAutor() + "\n" +
                "Tipo: Pintura Digital\n" +
                "Resolução: " + this.resolucao + "\n" +
                "Software: " + this.softwareUtilizado;
    }
}