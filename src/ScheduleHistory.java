

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ScheduleHistory
 */
@WebServlet("/scheduleHistory")
public class ScheduleHistory extends HttpServlet {
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String from_port=request.getParameter("from_port");
		  String to_port=request.getParameter("to_port");
		 
		  ArrayList<Flight> past_flights=new ArrayList<>();
		 
		  past_flights=db.getScheduleHistory(from_port, to_port);
		  HttpSession session=request.getSession();  
		  session.setAttribute("from_port",from_port);
		  session.setAttribute("to_port",to_port);
		  session.setAttribute("past_flights",past_flights);
	      response.sendRedirect("FlightScheduleHistory.jsp");
		 
	}
}