package Main;

import Parts.Barrel;
import Parts.EquipmentPart;
import Parts.Magazine;
import Parts.Muzzle;
import Parts.Receiver;
import Parts.Scope;
import Parts.StandardDefinitions;
import Parts.Stock;
import Parts.WeaponPart;
import java.text.DecimalFormat;

/**
 * Holds all the parts and stats that make a full weapon.
 */
public class Weapon {

    private StandardDefinitions stdef = new StandardDefinitions();

    private WeaponPart[] attachments;

    /**
     * The Receiver for this weapon.
     */
    public Receiver receiver;

    /**
     * Can this weapon equip a given attachment?
     */
    private boolean canHavePart[];

    /**
     * Does this weapon currently have a given attachment?
     */
    private boolean currentlyHasPart[];

    /**
     * The damage of this weapon.
     */
    public int damage;

    /**
     * The damage per second for this weapon.
     */
    public int dps;

    /**
     * The magazineSize of this weapon.
     */
    public int magazineSize;

    /**
     * The reserveAmmo of this weapon.
     */
    public int reserveAmmo;

    /**
     * The reloadSpeed of this weapon.
     */
    public double reloadSpeed;

    /**
     * The rangeLow of this weapon.
     */
    public int rangeLow;

    /**
     * The rangeMax of this weapon.
     */
    public int rangeMax;

    /**
     * The damrateOfFireage of this weapon.
     */
    public int rateOfFire;

    /**
     * The spreadHip of this weapon.
     */
    public double spreadHip;

    /**
     * The spreadHipMax of this weapon.
     */
    public double spreadHipMax;

    /**
     * The spreadAim of this weapon.
     */
    public double spreadAim;

    /**
     * The spreadAimMax of this weapon.
     */
    public double spreadAimMax;

    /**
     * The speed of this weapon.
     */
    public double runSpeed;

    /**
     * The recoil of this weapon.
     */
    public double recoil;

    /**
     * The scopeInTime of this weapon.
     */
    public double scopeInTime;

    /**
     * The zoomAmount of this weapon.
     */
    public double zoomAmount;

    /**
     * The weapon index of this weapon.
     */
    public int weaponIndex;

    /**
     * Create new weapon.
     */
    public Weapon()
    {
        canHavePart = new boolean[stdef.TOTAL_NUMBER_OF_PART_TYPES];
        currentlyHasPart = new boolean[canHavePart.length];
        attachments = new WeaponPart[canHavePart.length];
    }

    /**
     * @return True if this weapon has one of each part that is can equip.
     */
    public boolean isACompleteWeapon()
    {
        return (getCanHavePart(stdef.BARREL) == getCurrentlyHasPart(stdef.BARREL))
                && (getCanHavePart(stdef.MAGAZINE) == getCurrentlyHasPart(stdef.MAGAZINE))
                && (getCanHavePart(stdef.MUZZLE) == getCurrentlyHasPart(stdef.MUZZLE))
                && (getCanHavePart(stdef.SCOPE) == getCurrentlyHasPart(stdef.SCOPE))
                && (getCanHavePart(stdef.STOCK) == getCurrentlyHasPart(stdef.STOCK));
    }

    /**
     * If the weapon is complete, apply the stats from each part. Otherwise, set
     * all the stats to 0.
     */
    private void updateWeaponStats()
    {
        if (isACompleteWeapon()) {
            applyReceiverStats();

            if (getCanHavePart(stdef.BARREL)) {
                applyBarrelStats();
            }
            if (getCanHavePart(stdef.MAGAZINE)) {
                applyMagazineStats();
            }
            if (getCanHavePart(stdef.MUZZLE)) {
                applyMuzzleStats();
            }
            if (getCanHavePart(stdef.SCOPE)) {
                applyScopeStats();
            }
            if (getCanHavePart(stdef.STOCK)) {
                applyStockStats();
            }
            dps = damage * rateOfFire / 60;
        }
        else {
            damage = 0;
            magazineSize = 0;
            rangeLow = 0;
            rangeMax = 0;
            rateOfFire = 0;
            recoil = 0;
            reloadSpeed = 0;
            reserveAmmo = 0;
            runSpeed = 0;
            scopeInTime = 0;
            spreadAim = 0;
            spreadAimMax = 0;
            spreadHip = 0;
            spreadHipMax = 0;
            zoomAmount = 0;
        }

        formatData();
    }

