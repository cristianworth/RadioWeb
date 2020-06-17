/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 *
 * @author Marcelo
 */
public class Server {
    public volatile static ArrayList<String> ArrBuf = new ArrayList<String>();
    public volatile static ArrayList<MusicProtocol> ArrMP = new ArrayList<MusicProtocol>();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
    
     public static void splitFile(byte[] in) throws IOException, NoSuchAlgorithmException {
        ByteArrayOutputStream out = null;
        long filesize = in.length;
        InputStream is = null;
        long filesizeActual = 0L;
        int splitval = (int) (Math.ceil((filesize + (filesize % 18623)) / (18623)));
        int splitsize = (int) (filesize / splitval) + (int) (filesize % splitval);
        byte[] b = new byte[splitsize];
        try {
            is = new ByteArrayInputStream(in); 
            for (int j = 1; j <= splitval; j++) {
                int i = is.read(b);
                out.write(b, 0, i);
                ArrBuf.add(out.toString("UTF_8"));
                out.close();
                out = null;
                filesizeActual += i;
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
