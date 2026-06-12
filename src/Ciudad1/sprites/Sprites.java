package Ciudad1.sprites;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprites {

    public final BufferedImage suelo;
    public final BufferedImage pared;
    public final BufferedImage cofre;
    public final BufferedImage escaleraSubir;
    public final BufferedImage escaleraBajar;
    public final BufferedImage jugador;
    public final BufferedImage slotVacio;
    public final BufferedImage antorcha;
    public final BufferedImage mapaRasgado;
    public final BufferedImage amuletoMistico;

    public Sprites() {
        suelo = cargarImagen("src/Ciudad1/sprites/suelo.png");
        pared = cargarImagen("src/Ciudad1/sprites/pared.png");
        cofre = cargarImagen("src/Ciudad1/sprites/cofre.png");
        escaleraSubir = cargarImagen("src/Ciudad1/sprites/escalera_subir.png");
        escaleraBajar = cargarImagen("src/Ciudad1/sprites/escalera_bajar.png");
        jugador = cargarImagen("src/Ciudad1/sprites/jugador.png");
        slotVacio = cargarImagen("src/Ciudad1/sprites/slot_vacio.png");
        antorcha = cargarImagen("src/Ciudad1/sprites/antorcha.png");
        mapaRasgado = cargarImagen("src/Ciudad1/sprites/mapa_rasgado.png");
        amuletoMistico = cargarImagen("src/Ciudad1/sprites/amuleto_mistico.png");
    }

    private BufferedImage cargarImagen(String ruta) {
        try {
            File archivo = new File(ruta);
            if (archivo.exists()) {
                return ImageIO.read(archivo);
            } else {
                System.err.println("No se encontró la imagen: " + ruta);
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen: " + ruta);
        }
        return null;
    }
}