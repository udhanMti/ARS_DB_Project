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
	public static String error;
	
	
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
	        if(age.length()==0) {
	        	age="200";
	        }
	        em.setAge(Integer.parseInt(age));
	        em.setUser_category_id("2");
	        error="";
	        
	        int x = db.checkforuser(em);
	        
	        if(x==0){
	            boolean result = db.addUser(em);
            	request.setAttribute("error", error);
	            if (result){
	                
	            	request.getRequestDispatcher("success.jsp").forward(request, response);
	
	                
	            }else{
	                // error
	            	request.getRequestDispatcher("register.jsp").forward(request, response);
	            }
	        }else if(x==1){
	        	request.setAttribute("error", error);
	        	request.getRequestDispatcher("register.jsp").forward(request, response); 
	        }else{
	            //error while checking
	        	response.sendRedirect("register.jsp"); 
	        }
	        
	}
}
