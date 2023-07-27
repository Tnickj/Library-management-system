/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarysystemserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import za.ac.tut.requesthandler.RequestHandler;

/**
 *
 * @author user
 */
public class LibrarySystemServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerSocket sSocket = null;
        Socket socket = null;
        
        try {
            sSocket  =new ServerSocket(1010);
            while(true){
                socket = sSocket.accept();
                new RequestHandler(socket);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }finally{
            try{
                socket.close();
            }catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
