package GUI;

import Main.Weapon;
import Parts.StandardDefinitions;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

/**
 * Displays the stats for the weapon described in the
 * WeaponComponentSelectionPanel which is held by the WeaponCreationPanel which
 * also holds this WeaponStatsPanel.
 */
public class WeaponStatsPanel extends javax.swing.JPanel implements Observer {

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
     * An ID number that the panel uses when reading from the model. This panel
     * is linked to the WeaponComponentSelectionPanel with the same ID.
     */
    private int panelIDNumber;

    /**
     * A copy of the weapon that his stats panel represents.
     */
    private Weapon thisWeapon;

    /**
     * Is this referring to a primary weapon? False if secondary.
     */
    private boolean primary;

    /**
     * Creates new form PrimaryWeaponStatsPanel
     */
    public WeaponStatsPanel()
    {
        initComponents();
    }

    public void enableAllSubparts(boolean enable)
    {
        ammoDisplayLabel.setEnabled(enable);
        ammoLabel.setEnabled(enable);
        damageDisplayLabel.setEnabled(enable);
        damageLabel.setEnabled(enable);
        rangeDisplayLabel.setEnabled(enable);
        rangeLabel.setEnabled(enable);
        recoilDisplayLabel.setEnabled(enable);
        recoilLabel.setEnabled(enable);
        reloadSpeedDisplayLabel.setEnabled(enable);
        reloadSpeedLabel.setEnabled(enable);
        rofDisplayLabel.setEnabled(enable);
        rofLabel.setEnabled(enable);
        scopeDisplayLabel.setEnabled(enable);
        scopeInDisplayLabel.setEnabled(enable);
        scopeInLabel.setEnabled(enable);
        scopeLabel.setEnabled(enable);
        speedDisplayLabel.setEnabled(enable);
        speedLabel.setEnabled(enable);
        spreadAimDisplayLabel.setEnabled(enable);
        spreadAimLabel.setEnabled(enable);
        spreadAimMaxDisplayLabel.setEnabled(enable);
        spreadAimMaxLabel.setEnabled(enable);
        spreadHipDisplayLabel.setEnabled(enable);
        spreadHipLabel.setEnabled(enable);
        spreadHipMaxDisplayLabel.setEnabled(enable);
        spreadHipMaxLabel.setEnabled(enable);
    }

    /**
     * Updates the displayLabels of this WeaponStatsPanel. Is only called in
     * response to notifyObservers() in Model.java
     *
     * @param obs The observable
     * @param obj The object
     */
    @Override
    public void update(Observable obs, Object obj)
    {
        thisWeapon = model.getWeapon(panelIDNumber);
        String plusMinus;

        ammoDisplayLabel.setText("[" + thisWeapon.magazineSize + "/" + thisWeapon.reserveAmmo + "]");
        damageDisplayLabel.setText(thisWeapon.damage + "");
        rangeDisplayLabel.setText("[" + thisWeapon.rangeLow + "/" + thisWeapon.rangeMax + "]");
        recoilDisplayLabel.setText(thisWeapon.recoil + "");
        reloadSpeedDisplayLabel.setText(thisWeapon.reloadSpeed + "s");
        rofDisplayLabel.setText(thisWeapon.rateOfFire + "");
        scopeDisplayLabel.setText(thisWeapon.zoomAmount + "x");
        scopeInDisplayLabel.setText(thisWeapon.scopeInTime + "s");

        if (thisWeapon.runSpeed >= 0) {
            plusMinus = "+";
        }
        else {
            plusMinus = "-";
        }
        speedDisplayLabel.setText(plusMinus + thisWeapon.runSpeed + "m/s");

        spreadAimDisplayLabel.setText(thisWeapon.spreadAim + "");
        spreadAimMaxDisplayLabel.setText(thisWeapon.spreadAimMax + "");
        spreadHipDisplayLabel.setText(thisWeapon.spreadHip + "");
        spreadHipMaxDisplayLabel.setText(thisWeapon.spreadHipMax + "");

        changeLabelColorsForComparison();
    }

