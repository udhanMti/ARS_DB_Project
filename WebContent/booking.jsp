<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Airline System</title>


<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


</head>
<body>

<h3>Booking Table</h3>
  <form action="booking" method="post">
  
  <c:if test = "${error.length()>0}">
		  	<div class="alert alert-danger">
         	 <c:out value = "${error}"/><p>
         	 </div>
      	  </c:if>
        
  
  <table class="table table-bordered" >
  <tr>
  	<th>Seat No</th>
  	<th>Type</th>
  </tr>
  <c:forEach items="${seat}" var="s">
     <tr>
     <td>${s.getName()}</td>
     <td>${s.getType()}</td> 
     
     <td align="center">  
        <input type="radio" name="seat_no"   value="${s.getName()}"/>  
     </td>
     </tr>
  </c:forEach>
  
  </table>
  <input type="submit" class="btn btn-default" name="button1" value="Book" />
  </form>

</body>
</html>