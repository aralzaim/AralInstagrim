<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
        <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Profile Page</title>
	 <link rel="stylesheet" type="text/css" href="/Aralstagrim/Styles.css"/>
	</head>
	
	<body>
		<div id="wrapper" align="center">
		<div id="header">
        <header>
            <h1 align="center">Aralstagrim ! </h1>
            <h2 align="center">Your world in Black and White</h2>
        </header>
        
        </div>
                        
                      <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            if (lg.getlogedin()) {
                            User us;	
                            us=	lg.getUser();
                            us.fetchUserDetailsfromDB(us.getUsername());
                            
                            	
                    %>
        
        <h2>Hello ${LoggedIn.user.firstname} !</h2>
        	<h3>Edit your profile</h3>
        	
        	<%
        		PicModel tm = new PicModel();
        		
        		Pic p= tm.getProfilePicofUser(us.getUsername());
        		if(p!=null){
        		System.out.println(p);
        		%>
        		<p align="center"><img src="/Aralstagrim/ProfilePicture/<%=p.getSUUID() %>"/></p>
        		
        		<%}%>
        	
        	
        	<div id="content">
        		<form method = "POST" enctype="multipart/form-data" action="/Aralstagrim/ProfPicture">
        		<p>Change Profile Picture</p>
        		<input type="file" name="profilepic"></br>
        		<input type="submit" value="Change"></br></br>
        		</form>
        	
        		<form method = "POST"  action= "Profile">
        		<p>User Name</p> <input type="text" name="username" readonly="readonly" value="${LoggedIn.user.username}">
        		<p>First Name</p><input type="text" name="firstname" value="${LoggedIn.user.firstname}">
        		<p>Last Name</p> <input type="text" name="lastname" value="${LoggedIn.user.lastname}">
        		<p>Current E-mail(s)</p> 
        	 
        	 <p> ${LoggedIn.user.emails} </p>
        		
        		 </br>
        		
			<p>Want to add E-mail?</p> <input type="text" name="email">
        	<p>Address</p><input type="text" name="address"></br>
        	<input type="submit" value="Done"></br></br></br>
        	</form>
        	<%}}%>
        	</div>
        <div id="footer">
 		<footer>
          
               <a href="/Aralstagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></br>
               <a href="/Aralstagrim">Home</a>
         
        </footer>
        </div>
        </div>
    	
</body>
</html>