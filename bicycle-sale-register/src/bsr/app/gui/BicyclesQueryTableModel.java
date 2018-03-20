package bsr.app.gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

import bsr.app.db.sql.MySQLAccessLayer;

@SuppressWarnings("serial")
class BicyclesQueryTableModel extends AbstractTableModel {
	Vector modelData; //will hold String[] objects
	int colCount;
	String[] headers = new String[0];
	Connection con;
	String[] record;
	ResultSet rs = null;
	MySQLAccessLayer db;

	public BicyclesQueryTableModel(MySQLAccessLayer mysqlAL) {
		modelData = new Vector();
		db = mysqlAL;
	} // end constructor QueryTableModel

	public String getColumnName(int i) {
		return headers[i];
	}
	
	public int getColumnCount() {
		return colCount;
	}
	
	public int getRowCount() {
		return modelData.size();
	}
	
	public Object getValueAt(int row, int col) {
		return ((String[])modelData.elementAt(row))[col];
	}

	public void refreshFromDB() {
		//modelData is the data stored by the table
		//when set query is called the data from the 
		//DB is queried using �SELECT * FROM myInfo� 
		//and the data from the result set is copied 
		//into the modelData. Every time refreshFromDB is
		//called the DB is queried and a new 
		//modelData is created  
		modelData = new Vector();
		
		try {
			//Execute the query and store the result set and its metadata
			rs = db.getBicycles();
			ResultSetMetaData meta = rs.getMetaData();
		
			//to get the number of columns
			colCount = meta.getColumnCount(); 
			// Now must rebuild the headers array with the new column names
			headers = new String[colCount];
	
			for(int h = 0; h < colCount; h++) {
				headers[h] = meta.getColumnLabel(h+1);
			}//end for loop
		
			// fill the cache with the records from the query, ie get all the rows
		
			while(rs.next()) {
				record = new String[colCount];
				
				for(int i = 0; i < colCount; i++) {
					record[i] = rs.getString(i + 1);
				}//end for loop
				
				modelData.addElement(record);
			}// end while loop
			
			fireTableChanged(null); //Tell the listeners a new table has arrived.
		} catch(Exception e) {
					System.out.println("Error with refreshFromDB Method\n"+e.toString());
		} // end catch clause to query table
	}//end refreshFromDB method
}// end class QueryTableModel