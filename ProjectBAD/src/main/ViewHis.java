package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewHis extends JFrame implements MouseListener, ActionListener{
	
	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel, north, tPanel;
	private JTable table, table2;
	private JScrollPane scrollPane, scrollPane2;
	private DefaultTableModel model, model2;
	private JButton back;
	private JLabel title2, title, ts;
	private GridBagConstraints gbc;
	private int idUser;
	
	Vector<Vector<Object>> tableContents, tableContents2;
	Vector<Object> tableRows, tableHeader, tableRows2, tableHeader2;
	
	Connect con = Connect.getConnection();

	private void initialize() {
		setTitle("Welcome Admin");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private Vector<Object> createData(int id, String date) {
		tableRows = new Vector<>();
		tableRows.add(id);
		tableRows.add(date);
		
		return tableRows;
	}
	
	private void initTable() {
		nPanel = new JPanel(new GridBagLayout());
		nPanel.setBackground(Color.CYAN);
		
		gbc = new GridBagConstraints();		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		tableContents = new Vector<Vector<Object>>();
		
		tableHeader = new Vector<Object>();
		
		tableHeader.add("Transaction ID");
		tableHeader.add("Date");
		
		model = new DefaultTableModel(tableContents, tableHeader);
		fetchData();
		table = new JTable(model){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
				}
			};
		table.setBackground(Color.CYAN);
		table.addMouseListener(this);
			
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(700, 200));
		
		scrollPane.addMouseListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		nPanel.add(scrollPane, gbc);
		
		//Tabel detail
		cPanel = new JPanel(new GridBagLayout());
		cPanel.setBackground(Color.cyan);
		
		tableContents2 = new Vector<Vector<Object>>();
		
		tableHeader2 = new Vector<Object>();
		
		tableHeader2.add("Transaction Id");
		tableHeader2.add("Pokemon Id");
		tableHeader2.add("Pokemon Name");
		tableHeader2.add("Pokemon Level");
		tableHeader2.add("Pokemon Type");
		tableHeader2.add("Quantity");
		
		model2 = new DefaultTableModel(tableContents2, tableHeader2);
		
		table2 = new JTable(model2){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
				}
			};
			
		table.setBackground(Color.CYAN);
		scrollPane2 = new JScrollPane(table2);
		scrollPane2.setPreferredSize(new Dimension(700, 100));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(scrollPane2, gbc);

		scrollPane2.setVisible(false);
	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			
			String query = String.format("SELECT * FROM headertransaction WHERE userID = %d", idUser);
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int id = rs.getInt("TransactionId");
					String date = rs.getString("Time");
					
					Vector<Object> newData = createData(id, date);
					tableContents.add(newData);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	private void initComp() {
						
		bPanel = new JPanel(new GridBagLayout());
		bPanel.setBackground(Color.CYAN);
		
		lPanel = new JPanel(new GridBagLayout());
		lPanel.setBackground(Color.CYAN);
		
		sPanel = new JPanel(new GridBagLayout());
		sPanel.setBackground(Color.CYAN);
		
		title = new JLabel("Transaction History");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 50));
		
		title2 = new JLabel("Select the row for details");
		title2.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		
		ts = new JLabel();
		
		back = new JButton("Back to Main Menu");
		back.setPreferredSize(new Dimension(555, 30));
		back.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		nPanel.add(title, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		nPanel.add(title2, gbc);
		
		gbc.gridy = -1;
		sPanel.add(back, gbc);
		gbc.gridy = 1;
		sPanel.add(ts, gbc);

	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);

	}
	
	public ViewHis(int idUser) {
		this.idUser = idUser;
		initTable();
		initComp();
		addComp();
		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == back) {
			MainFormUser mfu = new MainFormUser(idUser);
			setVisible(false);
			mfu.setVisible(true);
		}
		
	}
	
	private Vector<Object> createData2(int transId, int pokeId, String pokeName, int pokeLvl, String pokeType, int qty) {
		tableRows = new Vector<>();
		tableRows.add(transId);
		tableRows.add(pokeId);
		tableRows.add(pokeName);
		tableRows.add(pokeLvl);
		tableRows.add(pokeType);
		tableRows.add(qty);
		
		return tableRows;
	}
	


	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == table) {
			scrollPane2.setVisible(true);
			ts.setText("hai");
			ts.setForeground(Color.CYAN);
			
			model2.setRowCount(0);
			
			int selectedRows = table.getSelectedRow();
			String transactionId = model.getValueAt(selectedRows, 0).toString();
					
			String query = String.format("SELECT * FROM detailtransaction dt JOIN pokemon p ON dt.PokemonId = p.PokemonId WHERE dt.TransactionId = %d", Integer.parseInt(transactionId) );
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int transId = rs.getInt("TransactionId");
					int pokemonid = rs.getInt("PokemonId");
					String pokeName = rs.getString("PokemonName");
					int pokeLvl = rs.getInt("PokemonLevel");
					String pokeType = rs.getString("PokemonType");
					int qty = rs.getInt("Quantity");
						
					Vector<Object> newData2 = createData2(transId, pokemonid, pokeName, pokeLvl, pokeType, qty);
					tableContents2.add(newData2);
				}
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
