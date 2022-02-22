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
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class RegistM extends JFrame implements ActionListener{

	private JPanel nPanel, cPanel, sPanel, gPanel, bPanel, lPanel;
	private JLabel title, user, fName, lName, email, Age, pass, rePass, label;
	private JTextField uname, first, last, emailField;
	private JPasswordField pfield, reField;
	private JButton regist, login;
	private GridBagConstraints gbc;
	private JRadioButton male, female;
	private ButtonGroup genderGrp;
	private JSpinner ageSpin;
//	private JTable table;
//	private JScrollPane scrollPane;
//	private DefaultTableModel model;
	
	Vector<Vector<Object>> tableContents;
	Vector<Object> tableRows, tableHeader;
	
	Connect con = Connect.getConnection();
	
	private void initialize() {
		setTitle("PokemonNK");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private void initComp() {
		nPanel = new JPanel();
		nPanel.setBackground(Color.CYAN);
		
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		cPanel.setBackground(Color.CYAN);
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		gPanel = new JPanel();
		gPanel.setBackground(Color.CYAN);
		
		bPanel = new JPanel(new GridBagLayout());
		bPanel.setBackground(Color.CYAN);
		
		lPanel = new JPanel(new GridBagLayout());
		lPanel.setBackground(Color.CYAN);
		
		sPanel = new JPanel(new BorderLayout());
		sPanel.setBackground(Color.CYAN);
		
		title = new JLabel("Register");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		user = new JLabel("Username");
		user.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		fName = new JLabel("First Name");
		fName.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lName = new JLabel("Last Name");
		lName.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		email = new JLabel("Email");
		email.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		Age = new JLabel("Age");
		Age.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		pass = new JLabel("Password");
		pass.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		rePass =  new JLabel("Confirm Password");
		rePass.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		label = new JLabel(" ");
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		regist = new JButton("Register");
		regist.addActionListener(this);
		login = new JButton("Back to Login");
		login.addActionListener(this);
		
		genderGrp = new ButtonGroup();
		male = new JRadioButton("Male");
		male.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		male.setActionCommand("Male");
		male.setBackground(Color.cyan);
		male.addActionListener(this);
		
		female = new JRadioButton("Female");
		female.setActionCommand("Female");
		female.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		female.setBackground(Color.cyan);
		female.addActionListener(this);
		
		ageSpin = new JSpinner(new SpinnerNumberModel(11, 11, 99, 1));
		ageSpin.setPreferredSize(new Dimension(555, 30));
				
		uname = new JTextField();
		uname.setPreferredSize(new Dimension(555, 30));
		first = new JTextField();
		first.setPreferredSize(new Dimension(555, 30));
		last = new JTextField();
		last.setPreferredSize(new Dimension(555, 30));
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(555, 30));
		pfield = new JPasswordField(50);
		pfield.setPreferredSize(new Dimension(50, 30));
		reField = new JPasswordField(50);
		reField.setPreferredSize(new Dimension(50, 30));

		nPanel.add(title);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(user, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(uname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		cPanel.add(fName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		cPanel.add(first, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		cPanel.add(lName, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		cPanel.add(last, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		cPanel.add(email, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		cPanel.add(emailField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		genderGrp.add(male);
		genderGrp.add(female);
		gPanel.add(male);
		gPanel.add(female);
		cPanel.add(gPanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		cPanel.add(Age, gbc);
		gbc.gridy = 10;
		cPanel.add(ageSpin, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 11;
		cPanel.add(pass, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 12;
		cPanel.add(pfield, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 13;
		cPanel.add(rePass, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 14;
		cPanel.add(reField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		bPanel.add(regist, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		bPanel.add(login, gbc);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lPanel.add(label, gbc);
		
		sPanel.add(lPanel, BorderLayout.SOUTH);		
	}
	
	private void addComp() {
		
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);
	}
	
	public RegistM() {
		initComp();
		addComp();
		initialize();
	}
	
	private boolean usernameCheck() {
		
		String uTemp = uname.getText();
		
		String query = "SELECT * FROM user";
		
		ResultSet rs = con.read(query);
		
		try {
			while (rs.next()) {
				if (uTemp.equals(rs.getString(2))) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			LoginM loginMenu = new LoginM();
			setVisible(false);
			loginMenu.setVisible(true);
		} else if(e.getSource() == regist){
			String uTemp, fTemp, lTemp, eTemp, pTemp, rTemp, fullName, gender;
			int age;
			uTemp = uname.getText();
			fTemp = first.getText();
			lTemp = last.getText();
			fullName = fTemp + " " + lTemp;
			age =  (int) ageSpin.getValue();
			eTemp = emailField.getText();
			pTemp = String.valueOf(pfield.getPassword());
			rTemp = String.valueOf(reField.getPassword());
			
//			Masih blm tau gmn
			if (uTemp.isEmpty() || fTemp.isEmpty() || lTemp.isEmpty() || eTemp.isEmpty() || eTemp.isEmpty() || pTemp.isEmpty() || rTemp.isEmpty()) {
				label.setText("All field can't be empty");
				label.setForeground(Color.red);
				
			} else if (usernameCheck()) {
				label.setText("Username has been taken!");
				label.setForeground(Color.RED);
			}else if (uTemp.length() < 8 || uTemp.length() > 15) {
				label.setText("Username length must be more than 8 and no more than 15");
				label.setForeground(Color.red);
				
			} else if (!(eTemp.contains("@") && eTemp.contains(".com"))) {
				label.setText("Email format must be 'xxxx@xxx.com'");
				label.setForeground(Color.red);
				
			} else if (!(male.isSelected() || female.isSelected())) {
				label.setText("Must choose gender");
				label.setForeground(Color.red);
				
			} else if (pTemp.length() > 20) {
				label.setText("Password length must be less than 20");
				label.setForeground(Color.red);		
				
			} else if (pTemp.matches("^[a-zA-Z0-9]*$")) {
				label.setText("Password must consist of alphabet and number characters");
				label.setForeground(Color.red);
				
			} else if (!rTemp.equals(pTemp)) {
				label.setText("Password doesn't match");
				label.setForeground(Color.red);
				
			} else  {
				gender = genderGrp.getSelection().getActionCommand();

				if (con.register(uTemp, fullName, age, eTemp, gender, pTemp)) {
					label.setText("Registered");
					label.setForeground(Color.green);
					
					
				}
				
			}
		}
		
	}

}
