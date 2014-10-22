<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="uk.ac.dundee.computing.aralzaim.instagrim.servlets.Comment"%>
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
    	<div id="wrapper" align="center">
        	<div id="header">
        	<header>
        	<h1>InstaGrim !</h1>
     		<h2>Your world in Black and White</h2>
        	</header>
   			</div>
           
           <%
           LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
           if (lg != null) {
           if (lg.getlogedin()){
           %>
              <a href="/Instagrim/upload.jsp">Upload</a></br>
              <a href="/Instagrim/Slideshow/<%=lg.getUser().getUsername() %>">Slide Show</a>

     	<div id="header">
            <h1>Your Pics</h1>
            </div>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        No Pictures found
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();

        %>
          <div id="content">
        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a> <br/>
         
         		<form method = "POST"  action="/Instagrim/Comment">
         		<%if(!p.getComments().isEmpty()){ 
         			Iterator<Comment> commentiterator;
       				commentiterator=p.getComments().iterator();
       				
         			while(commentiterator.hasNext()){%>
         			
         		<p><%=p.getOwner()%>: <%=commentiterator.next()%></p>
         		<%}} %>
        		<input type="text" name="comment" placeholder="Add Comment!"></br>
        		<input type="text" hidden="true" name="picid" value="<%=p.getSUUID()%>">
        		<input type="submit" value="Add"></br>
        		</form>
        		
        		
        		<a href="/Instagrim/OrginalImage/<%=p.getSUUID()%>">Orginal Image</a>
    			<a href="/Instagrim/Delete/<%=p.getSUUID()%>" onclick="return confirm('You are about to delete a picture!')">Delete Image</a> </br>
  
             	</div>
          <% }}} }
        %>
        
       
    
      <div id="footer">
        <footer>
               
          </br>
         <a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></br>
		  <a href="/Instagrim">Home</a>
            	
                
        </footer>
        </div>
      </div>
    </body>
</html>
