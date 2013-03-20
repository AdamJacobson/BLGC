package Parts;

import Main.TaggedDataList;

/**
 * The main body of a primary or secondary weapon. All other parts must be connected
 * to a receiver.
 */
public class Receiver extends WeaponPart
{

    /**
     * Abbreviated name for the receiver.
     */
    private String shortName;

    /**
     * Weapons are either primary or secondary.
     */
    private boolean isPrimary;

    /**
     * Can the receiver handle an attachment of a given partID.
     * The index refers to the partID as defined in StandardDefinitions.java
     */
    private boolean[] canHavePart;

    /**
     * Number of additional magazines the weapon comes with. Usually 4 but with some exceptions.
     */
    private int mags;

    /**
     * The base damage done by the receiver.
     */
    private int damage;

    /**
     * Rate of fire for the weapon. Only determined by the receiver.
     */
    private int rof;

    /**
     * Base spread when firing from the hip.
     */
    private double spreadHip;

    /**
     * Base spread when firing while aiming.
     */
    private double spreadAim;

    /**
     * Max base spread when firing from the hip.
     */
    private double spreadHipMax;

    /**
     * Max base spread when firing while aiming.
     */
    private double spreadAimMax;

    /**
     * Weapon damage begins to drop off after shot travels this distance in meters.
     */
    private int rangeLow;

    /**
     * No damage is done after shot travels this distance in meters.
     */
    private int rangeMax;

    /**
     * Base run speed.
     */
    private double runSpeed;

    /**
     * Base recoil when firing.
     */
    private double recoil;

    /**
     * Create a new Receiver with data from a TaggedDataList.
     *
     * @param tdList  The TaggedDataList holding the data relevant to this Receiver.
     * @param primary If this Receiver is a primary receiver
     */
    public Receiver(final TaggedDataList tdList, final boolean primary)
    {
        setPrimary(primary);

        canHavePart = new boolean[StandardDefinitions.TOTAL_NUMBER_OF_PART_TYPES
                - StandardDefinitions.NUMBER_OF_INDEPENDENT_PART_TYPES];

        setCanHavePart(StandardDefinitions.BARREL, true);
        setCanHavePart(StandardDefinitions.MAGAZINE, true);
        setCanHavePart(StandardDefinitions.MUZZLE, true);
        setCanHavePart(StandardDefinitions.SCOPE, true);
        setCanHavePart(StandardDefinitions.STOCK, true);

        fillData(tdList);
    }

    /**
     * Goes through the TaggedDataList made by the XMLReader. Looks for tag names that are
     * relevant to this type of object and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished.
     *
     * @param tdList The taggedDataList containing the data read from the XML tree.
     */
    @Override
    public final void fillData(final TaggedDataList tdList)
    {
        String dataString;
        boolean dataBoolean;
        double dataDouble;

        dataType type;
        String tagName;
        boolean usedData;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataString = "NO DATA FOUND";
            dataBoolean = false;
            dataDouble = Double.NEGATIVE_INFINITY;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.BOOLEAN)
            {
                dataBoolean = Boolean.parseBoolean(tdList.getItemDataByIndex(i));
            }
            else if (type == dataType.DOUBLE)
            {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            else
            {
                dataString = tdList.getItemDataByIndex(i);
            }
            
            usedData = true;

            switch (tagName.toLowerCase())
            {
                case "shortname":
                    setShortName(dataString);
                    addReceiverShortName(dataString);
                    break;
                case "primary":
                    setPrimary(dataBoolean);
                    break;
                case "hasbarrel":
                    setCanHavePart(StandardDefinitions.BARREL, dataBoolean);
                    break;
                case "hasmuzzle":
                    setCanHavePart(StandardDefinitions.MUZZLE, dataBoolean);
                    break;
                case "hasmagazine":
                    setCanHavePart(StandardDefinitions.MAGAZINE, dataBoolean);
                    break;
                case "hasscope":
                    setCanHavePart(StandardDefinitions.SCOPE, dataBoolean);
                    break;
                case "hasstock":
                    setCanHavePart(StandardDefinitions.STOCK, dataBoolean);
                    break;
                case "mags":
                    setMags((int) dataDouble);
                    break;
                case "damage":
                    setDamage((int) dataDouble);
                    break;
                case "rof":
                    setRof((int) dataDouble);
                    break;
                case "spreadaim":
                    setSpreadAim(dataDouble);
                    break;
                case "spreadhip":
                    setSpreadHip(dataDouble);
                    break;
                case "spreadaimmax":
                    setSpreadAimMax(dataDouble);
                    break;
                case "spreadhipmax":
                    setSpreadHipMax(dataDouble);
                    break;
                case "rangelow":
                    setRangeLow((int) dataDouble);
                    break;
                case "rangemax":
                    setRangeMax((int) dataDouble);
                    break;
                case "runspeed":
                    setRunSpeed(dataDouble);
                    break;
                case "recoil":
                    setRecoil(dataDouble);
                    break;
                default:
                    usedData = false;
                    break;
            }
            
            if (usedData)
            {
                tdList.flagDataAsUsed(i);
            } 
        }
        
        tdList.clearUsedData();
        super.fillData(tdList);
    }

