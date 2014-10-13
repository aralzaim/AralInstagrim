

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
        <header>
            <h1 align="center">InstaGrim ! </h1>
            <h2 align="center">Your world in Black and White</h2>
        </header>
       

               <div align="center">
               <li><a href="upload.jsp">Upload</a></li>
                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUser().getUsername();
                            if (lg.getlogedin()) {
                    %>

                <li><a href="/Instagrim/Images/<%=lg.getUser().getUsername()%>">Your Images</a></li>
                 <li class= "nav"><a href="/Instagrim/Profile/<%=lg.getUser().getUsername()%>">Your Profile</a></li>
                    <%}
                        }else{
                                %>
                 <li><a href="register.jsp">Register</a></li>
                <li><a href="login.jsp">Login</a></li>
                <%}%>
           </div>
           

           
          
          
           
        <footer>
            <div align="center"> <li class="footer"><a href="/Instagrim">Home</a></li>
                       <%
           if (lg != null) {
           if (lg.getlogedin()){
           
           %>
            
             <li class="nav"><a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></li>
           
             <%}} %>
                <li >ARAL ZAIM</li>
           </div>
               
        </footer>
    </body>
</html>
