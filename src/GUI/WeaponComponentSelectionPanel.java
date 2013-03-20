package GUI;

import Parts.StandardDefinitions;
import Main.Weapon;
import Parts.Barrel;
import Parts.EquipmentPart;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * The panel used to select the parts for a weapon. Contains 6 ComboBoxes, 1 for
 * each key part and a preview button and a label for each ComboBox.
 */
public class WeaponComponentSelectionPanel extends javax.swing.JPanel {

    /**
     * A copy of the StandardDefintions class for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();

    /**
     * The pointer to the Model that the program will use. Is initialized in
     * BLGCMainFrame.java
     */
    private Model model;

    /**
     * If primary is true, then this WeaponComponentSelectionPanel deals with
     * primary receivers; If false, it deals with secondary ones.
     */
    private boolean primary;

    /**
     * The number used to refer to which weapon set this panel makes changes to.
     * Also, links this panel to the appropriate WeaponStatsPanel
     */
    private int panelIDNumber;

    /**
     * Creates new form WeaponComponentSelectionPanel.
     */
    public WeaponComponentSelectionPanel()
    {
        initComponents();
    }

    public void enableAllSubparts(boolean enable)
    {
        if (enable) {
            receiverComboBox.setEnabled(true);
            receiverPreviewButton.setEnabled(true);
            receiverLabel.setEnabled(true);
            setComponentSelectability();
        }
        else {
            barrelComboBox.setEnabled(false);
            barrelPreviewButton.setEnabled(false);
            barrelLabel.setEnabled(false);
            magazineComboBox.setEnabled(false);
            magazinePreviewButton.setEnabled(false);
            magazineLabel.setEnabled(false);
            muzzleComboBox.setEnabled(false);
            muzzlePreviewButton.setEnabled(false);
            muzzleLabel.setEnabled(false);
            receiverComboBox.setEnabled(false);
            receiverPreviewButton.setEnabled(false);
            receiverLabel.setEnabled(false);
            scopeComboBox.setEnabled(false);
            scopePreviewButton.setEnabled(false);
            scopeLabel.setEnabled(false);
            stockComboBox.setEnabled(false);
            stockPreviewButton.setEnabled(false);
            stockLabel.setEnabled(false);
        }
    }

    /**
     * @param m The model
     */
    public void setModel(Model m)
    {
        model = m;
        populateReceiverComboBox(receiverComboBox);
    }

    /**
     * Sets the panels panelIDNumber.
     *
     * @param idNum The ID number this panel will use.
     * @param primacy If the weapons in this panel are primary or not.
     */
    public void setPanelIDNumberAndPrimacy(int idNum, boolean primacy)
    {
        panelIDNumber = idNum;
        setPrimary(primacy);
//        populateReceiverComboBox(receiverComboBox);
    }

    /**
     * Returns the panels panelIDNumber.
     *
     * @return The panelIDNumber for this panel.
     */
    public int getPanelIDNumber()
    {
        return panelIDNumber;
    }

    /**
     * Sets the primary field.
     *
     * @param prime If this panel deals with primary weapons.
     */
    public void setPrimary(boolean prime)
    {
        this.primary = prime;
    }

