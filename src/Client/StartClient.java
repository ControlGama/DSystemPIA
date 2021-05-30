/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Monitor.InfomModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.hyperic.sigar.Sigar;

/**
 *
 * @author MambaNegraPC
 */
public class StartClient {
    
    String[] datosCliente = new String[13];
    String ipServer;
    
    
    public StartClient(String ipServer, String[] datosCliente){
        this.datosCliente = datosCliente;
        this.ipServer = ipServer;
    }
    
    public boolean Start() throws IOException{
        
        boolean flag = false;
                        
        //Transferir información 
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket s = null;

        try {
            // instancio el server con la IP y el PORT
            s = new Socket(ipServer, 5432);
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());

            //Enviar Datos
            oos.writeObject(datosCliente);
            
            // Soy el nuevo Server?
            flag = (boolean) ois.readObject();
            
            // Cual es la ip del server?
            ipServer = (String) ois.readObject();
            
            // muestro la respuesta que envio el server
            System.out.println(flag);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
            if (s != null) {
                s.close();
            }
        }

        return flag;
    
    }
    
}
