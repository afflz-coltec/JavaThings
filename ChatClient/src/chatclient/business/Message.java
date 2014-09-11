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
    
    /**
     * Enumeration of the Services.
     */
    public enum Services {
        
        HelloService            ((byte)0x01),
        ChangeNickService       ((byte)0x02),
        ConnectedClientsService ((byte)0x03),
        RequestNickService      ((byte)0x04),
        SendMsgService          ((byte)0x05),
        ByeService              ((byte)0x0A),
        DeniedService           ((byte)0x7F);
        
        private final byte serviceByte;
        
        private Services(byte serviceByte) {
            this.serviceByte = serviceByte;
        }
        
        /**
         * Gets the service byte.
         * @return The service <code>byte</code>.
         */
        public byte getByte() {
            return this.serviceByte;
        }
        
        /**
         * Get Service by byte.
         * @param b Service byte.
         * @return Returns a <code>Services</code> element.
         */
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
                    s = RequestNickService;
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
                    
                default:
                    s = SendMsgService;
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
    
    /**
     * This class implements a Message object that contains a service, the size and the data of the message.
     * @param service A <code>byte</code> of the service.
     * @param size A <code>int</code> size of the data.
     * @param data The <code>byte[]</code> containing the data.
     */
    public Message(byte service, int size, byte[] data) {
        this.service = service;
        this.size = size;
        this.data = data;
    }

    /**
     * Gets the service of the message.
     * @return Returns the service <code>byte</code>.
     */
    public byte getService() {
        return this.service;
    }
    
    /**
     * Gets the size of message data.
     * @return A <code>int</code> containing the size.
     */
    public int getSize() {
        return this.size;
    }
    
    /**
     * Gets the data of the message.
     * @return A <code>byte[]</code> containing the data.
     */
    public byte[] getData() {
        return this.data;
    }
    
    /**
     * Prints the message in the console.
     * @param msg A <code>byte[]</code> containing the message.
     */
    public static void printMessage(byte[] msg) {
        
        for ( byte b : msg )
            System.out.print(String.format("%02X ", b));
        
        System.out.println();
        
    }
    
    /**
     * Calculates the checksum of a message.
     * @param msg A <code>Message</code> to be calculated.
     * @return An <code>int</code> checksum.
     */
    public static int getCheckSum(Message msg) {
        
        int checksum = (int)msg.service & 0xFF;
        
        checksum += (int) (msg.size >> 8) & 0xFF;
        checksum += (int) (msg.size >> 0) & 0xFF;
        
        for ( int i=0;i<msg.data.length;i++ )
            checksum += ((int)msg.data[i] & 0xFF);
        
        return checksum & 0xFFFF;
        
    }
    
    /**
     * Gets a message as a byte vector.
     * @param msg A <code>Message</code> object.
     * @return A <code>byte[]</code> containing the message.
     */
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
