<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Flight Price</title>
</head>
<body>
	<div class="page-header">
    <h2>AirLine System</h2>      
    </div>
    
    
    <h5>Flight Prices</h5>
    
    Econ_Price : ${p.getEcon_price()} <br>
    Business_Price :${p.getBusiness_price() }<br>
    Platinum_Price :${p.getPlatinum_price()}<br><br><br>
    
    Your Discount : ${discount}<br>

</body>
</html>