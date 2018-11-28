import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/booking")

public class Booking extends HttpServlet{
		DBoperation db= new DBoperation();
		
		public static String error;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String seat_no=request.getParameter("seat_no");
		  

		  HttpSession session=request.getSession();
		  
		  String schedule_id=session.getAttribute("schedule_id").toString();
		  
		  
		  String username=session.getAttribute("username").toString();
		  
		  boolean isinsert=false;
		  
		  try {
			//System.out.println("kkk");
				
			isinsert= db.bookSeat(schedule_id, seat_no, username);
			//System.out.println(isinsert);
				
		  } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }finally {
				if(isinsert) {
					response.sendRedirect("successbooking.jsp");
				}else {
					request.setAttribute("error", error);
		        	request.getRequestDispatcher("booking.jsp").forward(request, response);
				}
		  }
		  
		  
		  
		  
		  
		  
		  
	}
}
