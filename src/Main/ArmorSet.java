package Main;

import Parts.ArmorPart;
import Parts.BodyArmor;
import Parts.Helmet;
import Parts.LegArmor;
import Parts.StandardDefinitions;
import java.text.DecimalFormat;

/**
 * Holds base agent data : health, armor, speed, stamina, HRVenergy and
 * HRVrecharge. Has a helmet, bodyArmor and legArmor.
 */
public class ArmorSet
{
    /**
     * Copy of StandardDefinitions for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();

    /// Base stats that do not change.
    /// If a stat is affected by more than one part, it will usually have a base stat,
    /**
     * The base health of an agent.
     */
    private static final int BASEHEALTH = 200;

    /**
     * The base run speed of an agent.
     */
    private static final double BASERUNSPEED = 8.5;

    /**
     * The base stamina of an agent.
     */
    private static final double BASESTAMINA = 5.8;

    /// Stats after accounting for gear - changes depending on equipment
    /**
     * Determined only by the agents helmet. No base armor is needed.
     */
    private int armor;

    /**
     * Total health of the agent. Affected by all armor parts.
     */
    private int health;

    /**
     * Total HRV energy rate of the agent.
     * Determined only by the agents helmet. No base HRV energy is needed.
     */
    private double hrvEnergy;

    /**
     * Total HRV recharge rate of the agent.
     * Determined only by the agents helmet. No base HRV recharge is needed.
     */
    private double hrvRecharge;

    /**
     * Total number of gear slots of the agent. Affected by BodyArmor and LegArmor.
     */
    private int numberOfGearSlots;

    /**
     * Total run speed of the agent. Affected by all armor parts.
     */
    private double runSpeed;

    /**
     * Total stamina of the agent. Affected by all armor parts.
     */
    private double stamina;

    private ArmorPart[] armorParts;
    
    private boolean[] currentlyHasPart;

    /**
     * Create a new ArmorSet given an Inventory.
     */
    public ArmorSet()
    {
        armorParts = new ArmorPart[sd.NUMBER_OF_INDEPENDENT_PART_TYPES];
        currentlyHasPart = new boolean[sd.NUMBER_OF_INDEPENDENT_PART_TYPES];
        
        for (int i = 0; i < StandardDefinitions.NUMBER_OF_INDEPENDENT_PART_TYPES; i++) {
            currentlyHasPart[i] = false;
        }
    }

    public void setPart(int partID, ArmorPart ap)
    {
        armorParts[partID] = ap;
        currentlyHasPart[partID] = true;
        updateArmorSetStats();
    }
    
    private boolean isACompleteArmorSet()
    {
        return currentlyHasPart[sd.HELMET] 
                && currentlyHasPart[sd.BODY_ARMOR] 
                && currentlyHasPart[sd.LEG_ARMOR]; 
    }
    
    private void updateArmorSetStats()
    {
        if (isACompleteArmorSet()) {
            applyBaseStats();
            applyHelmetStats();
            applyBodyArmorStats();
            applyLegArmorStats();
        }
        else
        {
            armor = 0;
            health = 0;
            hrvEnergy = 0.0;
            hrvRecharge = 0.0;
            numberOfGearSlots = 0;
            runSpeed = 0.0;
        }
        
        formatData();
    }
    
    private void applyBaseStats()
    {
        health = BASEHEALTH;
        runSpeed = BASERUNSPEED;
        stamina = BASESTAMINA;
        numberOfGearSlots = 0;
    }
    
    private void applyHelmetStats()
    {
        Helmet helmet = (Helmet) armorParts[sd.HELMET];
        
        armor = helmet.getArmor();
        hrvEnergy = helmet.getHrvEnergy();
        hrvRecharge = helmet.getHrvRecharge();
        
        health += helmet.getHealth();
        runSpeed += helmet.getRunSpeed();
        stamina += helmet.getStamina();
    }
    
    private void applyBodyArmorStats()
    {
        BodyArmor bodyArmor = (BodyArmor) armorParts[sd.BODY_ARMOR];
        
        numberOfGearSlots += bodyArmor.getGearSlots();
        health += bodyArmor.getHealth();
        runSpeed += bodyArmor.getRunSpeed();
        stamina += bodyArmor.getStamina();
    }
    
    private void applyLegArmorStats()
    {
        LegArmor legArmor = (LegArmor) armorParts[sd.LEG_ARMOR];
        
        numberOfGearSlots += legArmor.getGearSlots();
        health += legArmor.getHealth();
        runSpeed += legArmor.getRunSpeed();
        stamina += legArmor.getStamina();
    }

    /**
     * Formats all the data that will be displayed to avoid showing roundoff error.
     */
    private void formatData()
    {
        DecimalFormat df = new DecimalFormat("0.000000");

        runSpeed = formatDouble(runSpeed, df);
        stamina = formatDouble(stamina, df);
        hrvEnergy = formatDouble(hrvEnergy, df);
        hrvRecharge = formatDouble(hrvRecharge, df);
    }
    
    private double formatDouble(double number, DecimalFormat df)
    {
        return Double.parseDouble(df.format(number));
    }
    
    /**
     * @return The String representation of this ArmorSet
     */
    @Override
    public String toString()
    {
        StringBuilder returnString = new StringBuilder("==== ArmorSet ====\n");
        
        returnString.append("Helmet: " + armorParts[sd.HELMET].getFullName() + "\n");
        returnString.append("Body Armor: " + armorParts[sd.BODY_ARMOR].getFullName() + "\n");
        returnString.append("Leg Armor: " + armorParts[sd.LEG_ARMOR].getFullName() + "\n");
        returnString.append("Health: " + health + "\n");
        returnString.append("Armor: " + armor + "%" + "\n");
        returnString.append("HRV Eng: " + hrvEnergy + "u" + "\n");
        returnString.append("HRV Rech: " + hrvRecharge + "u/s" + "\n");
        returnString.append("Speed: " + runSpeed + "\n");
        returnString.append("Stamina: " + stamina + "\n");
        returnString.append("Gear Slots: " + numberOfGearSlots + "\n");
        
        returnString.append("========");

        return returnString.toString();
    }
    
    /// Getters and setters for stats
    /**
     * @return The current amount of Armor for this ArmorSet.
     */
    public final int getArmor()
    {
        return armor;
    }

    /**
     * @return The current amount of Health for this ArmorSet.
     */
    public final int getHealth()
    {
        return health;
    }

    /**
     * @return The current number of Gear Slots for this ArmorSet.
     */
    public final int getNumberOfGearSlots()
    {
        return numberOfGearSlots;
    }

    /**
     * @return The current run speed for this ArmorSet.
     */
    public final double getRunSpeed()
    {
        return runSpeed;
    }

    /**
     * @return The current stamina for this ArmorSet.
     */
    public final double getStamina()
    {
        return stamina;
    }

    /**
     * @return The current hrv energy for this ArmorSet.
     */
    public final double getHrvEnergy()
    {
        return hrvEnergy;
    }

    /**
     * @return The current hrv recharge for this ArmorSet.
     */
    public final double getHrvRecharge()
    {
        return hrvRecharge;
    }
}
