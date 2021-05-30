package Server;

import Monitor.InfoControl;
import Monitor.InfomModel;
import Monitor.MonitorFrame;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.hyperic.sigar.SigarException;

public class StartServer {
    
    boolean isServer;
    ArrayList<InfomModel> lista = new ArrayList<>();
    String[] MisDatos = new String[13];
    
    public StartServer(boolean isServer,String[] datosCliente) {
        this.isServer = isServer;
        this.MisDatos = datosCliente; 
    }

    public String Start(String ipServer) throws IOException, SigarException {
        
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        Socket s = null;
        ServerSocket ss = new ServerSocket(5432);
        
        MonitorFrame nf = new MonitorFrame();
        nf.setVisible(true);
        
        InfoControl IC = new InfoControl();
        lista.add(IC.InfomModelData(MisDatos));
        nf.ShowData(lista);
        
        while (isServer) {
            try {
                
                String[] datosCliente = new String[13];

                // el ServerSocket me da el Socket
                s = ss.accept();
                // informacion en la consola
                System.out.println("Se conectaron desde la IP: " + s.getInetAddress());

                // enmascaro la entrada y salida de bytes
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());
                
                // leo el nombre que envia el cliente
                datosCliente = (String[]) ois.readObject();
                
                //Revisar si un cliente tiene mejorScore que el mio
                boolean flag = CheckScore(datosCliente[12]);
                
                if (flag){
                    isServer = false; //Paso a ser cliente
                    ipServer = s.getInetAddress().toString();
                }
                
                oos.writeObject(flag);
                oos.writeObject(ipServer);
                System.out.println("Datos Recibidos...");
                
                
                lista.add(IC.InfomModelData(datosCliente));
                nf.ShowData(lista);

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (s != null) {
                    s.close();
                }
                System.out.println("Conexion cerrada!");
            }
        }
        
        return ipServer;
    }

    private boolean CheckScore(String ScoreCliente) {
        boolean flag;
        int MyScore, ClientScore;
        
        MyScore = Integer.parseInt(MisDatos[12]);
        ClientScore = Integer.parseInt(ScoreCliente);
        
        if (ClientScore > MyScore ) {
            flag = true;
        }else{
            flag = false;
        }
        
        return flag;
        
    }

}
