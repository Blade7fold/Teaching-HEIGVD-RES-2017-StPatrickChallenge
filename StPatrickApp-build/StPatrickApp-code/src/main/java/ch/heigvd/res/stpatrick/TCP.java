/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.res.stpatrick;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nathan
 */
public class TCP {
    
    private static final Logger LOG = Logger.getLogger(TCP.class.getName());
    Socket clientSocket = null;
    OutputStream os = null;
    InputStream is = null;
    private final int BUFFER_SIZE = 256;
    
    try {
        try {
            clientSocket = new Socket("www.heig-vd.ch", 80);
        } catch (IOException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        os = clientSocket.getOutputStream();
        is = clientSocket.getInputStream();
        
        String malformedHttpRequest = "Hello, sorry, but I don't speak HTTP...\r\n\r\n";
        try {
            os.write(malformedHttpRequest.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int newBytes;
        
        try {
            while ((newBytes = is.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
            }
        } catch (IOException ex) {
            Logger.getLogger(TCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG.log(Level.INFO, "Response sent by the server: ");
        LOG.log(Level.INFO, responseBuffer.toString());
    } catch (IOException ex) {
        LOG.log(Level.SEVERE, null, ex);
    } finally {
        
    }
}
