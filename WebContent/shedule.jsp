<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">



<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Airline System</title>
</head>
<body>
  <h3>Shedule Table</h3>
  <form action="price" method="post">
  <table class="table table-bordered" >
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
     <td align="center">  
        <input type="radio" name="checkboxgroup"   value="${s.sheduleid}"/>  
     </td>
     </tr>
  </c:forEach>
  
  </table>
  <input type="submit" class="btn btn-default" name="button1" value="View Price" />
  <input type="submit" class="btn btn-default" name="button2" value="Book Seat" />
  </form>
</body>
</html>
