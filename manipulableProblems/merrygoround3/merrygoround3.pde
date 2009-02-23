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

void setup() {
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

void draw() {
  background(bgrnd);
  if(load) {
    image(wheel, 100, 100, 500, 500);
    image(arrow, 40, 250, 140, 126);
    image(startScreen, 0, 0, 700, 700);
    if(mousePressed == true && mouseX >= 580 && mouseX <=690 && mouseY >= 625 && mouseY <= 690) {
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
    if(a <= -1.35 && a >= -1.77) {
      rotate(radians(270));
      if(mousePressed == true) {
        fun = 100;
        direction = 1;
        state = true;
      }
    } 
    else if (a <= -2.25 && a >= -2.47) {
      rotate(radians(225));
      if(mousePressed == true) {
        fun = 50;
        direction = 0.5;
        state = true;
      }
    } 
    else if (a >= 2.25 && a <= 2.47) {
      rotate(radians(135));
      if(mousePressed == true) {
        fun = 50;
        direction = -0.5;
        state = true;
      }
    } 
    else if (a <= -0.66 && a >= -0.88) {
      rotate(radians(315));
      if(mousePressed == true) {
        fun = 50;
        direction = 0.5;
        state = true;
      }
    } 
    else if (a >= 0.66 && a <= 0.88) {
      rotate(radians(45));
      if(mousePressed == true) {
        fun = 50;
        direction = -0.5;
        state = true;
      }
    } 
    else if(a <= 0.21 && a >= -0.21) {
      rotate(radians(0));
      if(mousePressed == true) {
        fun = 0;
        direction = 0;
        state = true;
      }  
    } 
    else if(a >= 1.35 && a <= 1.77) {
      rotate(radians(90));
      if(mousePressed == true) {
        fun = 100;
        direction = -1;
        state = true;
      }
    } 
    else if(a <= 3.35 && a >= 2.93) {
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
          fun = -1.12*degrees(a);
          if(fun < 0) {
            fun = fun * -1;
          }
          direction = -0.012*degrees(a);
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



