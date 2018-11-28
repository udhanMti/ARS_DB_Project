

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AircraftRevenue
 */
@WebServlet("/total_revenue_aircraft")
public class AircraftRevenue extends HttpServlet {
	
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<AirplaneType> types_revenues=db.getTotalAircraftRevenue();
		HttpSession session=request.getSession();  
		session.setAttribute("total_revenues",types_revenues);
		response.sendRedirect("DisplayRevenue.jsp");
	}

}
