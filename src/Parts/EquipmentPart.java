package Parts;

import Main.TaggedDataList;

/**
 * Superclass for a weapon and armor parts. Includes fields for data that all items use.
 * Subclasses: ArmorPart, WeaponPart
 */
public class EquipmentPart
{
    
    private static final String electroMagDescription = 
            "Electro magazines deal slightly less damage to players, \n"
            + "and slightly increased damage to mechanical targets. \n"
            + "Does 90% base damage to normal targets. \n"
            + "Does additional 20% base damage to Hardsuits, Turrets, \n"
            + "Barricades and players affected by stun, EMP and digi effects. \n"
            + "Additionally slows the rotation speed of the Hardsuit for .25 seconds (does not stack).";
    
    private static final String toxicMagDescription = 
            "Toxic magazines deal periodic damage and obscure the target's vision. \n"
            + "Obscures target's vision. Deals 80% total damage to unaffected targets, \n"
            + "and 100% total damage to targets already suffering from toxic effects. \n"
            + "Half of the total damage is delivered up front, and the remaining half over several seconds.";

    private static final String fireMagDescription = 
            "Incendiary magazines deal periodic damage. \n"
            + "Deals 110% total damage, 70% damage up front, \n"
            + "and another 40% over several seconds. \n";
    
    private static final String explosiveMagDescription = 
            "Explosive magazines jar the target's vision and prevent \n"
            + "them from being revived if killed. Deals 80% damage and jars the targets vision. \n"
            + "If a target is killed with explosive ammo, it will prevent them from being revived." ;

    private static final String lightMagDescription = 
            "Light magazines increase run speed.";
    
    private static final String quickMagDescription = 
            "Quick magazines greatly increase reload speed.";
    
    private static final String expressMagDescription = 
            "Express magazines increase reload and run speeds.";
    
    private static final String standardMagDescription = 
            "Standard-issue magazine with no stat modification.";
    
    private static final String extendedMagDescription = 
            "Extended magazines extend ammo capacity, but decrease run speed.";
    
    private static final String drumMagDescription = 
            "Drummed magazines significantly increase ammo capacity, \n"
            + "but greatly reduce reload and run speed.";
    
    /**
     * Full name of the part.
     */
    private String fullName;

    /**
     * Filename for the items preview image
     */
    private String preview;

    /**
     * Description of the item.
     */
    private String description;

    /**
     * Required level to purchase the item.
     */
    private int level;

    /**
     * The prices for items in GP.
     */
    private int[] gpPrices;

    /**
     * The prices for items in ZEN.
     */
    private int[] zenPrices;

    /**
     * Static final index for price arrays for 1 day purchases.
     */
    private static final int DAY = 0;

    /**
     * Static final index for price arrays for 1 week purchases.
     */
    private static final int WEEK = 1;

    /**
     * Static final index for price arrays for 1 month purchases.
     */
    private static final int MONTH = 2;

    /**
     * Static final index for price arrays for 3 months purchases.
     */
    private static final int THREE_MONTH = 3;

    /**
     * Static final index for price arrays for permanent purchases.
     */
    private static final int PERMANENT = 4;

    /**
     * If true, an agent beings his career with this item. It is permanent and free.
     */
    private boolean standardIssue;

    /**
     * Constructor.
     */
    public EquipmentPart()
    {
        gpPrices = new int [5];
        zenPrices = new int [5];
    }

