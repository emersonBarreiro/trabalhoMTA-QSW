import controller.FrmReservas;
import observer.EventEmitter;

public class Principal {

	public static void main(String[] args) {

		EventEmitter emitter = new EventEmitter();

		FrmReservas frmReservas = new FrmReservas(emitter);
		frmReservas.setVisible(true);
	}
}
