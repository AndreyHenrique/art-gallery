package exceptions;

public class NotaInvalidaException extends RuntimeException {
    public NotaInvalidaException() {
        super("A nota deve estar obrigatoriamente entre 0 e 10.");
    }

    public NotaInvalidaException(String mensagem) {
        super(mensagem);
    }
}