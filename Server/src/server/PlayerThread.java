/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Santi
 */
public class PlayerThread extends Thread {

    Socket sk;
    
    int respuestaCliente;
    
    int numeroIA;

    public PlayerThread(Socket sk) {
        this.sk = sk;
    }

    @Override
    public void run() {
        
        InputStream is = null;
        OutputStream os = null;
        try {
            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();
            
            while(true){
                
                String linea = br.readLine();
                System.out.println(linea);
                
                numeroIA = (int) (Math.random() * 3) + 1;
                System.out.println("Num maquina: "+numeroIA);
                
                if(linea.equals("piedra")){
                     respuestaCliente = 1;
                }
                if(linea.equals("papel")){
                     respuestaCliente = 2;
                }
                if(linea.equals("tijera")){
                     respuestaCliente = 3;
                }
                
                if(respuestaCliente == 1 && numeroIA ==3 || respuestaCliente == 2 && numeroIA ==1 || respuestaCliente == 3 && numeroIA ==2){
                    bw.write("Ganaste");
                    bw.newLine();
                    bw.flush();
                }else if(respuestaCliente == 1 && numeroIA ==2 || respuestaCliente == 2 && numeroIA ==3 || respuestaCliente == 3 && numeroIA ==1){
                    bw.write("Perdiste");
                    bw.newLine();
                    bw.flush();
                }else{
                    bw.write("Empatasteis");
                    bw.newLine();
                    bw.flush();
                }
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(is != null) is.close();
            } catch (IOException ex) {
                Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
