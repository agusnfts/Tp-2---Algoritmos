package Ciudad9.modelo;

public class Accion {

    private TipoAccion tipo;

    public Accion(TipoAccion tipo) {
        this.tipo = tipo;
    }

    public TipoAccion getTipo() {
        return tipo;
    }
}