

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
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String username=request.getParameter("username");
		  String password=request.getParameter("password");
		  
		  HttpSession session=request.getSession();
		  
		  String category=db.check_for_user(username, password);
		
		  if(category==null) {
			  response.sendRedirect("login.jsp"); 
			  
		  }else{ 
			  session.setAttribute("username", username);
			  session.setAttribute("category", category);
			  if(category.equals("Admin")){	  
			      response.sendRedirect("admin_home.jsp");
		      }else {
		    	  response.sendRedirect("user_home.jsp");
		      }		   
		  }		 
	}
}