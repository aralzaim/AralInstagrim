<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
    </head>
    <body>
        <header>
    
        <h1 align="center">InstaGrim !</h1>
     
        <h2 align="center">Your world in Black and White</h2>
        </header>
        
       <div id="options" align="center">
             <div align="center">
                                 <%
           LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
           if (lg != null) {
           if (lg.getlogedin()){
           
        	   
        	   
           %>
                <li class="nav"><a href="/Instagrim/upload.jsp">Upload</a></li>
                <li class="nav"><a href="/Instagrim/Slideshow/<%=lg.getUser().getUsername() %>">Slide Show</a></li>
               
                
           
        
 </div>
        <article >
            <h1 align="center">Your Pics</h1>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        <p align="center">No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();

        %>
        <div id="showingpictures" align="center">
        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a> <br/>
        <a href="/Instagrim/OrginalImage/<%=p.getSUUID()%>">Orginal Image   </a>
    	<a href="/Instagrim/Delete/<%=p.getSUUID()%>" onclick="return confirm('You are about to delete a picture!')">Delete Image</a>
        </div>
       <%

            }
            }}}
        %>
        
        </article>
      
        <footer>
               
          
          <li class="nav"><a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></li>

             
                <li class="footer"><a href="/Instagrim">Home</a></li>
            	</div>
                
        </footer>
      
    </body>
</html>
