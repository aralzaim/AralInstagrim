/**
 * 
 * @author aralzaim
 * @since 25/10/2014
 */

package uk.ac.dundee.computing.aralzaim.instagrim.servlets;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aralzaim.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aralzaim.instagrim.models.PicModel;
import uk.ac.dundee.computing.aralzaim.instagrim.models.User;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.LoggedIn;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;

import com.datastax.driver.core.Cluster;

/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/Images",
    "/Images/*",
    "/Delete/*",
    "/OrginalImage/",
    "/OrginalImage/*",
    "/ProfilePicture/",
    "/ProfilePicture/*"
  
})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);
        CommandsMap.put("Delete", 4);
        CommandsMap.put("OrginalImage", 5);
        CommandsMap.put("ProfilePicture", 6);

    }

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String args[] = Convertors.SplitRequestPath(request);
        HttpSession session=request.getSession();
        LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
        if(lg!=null){
        User us=lg.getUser();
        String username= us.getUsername();
        
        
        
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            error("Bad Operator", response);
            return;
        }
        switch (command) {
            case 1:

    	        System.out.println("1");
                DisplayImage(Convertors.DISPLAY_PROCESSED,args[2],request ,response);
                break;
            case 2:

    	        System.out.println("2");
                DisplayImageList(args[2], request ,response);
                break;
            case 3:

    	        System.out.println("3");
                DisplayImage(Convertors.DISPLAY_THUMB,args[2],request , response);
                break;
            case 4:

    	        System.out.println("4");
            	DeleteImage(username,args[2], request, response);
            	break;
            case 5: 

    	        System.out.println("5");
            	DisplayImage(Convertors.DISPLAY_IMAGE, args[2],request, response);
                break;
                
            case 6: 

    	        System.out.println("DisplayProfilePicture!");
            	DisplayProfilePicture(Convertors.DISPLAY_THUMB,args[2],request,response);
            	break;
            default:
                error("Bad Operator", response);
                
                
               
                response.sendRedirect("/Aralstagrim/Profile/"+username);
                
        }
        }}



	private void DisplayProfilePicture(int dISPLAY_THUMB, String string,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		 	
			PicModel tm = new PicModel();
	        tm.setCluster(cluster);
	        HttpSession session= request.getSession();
	       try{
	        LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
	        if(lg!=null){
	        User user= lg.getUser();Pic p=tm.getProfilePicofUser(user.getUsername());
	        
	        System.out.println("DisplayProfilePicture!");
	        
	        showImage(p, response);
	        
	        }
	       }
	       catch(Exception e){
	    	   System.out.println("Error in Display profile picture.");
	       }
	}

	private void DisplayImageList(String owner, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(owner);
        RequestDispatcher rd = request.getRequestDispatcher("/UsersPics.jsp");
        request.setAttribute("ProfileOwner", owner);
        request.setAttribute("Pics", lsPics);
        rd.forward(request, response);

    }
    private void DeleteImage(String username,String delid,HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
    	
    
    	PicModel delpic=new PicModel();	
    	delpic.deletePic(username, delid);
    	response.sendRedirect("/Aralstagrim/Images/"+username);
        
    }
   
 
    
   
   private void DisplayImage(int type,String Image, HttpServletRequest request ,HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        HttpSession session= request.getSession();
       try{
        LoggedIn lg= (LoggedIn) session.getAttribute("LoggedIn");
        if(lg!=null){
        User user= lg.getUser();
        
       
      //NEED NEW FUNCTION FOR DISPALY PROFILE PICTURE  
        Pic p = tm.getPic(type,java.util.UUID.fromString(Image));
        
     //   Pic p=tm.getProfilePicofUser(user.getUsername());
        
        showImage(p,response);
        
        }
        }
       catch(Exception e){
    	   System.out.println("Error Display Image.");
       }
       
    }

    private void showImage(Pic p,HttpServletResponse response) throws IOException {
    	 OutputStream out=null;
    	 try{
    	 out = response.getOutputStream();

        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
       
        
        
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }}
    	  catch(Exception e){
	    	   System.out.println("Error in show image function.");
	       }
        out.close();
	
}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       try{
		for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());

            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            HttpSession session=request.getSession();
            LoggedIn lg= (LoggedIn)session.getAttribute("LoggedIn");
            String username="majed";
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
                tm.insertPic(b, type, filename, username);
                

                is.close();
            }
            RequestDispatcher rd = request.getRequestDispatcher("/upload.jsp");
             rd.forward(request, response);
        }
       }
       catch(Exception e){
    	   System.out.println("Exception in doPostImage");
       }
    }

    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have a na error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
}
