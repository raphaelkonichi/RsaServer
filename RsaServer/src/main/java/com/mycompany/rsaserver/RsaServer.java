/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rsaserver;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.SecureRandom;

/**
 *
 * @author rapha
 */
public class RsaServer {

    public static void main(String[] args) throws IOException
    {
        DatagramSocket ds = new DatagramSocket(4445);
        byte[] receive = new byte[65535];
  
        DatagramPacket DpReceive = null;
        
        String msgdecifrada = "";
        BigInteger n, d, e;
        int bitlen = 4096;

        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r); 
        BigInteger q = new BigInteger(bitlen / 2, 100, r); 

        n = p.multiply(q);

        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        e = new BigInteger("3");
        while(m.gcd(e).intValue() > 1) e = e.add(new BigInteger("2"));

        d = e.modInverse(m);
                
        System.out.println("Mesagem decifrada: " +msgdecifrada);
        while (true)
        {  
            DpReceive = new DatagramPacket(receive, receive.length);
  
            ds.receive(DpReceive);
  
            
            msgdecifrada = new String(new BigInteger(data(receive).toString()).modPow(d, n).toByteArray());
            System.out.println("Cliente: " + msgdecifrada);
  
            if (msgdecifrada.equals("fechar"))
            {
                System.out.println("Cliente enviou fechar.....FECHANDO");
                break;
            }
            receive = new byte[65535];
        }
    }
  
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
