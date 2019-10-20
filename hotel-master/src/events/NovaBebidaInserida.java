package events;

import model.Bebida;

public class NovaBebidaInserida implements Event {

    private Bebida bebida;

    public NovaBebidaInserida(Bebida bebida) {
        this.bebida = bebida;
    }

    public Bebida getBebida() {
        return bebida;
    }
}