    /**
     * Compares the each of the stats for a weapon.
     */
    private void changeLabelColorsForComparison()
    {
        Weapon comparisonWeapon = model.getComparisonWeapon(panelIDNumber);

        compareStats(thisWeapon.damage, comparisonWeapon.damage,
                damageDisplayLabel, true);
        compareStats(thisWeapon.magazineSize, comparisonWeapon.magazineSize,
                ammoDisplayLabel, true);
        compareStats(thisWeapon.recoil, comparisonWeapon.recoil,
                recoilDisplayLabel, false);
        compareStats(thisWeapon.runSpeed, comparisonWeapon.runSpeed,
                speedDisplayLabel, true);
        compareStats(thisWeapon.spreadAim, comparisonWeapon.spreadAim,
                spreadAimDisplayLabel, false);
        compareStats(thisWeapon.spreadAimMax, comparisonWeapon.spreadAimMax,
                spreadAimMaxDisplayLabel, false);
        compareStats(thisWeapon.spreadHip, comparisonWeapon.spreadHip,
                spreadHipDisplayLabel, false);
        compareStats(thisWeapon.spreadHipMax, comparisonWeapon.spreadHipMax,
                spreadHipMaxDisplayLabel, false);
        compareStats(thisWeapon.zoomAmount, comparisonWeapon.zoomAmount,
                scopeDisplayLabel, true);
        compareStats(thisWeapon.scopeInTime, comparisonWeapon.scopeInTime,
                scopeInDisplayLabel, false);
        compareStats(thisWeapon.reloadSpeed, comparisonWeapon.reloadSpeed,
                reloadSpeedDisplayLabel, false);
        compareStats(thisWeapon.rateOfFire, comparisonWeapon.rateOfFire,
                rofDisplayLabel, true);
        compareStats(thisWeapon.rangeLow + thisWeapon.rangeMax,
                comparisonWeapon.rangeLow + comparisonWeapon.rangeMax,
                rangeDisplayLabel, true);
    }

    /**
     * Compares the a stat from two weapons: The main weapon and the comparison
     * weapon. Comparisons are relative to the main weapon.
     *
     * @param main The stat from the main weapon
     * @param comp The stat from the comparison weapon
     * @param label The label whose color will be changed depending on the
     * results of the comparison.
     * @param moreIsBetter Is a stat considered better if it is a higher value?
     */
    private void compareStats(double main, double comp, JLabel label, boolean moreIsBetter)
    {
        double stat1 = main;
        double stat2 = comp;

        if (!moreIsBetter) {
            stat1 = comp;
            stat2 = main;
        }

        if (model.getColorCompareWeapon(primary)) {
            if (stat1 > stat2) {
                label.setForeground(sd.DARK_GREEN);
            }
            else if (stat1 < stat2) {
                label.setForeground(sd.DARK_RED);
            }
            else {
                label.setForeground(Color.BLACK);
            }
        }
        else {
            label.setForeground(Color.BLACK);
        }
    }

    /**
     * @param m The model
     */
    public void setModel(Model m)
    {
        model = m;
        model.addObserver(this);
    }

