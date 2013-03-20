package GUI;

import Main.ArmorSet;
import Main.Inventory;
import Parts.StandardDefinitions;
import Main.Weapon;
import Parts.ArmorPart;
import Parts.EquipmentPart;
import Parts.Receiver;
import Parts.WeaponPart;
import java.util.Observable;

/**
 * The Model used by the GUI to display and pass data. Contains an Inventory and
 * an array of Weapons.
 */
public class Model extends Observable {

    /**
     * Enable color comparison for primary weapon tab?
     */
    private boolean colorComparePrimaryWeapon;

    /**
     * Enable color comparison for secondary weapon tab?
     */
    private boolean colorCompareSecondaryWeapon;

    /**
     * Enable color comparison for armor tab?
     */
    private boolean colorCompareArmor;

    /**
     * A static Inventory that the GUI will access.
     */
    private Inventory inventory;

    /**
     * The array of weapons that can be modified in the GUI.
     */
    private Weapon[] equippedWeapons;

    /**
     * The array of armor sets that can be modified in the GUI.
     */
    private ArmorSet[] equippedArmorSets;

    /**
     * Constructor. Initializes all of the items in the equippedWeapons array.
     */
    public Model()
    {
        this.setInventory(new Inventory());

        equippedWeapons = new Weapon[StandardDefinitions.NUMBER_OF_WEAPONS];
        for (int i = 0; i < equippedWeapons.length; i++) {
            equippedWeapons[i] = new Weapon();
        }

        equippedArmorSets = new ArmorSet[StandardDefinitions.NUMBER_OF_ARMOR_SETS];
        for (int i = 0; i < equippedArmorSets.length; i++) {
            equippedArmorSets[i] = new ArmorSet();
        }
    }

    /**
     * Returns the weapon for a given panelID.
     *
     * @param panelID The panelID for the current WeaponCreationPanel or a child of such
     * @return Weapon for the specified panel
     */
    public final Weapon getWeapon(final int panelID)
    {
        return equippedWeapons[panelID];
    }

    /**
     * Returns the ArmorSet for a given panelID.
     * 
     * @param panelID The panelID for the current ArmorCreationPanel or a child of such
     * @return The armorSet for the specified panel 
     */
    public ArmorSet getArmorSet(int panelID)
    {
        return equippedArmorSets[panelID];
    }

    /**
     * Returns the parallel weapon on the same WeaponTabPanel
     * 
     * @param panelID The panelID for the current WeaponCreationPanel or a child of such
     * @return the other weapon on the same WeaponTabPanel
     */
    public final Weapon getComparisonWeapon(final int panelID)
    {
        if (panelID % 2 == 0) {
            return equippedWeapons[panelID + 1];
        }
        else {
            return equippedWeapons[panelID - 1];
        }
    }

    /** 
     * Returns the parallel ArmorSet on the same ArmorTabPanel
     * 
     * @param panelID The panelID for the current WeaponCreationPanel or a child of such
     * @return the other weapon on the same WeaponTabPanel
     */
    public ArmorSet getComparisonArmorSet(int panelID)
    {
        if (panelID == 0) {
            return equippedArmorSets[1];
        }
        else {
            return equippedArmorSets[0];
        }
    }

    /**
     * Sets the Inventory for the Model.
     *
     * @param inv The inventory that the Model will use.
     */
    public final void setInventory(final Inventory inv)
    {
        inventory = inv;
    }

    /**
     * @return Inventory The Inventory being used by this Model.
     */
    public final Inventory getInventory()
    {
        return inventory;
    }

    /**
     * Sets weather or not to do color comparison for either primary or secondary
     * weapon.
     * 
     * @param primary The primacy of the WeaponTabPanel
     * @param compare Perform comparison?
     */
    public final void setColorCompareWeapon(final boolean primary, final boolean compare)
    {
        if (primary) {
            colorComparePrimaryWeapon = compare;
        }
        else {
            colorCompareSecondaryWeapon = compare;
        }

        indicateChange();
    }

    /**
     * @param primary The primacy of the WeaponTabPanel
     * @return Perform comparison?
     */
    public boolean getColorCompareWeapon(final boolean primary)
    {
        if (primary) {
            return colorComparePrimaryWeapon;
        }
        else {
            return colorCompareSecondaryWeapon;
        }
    }

    public void setColorCompareArmor(boolean compare)
    {
        colorCompareArmor = compare;
        
        indicateChange();
    }

    public boolean getColorCompareArmor()
    {
        return colorCompareArmor;
    }

    /**
     * Sets the receiver for the weapon given the panelID.
     *
     * @param selectedIndex The index for the item selected in the
     * receiverComboBox.
     * @param panelID The panelIDnumber for the current
     * WeaponComponentSelectionPanel.
     */
    public final void setEquippedReceiver(
            final int selectedIndex, final int panelID, final boolean primary)
    {
        Receiver r;

        if (primary) {
            r = (Receiver) inventory.getIndependentPart(
                    selectedIndex, StandardDefinitions.PRIMARY_RECEIVERS);
        }
        else {
            r = (Receiver) inventory.getIndependentPart(
                    selectedIndex, StandardDefinitions.SECONDARY_RECEIVERS);
        }

        equippedWeapons[panelID].setReceiver(r);
//        System.out.println("   Equipped receiver [" + panelID + "] " + r.getFullName());
        indicateChange();
    }

    /**
     * Checks to see if the weapon to which this part will be attached can
     * accept a part of that type. If so, it gets the part from the Inventory
     * using the selectedIndex, then gives the part to the weapon to be
     * equipped. It then calls changeHasHappend() which notifies the observers
     * of any change.
     *
     * @param selectedIndex The selected index of the comboBox
     * @param panelID The panelID the selection was done in
     * @param partID The partID as defined in StandardDefinitions.java
     */
    public final void setEquippedAttachment(final int selectedIndex, final int panelID, final int partID)
    {
        if (equippedWeapons[panelID].getCanHavePart(partID)) {
            WeaponPart wp = inventory.getDependentPart(selectedIndex, partID, panelID);
            equippedWeapons[panelID].setAttachment(partID, wp);
//            System.out.println("Equipped part type [" + partID + "]"
//                    + " on weapon [" + panelID + "] : " + wp.getFullName());
            indicateChange();
        }
    }

    public void setEquippedArmorPart(int selectedIndex, int panelID, int partID)
    {
        ArmorPart ap = (ArmorPart) inventory.getIndependentPart(selectedIndex, partID);
        equippedArmorSets[panelID].setPart(partID, ap);
//        System.out.println("Equipped part type [" + partID + "]"
//                    + " on armor set [" + panelID + "] : " + ap.getFullName());
        indicateChange();
    }

    /**
     * A change has been made. Set the Changed flag and notify all observers.
     */
    public final void indicateChange()
    {
        setChanged();
        notifyObservers();
        clearChanged();
    }
}
