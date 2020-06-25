/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Marcelo
 */
public class Server {
    /**
     * @param args the command line arguments
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        new ServerThread().init();
    }
    
     
}
