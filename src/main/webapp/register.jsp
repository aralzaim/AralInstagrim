<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aralstagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
    	<div id="wrapper" align="center">
        <div id="header">
        <header>
        <h1>Aralstagrim ! </h1>
        <h2>Your world in Black and White</h2>
        </header>
        </div>

       
     	<div id="content">
            <h3>Register as user</h3>
            <form method="POST"  action="Register">
                <ul>
                 <input type="text" name="username" placeholder="Username"></br>
                 <input type="password" name="password" placeholder="Password"></br>
                 <input type="text" name="firstname" placeholder="First Name"></br>
                 <input type="text" name="lastname" placeholder="Last Name"></br>
                 <input type="text" name="address" placeholder="Address"></br>
                 <input type="text" name="email" placeholder="E-Mail"></br>
                </ul>
                <br/>
                <input type="submit" value="Register"> 
            </form>
		</div>
      
        <div id="footer">
        <footer>
       
            <ul>
                <li class="footer"><a href="/Aralstagrim">Home</a></li>
            </ul>
            
        </footer>
        
         </div>
        </div>
    </body>
</html>
