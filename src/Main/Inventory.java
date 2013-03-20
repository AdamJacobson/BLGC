package Main;

import Parts.StandardDefinitions;
import Parts.Barrel;
import Parts.BodyArmor;
import Parts.EquipmentPart;
import Parts.Helmet;
import Parts.LegArmor;
import Parts.Magazine;
import Parts.Muzzle;
import Parts.Receiver;
import Parts.Scope;
import Parts.Stock;
import Parts.WeaponPart;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Reads data from XML files and stores the data as part objects.
 */
public class Inventory {

    /**
     * A copy of the StandardDefintions class for code shortening.
     */
    private StandardDefinitions sd = new StandardDefinitions();

    /**
     * An array that indicates the number of each type of part for Dependent
     * parts. The index refers to parts as defined in the
     * StandardDefinitions.java file. numberOfParts[partID]
     */
    private int[] numberOfDependentParts = new int[sd.TOTAL_NUMBER_OF_PART_TYPES - sd.NUMBER_OF_INDEPENDENT_PART_TYPES];

    /**
     * The array of all Dependent Weapon Parts : Barrel, Muzzle, Magazine, Scope
     * and Stock. These are also called "Attachments". The first index is the
     * partID as defined in the StandardDefinitions.java file.
     * dependentPartList[partID][modelWeapon].
     */
    private WeaponPart[][] dependentPartList =
            new WeaponPart[sd.TOTAL_NUMBER_OF_PART_TYPES - sd.NUMBER_OF_INDEPENDENT_PART_TYPES][sd.MAX_NUMBER_OF_PARTS];

    /**
     * A reverse lookup table used to translate from the selected index of a
     * combo box to the true index in a weapon part listing. There is a reverse
     * lookup for each part type, each weapon in the model and each part.
     * reverseLookup[partID][modelWeapon][part]
     */
    private int[][][] reverseLookup =
            new int[sd.TOTAL_NUMBER_OF_PART_TYPES - sd.NUMBER_OF_INDEPENDENT_PART_TYPES][sd.NUMBER_OF_WEAPONS][sd.MAX_NUMBER_OF_PARTS];

    /**
     * An array that indicates the number of each type of part for Independent
     * parts. The index refers to parts as defined in the
     * StandardDefinitions.java file. numberOfParts[partID]
     */
    private int[] numberOfIndependentParts = new int[sd.NUMBER_OF_INDEPENDENT_PART_TYPES];

    /**
     * List of Independent Parts : Primary and Secondary Receivers, Helmets,
     * BodyArmors and LegArmors. The first index is the partID as defined in the
     * StandardDefinitions.java file.
     */
    private EquipmentPart[][] independentPartList = new EquipmentPart[sd.NUMBER_OF_INDEPENDENT_PART_TYPES][sd.MAX_NUMBER_OF_PARTS];

    private String[] filePaths;

    private int numberOfFilePaths;

    private void addPathToFileList(Path path)
    {
        filePaths[numberOfFilePaths] = path.toString();
        numberOfFilePaths++;
    }

