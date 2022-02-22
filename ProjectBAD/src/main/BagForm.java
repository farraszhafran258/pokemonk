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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BagForm extends JFrame implements ActionListener, MouseListener{

	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel, center, tPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JTextField pokeId;
	private JButton checkOut, back, delete;
	private JLabel poke, title, error;
	private GridBagConstraints gbc;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	private int idUser, transId;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Manage Bag");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private Vector<Object> createData(int id, String name, int lvl ,String type, int qty) {
		tableRows = new Vector<>();
		tableRows.add(id);
		tableRows.add(name);
		tableRows.add(lvl);
		tableRows.add(type);
		tableRows.add(qty);
		
		return tableRows;
	}
	
	private void initTable() {
		nPanel = new JPanel(new GridBagLayout());
		nPanel.setBackground(Color.CYAN);
		
		tableContents = new Vector<Vector<Object>>();
		
		tableHeader = new Vector<Object>();
		
		tableHeader.add("PokemonId");
		tableHeader.add("PokemonName");
		tableHeader.add("PokemonLevel");
		tableHeader.add("PokemonType");
		tableHeader.add("Quantity");
		
		dtm = new DefaultTableModel(tableContents, tableHeader);
		fetchData();
		table = new JTable(dtm){
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
				}
			};
		table.setBackground(Color.CYAN);
		table.addMouseListener(this);
			
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 350));
		
		nPanel.add(scrollPane, gbc);

	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (dtm.getRowCount() > 0) {
				dtm.removeRow(0);
			}
			
			String query = String.format("SELECT c.PokemonId, PokemonName, PokemonLevel, PokemonType, Quantity FROM cart c JOIN pokemon p ON p.pokemonId =  c.pokemonId WHERE userId = %d", idUser);
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int id = rs.getInt("PokemonId");
					String name = rs.getString("PokemonName");
					int lvl = rs.getInt("PokemonLevel");
					String type = rs.getString("PokemonType");
					int qty = rs.getInt("Quantity");
					
					Vector<Object> newData = createData(id, name, lvl, type, qty);
					tableContents.add(newData);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	private void initComp() {
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		cPanel.setBackground(Color.CYAN);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
				
		bPanel = new JPanel(new GridBagLayout());
		bPanel.setBackground(Color.CYAN);
		
		lPanel = new JPanel(new GridBagLayout());
		lPanel.setBackground(Color.CYAN);
		
		sPanel = new JPanel(new GridBagLayout());
		sPanel.setBackground(Color.CYAN);
		
		center = new JPanel(new BorderLayout());
		center.setBackground(Color.CYAN);
		
		tPanel = new JPanel(new GridBagLayout());
		tPanel.setBackground(Color.CYAN);
		
		title = new JLabel("Manage Cart");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));
		title.setVerticalAlignment(JLabel.CENTER);
		
		poke = new JLabel("Pokemon ID");
		poke.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		error = new JLabel("");
		error.setForeground(Color.red);
		error.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
				
		pokeId = new JTextField();
		pokeId.setPreferredSize(new Dimension(200, 30));
		pokeId.setPreferredSize(new Dimension(200, 30));
		
		checkOut = new JButton("Check Out");
		checkOut.addActionListener(this);
		checkOut.setPreferredSize(new Dimension(200, 30));
		back = new JButton("Back to main");
		back.addActionListener(this);
		back.setPreferredSize(new Dimension(200, 30));
		delete = new JButton("Delete");
		delete.setPreferredSize(new Dimension(200, 30));
		delete.addActionListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		tPanel.add(title, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(poke, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		cPanel.add(pokeId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		cPanel.add(delete, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		cPanel.add(checkOut, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		cPanel.add(back, gbc);

		center.add(tPanel, BorderLayout.NORTH);
		center.add(cPanel, BorderLayout.CENTER);
		
		sPanel.add(error, gbc);
	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}
	
	private boolean insertHeader(int idUser) {
		PreparedStatement ps;
		
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		try {
			ps = con.preparedStatement("INSERT INTO headertransaction (`UserId`, `Time`) VALUES (?, ?)");
			ps.setInt(1, idUser);
			ps.setTimestamp(2, sqlDate);
			ps.execute();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void getTransId() {
		
		String query = "SELECT * FROM headertransaction ORDER BY transactionId DESC LIMIT 1";
		
		ResultSet rs = con.execQuery(query);
		try {
			while (rs.next()) {
				transId = rs.getInt("transactionId");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public void getDetail() {
		while (dtm.getRowCount() > 0) {
			dtm.removeRow(0);
		}
		
		String query = String.format("SELECT c.PokemonId, PokemonName, PokemonLevel, PokemonType, Quantity FROM cart c JOIN pokemon p ON p.pokemonId =  c.pokemonId WHERE userId = %d", idUser);
		ResultSet rs = con.execQuery(query);
		PreparedStatement ps;

		try {
			while(rs.next()) {
				int pokeId = rs.getInt("PokemonId");
				int pokeQty = rs.getInt("Quantity");				
				
				try {
					ps = con.preparedStatement("INSERT INTO detailtransaction VALUES (?, ?, ?)");
					ps.setInt(1, transId);
					ps.setInt(2, pokeId);
					ps.setInt(3, pokeQty);
					ps.execute();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}			
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean delete() {
		int Id = Integer.parseInt(pokeId.getText());
		String query = "DELETE FROM cart WHERE PokemonId = ?";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, Id);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean checkTable() {
		String query = "SELECT * FROM cart";
		ResultSet rs = con.execQuery(query);
		try {
			if (rs.next() == false) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean numCheck(String str) {
		int checkNum = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i))) {
				checkNum += 1;
			}
		}
		if (checkNum == 0) {
			return false;
		} else {
			return true;
		}
		  
	}
	
	public void checkOutBag() {
		String query = "DELETE FROM cart";
		
		con.executeUpdate(query);
	}
	
	private boolean checkPokemon() {
		String pokeID = pokeId.getText();
		
		String query = "SELECT * FROM cart";
		
		ResultSet rs = con.read(query);
		try {
			while (rs.next()) {
				if (pokeID.equals(rs.getString(1))) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return true;
	}	
	
	
	public BagForm(int idUser) {
		this.idUser = idUser;
		initTable();
		initComp();
		addComp();
		initialize();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String iTemp = pokeId.getText(); 

		if (e.getSource() == back) {
			MainFormUser mfu = new MainFormUser(idUser);
			setVisible(false);
			mfu.setVisible(true);
		} else if (e.getSource() == delete) {
			int id = 0;
			try {
				id = Integer.parseInt(pokeId.getText());
				
			} catch (Exception e2) {
				// TODO: handle exception
				id = -1;
			}
			if (numCheck(iTemp)) {
				error.setText("PokemonID must be numeric");
				error.setForeground(Color.red);
			} else if(checkPokemon()) {
				error.setText("Pokemon must exist");
				error.setForeground(Color.red);
			} else if (delete()) {
				fetchData();
				error.setText("Deleted Successfully ");
				error.setForeground(Color.green);
			}
			
		} else if(e.getSource() == checkOut) {
			
				if (checkTable()) {
					error.setText("Check out Failed");
					error.setForeground(Color.red);
					
				} else {
					insertHeader(idUser);
					getTransId();
					getDetail();
					
					checkOutBag();
					fetchData();
					error.setText("Check out success");
					error.setForeground(Color.green);
					
				}
				
		}
		
	}

}
