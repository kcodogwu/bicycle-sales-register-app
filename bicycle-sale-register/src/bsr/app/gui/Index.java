package bsr.app.gui;

import java.awt.CardLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Index {
	private JFrame indexForm;
	private JPanel panel;
	private JPanel panel1;
	
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
	
	public Index() {
		initialize();
	}
	
	private void initialize() {
		indexForm = new JFrame();
		indexForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		indexForm.getContentPane().setLayout(new CardLayout(0, 0));
		indexForm.setBounds(100, 100, 960, 1000);
		panel = new JPanel();
		panel1 = new JPanel();
		panel.add(new Bicycle("Bicycle Details"));
		panel1.add(new Sale("Bicycle Sale"));
		indexForm.getContentPane().add(panel, "BICYCLE_DETAILS");
		indexForm.getContentPane().add(panel1, "BICYCLE_SALE");
	}
}
