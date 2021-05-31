/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor;

//import static Server.StartServer.lista;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author MambaNegraPC
 */
public class MonitorFrame extends javax.swing.JFrame {
   
    public MonitorFrame() {
        initComponents();
        
    }
    
    public void ShowData(ArrayList<InfomModel> lista) {
        
        String data[][] = new String[lista.size()][15];
                
        for (int i = 0; i < lista.size(); i++) {
            
            //Conexión
            data[i][0] = lista.get(i).IP;
            data[i][1] = lista.get(i).isServer;
            data[i][2] = lista.get(i).Conection;
            
            //Obtener Datos Estáticos
            data[i][3] = lista.get(i).Modeloprocesador;
            data[i][4] = lista.get(i).Velocidadprocesador;
            data[i][5] = lista.get(i).Nucleos;
            data[i][6] = lista.get(i).RAMTotal;
            data[i][7] = lista.get(i).SistemaOperativo;
            data[i][8] = lista.get(i).HHDTotal;
            
            //Obtener Datos Dinámicos
            data[i][9] = lista.get(i).HHDLibre;
            data[i][10] = lista.get(i).MemRamDisp;
            data[i][11] = lista.get(i).MemRam_p;
            data[i][12] = lista.get(i).usoCPU_p;
            data[i][13] = lista.get(i).Latency;
            data[i][14] = lista.get(i).Rank;
            
        } 
        
        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
               data,
                new String[]{
                    "Ip",
                    "Modo",
                    "Conexión",
                    
                    "Modelo procesador",
                    "Velocidad del procesador",
                    "Nucleos",
                    "RAM Total",
                    "Sistema operativo",
                    "HHD Total",
                    
                    "HHD Libre",
                    "RAM disponible",
                    "RAM %",
                    "CPU %",
                    "Latencia",
                    "Score"
                    
                }
        ));
        
    }
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "IP", "Modo", "Conexión", "Modelo Procesador", "Velocidad del procesador", "Nucleos", "RAM Total", "Sistema operativo", "HHD Total", "HHD Libre", "RAM Disponible", "RAM %", "CPU %", "Latencia", "Rank"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDatos.setDragEnabled(true);
        jScrollPane1.setViewportView(jTableDatos);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Monitor de puntuación");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable jTableDatos;
    // End of variables declaration//GEN-END:variables

}
