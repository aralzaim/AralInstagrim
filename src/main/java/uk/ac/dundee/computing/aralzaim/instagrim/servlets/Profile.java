/**
 * 
 * @author aralzaim
 * @since 25/10/2014
 */

package uk.ac.dundee.computing.aralzaim.instagrim.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.datastax.driver.core.Cluster;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aralzaim.instagrim.models.PicModel;
import uk.ac.dundee.computing.aralzaim.instagrim.models.User;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.LoggedIn;


@WebServlet(urlPatterns = {
	    "/Profile",
	    "/Profile/*"
	})

public class Profile extends HttpServlet {
	
	  private Cluster cluster;
	  HttpSession session;
	  

	  
	
	  public void init(ServletConfig config) throws ServletException {
	        // TODO Auto-generated method stub
	        cluster = CassandraHosts.getCluster();
	        
	    }
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
		
		
		  String username=request.getParameter("username");
		  String firstname=request.getParameter("firstname");
		  String surname=request.getParameter("lastname");
		  String email=request.getParameter("email");
		  String address=request.getParameter("address");
		
		  User us= new User();
		  us.setCluster(cluster);
		  
		 
		  System.out.println(username+firstname+surname+email);
	
		  
		  if(us.checkEmailExist(username,email)){
			  
		 
			  Set emails =new HashSet<String>();
		  if(!email.equals("")){
		 
			  System.out.println("NOT EMPTY"+ emails.toString());
			  emails.add(email);
		  }
		  
		
		  us.updateUserDetails(username, firstname, surname, emails, address);
		
		
		 
			response.sendRedirect("/Instagrim/Profile/"+username);
		  
	  

	  }
		  
	  
	  }
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  

		  RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
		   rd.forward(request, response);
	  }
	
}
