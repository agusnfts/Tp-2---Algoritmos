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

    //POST: carga todas las imagenes utilizadas por la interfaz gráfica
    public Sprites() {
        suelo = cargarImagen("imagenes/suelo.png");
        pared = cargarImagen("imagenes/pared.png");
        cofre = cargarImagen("imagenes/cofre.png");
        escaleraSubir = cargarImagen("imagenes/escalera_subir.png");
        escaleraBajar = cargarImagen("imagenes/escalera_bajar.png");
        jugador = cargarImagen("imagenes/jugador.png");
        slotVacio = cargarImagen("imagenes/slot_vacio.png");
        antorcha = cargarImagen("imagenes/antorcha.png");
        mapaRasgado = cargarImagen("imagenes/mapa_rasgado.png");
        amuletoMistico = cargarImagen("imagenes/amuleto_mistico.png");
    }

    //PRE: ruta corresponde a una imagen valida
    //POST: devuelve la imagen cargada o null si ocurrio un error
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