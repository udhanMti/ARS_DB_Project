import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DBoperation {
	String url = "jdbc:mysql://localhost:3307/airlinereservationsys";
    String password="";
    String username = "root";
    Connection con =null;
    PreparedStatement pst=null;
    ResultSet rs;
    
    public boolean check_for_admin_user(String uname,String pw) {
    	try {
    		System.out.println("in db operation class");
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
    
    
    public boolean checkforuser(String uname,String pw) {
    	try {
    		System.out.println("in db operation class");
			con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "select * from user where username=? and password=?";
	        pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, uname);
	        pst.setString(2, pw);
	        rs=pst.executeQuery();
	    
	        while(rs.next()) {
	        	return true;
	        }
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
        
    	return false;
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
    	System.out.println(past_flights);
    	return past_flights;
        
    }
    
    
    public boolean addUser(user em){
        System.out.println(em.getFirstname()+" "+em.getFirstname().length());
        try{
           
            con = (Connection)DriverManager.getConnection(url, username, password);
            
            String query= "INSERT INTO user values (?,?,?,?,?,?,?,?,?,?)";
            pst =(PreparedStatement)con.prepareStatement(query);
            
            pst.setInt(1,em.getUser_id());
            pst.setString(2,em.getUsername());
            pst.setString(3,em.getPassword());
            pst.setString(4,em.getFirstname());
            pst.setString(5,em.getLastname());
            pst.setString(6, em.getUser_category_id());
            pst.setString(7,em.getEmail());
            pst.setString(8,em.getPhonenumber());
            pst.setString(9,em.getAddress());
            pst.setInt(10,em.getAge());
            
            
            
            pst.executeUpdate();
            
            return true;
            
        }catch(Exception e){
            System.out.println(e);
            
            return false;
        }finally{
            try{
                if(pst != null){
                    pst.close();
                }
                if (con != null){
                    con.close();
                }
            }catch(Exception e){
            
            }
        }
    }
    
    
    int checkforuser(user em){
        try{
            con = (Connection)DriverManager.getConnection(url, username, password);
          
            String query= "select username from user";
            pst =(PreparedStatement)con.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(em.getUsername().equals(rs.getString(1))){
                     return 1;
                }
            }
            
            return 0;
            
        }catch(Exception e){
            System.out.println(e);
            
            return -1;
        }finally{
            try{
                if(pst != null){
                    pst.close();
                }
                if (con != null){
                    con.close();
                }
            }catch(Exception e){
            
            }
        }
    }
    
    
    
 ArrayList<Shedule> getShedule(){
        
        ArrayList<Shedule> list=new ArrayList<Shedule>();
        
        try{
           
            con = (Connection)DriverManager.getConnection(url, username, password);
           
            String query= "select  date,flight_id,airplane_id,b.name,c.name,departure_time,new_departure_time,schedule_id from schedule left join delay using (delay_id)  left join flight using(flight_id) left join route using (route_id),airport as b, airport as c where b.airport_code= from_port_id and c.airport_code = to_port_id";
            
            pst =(PreparedStatement)con.prepareStatement(query);
            
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                Shedule em=new Shedule();
                
                em.setDate(rs.getString(1));
                em.setAirline(rs.getString(2));
                em.setFlight(rs.getString(3));
                em.setFrom(rs.getString(4));
                em.setTo(rs.getString(5));
                em.setSheduled_time(rs.getString(6));
                em.setDelay_time(rs.getString(7));
                em.setSheduleid(rs.getString(8));
                
                
                
                list.add(em);
                
            }
            
            return list;
            
        }catch(Exception e){
            System.out.println(e);
            
            return null;
        }finally{
            try{
                if(pst != null){
                    pst.close();
                }
                if (con != null){
                    con.close();
                }
            }catch(Exception e){
            
            }
        }
        
        
    
    }
 
 
 Price getPrices(String sheduleid){
    
     try{
         Price em=new Price();
         con = (Connection)DriverManager.getConnection(url, username, password);
        
         String query= "select econ_price,business_price,platinum_price from schedule left join price using(price_id) where schedule_id="+sheduleid;
         pst =(PreparedStatement)con.prepareStatement(query);
        // pst.setString(1,ss.getSheduleid());
         
         rs = pst.executeQuery();
         
         while(rs.next()){
             
             
             em.setEcon_price(rs.getFloat(1));
             em.setBusiness_price(rs.getFloat(2));
             em.setPlatinum_price(rs.getFloat(3));
             
             
         }
         
         return em;
         
     }catch(Exception e){
         System.out.println(e);
         return null;
        
     }finally{
         try{
             if(pst != null){
                 pst.close();
             }
             if (con != null){
                 con.close();
             }
         }catch(Exception e){
         
         }
     }
 }
 
 double getDiscount(String uname) {
	 try{
         
         con = (Connection)DriverManager.getConnection(url, username, password);
        
         String query= "select discount from user left join user_category on user.user_category_id=user_category.category_id where username="+uname;
         pst =(PreparedStatement)con.prepareStatement(query);
        // pst.setString(1,ss.getSheduleid());
         
         rs = pst.executeQuery();
         
         double discount=0.5;
         
         while(rs.next()){
             discount= Double.parseDouble(rs.getString(1));
         }
         
         return discount;
         
     }catch(Exception e){
         System.out.println(e);
         return 0.5;
        
     }finally{
         try{
             if(pst != null){
                 pst.close();
             }
             if (con != null){
                 con.close();
             }
         }catch(Exception e){
         
         }
     }
	 
 }
 
 
}