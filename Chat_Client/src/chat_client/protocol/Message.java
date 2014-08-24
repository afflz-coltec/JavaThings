/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_client.protocol;

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
    
    private boolean isValid = false;
    
    public Message(byte service,byte[] size, byte[] data) {
        this.service = service;
        this.size = size;
        this.data = data;
    }
    
}
