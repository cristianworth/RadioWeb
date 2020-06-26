/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marcelo
 */
public class Server {

    private static final String PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    /**
     * Metodo validateIP Varifica se o IP é valido
     *
     * @param ip String IP
     * @return boolean
     */
    public static boolean validateIP(String ip) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * @param args the command line arguments
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Server IP: ");
        String ip;
        while (true) {
            Scanner scanner = new Scanner(System.in);
            ip = scanner.nextLine();
            if(validateIP(ip))
                break;
            else
                System.out.println("IP invalido");
        }
        new ServerThread("ServerThread",ip).init();
    }

}
