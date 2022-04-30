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

public class JEncryptRSA {
    
    public static void main(String[] args){
       
        try{
            //input message
            Scanner input = new Scanner(System.in);                             //scanner input
            System.out.println("RSA - Enter the message (No body can see me): ");     
            String inputMessage;                                                //input message string
            inputMessage = input.nextLine();                                    //scanner message set to input message
            byte [] plaintext = inputMessage.getBytes();                        //convert the input messaage to bytes
            System.out.println("Plaintext in bytes: " + (plaintext));           //output plaintext in bytes
            input.close();                                                      //close scanner
            
            //rsa keys
            KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");          //key pair generator
            KeyPair key = kg.generateKeyPair();                                 //rsa keys
            Key privateKey = key.getPrivate();                                  //private key
            Key publicKey = key.getPublic();                                    //public key
            
            
            //rsa cipher
            Cipher ciphRSA = Cipher.getInstance("RSA");                         //rsa cipher object creation

            //encrypt
            ciphRSA.init(Cipher.ENCRYPT_MODE, publicKey);                       //initialize the cipher to encrypt using the public key
            byte [] encrypted = ciphRSA.doFinal(plaintext);                     //converts the plaintext bytes into encrypted bytes
            String strE = new String(encrypted);                                //converts encrypted bytes into string
            System.out.println("Encrypted: " + strE);          

            //decrypt
            ciphRSA.init(Cipher.DECRYPT_MODE, privateKey);                      //initialize the cipher to decrypt using the private key
            byte [] decrypted = ciphRSA.doFinal(encrypted);                     //converts encrypted bytes into decrypted bytes
            String strD = new String(decrypted);                                //converts decrypted bytes into string
            System.out.println("Decrypted: " + strD);          
        }
        
        //exception catch
        catch(Exception e){
            System.out.println("Exception");       
        }
    }  
}

     
            
            

