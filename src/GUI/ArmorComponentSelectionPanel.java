package GUI;

import Parts.StandardDefinitions;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * The panel containing the combo boxes from which armor parts are selected.
 * Contains 3 combo boxes for Helmet, Body Armor and Leg Armor
 */
public class ArmorComponentSelectionPanel extends javax.swing.JPanel {

    /**
     * A copy of the StandardDefintions class for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();

    /**
     * The pointer to the Model that the program will use. Is initialized in
     * BLGCMainFrame.java and passed down through its children.
     */
    private Model model;

    /**
     * The number used to refer to which armor set this panel makes changes to.
     * Also, links this panel to the appropriate ArmorStatsPanel
     */
    private int panelIDNumber;
    
    /**
     * Constructor
     */
    public ArmorComponentSelectionPanel()
    {
        initComponents();
    }
    
    public void enableAllSubparts(boolean freeze)
    {
        bodyArmorComboBox.setEnabled(freeze);
        bodyArmorLabel.setEnabled(freeze);
        bodyArmorPreviewButton.setEnabled(freeze);
        helmetComboBox.setEnabled(freeze);
        helmetLabel.setEnabled(freeze);
        helmetPreviewButton.setEnabled(freeze);
        jPanel1.setEnabled(freeze);
        legArmorComboBox.setEnabled(freeze);
        legArmorLabel.setEnabled(freeze);
        legArmorPreviewButton.setEnabled(freeze);
    }
    
    public String getArmorSaveData()
    {
        StringBuilder returnString = new StringBuilder();

        if (model.getWeapon(panelIDNumber).isACompleteWeapon()) {
            returnString.append("armor, ");
            returnString.append(helmetComboBox.getSelectedIndex() + ", ");
            returnString.append(bodyArmorComboBox.getSelectedIndex() + ", ");
            returnString.append(legArmorComboBox.getSelectedIndex());
        }

        return returnString.toString();
    }

    /**
     * Creates a pointer the the model used. Also populates the combo boxes
     * for the first time.
     * 
     * @param m The model
     */
    public void setModel(Model m)
    {
        model = m;
        populateAllComboBoxes();
    }
    
    /**
     * Sets the ID Number for this panel. 
     * 
     * @param id The ID Number
     */
    public void setPanelIDNumber(int id)
    {
        panelIDNumber = id;
    }

    /**
     * Populate all combo boxes.
     */
    private void populateAllComboBoxes()
    {
        populateComboBox(helmetComboBox, sd.HELMET);
        populateComboBox(bodyArmorComboBox, sd.BODY_ARMOR);
        populateComboBox(legArmorComboBox, sd.LEG_ARMOR);
    }

    /**
     * Populate a given comboBox object with a list of independent parts given
     * by a partID as defined in StandardDefinitions.java
     *
     * @param comboBox A JComboBox to be populated.
     * @param partID The partId as defined in StandardDefinitions
     */
    private void populateComboBox(JComboBox comboBox, int partID)
    {
        String[] stringList;
        stringList = model.getInventory().getStringListOfIndependentParts(partID);

        comboBox.setModel(new DefaultComboBoxModel(stringList));

        if (stringList.length > 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        helmetLabel = new javax.swing.JLabel();
        bodyArmorLabel = new javax.swing.JLabel();
        legArmorLabel = new javax.swing.JLabel();
        helmetComboBox = new javax.swing.JComboBox();
        bodyArmorComboBox = new javax.swing.JComboBox();
        legArmorComboBox = new javax.swing.JComboBox();
        helmetPreviewButton = new javax.swing.JButton();
        bodyArmorPreviewButton = new javax.swing.JButton();
        legArmorPreviewButton = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Components", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, null, new java.awt.Color(0, 0, 0)));

        helmetLabel.setText("Helmet :");

        bodyArmorLabel.setText("Body Armor :");

        legArmorLabel.setText("Leg Armor :");

        helmetComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Nothing Selected"}));
        helmetComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                helmetComboBoxActionPerformed(evt);
            }
        });

        bodyArmorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected" }));
        bodyArmorComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bodyArmorComboBoxActionPerformed(evt);
            }
        });

        legArmorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected" }));
        legArmorComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                legArmorComboBoxActionPerformed(evt);
            }
        });

        helmetPreviewButton.setText("P");
        helmetPreviewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                helmetPreviewButtonActionPerformed(evt);
            }
        });

        bodyArmorPreviewButton.setText("P");

        legArmorPreviewButton.setText("P");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(helmetLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bodyArmorLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(legArmorLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(helmetComboBox, 0, 188, Short.MAX_VALUE)
                    .addComponent(bodyArmorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(legArmorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(helmetPreviewButton)
                    .addComponent(bodyArmorPreviewButton)
                    .addComponent(legArmorPreviewButton))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(helmetLabel)
                    .addComponent(helmetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(helmetPreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bodyArmorLabel)
                    .addComponent(bodyArmorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bodyArmorPreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(legArmorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(legArmorLabel)
                    .addComponent(legArmorPreviewButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void helmetComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_helmetComboBoxActionPerformed
    {//GEN-HEADEREND:event_helmetComboBoxActionPerformed
        // TODO add your handling code here:
        model.setEquippedArmorPart(helmetComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.HELMET);
    }//GEN-LAST:event_helmetComboBoxActionPerformed

    private void bodyArmorComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bodyArmorComboBoxActionPerformed
    {//GEN-HEADEREND:event_bodyArmorComboBoxActionPerformed
        // TODO add your handling code here:
        model.setEquippedArmorPart(bodyArmorComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.BODY_ARMOR);
    }//GEN-LAST:event_bodyArmorComboBoxActionPerformed

    private void legArmorComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_legArmorComboBoxActionPerformed
    {//GEN-HEADEREND:event_legArmorComboBoxActionPerformed
        // TODO add your handling code here:
        model.setEquippedArmorPart(legArmorComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.LEG_ARMOR);
    }//GEN-LAST:event_legArmorComboBoxActionPerformed

    private void helmetPreviewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_helmetPreviewButtonActionPerformed
    {//GEN-HEADEREND:event_helmetPreviewButtonActionPerformed
        // TODO add your handling code here:
        System.out.println(model.getArmorSet(panelIDNumber).toString());

    }//GEN-LAST:event_helmetPreviewButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox bodyArmorComboBox;
    private javax.swing.JLabel bodyArmorLabel;
    private javax.swing.JButton bodyArmorPreviewButton;
    private javax.swing.JComboBox helmetComboBox;
    private javax.swing.JLabel helmetLabel;
    private javax.swing.JButton helmetPreviewButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox legArmorComboBox;
    private javax.swing.JLabel legArmorLabel;
    private javax.swing.JButton legArmorPreviewButton;
    // End of variables declaration//GEN-END:variables
}
