package Parts;

import java.awt.Color;

/**
 * Predefined values that are used throughout the program.
 */
public class StandardDefinitions {

    /**
     * Premade color for when a stat is better than the comparison
     */
    public static final Color DARK_GREEN = new Color(0, 185, 0);

    /**
     * Premade color for when a stat is worse than the comparison
     */
    public static final Color DARK_RED = new Color(230, 0, 0);

    /**
     * The maximum number of parts for each part type that can be described.
     */
    public static final int MAX_NUMBER_OF_PARTS = 100;

    /**
     * The number of weapons that the model needs to deal with. There are 2
     * weapons on each comparison page and 2 on the overview page for a total of
     * 6.
     */
    public static final int NUMBER_OF_WEAPONS = 6;

    /**
     * The number of armor sets that the model needs to deal with. There are 2
     * on the comparison page and 1 on the overview page for a total of 3.
     */
    public static final int NUMBER_OF_ARMOR_SETS = 3;

    /**
     * The number of distinct part types.
     */
    public static final int TOTAL_NUMBER_OF_PART_TYPES = 11;

    /**
     * Primary receivers, Secondary receivers, Helmet, BodyArmor and LegArmor.
     */
    public static final int NUMBER_OF_INDEPENDENT_PART_TYPES = 5;

    // The following are partIDNumbers for DEPENDENT parts and must be unique.
    // And given number must be less than TOTAL_NUMBER_OF_PART_TYPES
    /**
     * The global standard part index for Barrels.
     */
    public static final int BARREL = 0;

    /**
     * The global standard part index for Magazines.
     */
    public static final int MAGAZINE = 1;

    /**
     * The global standard part index for Muzzles.
     */
    public static final int MUZZLE = 2;

    /**
     * The global standard part index for Scopes.
     */
    public static final int SCOPE = 3;

    /**
     * The global standard part index for Stocks.
     */
    public static final int STOCK = 4;

    // The following are partIDNumbers for INDEPENDENT parts and must be unique.
    // And given number must be less than TOTAL_NUMBER_OF_PART_TYPES
    /**
     * The global standard part index for PrimaryReceivers.
     */
    public static final int PRIMARY_RECEIVERS = 0;

    /**
     * The global standard part index for SecondaryReceivers.
     */
    public static final int SECONDARY_RECEIVERS = 1;

    /**
     * The global standard part index for BodyArmor.
     */
    public static final int BODY_ARMOR = 2;

    /**
     * The global standard part index for Helmets.
     */
    public static final int HELMET = 3;

    /**
     * The global standard part index for LegArmor.
     */
    public static final int LEG_ARMOR = 4;

}
