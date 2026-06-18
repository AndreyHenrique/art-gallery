import gui.JanelaPrincipal;
import models.*;
import repositories.IRepositorioExposicao;
import repositories.IRepositorioObra;
import repositories.RepositorioExposicao;
import repositories.RepositorioObra;
import services.ArtGallery;
import services.IArtGallery;

public class Main {
    public static void main(String[] args) {
        IRepositorioObra repositorioObra = new RepositorioObra();
        IRepositorioExposicao repositorioExposicao = new RepositorioExposicao();

        IArtGallery app = new ArtGallery(repositorioObra, repositorioExposicao);

        setMock(app);
        java.awt.EventQueue.invokeLater(() -> {
            new JanelaPrincipal(app).setVisible(true);
        });
    }

    public static void setMock(IArtGallery sistema){
        try {
            // ====================================================================
            // CRIAÇÃO DAS OBRAS
            // ====================================================================

            // Subclasse: PinturaDigital
            Obra obraJhin = new PinturaDigital("O Virtuoso de Ionia", "Jhin", "8K", "Photoshop");
            Obra obraSona = new PinturaDigital("Sinfonia Crescente", "Sona", "4K", "Clip Studio Paint");

            // Subclasse: Modelagem3D
            Obra obraOrnn = new Modelagem3D("Forja de Sangue e Ferro", "Ornn", 120000, "Blender");
            Obra obraHeimer = new Modelagem3D("Torreta H-28G Avançada", "Heimerdinger", 85000, "Maya");

            // Subclasse: ArteGenerativa
            Obra obraViktor = new ArteGenerativa("A Gloriosa Evolução", "Viktor", "Hextech Chaos Engine", 17032011L);
            Obra obraLux = new ArteGenerativa("Prisma de Demacia", "Lux", "Singularidade Luminosa", 20101019L);

            // Cadastrando todas as obras no sistema
            sistema.publicarObra(obraJhin);
            sistema.publicarObra(obraSona);
            sistema.publicarObra(obraOrnn);
            sistema.publicarObra(obraHeimer);
            sistema.publicarObra(obraViktor);
            sistema.publicarObra(obraLux);


            // ====================================================================
            // INJEÇÃO DE AVALIAÇÕES
            // ====================================================================

            // Avaliações para a obra do Jhin
            sistema.avaliarObra("O Virtuoso de Ionia", new Avaliacao("Zed", 4, "Falta equilíbrio, mas a intenção foi letal."));
            sistema.avaliarObra("O Virtuoso de Ionia", new Avaliacao("Shen", 4, "A arte deve manter o equilíbrio, não o caos."));

            // Avaliações para a obra do Viktor
            sistema.avaliarObra("A Gloriosa Evolução", new Avaliacao("Jayce", 9, "A tecnologia é brilhante, mas falta humanidade no código."));
            sistema.avaliarObra("A Gloriosa Evolução", new Avaliacao("Blitzcrank", 10, "BEEP BOOP. PERFEIÇÃO DETECTADA."));

            // Avaliações para a obra do Ornn
            sistema.avaliarObra("Forja de Sangue e Ferro", new Avaliacao("Anivia", 10, "Magnífico, irmão. Sinto o calor da sua dedicação."));
            sistema.avaliarObra("Forja de Sangue e Ferro", new Avaliacao("Volibear", 2, "Fraco! Um pedaço de ferro não substitui a força da tempestade."));


            // ====================================================================
            // CRIAÇÃO DAS EXPOSIÇÕES TEMÁTICAS
            // ====================================================================

            // Exposição 1: Mestres da Criação
            Exposicao expoCriadores = new Exposicao("Mestres da Criacao");
            sistema.criarExposicao(expoCriadores);
            sistema.adicionarObraEmExposicao("Mestres da Criacao", obraOrnn.getTitulo());
            sistema.adicionarObraEmExposicao("Mestres da Criacao", obraHeimer.getTitulo());
            sistema.adicionarObraEmExposicao("Mestres da Criacao", obraViktor.getTitulo());

            // Exposição 2: Harmonia e Destruição
            Exposicao expoHarmonia = new Exposicao("Harmonia e Destruicao");
            sistema.criarExposicao(expoHarmonia);
            sistema.adicionarObraEmExposicao("Harmonia e Destruicao", obraJhin.getTitulo());
            sistema.adicionarObraEmExposicao("Harmonia e Destruicao", obraSona.getTitulo());
            sistema.adicionarObraEmExposicao("Harmonia e Destruicao", obraLux.getTitulo());

            System.out.println(">>> [INFO] Dados do League of Legends mockados com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados mockados: " + e.getMessage());
        }
    }
}
