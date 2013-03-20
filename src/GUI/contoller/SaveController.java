
package GUI.contoller;

import java.util.Observable;

public class SaveController extends Observable
{
    private boolean enableMainFrame;
    
    public SaveController()
    {
        enableMainFrame = false;
    }
    
    public boolean getEnableMainFrame()
    {
        return enableMainFrame;
    }
    
    public void setEnableMainFrame(boolean enable)
    {
        enableMainFrame = enable;
        indicateChange();
    }
    
    /**
     * A change has been made. Set the Changed flag and notify all observers.
     */
    public final void indicateChange()
    {
        setChanged();
        notifyObservers();
        clearChanged();
    }
}
