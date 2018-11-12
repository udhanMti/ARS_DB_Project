

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
		  String from_port_id=request.getParameter("from_port_id");
		  String to_port_id=request.getParameter("to_port_id");
		 
		  ArrayList<PastFlight> past_flights=new ArrayList<>();

		  past_flights=db.getScheduleHistory(from_port_id, to_port_id);
		  HttpSession session=request.getSession();  
		  session.setAttribute("past_flights",past_flights);
	      response.sendRedirect("FlightScheduleHistory.jsp");
		 
	}
}