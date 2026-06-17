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

    }
}
