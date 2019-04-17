
package Viewer;


import Model.LawnMower;
import Model.SimulationMonitor;

public class MainPanel extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     *
     * @param simulationMonitor
     */
    public MainPanel(SimulationMonitor simulationMonitor) {
        this.simulationMonitor = simulationMonitor;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        btnPanel = new javax.swing.JPanel();
        nextBtn = new javax.swing.JButton();
        stopBtn = new javax.swing.JButton();
        forwardBtn = new javax.swing.JButton();
        txtPanel1 = new javax.swing.JPanel();
        currentState = new java.awt.Label();
        txtPanel2 = new javax.swing.JPanel();
        grassState = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        currentLawn = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentLawnPanel = new LawnPanel(600, 600, simulationMonitor.getLawn().getWidth(), simulationMonitor.getLawn().getHeight(), simulationMonitor.getLawn(), 5, 20, 12);
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nextBtn.setText("Next");
        nextBtn.setActionCommand("");
        nextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBtnActionPerformed(evt);
            }
        });

        stopBtn.setText("Stop");
        stopBtn.setActionCommand("");
        stopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopBtnActionPerformed(evt);
            }
        });

        forwardBtn.setText("Fast-Forward");
        forwardBtn.setActionCommand("");
        forwardBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnPanelLayout = new javax.swing.GroupLayout(btnPanel);
        btnPanel.setLayout(btnPanelLayout);
        btnPanelLayout.setHorizontalGroup(
                btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(btnPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(nextBtn)
                                .addGap(28, 28, 28)
                                .addComponent(stopBtn)
                                .addGap(31, 31, 31)
                                .addComponent(forwardBtn))
        );
        btnPanelLayout.setVerticalGroup(
                btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(btnPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(btnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(nextBtn)
                                        .addComponent(stopBtn)
                                        .addComponent(forwardBtn)))
        );

        currentState.setText("# of turns");

        javax.swing.GroupLayout txtPanel1Layout = new javax.swing.GroupLayout(txtPanel1);
        txtPanel1.setLayout(txtPanel1Layout);
        txtPanel1Layout.setHorizontalGroup(
                txtPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(txtPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(currentState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(55, Short.MAX_VALUE))
        );
        txtPanel1Layout.setVerticalGroup(
                txtPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, txtPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(currentState, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        grassState.setText("# grass cut and remaining");

        javax.swing.GroupLayout txtPanel2Layout = new javax.swing.GroupLayout(txtPanel2);
        txtPanel2.setLayout(txtPanel2Layout);
        txtPanel2Layout.setHorizontalGroup(
                txtPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(txtPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(grassState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        txtPanel2Layout.setVerticalGroup(
                txtPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(txtPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(grassState, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
        );


        //====================
        // mower status
        // TODO: add an update function to update the Mower status
        currentLawn.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {"", null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                },
                new String[]{
                        "Mower ID", "State", "Next Action", "# of remaining turns"
                }
        ));
        currentLawn.setAutoscrolls(false);
        currentLawn.setRowHeight(24);
        jScrollPane1.setViewportView(currentLawn);
        currentLawn.getAccessibleContext().setAccessibleParent(currentLawn);


        // mower status
        //====================

        currentLawnPanel.setAutoscrolls(false);
        jScrollPane2.setViewportView(currentLawnPanel);

        jLabel1.setText("Mower State");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(153, 153, 153)
                                                .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(94, 94, 94)
                                                .addComponent(txtPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(89, 89, 89)
                                                .addComponent(txtPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(51, 51, 51)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(51, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(218, 218, 218))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(btnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private void stopBtnActionPerformed(java.awt.event.ActionEvent evt) {
        LawnMower[] mowerList = simulationMonitor.getMowerList();
        for (int i = 0; i < mowerList.length; i++) {
            mowerList[i].setCurrentStatus(MowerStatus.turnedOff);
        }
        // TODO: get an alert window to say Simulation stopped?
    }

    private void forwardBtnActionPerformed(java.awt.event.ActionEvent evt) {
        while (simulationMonitor.issimulationOn()) {
            simulationMonitor.runOneTurn();
            currentLawnPanel.update(simulationMonitor.getLawn().getWidth(), simulationMonitor.getLawn().getHeight(), simulationMonitor.getLawn());
        }
    }

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {
        simulationMonitor.runOneTurn();
        currentLawnPanel.update(simulationMonitor.getLawn().getWidth(), simulationMonitor.getLawn().getHeight(), simulationMonitor.getLawn());
    }


    // Variables declaration - do not modify
    private javax.swing.JPanel btnPanel;
    private javax.swing.JTable currentLawn;
    private LawnPanel currentLawnPanel;
    private java.awt.Label currentState;
    private javax.swing.JButton forwardBtn;
    private java.awt.Label grassState;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton stopBtn;
    private javax.swing.JPanel txtPanel1;
    private javax.swing.JPanel txtPanel2;
    // End of variables declaration
    private SimulationMonitor simulationMonitor;
}
