package services;

import models.Avaliacao;
import models.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra);
    void removerObra(String titulo);
    void avaliarObra(String titulo, Avaliacao avaliacao);
    Vector<Obra> listarObras();
    Vector<Obra> buscarPorAutor(String autor);
    Vector<Obra> topObras();
    Vector<Obra> obrasExpostas(String nomeExposicao);
}
