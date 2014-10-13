/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aralzaim.instagrim.stores;

import uk.ac.dundee.computing.aralzaim.instagrim.models.User;

/**
 *
 * @author Administrator
 */
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
