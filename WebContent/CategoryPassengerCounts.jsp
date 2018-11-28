<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.HashMap" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="gray">
   <div align="center">
   <h3>Passenger count by type according to bookings from ${from} to ${to}</h3>
   <h2><% HashMap<String,String> category_counts=(HashMap<String,String>)session.getAttribute("category_counts");
   out.print("Gold  ");
   if(category_counts.containsKey("Gold")){
	   out.println(category_counts.get("Gold"));
   }else{
	   out.println(0);
   } %>
   </br>
   <% 
   out.print("Frequent  ");
   if(category_counts.containsKey("Frequent")){
	   out.println(category_counts.get("Frequent"));
   }else{
	   out.println(0);
   } %></h2>
   </div>
</body>
</html>