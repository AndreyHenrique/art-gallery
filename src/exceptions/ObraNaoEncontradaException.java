package exceptions;

public class ObraNaoEncontradaException extends RuntimeException {
    public ObraNaoEncontradaException() {
        super("A obra informada não foi encontrada no repositório.");
    }

    public ObraNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}