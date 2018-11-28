

import java.io.IOException;
import java.util.ArrayList;

import java.io.Console;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	CustomerDBoperation db= new CustomerDBoperation();
	DBoperation db1=new DBoperation();
	
	public static String error;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String username=request.getParameter("username");
		  String password=request.getParameter("pw");
		  
		  HttpSession session=request.getSession();
		  
		  
		  error="";
		  
		  if (request.getParameter("button1") != null) {
			  Boolean isValid=db.checkforuserlogin(username, password);
			  
			  if(isValid) {
				  session.setAttribute("username", username);
				  //session.setAttribute("usertype", "customer");
				  
				  ArrayList<Shedule> list= db.getShedule();
				  session.setAttribute("shedule",list);
				  Login.error="";
				  response.sendRedirect("shedule.jsp" );
				  
				  
				  
			  }else {
				  request.setAttribute("error", error);
		          request.getRequestDispatcher("login.jsp").forward(request, response); 
			  }	
			  
		  }else {
			  Boolean isValid=db1.checkforadminlogin(username, password);
			  
			  if(isValid) {
				  session.setAttribute("username", username);
				  //session.setAttribute("usertype", "admin");
				  
				  
				  Login.error="";
				  
				  
				  ArrayList<String> port_list= db1.getPortList();
				  ArrayList<String> flight_list= db1.getFlightList();
				  session.setAttribute("port_list",port_list);
				  session.setAttribute("flight_list",flight_list);
				  response.sendRedirect("admin_home.jsp" );
				  
				  
				  
			  }else {
				  request.setAttribute("error", error);
		          request.getRequestDispatcher("login.jsp").forward(request, response); 
			  }	
			  
		  }
		  
		  	 
	}
}
