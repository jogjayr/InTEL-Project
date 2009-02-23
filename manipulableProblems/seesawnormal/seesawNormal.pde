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
PFont arial;
PFont bigfont;

boolean held15 = false;
boolean held20 = false;
boolean unlocked = false;
boolean correct = false;
boolean load = true;
int pos15 = 510;
int pos20 = 60;
int float15 = 0;
int float20 = 0;

float tilt = 0;

void setup() {
  size(670, 400);
  frameRate(60);
  bigfont = loadFont("AngsanaNew-120.vlw");
  arial = loadFont("Arial-Black-20.vlw");
  base = loadImage("base.png");
  body15 = loadImage("body15.png");
  body20 = loadImage("body20.png");
  bar = loadImage("bar.png");
  lock = loadImage("lock.png");
  pass = loadImage("pass.png");
  fail = loadImage("fail.png");
  bar_left = loadImage("bar_left.png");
  bar_right = loadImage("bar_right.png");
  startScreen = loadImage("startscreen.png");
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
    rect(565, 275, 94,61 );
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
    text(float15+" cm", pos15, 145);
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
    image(base, 0, 270);
    textFont(arial, 20);
    text(float15+" cm", pos15, 145);
    text(float20+" cm", pos20, 145);
    image(body15, pos15, 150);
    image(body20, pos20, 150);
    image(pass, 305, 245);
    textFont(bigfont, 120);
    text("Success!", 200, 100);
  }
}






