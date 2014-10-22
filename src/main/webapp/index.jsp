

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>  
       <div id="wrapper" align="center">
       
       	<div id="header"> 
       		<header> 
            <h1 align="center">InstaGrim ! </h1>
            <h2 align="center">Your world in Black and White</h2>
        	</header>  
       </div>

            
                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUser().getUsername();
                            if (lg.getlogedin()) {
                    %>
                 <div id="content">
	     	     <a href="/Instagrim">Home</a>
		    	 <a href="upload.jsp">Upload</a>
                 <a href="/Instagrim/Images/<%=lg.getUser().getUsername()%>">Your Images</a>
                 <a href="/Instagrim/Profile/<%=lg.getUser().getUsername()%>">Your Profile</a>
                    <%}
                        }else{
                                %>
               <a href="register.jsp">Register</a>
                <a href="login.jsp">Login</a>
                <%}%>
           	</div>
           
			<div id="footer" align="center"> 
        		<footer>
           
                       <%
           				if (lg != null) {
           				if (lg.getlogedin()){
           				%>
             <a href="/Instagrim">Home</a></br>
             <a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></br>
             <%}} %>
              &copy;Aral
         		</footer>
          </div>
        
        </div>
    </body>
</html>
