

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FlightPassengerInfo
 */
@WebServlet("/flight_passenger_info")
public class FlightPassengerInfo extends HttpServlet {
	DBoperation db= new DBoperation();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String flight_id=request.getParameter("flight_id");
		ArrayList<ArrayList<Passenger>> passenger_list=db.getNextFlightInfo(flight_id);
		//System.out.println(passenger_list.size());
		HttpSession session=request.getSession(); 
		session.setAttribute("flight_id", flight_id);
		session.setAttribute("passenger_list_above18",passenger_list.get(0));
		session.setAttribute("passenger_list_below18",passenger_list.get(1));
	    response.sendRedirect("NextFlightInfo.jsp");
	}

}