    /**
     * Looks for tag names that are relevant to this type of object
     * and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished.
     *
     * @param tdList The TaggedDataList containing the relevant data.
     */
    public void fillData(final TaggedDataList tdList)
    {
        String dataString;
        boolean dataBoolean;
        double dataDouble;

        dataType type;
        String tagName;

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
            
            switch (tagName.toLowerCase())
            {
                case "fullname":
                    this.setFullName(dataString);
                    break;
                case "description":
                    descriptionFiller(dataString);
                    break;
                case "level":
                    this.setLevel((int) dataDouble);
                    break;
                case "preview":
                    this.setPreview(dataString);
                    break;
                case "isstandardissue":
                    this.setStandardIssue(dataBoolean);
                    break;
                case "gp1":
                    this.setGpPrice(DAY, (int) dataDouble);
                    break;
                case "gp7":
                    this.setGpPrice(WEEK, (int) dataDouble);
                    break;
                case "gp30":
                    this.setGpPrice(MONTH, (int) dataDouble);
                    break;
                case "gp90":
                    this.setGpPrice(THREE_MONTH, (int) dataDouble);
                    break;
                case "gpperm":
                    this.setGpPrice(PERMANENT, (int) dataDouble);
                    break;
                case "zen1":
                    this.setZenPrice(DAY, (int) dataDouble);
                    break;
                case "zen7":
                    this.setZenPrice(WEEK, (int) dataDouble);
                    break;
                case "zen30":
                    this.setZenPrice(MONTH, (int) dataDouble);
                    break;
                case "zen90":
                    this.setZenPrice(THREE_MONTH, (int) dataDouble);
                    break;
                case "zenperm":
                    this.setZenPrice(PERMANENT, (int) dataDouble);
                    break;
                default:
                    System.out.println("Didn't find case for tag name <" + tagName + "> for item: "
                            + fullName);
                    break;
            }
        }
    }

    private void descriptionFiller(String desc)
    {
        if (desc.trim().startsWith("##"))
        {
            switch (desc.trim().substring(2).toLowerCase())
            {
                case ("electro_mag"):
                    this.setDescription(electroMagDescription);
                    break;
                case ("toxic_mag"):
                    this.setDescription(toxicMagDescription);
                    break;
                case ("fire_mag"):
                    this.setDescription(fireMagDescription);
                    break;
                case ("explosive_mag"):
                    this.setDescription(explosiveMagDescription);
                    break;
                case ("standard_mag"):
                    this.setDescription(standardMagDescription);
                    break;
                case ("light_mag"):
                    this.setDescription(lightMagDescription);
                    break;
                case ("quick_mag"):
                    this.setDescription(quickMagDescription);
                    break;
                case ("express_mag"):
                    this.setDescription(expressMagDescription);
                    break;
                case ("extended_mag"):
                    this.setDescription(extendedMagDescription);
                    break;
                case ("drum_mag"):
                    this.setDescription(drumMagDescription);
                    break;
                default:
                    this.setDescription(desc);
            }
        }
        else
        {
            this.setDescription(desc);
        }
    }
    
    /**
     * @return String representation of this object.
     */
    @Override
    public String toString()
    {
        StringBuilder returnString = new StringBuilder("=== " + getFullName() + " ===\n");
        returnString.append("Description\t" + getDescription() + "\n");
        returnString.append("Image\t\t" + getPreview() + "\n");
        returnString.append("Level\t\t" + getLevel() + "\n");
        return returnString.toString();
    }

    /**
     * @return The fullName for this item.
     */
    public final String getFullName()
    {
        return fullName;
    }

    /**
     * Sets the fullName of this item.
     *
     * @param name The new fullName
     */
    private void setFullName(final String name)
    {
        this.fullName = name;
    }

    /**
     * @return the preview image file name
     */
    public final String getPreview()
    {
        return preview;
    }

    /**
     * Set the preview image file name
     *
     * @param p The file name
     */
    private void setPreview(final String p)
    {
        this.preview = p;
    }

    /**
     * @return The description of this item.
     */
    public final String getDescription()
    {
        return description;
    }

    /**
     * Sets the description for this item.
     *
     * @param desc The description
     */
    private void setDescription(final String desc)
    {
        this.description = desc;
    }

    /**
     * @return The level of this item
     */
    public final int getLevel()
    {
        return level;
    }

    /**
     * Sets the level of this item
     *
     * @param lvl The level
     */
    private void setLevel(final int lvl)
    {
        this.level = lvl;
    }

    /**
     * Sets the price in ZEN for this item for a given time period
     *
     * @param time  0: Day, 1: Week, 2: Month, 3: 3 Months, 4: Permanent
     * @param price The price in ZEN
     */
    public final void setZenPrice(final int time, final int price)
    {
        zenPrices[time] = price;
    }

    /**
     * Returns the price for this item in ZEN for the given time period
     *
     * @param time 0: Day, 1: Week, 2: Month, 3: 3 Months, 4: Permanent
     * @return the price in ZEN for this item given the time period.
     */
    public final int getZenPrice(final int time)
    {
        return zenPrices[time];
    }

    /**
     * Sets the price in GP for this item for a given time period
     *
     * @param time  0: Day, 1: Week, 2: Month, 3: 3 Months, 4: Permanent
     * @param price The price in GP
     */
    public final void setGpPrice(final int time, final int price)
    {
        gpPrices[time] = price;
    }

    /**
     * Returns the price for this item in GP for the given time period
     *
     * @param time 0: Day, 1: Week, 2: Month, 3: 3 Months, 4: Permanent
     * @return the price in GP for this item given the time period.
     */
    public final int getGpPrice(final int time)
    {
        return gpPrices[time];
    }

    /**
     * @return The boolean state of this items standardIssue field
     */
    public final boolean isStandardIssue()
    {
        return standardIssue;
    }

    /**
     * Sets if this item if standard issue to agents. If true, sets item level to 0.
     *
     * @param standard If this item is standard issue.
     */
    private void setStandardIssue(final boolean standard)
    {
        this.standardIssue = standard;
        setLevel(0);
        for (int i = 0; i < 5; i++)
        {
            setGpPrice(i, 0);
            setZenPrice(i, 0);
        }
    }
}
