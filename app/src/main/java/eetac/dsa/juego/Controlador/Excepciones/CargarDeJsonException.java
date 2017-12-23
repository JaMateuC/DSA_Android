package eetac.dsa.juego.Controlador.Excepciones;

public class CargarDeJsonException extends Exception {
    String mensaje;

    public CargarDeJsonException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getLocalizedMessage() {
        return mensaje;
    }

    public void setLocalizedMessage(String mensaje){
        this.mensaje = mensaje;
    }
}
