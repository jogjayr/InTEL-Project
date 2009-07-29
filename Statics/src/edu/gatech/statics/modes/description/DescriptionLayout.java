/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.description;

/**
 *
 * @author Calvin Ashmore
 */
public interface DescriptionLayout {

//    private DescriptionUI ui;
//
//    public DescriptionLayout(DescriptionUI ui) {
//        this.ui = ui;
//    }
//
//    public DescriptionUI getUI() {
//        return ui;
//    }
    /**
     * This methods adds and places the UI components. This will only happen ONCE.
     * An important detail to note with BUI is that BLabels are placed from a top left coordinate system,
     * while HTMLViews are placed from a bottom left coordinate system.
     * @param ui
     */
    abstract public void addComponents(DescriptionUI ui);

    abstract public void layout(DescriptionUI ui, Description description);
}
