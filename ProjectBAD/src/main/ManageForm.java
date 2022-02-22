package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class ManageForm  extends JFrame implements ActionListener, MouseListener{
	
	private JPanel nPanel, cPanel, sPanel, center, dPanel, uPanel, iPanel, bPanel, lPanel;
	private JTable table;
	private JScrollPane scrollPane;
	private DefaultTableModel model;
	private JTextField insertN, insertL, insertT, deleteI, updateI, updateN, updateL, updateT;
	private JButton insert, back, delete, update;
	private JLabel iName, iLevel, iType, dId, uID, uName, uLevel, uType, error;
	private GridBagConstraints gbc;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	private int idUser;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("Welcome Admin");
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
		
		model = new DefaultTableModel(tableContents, tableHeader);
		fetchData();
		table = new JTable(model) {
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
		scrollPane.setForeground(Color.cyan);
		
		nPanel.add(scrollPane);

	}
	
	private void fetchData() {
		// TODO Auto-generated method stub
			while (model.getRowCount() > 0) {
				model.removeRow(0);
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
		dPanel = new JPanel(new GridBagLayout());
		dPanel.setBackground(Color.CYAN);
		iPanel = new JPanel(new GridBagLayout());
		iPanel.setBackground(Color.CYAN);
		uPanel = new JPanel(new GridBagLayout());
		uPanel.setBackground(Color.CYAN);
		
		center = new JPanel(new BorderLayout());
		
		cPanel = new JPanel();
		cPanel.setBackground(Color.CYAN);
		gbc = new GridBagConstraints();
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		sPanel = new JPanel(new BorderLayout());
		sPanel.setBackground(Color.CYAN);
		
		lPanel = new JPanel(new GridBagLayout());
		lPanel.setBackground(Color.CYAN);

		bPanel = new JPanel(new GridBagLayout());
		bPanel.setBackground(Color.CYAN);
		
		iName = new JLabel("Pokemon Name: ");
		iLevel = new JLabel("Pokemon Level: ");
		iType = new JLabel("Pokemon Type");
		
		insertN = new JTextField();
		insertN.setPreferredSize(new Dimension(150, 25));
		insertL = new JTextField();
		insertL.setPreferredSize(new Dimension(150, 25));
		insertT = new JTextField();
		insertT.setPreferredSize(new Dimension(150, 25));
		
		insert = new JButton("Insert");
		insert.setPreferredSize(new Dimension(150, 25));
		insert.addActionListener(this);
		insert.addMouseListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		iPanel.add(iName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		iPanel.add(iLevel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		iPanel.add(iType, gbc);		
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		iPanel.add(insertN, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		iPanel.add(insertL, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		iPanel.add(insertT, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		iPanel.add(insert, gbc);
		
		dId = new JLabel("Pokemon ID: ");
		
		deleteI = new JTextField();
		deleteI.setPreferredSize(new Dimension(150, 25));
		
		delete = new JButton("Delete");
		delete.setPreferredSize(new Dimension(150, 25));
		delete.addActionListener(this);
		delete.addMouseListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		dPanel.add(dId, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		dPanel.add(deleteI, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		dPanel.add(delete, gbc);
		
		uID = new JLabel("Pokemon ID: ");
		uName = new JLabel("Pokemon Name: ");
		uLevel = new JLabel("Pokemon Level: ");
		uType = new JLabel("Pokemon Type: ");
		
		updateI = new JTextField();
		updateI.setPreferredSize(new Dimension(150, 25));
		updateN = new JTextField();
		updateN.setPreferredSize(new Dimension(150, 25));
		updateL = new JTextField();
		updateL.setPreferredSize(new Dimension(150, 25));
		updateT = new JTextField();
		updateT.setPreferredSize(new Dimension(150, 25));
		
		update = new JButton("Update");
		update.setPreferredSize(new Dimension(150, 25));
		update.addActionListener(this);
		update.addMouseListener(this);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		uPanel.add(uID, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		uPanel.add(uName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		uPanel.add(uLevel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		uPanel.add(uType, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		uPanel.add(updateI, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		uPanel.add(updateN, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		uPanel.add(updateL, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		uPanel.add(updateT, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		uPanel.add(update, gbc);
		
		center.add(iPanel, BorderLayout.WEST);
		
		center.add(dPanel, BorderLayout.CENTER);
		
		center.add(uPanel, BorderLayout.EAST);
		
		cPanel.add(center);
		
		back = new JButton("Back to Main");
		back.addActionListener(this);
		error = new JLabel("");
		error.setForeground(Color.red);
		
		bPanel.add(back);
		lPanel.add(error);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
		sPanel.add(lPanel, BorderLayout.SOUTH);
	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}

	public ManageForm(int idUser){
		this.idUser = idUser;
		initTable();
		initComp();
		addComp();
		initialize();
	}
	
	public boolean update() {
		String pokemonName, pokemonType;
		int pokeId, pokelvl;
		
		pokemonName = updateN.getText();
		pokemonType = updateT.getText();
		pokeId = Integer.parseInt(updateI.getText());
		pokelvl = Integer.parseInt(updateL.getText());
		
		String query = "UPDATE pokemon SET pokemonName = ?, pokemonLevel = ?, pokemonType = ? WHERE pokemonId = ?";
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setString(1, pokemonName);
			ps.setInt(2, pokelvl);
			ps.setString(3, pokemonType);
			ps.setInt(4, pokeId);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		return false;
	}
	
	public boolean delete() {
		int pokeId = Integer.parseInt(deleteI.getText());
		String query = "DELETE FROM pokemon WHERE PokemonId = ?";
		
		PreparedStatement ps = con.preparedStatement(query);
		
		try {
			ps.setInt(1, pokeId);
			return ps.executeUpdate() == 1;
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
		String iNTemp, iLTemp, iTTemp, dITemp, uITemp, uNTemp, uLTemp, uTTemp;
		iNTemp = insertN.getText();
		iLTemp = insertL.getText();
		iTTemp = insertT.getText();
		
		dITemp = deleteI.getText();
		
		uITemp = updateI.getText();
		uNTemp = updateN.getText();
		uLTemp = updateL.getText();
		uTTemp = updateT.getText();
		
		int level = 0, id = 0;
		try {
			if (e.getSource() == insert) {
				level = Integer.parseInt(uLTemp);
				
			} else if (e.getSource() == update) {
				level = Integer.parseInt(uLTemp);
			}
		} catch (Exception e2) {
			// TODO: handle exception
			level = -1;
		}
		
		if (e.getSource() == insert) {
			if (iNTemp.isEmpty() || iLTemp.isEmpty() || iTTemp.isEmpty() ) {
				error.setText("All insert field must be filled");
			} else {
				if (numCheck(iLTemp)) {
					error.setText("Pokemon level must be numeric");
					error.setForeground(Color.RED);
						
				} else {
					try {
						level = Integer.parseInt(iLTemp);
					} catch (Exception e2) {
						level = -1;
					}
					if (level < 1) {
						error.setText("Pokemon level must be more than 0");
						error.setForeground(Color.RED);
						
					} else if (con.insertPokemonAdmin(iNTemp, level, iTTemp)){
						fetchData();
						error.setText("Successfully added");
						error.setForeground(Color.green);
					}
				}
			}
			
		} else if (e.getSource()  == delete){
			if (dITemp.isEmpty()) {
				error.setText("All delete field must be filled");			
				error.setForeground(Color.RED);

			} else if (numCheck(dITemp)) {
				error.setText("Pokemon ID must be numeric");
				error.setForeground(Color.RED);
				
			}else if(delete()){
				fetchData();
				error.setText("Deleted Successfully ");
				error.setForeground(Color.green);
			} else {
				error.setText("Pokemon doesn't exist ");
				error.setForeground(Color.RED);
				
			}
			
		} else if(e.getSource() == update) {
					
			if (uITemp.isEmpty() || uNTemp.isEmpty() || uLTemp.isEmpty() || uTTemp.isEmpty() ) {
			error.setText("All update field must be filled");
			error.setForeground(Color.RED);
			
			} else if (numCheck(uITemp)) {
				error.setText("Pokemon ID must be numeric");
				error.setForeground(Color.RED);
				
			}else if (numCheck(uLTemp)) {
				error.setText("Pokemon level must be numeric");
				error.setForeground(Color.RED);
				
			}else if (level < 1) {
				error.setText("Pokemon level must be more than 0");
				error.setForeground(Color.RED);
			} else if(update()){
				fetchData();
				error.setText("Updated Successfully ");
				error.setForeground(Color.green);
			}  else {
		
				error.setText("Updated Failed");
				error.setForeground(Color.RED);
			}
			
		} else if (e.getSource() == back) {
			MainFormAdmin mfa = new MainFormAdmin(idUser);
			setVisible(false);
			mfa.setVisible(true);
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
