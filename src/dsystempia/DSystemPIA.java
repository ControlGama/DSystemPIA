/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsystempia;

import Monitor.InfoControl;
import Monitor.InfomModel;
import Monitor.MonitorFrame;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author MambaNegraPC
 */
public class DSystemPIA {

    public static void main(String[] args) throws SigarException, InterruptedException {

        MonitorFrame nf = new MonitorFrame();
        nf.setVisible(true);
        
        while (true) {

            ArrayList<InfomModel> lista = new ArrayList<>();
            long latency = 0;
            boolean Conection = true;

            //Obtener información de la PC
            InfoControl IC = new InfoControl(latency, Conection);
            lista.add(IC.InfomModelData(IC.getData()));

            //Seleccionamos un Servidor por default
            boolean isServer = true;

            nf.ShowData(lista);
            
            TimeUnit.SECONDS.sleep(10);
            
        }

//        IC.addData(IC.datosCliente);
    }

}
