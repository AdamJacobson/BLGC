package Parts;

import Main.TaggedDataList;

/**
 * A Helmet that an agent can equip.
 */
public class Helmet extends ArmorPart {

    /**
     * The amount of damage (%) reduction to head shots while wearing this helmet.
     */
    private int armor;

    /**
     * The amount of time that HRV can be turned on.
     */
    private double hrvEnergy;

    /**
     * The speed at which HRV energy recharges.
     */
    private double hrvRecharge;

    /**
     * Creates a new Helmet with data from a TaggedDataList.
     *
     * @param list The TaggedDataList holding the data relevant to this Helmet.
     */
    public Helmet(final TaggedDataList list)
    {
        armor = 0;
        hrvEnergy = 0.0;
        hrvRecharge = 0.0;

        this.fillData(list);
    }

    /** Goes through the TaggedDataList made by the XMLReader. Looks for tag names that are
     * relevant to this type of object and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished. 
     * 
     * @param tdList The taggedDataList containing the data read from the XML tree.
     */
    @Override
    public final void fillData(final TaggedDataList tdList)
    {
        double dataDouble;
        dataType dataType;
        String tagName;
        boolean usedData;

        for (int i = 0; i < tdList.getNumberOfItems(); i++) {
            dataDouble = Double.NEGATIVE_INFINITY;
            usedData = true;
            
            dataType = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (dataType == dataType.DOUBLE) {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }

            switch (tagName.toLowerCase()) {
                case "armor":
                    this.setArmor((int) dataDouble);
                    break;
                case "hrvenergy":
                    this.setHrvEnergy(dataDouble);
                    break;
                case "hrvrecharge":
                    this.setHrvRecharge(dataDouble);
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
        StringBuilder returnString = new StringBuilder("--HELMET--\n");
        returnString.append(super.toString());
        returnString.append("Armor\t\t" + this.getArmor() + "\n");
        returnString.append("Energy\t\t" + this.getHrvEnergy() + "\n");
        returnString.append("Recharge\t" + this.getHrvRecharge() + "\n");
        returnString.append("---------------------------\n");
        
        return returnString.toString();
    }

    /**
     * @return The armor for this Helmet 
     */
    public final int getArmor()
    {
        return armor;
    }

    /**
     * Sets the armor for this Helmet
     * @param armorAmnt The amount to be set
     */
    private void setArmor(final int armorAmnt)
    {
        this.armor = armorAmnt;
    }

    /**
     * @return The HRV Energy for this Helmet 
     */
    public final double getHrvEnergy()
    {
        return hrvEnergy;
    }

    /**
     * Sets the HRV Energy for this Helmet
     * @param hrvEnergyAmnt The amount to be set
     */
    private void setHrvEnergy(final double hrvEnergyAmnt)
    {
        this.hrvEnergy = hrvEnergyAmnt;
    }

    /**
     * @return The HRV Recharge for this Helmet 
     */
    public final double getHrvRecharge()
    {
        return hrvRecharge;
    }

    /**
     * Sets the HRV Recharge for this Helmet
     * @param hrvRechargeRate The amount to be set
     */
    private void setHrvRecharge(final double hrvRechargeRate)
    {
        this.hrvRecharge = hrvRechargeRate;
    }
}
