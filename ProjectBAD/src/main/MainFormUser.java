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

public class MainFormUser extends JFrame implements ActionListener{

	private JPanel mainPanel, cPanel;
	private JMenuBar menuBar;
	private JMenu user, adventure, trans;
	private JMenuItem signout, pokeM, bag, viewTrfH;
	private JLabel label;
	private GridBagConstraints gbc;
	
	private int idUser;
	
	private void initialize() {
		setTitle("Welcome User");
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
		
		label = new JLabel("PokemoNK");
		label.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
		
		menuBar = new JMenuBar();
		user = new JMenu("User");
		adventure = new JMenu("Adventure");
		trans = new JMenu("Transaction");
		
		signout = new JMenuItem("Sign Out");
		signout.addActionListener(this);
		
		pokeM = new JMenuItem("Pokemon Market");
		pokeM.addActionListener(this);
		
		bag = new JMenuItem("Bag");
		bag.addActionListener(this);
		
		viewTrfH = new JMenuItem("View Transaction History");
		viewTrfH.addActionListener(this);
	}
	
	private void addComp() {
		user.add(signout);
		menuBar.add(user);
		
		adventure.add(pokeM);
		adventure.add(bag);
		menuBar.add(adventure);
		
		trans.add(viewTrfH);
		menuBar.add(trans);
		mainPanel.add(menuBar, BorderLayout.NORTH);
		
		cPanel.add(label, gbc);
		
		add(mainPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);

	}
	
	public MainFormUser(int idUser) {
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
		} else if (e.getSource() == bag) {
			BagForm bf = new BagForm(idUser);
			setVisible(false);
			bf.setVisible(true);
		} else if (e.getSource() == pokeM) {
			BuyPoke bp = new BuyPoke(idUser);
			setVisible(false);
			bp.setVisible(true);
		} else if (e.getSource() == viewTrfH) {
			ViewHis vh = new ViewHis(idUser);
			setVisible(false);
			vh.setVisible(true);
		}
		
	}

}
