package Parts;

import Main.TaggedDataList;

/**
 * Barrels are weapon attachments. Barrels are designed to be usable on
 * more than one receiver but have different stats for each one. For this reason
 * most of the fields in this class are arrays where each index corresponds to
 * a different receiver.
 */
public class Barrel extends WeaponPart
{
    
    /**
     * A copy of the StandardDefinitions file for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();

    /**
     * Modification to base damage done by a weapon.
     */
    private int[] damage = new int[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to spread when firing while aiming.
     */
    private double[] spreadAim = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to spread when firing from the hip.
     */
    private double[] spreadHip = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to max base spread when firing while aiming.
     */
    private double[] spreadAimMax = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to max base spread when firing from the hip.
     */
    private double[] spreadHipMax = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Weapon damage begins to drop off after shot travels this distance (meters).
     */
    private int[] rangeLow = new int[sd.MAX_NUMBER_OF_PARTS];

    /**
     * No damage is done after shot travels this distance (meters).
     */
    private int[] rangeMax = new int[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Modification to player run speed.
     */
    private double[] runSpeed = new double[sd.MAX_NUMBER_OF_PARTS];

    /**
     * A special case in which a pistol barrel allows the use of a stock.
     */
    private boolean enableStock;

    /**
     * Create a new Barrel with data from a TaggedDataList.
     *
     * @param tdList The TaggedDataList holding the data relevant to this Barrel.
     */
    public Barrel(final TaggedDataList tdList)
    {
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
        int weaponIndex = 0;
        boolean usedData;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataString = "NO DATA FOUND";
            dataBoolean = false;
            dataDouble = Double.NEGATIVE_INFINITY;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.BOOLEAN)
            { // Boolean
                dataBoolean = Boolean.parseBoolean(tdList.getItemDataByIndex(i));
            }
            else if (type == dataType.DOUBLE)
            { // Double
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            else
            {
                dataString = tdList.getItemDataByIndex(i);
            }
            
            usedData = true;

            switch (tagName.toLowerCase())
            {
                case "matchesweapon":
                    weaponIndex = getIndexOfWeaponShortName(dataString);
                    setUsableOn(weaponIndex, true);
                    break;
                case "damage":
                    this.setDamage(weaponIndex, (int) dataDouble);
                    break;
                case "spreadaim":
                    this.setSpreadAim(weaponIndex, dataDouble);
                    break;
                case "spreadhip":
                    this.setSpreadHip(weaponIndex, dataDouble);
                    break;
                case "spreadaimmax":
                    this.setSpreadAimMax(weaponIndex, dataDouble);
                    break;
                case "spreadhipmax":
                    this.setSpreadHipMax(weaponIndex, dataDouble);
                    break;
                case "rangelow":
                    this.setRangeLow(weaponIndex, (int) dataDouble);
                    break;
                case "rangemax":
                    this.setRangeMax(weaponIndex, (int) dataDouble);
                    break;
                case "runspeed":
                    this.setRunSpeed(weaponIndex, dataDouble);
                    break;
                case "enablestock":
                    enableStock = dataBoolean;
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

    /**
     * @return The string representation of this object
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder();

        returnString.append(super.toString());

        for (int i = 0; i < weaponShortNames.length; i++)
        {
            if (getUsableOn(i))
            {
                returnString.append(getWeaponShortName(i) + " ------------\n");
                returnString.append("Damage:\t\t" + damage[i] + "\n");
                returnString.append("Spread Hip/Max: " + spreadHip[i] + "/" + spreadHipMax[i] + "\n");
                returnString.append("Spread Aim/Max: " + spreadAim[i] + "/" + spreadAimMax[i] + "\n");
                returnString.append("Range Low/Max:  " + rangeLow[i] + "/" + rangeMax[i] + "\n");
                returnString.append("Run Speed: \t" + runSpeed[i] + "\n");
            }
        }
        returnString.append("===========================\n");

        return returnString.toString();
    }

    /**
     * Returns the damage for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return damage[index]
     */
    public final int getDamage(final int index)
    {
        return damage[index];
    }

    /**
     * Sets the damage for this barrel for the given receiver index.
     *
     * @param index     The index of the weapon short name
     * @param newDamage The value to be set
     */
    public final void setDamage(final int index, final int newDamage)
    {
        this.damage[index] = newDamage;
    }

    /**
     * Returns the spreadAim for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return spreadAim[index]
     */
    public final double getSpreadAim(final int index)
    {
        return spreadAim[index];
    }

    /**
     * Sets the spreadAim for this barrel for the given receiver index.
     *
     * @param index        The index of the weapon short name
     * @param newSpreadAim The value to be set
     */
    public final void setSpreadAim(final int index, final double newSpreadAim)
    {
        this.spreadAim[index] = newSpreadAim;
    }

    /**
     * Returns the spreadHip for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return spreadHip[index]
     */
    public final double getSpreadHip(final int index)
    {
        return spreadHip[index];
    }

    /**
     * Sets the spreadHip for this barrel for the given receiver index.
     *
     * @param index        The index of the weapon short name
     * @param newSpreadHip The value to be set
     */
    public final void setSpreadHip(final int index, final double newSpreadHip)
    {
        this.spreadHip[index] = newSpreadHip;
    }

    /**
     * Returns the spreadAimMax for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return spreadAimMax[index]
     */
    public final double getSpreadAimMax(final int index)
    {
        return spreadAimMax[index];
    }

    /**
     * Sets the spreadAimMax for this barrel for the given receiver index.
     *
     * @param index           The index of the weapon short name
     * @param newSpreadAimMax The value to be set
     */
    public final void setSpreadAimMax(final int index, final double newSpreadAimMax)
    {
        this.spreadAimMax[index] = newSpreadAimMax;
    }

    /**
     * Returns the spreadHipMax for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return spreadHipMax[index]
     */
    public final double getSpreadHipMax(final int index)
    {
        return spreadHipMax[index];
    }

    /**
     * Sets the spreadHipMax for this barrel for the given receiver index.
     *
     * @param index           The index of the weapon short name
     * @param newSpreadHipMax The value to be set
     */
    public final void setSpreadHipMax(final int index, final double newSpreadHipMax)
    {
        this.spreadHipMax[index] = newSpreadHipMax;
    }

    /**
     * Returns the rangeLow for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return rangeLow[index]
     */
    public final int getRangeLow(final int index)
    {
        return rangeLow[index];
    }

    /**
     * Sets the rangeLow for this barrel for the given receiver index.
     *
     * @param index       The index of the weapon short name
     * @param newRangeLow The value to be set
     */
    public final void setRangeLow(final int index, final int newRangeLow)
    {
        this.rangeLow[index] = newRangeLow;
    }

    /**
     * Returns the rangeMax for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return rangeMax[index]
     */
    public final int getRangeMax(final int index)
    {
        return rangeMax[index];
    }

    /**
     * Sets the rangeMax for this barrel for the given receiver index.
     *
     * @param index       The index of the weapon short name
     * @param newRangeMax The value to be set
     */
    public final void setRangeMax(final int index, final int newRangeMax)
    {
        this.rangeMax[index] = newRangeMax;
    }

    /**
     * Returns the runSpeed for this barrel for the receiver index.
     *
     * @param index The index of the weapon short name
     * @return runSpeed[index]
     */
    public final double getRunSpeed(final int index)
    {
        return runSpeed[index];
    }

    /**
     * Sets the runSpeed for this barrel for the given receiver index.
     *
     * @param index       The index of the weapon short name
     * @param newRunSpeed The value to be set
     */
    public final void setRunSpeed(final int index, final double newRunSpeed)
    {
        this.runSpeed[index] = newRunSpeed;
    }

    /**
     * @return if this barrel enables the Stock on a weapon.
     * This is intended for a single special case for pistols.
     */
    public final boolean getEnableStock()
    {
        return enableStock;
    }
}
