<%@page import="java.security.acl.Owner"%>
<%@page import="uk.ac.dundee.computing.aralzaim.instagrim.models.CommentModel"%>
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
           String profileowner=(String)request.getAttribute("ProfileOwner");
           LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
           if (lg != null) {
           if (lg.getlogedin()){
           %>
           
              
              <a href="/Instagrim/Slideshow/<%=profileowner%>">Slide Show</a>
	 <div id="header">
        <%if(profileowner.equals(lg.getUser().getUsername())){
            %>
            <a href="/Instagrim/upload.jsp">Upload</a></br>
        	<h1>Your Profile</h1>
       
        <%}else{  %>
            <h1><%=profileowner %>'s Pics</h1>
 
            <%} %>
            </div> 
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
        	
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
        	
           	
        	
        	CommentModel cm=new CommentModel();
            Iterator<Pic> iterator;
            Iterator<CommentModel> setiterator;
            CommentModel c;
            iterator = lsPics.iterator();
            String comment;
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();
		
       %>
          <div id="content">
        <a href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a> <br/>
         
         		<form method = "POST"  action="/Instagrim/Comment">
         		<%if(!cm.getComments(p.getSUUID()).isEmpty()){ 
         			setiterator=cm.getComments(p.getSUUID()).iterator();
         			while(setiterator.hasNext()){
         				c=setiterator.next();
       				comment=c.getUser()+": "+c.getComment()+" "+c.getCurrentdate();
       				%>
       				
       				<p><%=comment%></p>
       			<%}}%>
        		<input type="text" name="comment" placeholder="Add Comment!"></br>
        		<input type="text" name="owner" hidden="true" value="<%=p.getOwner()%>">
        		<input type="text" hidden="true" name="picid" value="<%=p.getSUUID()%>">
        		<input type="submit" value="Add"></br>
        		</form>
        		
        	
        		<a href="/Instagrim/OrginalImage/<%=p.getSUUID()%>">Orginal Image</a>
        			<%if(p.getOwner().equals(lg.getUser().getUsername())) {%>
    			<a href="/Instagrim/Delete/<%=p.getSUUID()%>" onclick="return confirm('You are about to delete a picture!')">Delete Image</a> </br>
  <%} %>
             	</div>
          <%}}}} %>
        
       
    
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
