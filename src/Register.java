import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")

public class Register  extends HttpServlet{
	DBoperation db= new DBoperation();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String fname=request.getParameter("fname");
		  String lname=request.getParameter("lname");
		  String email=request.getParameter("email");
		  String phonenumber=request.getParameter("phonenumber");
		  String address=request.getParameter("address");
		  String age=request.getParameter("age");
		  String username=request.getParameter("username");
		  String password=request.getParameter("pw");
		  
		  user  em=new user();
	       
	        em.setUser_id(0);
	        em.setUsername(username);
	        em.setPassword(password);
	        em.setFirstname(fname);
	        em.setLastname(lname);
	        em.setEmail(email);
	        em.setAddress(address);
	        em.setPhonenumber(phonenumber);
	        em.setAge(Integer.parseInt(age));
	        em.setUser_category_id("2");
	        
	        int x = db.checkforuser(em);
	        
	        if(x==0){
	            boolean result = db.addUser(em);
	            if (result){
	                //success
	            	response.sendRedirect("login.jsp"); 
	
	                
	            }else{
	                // error
	            	response.sendRedirect("register.jsp"); 
	            }
	        }else if(x==1){
	            //user already exists
	        	response.sendRedirect("register.jsp"); 
	        }else{
	            //error while checking
	        	response.sendRedirect("register.jsp"); 
	        }
	        
	}
}
