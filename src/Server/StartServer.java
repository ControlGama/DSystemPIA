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
    String[] MisDatos = new String[15];

    public StartServer(boolean isServer) {
        this.isServer = isServer;
//        this.MisDatos = datosCliente; 
    }

    public String Start(String ipServer, String MyIp) throws IOException, SigarException {

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        Socket s = null;
        ServerSocket ss = new ServerSocket(5431);

        InfoControl IC = new InfoControl();
        MisDatos = IC.getData(ipServer, MyIp);
        lista.add(IC.InfomModelData(MisDatos));

        //Iniciar Monitor
        MonitorFrame nf = new MonitorFrame();
        nf.setVisible(true);
        nf.ShowData(lista);

        //Mejor segundo Rank;
        Integer SecondBestScore = 0;
        String BackupIP = "";

        while (isServer) {
            try {

                // el ServerSocket me da el Socket
                s = ss.accept();

                //Con cada petición actualizo mis datos para evaluar rank
                MisDatos = IC.getData(ipServer, MyIp);

                // informacion en la consola
                System.out.println("Se conectaron desde la IP: " + s.getInetAddress());

                // enmascaro la entrada y salida de bytes
                ois = new ObjectInputStream(s.getInputStream());
                oos = new ObjectOutputStream(s.getOutputStream());

                // leo el nombre que envia el cliente
                String[] datosCliente = (String[]) ois.readObject();

                //Revisar si un cliente tiene mejorScore que el mio
                boolean flag = CheckScore(datosCliente[14]);
                BackupIP = GetBackupIP(datosCliente[0], BackupIP, datosCliente[14], SecondBestScore);

                if (flag) {
                    nf.setVisible(false); //Cerramos Monitor
                    isServer = false; //Paso a ser cliente
                    ipServer = s.getInetAddress().toString().replace("/", "");
                }

                oos.writeObject(flag);
                oos.writeObject(ipServer);
                oos.writeObject(BackupIP);
                System.out.println("Datos Recibidos...");

                addLista(lista, datosCliente);

                //Revisar estado de conexion de los clientes
                getStatusConection(lista);

//                lista.add(IC.InfomModelData(datosCliente));
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

        if (ClientScore > MyScore) {
            flag = true;
        } else {

            flag = false;
        }

        return flag;

    }

    private void addLista(ArrayList<InfomModel> lista, String[] datosCliente) throws SigarException {

        for (int i = 0; i < lista.size(); i++) {
            InfomModel IP = lista.get(i);

            if (IP.getIP().equals(datosCliente[0])) {
                lista.remove(i);
            }
        }

        InfoControl IC = new InfoControl();
        lista.add(IC.InfomModelData(datosCliente));
    }

    private String GetBackupIP(String ClientIp, String BackupIP, String ScoreCliente, Integer SecondBestScore) {
        String IP = "";
        int Score = Integer.parseInt(ScoreCliente);

        if (Score > SecondBestScore) {
            SecondBestScore = Score;
            IP = ClientIp;
        } else {
            IP = BackupIP;
        }

        return IP;
    }

    private void getStatusConection(ArrayList<InfomModel> lista) throws IOException {

        boolean onOff;

        for (int i = 0; i < lista.size(); i++) {
            InfomModel data = lista.get(i);

            onOff = CheckConection(data.getIP());

            if (onOff) {
                data.setConection("On");
            } else {
                data.setConection("Off");
            }

        }

    }

    private boolean CheckConection(String ipServer) throws IOException {

        boolean onOff = false;
        Socket PinSocket = null;

        try {
            PinSocket = new Socket(ipServer, 5432);
            onOff = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            onOff = false;

        } finally {
            if (PinSocket != null) {
                PinSocket.close();
            }
        }

        return onOff;
    }

}
