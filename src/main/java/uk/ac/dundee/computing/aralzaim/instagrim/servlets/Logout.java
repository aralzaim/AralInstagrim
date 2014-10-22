package uk.ac.dundee.computing.aralzaim.instagrim.servlets;


import com.datastax.driver.core.Cluster;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aralzaim.instagrim.models.User;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.LoggedIn;

@WebServlet(name = "Logout", urlPatterns = {"/Logout"})


public class Logout extends HttpServlet {

    private Cluster cluster=null;


    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        request.getSession().invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    

            
    }
}
