package uk.ac.dundee.computing.aralzaim.instagrim.lib;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            //Add some keyspaces here
            String createkeyspace = "create keyspace if not exists aralstagrim  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists aralstagrim.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + "  processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ")";
            String Createuserpiclist = "CREATE TABLE if not exists aralstagrim.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (picid,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";
            String CreateAddressType = "CREATE TYPE if not exists aralstagrim.address (\n"
                    + "      street text,\n"
                    + "      city text,\n"
                    + "      zip int\n"
                    + "  );";
            String CreateUserProfile = "CREATE TABLE if not exists aralstagrim.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                     + "     password text,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      email set<text>,\n"
                    + "      addresses  map<text, frozen <address>>\n"
                    + "  );";
            
            
            String CreateUserProfilePic="CREATE TABLE if not exists aralstagrim.profilePics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " interaction_time timestamp,"
                    + " thumb blob,"
                    + " thumblength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (user)"
                    + ")";
            
            String CreateComment="CREATE TABLE if not exists aralstagrim.comments("
            		+"commentid uuid,"
            		+"comment varchar,"
            		+"picid uuid,"
            		+"user varchar,"
            		+"commentadded timestamp,"
            		+"PRIMARY KEY (commentid, commentadded))"
            		+ "WITH CLUSTERING ORDER BY (commentadded desc);";
            
            String  createCommentIndex="CREATE INDEX if not exists commentindex on aralstagrim.comments(picid)";
            
            
            String CreateUserPicsIndex= "CREATE INDEX if not exists userpiclistindex ON aralstagrim.userpiclist (user);";
            
            Session session = c.connect();
            try {
                PreparedStatement statement = session
                        .prepare(createkeyspace);
                BoundStatement boundStatement = new BoundStatement(
                        statement);
                ResultSet rs = session
                        .execute(boundStatement);
                System.out.println("created aralstagrim ");
            } catch (Exception et) {
                System.out.println("Can't create aralstagrim " + et);
            }

            //now add some column families 
            System.out.println("" + CreatePicTable);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create tweet table " + et);
            }
            System.out.println("" + Createuserpiclist);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list table " + et);
            }
            System.out.println("" + CreateAddressType);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateAddressType);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address type " + et);
            }
            System.out.println("" + CreateUserProfile);
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create Address Profile " + et);
            }
           
            System.out.println("" + CreateUserPicsIndex);

            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserPicsIndex);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user pic list index " + et);
            }
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfilePic);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create user profile pic list index " + et);    
            }
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(CreateComment);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't create comment tablee index " + et);
            }
            
            try {
                SimpleStatement cqlQuery = new SimpleStatement(createCommentIndex);
                session.execute(cqlQuery);
            } catch (Exception et) {
                System.out.println("Can't crete comment index index " + et);
            }
            
            session.close();

        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }
        
    
        

    }
}
