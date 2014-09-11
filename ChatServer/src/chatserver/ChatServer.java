/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatserver;

import chatserver.business.Server;
import chatserver.exceptions.InvalidPort;
import java.io.IOException;
import java.net.BindException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class ChatServer {
    
    private static final String HELP_MESSAGE =  "\n-------------------------------------------------------------" + 
                                                "\nUsage: java -jar Chat_Server.java [[-options] [port]]\n" + 
                                                "\nwhere options include:\n" + 
                                                "\t-p --port\tSets the port to run the chat server\n" + 
                                                "\t-h --help -?\tPrint this help message\n" + 
                                                "-------------------------------------------------------------\n";

    /**
     * Starts the server, with the default port or with the port the user sets.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int port;

        if (args.length > 2 || args.length == 1) { // If theres more than 2 or just 1 argument, just prints the help message.
            
            System.out.println(ChatServer.HELP_MESSAGE);
            System.exit(0);
            
        }
            
        try {
            
            if (args.length == 0) {
                
                Server server = new Server();
                
                server.startServer();

            } else {

                // switch the options chosen by the user.
                switch (args[0]) {

                    case "-h":
                    case "--help":
                    case "-?":
                        System.out.println(ChatServer.HELP_MESSAGE);
                        break;

                    case "-p":
                    case "--port":

                        port = Integer.parseInt(args[1]);

                        if (port <= 1023 || port > 65535) {
                            throw new InvalidPort();
                        }
                        
                        Server server = new Server(port);
                        
                        server.startServer();
                        
                        break;

                }
            }
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(ChatServer.HELP_MESSAGE);
            
        } catch (NumberFormatException e) {
            System.out.println("Port must an integer!");
            
        } catch (InvalidPort e) {
            System.out.println("Port number must be between 1024 and 65535!");
            
        } catch (IOException ex) {
            System.err.println("Address already in use!");
        }
        
    }
    
}
