import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDBoperation {
	String url = "jdbc:mysql://localhost:3307/airlinereservationsys";
    String password="";
    String username = "root";
    Connection con =null;
    PreparedStatement pst=null;
    ResultSet rs;
    
    public boolean checkforuserlogin(String uname,String pw) {
    	try {
    		//System.out.println("in db operation class");
			con = (Connection)DriverManager.getConnection(url, username, password);
			String query= "select * from user where username=? and password=? and (user_category_id=? or user_category_id=?)";
	        pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1, uname);
	        pst.setString(2, pw);
	        pst.setString(3, "1");
	        pst.setString(4, "2");
	        rs=pst.executeQuery();
	    
	        while(rs.next()) {
	        	
	        	return true;
	        }
	        
	        Login.error="Invalid username or password!";
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			Login.error="Error occured!";
			e.printStackTrace();
		}
        
    	return false;
    }
    
    
    
    public boolean addUser(user em){
        //System.out.println(em.getFirstname()+" "+em.getFirstname().length());
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
            
            Register.error="";
            
            return true;
            
        }catch(Exception e){
            System.out.println(e);
            
            Register.error=e.getMessage().substring(33,e.getMessage().length()-16);
            System.out.println(Register.error);
            
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
    
    
    
    ArrayList<Shedule> getShedule(){
        
        ArrayList<Shedule> list=new ArrayList<Shedule>();
        
        try{
           
            con = (Connection)DriverManager.getConnection(url, username, password);
           
            String query= "select  date,flight_id,airplane_id,b.name,c.name,departure_time,new_departure_time,schedule_id from schedule left join delay using (delay_id)  left join flight using(flight_id) left join route using (route_id),airport as b, airport as c where b.airport_code= from_port_id and c.airport_code = to_port_id and date>=date(now())";
            
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

	

    double getDiscount(String uname) {
	 try{
        
        con = (Connection)DriverManager.getConnection(url, username, password);
       
        String query= "select discount from user left join user_category on user.user_category_id=user_category.category_id where username=?";
        pst =(PreparedStatement)con.prepareStatement(query);
        pst.setString(1,uname);
        
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


    public ArrayList<Seat> getavailableseat(String sheduleid) {
		 ArrayList<Seat> list=new ArrayList<Seat>();
	    
	    try{
	       
	        con = (Connection)DriverManager.getConnection(url, username, password);
	        
	        //System.out.println(sheduleid);
	        String query= "select seat_no,type from (schedule  inner join airplane using(airplane_id)) inner join plane_seats using(airplane_id) where schedule_id=? and seat_no not in (select seat_no from booking where schedule_id=?)";
	        
	        pst =(PreparedStatement)con.prepareStatement(query);
	        pst.setString(1,sheduleid);
	        pst.setString(2,sheduleid);
	        
	        rs = pst.executeQuery();
	        
	        while(rs.next()){
	            
	            Seat em = new Seat();
	            em.setName(rs.getString(1));
	            em.setType(rs.getString(2));
	            
	           // System.out.println(11111);
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


	public boolean bookSeat(String schedule_id,String seat_no,String uname) throws SQLException {
		 try{
	        
	        con = (Connection)DriverManager.getConnection(url, username, password);
	        
	        con.setAutoCommit(false);
	        
	        String selectquery="select user_id from user where username=?";
	        
	        PreparedStatement pstSelect =(PreparedStatement)con.prepareStatement(selectquery);
	        pstSelect.setString(1,uname);
	        
	        rs = pstSelect.executeQuery();
	        
	        int user_id=0;
	        
	        while(rs.next()){
	            
	            user_id=Integer.parseInt(rs.getString(1));
	            
	        }
	        //booked_seats_business=booked_seats_business+1,booked_seats_platinum=booked_seats_platinum+1
	        
	        
	        
	        String insertquery= "INSERT INTO booking (user_id,schedule_id,payment_status,seat_no) values (?,?,?,?)";
	        
	        
	        PreparedStatement pstInsert =(PreparedStatement)con.prepareStatement(insertquery);
	        
	        
	        
	        
	        pstInsert.setInt(1,user_id);
	        pstInsert.setString(2,schedule_id);
	        pstInsert.setBoolean(3,false);
	        pstInsert.setInt(4,Integer.parseInt(seat_no));
	        
	        
	        
	        pstInsert.executeUpdate();
	        
	        
	        String query1= "select type from schedule inner join plane_seats using(airplane_id) where schedule_id=? and seat_no=?";
	        PreparedStatement psttype =(PreparedStatement)con.prepareStatement(query1);
	        psttype.setString(1, schedule_id);
	        
	        rs = pstSelect.executeQuery();
	        
	        String type="";
	        
	        while(rs.next()){
	            
	            type=rs.getString(1);
	            
	        }
	        
	        if(type.equals("Economy")) {
	       	 String updatequery="update schedule set booked_seats_econ=booked_seats_econ+1  where schedule_id=?";
	            PreparedStatement pstUpdate =(PreparedStatement)con.prepareStatement(updatequery);
	            
	            
	            pstUpdate.setString(1, schedule_id);
	            pstUpdate.executeUpdate();
	        }else if(type.equals("Platinum")) {
	       	 
	       	 String updatequery="update schedule set booked_seats_platinum=booked_seats_platinum+1  where schedule_id=?";
	            PreparedStatement pstUpdate =(PreparedStatement)con.prepareStatement(updatequery);
	            
	            
	            pstUpdate.setString(1, schedule_id);
	            pstUpdate.executeUpdate();
	        }else {
	       	 
	       	 String updatequery="update schedule set booked_seats_business=booked_seats_business+1  where schedule_id=?";
	            PreparedStatement pstUpdate =(PreparedStatement)con.prepareStatement(updatequery);
	            
	            
	            pstUpdate.setString(1, schedule_id);
	            pstUpdate.executeUpdate();
	        }
	        
	        
	        
	       
	        
	        
	        
	        con.commit();
	        
	        
	        
	        
	        
	        return true;
	        
	    }catch(Exception e){
	        System.out.println(e);
	        Booking.error=e.getMessage();
	        
	        con.rollback();
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
                	 Register.error="username already exists!!!";
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
 
 

    
}
