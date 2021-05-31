/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

//import static dsystempia.DSystemPIA.lista;
import java.io.IOException;
import java.net.Socket;
import javax.swing.ImageIcon;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author MambaNegraPC
 */
public class InfoControl {

//    public String[] datosCliente;
//    Boolean conection;

    public InfoControl() {
//        this.latency = latency;
//        this.conection = conection;
    }

    public String[] getData(String ipServer, String MyIp) throws SigarException, IOException {
                
        //Obtener Datos Estáticos  
        String[] datosCliente = new String[16];

        Sigar sigar = new Sigar();
        String sSistemaOperativo = System.getProperty("os.name");

        String Modelo;
        Modelo = sigar.getCpuInfoList()[0].getModel();

        Integer Velocidad;
        Velocidad = sigar.getCpuInfoList()[0].getMhz();

        Long MemRam;
        MemRam = sigar.getMem().getTotal();

        String SO;
        SO = sSistemaOperativo;

        Long HHDTotal;
        HHDTotal = sigar.getFileSystemUsage("C:").getTotal();

        Integer usoCPU_nucleos;
        usoCPU_nucleos = sigar.getCpuInfoList()[0].getTotalCores();
        
        Long latency = 0L;
        String Modo;
        if (ipServer.equals(MyIp)){
            Modo = "Servidor";
        }else{
            Modo = "Cliente";
            latency = getLatency(ipServer);
        }
        
        Long lastConection = System.currentTimeMillis();
        
        datosCliente[0] = MyIp; // IP
        datosCliente[1] = Modo; //isServer;
        datosCliente[2] = "On"; //Conection();
        
        datosCliente[3] = lastConection.toString(); //Last Conection();
        
        datosCliente[4] = Modelo;
        datosCliente[5] = Velocidad.toString();
        datosCliente[6] = usoCPU_nucleos.toString();
        datosCliente[7] = MemRam.toString();
        datosCliente[8] = SO;
        datosCliente[9] = HHDTotal.toString();

        //Obtener Datos Dinámicos
        Long HHDFree;
        HHDFree = sigar.getFileSystemUsage("C:").getFree();

        Long MemRamDisp;
        MemRamDisp = sigar.getMem().getFree();

        Double MemRam_p;
        MemRam_p = sigar.getMem().getUsedPercent();

        Double usoCPU_p;
        usoCPU_p = sigar.getCpuPerc().getCombined() * 100;
        
        

        datosCliente[10] = HHDFree.toString();
        datosCliente[11] = MemRamDisp.toString();
        datosCliente[12] = MemRam_p.toString();
        datosCliente[13] = usoCPU_p.toString();
        datosCliente[14] = latency.toString();

        datosCliente[15] = getScore(Modelo, usoCPU_nucleos, Velocidad, MemRam_p, usoCPU_p, latency).toString();

        return datosCliente;
    }

    public InfomModel InfomModelData(String[] datos) throws SigarException {

        InfomModel info = new InfomModel(
                
                datos[0], //IP
                datos[1], //Flag Servidor
                datos[2], //Estado de conexion
                datos[3], //Ultima Conexion
                
                //Obtener Datos Estáticos 
                datos[4], //Modelo
                datos[5], //Velocidad
                datos[6], //Nucleos
                datos[7], //MemRam
                datos[8], //SO
                datos[9], //HHDTotal

                //Obtener Datos Dinámicos
                datos[10], //HHDFree
                datos[11], //MemRamDisp
                datos[12], //MemRam %
                datos[13], //cpu %
                datos[14], //Latencia
                datos[15] //Rank
        );

        return info;

    }

