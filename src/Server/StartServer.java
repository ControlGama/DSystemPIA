package Server;

import Monitor.InfoControl;
import Monitor.InfomModel;
import Monitor.MonitorFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import org.hyperic.sigar.SigarException;

public class StartServer {

    boolean isServer;
    ArrayList<InfomModel> lista = new ArrayList<>();
    String[] MisDatos = new String[16];

    public StartServer(boolean isServer) {
        this.isServer = isServer;
//        this.MisDatos = datosCliente; 
    }

    public String Start(String ipServer, String MyIp) throws IOException, SigarException, InterruptedException {

        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        Socket s = null;
        ServerSocket ss = new ServerSocket(5432);

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

        boolean comandos;

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

                // Ejecutar Comandos
                comandos = (boolean) ois.readObject();
                if (comandos) {
                    setComandos();
                }

                // leo el nombre que envía el cliente
                String[] datosCliente = (String[]) ois.readObject();

                //Revisar si un cliente tiene mejor Score que el mío
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

                addLista(lista, MisDatos);
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

        MyScore = Integer.parseInt(MisDatos[15]);
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

            Long lastConection = Long.parseLong(data.getLastConection());
            long time = System.currentTimeMillis() - lastConection;

            //Si en 30 Segundos no hay comunicación se marca como desconectado
            if (time >= 30000) {
                data.setConection("Off");
            } else {
                data.setConection("On");
            }

        }

    }

    private void setComandos() throws IOException, InterruptedException {

        String cmd = "cmd /c start \"\" \"C:\\Users\\ControlGama\\Desktop\\CPU.lnk\"";
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line = buf.readLine()) != null) {
            System.out.println(line);
        }

    }

}
