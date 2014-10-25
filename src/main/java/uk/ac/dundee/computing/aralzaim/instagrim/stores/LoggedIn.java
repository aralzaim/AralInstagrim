/**
 * 
 * @author aralzaim
 * @since 25/10/2014
 */

package uk.ac.dundee.computing.aralzaim.instagrim.stores;

import uk.ac.dundee.computing.aralzaim.instagrim.models.User;

public class LoggedIn {
    boolean logedin=false;
    User user;
    public void LogedIn(){
        
    }
    
    public void setUser(User user){
        this.user=user;
    }
    public User getUser(){
        return user;
    }
    public void setLogedin(){
        logedin=true;
    }
    public void setLogedout(){
        logedin=false;
    }
    
    public void setLoginState(boolean logedin){
        this.logedin=logedin;
    }
    public boolean getlogedin(){
        return logedin;
    }
}
