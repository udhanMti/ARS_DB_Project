<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body bgcolor="gray">
<div align="center">
  <h3>Passenger of next ${flight_id} flight</h3>
  <h4>Passengers Above 18</h4>
  </div>

<table align="center">
  
  <tr style="background:yellow">
  <th>Passenger Name</th>
  <th>E mail</th>
  <th>Address</th>
  <th>Age</th>
  </tr> 
  <c:forEach items="${passenger_list_above18}" var="s">
     <tr style="background:orange">
     <td>${s.name}</td>
     <td>${s.email}</td> 
     <td>${s.address}</td> 
     <td>${s.age}</td> 
     </tr>
  </c:forEach>
  </table>
 </br></br>
 <div align="center"><h4>Passengers Below 18</h4>
  </div>
 <table align="center">
  <tr style="background:yellow">
  <th>Passenger Name</th>
  <th>E mail</th>
  <th>Address</th>
  <th>Age</th>
  </tr> 
  <c:forEach items="${passenger_list_below18}" var="s">
     <tr style="background:orange">
     <td>${s.name}</td>
     <td>${s.email}</td> 
     <td>${s.address}</td> 
     <td>${s.age}</td> 
     </tr>
  </c:forEach>
  </table>
</body>
</html>