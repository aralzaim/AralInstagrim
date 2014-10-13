<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@page import="java.util.*"%>
    <%@ page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Slide Show</title>
</head>
<body>

<%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
            	
        %> 
<p align="center">
<%
        } else {
       %>
	<p align="center"><img src="" width="700" height="400" name="slide" /></p>
 	<script>
 	var sImages=new Array()
 	
 	for(var i=0;i<<%=lsPics.size()%>;i++){
 	sImages[i] = new Image();
 	
 	sImages[i].src = "/Instagrim/Thumb/<%=lsPics.iterator().next().getSUUID()%>";
 	}
 	
 	var speed=2000

 	var countImage=0;
 	function slideit(){
 		
 		console.log(sImages);
 		
 	    document.getElementsByName("slide")[0].src=sImages[countImage].src;
 	    if (countImage<sImages.length-1)
 	        countImage++;
 	    else
 	        countImage=0;
 	    
 	    setTimeout("slideit()",speed);
 	}
 	slideit();

 	</script>
 
 		
  
       <% } %>
 </p>
</body>
</html>