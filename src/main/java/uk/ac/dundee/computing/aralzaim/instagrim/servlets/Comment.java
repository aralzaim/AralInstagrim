/**
 * 
 * @author aralzaim
 * @since 25/10/2014
 */

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
import uk.ac.dundee.computing.aralzaim.instagrim.models.CommentModel;
import uk.ac.dundee.computing.aralzaim.instagrim.models.PicModel;
import uk.ac.dundee.computing.aralzaim.instagrim.models.User;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;

import com.datastax.driver.core.Cluster;

@WebServlet(urlPatterns = {
	    "/Comment",
	    "/Comment/*"
	   
	})

public class Comment extends HttpServlet{
		

	private Cluster cluster;
	
	  public void init(ServletConfig config) throws ServletException {
	        // TODO Auto-generated method stub
	        cluster = CassandraHosts.getCluster();
	        
	    }
	
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
	  
		  try{
			System.out.println("inside of comments");
		 HttpSession session=request.getSession();
		 CommentModel cm= new CommentModel();
	     LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
	     User user = lg.getUser();
	     String username = user.getUsername();
	    
	     String ownerofpic=request.getParameter("owner");
	     String comment = request.getParameter("comment");
		 String picId = request.getParameter("picid");
		
		
		  if(!comment.equals(null)){
		 
			
		  
		  cm.insertComments(picId, comment,username);
		  }
		  
		 
		 
		 System.out.println(request.getRequestURI());
		  
		  response.sendRedirect("/Instagrim/Images/"+ownerofpic);
		  }
		  catch(Exception e){
			  System.out.println("Error in Comment.java");
		  }
	  
	  }
	  
}
