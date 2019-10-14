package controller;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import events.NovoHospedeInserido;
import model.Hospede;
import model.Quarto;
import model.Reserva;
import observer.EventEmitter;
import observer.EventHandler;
import repository.HospedeRepository;
import repository.QuartoRepository;
import repository.ReservaRepository;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.JSeparator;

public class FrmReservas extends JFrame implements EventHandler<NovoHospedeInserido> {
	private JTable table_hospedes;
	private JTable table_quartos;
	private JFormattedTextField textField_entrada;
	private JFormattedTextField textField_saida;
	private HospedeRepository hospedeRepository;
	private QuartoRepository quartoRepository;
	private ReservaRepository reservaRepository;
	private List<Hospede> hospedes;
	private List<Quarto> quartos;
	private List<Reserva> reservas;
	private EventEmitter emitter;
	private int idSelecionadoTabHospedes;
	private int idSelecionadoTabQuartos;
	private boolean quartoIndisponivel = false;
	
	public FrmReservas(EventEmitter eventEmitter) {
		this.emitter = eventEmitter;
		
		iniciarHibernate();
		recuperarHospedes();
		recuperarQuartos();
		recuperarReservas();
		
		setTitle("Reservas");
		setSize(800,619);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel labelHospede = new JLabel("Selecione um hospede abaixo: ");
		labelHospede.setBounds(12, 36, 235, 15);
		labelHospede.setFont(new Font("Tahoma", Font.BOLD, 12));
		getContentPane().add(labelHospede);
		
		JButton button_novoHospede = new JButton("Manter Hospedes");
		button_novoHospede.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmHospedes frmHospedes = new FrmHospedes(emitter);
				frmHospedes.setVisible(true);
			}
		});
		button_novoHospede.setBounds(595, 22, 179, 29);
		getContentPane().add(button_novoHospede);
		
		table_hospedes = new JTable();
		table_hospedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	int linhaTabela = table_hospedes.getSelectedRow();
		    	idSelecionadoTabHospedes = (int) table_hospedes.getValueAt(linhaTabela, 0);
			}
		});
		table_hospedes.setBounds(10, 56, 764, 173);
		getContentPane().add(table_hospedes);
		
		JLabel lblEntradaddmmaaaa = new JLabel("Entrada:");
		lblEntradaddmmaaaa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEntradaddmmaaaa.setBounds(10, 240, 210, 15);
		getContentPane().add(lblEntradaddmmaaaa);
		
		JLabel lblSaidaddmmaaaa = new JLabel("Saida:");
		lblSaidaddmmaaaa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSaidaddmmaaaa.setBounds(10, 278, 210, 15);
		getContentPane().add(lblSaidaddmmaaaa);
		
		textField_entrada = new JFormattedTextField(Mascara("##/##/####"));
		textField_entrada.setColumns(10);
		textField_entrada.setBounds(84, 238, 278, 20);
		getContentPane().add(textField_entrada);
		
		textField_saida = new JFormattedTextField(Mascara("##/##/####"));
		textField_saida.setColumns(10);
		textField_saida.setBounds(68, 276, 294, 20);
		getContentPane().add(textField_saida);
		
		JButton button = new JButton("Verificar Disponibilidade");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Quarto quarto = quartoRepository.obterPorId(idSelecionadoTabQuartos);
				
				if (quarto.getReservado().equals("true")) {
					JOptionPane.showMessageDialog(null, "Quarto " + quarto.getNumeroQuarto() + " indisponivel, \n"
							+ "selecione outro quarto.");
				} else {
					JOptionPane.showMessageDialog(null, "Quarto " + quarto.getNumeroQuarto() + " disponivel!");
				}
			}
		});
		button.setBounds(388, 252, 230, 29);
		getContentPane().add(button);
		
		table_quartos = new JTable();
		table_quartos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	int linhaTabela = table_quartos.getSelectedRow();
		    	idSelecionadoTabQuartos = (int) table_quartos.getValueAt(linhaTabela, 0);
		    	
		    	Quarto quarto = quartoRepository.obterPorId(idSelecionadoTabQuartos);
				
				if (quarto.getReservado().equals("true")) {
					quartoIndisponivel = true;
				} else {
					quartoIndisponivel = false;
				}
			}
		});
		table_quartos.setBounds(10, 363, 764, 153);
		getContentPane().add(table_quartos);
		
		JLabel lblSelecioneOQuarto = new JLabel("Selecione o quarto:");
		lblSelecioneOQuarto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSelecioneOQuarto.setBounds(10, 323, 291, 15);
		getContentPane().add(lblSelecioneOQuarto);
		
		JButton button_efetuarReserva = new JButton("Efetuar Reserva");
		button_efetuarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novaReserva();
			}
		});
		button_efetuarReserva.setBounds(10, 529, 179, 39);
		getContentPane().add(button_efetuarReserva);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 350, 764, 2);
		getContentPane().add(separator);
		
		JButton button_novoQuarto = new JButton("Manter Quartos");
		button_novoQuarto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrmQuartos frmQuartos = new FrmQuartos();
				frmQuartos.setVisible(true);
			}
		});
		button_novoQuarto.setBounds(595, 313, 179, 29);
		getContentPane().add(button_novoQuarto);
		
		JButton button_exibirReservas = new JButton("Exibir Reservas");
		button_exibirReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		    	FrmListarReservas frmListarReservas = new FrmListarReservas();
		    	frmListarReservas.setVisible(true);
			}
		});
		button_exibirReservas.setBounds(208, 528, 179, 40);
		getContentPane().add(button_exibirReservas);
		
		popularTabelaHospedes(hospedes);
		popularTabelaQuartos(quartos);

		this.emitter.on("novo-hospede-inserido", this);
	}
	
	private void iniciarHibernate() {
		hospedeRepository = new HospedeRepository();
		quartoRepository = new QuartoRepository();
		reservaRepository = new ReservaRepository();
	}
	
	private void recuperarHospedes() {
		hospedes = hospedeRepository.listarTodos();
	}
	
	private void recuperarQuartos() {
		quartos = quartoRepository.listarTodos();
	}
	
	private void recuperarReservas() {
		reservas = reservaRepository.listarTodos();
	}
	
	private void novaReserva() {
		
		if (quartoIndisponivel == true) {
			JOptionPane.showMessageDialog(null, "Quarto indisponivel, \n"
					+ "selecione outro quarto.");
		} else {
			Hospede hospede = hospedeRepository.obterPorId(idSelecionadoTabHospedes);
			Quarto quarto = quartoRepository.obterPorId(idSelecionadoTabQuartos);
			quarto.setReservado("true");

			Reserva reserva = new Reserva();

			reserva.setEntrada(textField_entrada.getText().toString());
			reserva.setSaida(textField_saida.getText().toString());
			reserva.setHospede(hospede);
			reserva.setQuarto(quarto);

			reservaRepository.salvar(reserva);
			quartoRepository.salvar(quarto);
		}
		
//		hospede.getReservas();
	}
	
	private void popularTabelaHospedes(List<Hospede> hospedes) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.table_hospedes.setModel(modelo);
		
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
	
	private void popularTabelaQuartos(List<Quarto> Quartos) {

		DefaultTableModel modelo = new DefaultTableModel();
		
		this.table_quartos.setModel(modelo);
		
		modelo.addColumn("Codigo");
		modelo.addColumn("Numero");
		modelo.addColumn("Tipo");
		modelo.addColumn("Valor");
		
		for (Quarto quarto : quartos) {
			modelo.addRow(new Object[]{quarto.getId(), quarto.getNumeroQuarto(),
					quarto.getTipoQuarto(), quarto.getValor()});
		}
	}

	@Override
	public void handle(NovoHospedeInserido event) {
		recuperarHospedes();
		recuperarQuartos();
		popularTabelaHospedes(hospedes);
		popularTabelaQuartos(quartos);
	}
	
	public MaskFormatter Mascara(String Mascara){
        MaskFormatter F_Mascara = new MaskFormatter();
        try{
            F_Mascara.setMask(Mascara);
            F_Mascara.setPlaceholderCharacter(' '); 
        }
        catch (Exception excecao) {
        excecao.printStackTrace();
        } 
        return F_Mascara;
 }
}
