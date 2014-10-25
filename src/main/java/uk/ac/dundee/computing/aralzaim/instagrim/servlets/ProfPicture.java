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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;

@WebServlet(urlPatterns = {
	    "/ProfPicture",
	    "/ProfPicture/*"
	})
@MultipartConfig

public class ProfPicture extends HttpServlet {

	private Cluster cluster;
	
	  public void init(ServletConfig config) throws ServletException {
	        // TODO Auto-generated method stub
	        cluster = CassandraHosts.getCluster();
	        
	    }
	
	  
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{

		  System.out.println("IM HERE");
		  	HttpSession session=request.getSession();
	        LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
	        User user= lg.getUser();
	        String username=user.getUsername();
	        
	        Pic profile=null;
	        
			  for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());
      	  System.out.println("PROFIL FOTOSU DEGISECEK");
            String type = part.getContentType();
            
            String filename = part.getSubmittedFileName();
            
            
            InputStream is = request.getPart(part.getName()).getInputStream();
     
            
            int i = is.available();
           
            if (lg.getlogedin()){
                username=lg.getUser().getUsername();
            }
            if (i > 0) {
                byte[] b = new byte[i + 1];
                is.read(b);
                System.out.println("Length : " + b.length);
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                System.out.println("HERE" + b);
                
              profile=tm.getProfilePicofUser(username);
              
            
                tm.insertProfilePic(b, type, filename, username);
             

                is.close();
            }

			response.sendRedirect("/Instagrim/Profile/"+username);
		  
		  }
	
	
}
}
