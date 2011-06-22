/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gatech.statics.modes.description;

import edu.gatech.statics.modes.description.layouts.StandardLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * A description consists of a lot of data that is given about a problem. Description
 * elements are the problem's title, a narrative of sorts, text useful to make sense
 * of the problem from an engineering perspective, any meta-information useful
 * for classifing or describing the problem, and also images used to be shown on the
 * description page.
 * @author Calvin Ashmore
 */
public class Description {

    private String title;
    private String narrative;
    private String problemStatement;
    private String goals;
    private List<BufferedImage> images;
    private DescriptionLayout layout = new StandardLayout();

    /**
     * 
     * @return Problem goals
     */
    public String getGoals() {
        return goals;
    }

    /**
     * Set problem goals
     * @param goals Problem goals
     */
    public void setGoals(String goals) {
        this.goals = goals;
    }

    /**
     * @return Images in problem description
     */
    public List<BufferedImage> getImages() {
        if (images == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(images);
    }

    /**
     * @param imageURL URL of image to be added
     */
    public void addImage(String imageURL) {
        if (images == null) {
            images = new ArrayList<BufferedImage>();
        }

        BufferedImage img;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(imageURL));
            images.add(img);
        } catch (IOException ex) {
            Logger.getLogger(Description.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Set image at index to imageURL
     * @param imageURL Url of image
     * @param index Position in image list to set to imageURL
     */
    public void setImageAt(String imageURL, int index) {
        if (images == null) {
            images = new ArrayList<BufferedImage>();
        }

        BufferedImage img;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResource(imageURL));
            //images.add(img);
            images.set(index, img);
        } catch (IOException ex) {
            Logger.getLogger(Description.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @return Problem narrative
     */
    public String getNarrative() {
        return narrative;
    }

    /**
     * @param narrative Problem narrative
     */
    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    /**
     * 
     * @return Problem statement
     */
    public String getProblemStatement() {
        return problemStatement;
    }

    /**
     * 
     * @param problemStatement Problem statement
     */
    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    /**
     * 
     * @return Problem title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title Problem title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return DescriptionLayout for this description
     */
    public DescriptionLayout getLayout() {
        return layout;
    }

    /**
     * 
     * @param layout DescriptionLayout for this description
     */
    public void setLayout(DescriptionLayout layout) {
        this.layout = layout;
    }
}
