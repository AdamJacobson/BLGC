package GUI;

import GUI.contoller.SaveController;
import java.util.Observable;
import java.util.Observer;

/** The main frame for the application */
public class BLGCMainFrame extends javax.swing.JFrame implements Observer
{
    /** The Model that will be initialized. */
    private Model model;
    
    private SaveController saveController;

    /** Creates new form BLGCMainFrame */
    public BLGCMainFrame()
    {
        System.out.println("The next line should say 'Printing Check Okay'. If not, problems.");    
        model = new Model();
        saveController = new SaveController();
        
        saveController.addObserver(this);

        initComponents();
       
        primaryWeaponTabPanel.setSaveController(saveController);
        secondaryWeaponTabPanel.setSaveController(saveController);
        armorTabPanel.setSaveController(saveController);
        
        primaryWeaponTabPanel.setPanelIDNumberAndPrimacy(0, true);
        secondaryWeaponTabPanel.setPanelIDNumberAndPrimacy(2, false);
        armorTabPanel.setPanelIDNumber(0);
        
        primaryWeaponTabPanel.setModel(model);
        secondaryWeaponTabPanel.setModel(model);
        armorTabPanel.setModel(model);
    }

    /**
     * @param obs The observable
     * @param obj The object
     */
    @Override
    public void update(Observable obs, Object obj)
    {
        enableMainFrame(saveController.getEnableMainFrame());
    }
    
    /**
     * Starting at this level in the GUI hierarchy, enable/disable each individual
     * component then move down a level and continue.
     */
    private void enableMainFrame(boolean enable)
    {
        this.setEnabled(enable);
        mainTabbedPanel.setEnabled(enable);
        
        armorTabPanel.enableAllSubparts(enable);
        primaryWeaponTabPanel.enableAllSubparts(enable);
        secondaryWeaponTabPanel.enableAllSubparts(enable);
    }

    /** This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        mainTabbedPanel = new javax.swing.JTabbedPane();
        primaryWeaponTabPanel = new GUI.WeaponTabPanel();
        secondaryWeaponTabPanel = new GUI.WeaponTabPanel();
        armorTabPanel = new GUI.ArmorTabPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BLGC");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName(""); // NOI18N

        mainTabbedPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 2, true));
        mainTabbedPanel.addTab("Primary Weapon", primaryWeaponTabPanel);
        mainTabbedPanel.addTab("Secondary Weapon", secondaryWeaponTabPanel);
        mainTabbedPanel.addTab("Armor", armorTabPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainTabbedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Main method.
     *
     * @param args Command line arguments. */
    public static void main(final String[] args)
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BLGCMainFrame.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BLGCMainFrame.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BLGCMainFrame.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BLGCMainFrame.class.getName()).
                    log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new BLGCMainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private GUI.ArmorTabPanel armorTabPanel;
    private javax.swing.JTabbedPane mainTabbedPanel;
    private GUI.WeaponTabPanel primaryWeaponTabPanel;
    private GUI.WeaponTabPanel secondaryWeaponTabPanel;
    // End of variables declaration//GEN-END:variables
}