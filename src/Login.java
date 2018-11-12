

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
		  String password=request.getParameter("pw");
		  
		  HttpSession session=request.getSession();
		  Boolean isValid=db.check_for_admin_user(username, password);
		  
		  if(isValid) {
			  session.setAttribute("username", username);
			  response.sendRedirect("admin_home.jsp");
		  }else {
			  response.sendRedirect("login.jsp"); 
		  }		 
	}
}