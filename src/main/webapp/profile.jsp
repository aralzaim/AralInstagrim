<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
        <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Profile Page</title>
</head>
<body>

        <header>
            <h1 align="center">InstaGrim ! </h1>
            <h2 align="center">Your world in Black and White</h2>
        </header>
        
        
                        
                      <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            if (lg.getlogedin()) {
                            User us;	
                            us=	lg.getUser();
                            us.fetchUserDetailsfromDB(us.getUsername());
                            
                            	
                    %>
        <div align="center">
        <article>
        <h2>Hello <%= us.getFirstname() %> !</h2>
        	<h3>Edit your profile</h3>
        	
        	<%
        		PicModel tm = new PicModel();
        		
        		Pic p= tm.getProfilePicofUser(us.getUsername());
        		if(p!=null){
        		System.out.println(p);
        		%>
        		<p align="center"><img src="/Instagrim/ProfilePicture/<%=p.getSUUID() %>"/></p>
        		
        		<%}%>
        	
        	
        	
        	<form method = "POST" enctype="multipart/form-data" action="/Instagrim/ProfPicture">
        		Change Profile Picture: <input type="file" name="profilepic"></br>
        		<input type="submit" value="Change"></br></br>
        		</form>
        	
        	<form method = "POST"  action= "Profile">
        	User Name: <input type="text" name="username" readonly="readonly" value="<%=us.getUsername()%>"></br>
        	First Name: <input type="text" name="firstname" value="<%=us.getFirstname()%>"></br>
        	Last Name: <input type="text" name="lastname" value="<%=us.getLastname()%>"></br>
        	Current E-mail(s) : 
        	  
        	  <%=us.getEmails().toString()%>
        		
        		 </br>
        		
			Want to add E-mail?: <input type="text" name="email"></br>
        	Address: <input type="text" name="address"></br>
        	<input type="submit" value="Done">
        	</form>
        	<%}}%>
        	
        </article>
        
 <footer>
          
            	<li class="nav"><a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></li>
           
                <li class="footer"><a href="/Instagrim">Home</a></li>
         
        </footer>
</div>
    	
</body>
</html>