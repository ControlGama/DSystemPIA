package Server;

import Monitor.InfomModel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.hyperic.sigar.SigarException;

public class StartServer {
    
    boolean isServer;
    
    public StartServer(boolean isServer) {
        this.isServer = isServer;
    }

    public void Start() throws IOException, SigarException {

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        Socket s = null;
        ServerSocket ss = new ServerSocket(5432);

        while (isServer) {
            try {
                
                ArrayList<InfomModel> lista = new ArrayList<>();
                
                // el ServerSocket me da el Socket
                s = ss.accept();
                // informacion en la consola
                System.out.println("Se conectaron desde la IP: " + s.getInetAddress());

                // enmascaro la entrada y salida de bytes
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());
                
                // leo el nombre que envia el cliente
                lista = (ArrayList<InfomModel>) ois.readObject();

                oos.writeObject("Listo..");
                System.out.println("Datos Recibidos...");

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
    }

}
