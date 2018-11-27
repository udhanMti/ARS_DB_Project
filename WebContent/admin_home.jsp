<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	
	<div class="page-header">
    <h2>AirLine System</h2>      
    </div>
    
    
    <h5>Admin home</h5>
	
   <form action="scheduleHistory" method="post" class="form-horizontal">
	   <div class="form-group">
	    <label class="control-label col-sm-2" for="fai">From Airport Id :</label>
	    <div class="col-sm-10">
	      <input type="text" class="form-control" id="fai" name="from_port_id" >
	    </div>
	  </div>
	  
	  <div class="form-group">
	    <label class="control-label col-sm-2" for="pwd">To Airport Id :</label>
	    <div class="col-sm-10"> 
	      <input type="text" class="form-control" id="pwd" name="to_port_id">
	    </div>
	  </div>
   
   <div class="form-group"> 
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">Check</button>
    </div>
  </div>
  
   	
   </form>
</body>
</html>