    /**
     * Runs through the file tree from /dataFiles looking for XML files to be read.
     * Any other file type will be ignored.
     * @param uri 
     */
    private void searchFileTree(String uri)
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(uri), "*{.xml,}")) {
            for (Path file : stream) {
                if (file.getFileName().toString().endsWith(".xml")) {
                    addPathToFileList(file.toAbsolutePath());
                }
                else if (file.getFileName().toString().endsWith(".txt"))
                {
                    // Ignore .txt files.
                    // DirectoryStream will include these even though we don't need them.
                    // Simpler to ignore than avoid.
                }
                else
                {
                    searchFileTree(uri + "\\" + file.getFileName());
                }
            }
        }
        catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println("Inventory.searchFileTree: " + x);
        }
    }

    /**
     * Initializes reverse lookup lists.
     */
    private void initializeReverseLookupLists()
    {
        for (int i = 0; i < sd.NUMBER_OF_WEAPONS; i++) {
            reverseLookup[i] = new int[sd.NUMBER_OF_WEAPONS][sd.MAX_NUMBER_OF_PARTS];
        }
    }

    /**
     * Automatically begins reading files and storing data on creation.
     */
    public Inventory()
    {
        System.out.println("Printing Check Okay.");

        numberOfFilePaths = 0;
        filePaths = new String[100];

        searchFileTree("src\\dataFiles");

        int numPivotalElements;
        XMLReader reader;
        TaggedDataList tdList;

        initializeReverseLookupLists();

        reader = new XMLReader();

        // For each specified file path tail
        for (int fileNumber = 0; fileNumber < numberOfFilePaths; fileNumber++) {
            // Pass the fileName to the XMLReader
            reader.readFile(filePaths[fileNumber], false);

            numPivotalElements = reader.getNumberOfPivotalElements();

            // For each pivotal element in the file
            for (int i = 0; i < numPivotalElements; i++) {
                // Get the TaggedDataList which holds the data for that pivotal element
                tdList = reader.getListOfTaggedDataList(i);

                // Send that data to be made into a new object of the correct type
                createNewObjectFromXMLData(tdList, reader.getPivotalElementName(i), filePaths[fileNumber]);
            }
        }
    }

    /**
     * Create a new object of the given pivotalElementName
     *
     * @param tdList The tagged data list made by the XMLReader.
     * @param objectType The pivotalElementName found by the XMLReader.
     */
    private void createNewObjectFromXMLData(TaggedDataList tdList, String objectType, String fileName)
    {
        switch (objectType.toLowerCase()) {
            case "barrel":
                dependentPartList[sd.BARREL][numberOfDependentParts[sd.BARREL]] = new Barrel(tdList);
                numberOfDependentParts[sd.BARREL]++;
                break;
            case "magazine":
                dependentPartList[sd.MAGAZINE][numberOfDependentParts[sd.MAGAZINE]] = new Magazine(tdList);
                numberOfDependentParts[sd.MAGAZINE]++;
                break;
            case "muzzle":
                dependentPartList[sd.MUZZLE][numberOfDependentParts[sd.MUZZLE]] = new Muzzle(tdList);
                numberOfDependentParts[sd.MUZZLE]++;
                break;
            case "scope":
                dependentPartList[sd.SCOPE][numberOfDependentParts[sd.SCOPE]] = new Scope(tdList);
                numberOfDependentParts[sd.SCOPE]++;
                break;
            case "stock":
                dependentPartList[sd.STOCK][numberOfDependentParts[sd.STOCK]] = new Stock(tdList);
                numberOfDependentParts[sd.STOCK]++;
                break;
            case "helmet":
                independentPartList[sd.HELMET][numberOfIndependentParts[sd.HELMET]] = new Helmet(tdList);
                numberOfIndependentParts[sd.HELMET]++;
                break;
            case "bodyarmor":
                independentPartList[sd.BODY_ARMOR][numberOfIndependentParts[sd.BODY_ARMOR]] = new BodyArmor(tdList);
                numberOfIndependentParts[sd.BODY_ARMOR]++;
                break;
            case "legarmor":
                independentPartList[sd.LEG_ARMOR][numberOfIndependentParts[sd.LEG_ARMOR]] = new LegArmor(tdList);
                numberOfIndependentParts[sd.LEG_ARMOR]++;
                break;
            case "primaryreceiver":
                independentPartList[sd.PRIMARY_RECEIVERS][numberOfIndependentParts[sd.PRIMARY_RECEIVERS]] = new Receiver(tdList, true);
                numberOfIndependentParts[sd.PRIMARY_RECEIVERS]++;
                break;
            case "secondaryreceiver":
                independentPartList[sd.SECONDARY_RECEIVERS][numberOfIndependentParts[sd.SECONDARY_RECEIVERS]] = new Receiver(tdList, false);
                numberOfIndependentParts[sd.SECONDARY_RECEIVERS]++;
                break;
            default:
                System.out.println("Did not find object type case for: <" + objectType + "> in file: " + fileName);
                break;
        }
    }

    /**
     * Returns an array of Strings for use in populating comboBoxes. This is
     * only for Independent parts, parts that can be equipped regardless of any
     * other equipped part: Helmet, BodyArmor, LegArmor and Receiver
     *
     * @param partID The partID as defined in StandardDefinitions.java
     * @return String[] of usable parts of the requested type.
     */
    public String[] getStringListOfIndependentParts(int partID)
    {
        String[] returnString = new String[numberOfIndependentParts[partID]];

        for (int i = 0; i < numberOfIndependentParts[partID]; i++) {
            returnString[i] = independentPartList[partID][i].getFullName();
        }

        return returnString;
    }

    /**
     * Returns an array of Strings for use in populating comboBoxes. This is
     * only for Dependent parts, parts that change either availability or stats
     * depending on what other parts are equipped: Barrel, Muzzle/Grip,
     * Magazine, Stock and Scope.
     *
     * @param shortName The shortName of the receiver that the part must apply
     * to
     * @param partID The partID as defined in the StandardDefinitions.java file.
     * @param panelID The ID Number of the WeaponComponentSelectionPanel.
     * @return String[] of usable parts of the requested type.
     */
    public String[] getStringListOfDependentParts(String shortName,
            int partID, int panelID)
    {
        WeaponPart wp;

        int j = 0;
        for (int i = 0; i < numberOfDependentParts[partID]; i++) {
            wp = dependentPartList[partID][i];
            if (wp.getUsableOn(wp.getIndexOfWeaponShortName(shortName))) {
                reverseLookup[partID][panelID][j++] = i;
            }
        }

        String[] returnString = new String[j];

        for (int i = numberOfDependentParts[partID] - 1; i >= 0; i--) {
            wp = dependentPartList[partID][i];
            if (wp.getUsableOn(wp.getIndexOfWeaponShortName(shortName))) {
                returnString[--j] = wp.getFullName();
            }
        }

        return returnString;
    }

    /**
     * Returns the dependent weapon part given a selected index of a combo box
     * for a given partID and Selection Panel. Only used for Dependent parts
     * which require a reverse lookup list. Dependent parts are: Barrel,
     * Magazine, Muzzle, Scope and Stock
     *
     * @param selectedIndex The selected index from a comboBox
     * @param partID The partID as defined in StandardDefinitions
     * @param panelID The ID of the panel the comboBox resides in.
     * @return The WeaponPart selected by the comboBox
     */
    public WeaponPart getDependentPart(int selectedIndex,
            int partID, int panelID)
    {
        // Prevent an out of bounds exception from occuring
        if (selectedIndex < 0) {
            selectedIndex = 0;
        }
//        System.out.println("dependentPartList[" + partID + "][reverseLookup[" + partID + "][" + panelID + "]" + selectedIndex + "]]");
        return dependentPartList[partID][ reverseLookup[partID][panelID][selectedIndex]];
    }

    /**
     * Returns the Independent EquipmentPart given a selected index of a combo
     * box for a given partID.
     *
     * @param selectedIndex The selected index of the comboBox
     * @param partID The partID as defined in StandardDefinitions
     * @return The EquipmentPart selected by the comboBox.
     */
    public EquipmentPart getIndependentPart(int selectedIndex, int partID)
    {
        return independentPartList[partID][selectedIndex];
    }
}
