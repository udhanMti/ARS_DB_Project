import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
    
    public ArrayList<Flight> getScheduleHistory(String from_port,String to_port) {
    	ArrayList<Flight> past_flights=new ArrayList<>();
    	
    	try {
    		   
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			String query_from="select airport_code from airport where name=?";
			String query_to="select airport_code from airport where name=?";
			
			PreparedStatement pst_from =(PreparedStatement)con.prepareStatement(query_from);
			PreparedStatement pst_to =(PreparedStatement)con.prepareStatement(query_to);
			
			pst_from.setString(1, from_port);
	        pst_to.setString(1, to_port);
			
	        ResultSet rs_from=pst_from.executeQuery();
	        ResultSet rs_to=pst_to.executeQuery();
	        
	        String from_port_id=null;
	        String to_port_id=null;
	        while(rs_from.next()) { from_port_id=rs_from.getString(1);}
	        while(rs_to.next()) {to_port_id=rs_to.getString(1);}
	        
	    //    if(!(from_port_id.equals(null) || to_port_id.equals(null))) {
				String query= "Select schedule_id,date,flight_id,booked_seats_business+booked_seats_platinum+booked_seats_econ,airplane_id from schedule where date<=Date(Now()) and flight_id in (Select flight_id from flight inner join route using(route_id) where from_port_id=? and to_port_id=?)";
		        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
		        pst.setString(1, from_port_id);
		        pst.setString(2, to_port_id);
		        ResultSet rs=pst.executeQuery();
		        
		     
		        while(rs.next()) {
		        	 Flight pf=new Flight();
		        	
		        	 pf.setDate(rs.getString(2));
	                 pf.setFlight_id(rs.getString(3));
	                 pf.setSchedule_id(rs.getString(1));
	                 pf.setPassenger_count(rs.getString(4));
	                 pf.setAirplane_id(rs.getString(5));
	                 past_flights.add(pf);
		        }
	      //  }else {
	        	
	      //  }    
	        con.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return past_flights;
        
    }
    
    public ArrayList<ArrayList<Passenger>> getNextFlightInfo(String flight_id) {
    	ArrayList<ArrayList<Passenger>> returnArray=new ArrayList<>();
    	ArrayList<Passenger> passenger_list_above18=new ArrayList<>();
    	ArrayList<Passenger> passenger_list_below18=new ArrayList<>();
    	try {
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "select schedule_id from schedule where flight_id =? and date>=Date(Now()) order by date asc limit 1";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, flight_id);
	       
	        ResultSet rs=pst.executeQuery();
	        
	        while(rs.next()) {
	        	String schedule_id=rs.getString(1);
	        	String query_2="select * from user where user_id in (select user_id from booking where schedule_id=?)";
	        	PreparedStatement pst_2 =(PreparedStatement)con.prepareStatement(query_2);
	        	pst_2.setString(1, schedule_id);
	        	
	        	ResultSet rs_2=pst_2.executeQuery();
	        	
	        	while(rs_2.next()) {
	        		Passenger passenger=new Passenger();
	        		passenger.setName(rs_2.getString(4)+" "+rs_2.getString(5));
	                passenger.setEmail(rs_2.getString(7));
	                passenger.setAddress(rs_2.getString(9));
	                passenger.setAge(rs_2.getString(10));
	                if(new Integer(rs_2.getString(10))>=18) {
	                	 passenger_list_above18.add(passenger);
	                }else {
	                	passenger_list_below18.add(passenger);	
	                }
	               
	        	}
	        }
	        con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	returnArray.add(passenger_list_above18);returnArray.add(passenger_list_below18);
    	return returnArray;
    }
    
    public String getDestinationPassengerCount(String destination,Date from_date,Date to_date) {
    	try {
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			
			String query_destination="select airport_code from airport where name=?";
			PreparedStatement pst_destination =(PreparedStatement)con.prepareStatement(query_destination);
			pst_destination.setString(1, destination);
			ResultSet rs_destination=pst_destination.executeQuery();
			
			String destination_id=null;
			while(rs_destination.next()) { destination_id=rs_destination.getString(1);}
			
			String query= "select sum(booked_seats_econ)+sum(booked_seats_business)+sum(booked_seats_platinum) from schedule where date>=? and date<=? and flight_id in (select flight_id from flight where route_id in (select route_id from route where to_port_id=?))";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
	        
	        Timestamp date1=new Timestamp(from_date.getTime());
	        Timestamp date2=new Timestamp(to_date.getTime());
	        pst.setTimestamp(1, date1);
	        pst.setTimestamp(2, date2);
	        
	        pst.setString(3, destination_id);
	        
	        ResultSet rs=pst.executeQuery();
	        while(rs.next()) {
	        	
                return rs.getString(1);
	        }
	        con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    public HashMap<String,String> getPassengerTypeCount(Date from_date,Date to_date) {
    	HashMap<String,String> category_counts=new HashMap<>();
    	try {
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "select category_name,count(user_category_id) from booking inner join (select * from user inner join user_category on user.user_category_id=user_category.category_id) c on booking.user_id=c.user_id where schedule_id in (select schedule_id from schedule where date>=? and date<=?) group by user_category_id";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
	        
	        Timestamp date1=new Timestamp(from_date.getTime());
	        Timestamp date2=new Timestamp(to_date.getTime());
	        pst.setTimestamp(1, date1);
	        pst.setTimestamp(2, date2);
	        
	        ResultSet rs=pst.executeQuery();
	        while(rs.next()) {
	        	category_counts.put(rs.getString(1),rs.getString(2));
	        }
	        con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return category_counts;
    }
    
    public String getTotalAircraftRevenue() {
    	
		try {
			Connection con = (Connection)DriverManager.getConnection(url, username, password);
			String query="select * from airplane group by (type_id)";
	        PreparedStatement pst =(PreparedStatement)con.prepareStatement(query);
			
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
    	return "0";
    }
}