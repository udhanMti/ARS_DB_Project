

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AircraftRevenue
 */
@WebServlet("/total_revenue_aircraft")
public class AircraftRevenue extends HttpServlet {
	
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String total_revenue=db.getTotalAircraftRevenue();
	}

}
