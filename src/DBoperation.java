import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBoperation {
	String url = "jdbc:mysql://localhost:3306/airlinereservationsys";
    String password="";
    String username = "root";
    
    public String check_for_user(String uname,String pw) {
    	try {
   
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			
			String query= "select * from user where username=? and password=?";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, uname);
	        pst.setString(2, pw);
	        ResultSet rs=pst.executeQuery();
	        
	        String category=null;
	       
	        while(rs.next()) {
	        	String category_id=rs.getString("user_category_id");
	        	
	        	String query_category="select * from user_category where category_id=?";
 	        	PreparedStatement pst_category =(PreparedStatement)con.prepareStatement(query_category);
 	        	pst_category.setString(1, category_id);
	        	ResultSet rs_category=pst_category.executeQuery();
	        	
	        	while(rs_category.next()) {
	        		category=rs_category.getString("category_name");
	        	}
	        	
	        	return category;
	        }
	        con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return null;
    }
    
    public ArrayList<PastFlight> getScheduleHistory(String from_port_id,String to_port_id) {
    	ArrayList<PastFlight> past_flights=new ArrayList<>();
    	
    	try {
    		   
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "Select schedule_id,date,flight_id,booked_seats_business+booked_seats_platinum+booked_seats_econ from schedule where date<=Date(Now()) and flight_id in (Select flight_id from flight inner join route using(route_id) where from_port_id=? and to_port_id=?)";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, from_port_id);
	        pst.setString(2, to_port_id);
	        ResultSet rs=pst.executeQuery();
	        
	     
	        while(rs.next()) {
	        	 PastFlight pf=new PastFlight();
	        	
	        	 pf.setDate(rs.getString(2));
                 pf.setFlight_id(rs.getString(3));
                 pf.setSchedule_id(rs.getString(1));
                 pf.setPassenger_count(rs.getString(4));
                 past_flights.add(pf);
	        }
	        con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return past_flights;
        
    }
}