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
public class Message {
    
    public enum Services {
        
        HelloService            ((byte)0x01),
        ChangeNickService       ((byte)0x02),
        ConnectedClientsService ((byte)0x03),
        GetNickService          ((byte)0x04),
        SendMsgService          ((byte)0x05),
        ByeService              ((byte)0x0A),
        DeniedService           ((byte)0x7F);
        
        private final byte serviceByte;
        
        private Services(byte serviceByte) {
            this.serviceByte = serviceByte;
        }
        
        public byte getByte() {
            return this.serviceByte;
        }
        
        public static Services getService(byte b) {
            
            Services s = null;
            
            switch(b) {
                case 0x01:
                    s = HelloService;
                    break;
                    
                case 0x02:
                    s = ChangeNickService;
                    break;
                    
                case 0x03:
                    s = ConnectedClientsService;
                    break;
                    
                case 0x04:
                    s = GetNickService;
                    break;
                    
                case 0x05:
                    s = SendMsgService;
                    break;
                    
                case 0x0A:
                    s = ByeService;
                    break;
                    
                case 0x7F:
                    s = DeniedService;
                    break;
            }
            
            return s;
            
        }
        
    };
    
    private byte service;
    private int size;
    private byte[] data;
    
    private static final int PAYLOAD = 5;
    private static final int BYTES_PER_CHAR = 2;
    
    public Message(byte service, int size, byte[] data) {
        this.service = service;
        this.size = size;
        this.data = data;
    }

    public byte getService() {
        return this.service;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    public static void printMessage(byte[] msg) {
        
        for ( byte b : msg )
            System.out.print(String.format("%02X ", b));
        
        System.out.println();
        
    }
    
    public static int getCheckSum(Message msg) {
        
        int checksum = (int)msg.service & 0xFF;
        
        checksum += (int) (msg.size >> 8) & 0xFF;
        checksum += (int) (msg.size >> 0) & 0xFF;
        
        for ( int i=0;i<msg.data.length;i++ )
            checksum += ((int)msg.data[i] & 0xFF);
        
        return checksum & 0xFFFF;
        
    }
    
    public static byte[] getMsgAsByteVector(Message msg) {
        
        byte[] messageAsByte = new byte[msg.size + PAYLOAD];
        
        int checksum = getCheckSum(msg);
        
        messageAsByte[0] = msg.service;
        messageAsByte[1] = (byte)(msg.size >> 8);
        messageAsByte[2] = (byte)(msg.size >> 0);
        
        System.arraycopy(msg.data, 0, messageAsByte, 3, msg.size);
        
        messageAsByte[msg.size + 3] = (byte)((checksum>>8) & 0x000000FF);
        messageAsByte[msg.size + 4] = (byte)((checksum>>0) & 0x000000FF);
        
        return messageAsByte;
        
    }
    
}
