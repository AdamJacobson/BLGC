package Main;

import Parts.StandardDefinitions;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Reads an XML file specified and extracts tag data from it.
 */
public class XMLReader {

    /**
     * A copy of the StandardDefintions class.
     */
    private StandardDefinitions stdef = new StandardDefinitions();

    /**
     * The maximum number of XML leaf nodes for a subtree beginning at depth
     * PIVOTAL_DEPTH.
     */
    private static final int MAX_NUMBER_OF_LEAF_NODES = 50;

    /**
     * Should remain at 1 in compliance with standards.
     */
    private static final int PIVOTAL_DEPTH = 1;

    /**
     * The String which represents a fully read XML subtree which starts at
     * depth PIVOTAL_DEPTH.
     */
    private StringBuilder fullXmlTree;

    /**
     * The names of the tags with depth PIVOTAL_DEPTH. Used to determine the
     * type of object that is being read.
     */
    private String[] pivotalElementName;

    /**
     * Number of subtrees that begin at PIVOTAL_DEPTH.
     */
    private int numberOfPivotalElements;

    /**
     * If this is true, loadData will print the entire XML document tree after
     * reading it.
     */
    private boolean printFullTree;

    /**
     * A temporary TaggedDataList that is used when searching through each XML
     * subtree. Is copied into taggedDataListArray once finished.
     */
    private TaggedDataList temporaryTaggedDataList;

    /**
     * Holds ALL of the XML subtrees that begin at depth pivotalDepth.
     */
    private TaggedDataList[] taggedDataListArray;


    /**
     * Testing purposes only
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args)
    {
        System.out.println("Running test with XMLReader");
        XMLReader reader = new XMLReader();
        reader.readFile("weapon\\receivers.xml", false);
    }
    
    

    /**
     * Creates a new XMLReader.
     */
    public XMLReader()
    {
        
        String filePathHead = "";

        Path inputPath = Paths.get("src\\dataFiles");

        try
        {
            filePathHead = inputPath.toRealPath().toString();
            System.out.println("PATH: " + inputPath.toRealPath().toString());
        }
        catch (NoSuchFileException x)
        {
            System.err.format("%s: no such" + " file or directory%n", filePathHead);
            // Logic for case when file doesn't exist.
        }
        catch (IOException x)
        {
            System.err.format("%s%n", x);
            // Logic for other sort of file error.
        }
    }

    /**
     * Read an XML file given the filePathTail and prints the full XML dom tree
     * if requested.
     *
     * @param filePath The file path for the XML file to be read.
     * @param printTree If true, print the entire XML dom tree as it is read.
     */
    public final void readFile(final String filePath, final boolean printTree)
    {
        setNumberOfPivotalElements(0);
        clearFullTree();

        taggedDataListArray = new TaggedDataList[stdef.MAX_NUMBER_OF_PARTS];
        temporaryTaggedDataList = new TaggedDataList(stdef.MAX_NUMBER_OF_PARTS);
        pivotalElementName = new String[stdef.MAX_NUMBER_OF_PARTS];

        setPrintFullTree(printTree);
        loadData(filePath);
    }

