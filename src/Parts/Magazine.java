package Parts;

import Main.TaggedDataList;

/**
 * Magazines are weapon attachments. All magazines are unique to one receiver
 * type. There are no universal magazines, nor is it possible to make one.
 */
public class Magazine extends WeaponPart {

    /**
     * Number of shots held in the magazine.
     */
    private int magSize;

    /**
     * Reload speed in seconds.
     */
    private double reloadSpeed;

    /**
     * Modification of players run speed.
     */
    private double runSpeed;

    /**
     * Create a new Magazine with data from a TaggedDataList.
     *
     * @param tdList The TaggedDataList holding the data relevant to this
     * Magazine.
     */
    public Magazine(final TaggedDataList tdList)
    {
        fillData(tdList);
    }

    /**
     * Goes through the TaggedDataList made by the XMLReader. Looks for tag
     * names that are relevant to this type of object and modifies data
     * accordingly. Passes the TaggedDataList to the super class when finished.
     *
     * @param tdList The taggedDataList containing the data read from the XML
     * tree.
     */
    @Override
    public final void fillData(final TaggedDataList tdList)
    {
        String dataString;
        double dataDouble;

        dataType type;
        String tagName;
        int weaponIndex;
        boolean usedData;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataString = "NO DATA FOUND";
            dataDouble = Double.NEGATIVE_INFINITY;
            usedData = true;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            else
            {
                dataString = tdList.getItemDataByIndex(i);
            }

            switch (tagName.toLowerCase())
            {
                case "matchesweapon":
                    weaponIndex = getIndexOfWeaponShortName(dataString);
                    setUsableOn(weaponIndex, true);
                    break;
                case "magsize":
                    this.setMagSize((int) dataDouble);
                    break;
                case "reloadspeed":
                    this.setReloadSpeed(dataDouble);
                    break;
                case "runspeed":
                    this.setRunSpeed(dataDouble);
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
        returnString.append("-- MAGAZINE --\n");
        returnString.append("Mag Size\t" + magSize + "\n");
        returnString.append("Reload Speed:\t" + reloadSpeed + "\n");
        returnString.append("Run Speed:\t" + runSpeed + "\n");
        returnString.append("===========================\n");

        return returnString.toString();
    }

    /**
     * @return The number of bullets this magazine holds
     */
    public final int getMagSize()
    {
        return magSize;
    }

    /**
     * Sets the number of bullets that this magazine holds
     *
     * @param newMagSize The amount to be set
     */
    private void setMagSize(final int newMagSize)
    {
        this.magSize = newMagSize;
    }

    /**
     * @return The reload speed of this magazine in seconds
     */
    public final double getReloadSpeed()
    {
        return reloadSpeed;
    }

    /**
     * Sets the reload speed of this magazine in seconds
     *
     * @param newReloadSpeed The reload speed
     */
    private void setReloadSpeed(final double newReloadSpeed)
    {
        this.reloadSpeed = newReloadSpeed;
    }

    /**
     * @return The modification to the agents run speed with this magazine
     */
    public final double getRunSpeed()
    {
        return runSpeed;
    }

    /**
     * Sets the modification to the agents run speed with this magazine
     *
     * @param newRunSpeed The change in speed.
     */
    private void setRunSpeed(final double newRunSpeed)
    {
        this.runSpeed = newRunSpeed;
    }
}
