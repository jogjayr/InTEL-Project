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

public class seesawMultiple extends PApplet {

PImage base;
PImage body10;
PImage body25;
PImage body50;
PImage bar;
PImage lock;
PImage pass;
PImage fail;
PImage bar_left; 
PImage bar_right;
PImage startScreen;
PImage force10;
PImage force25;
PImage force50;
PImage force85;
PImage fbdCheck;
PImage fbdUncheck;
PFont arial;
PFont bigfont;

boolean held10 = false;
boolean held25 = false;
boolean held50 = false;
boolean unlocked = false;
boolean correct = false;
boolean load = true;
boolean fbd = false;
int pos10 = 100;
int pos25 = 60;
int pos50 = 510;
int float10 = 0;
int float25 = 0;
int float50 = 0;

float tilt = 0;

public void setup() {
  size(670, 400);
  frameRate(60);
  bigfont = loadFont("Arial-Black-120.vlw");
  arial = loadFont("Arial-Black-20.vlw");
  base = loadImage("base.png");
  body10 = loadImage("body10.png");
  body25 = loadImage("body25.png");
  body50 = loadImage("body50.png");
  bar = loadImage("bar.png");
  lock = loadImage("lock.png");
  pass = loadImage("pass.png");
  fail = loadImage("fail.png");
  bar_left = loadImage("bar_left.png");
  bar_right = loadImage("bar_right.png");
  startScreen = loadImage("startscreen.png");
  force10 = loadImage("10kgForce.png");
  force25 = loadImage("25kgForce.png");
  force50 = loadImage("50kgForce.png");
  force85 = loadImage("85kgForce.png");
  fbdCheck = loadImage("FBD.png");
  fbdUncheck = loadImage("noFBD.png");
}

public void mousePressed() {
  if(mouseX>pos10 && mouseX<pos10+body10.width){
    held10 = true;
  }

  if(mouseX>pos25 && mouseX<pos25+body25.width){
    held25 = true;
  }

  if(mouseX>pos50 && mouseX<pos50+body50.width){
    held50 = true;
  }

  if(mouseX>305 && mouseX<365 && mouseY>245 && mouseY<305 && !load)
  {
    if((25*9.8f)*float25 + (10*9.8f)*float10 == (50*9.8f)*float50) {
      unlocked = true;
      correct = true;   
    } 
    else {
      unlocked = true;
    }
  }
}

public void mouseReleased() {
  held10 = false;
  held25 = false;
  held50 = false;
}

public void draw() {
  background(0, 219, 219);
  textFont(arial, 20);

  if(load) {
    image(bar, 83, 285);
    image(base, 0, 270);
    image(body10, pos10, 160);
    image(body25, pos25, 162);
    image(body50, pos50, 150);
    image(lock, 305, 245);
    image(startScreen, 0,0);
    if(mousePressed == true && mouseX >= 565 && mouseX <= 659 && mouseY >= 275 && mouseY <= 331) {
      mousePressed = false;
      load = false;
    }
  }

  if(!unlocked && !load) {
    image(bar, 83, 285);
    image(base, 0, 270);
    textFont(arial, 20);
    if(pos10 > 310){
      pos10 = 310;
    }
    if(pos10 < 60){
      pos10 = 60;
    }

    if(pos25 > 310){
      pos25 = 310;
    }
    if(pos25 < 60){
      pos25 = 60;
    }


    if(pos50 < 310){
      pos50 = 310;
    }
    if(pos50 > 560){
      pos50 = 560;
    }
    float10 = ((310-pos10));
    float25 = ((310-pos25));
    float50 = ((310-pos50)*-1);
    text(float10+" cm", pos10, 145);
    text(float25+" cm", pos25, 145);
    text(float50+" cm", pos50, 145);
    if(held10 == true) {
      pos10 = mouseX-24;
      if(pos10 > 310){
        pos10 = 310;
      }
      if(pos10 < 60){
        pos10 = 60;
      }
      float10 = (310-pos10);  
    }
    if(held25 == true) {
      pos25 = mouseX-24;
      if(pos25 > 310){
        pos25 = 310;
      }
      if(pos25 < 60){
        pos25 = 60;
      }
      float25 = (310-pos25);
    }
    if(held50 == true) {
      pos50 = mouseX-24;
      if(pos50 < 310){
        pos50 = 310;
      }
      if(pos50 > 560){
        pos50 = 560;
      }
      float50 = (310-pos50)*-1;
    }
    image(body10, pos10, 160);
    image(body25, pos25, 162);
    image(body50, pos50, 150);
    image(lock, 305, 245);
  }

  if (unlocked && !correct && !load) {
    if((25*9.8f)*float25 + (10*9.8f)*float10 > (50*9.8f)*float50) {
      background(0, 219, 219);

      float theta = (float)tilt/width * PI;
      pushMatrix();
      translate(width/2,285);
      rotate(theta);
      image(bar,-bar.width/2,-bar.height/2,bar.width,bar.height);

      pushMatrix();
      translate(pos10-311,0);
      text(float10+" cm", -24,-140);
      if(held10 == true) {
        pos10 = mouseX-24;
        if(pos10 > 310){
          pos10 = 310;
        }
        if(pos10 < 60){
          pos10 = 60;
        }
        float10 = (310-pos10);  
      }
      image(body10,-body10.width/2,-body10.height,body10.width,body10.height);
      popMatrix();

      pushMatrix();
      translate(pos25-311,0);
      text(float25+" cm", -24, -140);
      if(held25 == true) {
        pos25 = mouseX-24;
        if(pos25 > 310){
          pos25 = 310;
        }
        if(pos25 < 60){
          pos25 = 60;
        }
        float25 = (310-pos25);
      }
      image(body25,-body25.width/2,-body25.height,body25.width,body25.height);
      popMatrix();

      pushMatrix();
      translate(pos50-311,0);
      text(float50+" cm", -24, -140);
      if(held50 == true) {
        pos50 = mouseX-24;
        if(pos50 < 310){
          pos50 = 310;
        }
        if(pos50 > 560){
          pos50 = 560;
        }
        float50 = (310-pos50)*-1;
      }
      image(body50,-body50.width/2,-body50.height,body50.width,body50.height);
      popMatrix();

      popMatrix();

      image(base, 0, 270);
      image(fail, 305, 245);
      if(tilt>-50) {
        tilt--;
      }
    }
    else if((25*9.8f)*float25 + (10*9.8f)*float10 < (50*9.8f)*float50) {
      background(0, 219, 219);

      float theta = (float)tilt/width * PI;
      pushMatrix();
      translate(width/2,285);
      rotate(theta);
      image(bar,-bar.width/2,-bar.height/2,bar.width,bar.height);

      pushMatrix();
      translate(pos10-311,0);
      text(float10+" cm", -24,-140);
      if(held10 == true) {
        pos10 = mouseX-24;
        if(pos10 > 310){
          pos10 = 310;
        }
        if(pos10 < 60){
          pos10 = 60;
        }
        float10 = (310-pos10);  
      }
      image(body10,-body10.width/2,-body10.height,body10.width,body10.height);
      popMatrix();

      pushMatrix();
      translate(pos25-311,0);
      text(float25+" cm", -24, -140);
      if(held25 == true) {
        pos25 = mouseX-24;
        if(pos25 > 310){
          pos25 = 310;
        }
        if(pos25 < 60){
          pos25 = 60;
        }
        float25 = (310-pos25);
      }
      image(body25,-body25.width/2,-body25.height,body25.width,body25.height);
      popMatrix();

      pushMatrix();
      translate(pos50-311,0);
      text(float50+" cm", -24, -140);
      if(held50 == true) {
        pos50 = mouseX-24;
        if(pos50 < 310){
          pos50 = 310;
        }
        if(pos50 > 560){
          pos50 = 560;
        }
        float50 = (310-pos50)*-1;
      }
      image(body50,-body50.width/2,-body50.height,body50.width,body50.height);
      popMatrix();

      popMatrix();

      image(base, 0, 270);
      image(fail, 305, 245);
      if(tilt<50) {
        tilt++;
      }
    }
    else{
      correct = true;
    }
  }
  if(unlocked && correct && !load) {
    image(bar, 83, 285);
    if(mousePressed == true && mouseX >= 10 && mouseX <= 106 && mouseY >= 360 && mouseY <= 392) {
      mousePressed = false;
      if(fbd == true){ 
        fbd = false; 
      }
      else{ 
        fbd = true;
      }
    }
    if(fbd) {
          //    image(base, 0, 270);
        textFont(arial, 20);
        text("Success!", 10, 25);
        text("\u03a3F[X] = 0", 10, 50);
        text("\u03a3F[Y] = -245N - 98N - 490N + 833N = 0", 10, 75);
        text("\u03a3M[C] = 245N * " + float25 + "cm" + " + 98N * " + float10 + "cm" + " - 490N * " + float50 + "cm = 0", 10, 100);
        
        image(force10, pos10-10, 264);
        image(force25, pos25-10, 264);
        image(force50, pos50-11, 264);
        image(force85, 245, 189);
        
        line(pos10+22, 195, 333, 195);
        line(pos10+22, 190, pos10+22, 200);
        line(333, 190, 333, 200);
        line(333, 215, 333, 225);
        line(pos25+22, 220, 333, 220);
        line(pos25+22, 215, pos25+22, 225);
        line(pos50+23, 215, pos50+23, 225);
        line(333, 220, pos50+23, 220);
        
        image(fbdCheck, 10, 360);
    } else {
      textFont(bigfont, 120);
      text("Success!", 35, 100);
      image(base, 0, 270);
      image(body10, pos10, 160);
      image(body25, pos25, 162);
      image(body50, pos50, 150);
      image(fbdUncheck, 10, 360);
    }
  }
}











  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#f0f0f0", "seesawMultiple" });
  }
}
