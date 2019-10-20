package controller;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;


import observer.EventEmitter;

@SuppressWarnings("serial")
public class FrmPrincipal extends JFrame {
	
	public FrmPrincipal() {
		

		
		setTitle("Boteco Tech");
		setSize(796,276);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton button_ingredientes = new JButton("Cadastro de ingredientes");
		button_ingredientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventEmitter emitter = new EventEmitter();

				FrmIngredientes frmIngredientes = new FrmIngredientes(emitter);
				frmIngredientes.setVisible(true);
			}
		});
		button_ingredientes.setBounds(65, 106, 174, 63);
		getContentPane().add(button_ingredientes);
		
		JButton button_bebidas = new JButton("Cadastro de bebidas");
		button_bebidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventEmitter emitter = new EventEmitter();

				FrmBebidas frmBebidas = new FrmBebidas(emitter);
				frmBebidas.setVisible(true);
			}
		});
		button_bebidas.setBounds(304, 106, 174, 63);
		getContentPane().add(button_bebidas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 54, 764, 2);
		getContentPane().add(separator);
		
		JLabel label_nome = new JLabel("Boteco Tech");
		label_nome.setFont(new Font("Tahoma", Font.BOLD, 40));
		label_nome.setBounds(10, 11, 764, 32);
		getContentPane().add(label_nome);
		
		JLabel label_aviso = new JLabel("");
		label_aviso.setHorizontalAlignment(SwingConstants.CENTER);
		label_aviso.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_aviso.setBounds(10, 467, 574, 29);
		getContentPane().add(label_aviso);
		
		JButton button_pratos = new JButton("Cadastro de pratos");
		button_pratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_pratos.setBounds(543, 106, 174, 63);
		getContentPane().add(button_pratos);
		
		

		
	}


	
	
}
