<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <h3>Shedule Table</h3>
  <table>
  <tr>
  	<th>Date</th>
  	<th>Airline</th>
  	<th>Flight</th>
  	<th>From</th>
  	<th>To</th>
  	<th>Shedule Time</th>
  	<th>Delay Time</th>
  </tr>
  <c:forEach items="${shedule}" var="s">
     <tr>
     <td>${s.date}</td>
     <td>${s.airline}</td> 
     <td>${s.flight}</td> 
     <td>${s.from}</td>
     <td>${s.to}</td> 
     <td>${s.sheduled_time}</td>
     <td>${s.delay_time}</td>
     </tr>
  </c:forEach>
  
  </table>
</body>
</html>
