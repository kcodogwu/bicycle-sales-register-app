package bsr.app.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import bsr.app.db.sql.MySQLAccessLayer;

@SuppressWarnings("serial")
public class Bicycle  extends JPanel implements ActionListener {	
	String cmd = null;

	// DB Connectivity Attributes
	private MySQLAccessLayer db;
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	//private Container content;

	private JPanel detailsPanel;
	private JPanel exportButtonPanel;
	//private JPanel exportConceptDataPanel;
	private JScrollPane dbContentsPanel;

	private Border lineBorder;

	private JLabel IDLabel=new JLabel("ID:                 ");
	private JLabel BrandNameLabel=new JLabel("Brand name:               ");
	private JLabel ModelNameLabel=new JLabel("Model name:      ");
	private JLabel CostLabel=new JLabel("Cost:");
	private JLabel QuantityLabel=new JLabel("Quantity in stock:        ");

	private JTextField IDTF= new JTextField(10);
	private JTextField BrandNameTF=new JTextField(10);
	private JTextField ModelNameTF=new JTextField(10);
	private JTextField CostTF=new JTextField(10);
	private JTextField QuantityTF=new JTextField(10);


	private static BicyclesQueryTableModel TableModel;
	//Add the models to JTabels
	private JTable TableofDBContents;
	//Buttons for inserting, and updating members
	//also a clear button to clear details panel
	private JButton updateButton = new JButton("Update");
	private JButton insertButton = new JButton("Insert");
	private JButton exportButton  = new JButton("Export");
	private JButton deleteButton  = new JButton("Delete");
	private JButton clearButton  = new JButton("Clear");

	private JButton  NumberOfBicyclesForBrandButton = new JButton("No. of Bicycles for Brand:");
	private JTextField NumOfBicyclesForBrandTF  = new JTextField(12);
	private JButton AverageCostOfBicycleForBrandButton  = new JButton("Avg. Cost of Bicycle For Brand:");
	private JTextField AvgCostOfBicyclesForBrandTF  = new JTextField(12);
	private JButton ListAllBicycleBrandsButton  = new JButton("List All Bicycle Brands");
	private JButton ListAllBicycleModelsButton  = new JButton("List All Bicycle Models");



	public Bicycle(String aTitle, MySQLAccessLayer mysqlAL) {	
		//setting up the GUI
		//super(aTitle, false,false,false,false);
		super();
		db = mysqlAL;
		TableModel = new BicyclesQueryTableModel(db);
		TableofDBContents=new JTable(TableModel);
		setEnabled(true);

		//initiate_db_conn();
		//add the 'main' panel to the Internal Frame
		//content=getContentPane();
		//content.setLayout(null);
		//content.setBackground(Color.lightGray);
		this.setLayout(null);
		this.setBackground(Color.lightGray);
		lineBorder = BorderFactory.createEtchedBorder(15, Color.red, Color.black);

		//setup details panel and add the components to it
		detailsPanel=new JPanel();
		detailsPanel.setLayout(new GridLayout(11,2));
		detailsPanel.setBackground(Color.lightGray);
		detailsPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Bicycle Details"));

		detailsPanel.add(IDLabel);			
		detailsPanel.add(IDTF);
		detailsPanel.add(BrandNameLabel);		
		detailsPanel.add(BrandNameTF);
		detailsPanel.add(ModelNameLabel);		
		detailsPanel.add(ModelNameTF);
		detailsPanel.add(CostLabel);	
		detailsPanel.add(CostTF);
		detailsPanel.add(QuantityLabel);		
		detailsPanel.add(QuantityTF);

		//setup details panel and add the components to it
		exportButtonPanel=new JPanel();
		exportButtonPanel.setLayout(new GridLayout(3,2));
		exportButtonPanel.setBackground(Color.lightGray);
		exportButtonPanel.setBorder(BorderFactory.createTitledBorder(lineBorder, "Export Data"));
		exportButtonPanel.add(NumberOfBicyclesForBrandButton);
		exportButtonPanel.add(NumOfBicyclesForBrandTF);
		exportButtonPanel.add(AverageCostOfBicycleForBrandButton);
		exportButtonPanel.add(AvgCostOfBicyclesForBrandTF);
		exportButtonPanel.add(ListAllBicycleBrandsButton);
		exportButtonPanel.add(ListAllBicycleModelsButton);
		exportButtonPanel.setSize(500, 200);
		exportButtonPanel.setLocation(3, 300);
		//content.add(exportButtonPanel);
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

		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		exportButton.addActionListener(this);
		deleteButton.addActionListener(this);
		clearButton.addActionListener(this);

		this.ListAllBicycleBrandsButton.addActionListener(this);
		this.ListAllBicycleModelsButton.addActionListener(this);
		this.NumberOfBicyclesForBrandButton.addActionListener(this);
		this.AverageCostOfBicycleForBrandButton.addActionListener(this);


		//content.add(insertButton);
		//content.add(updateButton);
		//content.add(exportButton);
		//content.add(deleteButton);
		//content.add(clearButton);
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
		dbContentsPanel.setSize(700, 300);
		dbContentsPanel.setLocation(477, 0);

		//content.add(detailsPanel);
		//content.add(dbContentsPanel);
		this.add(detailsPanel);
		this.add(dbContentsPanel);

		setSize(982,645);
		setVisible(true);

		TableModel.refreshFromDB();
	}

