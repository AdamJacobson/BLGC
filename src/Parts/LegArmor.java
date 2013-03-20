package Parts;

import Main.TaggedDataList;

/**
 * A piece of Leg Armor that an agent can equip.
 */
public class LegArmor extends ArmorPart
{

    /**
     * The number of gear slots the armor piece provides
     */
    private int gearSlots;

    /**
     * Creates a new Leg Armor with data from a TaggedDataList.
     *
     * @param list The TaggedDataList holding the data relevant to this LegArmor.
     */
    public LegArmor(final TaggedDataList list)
    {
        this.fillData(list);
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

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataDouble = Double.NEGATIVE_INFINITY;
            usedData = true;
            
            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }

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
        StringBuilder returnString = new StringBuilder("--LEG ARMOR--\n");
        returnString.append(super.toString());
        returnString.append("Gear Slots\t" + this.getGearSlots() + "\n");
        returnString.append("---------------------------\n");

        return returnString.toString();
    }

    public int getGearSlots()
    {
        return gearSlots;
    }

    public void setGearSlots(double gearSlots)
    {
        this.gearSlots = (int) gearSlots;
    }
}
