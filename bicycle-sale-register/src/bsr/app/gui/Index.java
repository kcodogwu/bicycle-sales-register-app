package bsr.app.gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.sql.SQLException;
import javax.swing.JFrame;
import bsr.app.db.sql.MySQLAccessLayer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Index implements ActionListener {
	private JFrame indexForm;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	private JMenuItem mntmBicycles;
	private JMenuItem mntmSales;
	private JMenuItem mntmExit;
	private Bicycle bicyclePage;
	private Sale salePage;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index window = new Index();
					window.indexForm.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Index() throws ClassNotFoundException, SQLException {
		initialize();
	}
	
	private void initialize() throws ClassNotFoundException, SQLException {
		MySQLAccessLayer db = MySQLAccessLayer.getInstance();
		bicyclePage = new Bicycle("Bicycle Details", db);
		salePage = new Sale("Bicycle Sale", db);
		indexForm = new JFrame();
		indexForm.setTitle("Bicycle Sales Register");
		indexForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		indexForm.getContentPane().setLayout(new CardLayout(0, 0));
		indexForm.setBounds(100, 100, 960, 1000);
		indexForm.getContentPane().add(bicyclePage, "BICYCLE_DETAILS");
		indexForm.getContentPane().add(salePage, "BICYCLE_SALE");
		menuBar = new JMenuBar();
		indexForm.setJMenuBar(menuBar);
		mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		mntmBicycles = new JMenuItem("Bicycles");
		mntmBicycles.addActionListener(this);
		mnMenu.add(mntmBicycles);
		mntmSales = new JMenuItem("Sales");
		mntmSales.addActionListener(this);
		mnMenu.add(mntmSales);
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(this);
		mnMenu.add(mntmExit);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		
		if (target == mntmBicycles) {
			CardLayout cl = (CardLayout) indexForm.getContentPane().getLayout();
			bicyclePage.refreshData();
			cl.show(indexForm.getContentPane(), "BICYCLE_DETAILS");
		} else if (target == mntmSales) {
			CardLayout cl = (CardLayout) indexForm.getContentPane().getLayout();
			try {
				salePage.refreshData();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			cl.show(indexForm.getContentPane(), "BICYCLE_SALE");
		} else if (target == mntmExit) {
				System.exit(0);
		}
	}
}
