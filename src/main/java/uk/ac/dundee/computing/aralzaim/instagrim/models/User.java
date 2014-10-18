/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aralzaim.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import uk.ac.dundee.computing.aralzaim.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aralzaim.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User {
	
	String username;
	String firstname;
	String lastname;
	Set emails=new HashSet<String>();
	String address;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Set getEmails() {
		return emails;
	}

	public void setEmails(Set emails) {
		this.emails = emails;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	HttpSession httpsession;
    Cluster cluster;
    public User(){
        
    }
    
    public boolean checkEmailExist(String username, String email){
		
    	Session session= cluster.connect("instagrim");
    	String queryEmailList="select email from userprofiles where login=?";
    	
    	PreparedStatement ps= session.prepare(queryEmailList);
    	
    	BoundStatement boundStatement =new BoundStatement(ps);
    	
    	ResultSet rs = session.execute(boundStatement.bind(username));
    	
    	if(rs==null){
    		System.out.println("Error in checking email existing");
    	}
    	else {
    		for(Row row : rs){
    			Set emails = row.getSet("email",String.class);
    		}
    		
    		if(emails.contains(email))
    		{
    			System.out.println("emails contains the same!!!");
    			return false;
    			
    		}
    		else
    		return true;
    	}
		return true;
    	
    	
    	
    }
    
    public void fetchUserDetailsfromDB(String username){
    	
    	Session session= cluster.connect("instagrim");
    	String querySelect="select * from userprofiles where login=?";
    	
    	PreparedStatement ps= session.prepare(querySelect);
    	
    	BoundStatement boundStatement = new BoundStatement(ps);
    	
    	ResultSet rs= session.execute(boundStatement.bind(username));
    	
    	  if (rs==null) {
              System.out.println("Error in Fetching user details");
            
          } else {
        	   for (Row row : rs) {
                   
                   String firstname = row.getString("first_name");
                   String lastname= row.getString("last_name");
                   Set emails =row.getSet("email", String.class);
                  
                   this.firstname=firstname;
                   this.lastname=lastname;
                   this.emails=emails;
                   
               }
        	   
        	   //System.out.println(firstname+lastname+emails.toString());
          }
    	
    }
    
    public void updateUserDetails(String username,String firstname, String surname, Set<String> email, String address){
    	
    	
    	String queryUpdate;
    	
    	Session session = cluster.connect ("instagrim");
    	
    	if(email.isEmpty())
    	{
    		 System.out.println("EMAIL IN UPDATE EMPTY SET");
    		 queryUpdate="update userprofiles SET first_name=?, last_name=? where login=?";
    	}
    	
    	
    	queryUpdate="update userprofiles SET first_name=?, last_name=?, email = email+? where login=?";
    	PreparedStatement ps= session.prepare(queryUpdate);
    	
    	BoundStatement boundStatement = new BoundStatement(ps);
    	
    	session.execute(boundStatement.bind(firstname,surname, email, username));
    	
    	System.out.println("after execute");
    	
    }
    
    public boolean RegisterUser(String username, String password,String firstname,String lastname,Set <String> email, String address){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String encodedPassword=null;
        try {
            encodedPassword= sha1handler.SHA1(password);
            System.out.println("after encoded");
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
   
        String query="insert into userprofiles (login,password,first_name,last_name,email) Values (?,?,?,?,?)";
        
        PreparedStatement ps = session.prepare(query);

        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,encodedPassword,firstname,lastname,email));
        System.out.println("after execute");
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
    }
    
    public boolean IsValidUser(String username, String Password){
        AeSimpleSHA1 sha1handler=  new AeSimpleSHA1();
        String EncodedPassword=null;
        try {
            EncodedPassword= sha1handler.SHA1(Password);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException et){
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {
               
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                    return true;
            }
        }
   
    
    return false;  
    }
       public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    
}
