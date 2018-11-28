import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DestinationPassengerInfo
 */
@WebServlet("/destination_passenger_info")
public class DestinationPassengerInfo extends HttpServlet {
	DBoperation db= new DBoperation();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String destination=request.getParameter("destination");
		String from_date=request.getParameter("from_date");
		String to_date=request.getParameter("to_date");
		
		SimpleDateFormat fromDate=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat toDate=new SimpleDateFormat("yyyy-MM-dd");
		
		Date fromD=null;
		Date toD=null;
		try {
			fromD=toDate.parse(from_date);
		    toD=toDate.parse(to_date);
		    if(fromD.before(toD)) {
		    	String count=db.getDestinationPassengerCount(destination,fromD,toD);
		    	HttpSession session=request.getSession();  
				session.setAttribute("passenger_count",count);
				session.setAttribute("destination", destination);
				session.setAttribute("from",from_date);
				session.setAttribute("to",to_date);
			    response.sendRedirect("DestinationPassengerCount.jsp");
		    }else {
		    	//System.out.println("Wrong");
		    	response.sendRedirect("admin_home.jsp");
		    }  
		} catch (Exception e) {
			//TODO Auto-generated catch block
		 	e.printStackTrace();
		}
		
	    
	    //System.out.println(chosenDate);
	}

}
