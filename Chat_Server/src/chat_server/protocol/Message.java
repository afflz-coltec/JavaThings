/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server.protocol;

import chat_server.utils.MessageUtils;

/**
 *
 * @author pedro
 */
public class Message {
    
    public static enum Services {
        HelloService,
        ChangeNickService,
        ConnectedClientsService,
        GetNickService,
        SendMsgService,
        ByeService,
        DeniedService
    }
    
    public static enum Nack {
        InvalidChecksum,
        NotGreetedClient,
        BadFormedMessage,
        UnidentifiedClient,
        ExistentClient
    }
    
    private static final byte[] SERVICES = {0x01,0x02,0x03,0x04,0x05,0x0A};
    private static final byte[] NACKS    = {(byte)0xFF,(byte)0xEE,(byte)0xDD,(byte)0xCC,(byte)0xBB};
    
    private byte service;
    private byte[] size;
    private byte[] data;
    private byte[] checksum;
    
    public Message(byte service,byte[] size,byte[] data) {
        this.service = service;
        this.size = size;
        this.data = data;
        //this.checksum = checksum;
    }
    
    public int getCheckSum(Message msg) {
        
        int cs = (int)msg.service & 0xFF;
        cs +=    (int)(msg.size[0] >> 8) & 0xFF;
        cs +=    (int)(msg.size[1] >> 0) & 0xFF;
        
        for ( int i=0;i<msg.data.length;i++ ) {
            cs += ((int)msg.data[i] & 0xFF);
        }
        
        return cs;
        
    }
    
    public void printMsg() {
        System.out.print(String.format("%02X ", this.service));
        for ( byte b : this.size ) {
            System.out.print(String.format("%02X ", b));
        }
        for ( byte b : this.data ) {
            System.out.print(String.format("%02X ", b));
        }
        //for ( byte b : this.checksum ) {
        //    System.out.print(" " + b);
        //}
        
        System.out.print( " " + MessageUtils.byteVectorToInteger(this.size));
        
        System.out.print( " " + MessageUtils.byteVectorToString(this.data));
    }
    
}
