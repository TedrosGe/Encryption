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

public class JEncryptDES {

    public static void main(String[] args){
       
        try{
            //input message
            Scanner input = new Scanner(System.in);                             //scanner input
            System.out.println("DES - Enter the message (No body can see me): ");     
            String inputMessage;                                                //input message string
            inputMessage = input.nextLine();                                    //scanner message set to input message
            byte [] plaintext = inputMessage.getBytes();                        //convert the input messaage to bytes
            System.out.println("Plaintext in bytes: " + (plaintext));           //output plaintext in bytes
            input.close();                                                      //close scanner
            
            //des key
            KeyGenerator kg  = KeyGenerator.getInstance("DES");                 //key generator
            SecretKey key = kg.generateKey();                                   //des secret key
            
            //des cipher
            Cipher ciphDES = Cipher.getInstance("DES");                         //des cipher object creation

            //encrypt
            ciphDES.init(Cipher.ENCRYPT_MODE,key);                              //initialize the cipher to encrypt using the key
            byte [] encrypted = ciphDES.doFinal(plaintext);                     //converts plaintext bytes into encrypted bytes 
            String strE = new String(encrypted);                                //converts encrypted bytes into string
            System.out.println("Encrypted: " + strE);                                  

            //decrypt
            ciphDES.init(Cipher.DECRYPT_MODE,key);                              //initialize the cipher to decrypt using the key
            byte [] decrypted = ciphDES.doFinal(encrypted);                     //converts encrypted bytes into decrypted bytes
            String strD = new String(decrypted);                                //converts decrypted bytes into string
            System.out.println("Decrypted: " + strD);          
        }
        
        //exception catch
        catch(Exception e){
            System.out.println("Exception");
        }
    }  
}
