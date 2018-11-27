import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/price")

public class FlightPrice extends HttpServlet {
	
	DBoperation db= new DBoperation();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String checkboxgroup=request.getParameter("checkboxgroup");
		  
		  if (request.getParameter("button1") != null) {
			  Price p = db.getPrices(checkboxgroup);
			  
			  HttpSession session=request.getSession();
			  String uname= session.getAttribute("username").toString();
			  
			  double discount=db.getDiscount(uname);
			  
			  request.setAttribute("p", p);
			  request.setAttribute("discount", discount);
			  request.getRequestDispatcher("price.jsp").forward(request, response);
	      }else {
	    	  
	      }
		  
		  
		  
		  
		  
	}
		 
	
}
