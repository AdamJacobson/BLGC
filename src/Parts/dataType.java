
package Parts;

/**
 * An enumeration for the type of data that is being held at each index of a TaggedDataList
 */
public enum dataType {
    /**
     * The data is a number (if it is an int, it will be cast later)
     */
    DOUBLE, 
    
    /**
     * Data is either true or false
     */
    BOOLEAN, 
    
    /**
     * All other data
     */
    STRING;
}
