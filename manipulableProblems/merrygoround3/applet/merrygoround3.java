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
import processing.core.*; 
import processing.xml.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class merrygoround3 extends PApplet {

PImage wheel;
PImage arrow;
PImage wheelArrow;
PImage wheelArrowFail;
PImage wheelArrowReverse;
PImage bgrnd;
PImage startScreen;
PFont arial;
PFont bigfont;

float degrees = 0;
float fun = 0;
float direction = 0;
boolean state = false;
boolean load = true;

public void setup() {
  size(700, 700);
  frameRate(60);
  arial = loadFont("AngsanaNew-48.vlw");
  bigfont = loadFont("AngsanaNew-120.vlw");
  bgrnd = loadImage("grass.jpg");
  wheel = loadImage("wheel.png");
  arrow = loadImage("arrow.png");
  wheelArrow = loadImage("wheel2.png");
  wheelArrowFail = loadImage("wheel3.png");
  wheelArrowReverse = loadImage("wheel4.png");
  startScreen = loadImage("startscreen.png");
}

public void draw() {
  background(bgrnd);
  if(load) {
    image(wheel, 100, 100, 500, 500);
    image(arrow, 40, 250, 140, 126);
    image(startScreen, 0, 0, 700, 700);
    if(mousePressed == true && mouseX >= 580 && mouseX <=690 && mouseY >= 5 && mouseY <= 70) {
      load = false;
      mousePressed = false;
    }
  }
  if(!state && !load) {
    pushMatrix();
    translate(width/2, height/2);
    image(wheel, -250, -250, 500, 500);
    popMatrix();

    resetMatrix();

    pushMatrix();
    translate(120, height/2);
    float a = atan2(mouseY-height/2, mouseX-120);
    if(a <= -1.35f && a >= -1.77f) {
      rotate(radians(270));
      if(mousePressed == true) {
        fun = 100;
        direction = 1;
        state = true;
      }
    } 
    else if (a <= -2.25f && a >= -2.47f) {
      rotate(radians(225));
      if(mousePressed == true) {
        fun = 50;
        direction = 0.5f;
        state = true;
      }
    } 
    else if (a >= 2.25f && a <= 2.47f) {
      rotate(radians(135));
      if(mousePressed == true) {
        fun = 50;
        direction = -0.5f;
        state = true;
      }
    } 
    else if (a <= -0.66f && a >= -0.88f) {
      rotate(radians(315));
      if(mousePressed == true) {
        fun = 50;
        direction = 0.5f;
        state = true;
      }
    } 
    else if (a >= 0.66f && a <= 0.88f) {
      rotate(radians(45));
      if(mousePressed == true) {
        fun = 50;
        direction = -0.5f;
        state = true;
      }
    } 
    else if(a <= 0.21f && a >= -0.21f) {
      rotate(radians(0));
      if(mousePressed == true) {
        fun = 0;
        direction = 0;
        state = true;
      }  
    } 
    else if(a >= 1.35f && a <= 1.77f) {
      rotate(radians(90));
      if(mousePressed == true) {
        fun = 100;
        direction = -1;
        state = true;
      }
    } 
    else if(a <= 3.35f && a >= 2.93f) {
      rotate(radians(180));
      if(mousePressed == true) {
        fun = 0;
        direction = 0;
        state = true;
      }
    } 
    else {
      rotate(a);
      if(mousePressed == true) {
        if(degrees(a) <= 90 && degrees(a) >= -90) {
          fun = -1.12f*degrees(a);
          if(fun < 0) {
            fun = fun * -1;
          }
          direction = -0.012f*degrees(a);
          state = true;
        }
      }
    }

    image(arrow, -70, -100, 140, 126);
    popMatrix();
  } 
  else if (state && !load) {
    if(degrees == 360) {
      degrees = 1;
    }
    pushMatrix();
    translate(width/2, height/2);
    rotate(radians(direction * degrees));
    if(direction == 1) {
      image(wheelArrow, -250, -250, 500, 500);
    } 
    else if (direction == -1) {
      image(wheelArrowReverse, -250, -250, 500, 500);
    } 
    else {
      image(wheelArrowFail, -250, -250, 500, 500);
    }
    degrees+=5;
    popMatrix();
  }
  textFont(arial, 34);
  text(round(fun)+"% \n fun", 325, 340);
  if(direction == 1 || direction == -1){
    textFont(bigfont, 120);
    text("Success!", 200, 650);
  }
}




  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#f0f0f0", "merrygoround3" });
  }
}
