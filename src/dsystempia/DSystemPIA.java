/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsystempia;

import Client.StartClient;
import Monitor.InfoControl;
import Monitor.InfomModel;
import Monitor.MonitorFrame;
import Server.StartServer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author MambaNegraPC
 */
public class DSystemPIA {

    public static void main(String[] args) throws SigarException, InterruptedException, IOException {

        //Seleccionamos un Servidor por default
        boolean isServer = false;
        
        while (true) {

            ArrayList<InfomModel> lista = new ArrayList<>();
            long latency = 0;
            boolean Conection = true;

            //Obtener información de la PC
            InfoControl IC = new InfoControl();

            String[] datosCliente = IC.getData(latency, Conection);
//            lista.add());

            if (isServer) {
                StartServer ss = new StartServer(isServer,datosCliente);
                ss.Start();
            }else{
                StartClient sc = new StartClient(datosCliente);
                isServer = sc.Start();
            }
            
            TimeUnit.SECONDS.sleep(10);
            
        }

//        IC.addData(IC.datosCliente);
    }

}
