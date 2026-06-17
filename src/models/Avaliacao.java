package models;

import exceptions.NotaInvalidaException;

public class Avaliacao {
    private final String usuario;
    private final int nota;
    private final String comentario;

    public Avaliacao(String usuario, int nota, String comentario) {
        if (nota >= 0 && nota <= 10) {
            this.usuario = usuario;
            this.nota = nota;
            this.comentario = comentario;
        }
        else {
            throw new NotaInvalidaException();
        }
    }

    public double getNota() {
        return nota;
    }
    public String getUsuario() { return usuario; }
    public String getComentario() { return comentario; }

    public String exibirAvaliacao() {
        return "   * [" + this.getUsuario() + "] Nota: " + this.getNota() + " - \"" + this.getComentario() + "\"";
    }
}
