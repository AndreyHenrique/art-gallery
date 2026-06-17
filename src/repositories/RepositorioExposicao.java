package repositories;

import java.util.Vector;
import models.Exposicao;

public class RepositorioExposicao implements IRepositorioExposicao {

    private Vector<Exposicao> exposicoes = new Vector<>();

    @Override
    public void cadastrar(Exposicao exposicao) {
        if (exposicao != null && buscar(exposicao.getNome()) == null) {
            exposicoes.add(exposicao);
        }
    }

    @Override
    public Exposicao buscar(String nome) {
        for (Exposicao expo : exposicoes) {
            if (expo.getNome().equalsIgnoreCase(nome)) {
                return expo;
            }
        }
        return null;
    }

    @Override
    public Vector<Exposicao> listar() {
        return new Vector<>(exposicoes);
    }
}