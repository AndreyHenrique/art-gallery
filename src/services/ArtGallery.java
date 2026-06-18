package services;

import exceptions.DadoInvalidoException;
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
        if (obra == null) {
            throw new DadoInvalidoException("Não é possível publicar uma obra nula.");
        }

        if (obra.getTitulo() == null || obra.getTitulo().trim().isEmpty()) {
            throw new DadoInvalidoException("O título da obra é obrigatório.");
        }
        if (obra.getAutor() == null || obra.getAutor().trim().isEmpty()) {
            throw new DadoInvalidoException("O autor da obra é obrigatório.");
        }

        this.repositorioObra.cadastrar(obra);
    }

    @Override
    public void removerObra(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DadoInvalidoException("É necessário informar o título para remover uma obra.");
        }

        this.repositorioObra.remover(titulo);
    }

    @Override
    public void atualizarObra(Obra obra) {
        if (obra == null) {
            throw new DadoInvalidoException("Não é possível atualizar uma obra nula.");
        }
        if (obra.getTitulo() == null || obra.getTitulo().trim().isEmpty()) {
            throw new DadoInvalidoException("O título da obra não pode ficar vazio.");
        }
        if (obra.getAutor() == null || obra.getAutor().trim().isEmpty()) {
            throw new DadoInvalidoException("O autor da obra não pode ficar vazio.");
        }

        this.repositorioObra.atualizar(obra);
    }

    @Override
    public void avaliarObra(String titulo, Avaliacao avaliacao) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new DadoInvalidoException("O título da obra deve ser informado para avaliação.");
        }
        if (avaliacao == null) {
            throw new DadoInvalidoException("A avaliação não pode ser nula.");
        }

        if (avaliacao.getUsuario() == null || avaliacao.getUsuario().trim().isEmpty()) {
            throw new DadoInvalidoException("O nome do usuário que está avaliando é obrigatório.");
        }

        Obra obraEncontrada = this.repositorioObra.buscar(titulo);
        if  (obraEncontrada == null || !obraEncontrada.isAtiva())
            throw new ObraNaoEncontradaException(
                    "Não é possível avaliar: a obra '" + titulo + "' não existe."
            );

        obraEncontrada.adicionarAvaliacao(avaliacao);
    }

    @Override
    public void criarExposicao(Exposicao exposicao) {
        if (exposicao == null) {
            throw new DadoInvalidoException("A exposição não pode ser nula.");
        }

        if (exposicao.getNome() == null || exposicao.getNome().trim().isEmpty()) {
            throw new DadoInvalidoException("O nome da exposição é obrigatório.");
        }

        this.repositorioExposicao.cadastrar(exposicao);
    }

    @Override
    public void adicionarObraEmExposicao(String nomeExposicao, String tituloObra) {
        if (nomeExposicao == null || nomeExposicao.trim().isEmpty()) {
            throw new DadoInvalidoException("O nome da exposição deve ser informado.");
        }
        if (tituloObra == null || tituloObra.trim().isEmpty()) {
            throw new DadoInvalidoException("O título da obra deve ser informado.");
        }

        Exposicao exposicao = this.repositorioExposicao.buscar(nomeExposicao);
        if (exposicao == null) {
            throw new ObraNaoEncontradaException("A exposição '" + nomeExposicao + "' não foi encontrada.");
        }

        Obra obra = this.repositorioObra.buscar(tituloObra);
        if (obra == null || !obra.isAtiva()) {
            throw new ObraNaoEncontradaException("A obra '" + tituloObra + "' não existe ou está inativa.");
        }

        exposicao.adicionarObra(obra);
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

    public IRepositorioExposicao getRepositorioExposicao() {
        return this.repositorioExposicao;
    }
}