/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.*;
import javax.crypto.*;
import java.security.*;
/**
 *
 * @authors:
 *  Tedros Gebrenegus - (section 4)
 */
public class Server {
    
    public static void main(String[] args) {
        
        try{
            System.out.println("SERVER SIDE: ");
            
            //socket server
            int port = 2000;
            ServerSocket sSocket = new ServerSocket(port);
            Socket server = sSocket.accept();
            
            //receiving message 1 from A
            DataInputStream input = new DataInputStream(server.getInputStream());
            String clientID = input.readUTF();
            System.out.println("The received Message 1: " +clientID);
            
            //des key
            String km = "NETWORK SECURITY";
            byte[] kmBytes = km.getBytes();
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance("DES");
            SecretKey key = secretKey.generateSecret(new DESKeySpec(kmBytes));
       
            String idB = "RESPONDER B";
            String ks = "RYERSON ";
            String plainText = ks + "| " + clientID + "| " + idB;
            byte[] outEncrypted = plainText.getBytes();
            Cipher ciphDES = null;
            ciphDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
            
            //B sending encrypted message 2 to A
            ciphDES.init(Cipher.ENCRYPT_MODE, key);
            outEncrypted = ciphDES.doFinal(outEncrypted);
            System.out.println("The ciphertext of message 2: "+ new String(outEncrypted));
            DataOutputStream output = new DataOutputStream(server.getOutputStream());
            output.writeInt(outEncrypted.length);
            output.write(outEncrypted);
            
            //B receiving message 3 from A
            int duration = input.readInt();
            byte[] inEncrypted = null;
            if(duration > 0) inEncrypted = new byte[duration];
            input.read(inEncrypted, 0, duration);
            System.out.println("The received CipherText of message 3: " + new String(inEncrypted));
 
            //decrypting message 3
            kmBytes = ks.getBytes();
            secretKey = SecretKeyFactory.getInstance("DES");
            key = secretKey.generateSecret(new DESKeySpec(kmBytes));
            ciphDES.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = ciphDES.doFinal(inEncrypted);
            System.out.println("The decrypted message 3: " + new String(decrypted));
            
            input.close();
            output.close();
            server.close();
        }
        
        //catch exception
        catch(Exception e){
            System.out.println("Exception");   
        }
    }
}
