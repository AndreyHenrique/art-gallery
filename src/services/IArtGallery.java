package services;

import models.Avaliacao;
import models.Exposicao;
import models.Obra;

import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra);
    void removerObra(String titulo);
    void atualizarObra(Obra obra);
    void avaliarObra(String titulo, Avaliacao avaliacao);
    void criarExposicao(Exposicao exposicao);
    void adicionarObraEmExposicao(String nomeExposicao, String tituloObra);
    Vector<Obra> listarObras();
    Vector<Obra> buscarPorAutor(String autor);
    Vector<Obra> topObras();
    Vector<Obra> obrasExpostas(String nomeExposicao);
}
