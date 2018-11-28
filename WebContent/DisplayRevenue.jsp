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

<title>Insert title here</title>
</head>
<body>
<div align="center">

  </div>
  <table class="table table-bordered">
     <tr style="background:yellow">
     <th>Type  </th>
     <th>Total Revenue  </th>
     </tr>
  <c:forEach items="${total_revenues}" var="s">
     <tr style="background:pink">
     <td>${s.type_id}</td>
     <td>${s.total_revenue}</td> 
     </tr>
  </c:forEach>
  </table>
</body>
</html>