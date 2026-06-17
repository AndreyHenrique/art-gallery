package services;

import exceptions.ObraNaoEncontradaException;
import models.Avaliacao;
import models.Exposicao;
import models.Obra;
import repositories.IRepositorioExposicao;
import repositories.IRepositorioObra;

import java.util.Vector;

public class ArtGallery implements IArtGallery{
    private final IRepositorioObra repositorioObra;
    private final IRepositorioExposicao repositorioExposicao;

    public ArtGallery(IRepositorioObra repositorioObra, IRepositorioExposicao repositorioExposicao) {
        this.repositorioObra = repositorioObra;
        this.repositorioExposicao = repositorioExposicao;
    }

    @Override
    public void publicarObra(Obra obra) {
        // validações feitas em repositorioObra.cadastrar
        this.repositorioObra.cadastrar(obra);
    }

    @Override
    public void removerObra(String titulo) {
        this.repositorioObra.remover(titulo);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) {
        Obra obraEncontrada = this.repositorioObra.buscar(titulo);

        if  (obraEncontrada == null || !obraEncontrada.isAtiva())
            throw new ObraNaoEncontradaException(
                    "Não é possível avaliar: a obra '" + titulo + "' não existe."
            );

        obraEncontrada.adicionarAvaliacao(avaliacao);
    }

    @Override
    public Vector<Obra> listarObras() {
        Vector<Obra> todasObras = this.repositorioObra.listar();
        Vector<Obra> obrasAtivas = new Vector<>();

        for (Obra obra : todasObras) {
            if (obra.isAtiva()) obrasAtivas.add(obra);
        }

        return obrasAtivas;
    }

    @Override
    public Vector<Obra> buscarPorAutor(String autor) {
        Vector<Obra> todasObras = this.repositorioObra.listar();
        Vector<Obra> obrasAutor = new Vector<>();

        for (Obra obra : todasObras) {
            if (obra.getAutor().equalsIgnoreCase(autor)) obrasAutor.add(obra);
        }

        return obrasAutor;
    }

    @Override
    public Vector<Obra> topObras() {
        Vector<Obra> obrasOrdenadas = this.repositorioObra.listar();
        obrasOrdenadas.sort((o2, o1)
                -> Double.compare(o1.mediaAvaliacoes(), o2.mediaAvaliacoes())
        );

        return obrasOrdenadas;
    }

    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) {
        Exposicao exposicao = this.repositorioExposicao.buscar(nomeExposicao);

        if (exposicao == null) {
            return new Vector<>();
        }

        return exposicao.listarObras();
    }
}