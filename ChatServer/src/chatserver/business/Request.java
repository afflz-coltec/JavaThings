/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver.business;

/**
 *
 * @author pedro
 */
public class Request {
    
    private Client client;
    private Message msg;
    
    public Request (Client c, Message m) {
        this.client = c;
        this.msg = m;
    }

    public Client getClient() {
        return client;
    }

    public Message getMsg() {
        return msg;
    }
    
}
