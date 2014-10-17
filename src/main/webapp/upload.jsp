<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
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
        <h1 align="center">InstaGrim ! </h1>
        <h2 align="center">Your world in Black and White</h2>
        <div align="center">
      
        <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile"><br/>

                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form>

        </article>
       
                <footer>
               
          
          		<li class="nav"><a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></li>

             
                <li class="footer"><a href="/Instagrim">Home</a></li>
            	</div>
                
        </footer>
      
         </div>
    </body>
</html>