	public void clearTFs() {
		IDTF.setText("");
		BrandNameTF.setText("");
		ModelNameTF.setText("");
		CostTF.setText("");
		QuantityTF.setText("");
		NumOfBicyclesForBrandTF.setText("");
		AvgCostOfBicyclesForBrandTF.setText("");
	}
	
	public void refreshData() {
		TableModel.refreshFromDB();
	}
	
	//event handling 
	public void actionPerformed(ActionEvent e) {
		Object target=e.getSource();
		boolean result = false;
		
		if (target == clearButton) {
			clearTFs();
		}
		
		if (target == exportButton) {
			try {	
				writeToFile(db.getBicycles());
			} catch(Exception e1) {
				System.err.println("Error with listing all bicycles.");
				e1.printStackTrace();
			}
		}

		if (target == insertButton) {		 
			try {
				result = db.addBicycle(
					BrandNameTF.getText(), 
					ModelNameTF.getText(), 
					Double.parseDouble(CostTF.getText()), 
					Integer.parseInt(QuantityTF.getText())
				);
				
				if (result) {
					clearTFs();
				}
			} catch (SQLException sqle) {
				System.err.println("Error with bicycle insert.");
				sqle.printStackTrace();
			} finally {
				TableModel.refreshFromDB();
			}
		}
		
		if (target == deleteButton) {
			if (!IDTF.getText().equals("")) {
				try {
					result = db.deleteBicycle(Integer.parseInt(IDTF.getText()));
					
					if (result) {
						clearTFs();
					}
				} catch (SQLException sqle) {
					System.err.println("Error with bicycle delete.");
					sqle.printStackTrace();
				} finally {
					TableModel.refreshFromDB();
				}
			}
		}
		
		if (target == updateButton) {	 
			if (!IDTF.getText().equals("")) {
				try { 			
					result = db.editBicycle(
					  Integer.parseInt(IDTF.getText()), 
					  BrandNameTF.getText(),
					  ModelNameTF.getText(), 
					  Double.parseDouble(CostTF.getText()), 
					  Integer.parseInt(QuantityTF.getText())
				  );
					
					if (result) {
						clearTFs();
					}
				} catch (SQLException sqle) {
					System.err.println("Error with bicycle update.");
					sqle.printStackTrace();
				} finally{
					TableModel.refreshFromDB();
				}
			}
		}

		/////////////////////////////////////////////////////////////////////////////////////
		//I have only added functionality of 2 of the button on the lower right of the template
		///////////////////////////////////////////////////////////////////////////////////

		if (target == this.ListAllBicycleBrandsButton) {
			try {	
				writeToFile(db.getAllActiveBicycleBrands());
			} catch(Exception e1) {
				System.err.println("Error with listing all bicycle brands.");
				e1.printStackTrace();
			}
		}
		
		if (target == this.ListAllBicycleModelsButton) {
			try {	
				writeToFile(db.getAllBicycleModels());
			} catch(Exception e1) {
				System.err.println("Error with listing all bicycle brands.");
				e1.printStackTrace();
			}
		}

		if (target == this.NumberOfBicyclesForBrandButton) {
			if (!NumOfBicyclesForBrandTF.getText().equals("")) {
				try{ 	
					writeToFile(db.getNumberOfBicyclesForBrand(NumOfBicyclesForBrandTF.getText()));
					clearTFs();
				} catch(Exception e1) {
					System.err.println("Error with listing the number of bicycle for the brand.");
					e1.printStackTrace();
				}
			}
		} 

		if (target == this.AverageCostOfBicycleForBrandButton) {
			if (!AvgCostOfBicyclesForBrandTF.getText().equals("")) {
				try{ 	
					writeToFile(db.getAverageCostOfBicyclesForBrand(AvgCostOfBicyclesForBrandTF.getText()));
					clearTFs();
				} catch(Exception e1) {
					System.err.println("Error with listing the average cost of bicycle for the brand.");
					e1.printStackTrace();
				}
			}
		}
	}
	///////////////////////////////////////////////////////////////////////////

	private void writeToFile(ResultSet rs){
		try{
			System.out.println("In writeToFile");
			FileWriter outputFile = new FileWriter("bicycle-export.csv");
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
}