//    public String toString()
//    {
//        // fullName, description and siloImage
//        String returnString = super.toString();
//
//        returnString += "Short Name: " + getShortName() + "\n";
//        returnString += "Primary: " + isPrimary + "\n";
//
//        returnString += "    hasBarrel: " + canHaveBarrel + "\n";
//        returnString += "    hasMagazine: " + canHaveMagazine + "\n";
//        returnString += "    hasMuzzle: " + canHaveMuzzle + "\n";
//        returnString += "    hasScope: " + canHaveScope + "\n";
//        returnString += "    hasStock: " + canHaveStock + "\n";
//
//        returnString += "Damage: " + damage + "\n";
//        returnString += "Rate of Fire: " + rof + "\n";
//        returnString += "Spread Hip/Max: " + spreadHip + "/" + spreadHipMax + "\n";
//        returnString += "Spread Aim/Max: " + spreadAim + "/" + spreadAimMax + "\n";
//        returnString += "Range Low/Max:  " + rangeLow + "/" + rangeMax + "\n";
//        returnString += "Recoil: \t" + recoil + "\n";
//        returnString += "---------------------------\n";
//
//        return returnString;
//    }
    /**
     * @return Return if the Receiver is a primary Receiver.
     */
    public final boolean isPrimary()
    {
        return isPrimary;
    }

    /**
     * Sets if this receiver is primary
     *
     * @param primary Is the receiver is primary
     */
    private void setPrimary(final boolean primary)
    {
        this.isPrimary = primary;
    }

    /**
     * @return The shortName of this receiver
     */
    public final String getShortName()
    {
        return shortName;
    }

    /**
     * Sets the shortName for this receiver
     *
     * @param newShortName The shortName
     */
    private void setShortName(final String newShortName)
    {
        this.shortName = newShortName;
    }

    /**
     * @return The number of additional magazines this receiver has
     */
    public final int getMags()
    {
        return mags;
    }

    /**
     * Sets the number of additional magazines this receiver has.
     *
     * @param newMags The number of additional magazines
     */
    private void setMags(final int newMags)
    {
        this.mags = newMags;
    }

    /**
     * @return The base damage this receiver does. 
     */
    public final int getDamage()
    {
        return damage;
    }

    /**
     * Sets the base damage this receiver does.
     *
     * @param newDamage the base damage this receiver will do.
     */
    private void setDamage(final int newDamage)
    {
        this.damage = newDamage;
    }

    /**
     * @return The rate of fire for this receiver.
     */
    public final int getRof()
    {
        return rof;
    }

    /**
     * Sets the rate of fire for this receiver.
     *
     * @param newROF the rate of fire
     */
    private void setRof(final int newROF)
    {
        this.rof = newROF;
    }

    /**
     * @return The base spread while aiming with this receiver
     */
    public final double getSpreadAim()
    {
        return spreadAim;
    }

    /**
     * Sets the base spread while aiming with this receiver
     *
     * @param newSpreadAim the base spread while aiming
     */
    private void setSpreadAim(final double newSpreadAim)
    {
        this.spreadAim = newSpreadAim;
    }

    /**
     * @return The base spread while firing from the hip with this receiver
     */
    public final double getSpreadHip()
    {
        return spreadHip;
    }

    /**
     * Sets the base spread while firing from the hip with this receiver
     *
     * @param newSpreadHip the base spread while firing from the hip
     */
    private void setSpreadHip(final double newSpreadHip)
    {
        this.spreadHip = newSpreadHip;
    }

    /**
     * @return The base max spread while aiming with this receiver
     */
    public final double getSpreadAimMax()
    {
        return spreadAimMax;
    }

    /**
     * Sets the base max spread while while aiming with this receiver
     *
     * @param newSpreadAimMax The base max spread while while aiming
     */
    private void setSpreadAimMax(final double newSpreadAimMax)
    {
        this.spreadAimMax = newSpreadAimMax;
    }

    /**
     * @return The base max spread while firing from the hip with this receiver
     */
    public final double getSpreadHipMax()
    {
        return spreadHipMax;
    }

    /**
     * Sets the base max spread while firing from the hip with this receiver
     *
     * @param newSpreadHipMax the base max spread while firing from the hip
     */
    private void setSpreadHipMax(final double newSpreadHipMax)
    {
        this.spreadHipMax = newSpreadHipMax;
    }

    /**
     * @return The base distance at which damage begins to drop off
     */
    public final int getRangeLow()
    {
        return rangeLow;
    }

    /**
     * Sets the base distance at which damage begins to drop off
     *
     * @param newRangeLow the base distance
     */
    private void setRangeLow(final int newRangeLow)
    {
        this.rangeLow = newRangeLow;
    }

    /**
     * @return The base distance at which damage is reduced to zero
     */
    public final int getRangeMax()
    {
        return rangeMax;
    }

    /**
     * Sets the base distance at which damage is reduced to zero
     *
     * @param newRangeMax the base distance
     */
    private void setRangeMax(final int newRangeMax)
    {
        this.rangeMax = newRangeMax;
    }

    /**
     * @return The modification to the run speed of this receiver
     */
    public final double getRunSpeed()
    {
        return runSpeed;
    }

    /**
     * Sets the modification to the run speed of this receiver
     *
     * @param newRunSpeed The run speed
     */
    private void setRunSpeed(final double newRunSpeed)
    {
        this.runSpeed = newRunSpeed;
    }

    /**
     * @return The base recoil of this receiver.
     */
    public final double getRecoil()
    {
        return this.recoil;
    }

    /**
     * Sets the base recoil of this receiver.
     *
     * @param newRecoil The baser recoil
     */
    private void setRecoil(final double newRecoil)
    {
        this.recoil = newRecoil;
    }

    /**
     * @param partID The part type ID as defined in StandardDefinitions.java
     * @return If this receiver can use a part of part type ID partID
     */
    public final boolean getCanHavePart(final int partID)
    {
        return canHavePart[partID];
    }

    /**
     * Sets if this receiver can use a part of part type ID partID
     *
     * @param partID  The part type ID as defined in StandardDefinitions.java
     * @param canHave If this receiver can use that part.
     */
    private void setCanHavePart(final int partID, final boolean canHave)
    {
        canHavePart[partID] = canHave;
    }
}
