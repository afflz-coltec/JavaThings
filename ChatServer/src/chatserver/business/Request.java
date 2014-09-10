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
    
    /**
     * This class implements a simple request from a client.
     * @param c <code>Client</code> who sent the message.
     * @param m <code>Message</code> sent by the <code>Client</code>.
     */
    public Request (Client c, Message m) {
        this.client = c;
        this.msg = m;
    }

    /**
     * Gets the client who made the request.
     * @return The <code>Client</code>.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Gets the message from sent by the client.
     * @return The <code>Message</code> sent.
     */
    public Message getMsg() {
        return msg;
    }
    
}
