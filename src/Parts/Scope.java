package Parts;

import Main.TaggedDataList;

/**
 * Scopes are weapon attachments. Scopes are universal and can be used on all weapons
 * unless explicitly specified.
 */
public class Scope extends WeaponPart
{

    /**
     * The amount of magnification
     */
    private double zoomAmount;

    /**
     * The time it takes in seconds to scope in completely
     */
    private double scopeInTime;

    /**
     * The file path to the overlay preview image
     */
    private String overlayPreview;

    /**
     * Create a new Scope with data from a TaggedDataList.
     *
     * @param tdList The TaggedDataList holding the data relevant to this Scope.
     */
    public Scope(final TaggedDataList tdList)
    {
        // By default, scopes are usable on all weapons. The file specifies
        // if a particular scope is not usable on a certain weapon.
        for (int i = 0; i < weaponShortNames.length; i++)
        {
            setUsableOn(i, true);
        }
        fillData(tdList);
    }

    /**
     * Goes through the TaggedDataList made by the XMLReader. Looks for tag names that are
     * relevant to this type of object and modifies data accordingly. Passes the TaggedDataList
     * to the super class when finished.
     *
     * @param tdList The taggedDataList containing the data read from the XML tree.
     */
    @Override
    public final void fillData(final TaggedDataList tdList)
    {
        String dataString;
        double dataDouble;
        boolean usedData;

        dataType type;
        String tagName;

        for (int i = 0; i < tdList.getNumberOfItems(); i++)
        {
            dataString = "NO DATA FOUND";
            dataDouble = Double.NEGATIVE_INFINITY;
            usedData = true;

            type = tdList.getTypeByIndex(i);
            tagName = tdList.getItemKeyByIndex(i);

            if (type == dataType.DOUBLE)
            { // Double
                dataDouble = Double.parseDouble(tdList.getItemDataByIndex(i));
            }
            else
            {
                dataString = tdList.getItemDataByIndex(i);
            }

            switch (tagName.toLowerCase())
            {
                case "zoomamount":
                    this.setZoomAmount(dataDouble);
                    break;
                case "scopeintime":
                    this.setScopeInTime(dataDouble);
                    break;
                case "overlaypreview":
                    this.setOverlayPreview(dataString);
                    break;
                case "notusableon":
                    setUsableOn(getIndexOfWeaponShortName(dataString), false);
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
     * @return The String representation of this weapon.
     */
    @Override
    public final String toString()
    {
        StringBuilder returnString = new StringBuilder("--SCOPE--\n");
        returnString.append(super.toString());
        returnString.append("Overlay\t\t" + this.getOverlayPreview() + "\n");
        returnString.append("Zoom\t\t" + this.getZoomAmount() + "\n");
        returnString.append("Scope-In Time\t" + this.getScopeInTime() + "\n");
        returnString.append("---------------------------\n");

        return returnString.toString();
    }

    /**
     * @return The amount that this scope amplifies the view
     */
    public final double getZoomAmount()
    {
        return zoomAmount;
    }

    /**
     * Sets the amount that this scope amplifies the view
     *
     * @param newZoomAmount The amplification level
     */
    private void setZoomAmount(final double newZoomAmount)
    {
        this.zoomAmount = newZoomAmount;
    }

    /**
     * @return The time it takes for this scope to zoom in fully.
     */
    public final double getScopeInTime()
    {
        return scopeInTime;
    }

    /**
     * Sets the time it takes for this scope to zoom in fully.
     *
     * @param newScopeInTime The zoom-in time
     */
    private void setScopeInTime(final double newScopeInTime)
    {
        this.scopeInTime = newScopeInTime;
    }

    /**
     * @return The filename for the scope overlay image
     */
    public final String getOverlayPreview()
    {
        return overlayPreview;
    }

    /**
     * Sets the filename for the scope overlay image
     *
     * @param preview The file name
     */
    private void setOverlayPreview(final String preview)
    {
        this.overlayPreview = preview;
    }
}
