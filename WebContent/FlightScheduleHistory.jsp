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
  <h3>Flights from ${from_port} to ${to_port}</h3>
  </div>
  <table align="center">
     <tr style="background:yellow">
     <th>Schedule Id  </th><th>Departure Date  </th><th>Flight_Id  </th>
     <th>Airplane Id  </th><th>Passenger Count  </th>
     </tr>
  <c:forEach items="${past_flights}" var="s">
     <tr style="background:pink">
     <td>${s.schedule_id}</td>
     <td>${s.date}</td> 
     <td>${s.flight_id}</td> 
     <td>${s.airplane_id}</td>
     <td>${s.passenger_count}</td>
     </tr>
  </c:forEach>
  </table>
</body>
</html>