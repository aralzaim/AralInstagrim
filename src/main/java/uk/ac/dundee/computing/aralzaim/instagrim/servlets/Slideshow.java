/**
 * 
 * @author aralzaim
 * @since 25/10/2014
 */

package uk.ac.dundee.computing.aralzaim.instagrim.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.core.Cluster;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aralzaim.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aralzaim.instagrim.models.PicModel;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;

@WebServlet(urlPatterns = {
	    "/Slideshow",
	    "/Slideshow/*"
	})

public class Slideshow extends HttpServlet{
	
    private Cluster cluster;
	
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	     
        String args[] = Convertors.SplitRequestPath(request);
	
    	
    PicModel pm = new PicModel();
	pm.setCluster(cluster);
	java.util.LinkedList<Pic> lsPics = pm.getPicsForUser(args[2]) ;   
	
	RequestDispatcher rd= request.getRequestDispatcher("/slideshow.jsp");
	request.setAttribute("Pics", lsPics);
	
	rd.forward(request,response);
	
    }

	 
	
}
