package controller;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import model.Reserva;
import repository.HospedeRepository;
import repository.QuartoRepository;
import repository.ReservaRepository;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FrmListarReservas extends JFrame {
	private JTable table_reservas;
	private List<Reserva> reservas;
	private ReservaRepository reservaRepository;
	
	public FrmListarReservas() {
		
		iniciarHibernate();
		recuperarReservas();
		
		setTitle("Reservas");
		setSize(571,262);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		table_reservas = new JTable();
		table_reservas.setBounds(12, 12, 538, 209);
		getContentPane().add(table_reservas);
		
		popularTabelaReservas(reservas);
	}
	
	private void iniciarHibernate() {
		reservaRepository = new ReservaRepository();
	}
	
	private void recuperarReservas() {
		reservas = reservaRepository.listarTodos();
	}
	
	private void popularTabelaReservas(List<Reserva> reservas) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.table_reservas.setModel(modelo);
		
		modelo.addColumn("Numero Reserva");
		modelo.addColumn("Hospede");
		modelo.addColumn("Quarto");
		
		for (Reserva reserva : reservas) {
			modelo.addRow(new Object[]{reserva.getId(), reserva.getHospede().getNome(),
					reserva.getQuarto().getNumeroQuarto()});
		}
	}

}
