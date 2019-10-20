package events;

import model.Ingrediente;

public class NovoIngredienteInserido implements Event {

    private Ingrediente ingrediente;

    public NovoIngredienteInserido(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }
}
