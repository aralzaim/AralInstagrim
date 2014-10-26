<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Administrator
--%>
<%@ page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Aralstagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <h1 align="center">Aralstagrim ! </h1>
        <h2 align="center">Your world in Black and White</h2>
        <div align="center">
      <a href="/Aralstagrim">Home</a> 
          <a href="/Aralstagrim/Images/${LoggedIn.user.username}">Your Images</a>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
            <p>    File to upload: <input type="file" name="upfile"><br/></p>

                <br/>
                <input type="submit" value="Press"> <p>to upload the file!</p>
            </form>

      
                <footer>
               
          
          		<a href="/Aralstagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a>
				<a href="/Aralstagrim">Home</a>
            	</div>
                
        </footer>
      
         </div>
    </body>
</html>
