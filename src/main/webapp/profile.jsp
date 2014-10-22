
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.stores.*" %>
        <%@page import="uk.ac.dundee.computing.aralzaim.instagrim.models.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head> 
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Profile Page</title>
	 <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css"/>
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
                            if (lg.getlogedin()) {
                            User us;	
                            us=	lg.getUser();
                            us.fetchUserDetailsfromDB(us.getUsername());
                            
                            	
                    %>
        
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
        	
        	
        	<div id="content">
        		<form method = "POST" enctype="multipart/form-data" action="/Instagrim/ProfPicture">
        		<p>Change Profile Picture</p>
        		<input type="file" name="profilepic"></br>
        		<input type="submit" value="Change"></br></br>
        		</form>
        	
        		<form method = "POST"  action= "Profile">
        		<p>User Name</p> <input type="text" name="username" readonly="readonly" value="<%=us.getUsername()%>">
        		<p>First Name</p><input type="text" name="firstname" value="<%=us.getFirstname()%>">
        		<p>Last Name</p> <input type="text" name="lastname" value="<%=us.getLastname()%>">
        		<p>Current E-mail(s)</p> 
        	 
        	 <p> <%=us.getEmails().toString()%> </p>
        		
        		 </br>
        		
			<p>Want to add E-mail?</p> <input type="text" name="email">
        	<p>Address</p><input type="text" name="address"></br>
        	<input type="submit" value="Done"></br></br></br>
        	</form>
        	<%}}%>
        	</div>
        <div id="footer">
 		<footer>
          
               <a href="/Instagrim/Logout" onclick="return confirm('You are about to LOGOUT!')">Logout</a></br>
               <a href="/Instagrim">Home</a>
         
        </footer>
        </div>
        </div>
    	
</body>
</html>