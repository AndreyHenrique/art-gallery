package exceptions;

public class ObraJaCadastradaException extends RuntimeException {
    public ObraJaCadastradaException() {
        super("Já existe uma obra cadastrada com este mesmo título e autor.");
    }

    public ObraJaCadastradaException(String mensagem) {
        super(mensagem);
    }
}