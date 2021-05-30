/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

import javax.swing.Icon;

/**
 *
 * @author MambaNegraPC
 */
public class InfomModel {

    String Conection;

    //Obtener Datos Estáticos 
    String Modeloprocesador;
    String Velocidadprocesador;
    String Generacionprocesador;
    String RAMTotal;
    String SistemaOperativo;
    String HHDTotal;

    //Obtener Datos Dinámicos
    String HHDLibre;
    String MemRamDisp;
    String MemRam_p;
    String usoCPU_p;
    String Latency;
    String Score;

    public InfomModel(
            String Conection,
            String Modeloprocesador,
            String Velocidadprocesador,
            String Generacionprocesador,
            String RAMTotal,
            String SistemaOperativo,
            String HHDTotal,
            String HHDLibre,
            String MemRamDisp,
            String MemRam_p,
            String usoCPU_p,
            String Latency,
            String Score
    ) {
        
        this.Conection = Conection;
        
        //Obtener Datos Estáticos
        this.Modeloprocesador = Modeloprocesador;
        this.Velocidadprocesador = Velocidadprocesador;
        this.Generacionprocesador = Generacionprocesador;
        this.RAMTotal = RAMTotal;
        this.SistemaOperativo = SistemaOperativo;
        this.HHDTotal = HHDTotal;

        //Obtener Datos Dinámicos
        this.HHDLibre = HHDLibre;
        this.MemRamDisp = MemRamDisp;
        this.MemRam_p = MemRam_p;
        this.usoCPU_p = usoCPU_p;
        this.Latency = Latency;
        this.Score = Score;

    }

    public String getConection() {
        return Conection;
    }

    public void setConection(String Conection) {
        this.Conection = Conection;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public String getMemRamDisp() {
        return MemRamDisp;
    }

    public void setMemRamDisp(String MemRamDisp) {
        this.MemRamDisp = MemRamDisp;
    }

    public String getMemRam_p() {
        return MemRam_p;
    }

    public void setMemRam_p(String MemRam_p) {
        this.MemRam_p = MemRam_p;
    }

    public String getUsoCPU_p() {
        return usoCPU_p;
    }

    public void setUsoCPU_p(String usoCPU_p) {
        this.usoCPU_p = usoCPU_p;
    }

    public String getLatency() {
        return Latency;
    }

    public void setLatency(String Latency) {
        this.Latency = Latency;
    }

    public String getModeloprocesador() {
        return Modeloprocesador;
    }

    public void setModeloprocesador(String Modeloprocesador) {
        this.Modeloprocesador = Modeloprocesador;
    }

    public String getVelocidadprocesador() {
        return Velocidadprocesador;
    }

    public void setVelocidadprocesador(String Velocidadprocesador) {
        this.Velocidadprocesador = Velocidadprocesador;
    }

    public String getGeneracionprocesador() {
        return Generacionprocesador;
    }

    public void setGeneracionprocesador(String Generacionprocesador) {
        this.Generacionprocesador = Generacionprocesador;
    }

    public String getRAMTotal() {
        return RAMTotal;
    }

    public void setRAMTotal(String RAMTotal) {
        this.RAMTotal = RAMTotal;
    }

    public String getSistemaOperativo() {
        return SistemaOperativo;
    }

    public void setSistemaOperativo(String SistemaOperativo) {
        this.SistemaOperativo = SistemaOperativo;
    }

    public String getHHDTotal() {
        return HHDTotal;
    }

    public void setHHDTotal(String HHDTotal) {
        this.HHDTotal = HHDTotal;
    }

    public String getHHDLibre() {
        return HHDLibre;
    }

    public void setHHDLibre(String HHDLibre) {
        this.HHDLibre = HHDLibre;
    }

}