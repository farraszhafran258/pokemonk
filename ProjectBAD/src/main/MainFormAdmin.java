package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainFormAdmin extends JFrame implements ActionListener{

	private JPanel mainPanel, cPanel;
	private JMenuBar menuBar;
	private JMenu user, manage;
	private JMenuItem signout, manaPoke;
	private GridBagConstraints gbc;
	private JLabel label;
	
	private int idUser;
	
	private void initialize() {
		setTitle("Welcome Admin");
		setSize(900, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		setVisible(true);
	}
	
	private void initComp() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.CYAN);
		
		cPanel = new JPanel(new GridBagLayout());
		cPanel.setBackground(Color.CYAN);
		gbc = new GridBagConstraints();		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.LINE_START;
		
		menuBar = new JMenuBar();
		user = new JMenu("User");
		manage = new JMenu("Manage");
		signout = new JMenuItem("Sign Out");
		signout.addActionListener(this);
		
		manaPoke = new JMenuItem("Manage Pokemon");
		manaPoke.addActionListener(this);
		
		label = new JLabel("PokemoNK");
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
		
	}
	
	private void addComp() {
		user.add(signout);
		menuBar.add(user);
		
		manage.add(manaPoke);
		menuBar.add(manage);
		
		mainPanel.add(menuBar, BorderLayout.NORTH);
		
		cPanel.add(label, gbc);
		
		add(mainPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
	}
	
	public MainFormAdmin(int idUser) {
		this.idUser = idUser;
		initComp();
		addComp();
		initialize();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == signout) {
			LoginM login = new LoginM();
			setVisible(false);
			login.setVisible(true);
		} else if (e.getSource() == manaPoke) {
			ManageForm mf = new ManageForm(idUser);
			setVisible(false);
			mf.setVisible(true);
		}
		
	}

}
