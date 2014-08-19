/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chat_server;

import chat_server.exceptions.InvalidPort;

/**
 *
 * @author pedro
 */
public class Chat_Server {
    
    private static final String HelpMessage =   "Usage: java -jar Chat_Server.java [-options] [port]\n" +
                                                "\nwhere options include:\n" +
                                                "\t-p --port\tSets the port to run the chat server\n" +
                                                "\t-h --help -?\tPrint this help message" +
                                                "\n";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int port;
        
        if ( args.length > 2 || args.length == 1 ) {
            System.out.println(Chat_Server.HelpMessage);
            System.exit(0);
        }
        
        try {
            switch( args[0] ) {

                case "-h":
                case "--help":
                case "-?":
                    System.out.println(Chat_Server.HelpMessage);
                    break;

                case "-p":
                case "--port":
                    
                    port = Integer.parseInt(args[1]);
                    
                    if ( port <= 1023 || port > 65535 ) {
                        throw new InvalidPort();
                    }
                    
                    // TODO: Create and start server.
                    
                    System.out.println(args[1]);
                    break;

            }
        }
        catch ( ArrayIndexOutOfBoundsException e ) {
            System.out.println(Chat_Server.HelpMessage);
        }
        catch ( NumberFormatException e ) {
            System.out.println("Port must an integer!");
        }
        catch ( InvalidPort e ) {
            System.out.println("Port number must be between 1024 and 65535!");
        }
        
        
    }
    
}
