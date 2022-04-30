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
public class Client {
    
    public static void main(String[] args) {
       
        try{
            System.out.println("CLIENT SIDE: ");
            
            //socket client
            String host = "localhost";                                          //host
            int port = 2000;                                                    //port
            Socket client = new Socket(host, port);                             //socket creation
            
            //A sending message 1 to B
            String idA = "INITIATOR A";                                         //idA string
            DataOutputStream output = new DataOutputStream(client.getOutputStream()); //output stream created
            output.writeUTF(idA);                                               //writes string to output stream
            System.out.println("The cleartext of message 1 " + idA);            //print completed action
            
            //km des key
            String km = "NETWORK SECURITY";
            byte[] kmBytes =  km.getBytes();
            SecretKeyFactory secretKey = SecretKeyFactory.getInstance("DES");
            SecretKey key = secretKey.generateSecret(new DESKeySpec(kmBytes));
            
            //A receiving message 2 from B
            DataInputStream input = new DataInputStream(client.getInputStream());
            int duration = input.readInt();
            byte[] inEncrypted = null;
            if(duration > 0) inEncrypted = new byte[duration];
            input.read(inEncrypted, 0, duration);
            System.out.println("The received ciphertext of message 2: " +new String(inEncrypted));
            
            //decrypt  message 2
            Cipher ciphDES = null;
            ciphDES = Cipher.getInstance("DES/ECB/PKCS5Padding");
            ciphDES.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = null;
            decrypted = ciphDES.doFinal(inEncrypted);
            System.out.println("The decrypted message 2: " + new String(decrypted));
            
            //A sends message 3 to B
            String decryptedText = new String(decrypted);
            String[] decryptedArray = decryptedText.split("\\|");
            kmBytes =  (decryptedArray[0]+ " ").getBytes();
            secretKey = SecretKeyFactory.getInstance("DES");
            key = secretKey.generateSecret(new DESKeySpec(kmBytes));
            
            byte[] outEncrypted = decryptedArray[2].getBytes();
            ciphDES.init(Cipher.ENCRYPT_MODE, key);
            outEncrypted = ciphDES.doFinal(outEncrypted);
            
            output.writeInt(outEncrypted.length);
            output.write(outEncrypted);
            
            input.close();
            output.close();
            client.close();   
        }
        
        //exception catch
        catch(Exception e){
            System.out.println("Exception"); 
        }
    }
}