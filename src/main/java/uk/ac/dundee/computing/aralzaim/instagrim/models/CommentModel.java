package uk.ac.dundee.computing.aralzaim.instagrim.models;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.*;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CommentModel {
	
	String comment;
	UUID picid;
	UUID commentid;
	String user;
	Date currentdate;

    Cluster cluster;

    public CommentModel() {

    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
    
    
    public void insertComments(String picId, String comment, String username) {
    	
    	try{
    		
    
    	Date currentdate= new Date();
    	UUID commentid=Convertors.getTimeUUID();
    	Session session= CassandraHosts.getCluster().connect("instagrim");
    	java.util.UUID picid = java.util.UUID.fromString(picId);
        
    	System.out.println("inside of insertcomments");
    	String queryInsertComments="insert into comments (commentid,comment,picid,user,commentadded) Values (?,?,?,?,?)";
        
    	
    	
    	
    	PreparedStatement psInsertComment=session.prepare(queryInsertComments);
    
    	session.execute(psInsertComment.bind(commentid,comment,picid, username, currentdate));    
    	session.close();
    	}
    	catch(Exception e){
    		System.out.println("Exception in insert comment");
    	}
    	
    }
    
    public Set<CommentModel> getComments(String picid){
		    	
    	
    	UUID uuid=UUID.fromString(picid);
    	Set<CommentModel> commentrow= new HashSet<CommentModel>();
    	Session session= CassandraHosts.getCluster().connect("instagrim");
    	String querycomments="select * from comments where picid=?";
   
    	PreparedStatement psSelectComments= session.prepare(querycomments);
    
    	
    	ResultSet rs=session.execute(psSelectComments.bind(uuid));
    	session.close();
    	for(Row row : rs){
    		CommentModel cm=new CommentModel();
    		cm.comment=row.getString("comment");
    		cm.commentid=row.getUUID("commentid");
    		cm.currentdate=row.getDate("commentadded");
    		cm.picid=row.getUUID("picid");
    		cm.user=row.getString("user");
    		
    		
    		
    		commentrow.add(cm);
    	}
    	
    	
    	
    	return commentrow;
		
    }

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public UUID getPicid() {
		return picid;
	}

	public void setPicid(UUID picid) {
		this.picid = picid;
	}

	public UUID getCommentid() {
		return commentid;
	}

	public void setCommentid(UUID commentid) {
		this.commentid = commentid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}

	public Cluster getCluster() {
		return cluster;
	}
	
}
