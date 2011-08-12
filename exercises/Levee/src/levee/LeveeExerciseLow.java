/*
 *  This file is part of InTEL, the Interactive Toolkit for Engineering Learning.
 *  http://intel.gatech.edu
 *
 *  InTEL is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  InTEL is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with InTEL.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package levee;

import edu.gatech.statics.modes.description.Description;

/**
 *
 * @author Calvin Ashmore
 */
public class LeveeExerciseLow extends LeveeExercise {

    public LeveeExerciseLow() {
        waterHeight = 10;
    }

    @Override
    public Description getDescription() {
        Description description  = super.getDescription();

        description.setProblemStatement(
                "This shows a levee under normal conditions, where the height of the " +
                "water is 10 ft. Assume that the ground can resist up to 10,400 lb*ft " +
                "of moment and that it has unlimited resistance to a horizontal force. " +
                "The distributed force of the water is " +
                "given by a linear pressure distribution p(h) = d*g*h, where h represents depth. The quantity d*g " +
                "is 62.4 lb/ft^3, the specific weight of water.");

        description.setImageAt("levee/assets/levee1-12ft.png",0);

        return description;
    }



    @Override
    public void initExercise() {
        super.initExercise();

//        setDescription(
//                "<p>In 2005, hurricane Katrina devastated New Orleans. New Orleans is " +
//                "below sea level, between the Mississippi river and Lake Pontchartrain, " +
//                "and relies on levees for protection. A day after Katrina hit the city, " +
//                "the levee system broke in three places.<br><br></p>" +
//
//                "<p>This shows a levee under normal conditions, where the height of the " +
//                "water is 10 ft. Assume that the ground can resist up to 10,400 lb*ft " +
//                "of moment and that it has unlimited resistance to a horizontal force. " +
//                "Examining a 1 foot cross section, solve for the actual loading of the " +
//                "water on the ground at the base of the levee (represented by the fixed " +
//                "support A). Will the ground be able to support the levee?<br><br></p>" +
//
//                "<p>To calculate the resultant, the distributed force of the water is " +
//                "given by a linear pressure distribution p(h) = d*g*h, where h represents depth. The quantity d*g " +
//                "is 62.4 lb/ft^3, the specific weight of water.</p>");
    }
}
