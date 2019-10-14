package events;

import model.Hospede;

public class NovoHospedeInserido implements Event {

    private Hospede hospede;

    public NovoHospedeInserido(Hospede hospede) {
        this.hospede = hospede;
    }

    public Hospede getHospede() {
        return hospede;
    }
}
