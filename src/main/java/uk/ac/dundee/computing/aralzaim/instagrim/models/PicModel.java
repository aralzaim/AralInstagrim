package uk.ac.dundee.computing.aralzaim.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import com.eaio.uuid.UUID;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.imageio.ImageIO;

import static org.imgscalr.Scalr.*;

import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.*;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

public class PicModel {

    Cluster cluster;

    public void PicModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    
    public void insertComments(String picId, Set<String> comments) {
    	
    	try{
    	Session session= CassandraHosts.getCluster().connect("aralstagrim");
    	java.util.UUID picid = java.util.UUID.fromString(picId);
    	String queryInsertComments="update pics SET comment=comment+? where picid=?";
    	
    	PreparedStatement psInsertComment=null;
    	BoundStatement bsInsertComment=null;
    	psInsertComment=session.prepare(queryInsertComments);
    	bsInsertComment= new BoundStatement(psInsertComment);
    	session.execute(bsInsertComment.bind(comments,picid));    
    	session.close();
    	}
    	catch(Exception e){
    		
    	}
    	
    }
    
    public void insertProfilePic(byte[] b, String type, String name, String user) throws IOException{
    	
    	try{
    	Convertors convertor= new Convertors();
    	String types[]=Convertors.SplitFiletype(type);
    	PreparedStatement psInsertProfilePic=null;
    	BoundStatement bsInsertProfilePic=null;
    	int length = b.length;
    	java.util.UUID picid= convertor.getTimeUUID();
    	
    	Boolean success= (new File("/var/tmp/aralstagrim/")).mkdirs();
    	FileOutputStream output = new FileOutputStream(new File("/var/tmp/aralstagrim/"+picid));
    	
    	output.write(b);
    	
    	byte []  thumbb = picresize(picid.toString(),types[1]);
        int thumblength= thumbb.length;
        ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
        Session session = cluster.connect("aralstagrim");
        
        Date DateAdded = new Date();
        
  //     if(null==getPicsForUser(user)){

    	  
        psInsertProfilePic = session.prepare("insert into profilePics ( picid,thumb, user, interaction_time,thumblength,type,name) values(?,?,?,?,?,?,?)");
        bsInsertProfilePic = new BoundStatement(psInsertProfilePic);
        
        session.execute(bsInsertProfilePic.bind(picid, thumbbuf, user, DateAdded,thumblength, type, name));
   //    }
       
   //    else{
    	   
   // 	   System.out.println("UPDATE");
   // 	   psInsertProfilePic=session.prepare("update profilepics SET picid=?, thumb=?, interaction_time=?,thumblength=?,type=?,name=?  where login=?");
    	   
   //        session.execute(bsInsertProfilePic.bind(picid, thumbbuf, DateAdded,thumblength, type, name,user));
   //    }

        session.close();
    	
    	}
    	catch(Exception e){
    		
    	}
    }
    public void insertPic(byte[] b, String type, String name, String user) {
        try {
            Convertors convertor = new Convertors();

            String types[]=Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
          
            java.util.UUID picid = convertor.getTimeUUID();
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/aralstagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/aralstagrim/" + picid));

            output.write(b);
            byte []  thumbb = picresize(picid.toString(),types[1]);
            int thumblength= thumbb.length;
            ByteBuffer thumbbuf=ByteBuffer.wrap(thumbb);
            byte[] processedb = picdecolour(picid.toString(),types[1]);
            ByteBuffer processedbuf=ByteBuffer.wrap(processedb);
            int processedlength=processedb.length;
            Session session = cluster.connect("aralstagrim");

            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, interaction_time,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid, buffer, thumbbuf,processedbuf, user, DateAdded, length,thumblength,processedlength, type, name));
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
            session.close();
           
        } catch (IOException ex) {
            System.out.println("Error --> " + ex);
        }
    }
    
    public void deletePic(String username, String picid){
    	
    	Session session = CassandraHosts.getCluster().connect("aralstagrim");
    	ResultSet rs=null;
    	String owner;
    	
    	
    	java.util.UUID uuid=java.util.UUID.fromString(picid);
    	try{
    		
    	String getOwnerPic="select user from aralstagrim.pics where picid=?";	
    	PreparedStatement psGetOwnerPic= session.prepare(getOwnerPic);
    	BoundStatement bsGetOwnerPic= new BoundStatement (psGetOwnerPic);
    	rs=session.execute(bsGetOwnerPic.bind(uuid));
    	owner=rs.one().getString("user");
    	
    	if(owner.equals(username))
    	{
    	String queryDeletePic="DELETE FROM aralstagrim.pics WHERE picid=?";
    	String queryDeletePicToUser="DELETE FROM aralstagrim.userpiclist WHERE picid=?";
    	
    	
    	
    	
    	
    	PreparedStatement psDeletePic = session.prepare(queryDeletePic);
    	PreparedStatement psDeletePicToUser=session.prepare(queryDeletePicToUser);
    	
    	
    	
     	BoundStatement bsDeletePic=new BoundStatement(psDeletePic);
     	BoundStatement bsDeletePicToUser=new BoundStatement(psDeletePicToUser);
    	
    	
     	
    	session.execute(bsDeletePic.bind(uuid));
    	session.execute(bsDeletePicToUser.bind(uuid));
    	
    	}
    	
    	session.close();
    	}
    	catch(Exception e){
    		
    	}
   
    }

    public byte[] picresize(String picid,String type) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/aralstagrim/" + picid));
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }
    
    public byte[] picdecolour(String picid,String type) {
        try {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/aralstagrim/" + picid));
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public static BufferedImage createThumbnail(BufferedImage img) {
        img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
        // Let's add a little border before we return result.
        return pad(img, 2);
    }
    
   public static BufferedImage createProcessed(BufferedImage img) {
        int Width=img.getWidth()-1;
        img = resize(img, Method.SPEED, Width, OP_ANTIALIAS, OP_GRAYSCALE);
        return pad(img, 4);
    }
   
    public java.util.LinkedList<Pic> getPicsForUser(String user) {
    	java.util.UUID picid=null;
        String owner=user;
    	java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
    	Session session = CassandraHosts.getCluster().connect("aralstagrim");    	
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        
        try{
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        session.close();
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
               picid = row.getUUID("picid");
               
               
                System.out.println("UUID - " + picid.toString());
                

                pic.setOwner(owner);
           
                pic.setUUID(picid);
                Pics.add(pic);
            }}}
            catch(Exception et) {
                System.out.println("Can't get Profile Pic" + et);
                return null;
        }
        return Pics;
    }
    
    public Pic getProfilePicofUser(String user){
    	Session session = CassandraHosts.getCluster().connect("aralstagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        java.util.UUID uuid=null;
    	
    	try{
    		ResultSet rs=null;
    		PreparedStatement ps=null;
    		
    		ps= session.prepare("select picid,thumb, thumblength,type, picid from profilePics where user=?");
    		
    		BoundStatement boundStatement = new BoundStatement(ps);
    		rs= session.execute(boundStatement.bind(user));
    		
    		   if (rs.isExhausted()) {
                   System.out.println("No Images returned");
                   return null;
               } else {
                   for (Row row : rs) {
                      
                      
                           bImage = row.getBytes("thumb");
                           length = row.getInt("thumblength");
                           uuid= row.getUUID("picid");
                           type = row.getString("type");

                   }
               }
    	}
     catch (Exception et) {
        System.out.println("Can't get Profile Pic" + et);
        return null;
    }
    	
    	 session.close();
         Pic p = new Pic();
         p.setPic(bImage, length, type);
         p.setUUID(uuid);

         return p;
		
    }

    public Pic getPic(int image_type, java.util.UUID picid) {
        Session session = cluster.connect("aralstagrim");
        ByteBuffer bImage = null;
        String type = null;
        String owner=null;
        int length = 0;
        try {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE) {
                
                ps = session.prepare("select image,imagelength,type,user from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type,user from pics where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type,user from pics where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned");
                return null;
            } else {
                for (Row row : rs) {
                    if (image_type == Convertors.DISPLAY_IMAGE) {
                    	owner=row.getString("user");
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    } else if (image_type == Convertors.DISPLAY_THUMB) {
                    	owner=row.getString("user");
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                    	owner=row.getString("user");
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }
                    
                    type = row.getString("type");

                }
            }
        } catch (Exception et) {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        session.close();
        Pic p = new Pic();
        p.setOwner(owner);
        p.setPic(bImage, length, type);

        return p;

    }

}
