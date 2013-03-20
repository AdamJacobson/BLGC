package Main;

import Parts.dataType;

/**
 * Stores a pair of String arrays where the first String represents the name
 * of an XML tag and the second String is the data held between those tags.
 */
public class TaggedDataList
{

    /**
     * Array of strings representing the XML tag name.
     */
    private String[] key;

    /**
     * Array of strings representing the data between two XML leaf tags.
     */
    private String[] data;
    
    /**
     * True for each index where the data has been read.
     */
    private boolean[] dataUsed;

    /**
     * The number of key-data pairs.
     */
    private int numberOfItems;

    /**
     * @param length The number of items to be stored.
     */
    public TaggedDataList(final int length)
    {
        key = new String[length];
        data = new String[length];
        dataUsed = new boolean[length];
        setNumberOfItems(0);
    }

    /**
     * Adds a pair of strings to the taggedDataList.
     *
     * @param newKey  A string representing the tag name
     * @param newData A string representing the data in the tag
     */
    public final void addItem(final String newKey, final String newData)
    {
        int n = getNumberOfItems();
        setNumberOfItems(n + 1);
        key[n] = newKey;
        data[n] = newData;
        dataUsed[n] = false;
    }

    /**
     * Returns the data for the given index.
     *
     * @param index The index
     * @return value[index]
     */
    public final String getItemDataByIndex(final int index)
    {
        return data[index];
    }

    /**
     * Returns the key for the given index.
     *
     * @param index The index
     * @return key[index]
     */
    public final String getItemKeyByIndex(final int index)
    {
        return key[index];
    }

    /**
     * Sets the number of items that are contained in this object.
     *
     * @param items The number of items.
     */
    private void setNumberOfItems(final int items)
    {
        numberOfItems = items;
    }

    /**
     * @return The number of items in this TaggedDataList
     */
    public final int getNumberOfItems()
    {
        return this.numberOfItems;
    }
    
    public final void flagDataAsUsed(final int index){
        dataUsed[index] = true;
    }
    
    public final void clearUsedData(){
        int newNumberOfItems = 0;
        // First, figure out how long the new key, data and usedData arrays will need to be.
        for (int i = 0; i < numberOfItems; i++)
        {
            if (!dataUsed[i])
            {
                newNumberOfItems++;
            }
        }

        // Initialize new temp key, data and usedData arrays
        String[] newKey = new String[newNumberOfItems];
        String[] newData = new String[newNumberOfItems];
        boolean[] newDataUsed = new boolean[newNumberOfItems];
        
        int newIndex = 0;
        int index = 0;
        while (index < numberOfItems)
        {
            if (!dataUsed[index])
            {
                newKey[newIndex] = key[index];
                newData[newIndex] = data[index];
                newDataUsed[newIndex] = false;
                newIndex++;
            }
            index++;
        }
                
        key = newKey;
        data = newData;
        dataUsed = newDataUsed;
        setNumberOfItems(newNumberOfItems);
    }

    /**
     * Returns an int indicating the type of data for the given index.
     *
     * @param index The index
     * @return Enumeration from dataType.java: DOUBLE, BOOLEAN, or STRING
     */
    public final dataType getTypeByIndex(final int index)
    {
        String currentData = getItemDataByIndex(index);
        
        // TODO! Figure out a way to do this without try-catch
        try
        {
            double doubleValue = Double.parseDouble(currentData);
            return dataType.DOUBLE;
        }
        catch (Exception e)
        {
        }

        if ((currentData.compareToIgnoreCase("true") == 0) || (currentData.compareToIgnoreCase("false") == 0))
        {
            return dataType.BOOLEAN; // boolean
        }
        else
        {
            return dataType.STRING; // string
        }
    }

    /**
     * @return A String representation of this TaggedDataList.
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder();

        for (int i = 0; i < getNumberOfItems(); i++)
        {
            returnString.append("<" + getItemKeyByIndex(i) + "> " + getItemDataByIndex(i));

            switch (getTypeByIndex(i))
            {
                case DOUBLE:
                    returnString.append(" (Double) ");
                    break;
                case BOOLEAN:
                    returnString.append(" (Boolean) ");
                    break;
                default:
                    returnString.append(" (String) ");
                    break;
            }
        }

        return returnString.toString();
    }
}
