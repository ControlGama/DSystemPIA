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
    
    ArrayList<InfomModel> lista = new ArrayList<>();
    
    public StartClient(ArrayList<InfomModel> lista){
        this.lista = lista; 
                
    }
    
    public void Start() throws IOException{
        
        //Inicio de ejecución 
        long Inicioexec = System.currentTimeMillis();
                
        //Transferir información 
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Socket s = null;

        try {
            // instancio el server con la IP y el PORT
            s = new Socket("127.0.0.1", 5432);
            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());

            //Enviar Datos
            oos.writeObject(lista);
            
            // recibo la respuesta (el saludo personalizado)
            String ret = (String) ois.readObject();

            // muestro la respuesta que envio el server
            System.out.println(ret);
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

        //Obtenemos y guardamos tiempo de ejecución
        long Totalexec = System.currentTimeMillis() - Inicioexec;
        System.out.println("Tiempo ejecución:" + Totalexec);
    
    }
    
}
