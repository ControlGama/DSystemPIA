package dsystempia;

import Client.StartClient;
import Server.StartServer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.hyperic.sigar.SigarException;

public class DSystemPIA {

    public static void main(String[] args) throws SigarException, InterruptedException, IOException {

        //Seleccionamos un Servidor por default
        boolean isServer = true;
        String ipServer = "25.12.245.237";
        String MyIp = "25.12.245.237";
        
        while (true) {

            if (isServer) { 
                StartServer ss = new StartServer(isServer);
                ipServer = ss.Start(ipServer,MyIp);
                isServer = false; //Si salió del método Start quiere decir que ya no es server
            }else{
                StartClient sc = new StartClient(ipServer);
                isServer = sc.Start(MyIp);
            }
            
            TimeUnit.SECONDS.sleep(10);
            
        }

    }

}
