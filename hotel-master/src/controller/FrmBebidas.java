package controller;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

import java.awt.Font;
import javax.swing.table.DefaultTableModel;

import events.Event;
import events.NovaBebidaInserida;
import model.Bebida;
import observer.EventEmitter;
import observer.EventHandler;
import repository.BebidaRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmBebidas extends JFrame implements EventHandler<NovaBebidaInserida> {
	private JTable tabela_hospedes;
	private JTextField textField_id;
	private JTextField textField_nome;
	private JTextField textField_preco;
	private BebidaRepository bebidaRepository;
	private List<Bebida> bebidas;
	private int idSelecionadoTab;
	private EventEmitter emitter;
	
	public FrmBebidas(EventEmitter eventEmitter) {
		this.emitter = eventEmitter;
		
		iniciarHibernate();
		recuperarBebidas();
		
		setTitle("Bebidas");
		setSize(800,423);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton button_new = new JButton("Nova Bebida");
		button_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoBebida();
			}
		});
		button_new.setBounds(20, 321, 180, 32);
		getContentPane().add(button_new);
		
		JButton button_alter = new JButton("Alterar Bebida");
		button_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarBebida();
			}
		});
		button_alter.setBounds(404, 321, 180, 32);
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
		
		JLabel label_nome = new JLabel("Nome:");
		label_nome.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_nome.setBounds(219, 222, 87, 14);
		getContentPane().add(label_nome);
		
		textField_nome = new JTextField();
		textField_nome.setColumns(10);
		textField_nome.setBounds(268, 220, 506, 20);
		getContentPane().add(textField_nome);
		
		JLabel label_preco = new JLabel("Preço:");
		label_preco.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_preco.setBounds(10, 257, 66, 14);
		getContentPane().add(label_preco);
		
		textField_preco = new JTextField();
		textField_preco.setColumns(10);
		textField_preco.setBounds(78, 257, 133, 20);
		getContentPane().add(textField_preco);
		
		
		
		tabela_hospedes = new JTable();
		tabela_hospedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	int linhaTabela = tabela_hospedes.getSelectedRow();
		    	idSelecionadoTab = (int) tabela_hospedes.getValueAt(linhaTabela, 0);
		    	textField_id.setText(tabela_hospedes.getValueAt(linhaTabela, 0).toString());
				textField_nome.setText(tabela_hospedes.getValueAt(linhaTabela, 1).toString());
				textField_preco.setText(tabela_hospedes.getValueAt(linhaTabela, 2).toString());
			}
		});
		tabela_hospedes.setBounds(10, 11, 764, 142);
		getContentPane().add(tabela_hospedes);
		
		JLabel label_aviso = new JLabel("");
		label_aviso.setHorizontalAlignment(SwingConstants.CENTER);
		label_aviso.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_aviso.setBounds(10, 467, 574, 29);
		getContentPane().add(label_aviso);
		
		JButton button_excluir = new JButton("Excluir Bebida");
		button_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirBebida();
			}
		});
		button_excluir.setBounds(600, 321, 174, 32);
		getContentPane().add(button_excluir);
		
		popularTabela(bebidas);

		this.emitter.on("novo-bebida-inserido", this);
	}
	
	private void iniciarHibernate() {
		bebidaRepository = new BebidaRepository();
	}
	
	private void recuperarBebidas() {
		
		bebidas = bebidaRepository.listarTodos();

		for (Bebida bebida : bebidas) {
			System.out.println("Nome Bebida " + bebida.getNome());
			
		}

	}
	
	private void popularTabela(List<Bebida> bebidas) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.tabela_hospedes.setModel(modelo);
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Nome");
		modelo.addColumn("Preço");
		
		for (Bebida bebida : bebidas) {
			modelo.addRow(new Object[]{bebida.getId(), bebida.getNome(),
					bebida.getPreco()});
		}
	}
	
	private void novoBebida() {
		
		Bebida bebida = new Bebida();
		bebida.setNome(textField_nome.getText().toString());
		bebida.setPreco(textField_preco.getText().toString());
		
		
		bebidaRepository.salvar(bebida);

		this.emitter.dispatch("novo-bebida-inserido", new NovaBebidaInserida(bebida));
	}
	
	private void alterarBebida() {
		
		Bebida bebida = bebidaRepository.obterPorId(idSelecionadoTab);
		bebida.setNome(textField_nome.getText().toString());
		bebida.setPreco(textField_preco.getText().toString());
		
		bebidaRepository.salvar(bebida);
		
		recuperarBebidas();
		popularTabela(bebidas);
	}
	
	private void excluirBebida() {
		
		Bebida bebida = bebidaRepository.obterPorId(idSelecionadoTab);
		
		bebidaRepository.remover(bebida);
		
		recuperarBebidas();
		popularTabela(bebidas);
	}


	@Override
	public void handle(NovaBebidaInserida event) {
		recuperarBebidas();
		popularTabela(bebidas);

		JOptionPane.showMessageDialog(null, "Bebida " + event.getBebida().getNome() + " cadastrado com sucesso");
	}
}
