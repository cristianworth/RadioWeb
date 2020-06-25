/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radioweb.serverController;

/**
 *
 * @author Marcelo
 */
import java.io.File;

public class AudioUtil {
     /**
     * Metodo getSoundFile
     * verifica se o arquivo existe
     * @param fileName caminho do arquivo
     * @return File
     * @exception IllegalArgumentException
     */
    public static File getSoundFile(String fileName) {
        File soundFile = new File(fileName);
        if (!soundFile.exists() || !soundFile.isFile())
            throw new IllegalArgumentException("not a file: " + soundFile);
        return soundFile;
    }
}