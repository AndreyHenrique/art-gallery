package repositories;

import exceptions.ObraJaCadastradaException;
import exceptions.ObraNaoEncontradaException;
import models.Obra;

import java.util.Vector;

public class RepositorioObra implements IRepositorioObra {
    private final Vector<Obra> obras =  new Vector<>();

    @Override
    public void cadastrar(Obra obra) {
        for (Obra o : this.obras) {
            // Valida se o título e o autor são iguais
            if (o.getTitulo().equalsIgnoreCase(obra.getTitulo()) &&
                    o.getAutor().equalsIgnoreCase(obra.getAutor())) {
                throw new ObraJaCadastradaException("Já existe uma obra com este título para este autor.");
            }
        }
        this.obras.add(obra);
    }

    @Override
    public void atualizar(Obra obra) {
        Obra  obraEncontrada = this.buscar(obra.getTitulo());

        // Se a obra não existe
        if (obraEncontrada == null) {
            throw new ObraNaoEncontradaException("A obra '" + obra.getTitulo() + "' não existe.");
        }

        // Se a obra existe, mas não está ativa
        if (!obraEncontrada.isAtiva()) {
            throw new ObraNaoEncontradaException("A obra '" + obra.getTitulo() + "' já se encontra desativada.");
        }

        // Pega o indice e atualiza em obras com o indice
        int indice =  this.obras.indexOf(obraEncontrada);
        this.obras.set(indice, obra);
    }

    @Override
    public void remover(String titulo) {
        Obra obraEncontrada = this.buscar(titulo);

        // Se a obra não existe
        if (obraEncontrada == null) throw new ObraNaoEncontradaException("A obra '" + titulo + "' não existe.");

        // Se a obra existe, mas não está ativa
        if (!obraEncontrada.isAtiva()) throw new ObraNaoEncontradaException("A obra '" + titulo + "' já se encontra desativada.");

        // Se a obra existe, mas está ativa
        obraEncontrada.setAtiva(false);
    }

    @Override
    public Obra buscar(String titulo) {
        for (Obra obra : this.obras) {
            if (obra.getTitulo().equalsIgnoreCase(titulo)) return obra;
        }

        return null;
    }

    @Override
    public Vector<Obra> listar() {
        // cópia do vetor de obras
        return new Vector<>(this.obras);
    }
}
