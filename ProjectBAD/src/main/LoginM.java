package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;

public class LoginM extends JFrame implements ActionListener{
	
	private JPanel nPanel, cPanel, sPanel, bPanel, lPanel;
	private JLabel title, user, pass;
	private JTextField uname;
	private JPasswordField pfield;
	private JButton regist, login;
	private GridBagConstraints gbc;
	private JLabel label;
	
	Connect con = Connect.getConnection();
	
	private String uTemp, pTemp;
	private int idUser;
	
	private void initialize() {
		setTitle("PokemonNK");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	private void initComp() {
		nPanel = new JPanel();
		nPanel.setBackground(Color.CYAN);
		
		cPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		cPanel.setBackground(Color.CYAN);
		
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		sPanel = new JPanel(new BorderLayout());
		sPanel.setBackground(Color.CYAN);
		
		bPanel = new JPanel(new GridBagLayout());
		bPanel.setBackground(Color.cyan);
		
		lPanel = new JPanel(new GridBagLayout());
		lPanel.setBackground(Color.CYAN);
		
		title = new JLabel("Log in");
		title.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
		user = new JLabel("Username");
		user.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		pass = new JLabel("Password");
		pass.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		
		uname = new JTextField();
		uname.setPreferredSize(new Dimension(555, 30));
		pfield = new JPasswordField(50);
		pfield.setPreferredSize(new Dimension(50, 30));
		
		login = new JButton("Log in");
		login.setPreferredSize(new Dimension(555, 30));
		login.addActionListener(this);
		regist = new JButton("Register");
		regist.setPreferredSize(new Dimension(555, 30));
		regist.addActionListener(this);
		
		label = new JLabel(" ");
		label.setPreferredSize(new Dimension(200, 30));
		
		nPanel.add(title);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		cPanel.add(user, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		cPanel.add(uname, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		cPanel.add(pass, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		cPanel.add(pfield, gbc);
		
		gbc.gridy = 0;
		bPanel.add(login, gbc);
		
		gbc.gridy = 1;
		bPanel.add(regist, gbc);
		
		lPanel.add(label, gbc);
		
		sPanel.add(bPanel, BorderLayout.NORTH);
		sPanel.add(lPanel, BorderLayout.CENTER);
		
	}
	
	private void addComp() {
		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(sPanel, BorderLayout.SOUTH);

	}

	public LoginM() {
		initComp();
		addComp();
		initialize();
	}
	
	private void login() {
		String query = "SELECT * FROM user";
		
		ResultSet rs = con.read(query);
		
		try {
			while (rs.next()) {
				if (uTemp.equals(rs.getString(2))) {
					if (uTemp.contains("admin") || uTemp.contains("Admin") || uTemp.contains("ADMIN")) {
						if (pTemp.equals(rs.getString(7))) {
							idUser = rs.getInt(1);
							MainFormAdmin mfa = new MainFormAdmin(idUser);
							setVisible(false);
							mfa.setVisible(true);
						} else {
							label.setText("Wrong username or password!");
							label.setForeground(Color.RED);
						}
					} else {
						if (pTemp.equals(rs.getString(7))) {
							idUser = rs.getInt(1);
							MainFormUser mfu = new MainFormUser(idUser);
							setVisible(false);
							mfu.setVisible(true);
						} else {
							label.setText("Wrong username or password!");
							label.setForeground(Color.RED);
						}
					}
				} else {
					label.setText("Wrong username or password!");
					label.setForeground(Color.RED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			uTemp = uname.getText();
			pTemp = String.valueOf(pfield.getPassword());
			
			if (uTemp.isEmpty() || pTemp.isEmpty()) {
				label.setText("All field must be filled");
				label.setForeground(Color.red);
			}

			else {
				login();
			} 
			
		} else if (e.getSource() == regist) {
			RegistM registMenu = new RegistM();
			setVisible(false);
			registMenu.setVisible(true);
		}
		
	}

}
