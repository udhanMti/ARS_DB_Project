

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PassengerTypeCount
 */
@WebServlet("/passenger_type_count")
public class PassengerTypeCount extends HttpServlet {
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String from_date=request.getParameter("from_date_2");
		String to_date=request.getParameter("to_date_2");
		
		SimpleDateFormat fromDate=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat toDate=new SimpleDateFormat("yyyy-MM-dd");
		
		Date fromD=null;
		Date toD=null;
		try {
			fromD=toDate.parse(from_date);
		    toD=toDate.parse(to_date);
		    if(fromD.before(toD)) {
			    HashMap<String,String> category_counts=db.getPassengerTypeCount(fromD,toD);
			   
			    HttpSession session=request.getSession();  
			    session.setAttribute("from", from_date);
			    session.setAttribute("to", to_date);
				session.setAttribute("category_counts",category_counts);
			    response.sendRedirect("CategoryPassengerCounts.jsp");
		    }else {
		    	response.sendRedirect("admin_home.jsp");
		    }
		} catch (ParseException e) {
			//TODO Auto-generated catch block
		 	e.printStackTrace();
		}
	}

}
