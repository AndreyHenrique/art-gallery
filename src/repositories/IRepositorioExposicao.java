package repositories;

import java.util.Vector;
import models.Exposicao;

public interface IRepositorioExposicao {
    void cadastrar(Exposicao exposicao);
    Exposicao buscar(String nome);
    Vector<Exposicao> listar();
}