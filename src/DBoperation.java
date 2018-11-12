import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBoperation {
	String url = "jdbc:mysql://localhost:3306/airlinereservationsys";
    String password="";
    String username = "root";
    Connection con =null;
    PreparedStatement pst=null;
    ResultSet rs;
    
    public boolean check_for_admin_user(String uname,String pw) {
    	try {
   
			con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "select * from admin where username=? and password=?";
	        pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, uname);
	        pst.setString(2, pw);
	        rs=pst.executeQuery();
	    
	        while(rs.next()) {
	        	return true;
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	return false;
    }
    
    public ArrayList<PastFlight> getScheduleHistory(String from_port_id,String to_port_id) {
    	ArrayList<PastFlight> past_flights=new ArrayList<>();
    	
    	try {
    		   
			con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "Select schedule_id,date,flight_id,booked_seats_business+booked_seats_platinum+booked_seats_econ from schedule where date<=Date(Now()) and flight_id in (Select flight_id from flight inner join route using(route_id) where from_port_id=? and to_port_id=?)";
	        pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, from_port_id);
	        pst.setString(2, to_port_id);
	        rs=pst.executeQuery();
	        
	     
	        while(rs.next()) {
	        	 PastFlight pf=new PastFlight();
	        	
	        	 pf.setDate(rs.getString(2));
                 pf.setFlight_id(rs.getString(3));
                 pf.setSchedule_id(rs.getString(1));
                 pf.setPassenger_count(rs.getString(4));
                 past_flights.add(pf);
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(past_flights);
    	return past_flights;
        
    }
}