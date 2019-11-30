import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class TietokantaGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmLopeta;
	private JPanel panel;
	private JButton btnDeleteEntry;
	private JButton btnAddEntry;
	
	toiminnallisuus Toiminnallisuus = new toiminnallisuus();
	
	//Tauluun liittyviä määreitä
	static String[] columnNames = { "Name", "Age", "PhoneNumber", "Address" };

	static Object[][] data = { { "Name", "Age", "Phone Number", "Address" } };

	static DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
	
	//Poistamiseen liittyviä muuttujia
	private static String convertedCD1;
	private static String convertedCD2;
	private static String convertedCD3;
	private static String convertedCD4;
	
	//Otsikkotaulu
	private JTable table_1;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Testataan yhteys tietokantaan ohjelman käynnistyessä
		toiminnallisuus.connectionTest();

		// Ikkunan luonti ja elementit näkyviin
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TietokantaGUI frame = new TietokantaGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		refreshTable();
	}

	/**
	 * Create the frame.
	 */
	public TietokantaGUI() {
		setTitle("Tietokanta");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setResizable(false);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Ty\u00F6kalut");
		menuBar.add(mnNewMenu);

		mntmLopeta = new JMenuItem("Lopeta");
		mntmLopeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmLopeta);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Taulukko

		table = new JTable(tableModel);
		table.setRowSelectionAllowed(true);
		table.setBounds(140, 27, 584, 402);
		contentPane.add(table);

		panel = new JPanel();
		panel.setBounds(5, 5, 125, 69);
		contentPane.add(panel);
		panel.setLayout(null);

		// NAPPI RIVIEN POISTAMISEEN
		btnDeleteEntry = new JButton("Delete Entry");
		btnDeleteEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					int selectedRow = table.getSelectedRow();
					
					
					//Haetaan data rivin kaikista soluista, jotta niitä voidaan verrata tietokannassa tiettyyn riviin
					
					Object columnData1 = tableModel.getValueAt(selectedRow, 0);
					convertedCD1 = String.valueOf(columnData1);
					
					Object columnData2 = tableModel.getValueAt(selectedRow, 1);
					convertedCD2 = String.valueOf(columnData2);
					
					Object columnData3 = tableModel.getValueAt(selectedRow, 2);
					convertedCD3 = String.valueOf(columnData3);
					
					Object columnData4 = tableModel.getValueAt(selectedRow, 3);
					convertedCD4 = String.valueOf(columnData4);
					
					//Kutsutaan rivin poistavaa metodia
					toiminnallisuus.deleteFromDB();
					//Päivitetään lopuksi taulu muutosten jälkeen näkyviin
					refreshTable();
					
					//Viesti konsoliin poistetusta rivistä
					int deletedRow = selectedRow + 1;
					System.out.println("Row " + deletedRow + " deleted succesfully.");
					
					

					
				} catch (Exception deleteError) {
					System.out.println("Error.");
					System.out.println(deleteError);
				}
			}
		});
		btnDeleteEntry.setBounds(5, 35, 113, 23);
		panel.add(btnDeleteEntry);

		btnAddEntry = new JButton("Add Entry");
		btnAddEntry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// LUODAAN UUSI IKKUNA
				JFrame addEntry = new JFrame();
				// MÄÄTIETÄÄN IKKUNAN ASETTELU
				GridLayout layout = new GridLayout(5, 2);
				addEntry.getContentPane().setLayout(layout);
				addEntry.setTitle("Add Entry to Database");
				addEntry.setResizable(false);
				addEntry.setBounds(0, 0, 350, 150);
				addEntry.setLocationRelativeTo(rootPane);

				// IKKUNAN SISÄLTÖ
				JLabel nameLabel = new JLabel("Name");
				JLabel ageLabel = new JLabel("Age");
				JLabel phonroLabel = new JLabel("Phone Number");
				JLabel addressLabel = new JLabel("Address");

				JTextField nameInput = new JTextField();
				JTextField ageInput = new JTextField();
				JTextField phonroInput = new JTextField();
				JTextField addressInput = new JTextField();

				//Määritetään toiminta painettaessa nappia, joka tallentaa tiedot tauluun ja tietokantaan
				JButton addBtn = new JButton("Add");
				addBtn.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						try {

							// Haetaan käyttäjän syötteet
							String newName = nameInput.getText();
							String newAge = ageInput.getText();
							String newPhoneNro = phonroInput.getText();
							String newAddress = addressInput.getText();
							
							//Sijoitetaan käyttäjän syöte metodissa käytettäviin muuttujiin
							
							Toiminnallisuus.setFullame(newName);
							Toiminnallisuus.setAge(newAge);
							Toiminnallisuus.setPhoneNro(newPhoneNro);
							Toiminnallisuus.setAddress(newAddress);
							
							//Lisätään uusi rivi tietokantaan
							toiminnallisuus.saveToDB();
							
							//Päivitetään taulu
							refreshTable();
							
							addEntry.dispose();

						} catch (Exception exception) {

							JOptionPane.showMessageDialog(null, "Error in creating new entry!");
							addEntry.dispose();

						}

					}
				});

				JButton cancelBtn = new JButton("Cancel");
				cancelBtn.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						addEntry.dispose();

					}
				});

				// SISÄLTÖ FRAMEEN

				// Nimi-kentät
				addEntry.getContentPane().add(nameLabel);
				addEntry.getContentPane().add(nameInput);
				// Ikä-Kentät
				addEntry.getContentPane().add(ageLabel);
				addEntry.getContentPane().add(ageInput);
				// Puh. Nro -kentät
				addEntry.getContentPane().add(phonroLabel);
				addEntry.getContentPane().add(phonroInput);
				// Osoite- Kentät
				addEntry.getContentPane().add(addressLabel);
				addEntry.getContentPane().add(addressInput);
				// Napit
				addEntry.getContentPane().add(addBtn);
				addEntry.getContentPane().add(cancelBtn);

				// Ikkuna näkyviin
				addEntry.setVisible(true);

			}
		});
		btnAddEntry.setBounds(5, 5, 113, 23);
		panel.add(btnAddEntry);
		
	//TAULUKON OTSIKKORIVI
		
		String[] headerColumnNames = { "Name", "Age", "PhoneNumber", "Address" };

		Object[][] headerData = { { "Name", "Age", "Phone Number", "Address" } };

		DefaultTableModel headerTableModel = new DefaultTableModel(headerData, headerColumnNames);
		
		table_1 = new JTable(headerTableModel);
		table_1.setBounds(140, 0, 584, 16);
		contentPane.add(table_1);
	}

	
	//METODI TAULUKON PÄIVITTÄMISEEN KÄYTTÖLIITTYMÄSSÄ
	
	public static void refreshTable() {
		
		try {
			//Yhteys tietokantaan
			Connection connection = DriverManager.getConnection(toiminnallisuus.getURL(), toiminnallisuus.getUsername(), toiminnallisuus.getPsswd());
			//Statement-olio 
			Statement statement = connection.createStatement();
			//SQL-kysely tietokannan sisällön hakemiseksi
			ResultSet rs = statement.executeQuery("SELECT * FROM members");
			
			//Luodaan ArrayList tietokannasta haetun datan tallentamiseen
			ArrayList<Object[]> data = new ArrayList<Object[]>();
			
			//Käydään tulokset läpi silmukan avulla
			while (rs.next()) {
				data.add(new Object[] {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
			}
			
			//Tyhjennetään taulkko ennen sen päivittämistä
			tableModel.setRowCount(0);
			//Lisätään haettu data näkyville taulukkoon
			for(int i = 0 ; i < data.size(); i++) {
				tableModel.addRow(data.get(i));
			}
			
			//Suljetaan yhteys
			connection.close();
			
		} catch(SQLException e) {
			System.out.println("Error occured while refreshing table.");
			System.out.println(e);
		}
	}
	
	
	//GETTERIT JA SETTERIT
	public JButton getBtnDeleteEntry() {
		return btnDeleteEntry;
	}

	public void setBtnDeleteEntry(JButton btnDeleteEntry) {
		this.btnDeleteEntry = btnDeleteEntry;
	}

	public JButton getBtnAddEntry() {
		return btnAddEntry;
	}

	public void setBtnAddEntry(JButton btnAddEntry) {
		this.btnAddEntry = btnAddEntry;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public static String getConvertedCD1() {
		return convertedCD1;
	}

	public static void setConvertedCD1(String convertedCD1) {
		TietokantaGUI.convertedCD1 = convertedCD1;
	}

	public static String getConvertedCD2() {
		return convertedCD2;
	}

	public static void setConvertedCD2(String convertedCD2) {
		TietokantaGUI.convertedCD2 = convertedCD2;
	}

	public static String getConvertedCD3() {
		return convertedCD3;
	}

	public static void setConvertedCD3(String convertedCD3) {
		TietokantaGUI.convertedCD3 = convertedCD3;
	}

	public static String getConvertedCD4() {
		return convertedCD4;
	}

	public static void setConvertedCD4(String convertedCD4) {
		TietokantaGUI.convertedCD4 = convertedCD4;
	}
}