    private Integer getScore(String Modelo, int usoCPU_nucleos, Integer Velocidad, Double MemRam_p, Double usoCPU_p, Long latency) {
        Integer score = 0;

        //Modelo Rank
        switch (Modelo) {
            case "Ryzen 9 3900X 12-Core Processor":
                score = 1000;
                break;
            case "Core(TM) i5-7200U CPU @ 2.50GHz":
                score = 8000;
                break;
            case "B":
                score = 500;
                break;
            default:
                score = 0;
        }

        //Nucleos Rank
        switch (usoCPU_nucleos) {
            case 1:
                score = score + 50;
                break;
            case 2:
                score = score + 100;
                break;
            case 3:
                score = score + 150;
                break;
            case 4:
                score = score + 200;
                break;
            case 5:
                score = score + 250;
                break;
            case 6:
                score = score + 300;
                break;
            case 7:
                score = score + 350;
                break;
            case 8:
                score = score + 400;
                break;
            case 9:
                score = score + 450;
                break;
            case 10:
                score = score + 500;
                break;
            case 11:
                score = score + 550;
                break;
            case 12:
                score = score + 600;
                break;
            case 13:
                score = score + 650;
                break;
            case 14:
                score = score + 700;
                break;
            case 15:
                score = score + 750;
                break;
            case 16:
                score = score + 800;
                break;
            case 17:
                score = score + 850;
                break;
            case 18:
                score = score + 900;
                break;
            case 19:
                score = score + 950;
                break;
            case 20:
                score = score + 1000;
                break;
            case 21:
                score = score + 1050;
                break;
            case 22:
                score = score + 1100;
                break;
            case 23:
                score = score + 1150;
                break;
            case 24:
                score = score + 1200;
                break;
            default:
                score = score + 1500;
        }

        //Velocidad Rank
        if (isBetween(Velocidad, 1000, 1199)) {
            score = score + 100;
        } else if (isBetween(Velocidad, 1200, 1399)) {
            score = score + 200;
        } else if (isBetween(Velocidad, 1400, 1599)) {
            score = score + 300;
        } else if (isBetween(Velocidad, 1600, 1799)) {
            score = score + 400;
        } else if (isBetween(Velocidad, 1800, 1999)) {
            score = score + 500;
        } else if (isBetween(Velocidad, 2000, 2199)) {
            score = score + 600;
        } else if (isBetween(Velocidad, 2200, 2399)) {
            score = score + 700;
        } else if (isBetween(Velocidad, 2400, 2599)) {
            score = score + 800;
        } else if (isBetween(Velocidad, 2800, 2999)) {
            score = score + 900;
        } else if (isBetween(Velocidad, 3000, 3199)) {
            score = score + 1000;
        } else if (isBetween(Velocidad, 3200, 3399)) {
            score = score + 1100;
        } else if (isBetween(Velocidad, 3400, 3599)) {
            score = score + 1200;
        } else if (isBetween(Velocidad, 3600, 3799)) {
            score = score + 1300;
        } else if (isBetween(Velocidad, 3800, 3999)) {
            score = score + 1400;
        } else if (isBetween(Velocidad, 4000, 4199)) {
            score = score + 1500;
        } else if (isBetween(Velocidad, 4200, 4399)) {
            score = score + 1600;
        } else if (isBetween(Velocidad, 4400, 4599)) {
            score = score + 1700;
        } else if (isBetween(Velocidad, 4600, 4799)) {
            score = score + 1800;
        } else if (Velocidad >= 4800) {
            score = score + 2200;
        }

        //RAM Rank
        if (isBetween(MemRam_p, 0.0, 9.9)) {
            score = score + 2000;
        } else if (isBetween(MemRam_p, 10.0, 19.9)) {
            score = score + 1800;
        } else if (isBetween(MemRam_p, 20.0, 29.9)) {
            score = score + 1600;
        } else if (isBetween(MemRam_p, 30.0, 39.9)) {
            score = score + 1400;
        } else if (isBetween(MemRam_p, 40.0, 49.9)) {
            score = score + 1200;
        } else if (isBetween(MemRam_p, 50.0, 59.9)) {
            score = score + 1000;
        } else if (isBetween(MemRam_p, 60.0, 69.9)) {
            score = score + 800;
        } else if (isBetween(MemRam_p, 70.0, 79.9)) {
            score = score + 600;
        } else if (isBetween(MemRam_p, 80.0, 89.9)) {
            score = score + 400;
        } else if (isBetween(MemRam_p, 90.0, 100.0)) {
            score = score + 200;
        }

        //CPU Rank
        if (isBetween(usoCPU_p, 0.0, 9.9)) {
            score = score + 2000;
        } else if (isBetween(usoCPU_p, 10.0, 19.9)) {
            score = score + 1800;
        } else if (isBetween(usoCPU_p, 20.0, 29.9)) {
            score = score + 1600;
        } else if (isBetween(usoCPU_p, 30.0, 39.9)) {
            score = score + 1400;
        } else if (isBetween(usoCPU_p, 40.0, 49.9)) {
            score = score + 1200;
        } else if (isBetween(usoCPU_p, 50.0, 59.9)) {
            score = score + 1000;
        } else if (isBetween(usoCPU_p, 60.0, 69.9)) {
            score = score + 800;
        } else if (isBetween(usoCPU_p, 70.0, 79.9)) {
            score = score + 600;
        } else if (isBetween(usoCPU_p, 80.0, 89.9)) {
            score = score + 400;
        } else if (isBetween(usoCPU_p, 90.0, 100.0)) {
            score = score + 200;
        }

        //Latency Rank
        if (isBetween(latency, 0.00, 4.99)) {
            score = score + 2000;
        } else if (isBetween(latency, 5.0, 9.9)) {
            score = score + 1800;
        } else if (isBetween(latency, 10.0, 14.9)) {
            score = score + 1600;
        } else if (isBetween(latency, 15.0, 19.9)) {
            score = score + 1400;
        } else if (isBetween(latency, 20.0, 24.9)) {
            score = score + 1200;
        } else if (isBetween(latency, 25.0, 29.9)) {
            score = score + 1000;
        } else if (isBetween(latency, 30.0, 34.9)) {
            score = score + 800;
        } else if (isBetween(latency, 35.0, 39.9)) {
            score = score + 600;
        } else if (isBetween(latency, 40.0, 44.9)) {
            score = score + 400;
        } else if (isBetween(latency, 45.0, 50.9)) {
            score = score + 200;
        } else if (latency >= 51) {
            score = score + 10;
        }
        
        return score;
    }

    private boolean isBetween(int value, int ini, int fin) {
        return ini <= value && value <= fin;
    }

    private boolean isBetween(Double value, Double ini, Double fin) {
        return ini <= value && value <= fin;
    }

    private boolean isBetween(Long value, Double ini, Double fin) {
        return ini <= value && value <= fin;
    }

    private Long getLatency(String ipServer) throws IOException {

        Socket s = null;

        //Inicio de ejecución 
        long Inicioexec = System.currentTimeMillis();

        try {
            s = new Socket(ipServer, 5432);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
        }

        //Obtenemos y guardamos tiempo de ejecución
        long latency = System.currentTimeMillis() - Inicioexec;
//        System.out.println("Tiempo ejecución:" + Totalexec);
    
        return latency;
    }

}
