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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;

public class BuyPoke extends JFrame implements ActionListener, MouseListener{

	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;
	private JTextField pokeId, pokeQty;
	private JButton insertP, back;
	private JLabel insert, quantity, error;
	private GridBagConstraints gbc;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	private int idUser;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("1");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private Vector<Object> createData(int id, String name, int lvl ,String type) {
		tableRows = new Vector<>();
		tableRows.add(id);
		tableRows.add(name);
		tableRows.add(lvl);
		tableRows.add(type);
		
		return tableRows;
	}
	
	private void initTable() {
		nPanel = new JPanel();
		nPanel.setBackground(Color.CYAN);
		
		tableContents = new Vector<Vector<Object>>();
		
		tableHeader = new Vector<Object>();
		
		tableHeader.add("PokemonId");
		tableHeader.add("PokemonName");
		tableHeader.add("PokemonLevel");
		tableHeader.add("PokemonType");
		
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
		
		nPanel.add(scrollPane);

	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (dtm.getRowCount() > 0) {
				dtm.removeRow(0);
			}
			
			String query = "SELECT * FROM Pokemon";
			ResultSet rs = con.execQuery(query);

			try {
				while(rs.next()) {
					int id = rs.getInt("PokemonId");
					String name = rs.getString("PokemonName");
					int lvl = rs.getInt("PokemonLevel");
					String type = rs.getString("PokemonType");
					
					Vector<Object> newData = createData(id, name, lvl, type);
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
		
		sPanel = new JPanel(new BorderLayout());
		sPanel.setBackground(Color.CYAN);
		
		insert = new JLabel("Insert Pokemon ID");
		insert.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		quantity = new JLabel("Quantity");
		quantity.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		pokeId = new JTextField();
		pokeId.setPreferredSize(new Dimension(200, 30));
		pokeQty = new JTextField();
		pokeQty.setPreferredSize(new Dimension(200, 30));
		
		insertP = new JButton("Insert");
		insertP.addActionListener(this);
		back = new JButton("Back to main");
		back.addActionListener(this);
				
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(insert, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(quantity, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		cPanel.add(pokeId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		cPanel.add(pokeQty, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bPanel.add(insertP, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		bPanel.add(back, gbc);
		
		lPanel.add(error, gbc);
		
		sPanel.add(bPanel, BorderLayout.CENTER);
		
		gbc.gridy = 1;
		sPanel.add(lPanel, BorderLayout.SOUTH);

	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}
	
	public BuyPoke(int idUser) {
		this.idUser = idUser;
		initTable();
		initComp();
		addComp();
		initialize();
	}
	
	private boolean checkPokemon() {
		String pokeID = pokeId.getText();
		
		String query = "SELECT * FROM pokemon";
		
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
	
	private boolean buy(int pId, int uId, int qty) {
		PreparedStatement ps;
		try {
			ps = con.preparedStatement("INSERT INTO cart (`PokemonId`, `UserId`, `Quantity`)VALUES (?, ?, ?)");
			ps.setInt(1, pId);
			ps.setInt(2, uId);
			ps.setInt(3, qty);
			ps.execute();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == back) {
			MainFormUser mfu = new MainFormUser(idUser);
			setVisible(false);
			mfu.setVisible(true);
		} else if (e.getSource() == insertP) {
			String iTemp, qTemp;
			iTemp = pokeId.getText();
			qTemp = pokeQty.getText();
			
			int pokemonID = 0, quantity = 0;
			
			try {
				quantity = Integer.parseInt(qTemp);
				
			} catch (Exception e2) {
				// TODO: handle exception
				quantity = -1;
			}
			
			try {
				pokemonID = Integer.parseInt(iTemp);
				
			} catch (Exception e2) {
				// TODO: handle exception
				pokemonID = -1;
			}
			
			if (iTemp.isEmpty() || qTemp.isEmpty()) {
				error.setText("All field must be filled");
			} else {
				if (numCheck(iTemp)) {
					error.setText("Pokemon ID must be numeric");
					error.setForeground(Color.RED);
				} else if (checkPokemon()) {
					error.setText("Pokemon must exist");
					error.setForeground(Color.RED);
					
				} else if (numCheck(qTemp)) {
					error.setText("Quantity must be numeric");
					error.setForeground(Color.RED);
					
				}else if (quantity < 0){
					error.setText("Pokemon level cannot below 0");						
					error.setForeground(Color.RED);
					
				} else if(buy(pokemonID, idUser, quantity)){
						fetchData();
						error.setText("Pokemon added");
						error.setForeground(Color.green);
				}
			}
			
			
		}
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


}
