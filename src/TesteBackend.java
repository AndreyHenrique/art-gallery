import exceptions.*;
import models.*;
import repositories.*;
import services.*;
import java.util.Vector;

public class TesteBackend {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTES DO BACKEND ARTGALLERY ===\n");

        // 1. Inicialização do Ambiente
        IRepositorioObra repoObra = new RepositorioObra();
        IRepositorioExposicao repoExpo = new RepositorioExposicao();
        IArtGallery sistema = new ArtGallery(repoObra, repoExpo);

        // ==========================================
        // TESTE 1: Cadastro de Obras e Polimorfismo
        // ==========================================
        System.out.print("Teste 1: Cadastrando diferentes tipos de obras... ");
        try {
            Obra p1 = new PinturaDigital("Aurora Neon", "Alice", "4K", "Photoshop");
            Obra m1 = new Modelagem3D("Cyber Dragon", "Bruno", 50000, "Blender");
            Obra g1 = new ArteGenerativa("Chaos Waves", "Carla", "Perlin Noise", 98231L);

            sistema.publicarObra(p1);
            sistema.publicarObra(m1);
            sistema.publicarObra(g1);
            System.out.println("OK! (3 obras cadastradas)");
        } catch (Exception e) {
            System.out.println("FALHOU: " + e.getMessage());
        }

        // ==========================================
        // TESTE 2: Restrição de Obra Duplicada
        // ==========================================
        System.out.print("Teste 2: Forçando cadastro duplicado (Mesmo título e autor)... ");
        try {
            Obra duplicada = new PinturaDigital("Aurora Neon", "Alice", "1080p", "Krita");
            sistema.publicarObra(duplicada);
            System.out.println("FALHOU (O sistema aceitou duplicata!)");
        } catch (ObraJaCadastradaException e) {
            System.out.println("OK! (Exceção capturada com sucesso: " + e.getMessage() + ")");
        }

        // ==========================================
        // TESTE 3: Avaliações e Validação de Notas
        // ==========================================
        System.out.print("Teste 3: Adicionando avaliações válidas e testando médias... ");
        try {
            // Adicionando notas para Aurora Neon (Alice)
            sistema.avaliarObra("Aurora Neon", new Avaliacao("User1", 8, "Ótimo uso de cores!"));
            sistema.avaliarObra("Aurora Neon", new Avaliacao("User2", 10, "Incrível, sensacional."));

            // Adicionando nota para Cyber Dragon (Bruno)
            sistema.avaliarObra("Cyber Dragon", new Avaliacao("ArtLover", 9, "Malha 3D muito limpa."));

            Obra aurora = repoObra.buscar("Aurora Neon");
            if (aurora.mediaAvaliacoes() == 9.0) {
                System.out.println("OK! (Média calculada corretamente: 9.0)");
            } else {
                System.out.println("FALHOU (Média incorreta: " + aurora.mediaAvaliacoes() + ")");
            }
        } catch (Exception e) {
            System.out.println("FALHOU com erro inesperado: " + e.getMessage());
        }

        System.out.print("Teste 3.1: Forçando nota inválida (maior que 10)... ");
        try {
            sistema.avaliarObra("Cyber Dragon", new Avaliacao("Hater", 11, "Ruim"));
            System.out.println("FALHOU (O sistema aceitou nota 11!)");
        } catch (NotaInvalidaException e) {
            System.out.println("OK! (Exceção capturada com sucesso: " + e.getMessage() + ")");
        }

        // ==========================================
        // TESTE 4: Desativação e Listagem de Ativas
        // ==========================================
        System.out.print("Teste 4: Testando remoção (desativação) e listagem... ");
        try {
            sistema.removerObra("Chaos Waves"); // Desativa a obra da Carla

            Vector<Obra> ativas = sistema.listarObras();
            if (ativas.size() == 2) {
                System.out.println("OK! (Apenas 2 obras continuam ativas no sistema)");
            } else {
                System.out.println("FALHOU (Esperava 2 obras, mas retornou " + ativas.size() + ")");
            }
        } catch (Exception e) {
            System.out.println("FALHOU: " + e.getMessage());
        }

        // ==========================================
        // TESTE 5: Impressão com Detalhes e Avaliações
        // ==========================================
        System.out.println("\n=== TESTE 5: Demonstração de Late Binding e Exibição de Avaliações ===");
        Vector<Obra> acervoCompleto = repoObra.listar();
        for (Obra o : acervoCompleto) {
            System.out.println("----------------------------------------");
            // Exibe os detalhes polimórficos da obra
            System.out.println(o.exibirDetalhes());
            System.out.println("Status: " + (o.isAtiva() ? "Ativa" : "Inativa"));
            System.out.printf("Média de Avaliações: %.1f\n", o.mediaAvaliacoes());

            // Método adicionado para listar os feedbacks dos usuários
            exibirAvaliacoesDaObra(o);
        }
        System.out.println("----------------------------------------");

        System.out.println("\n=== FIM DOS TESTES ===");
    }

    /**
     * Método auxiliar de teste para exibir textualmente as avaliações de uma obra.
     * Como o vetor de avaliações é privado na classe Obra, para rodar este teste perfeitamente,
     * certifique-se de ter criado um método "public Vector<Avaliacao> getAvaliacoes()" na sua classe Obra.
     */
    private static void exibirAvaliacoesDaObra(Obra obra) {
        System.out.println(">> Comentários dos Usuários:");

        Vector<Avaliacao> avs = obra.getAvaliacoes();

        if (avs == null || avs.isEmpty()) {
            System.out.println("   (Nenhuma avaliação recebida ainda)");
            return;
        }

        for (Avaliacao av : avs) {
            System.out.println(av.exibirAvaliacao());
        }
    }
}