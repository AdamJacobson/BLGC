package Parts;

import Main.TaggedDataList;

/**
 * Superclass for all armor parts: Helmet, BodyArmor and LegArmor
 * Contains fields: health, runSpeed and stamina
 */
public class ArmorPart extends EquipmentPart
{

    /**
     * Basic constructor.
     */
    public ArmorPart()
    {
    }
    
    /**
     * Modification to the agents base health.
     */
    private int health;

    /**
     * Modification to the agents base running speed.
     */
    private double runSpeed;

    /**
     * Modification to the agents base stamina.
     */
    private double stamina;

    /**
     * Goes through the TaggedDataList made by the XMLReader. Looks for tag names that are
     * relevant to this type of object and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished.
     *
     * @param tdList The TaggedDataList containing the relevant data.
     */
    @Override
    public void fillData(final TaggedDataList tdList)
    {
        double dataDouble;
        boolean dataUsed;

        dataType type;
        String tagName;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataDouble = Double.NEGATIVE_INFINITY;
            dataUsed = true;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            {
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }

            switch (tagName.toLowerCase())
            {
                case "health":
                    this.setHealth((int) dataDouble);
                    break;
                case "runspeed":
                    this.setRunSpeed(dataDouble);
                    break;
                case "stamina":
                    this.setStamina(dataDouble);
                    break;
                default:
                    dataUsed = false;
                    break;
            }
            if (dataUsed)
            {
                tdList.flagDataAsUsed(i);
            }

        }
        tdList.clearUsedData();
        super.fillData(tdList);
    }

    /**
     * @return A string of the relevant data contained in this ArmorPart object.
     */
    @Override
    public String toString()
    {
        StringBuilder returnString = new StringBuilder();

        returnString.append(super.toString());

        returnString.append("Health\t\t" + this.getHealth() + "\n"
                + "Speed\t\t" + this.getRunSpeed() + "\n"
                + "Stamina\t\t" + this.getStamina() + "\n");

        return returnString.toString();
    }

    /**
     * @return The modification to the Agents health.
     */
    public final int getHealth()
    {
        return health;
    }

    /**
     * Sets the modification to the Agents health.
     *
     * @param newHealth The health to be set
     */
    public final void setHealth(final int newHealth)
    {
        this.health = newHealth;
    }

    /**
     * @return the modification to the Agents runSpeed.
     */
    public final double getRunSpeed()
    {
        return runSpeed;
    }

    /**
     * Sets the modification to the Agents runSpeed.
     *
     * @param newRunSpeed The runSpeed to be set
     */
    public final void setRunSpeed(final double newRunSpeed)
    {
        this.runSpeed = newRunSpeed;
    }

    /**
     * @return the modification to the Agents stamina.
     */
    public final double getStamina()
    {
        return stamina;
    }

    /**
     * Sets the modification to the Agents stamina.
     *
     * @param newStamina The stamina to be set
     */
    public final void setStamina(final double newStamina)
    {
        this.stamina = newStamina;
    }
}