    /**
     * Create an XML document object using the given file path. Then, send it to
     * be traversed and have its data read.
     *
     * @param filePath The tail of the file path for the file to be read.
     */
    private void loadData(final String filePath)
    {
        try
        {
            File fXMLFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXMLFile);
            parseXMLDocument(doc);
        }
        catch (ParserConfigurationException ex)
        {
            System.err.println("\t ParserConfigurationException caught at XMLReader.loadData");
            ex.printStackTrace();
        }
        catch (SAXException ex)
        {
            System.err.println("\t SAXException caught at XMLReader.loadData");
            ex.printStackTrace();
        }
        catch (IOException ex)
        {
            System.err.println("\t IOException caught at XMLReader.loadData");
            ex.printStackTrace();
        }
    }

    /**
     * Given an XML document, begin to traverse the XML tree.
     *
     * @param doc The XML document to be traversed.
     */
    private void parseXMLDocument(final Document doc)
    {
        Node docRoot = doc.getDocumentElement();
        StringBuilder indent = new StringBuilder();

        fullXmlTree.append("<" + docRoot.getNodeName() + ">\n");
        searchTreeFromBranch(docRoot, 0, indent);
        fullXmlTree.append("</" + docRoot.getNodeName() + ">\n");

        if (getPrintFullTree())
        {
            printFullTree();
        }
    }

    /**
     * Starting at a Node branch, recursively search each branching point.
     *
     * @param branch The node to begin searching from.
     * @param depth The depth of the branch node.
     * @param indentation The StringBuilder of tabs used to print the tree to
     * this depth.
     */
    private void searchTreeFromBranch(final Node branch, final int depth, final StringBuilder indentation)
    {
        int currentDepth = depth + 1;
        Node currentNode;

        StringBuilder indent = new StringBuilder(indentation.toString());
        indent.append("\t");

        // Find number of child nodes of this branch node
        // A child node is either a <tag> or text
        int numChildren = countChildElements(branch);

        if (numChildren > 0)
        {
            for (int i = 0; i < numChildren; i++)
            {
                currentNode = branch.getChildNodes().item(i);

                if (isText(currentNode))
                {
                    if (isNotEmptyAndNotNull(currentNode))
                    {
                        fullXmlTree.append(indent + currentNode.getNodeValue().trim() + "\n");
                        temporaryTaggedDataList.addItem(branch.getNodeName(),
                                currentNode.getNodeValue().trim());
                    }
                }
                else
                {
                    fullXmlTree.append(indent + "<"
                            + currentNode.getNodeName() + ">\n");
                    searchTreeFromBranch(currentNode, currentDepth, indent);
                    fullXmlTree.append(indent
                            + "</" + currentNode.getNodeName() + ">\n");

                    if (currentDepth == getPivotalDepth())
                    {
                        setPivotalElementName(getNumberOfPivotalElements(),
                                currentNode.getNodeName());
                        taggedDataListArray[getNumberOfPivotalElements()] = temporaryTaggedDataList;
                        numberOfPivotalElements++;
                        temporaryTaggedDataList = new TaggedDataList(MAX_NUMBER_OF_LEAF_NODES);
                    }
                }
            }
        }
    }

    /**
     * Return the number of direct offspring (tags and text fields) of a given
     * node.
     *
     * @param parent The parent Node
     * @return The number of child elements of parent.
     */
    private int countChildElements(final Node parent)
    {
        NodeList listOfChildren = parent.getChildNodes();
        return listOfChildren.getLength();
    }

    /**
     * Returns true if the data in the given XML node is text and not another
     * tag.
     *
     * @param node The node in question.
     * @return If the node data type is #text
     */
    private boolean isText(final Node node)
    {
        if (node.getNodeName().startsWith("#text"))
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the data in the given XML node is not empty and not null.
     *
     * @return True if the node has data in it that is not a blank String.
     * @param node The Node in question.
     */
    private boolean isNotEmptyAndNotNull(final Node node)
    {
        if ("null".equals(node.getNodeValue()))
        {
            return false;
        }
        if (node.getNodeValue().trim().isEmpty())
        {
            return false;
        }
        return true;
    }

    /**
     * @return The pivotal element name for the subtree at index index of the
     * file.
     *
     * @param index The index of the subtree.
     */
    public final String getPivotalElementName(final int index)
    {
        return pivotalElementName[index];
    }

    /**
     * Sets the pivotal element name for a given subtree.
     *
     * @param index The index of the subtree.
     * @param name The name of the pivotalElement
     */
    private void setPivotalElementName(final int index, final String name)
    {
        this.pivotalElementName[index] = name;
    }

    /**
     * @return the number of pivotal elements.
     */
    public final int getNumberOfPivotalElements()
    {
        return numberOfPivotalElements;
    }

    /**
     * Sets the number of pivotal elements in this file.
     *
     * @param numPivotElem
     */
    private void setNumberOfPivotalElements(final int numPivotElem)
    {
        this.numberOfPivotalElements = numPivotElem;
    }

    /**
     * @return The value of printFullTree.
     */
    private boolean getPrintFullTree()
    {
        return printFullTree;
    }

    /**
     * Set to true if full tree should be printed.
     *
     * @param printTree If the full tree should be printed or not.
     */
    private void setPrintFullTree(final boolean printTree)
    {
        this.printFullTree = printTree;
    }

    /**
     * Prints the full tree representation of the current subtree.
     */
    private void printFullTree()
    {
        System.out.println(fullXmlTree.toString());
    }

    /**
     * Sets the fullTree String to "".
     */
    private void clearFullTree()
    {
        fullXmlTree = new StringBuilder();
    }

    /**
     * @return PIVOTALDEPTH
     */
    private int getPivotalDepth()
    {
        return PIVOTAL_DEPTH;
    }

    /**
     * @return The TaggedDataList that represents the subtree of the given
     * index.
     * @param index The given index.
     */
    public final TaggedDataList getListOfTaggedDataList(final int index)
    {
        return taggedDataListArray[index];
    }
}
