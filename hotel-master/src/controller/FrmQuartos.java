package controller;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.Quarto;
import repository.QuartoRepository;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrmQuartos extends JFrame{
	private JTable tabela_quartos;
	private JTextField textField_id;
	private JTextField textField_numeroQuarto;
	private JTextField textField_tipoQuarto;
	private JTextField textField_valor;
	private QuartoRepository quartoRepository;
	private List<Quarto> quartos;
	private int idSelecionadoTab;
	
	public FrmQuartos() {
		
		iniciarHibernate();
		recuperarQuartos();
		
		setTitle("Quartos");
		setSize(633,433);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton button_new = new JButton("Novo Quarto");
		button_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novoQuarto();
			}
		});
		button_new.setBounds(12, 355, 180, 32);
		getContentPane().add(button_new);
		
		JButton button_alter = new JButton("Alterar Quarto");
		button_alter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarQuarto();
			}
		});
		button_alter.setBounds(221, 355, 180, 32);
		getContentPane().add(button_alter);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 207, 596, 2);
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
		
		JLabel lblNumeroQuarto = new JLabel("Numero quarto:");
		lblNumeroQuarto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNumeroQuarto.setBounds(221, 222, 140, 14);
		getContentPane().add(lblNumeroQuarto);
		
		textField_numeroQuarto = new JTextField();
		textField_numeroQuarto.setColumns(10);
		textField_numeroQuarto.setBounds(358, 220, 121, 20);
		getContentPane().add(textField_numeroQuarto);
		
		JLabel lblTipoQuarto = new JLabel("Tipo quarto:");
		lblTipoQuarto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTipoQuarto.setBounds(10, 270, 115, 14);
		getContentPane().add(lblTipoQuarto);
		
		textField_tipoQuarto = new JTextField();
		textField_tipoQuarto.setColumns(10);
		textField_tipoQuarto.setBounds(109, 268, 497, 20);
		getContentPane().add(textField_tipoQuarto);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblValor.setBounds(10, 309, 66, 14);
		getContentPane().add(lblValor);
		
		textField_valor = new JTextField();
		textField_valor.setColumns(10);
		textField_valor.setBounds(78, 307, 197, 20);
		getContentPane().add(textField_valor);
		
		tabela_quartos = new JTable();
		tabela_quartos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	int linhaTabela = tabela_quartos.getSelectedRow();
		    	idSelecionadoTab = (int) tabela_quartos.getValueAt(linhaTabela, 0);
		    	textField_id.setText(tabela_quartos.getValueAt(linhaTabela, 0).toString());
				textField_numeroQuarto.setText(tabela_quartos.getValueAt(linhaTabela, 1).toString());
				textField_tipoQuarto.setText(tabela_quartos.getValueAt(linhaTabela, 2).toString());
				textField_valor.setText(tabela_quartos.getValueAt(linhaTabela, 3).toString());
			}
		});
		tabela_quartos.setBounds(10, 11, 596, 142);
		getContentPane().add(tabela_quartos);
		
		JLabel label_aviso = new JLabel("");
		label_aviso.setHorizontalAlignment(SwingConstants.CENTER);
		label_aviso.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_aviso.setBounds(10, 467, 574, 29);
		getContentPane().add(label_aviso);
		
		JButton button_excluir = new JButton("Excluir Quarto");
		button_excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirQuarto();
			}
		});
		button_excluir.setBounds(432, 355, 174, 32);
		getContentPane().add(button_excluir);
		
		popularTabela(quartos);
	}
	
	private void iniciarHibernate() {
		quartoRepository = new QuartoRepository();
	}
	
	private void recuperarQuartos() {
		
		quartos = quartoRepository.listarTodos();
	}
	
	private void popularTabela(List<Quarto> quartos) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.tabela_quartos.setModel(modelo);
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Numero");
		modelo.addColumn("Tipo");
		modelo.addColumn("Valor");
		
		for (Quarto quarto : quartos) {
			modelo.addRow(new Object[]{quarto.getId(), quarto.getNumeroQuarto(),
					quarto.getTipoQuarto(), quarto.getValor()});
		}
	}
	
	private void novoQuarto() {
		
		Quarto quarto = new Quarto();
		quarto.setNumeroQuarto(Integer.valueOf(textField_numeroQuarto.getText().toString()));
		quarto.setTipoQuarto(textField_tipoQuarto.getText().toString());
		quarto.setValor(Integer.valueOf(textField_valor.getText().toString()));
		quarto.setReservado("false");
		
		quartoRepository.salvar(quarto);
		
		recuperarQuartos();
		popularTabela(quartos);
	}
	
	private void alterarQuarto() {
		
		Quarto quarto = quartoRepository.obterPorId(idSelecionadoTab);
		quarto.setNumeroQuarto(Integer.valueOf(textField_numeroQuarto.getText().toString()));
		quarto.setTipoQuarto(textField_tipoQuarto.getText().toString());
		quarto.setValor(Integer.valueOf(textField_valor.getText().toString()));
		
		quartoRepository.salvar(quarto);
		
		recuperarQuartos();
		popularTabela(quartos);
	}
	
	private void excluirQuarto() {
		
		Quarto quarto = quartoRepository.obterPorId(idSelecionadoTab);
		
		quartoRepository.remover(quarto);
		
		recuperarQuartos();
		popularTabela(quartos);
	}
}