package main;

import Main.*;

@Deprecated
public class Agent {

    public static final java.util.Scanner INPUT = new java.util.Scanner(System.in);
    private Inventory inventory;
    private ArmorSet equipedArmor;
    private Weapon equipedPrimaryWeapon;
    private Weapon equipedSecondaryWeapon;

    public static void main(String[] args) {
        Agent agent = new Agent();
    }

    public Agent() {
        inventory = new Inventory();
        equipedArmor = new ArmorSet();
    }


    /**
     * Returns the agents equipedArmor, an object of type ArmorSet
     */
    public ArmorSet getEquipedArmor() {
        return equipedArmor;
    }

    /**
     * Returns the agents equipedPrimaryWeapon, an object of type Weapon
     */
    public Weapon getEquipedPrimaryWeapon() {
        return equipedPrimaryWeapon;
    }

    /**
     * Returns the agents equipedSecondaryWeapon, an object of type Weapon
     */
    public Weapon getEquipedSecondaryWeapon() {
        return equipedSecondaryWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