    public void setPanelIDNumberAndPrimacy(int idNum, boolean primacy)
    {
        panelIDNumber = idNum;
        primary = primacy;
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

        spreadHipMaxLabel = new javax.swing.JLabel();
        rangeLabel = new javax.swing.JLabel();
        spreadAimMaxLabel = new javax.swing.JLabel();
        reloadSpeedLabel = new javax.swing.JLabel();
        scopeInLabel = new javax.swing.JLabel();
        rofLabel = new javax.swing.JLabel();
        scopeLabel = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        spreadAimLabel = new javax.swing.JLabel();
        spreadHipLabel = new javax.swing.JLabel();
        ammoLabel = new javax.swing.JLabel();
        damageLabel = new javax.swing.JLabel();
        spreadAimDisplayLabel = new javax.swing.JLabel();
        spreadHipDisplayLabel = new javax.swing.JLabel();
        ammoDisplayLabel = new javax.swing.JLabel();
        damageDisplayLabel = new javax.swing.JLabel();
        speedDisplayLabel = new javax.swing.JLabel();
        scopeDisplayLabel = new javax.swing.JLabel();
        rofDisplayLabel = new javax.swing.JLabel();
        reloadSpeedDisplayLabel = new javax.swing.JLabel();
        spreadHipMaxDisplayLabel = new javax.swing.JLabel();
        spreadAimMaxDisplayLabel = new javax.swing.JLabel();
        rangeDisplayLabel = new javax.swing.JLabel();
        scopeInDisplayLabel = new javax.swing.JLabel();
        recoilLabel = new javax.swing.JLabel();
        recoilDisplayLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED), "Stats", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 0, 0)));

        spreadHipMaxLabel.setText("Max :");

        rangeLabel.setText("Range :");

        spreadAimMaxLabel.setText("Max :");

        reloadSpeedLabel.setText("Reload Speed :");

        scopeInLabel.setText("Scope-In :");

        rofLabel.setText("Rate of Fire :");

        scopeLabel.setText("Zoom :");

        speedLabel.setText("Run Speed :");

        spreadAimLabel.setText("Spread Aim :");

        spreadHipLabel.setText("Spread Hip :");

        ammoLabel.setText("Ammo :");

        damageLabel.setText("Damage :");

        spreadAimDisplayLabel.setText("         ");

        spreadHipDisplayLabel.setText("        ");

        ammoDisplayLabel.setText("          ");

        damageDisplayLabel.setText("        ");

        speedDisplayLabel.setText("         ");

        scopeDisplayLabel.setText("               ");

        rofDisplayLabel.setText("           ");

        reloadSpeedDisplayLabel.setText("           ");

        spreadHipMaxDisplayLabel.setText("           ");

        spreadAimMaxDisplayLabel.setText("           ");

        rangeDisplayLabel.setText("           ");

        scopeInDisplayLabel.setText("               ");

        recoilLabel.setText("Recoil :");

        recoilDisplayLabel.setText("           ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(damageLabel)
                    .addComponent(spreadHipLabel)
                    .addComponent(ammoLabel)
                    .addComponent(recoilLabel)
                    .addComponent(spreadAimLabel)
                    .addComponent(scopeLabel)
                    .addComponent(speedLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(ammoDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(recoilDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                .addComponent(spreadHipDisplayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(damageDisplayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(spreadAimDisplayLabel)
                            .addComponent(scopeDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(spreadHipMaxLabel)
                                    .addComponent(reloadSpeedLabel)
                                    .addComponent(spreadAimMaxLabel)
                                    .addComponent(scopeInLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(reloadSpeedDisplayLabel)
                                    .addComponent(scopeInDisplayLabel)
                                    .addComponent(spreadHipMaxDisplayLabel)
                                    .addComponent(spreadAimMaxDisplayLabel)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rofLabel)
                                    .addComponent(rangeLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rangeDisplayLabel)
                                    .addComponent(rofDisplayLabel)))))
                    .addComponent(speedDisplayLabel))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {rangeDisplayLabel, reloadSpeedDisplayLabel, rofDisplayLabel, scopeInDisplayLabel, spreadAimMaxDisplayLabel, spreadHipMaxDisplayLabel});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ammoDisplayLabel, damageDisplayLabel, recoilDisplayLabel, scopeDisplayLabel, speedDisplayLabel, spreadAimDisplayLabel, spreadHipDisplayLabel});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(damageLabel)
                    .addComponent(damageDisplayLabel)
                    .addComponent(rofLabel)
                    .addComponent(rofDisplayLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(recoilLabel)
                        .addComponent(recoilDisplayLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rangeLabel)
                        .addComponent(rangeDisplayLabel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ammoLabel)
                            .addComponent(ammoDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spreadHipLabel)
                            .addComponent(spreadHipDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spreadAimLabel)
                            .addComponent(spreadAimDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scopeLabel)
                            .addComponent(scopeDisplayLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reloadSpeedLabel)
                            .addComponent(reloadSpeedDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spreadHipMaxLabel)
                            .addComponent(spreadHipMaxDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spreadAimMaxLabel)
                            .addComponent(spreadAimMaxDisplayLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(scopeInLabel)
                            .addComponent(scopeInDisplayLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speedLabel)
                    .addComponent(speedDisplayLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ammoDisplayLabel, damageDisplayLabel, recoilDisplayLabel, scopeDisplayLabel, speedDisplayLabel, spreadAimDisplayLabel, spreadHipDisplayLabel});

    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ammoDisplayLabel;
    private javax.swing.JLabel ammoLabel;
    private javax.swing.JLabel damageDisplayLabel;
    private javax.swing.JLabel damageLabel;
    private javax.swing.JLabel rangeDisplayLabel;
    private javax.swing.JLabel rangeLabel;
    private javax.swing.JLabel recoilDisplayLabel;
    private javax.swing.JLabel recoilLabel;
    private javax.swing.JLabel reloadSpeedDisplayLabel;
    private javax.swing.JLabel reloadSpeedLabel;
    private javax.swing.JLabel rofDisplayLabel;
    private javax.swing.JLabel rofLabel;
    private javax.swing.JLabel scopeDisplayLabel;
    private javax.swing.JLabel scopeInDisplayLabel;
    private javax.swing.JLabel scopeInLabel;
    private javax.swing.JLabel scopeLabel;
    private javax.swing.JLabel speedDisplayLabel;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JLabel spreadAimDisplayLabel;
    private javax.swing.JLabel spreadAimLabel;
    private javax.swing.JLabel spreadAimMaxDisplayLabel;
    private javax.swing.JLabel spreadAimMaxLabel;
    private javax.swing.JLabel spreadHipDisplayLabel;
    private javax.swing.JLabel spreadHipLabel;
    private javax.swing.JLabel spreadHipMaxDisplayLabel;
    private javax.swing.JLabel spreadHipMaxLabel;
    // End of variables declaration//GEN-END:variables
}
