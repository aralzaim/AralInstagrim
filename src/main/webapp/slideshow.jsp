<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@page import="java.util.*"%>
    <%@ page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
 <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css"/>
<title>Slide Show</title>
</head>
<body>

<h2 align="center">Slide Show with your Pictures</h2>
	
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
 	var i=0;
 	<%Iterator <Pic> iterator=lsPics.iterator();
	while(iterator.hasNext()){
		
 	%>
 	
		
		
 	
 			<%    Pic p = (Pic) iterator.next();%>
 		sImages[i] = new Image();
 		sImages[i].src="/Instagrim/Image/<%=p.getSUUID()%>";
 		console.log(i);
 		console.log(sImages[i]);
 		i++;
 		
 		<%}%>
 

 	var speed=2000

 	var countImage=0;
 	function slideit(){
 		
 		console.log(sImages[countImage]);
 		
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
         <footer>
               
          <div align="center">
          <li class="nav"><a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></li>

             
                <li class="footer"><a href="/Instagrim">Home</a></li>
            	</div>
                
        </footer>
      
</body>
</html>