    /**
     * Apply the stats from the Receiver to this weapon.
     */
    private void applyReceiverStats()
    {
        damage = receiver.getDamage();
        rateOfFire = receiver.getRof();
        rangeLow = receiver.getRangeLow();
        rangeMax = receiver.getRangeMax();
        recoil = receiver.getRecoil();
        reserveAmmo = receiver.getMags();
        runSpeed = receiver.getRunSpeed();
        spreadAim = receiver.getSpreadAim();
        spreadAimMax = receiver.getSpreadAimMax();
        spreadHip = receiver.getSpreadHip();
        spreadHipMax = receiver.getSpreadHipMax();
    }

    /**
     * Apply the stats from the Barrel to this Weapon.
     */
    private void applyBarrelStats()
    {
        Barrel barrel = (Barrel) attachments[stdef.BARREL];

        damage += barrel.getDamage(weaponIndex);
        rangeLow += barrel.getRangeLow(weaponIndex);
        rangeMax += barrel.getRangeMax(weaponIndex);
        runSpeed += barrel.getRunSpeed(weaponIndex);
        spreadAim += barrel.getSpreadAim(weaponIndex);
        spreadAimMax += barrel.getSpreadAimMax(weaponIndex);
        spreadHip += barrel.getSpreadHip(weaponIndex);
        spreadHipMax += barrel.getSpreadHipMax(weaponIndex);
    }

    /**
     * Apply to stats from the Magazine to this Weapon.
     */
    private void applyMagazineStats()
    {
        Magazine magazine = (Magazine) attachments[stdef.MAGAZINE];

        magazineSize = magazine.getMagSize();
        reloadSpeed = magazine.getReloadSpeed();
        reserveAmmo = receiver.getMags() * magazineSize;
        runSpeed += magazine.getRunSpeed();
    }

    /**
     * Apply the stats from the Muzzle to this Weapon.
     */
    private void applyMuzzleStats()
    {
        Muzzle muzzle = (Muzzle) attachments[stdef.MUZZLE];

        damage += muzzle.getDamage(weaponIndex);
        rangeLow += muzzle.getRangeLow(weaponIndex);
        rangeMax += muzzle.getRangeMax(weaponIndex);
        recoil += muzzle.getRecoil(weaponIndex);
        spreadAim += muzzle.getSpreadAim(weaponIndex);
        spreadAimMax += muzzle.getSpreadAimMax(weaponIndex);
        spreadHip += muzzle.getSpreadHip(weaponIndex);
        spreadHipMax += muzzle.getSpreadHipMax(weaponIndex);
    }

    /**
     * Apply the stats from the Scope to this Weapon.
     */
    private void applyScopeStats()
    {
        Scope scope = (Scope) attachments[stdef.SCOPE];

        scopeInTime = scope.getScopeInTime();
        zoomAmount = scope.getZoomAmount();

    }

    /**
     * Apply the stats from the Stock to this Weapon.
     */
    private void applyStockStats()
    {
        Stock stock = (Stock) attachments[stdef.STOCK];

        spreadHip += stock.getSpreadHip(weaponIndex);
        spreadHipMax += stock.getSpreadHipMax(weaponIndex);
        spreadAim += stock.getSpreadAim(weaponIndex);
        spreadAimMax += stock.getSpreadAimMax(weaponIndex);
        rangeLow += stock.getRangeLow(weaponIndex);
        rangeMax += stock.getRangeMax(weaponIndex);
        runSpeed += stock.getRunSpeed(weaponIndex);
        recoil += stock.getRecoil(weaponIndex);
        scopeInTime += stock.getScopeInTime(weaponIndex);
    }

    /**
     * Formats all the data that will be displayed to avoid showing roundoff
     * error.
     */
    private void formatData()
    {
        DecimalFormat df = new DecimalFormat("0.000000");

        recoil = formatDouble(recoil, df);
        reloadSpeed = formatDouble(reloadSpeed, df);
        runSpeed = formatDouble(runSpeed, df);
        scopeInTime = formatDouble(scopeInTime, df);
        spreadAim = formatDouble(spreadAim, df);
        spreadAimMax = formatDouble(spreadAimMax, df);
        spreadHip = formatDouble(spreadHip, df);
        spreadHipMax = formatDouble(spreadHipMax, df);
        zoomAmount = formatDouble(zoomAmount, df);
    }

