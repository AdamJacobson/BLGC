package Parts;

import Main.TaggedDataList;

/**
 * Super class for weapon parts: Receiver, Barrel, Magazine, Muzzle, Scope and Stock
 */
public class WeaponPart extends EquipmentPart
{

    /**
     * A copy of the StandardDefinitions file for code shortening.
     */
    private static StandardDefinitions sd = new StandardDefinitions();

    /**
     * A list of the weapon short names which represent all of the receivers.
     */
    public static String[] weaponShortNames = new String[sd.MAX_NUMBER_OF_PARTS];

    /**
     * The number of receivers there are.
     */
    private static int numberOfReceivers;

    /**
     * Indicates for each weapon receiver given by the list weaponShortNames in WeaponPart if this
     * particular part can be used with that weapon receiver. This is only needed for parts that
     * apply to more that one weapon receiver.
     */
    private boolean[] usableOn = new boolean[sd.MAX_NUMBER_OF_PARTS];

    /**
     * Adds a given string to the list of receiver short names.
     *
     * @param name The name to be added
     */
    public final void addReceiverShortName(final String name)
    {
        checkShortNameIsUnique(name);
        weaponShortNames[numberOfReceivers] = name;
        numberOfReceivers++;
    }

    /**
     * Checks to ensure if the weaponShortName does not already exist in the list. If it is unique,
     * nothing happens. Else, print an error message and exit the program.
     *
     * @param name The weapon short name in question.
     */
    private void checkShortNameIsUnique(final String name)
    {
        for (int i = 0; i < numberOfReceivers; i++)
        {
            if (name.equals(weaponShortNames[i]))
            {
                System.err.println("Receiver short names must be unique. Already found one or more"
                        + " instances of <shortName>" + name + "<shortName>.");
                System.exit(0);
            }
        }
    }

    /**
     * @return the weapon short name for the given index.
     * @param index The index
     */
    public final String getWeaponShortName(final int index)
    {
        return weaponShortNames[index];
    }

    /**
     * @return the weapon index for the given short name.
     * @param name The short name
     */
    public final int getIndexOfWeaponShortName(final String name)
    {
        for (int i = 0; i < weaponShortNames.length; i++)
        {
            if (name.equals(weaponShortNames[i]))
            {
                return i;
            }
        }
        System.err.println("Could not find receiver with <shortName>" + name + "</shortName>");
        System.exit(0);
        return -1;
    }

    /**
     * Simply passes the TaggedDataList to the super class.
     *
     * @param tdList The tagged Data list containing data about this part
     */
    @Override
    public void fillData(final TaggedDataList tdList)
    {
        super.fillData(tdList);
    }

    /**
     * @return The string representation of this object
     */
    @Override
    public String toString()
    {
        return super.toString();
    }

    /**
     * @param weaponIndex The weapon index which can be found using getIndexOfWeaponShortName
     * @return If this part is usable on the weapon index related to the given short name
     */
    public final boolean getUsableOn(final int weaponIndex)
    {
        return usableOn[weaponIndex];
    }

    /**
     * Sets if this part is usable on a given weapon index
     *
     * @param weaponIndex The given weapon index
     * @param setUsableOn If this part is usable
     */
    public final void setUsableOn(final int weaponIndex, final boolean setUsableOn)
    {
        this.usableOn[weaponIndex] = setUsableOn;
    }
}
