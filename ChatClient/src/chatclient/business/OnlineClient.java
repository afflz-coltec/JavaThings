/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient.business;

/**
 *
 * @author pedro
 */
public class OnlineClient {
    
    private String nick;
    private int ClientID;
    
    public OnlineClient(int iD) {
        this.ClientID = iD;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    
    public int getClientID() {
        return ClientID;
    }
  
}
