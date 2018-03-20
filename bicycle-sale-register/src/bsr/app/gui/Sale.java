package bsr.app.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import bsr.app.db.sql.MySQLAccessLayer;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class Sale  extends JPanel implements ActionListener {	
	String cmd = null;

	// DB Connectivity Attributes
	private MySQLAccessLayer db;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	private int[] bicycleIdArr;
	private String[] bicycleArr;
	private double[] costArr;
	private int[] quantityArr;
	
	//private Container content;

	private JPanel detailsPanel;
	private JPanel bicycleDetailsPanel;
	private JPanel exportButtonPanel;
	//private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;

	private JLabel IDLabel=new JLabel("Sale Order Number:                 ");
	private JLabel FirstNameLabel=new JLabel("Customer:               ");
	private JLabel LastNameLabel=new JLabel("Address:      ");
	private JLabel AgeLabel=new JLabel("Phone Number:        ");
	private JLabel TotalLabel=new JLabel("Total:                 ");
	private JLabel AmountPaidLabel=new JLabel("Amount Paid:               ");
	private JLabel ChangeLabel=new JLabel("Change:      ");

	private JTextField SaleOrderNumberTF= new JTextField(10);
	private JTextField CustomerTF=new JTextField(10);
	private JTextField AddressTF=new JTextField(10);
	private JTextField PhoneNumberTF=new JTextField(10);
	private JTextField TotalTF=new JTextField(10);
	private JTextField AmountPaidTF=new JTextField(10);
	private JTextField ChangeTF=new JTextField(10);


	private static SalesQueryTableModel TableModel;
	//Add the models to JTabels
	private JTable TableofDBContents;
	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");

	private JButton  NumOfTransactionsWithCustomerButton = new JButton("No. Of Transactions With Customer:");
	private JTextField NumOfTransactionsTF  = new JTextField(12);
	private JButton TotalAmountPaidByCustomerButton  = new JButton("Total Amount Paid By Customer:");
	private JTextField TotalPaidByCustomerTF  = new JTextField(12);
	private JButton ListNumOfBicyclesSoldPerModelButton  = new JButton("List No. Of Bicycles Sold Per Model");
	private JButton ListAllSaleOrdersButton  = new JButton("List All Sale Orders");
	private final JTextField No1TF = new JTextField(10);
	private final JComboBox No1ComboBox = new JComboBox();
	private final JComboBox No2ComboBox = new JComboBox();
	private final JTextField No2TF = new JTextField(10);
	private final JComboBox No3ComboBox = new JComboBox();
	private final JTextField No3TF = new JTextField(10);

	public Sale(String aTitle, MySQLAccessLayer mysqlAL) throws SQLException {	
		//setting up the GUI
		//super(aTitle, false,false,false,false);
		super();
		db = mysqlAL;
		TableModel = new SalesQueryTableModel(db);
		TableofDBContents=new JTable(TableModel);
		setEnabled(true);

		//add the 'main' panel to the Internal Frame
		//content=getContentPane();
		this.setLayout(null);
		this.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		//setup details panel and add the components to it
		detailsPanel=new JPanel();
		detailsPanel.setLayout(new GridLayout(11,2));
		detailsPanel.setBackground(Color.lightGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Sale Details"));

		detailsPanel.add(IDLabel);			
		detailsPanel.add(SaleOrderNumberTF);
		detailsPanel.add(FirstNameLabel);		
		detailsPanel.add(CustomerTF);
		detailsPanel.add(LastNameLabel);		
		detailsPanel.add(AddressTF);
		detailsPanel.add(AgeLabel);	
		detailsPanel.add(PhoneNumberTF);
		
		JLabel BicycleLabel = new JLabel("Bicycle");
		BicycleLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		BicycleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detailsPanel.add(BicycleLabel);
		
		JLabel QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		QuantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detailsPanel.add(QuantityLabel);

		detailsPanel.add(No1ComboBox);
		detailsPanel.add(No1TF);
		detailsPanel.add(No2ComboBox);
		detailsPanel.add(No2TF);
		detailsPanel.add(No3ComboBox);
		detailsPanel.add(No3TF);
		detailsPanel.add(TotalLabel);		
		TotalTF.setText("0");
		detailsPanel.add(TotalTF);
		detailsPanel.add(AmountPaidLabel);
		detailsPanel.add(AmountPaidTF);
		detailsPanel.add(ChangeLabel);
		ChangeTF.setText("0");
		detailsPanel.add(ChangeTF);

		//setup details panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(NumOfTransactionsWithCustomerButton);
		exportButtonPanel.add(NumOfTransactionsTF);
		exportButtonPanel.add(TotalAmountPaidByCustomerButton);
		exportButtonPanel.add(TotalPaidByCustomerTF);
		exportButtonPanel.add(ListNumOfBicyclesSoldPerModelButton);
		exportButtonPanel.add(ListAllSaleOrdersButton);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		this.add(exportButtonPanel);

		insertButton.setSize(100, 30);
		updateButton.setSize(100, 30);
		exportButton.setSize (100, 30);
		deleteButton.setSize (100, 30);
		clearButton.setSize (100, 30);

		insertButton.setLocation(370, 10);
		updateButton.setLocation(370, 110);
		exportButton.setLocation (370, 160);
		deleteButton.setLocation (370, 60);
		clearButton.setLocation (370, 210);
		
		No1ComboBox.addActionListener(this);
		No2ComboBox.addActionListener(this);
		No3ComboBox.addActionListener(this);
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		this.ListNumOfBicyclesSoldPerModelButton.addActionListener(this);
		this.NumOfTransactionsWithCustomerButton.addActionListener(this);
		this.TotalAmountPaidByCustomerButton.addActionListener(this);
		this.ListAllSaleOrdersButton.addActionListener(this);

		this.add(insertButton);
		this.add(updateButton);
		this.add(exportButton);
		this.add(deleteButton);
		this.add(clearButton);


		TableofDBContents.setPreferredScrollableViewportSize(new Dimension(900, 300));

		dbContentsPanel=new JScrollPane(TableofDBContents,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		dbContentsPanel.setBackground(Color.lightGray);
		dbContentsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder,"Database Content"));

		detailsPanel.setSize(360, 300);
		detailsPanel.setLocation(3,0);
		dbContentsPanel.setSize(900, 300);
		dbContentsPanel.setLocation(477, 0);

		this.add(detailsPanel);
		this.add(dbContentsPanel);

		setSize(982,645);
		setVisible(true);

		TableModel.refreshFromDB();
		loadComboBox();
		
		No1TF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				calculateTotal(No1TF);
			}

			public void insertUpdate(DocumentEvent e) {
				calculateTotal(No1TF);
			}

			public void removeUpdate(DocumentEvent e) {
				calculateTotal(No1TF);
			}
		});
		
		No2TF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				calculateTotal(No2TF);
			}

			public void insertUpdate(DocumentEvent e) {
				calculateTotal(No2TF);
			}

			public void removeUpdate(DocumentEvent e) {
				calculateTotal(No2TF);
			}
		});
		
		No3TF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				calculateTotal(No3TF);
			}

			public void insertUpdate(DocumentEvent e) {
				calculateTotal(No3TF);
			}

			public void removeUpdate(DocumentEvent e) {
				calculateTotal(No3TF);
			}
		});
		
		AmountPaidTF.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				calculateTotal(AmountPaidTF);
			}

			public void insertUpdate(DocumentEvent e) {
				calculateTotal(AmountPaidTF);
			}

			public void removeUpdate(DocumentEvent e) {
				calculateTotal(AmountPaidTF);
			}
		});
	}
	
	public void loadComboBox() throws SQLException {
		int i = 0;
		
		rs = db.getBicyclesAndStockQuantity();
		rs.last();
		bicycleIdArr = new int[rs.getRow()];
		bicycleArr = new String[rs.getRow() + 1];
		costArr = new double[rs.getRow()];
		quantityArr = new int[rs.getRow()];
		rs.beforeFirst();
		bicycleArr[0] = "Select Bicycle";
		
		while (rs.next()) {
			bicycleIdArr[i] = rs.getInt("Bicycle IDs");
			bicycleArr[i + 1] = rs.getString("Bicycles");
			costArr[i] = rs.getDouble("Cost");
			quantityArr[i] = rs.getInt("Quantity");
			i++;
		}
		
		No1ComboBox.setModel(new DefaultComboBoxModel(bicycleArr));
		No2ComboBox.setModel(new DefaultComboBoxModel(bicycleArr));
		No3ComboBox.setModel(new DefaultComboBoxModel(bicycleArr));
	}

	public void clearTFs() {
		SaleOrderNumberTF.setText("");
		CustomerTF.setText("");
		PhoneNumberTF.setText("");
		AddressTF.setText("");
		No1ComboBox.setSelectedIndex(0);
		No1TF.setText("");
		No2ComboBox.setSelectedIndex(0);
		No2TF.setText("");
		No3ComboBox.setSelectedIndex(0);
		No3TF.setText("");
		TotalTF.setText("0.00");
		AmountPaidTF.setText("");
		ChangeTF.setText("0.00");
		NumOfTransactionsTF.setText("");
		TotalPaidByCustomerTF.setText("");
	}
	
	public void refreshData() throws SQLException {
		TableModel.refreshFromDB();
		loadComboBox();
	}
	
	//event handling 
	public void actionPerformed(ActionEvent e) {
		Object target=e.getSource();
		boolean result = false;		 
		String orderedBicycles = "";
		String orderedQuantities = "";
		
		if (target == clearButton) {
			clearTFs();
		}
		
		if (target == exportButton) {
			try {	
				writeToFile(db.getSales());
			} catch(Exception e1) {
				System.err.println("Error with listing all sales.");
				e1.printStackTrace();
			}
		}

		if (target == insertButton) {
			if (No1ComboBox.getSelectedIndex() != 0)
				orderedBicycles = "" + bicycleIdArr[No1ComboBox.getSelectedIndex() - 1]
			;
			
			if (No2ComboBox.getSelectedIndex() != 0) {
				if (orderedBicycles != "")
					orderedBicycles += ", " + bicycleIdArr[No2ComboBox.getSelectedIndex() - 1]
				; else
					orderedBicycles = "" + bicycleIdArr[No2ComboBox.getSelectedIndex() - 1]
				;
			}
			
			if (No3ComboBox.getSelectedIndex() != 0) {
				if (orderedBicycles != "")
					orderedBicycles += ", " + bicycleIdArr[No3ComboBox.getSelectedIndex() - 1]
				; else
					orderedBicycles = "" + bicycleIdArr[No3ComboBox.getSelectedIndex() - 1]
				;
			}
			
			if (!No1TF.getText().equals("") && Integer.parseInt(No1TF.getText()) != 0)
				orderedQuantities = No1TF.getText()
			;
			
			if (!No2TF.getText().equals("") && Integer.parseInt(No2TF.getText()) != 0) {
				if (orderedQuantities != "")
					orderedQuantities += ", " +  No2TF.getText()
				; else
					orderedQuantities = No2TF.getText()
				;
			}
			
			if (!No3TF.getText().equals("") && Integer.parseInt(No3TF.getText()) != 0) {
				if (orderedQuantities != "")
					orderedQuantities += ", " +  No3TF.getText()
				; else
					orderedQuantities = No3TF.getText()
				;
			}
			
			try {
				result = db.addSale(
					CustomerTF.getText(), 
					PhoneNumberTF.getText(), 
					AddressTF.getText(), 
					orderedBicycles, 
					orderedQuantities, 
					Double.parseDouble(TotalTF.getText()), 
					Double.parseDouble(AmountPaidTF.getText()), 
					Double.parseDouble(ChangeTF.getText())
				);
				
				if (result) {
					clearTFs();
					loadComboBox();
				}
			} catch (SQLException sqle) {
				System.err.println("Error with sale insert.");
				sqle.printStackTrace();
			} finally {
				TableModel.refreshFromDB();
			}
		}
		
		if (target == deleteButton) {
			if (!SaleOrderNumberTF.getText().equals("")) {
				try {
					result = db.deleteSale(Integer.parseInt(SaleOrderNumberTF.getText()));
					
					if (result) {
						clearTFs();
						loadComboBox();
					}
				} catch (SQLException sqle) {
					System.err.println("Error with sale delete.");
					sqle.printStackTrace();
				} finally {
					TableModel.refreshFromDB();
				}
			}
		}
		
		if (target == updateButton) {	 	
			if (No1ComboBox.getSelectedIndex() != 0)
				orderedBicycles = "" + bicycleIdArr[No1ComboBox.getSelectedIndex() - 1]
			;
			
			if (No2ComboBox.getSelectedIndex() != 0) {
				if (orderedBicycles != "")
					orderedBicycles += ", " + bicycleIdArr[No2ComboBox.getSelectedIndex() - 1]
				; else
					orderedBicycles = "" + bicycleIdArr[No2ComboBox.getSelectedIndex() - 1]
				;
			}
			
			if (No3ComboBox.getSelectedIndex() != 0) {
				if (orderedBicycles != "")
					orderedBicycles += ", " + bicycleIdArr[No3ComboBox.getSelectedIndex() - 1]
				; else
					orderedBicycles = "" + bicycleIdArr[No3ComboBox.getSelectedIndex() - 1]
				;
			}
			
			if (!No1TF.getText().equals("") && Integer.parseInt(No1TF.getText()) != 0)
				orderedQuantities = No1TF.getText()
			;
			
			if (!No2TF.getText().equals("") && Integer.parseInt(No2TF.getText()) != 0) {
				if (orderedQuantities != "")
					orderedQuantities += ", " +  No2TF.getText()
				; else
					orderedQuantities = No2TF.getText()
				;
			}
			
			if (!No3TF.getText().equals("") && Integer.parseInt(No3TF.getText()) != 0) {
				if (orderedQuantities != "")
					orderedQuantities += ", " +  No3TF.getText()
				; else
					orderedQuantities = No3TF.getText()
				;
			}
			
			if (!SaleOrderNumberTF.getText().equals("")) {
				try {
					result = db.editSale(
						Integer.parseInt(SaleOrderNumberTF.getText()),
						CustomerTF.getText(), 
						PhoneNumberTF.getText(), 
						AddressTF.getText(), 
						orderedBicycles, 
						orderedQuantities, 
						Double.parseDouble(TotalTF.getText()), 
						Double.parseDouble(AmountPaidTF.getText()), 
						Double.parseDouble(ChangeTF.getText())
					);
					
					if (result) {
						clearTFs();
						loadComboBox();
					}
				} catch (SQLException sqle){
					System.err.println("Error with sale update.");
					sqle.printStackTrace();
				} finally{
					TableModel.refreshFromDB();
				}
			}
		}

		/////////////////////////////////////////////////////////////////////////////////////
		//I have only added functionality of 2 of the button on the lower right of the template
		///////////////////////////////////////////////////////////////////////////////////

		if (target == this.ListNumOfBicyclesSoldPerModelButton) {
			try {	
				writeToFile(db.getTotalBicyclesSold());
				clearTFs();
				loadComboBox();
			} catch(Exception e1) {
				System.err.println("Error with listing the number of bicycles sold.");
				e1.printStackTrace();
			}
		}

		if (target == this.ListAllSaleOrdersButton) {
			try {	
				writeToFile(db.getSaleOrders());
				clearTFs();
				loadComboBox();
			} catch(Exception e1) {
				System.err.println("Error with listing the sales order summary.");
				e1.printStackTrace();
			}
		}
		
		if (target == this.NumOfTransactionsWithCustomerButton) {
			try {					
				writeToFile(db.getNumOfCustomerTransactions(this.NumOfTransactionsTF.getText()));
				clearTFs();
				loadComboBox();
			} catch(Exception e1) {
				System.err.println("Error with listing the number of transactions with a customer.");
				e1.printStackTrace();
			}
		}
		
		if (target == this.TotalAmountPaidByCustomerButton) {
			try {					
				writeToFile(db.getTotalAmountPaidByCustomer(this.TotalPaidByCustomerTF.getText()));
				clearTFs();
				loadComboBox();
			} catch(Exception e1) {
				System.err.println("Error with listing the total amount paid by a customer.");
				e1.printStackTrace();
			}
		}

		if (target == No1ComboBox) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No1TF.getText().equals("") && No1ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No1TF.getText()) * costArr[No1ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
		
		if (target == No2ComboBox) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No2TF.getText().equals("") && No2ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No2TF.getText()) * costArr[No2ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
		
		if (target == No3ComboBox) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No3TF.getText().equals("") && No3ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No3TF.getText()) * costArr[No3ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
	}
	///////////////////////////////////////////////////////////////////////////

	private void writeToFile(ResultSet rs){
		try{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("sale-export.csv");
			PrintWriter printWriter = new PrintWriter(outputFile);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numColumns = rsmd.getColumnCount();

			for(int i=0;i<numColumns;i++){
				printWriter.print(rsmd.getColumnLabel(i+1)+",");
			}
			printWriter.print("\n");
			while(rs.next()){
				for(int i=0;i<numColumns;i++){
					printWriter.print(rs.getString(i+1)+",");
				}
				printWriter.print("\n");
				printWriter.flush();
			}
			printWriter.close();
		}
		catch(Exception e){e.printStackTrace();}
	}

	
	public void calculateTotal(Object target) {
		if (target == No1TF) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No1TF.getText().equals("") && No1ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No1TF.getText()) * costArr[No1ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
		
		if (target == No2TF) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No2TF.getText().equals("") && No2ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No2TF.getText()) * costArr[No2ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
		
		if (target == No3TF) {
			double t = Double.parseDouble(TotalTF.getText());
			
			if (!No3TF.getText().equals("") && No3ComboBox.getSelectedIndex() != 0) {
				t += (Integer.parseInt(No3TF.getText()) * costArr[No3ComboBox.getSelectedIndex() - 1]);
			}
			
			TotalTF.setText("" + t);
		}
		
		if (target == AmountPaidTF) {
			double t = Double.parseDouble(TotalTF.getText());
			double p = AmountPaidTF.getText().equals("") ? 0 : Double.parseDouble(AmountPaidTF.getText());
			
			ChangeTF.setText("" + (p - t));
		}
	}	
}