package controller;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;

import events.Event;
import events.NovoHospedeInserido;
import model.Hospede;
import model.Reserva;
import observer.EventEmitter;
import observer.EventHandler;
import repository.HospedeRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmHospedes extends JFrame implements EventHandler<NovoHospedeInserido> {
	private JTable tabela_hospedes;
	private JComboBox comboBox_pais;
	private JTextField textField_id;
	private JTextField textField_doc;
	private JTextField textField_nome;
	private JTextField textField_endereco;
	private JTextField textField_cidade;
	private JTextField textField_estado;
	private JTextField textField_telefone;
	private JTextField textField_email;
	private HospedeRepository hospedeRepository;
	private List<Hospede> hospedes;
	private int idSelecionadoTab;
	private EventEmitter emitter;
	
	public FrmHospedes(EventEmitter eventEmitter) {
		this.emitter = eventEmitter;
		
		iniciarHibernate();
		recuperarHospedes();
		
		setTitle("Hospedes");
		setSize(800,550);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton button_new = new JButton("Novo Hospede");
		button_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoHospede();
			}
		});
		button_new.setBounds(20, 467, 180, 32);
		getContentPane().add(button_new);
		
		JButton button_alter = new JButton("Alterar Hospede");
		button_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarHospede();
			}
		});
		button_alter.setBounds(399, 467, 180, 32);
		getContentPane().add(button_alter);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 207, 764, 2);
		getContentPane().add(separator);
		
		JLabel lblId = new JLabel("Codigo:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setBounds(10, 222, 87, 14);
		getContentPane().add(lblId);
		
		textField_id = new JTextField();
		textField_id.setBounds(78, 220, 133, 20);
		textField_id.setEnabled(false);
		getContentPane().add(textField_id);
		textField_id.setColumns(10);
		
		JLabel label_doc = new JLabel("Doc. de Identificao:");
		label_doc.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_doc.setBounds(221, 222, 140, 14);
		getContentPane().add(label_doc);
		
		textField_doc = new JTextField();
		textField_doc.setColumns(10);
		textField_doc.setBounds(358, 220, 416, 20);
		getContentPane().add(textField_doc);
		
		JLabel label_nome = new JLabel("Nome:");
		label_nome.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_nome.setBounds(10, 270, 87, 14);
		getContentPane().add(label_nome);
		
		textField_nome = new JTextField();
		textField_nome.setColumns(10);
		textField_nome.setBounds(78, 268, 696, 20);
		getContentPane().add(textField_nome);
		
		JLabel label_endereco = new JLabel("Endereco:");
		label_endereco.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_endereco.setBounds(10, 309, 66, 14);
		getContentPane().add(label_endereco);
		
		textField_endereco = new JTextField();
		textField_endereco.setColumns(10);
		textField_endereco.setBounds(78, 307, 696, 20);
		getContentPane().add(textField_endereco);
		
		JLabel label_cidade = new JLabel("Cidade:");
		label_cidade.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_cidade.setBounds(10, 347, 66, 14);
		getContentPane().add(label_cidade);
		
		textField_cidade = new JTextField();
		textField_cidade.setColumns(10);
		textField_cidade.setBounds(78, 345, 569, 20);
		getContentPane().add(textField_cidade);
		
		JLabel label_pais = new JLabel("Pais:");
		label_pais.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_pais.setBounds(10, 387, 66, 14);
		getContentPane().add(label_pais);
		
		comboBox_pais = new JComboBox();
		comboBox_pais.setBounds(78, 385, 256, 20);
		comboBox_pais.addItem("Brasil");
		getContentPane().add(comboBox_pais);
		
		JLabel label_estado = new JLabel("Estado:");
		label_estado.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_estado.setBounds(657, 348, 66, 14);
		getContentPane().add(label_estado);
		
		textField_estado = new JTextField();
		textField_estado.setColumns(10);
		textField_estado.setBounds(708, 345, 66, 20);
		getContentPane().add(textField_estado);
		
		JLabel label_telefone = new JLabel("Telefone:");
		label_telefone.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_telefone.setBounds(348, 388, 66, 14);
		getContentPane().add(label_telefone);
		
		textField_telefone = new JTextField();
		textField_telefone.setColumns(10);
		textField_telefone.setBounds(410, 385, 364, 20);
		getContentPane().add(textField_telefone);
		
		JLabel label_email = new JLabel("E-mail:");
		label_email.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_email.setBounds(10, 427, 66, 14);
		getContentPane().add(label_email);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(78, 425, 696, 20);
		getContentPane().add(textField_email);
		
		tabela_hospedes = new JTable();
		tabela_hospedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	int linhaTabela = tabela_hospedes.getSelectedRow();
		    	idSelecionadoTab = (int) tabela_hospedes.getValueAt(linhaTabela, 0);
		    	textField_id.setText(tabela_hospedes.getValueAt(linhaTabela, 0).toString());
				textField_doc.setText(tabela_hospedes.getValueAt(linhaTabela, 1).toString());
				textField_nome.setText(tabela_hospedes.getValueAt(linhaTabela, 2).toString());
				textField_endereco.setText(tabela_hospedes.getValueAt(linhaTabela, 3).toString());
				textField_cidade.setText(tabela_hospedes.getValueAt(linhaTabela, 4).toString());
				textField_estado.setText(tabela_hospedes.getValueAt(linhaTabela, 5).toString());
				textField_telefone.setText(tabela_hospedes.getValueAt(linhaTabela, 7).toString());
				textField_email.setText(tabela_hospedes.getValueAt(linhaTabela, 8).toString());
			}
		});
		tabela_hospedes.setBounds(10, 11, 764, 142);
		getContentPane().add(tabela_hospedes);
		
		JLabel label_aviso = new JLabel("");
		label_aviso.setHorizontalAlignment(SwingConstants.CENTER);
		label_aviso.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_aviso.setBounds(10, 467, 574, 29);
		getContentPane().add(label_aviso);
		
		JButton button_excluir = new JButton("Excluir Hospede");
		button_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirHospede();
			}
		});
		button_excluir.setBounds(600, 467, 174, 32);
		getContentPane().add(button_excluir);
		
		popularTabela(hospedes);

		this.emitter.on("novo-hospede-inserido", this);
	}
	
	private void iniciarHibernate() {
		hospedeRepository = new HospedeRepository();
	}
	
	private void recuperarHospedes() {
		
		hospedes = hospedeRepository.listarTodos();

		for (Hospede hospede : hospedes) {
			System.out.println("Nome Hospede " + hospede.getNome());
			for (Reserva reserva: hospede.getReservas().values()) {
				System.out.println("Data da reserva " + reserva.getEntrada());
			}
		}

	}
	
	private void popularTabela(List<Hospede> hospedes) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.tabela_hospedes.setModel(modelo);
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Documento");
		modelo.addColumn("Nome");
		modelo.addColumn("Endereco");
		modelo.addColumn("Cidade");
		modelo.addColumn("Estado");
		modelo.addColumn("Pais");
		modelo.addColumn("Telefone");
		modelo.addColumn("Email");
		
		for (Hospede hospede : hospedes) {
			modelo.addRow(new Object[]{hospede.getId(), hospede.getDoc(),
					hospede.getNome(), hospede.getEndereco(), hospede.getCidade(),
					hospede.getEstado(), hospede.getPais(), hospede.getTelefone(),
					hospede.getEmail()});
		}
	}
	
	private void novoHospede() {
		
		Hospede hospede = new Hospede();
		hospede.setDoc(textField_doc.getText().toString());
		hospede.setNome(textField_nome.getText().toString());
		hospede.setEndereco(textField_endereco.getText().toString());
		hospede.setCidade(textField_cidade.getText().toString());
		hospede.setEstado(textField_estado.getText().toString());
		hospede.setPais(comboBox_pais.getSelectedItem().toString());
		hospede.setTelefone(textField_telefone.getText().toString());
		hospede.setEmail(textField_email.getText().toString());
		
		hospedeRepository.salvar(hospede);

		this.emitter.dispatch("novo-hospede-inserido", new NovoHospedeInserido(hospede));
	}
	
	private void alterarHospede() {
		
		Hospede hospede = hospedeRepository.obterPorId(idSelecionadoTab);
		hospede.setDoc(textField_doc.getText().toString());
		hospede.setNome(textField_nome.getText().toString());
		hospede.setEndereco(textField_endereco.getText().toString());
		hospede.setCidade(textField_cidade.getText().toString());
		hospede.setEstado(textField_estado.getText().toString());
		hospede.setPais(comboBox_pais.getSelectedItem().toString());
		hospede.setTelefone(textField_telefone.getText().toString());
		hospede.setEmail(textField_email.getText().toString());
		
		hospedeRepository.salvar(hospede);
		
		recuperarHospedes();
		popularTabela(hospedes);
	}
	
	private void excluirHospede() {
		
		Hospede hospede = hospedeRepository.obterPorId(idSelecionadoTab);
		
		hospedeRepository.remover(hospede);
		
		recuperarHospedes();
		popularTabela(hospedes);
	}


	@Override
	public void handle(NovoHospedeInserido event) {
		recuperarHospedes();
		popularTabela(hospedes);

		JOptionPane.showMessageDialog(null, "Hospede " + event.getHospede().getNome() + " cadastrado com sucesso");
	}
}
