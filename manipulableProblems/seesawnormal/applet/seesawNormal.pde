PImage base;
PImage body15;
PImage body20;
PImage bar;
PImage lock;
PImage pass;
PImage fail;
PImage bar_left; 
PImage bar_right;
PImage startScreen;
PImage force35;
PImage force15;
PImage force20;
PImage fbdCheck;
PImage fbdUncheck;
PFont arial;
PFont bigfont;

boolean held15 = false;
boolean held20 = false;
boolean unlocked = false;
boolean correct = false;
boolean load = true;
boolean fbd = false;
int pos15 = 510;
int pos20 = 60;
int float15 = 0;
int float20 = 0;

float tilt = 0;

void setup() {
  size(670, 400);
  frameRate(60);
  bigfont = loadFont("Arial-Black-120.vlw");
  arial = loadFont("Arial-Black-20.vlw");
  base = loadImage("base.png");
  body15 = loadImage("body15.png");
  body20 = loadImage("body20.png");
  bar = loadImage("bar.png");
  lock = loadImage("lock.png");
  pass = loadImage("successtext.png");
  fail = loadImage("fail.png");
  bar_left = loadImage("bar_left.png");
  bar_right = loadImage("bar_right.png");
  startScreen = loadImage("startscreen.png");
  force35 = loadImage("35kgForce.png");
  force15 = loadImage("15kgForce.png");
  force20 = loadImage("20kgForce.png");
  fbdCheck = loadImage("FBD.png");
  fbdUncheck = loadImage("noFBD.png");
}

void mousePressed() {
  if(mouseX>pos15 && mouseX<pos15+body15.width){
    held15 = true;
  }

  if(mouseX>pos20 && mouseX<pos20+body20.width){
    held20 = true;
  }

  if(mouseX>305 && mouseX<365 && mouseY>245 && mouseY<305 && !load)
  {
    if((20*9.8)*float20 == (15*9.8)*float15) {
      unlocked = true;
      correct = true;   
    } 
    else {
      unlocked = true;
    }
  }
}

void mouseReleased() {
  held15 = false;
  held20 = false;
}

void draw() {
  background(0, 219, 219);
  textFont(arial, 20);

  if(load) {
    image(bar, 83, 285);
    image(base, 0, 270);
    image(body15, pos15, 150);
    image(body20, pos20, 150);
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

    if(pos20 > 310){
      pos20 = 310;
    }
    if(pos20 < 60){
      pos20 = 60;
    }


    if(pos15 < 310){
      pos15 = 310;
    }
    if(pos15 > 560){
      pos15 = 560;
    }

    float15 = ((310-pos15)*-1);
    float20 = ((310-pos20));
    text(float15+" cm", pos15, 135);
    text(float20+" cm", pos20, 145);
    if(held15 == true) {
      pos15 = mouseX-24;
      if(pos15 < 310){
        pos15 = 310;
      }
      if(pos15 > 560){
        pos15 = 560;
      }
      float15 = ((310-pos15)*-1);  
    }
    if(held20 == true) {
      pos20 = mouseX-24;
      if(pos20 > 310){
        pos20 = 310;
      }
      if(pos20 < 60){
        pos20 = 60;
      }
      float20 = (310-pos20);
    }
    image(body15, pos15, 150);
    image(body20, pos20, 150);
    image(lock, 305, 245);
  }

  if (unlocked && !correct && !load) {
    if((20*9.8)*float20 > (15*9.8)*float15) {
      background(0, 219, 219);

      float theta = (float)tilt/width * PI;
      pushMatrix();
      translate(width/2,285);
      rotate(theta);
      image(bar,-bar.width/2,-bar.height/2,bar.width,bar.height);

      pushMatrix();
      translate(pos15-311,0);
      text(float15+" cm", -24,-140);
      if(held15 == true) {
        pos15 = mouseX-24;
        if(pos15 < 310){
          pos15 = 310;
        }
        if(pos15 > 560){
          pos15 = 560;
        }
        float15 = ((310-pos15)*-1);  
      }
      image(body15,-body15.width/2,-body15.height,body15.width,body15.height);
      popMatrix();

      pushMatrix();
      translate(pos20-311,0);
      text(float20+" cm", -24, -140);
      if(held20 == true) {
        pos20 = mouseX-24;
        if(pos20 > 310){
          pos20 = 310;
        }
        if(pos20 < 60){
          pos20 = 60;
        }
        float20 = (310-pos20);
      }
      image(body20,-body20.width/2,-body20.height,body20.width,body20.height);
      popMatrix();

      popMatrix();

      image(base, 0, 270);
      image(fail, 305, 245);
      if(tilt>-50) {
        tilt--;
      }
    }
    else if((20*9.8)*float20 < (15*9.8)*float15) {
      background(0, 219, 219);

      float theta = (float)tilt/width * PI;
      pushMatrix();
      translate(width/2,285);
      rotate(theta);
      image(bar,-bar.width/2,-bar.height/2,bar.width,bar.height);

      pushMatrix();
      translate(pos15-311,0);
      text(float15+" cm", -24,-140);
      if(held15 == true) {
        pos15 = mouseX-24;
        if(pos15 < 310){
          pos15 = 310;
        }
        if(pos15 > 560){
          pos15 = 560;
        }
        float15 = ((310-pos15)*-1);  
      }
      image(body15,-body15.width/2,-body15.height,body15.width,body15.height);
      popMatrix();

      pushMatrix();
      translate(pos20-311,0);
      text(float20+" cm", -24, -140);
      if(held20 == true) {
        pos20 = mouseX-24;
        if(pos20 > 310){
          pos20 = 310;
        }
        if(pos20 < 60){
          pos20 = 60;
        }
        float20 = (310-pos20);
      }
      image(body20,-body20.width/2,-body20.height,body20.width,body20.height);
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
      textFont(arial, 20);
      text(float15+" cm", (pos15+23+335)/2-30, 215);
      text(float20+" cm", (pos20+22+335)/2-30, 215);
      text("Success!", 10, 25);
      text("ΣF[X] = 0", 10, 50);
      text("ΣF[Y] = -196N - 147N + 343N = 0", 10, 75);
      text("ΣM[B] = 196N * " + float20 + "cm" + " - 147N * " + float15 + "cm", 10, 100);
      image(force15, pos15-14, 264);
      image(force20, pos20-12, 264);
      image(force35, 245, 189);
      //image(pass, 10, 10);

      line(pos20+22, 220, 333, 220);
      line(pos20+22, 215, pos20+22, 225);
      line(333, 215, 333, 225);

      line(pos15+23, 215, pos15+23, 225);
      line(333, 220, pos15+23, 220);

      image(fbdCheck, 10, 360);
    } 
    else {
      textFont(bigfont, 120);
      text("Success!", 35, 100);
      image(base, 0, 270);
      image(body15, pos15, 150);
      image(body20, pos20, 150);
      image(fbdUncheck, 10, 360);
    }
  }
}

