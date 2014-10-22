package uk.ac.dundee.computing.aralzaim.instagrim.servlets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aralzaim.instagrim.models.PicModel;
import uk.ac.dundee.computing.aralzaim.instagrim.models.User;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.LoggedIn;

import com.datastax.driver.core.Cluster;

@WebServlet(urlPatterns = {
	    "/Comment",
	    "/Comment/*"
	   
	})

public class Comment extends HttpServlet{
	PicModel pm= new PicModel();
	private Cluster cluster;
	
	  public void init(ServletConfig config) throws ServletException {
	        // TODO Auto-generated method stub
	        cluster = CassandraHosts.getCluster();
	        
	    }
	
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	  
		  try{
			
		 HttpSession session=request.getSession();
	     LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
	     User user = lg.getUser();
	     String username = user.getUsername();
	    
	      
	      
		 PicModel pm= new PicModel();
		 String comment = request.getParameter("comment");
		 String picId = request.getParameter("picid");
		
		  Set comments =new HashSet<String>();
		  if(!comment.equals("")){
		 
			  System.out.println("NOT EMPTY"+ comments.toString());
			  comments.add(comment);
		  }
		  
		  pm.insertComments(picId, comments);
		  
		  
		 
		  
		 
		  
		  response.sendRedirect("/Instagrim/Images/"+username);
		  }
		  catch(Exception e){
			  System.out.println("Error in Comment.java");
		  }
	  
	  }
	  
}