    private double formatDouble(double number, DecimalFormat df)
    {
        return Double.parseDouble(df.format(number));
    }

    /**
     * @param partID The partID as defined in the StandardDefinitions file
     * @param newAttachment The attachment (Barrel, Magazine, Muzzle, Scope or
     * Stock) that is going to be attached to the Receiver.
     */
    public final void setAttachment(final int partID, final WeaponPart newAttachment)
    {
        attachments[partID] = newAttachment;
        setCurrentlyHasPart(partID, true);
        updateWeaponStats();
    }

    /**
     * Sets the currently equipped Receiver.
     *
     * @param newRec The Receiver to be equipped.
     */
    public final void setReceiver(final Receiver newRec)
    {
        receiver = newRec;
        weaponIndex = receiver.getIndexOfWeaponShortName(receiver.getShortName());

        setCanHavePart(stdef.BARREL, receiver.getCanHavePart(stdef.BARREL));
        setCanHavePart(stdef.MAGAZINE, receiver.getCanHavePart(stdef.MAGAZINE));
        setCanHavePart(stdef.MUZZLE, receiver.getCanHavePart(stdef.MUZZLE));
        setCanHavePart(stdef.SCOPE, receiver.getCanHavePart(stdef.SCOPE));
        setCanHavePart(stdef.STOCK, receiver.getCanHavePart(stdef.STOCK));

        setCurrentlyHasPart(stdef.BARREL, false);
        setCurrentlyHasPart(stdef.MAGAZINE, false);
        setCurrentlyHasPart(stdef.MUZZLE, false);
        setCurrentlyHasPart(stdef.SCOPE, false);
        setCurrentlyHasPart(stdef.STOCK, false);

        updateWeaponStats();
    }

    /**
     * @return The currently equipped receiver.
     */
    public final Receiver getReceiver()
    {
        return receiver;
    }

    public EquipmentPart getAttachment(int typeID)
    {
        return attachments[typeID];
    }

    /**
     * @return Returns if weapon needs a stock but does not currently have one
     * equipped.
     */
    public final boolean barrelEnablesStock()
    {
        Barrel barrel = (Barrel) attachments[stdef.BARREL];
        return barrel.getEnableStock();
    }

    public final boolean getCanHavePart(final int partID)
    {
        return canHavePart[partID];
    }

    public void setCanHavePart(final int partID, final boolean canHave)
    {
        canHavePart[partID] = canHave;
    }

    public final boolean getCurrentlyHasPart(final int partID)
    {
        return currentlyHasPart[partID];
    }

    public void setCurrentlyHasPart(final int partID, final boolean currentlyHas)
    {
        currentlyHasPart[partID] = currentlyHas;
    }

    /**
     * @return A String of representation of this Weapons data.
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder();

        returnString.append(receiver.getFullName() + "\n");
        returnString.append("Damage: " + damage + "\n");
        returnString.append("Rate of Fire: " + rateOfFire + "\n");
        returnString.append("Ammo: [" + magazineSize + "/" + reserveAmmo + "]\n");
        returnString.append("Reload Speed: " + reloadSpeed + "\n");
        returnString.append("Range: [" + rangeLow + "/" + rangeMax + "]\n");
        returnString.append("Run Speed: " + runSpeed + "\n");
        returnString.append("Recoil : " + recoil + "\n");
        returnString.append("Spread Hip: [" + spreadHip + "/" + spreadHipMax + "]\n");
        returnString.append("Spread Aim: [" + spreadAim + "/" + spreadAimMax + "]\n");
        returnString.append("Scope: " + zoomAmount + "\n");
        returnString.append("Scope-in Time: " + scopeInTime + "\n");

        returnString.append("Barrl: " + attachments[stdef.BARREL].getFullName() + "\n");
        returnString.append("Magzn: " + attachments[stdef.MAGAZINE].getFullName() + "\n");
        returnString.append("Muzle: " + attachments[stdef.MUZZLE].getFullName() + "\n");
        returnString.append("Scope: " + attachments[stdef.SCOPE].getFullName() + "\n");
        if (canHavePart[stdef.STOCK]) {
            returnString.append("Stock: " + attachments[stdef.STOCK].getFullName() + "\n");
        }

        return returnString.toString();
    }
}
