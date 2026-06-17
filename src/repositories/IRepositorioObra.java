package repositories;

import models.Obra;

import java.util.Vector;

public interface IRepositorioObra {
     void cadastrar(Obra obra);
     void atualizar(Obra obra);
     void remover(String titulo);
     Obra buscar(String titulo);
     Vector<Obra> listar();
}