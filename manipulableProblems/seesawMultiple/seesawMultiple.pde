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
PFont arial;
PFont bigfont;

boolean held10 = false;
boolean held25 = false;
boolean held50 = false;
boolean unlocked = false;
boolean correct = false;
boolean load = true;
int pos10 = 100;
int pos25 = 60;
int pos50 = 510;
int float10 = 0;
int float25 = 0;
int float50 = 0;

float tilt = 0;

void setup() {
  size(670, 400);
  frameRate(60);
  bigfont = loadFont("AngsanaNew-120.vlw");
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
}

void mousePressed() {
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
    if((25*9.8)*float25 + (10*9.8)*float10 == (50*9.8)*float50) {
      unlocked = true;
      correct = true;   
    } 
    else {
      unlocked = true;
    }
  }
}

void mouseReleased() {
  held10 = false;
  held25 = false;
  held50 = false;
}

void draw() {
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
    if((25*9.8)*float25 + (10*9.8)*float10 > (50*9.8)*float50) {
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
    else if((25*9.8)*float25 + (10*9.8)*float10 < (50*9.8)*float50) {
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
    image(base, 0, 270);
    textFont(arial, 20);
    text(float10+" cm", (pos10+22+335)/2-30, 135);
    text(float25+" cm", (pos25+22+335)/2-30, 135);
    text(float50+" cm", (pos50+23+335)/2-30, 135);
    
    line(pos10+22, 140, 333, 140);
    line(pos10+22, 135, pos10+22, 145);
    line(333, 135, 333, 145);
    
    line(pos25+22, 140, 333, 140);
    line(pos25+22, 135, pos25+22, 145);
    
    line(pos50+23, 135, pos50+23, 145);
    line(333, 140, pos50+23, 140);
    image(force10, pos10, 280);
    image(force25, pos25, 280);
    image(force50, pos50-1, 280);
    image(force85, 308, 160);
    image(body10, pos10, 160);
    image(body25, pos25, 162);
    image(body50, pos50, 150);
    image(pass, 305, 245);
    textFont(bigfont, 120);
    text("Success!", 200, 100);
  }
}








