<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%@page import="java.util.*"%>
<%@ page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Alter your Images</title>
</head>
<body>
<div align="center">
<h2>Alter your pictures :) !</h2>

<% Pic p = (Pic)request.getAttribute("Pic");

%>


  <p><img src="/Instagrim/Thumb/<%=p.getSUUID()%>" width="300" height="400"> </p><br/>
  <form action="" method="post">
   <input type="submit" name="original" value="Original Picture" /></br>
    <input type="submit" name="filter1 " value="Filter 1" />
    <input type="submit" name="filter2" value="Filter 2" />
    <input type="submit" name="filter3" value="Filter 3" />
</form>
  </div>

</body>
</html>