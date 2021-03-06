package Parts;

import Main.TaggedDataList;

/**
 * A piece of Body Armor that an agent can equip.
 */
public class BodyArmor extends ArmorPart
{

    /**
     * The number of gear slots the armor piece provides
     */
    private int gearSlots;

    /**
     * Create a new BodyArmor with data from a TaggedDataList.
     *
     * @param tdList The TaggedDataList holding the data relevant to this BodyArmor.
     */
    public BodyArmor(final TaggedDataList tdList)
    {
        this.fillData(tdList);
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
        double dataDouble;

        dataType type;
        String tagName;
        
        boolean usedData;

        // For each item in the taggedDataList
        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataDouble = Double.NEGATIVE_INFINITY;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            
            usedData = true;

            switch (tagName.toLowerCase())
            {
                case "gearslots":
                    this.setGearSlots(dataDouble);
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
     * @return String representation of this object.
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder("--BODY ARMOR--\n");
        returnString.append(super.toString());
        returnString.append("Gear Slots\t" + this.getGearSlots() + "\n");
        returnString.append("---------------------------\n");

        return returnString.toString();
    }

    /**
     * Returns the gearSlots for this BodyArmor.
     *
     * @return number of gearSlots
     */
    public final int getGearSlots()
    {
        return gearSlots;
    }

    /**
     * Sets the gearSlots for this BodyArmor.
     *
     * @param newGearSlots The value to be set
     */
    public final void setGearSlots(final double newGearSlots)
    {
        this.gearSlots = (int) newGearSlots;
    }
}
