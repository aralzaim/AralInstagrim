<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
     <div id="wrapper" align="center">
     	<div id="header">
        <header>
        <h1>InstaGrim ! </h1>
        <h2>Your world in Black and White</h2>
        <h3>Login</h3>
        </header>
        </div>
        
        <div id="content">
        	<form method="POST"  action="Login">
              
                    <input type="text" name="username" placeholder="Username"></br>
                  
                    <input type="password" name="password" placeholder="Password"></br>
                    <%if(request.getAttribute("errorMessage")!=null){
                   %>
                   <%=request.getAttribute("errorMessage")  %>
                   <%} %>
              
                <input type="submit" value="Login"> 
            </form>

       </div>
        <div id="footer">
        <footer>
           
              <a href="/Instagrim">Home</a></li>
          
        </footer>
        </div>
        </div>
    </body>
</html>
