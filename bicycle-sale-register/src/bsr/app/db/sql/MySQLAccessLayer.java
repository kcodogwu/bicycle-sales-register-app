package bsr.app.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MySQLAccessLayer {
	private Connection con = null;
	private CallableStatement cStmt = null;
	private static MySQLAccessLayer single;
	private static boolean created = false;
	
	private MySQLAccessLayer() throws ClassNotFoundException, SQLException {
		super();
		single = this;
		created = true;
		initiateDbConnection();
	}
	
	public static MySQLAccessLayer getInstance() throws ClassNotFoundException, SQLException {
		if (!created)
			single = new MySQLAccessLayer()
		;
		
		return single;
	}

	public void initiateDbConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String url="jdbc:mysql://localhost:3306/bicycle_sale_register?useSSL=no";
		
		con = DriverManager.getConnection(url, "root", "#880854585kCo#0");
	}
	
	public ResultSet getBicycles() throws SQLException {
		String sqlStr = "{ call get_bicycles() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public boolean addBicycle(String brandName, String modelName, double cost, int quantity) throws SQLException {
		String sqlStr = "{ call add_bicycle(?, ?, ?, ?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("bicycle_brand_name_param", brandName);
		cStmt.setString("bicycle_model_param", modelName);
		cStmt.setDouble("cost_price_param", cost);
		cStmt.setInt("quantity_in_stock_param", quantity);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public boolean editBicycle(int bicycleId, String brandName, String modelName, double cost, int quantity) throws SQLException {
		String sqlStr = "{ call edit_bicycle(?, ?, ?, ?, ?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setInt("bicycle_id_param", bicycleId);
		cStmt.setString("bicycle_brand_name_param", brandName);
		cStmt.setString("bicycle_model_param", modelName);
		cStmt.setDouble("cost_price_param", cost);
		cStmt.setInt("quantity_in_stock_param", quantity);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public boolean deleteBicycle(int bicycleId) throws SQLException {
		String sqlStr = "{ call delete_bicycle(?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setInt("bicycle_id_param", bicycleId);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public ResultSet getAllActiveBicycleBrands() throws SQLException {
		String sqlStr = "{ call get_all_active_bicycle_brands() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getAllBicycleModels() throws SQLException {
		String sqlStr = "{ call get_all_bicycle_models() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getNumberOfBicyclesForBrand(String brand) throws SQLException {
		String sqlStr = "{ call get_number_of_bicycles_for_brand(?) }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("bicycle_brand_name_param", brand);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getAverageCostOfBicyclesForBrand(String brand) throws SQLException {
		String sqlStr = "{ call get_average_cost_of_bicycles_for_brand(?) }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("bicycle_brand_name_param", brand);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getBicyclesAndStockQuantity() throws SQLException {
		String sqlStr = "{ call get_bicycles_and_stock_quantity() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getSales() throws SQLException {
		String sqlStr = "{ call get_sales() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public boolean addSale(String customer, String phoneNumber, String address, String orderedBicycles, String orderedQuantities, double total, double amountPaid, double change) throws SQLException {
		String sqlStr = "{ call add_sale(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("customer_param", customer);
		cStmt.setString("phone_number_param", phoneNumber);
		cStmt.setString("address_param", address);
		cStmt.setString("ordered_bicycles_param", orderedBicycles);
		cStmt.setString("ordered_quantities_param", orderedQuantities);
		cStmt.setDouble("total_param", total);
		cStmt.setDouble("amount_paid_param", amountPaid);
		cStmt.setDouble("change_param", change);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public boolean editSale(int saleOrderNumber, String customer, String phoneNumber, String address, String orderedBicycles, String orderedQuantities, double total, double amountPaid, double change) throws SQLException {
		String sqlStr = "{ call edit_sale(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setInt("sale_order_number_param", saleOrderNumber);
		cStmt.setString("customer_param", customer);
		cStmt.setString("phone_number_param", phoneNumber);
		cStmt.setString("address_param", address);
		cStmt.setString("ordered_bicycles_param", orderedBicycles);
		cStmt.setString("ordered_quantities_param", orderedQuantities);
		cStmt.setDouble("total_param", total);
		cStmt.setDouble("amount_paid_param", amountPaid);
		cStmt.setDouble("change_param", change);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public boolean deleteSale(int saleOrderNumber) throws SQLException {
		String sqlStr = "{ call delete_sale(?, ?) }";
		int hadResults = 0;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setInt("sale_order_number_param", saleOrderNumber);
		cStmt.registerOutParameter("update_count", Types.INTEGER);
		cStmt.execute();
		hadResults = cStmt.getInt("update_count");

		if (hadResults >= 1)
			return true
		; else
			return false
		;
	}
	
	public ResultSet getNumOfCustomerTransactions(String customer) throws SQLException {
		String sqlStr = "{ call get_num_of_customer_transactions(?) }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("customer_param", customer);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getTotalAmountPaidByCustomer(String customer) throws SQLException {
		String sqlStr = "{ call get_total_amount_paid_by_customer(?) }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		cStmt.setString("customer_param", customer);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getTotalBicyclesSold() throws SQLException {
		String sqlStr = "{ call get_total_bicycles_sold() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
	
	public ResultSet getSaleOrders() throws SQLException {
		String sqlStr = "{ call get_sale_orders() }";
		boolean hadResults = false;
		
		cStmt = con.prepareCall(sqlStr);
		hadResults = cStmt.execute();
		
		if (hadResults)
			return cStmt.getResultSet()
		; else
			return null
		;
	}
}
