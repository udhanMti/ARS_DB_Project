<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


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

<title>Register</title>
</head>
<body>

	<div class="page-header">
    <h2>AirLine System</h2>      
    </div>
    
    
    <h5>Register</h5>
    
	<form action="register" method="post">
		
		  <c:if test = "${error.length()>0}">
		  	<div class="alert alert-danger">
         	 <c:out value = "${error}"/><p>
         	 </div>
      	  </c:if>
        
        <div class="form-group">
	    	<label for="fname" >First name :</label>
	    	<input type="text" class="form-control" id="fname" name="fname">
	    </div>
	    
	    <div class="form-group">
	    	<label for="lname" >Last name :</label>
	    	<input type="text" class="form-control" id="lname" name="lname">
	    </div>
	    
	    <div class="form-group">
	    	<label for="email" >Email :</label>
	    	<input type="text" class="form-control" id="email" name="email">
	    </div>
	    
	    <div class="form-group">
	    	<label for="phonenumber" >Phone number :</label>
	    	<input type="text" class="form-control" id="phonenumber" name="phonenumber">
	    </div>
	    
	    <div class="form-group">
	    	<label for="address" >Address :</label>
	    	<input type="text" class="form-control" id="address" name="address">
	    </div>
	    
	    <div class="form-group">
	    	<label for="age" >Age :</label>
	    	<input type="text" class="form-control" id="age" name="age">
	    </div>
	    
	    <div class="form-group">
	    	<label for="email" >Username:</label>
	    	<input type="text" class="form-control" id="email" name="username">
	    </div>
	    
		  <div class="form-group">
		    <label for="pwd">Password:</label>
		    <input type="password" class="form-control" id="pwd" name="pw">
		  </div>
		  
		  
		  
      	  
		  
		  
	  	<button type="submit" class="btn btn-default">Register</button>
	    
	    
        
    </form>
    
    
</body>
</html>