    /**
     * Populates the receiver comboBox
     *
     * @param comboBox The comboBox to be populated.
     */
    private void populateReceiverComboBox(JComboBox comboBox)
    {
        String[] stringList;

        if (primary) {
            stringList = model.getInventory().getStringListOfIndependentParts(
                    StandardDefinitions.PRIMARY_RECEIVERS);
        }
        else {
            stringList = model.getInventory().getStringListOfIndependentParts(
                    StandardDefinitions.SECONDARY_RECEIVERS);
        }

        comboBox.setModel(new DefaultComboBoxModel(stringList));

        if (stringList.length > 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    /**
     * Populates a given comboBox depending on the standard partIndexID given.
     *
     * @param comboBox The comboBox that needs to be populated.
     * @param partID The partID as defined in StandardDefinitions.java
     */
    private void populateComboBox(JComboBox comboBox, int partID)
    {
        String[] stringList;
        String shortName;

        // The receiver must be set before the other comboBoxes can be populated
        // This catches a possible exception.
        try {
            shortName = model.getWeapon(panelIDNumber).getReceiver().getShortName();
        }
        catch (NullPointerException n) {
            shortName = "";
        }

        stringList = model.getInventory().
                getStringListOfDependentParts(shortName, partID, panelIDNumber);

        comboBox.setModel(new DefaultComboBoxModel(stringList));

        if (stringList.length > 0) {
            comboBox.setSelectedIndex(0);
        }
    }

    /**
     * Populates the comboBoxes with the relevant lists of data. The
     * receiverComboBox never changes data so it is filled out in the
     * constructor.
     */
    private void populateComboBoxes()
    {
        populateComboBox(barrelComboBox, StandardDefinitions.BARREL);
        populateComboBox(magazineComboBox, StandardDefinitions.MAGAZINE);
        populateComboBox(muzzleComboBox, StandardDefinitions.MUZZLE);
        populateComboBox(scopeComboBox, StandardDefinitions.SCOPE);
        populateComboBox(stockComboBox, StandardDefinitions.STOCK);
    }

    /**
     * Enables or disables the comboBoxes, labels and preview buttons for a
     * specific part.
     */
    private void setComponentSelectability()
    {
        final Weapon w = model.getWeapon(panelIDNumber);
        boolean temp;

        // If the receiver is a shotgun, change the muzzleLabel text to "Grip :"
        if ("SHOT".equals(w.getReceiver().getShortName())) {
            muzzleLabel.setText("Grip :");
        }
        else {
            muzzleLabel.setText("Muzzle :");
        }

        temp = w.getCanHavePart(StandardDefinitions.BARREL);
        barrelComboBox.setEnabled(temp);
        barrelLabel.setEnabled(temp);
        barrelPreviewButton.setEnabled(temp);

        temp = w.getCanHavePart(StandardDefinitions.MAGAZINE);
        magazineComboBox.setEnabled(temp);
        magazineLabel.setEnabled(temp);
        magazinePreviewButton.setEnabled(temp);

        temp = w.getCanHavePart(StandardDefinitions.MUZZLE);
        muzzleComboBox.setEnabled(temp);
        muzzleLabel.setEnabled(temp);
        muzzlePreviewButton.setEnabled(temp);

        temp = w.getCanHavePart(StandardDefinitions.SCOPE);
        scopeComboBox.setEnabled(temp);
        scopeLabel.setEnabled(temp);
        scopePreviewButton.setEnabled(temp);

        temp = w.getCanHavePart(StandardDefinitions.STOCK);
        stockComboBox.setEnabled(temp);
        stockLabel.setEnabled(temp);
        stockPreviewButton.setEnabled(temp);
    }

    /**
     * There is a Barrel for pistols that enables the use of stocks. A special
     * case must be defined for this barrel. That is done here. This method is
     * called before equipping a selected Barrel.
     */
    private void checkBarrelSpecialCase()
    {
        Barrel potentialBarrel = (Barrel) model.getInventory()
                .getDependentPart(barrelComboBox.getSelectedIndex(),
                StandardDefinitions.BARREL, panelIDNumber);

        Weapon thisWeapon = model.getWeapon(panelIDNumber);
        boolean enablesStock = potentialBarrel.getEnableStock();
        boolean originalSetting = model.getWeapon(panelIDNumber)
                .getReceiver().getCanHavePart(StandardDefinitions.STOCK);
        int stockID = StandardDefinitions.STOCK;
        int selectedStock = stockComboBox.getSelectedIndex();

        if (enablesStock && !originalSetting) {
            thisWeapon.setCanHavePart(stockID, true);
            model.setEquippedAttachment(selectedStock, panelIDNumber, stockID);
        }
        else {
            thisWeapon.setCanHavePart(stockID, originalSetting);

            if (originalSetting) {
                model.setEquippedAttachment(selectedStock, panelIDNumber, stockID);
            }
            else {
                thisWeapon.setCurrentlyHasPart(stockID, false);
            }
        }
    }

    public String getWeaponSaveData()
    {
        StringBuilder returnString = new StringBuilder();

        if (model.getWeapon(panelIDNumber).isACompleteWeapon()) {
            if (primary) {
                returnString.append("primary, ");
            }
            else {
                returnString.append("secondary, ");
            }
            returnString.append(receiverComboBox.getSelectedIndex() + ", ");
            returnString.append(barrelComboBox.getSelectedIndex() + ", ");
            returnString.append(magazineComboBox.getSelectedIndex() + ", ");
            returnString.append(muzzleComboBox.getSelectedIndex() + ", ");
            returnString.append(scopeComboBox.getSelectedIndex() + ", ");
            returnString.append(stockComboBox.getSelectedIndex());
        }

        return returnString.toString();
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

        receiverLabel = new javax.swing.JLabel();
        barrelLabel = new javax.swing.JLabel();
        magazineLabel = new javax.swing.JLabel();
        muzzleLabel = new javax.swing.JLabel();
        scopeLabel = new javax.swing.JLabel();
        stockLabel = new javax.swing.JLabel();
        receiverComboBox = new javax.swing.JComboBox();
        barrelComboBox = new javax.swing.JComboBox();
        magazineComboBox = new javax.swing.JComboBox();
        muzzleComboBox = new javax.swing.JComboBox();
        scopeComboBox = new javax.swing.JComboBox();
        stockComboBox = new javax.swing.JComboBox();
        receiverPreviewButton = new javax.swing.JButton();
        barrelPreviewButton = new javax.swing.JButton();
        magazinePreviewButton = new javax.swing.JButton();
        muzzlePreviewButton = new javax.swing.JButton();
        scopePreviewButton = new javax.swing.JButton();
        stockPreviewButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Components", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, null, new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(300, 200));
        setName(""); // NOI18N

        receiverLabel.setText("Receiver :");

        barrelLabel.setText("Barrel :");

        magazineLabel.setText("Magazine :");

        muzzleLabel.setText("Muzzle :");

        scopeLabel.setText("Scope :");

        stockLabel.setText("Stock :");

        receiverComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Nothing Selected"}));
        receiverComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                receiverComboBoxActionPerformed(evt);
            }
        });

        barrelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected" }));
        barrelComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                barrelComboBoxActionPerformed(evt);
            }
        });

        magazineComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected" }));
        magazineComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                magazineComboBoxActionPerformed(evt);
            }
        });

        muzzleComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Nothing selected" }));
        muzzleComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                muzzleComboBoxActionPerformed(evt);
            }
        });

        scopeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected"  }));
        scopeComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                scopeComboBoxActionPerformed(evt);
            }
        });

        stockComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nothing selected" }));
        stockComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stockComboBoxActionPerformed(evt);
            }
        });

        receiverPreviewButton.setText("P");
        receiverPreviewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                receiverPreviewButtonActionPerformed(evt);
            }
        });

        barrelPreviewButton.setText("P");

        magazinePreviewButton.setText("P");

        muzzlePreviewButton.setText("P");

        scopePreviewButton.setText("P");

        stockPreviewButton.setText("P");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(muzzleLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scopeLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(receiverLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(barrelLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(magazineLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(receiverComboBox, 0, 208, Short.MAX_VALUE)
                    .addComponent(barrelComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scopeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stockComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(magazineComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(muzzleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(receiverPreviewButton)
                    .addComponent(barrelPreviewButton)
                    .addComponent(muzzlePreviewButton)
                    .addComponent(scopePreviewButton)
                    .addComponent(stockPreviewButton)
                    .addComponent(magazinePreviewButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(receiverLabel)
                    .addComponent(receiverComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(receiverPreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(barrelLabel)
                    .addComponent(barrelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(barrelPreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(magazineLabel)
                    .addComponent(magazineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(magazinePreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(muzzleLabel)
                    .addComponent(muzzleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(muzzlePreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scopeLabel)
                    .addComponent(scopeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scopePreviewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stockLabel)
                    .addComponent(stockComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockPreviewButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * When a Barrel is selected, check for the special case. Then, send that
     * data to the model.
     *
     * @param evt The ActionEvent created.
     */
    private void barrelComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_barrelComboBoxActionPerformed
    {//GEN-HEADEREND:event_barrelComboBoxActionPerformed
        checkBarrelSpecialCase();
        model.setEquippedAttachment(barrelComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.BARREL);
        setComponentSelectability();
    }//GEN-LAST:event_barrelComboBoxActionPerformed

    /**
     * When a Magazine is selected, send that data to the model.
     *
     * @param evt The ActionEvent created.
     */
    private void magazineComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_magazineComboBoxActionPerformed
    {//GEN-HEADEREND:event_magazineComboBoxActionPerformed
        model.setEquippedAttachment(magazineComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.MAGAZINE);
        setComponentSelectability();
    }//GEN-LAST:event_magazineComboBoxActionPerformed

    /**
     * When a Muzzle is selected, send that data to the model.
     *
     * @param evt The ActionEvent created.
     */
    private void muzzleComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_muzzleComboBoxActionPerformed
    {//GEN-HEADEREND:event_muzzleComboBoxActionPerformed
        model.setEquippedAttachment(muzzleComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.MUZZLE);
        setComponentSelectability();
    }//GEN-LAST:event_muzzleComboBoxActionPerformed

    /**
     * When a Scope is selected, send that data to the model.
     *
     * @param evt The ActionEvent created.
     */
    private void scopeComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scopeComboBoxActionPerformed
    {//GEN-HEADEREND:event_scopeComboBoxActionPerformed
        model.setEquippedAttachment(scopeComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.SCOPE);
        setComponentSelectability();
    }//GEN-LAST:event_scopeComboBoxActionPerformed

    /**
     * When a Stock is selected, send that data to the model.
     *
     * @param evt The ActionEvent created.
     */
    private void stockComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stockComboBoxActionPerformed
    {//GEN-HEADEREND:event_stockComboBoxActionPerformed
        model.setEquippedAttachment(stockComboBox.getSelectedIndex(),
                panelIDNumber, StandardDefinitions.STOCK);

        setComponentSelectability();    }//GEN-LAST:event_stockComboBoxActionPerformed

    private void receiverComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_receiverComboBoxActionPerformed
    {//GEN-HEADEREND:event_receiverComboBoxActionPerformed
        model.setEquippedReceiver(receiverComboBox.getSelectedIndex(), panelIDNumber, primary);
        populateComboBoxes();
        setComponentSelectability();
        receiverComboBox.setToolTipText(
                model.getWeapon(panelIDNumber).getReceiver().getDescription());
    }//GEN-LAST:event_receiverComboBoxActionPerformed

    /**
     * Currently prints the stats of the weapon.
     *
     * @param evt
     */
    private void receiverPreviewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_receiverPreviewButtonActionPerformed
    {//GEN-HEADEREND:event_receiverPreviewButtonActionPerformed
        System.out.println(model.getWeapon(panelIDNumber).toString());
    }//GEN-LAST:event_receiverPreviewButtonActionPerformed
    // <editor-fold defaultstate="collapsed" desc="Generated GUI components">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox barrelComboBox;
    private javax.swing.JLabel barrelLabel;
    private javax.swing.JButton barrelPreviewButton;
    private javax.swing.JComboBox magazineComboBox;
    private javax.swing.JLabel magazineLabel;
    private javax.swing.JButton magazinePreviewButton;
    private javax.swing.JComboBox muzzleComboBox;
    private javax.swing.JLabel muzzleLabel;
    private javax.swing.JButton muzzlePreviewButton;
    /** A comboBox that allows the selection of a Receiver. */
    private javax.swing.JComboBox receiverComboBox;
    private javax.swing.JLabel receiverLabel;
    private javax.swing.JButton receiverPreviewButton;
    private javax.swing.JComboBox scopeComboBox;
    private javax.swing.JLabel scopeLabel;
    private javax.swing.JButton scopePreviewButton;
    private javax.swing.JComboBox stockComboBox;
    private javax.swing.JLabel stockLabel;
    private javax.swing.JButton stockPreviewButton;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
