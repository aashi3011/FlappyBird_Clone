package com.example.flappyclone;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyMy extends ApplicationAdapter {
	SpriteBatch batch;
//	ShapeRenderer shaper;
	Circle birdcircle;
Rectangle[] rectangle1;
	Rectangle[] rectangle2;

	Texture back;
	Texture tube1;
	Texture tube2;

	Texture[] birds;
	int flag=0;
	Texture gameover;
int flap=0;
	int gamestate=0;
	int velocity=0;
	int gravity=2;
	int birdY;
float gap=700;
BitmapFont font;

Random ran;
int number=4;
float tubeX[]=new float[number];
	float tubeoff[]=new float[number];
	int distancebetweentubes;
	int score;
	int r=0;

	@Override
	public void create () {
		score=0;
		gameover=new Texture("game_over.png");
		font=new BitmapFont();
		font.setColor(Color.FIREBRICK);
		font.getData().setScale(10);
		batch = new SpriteBatch();
		//shaper=new ShapeRenderer();
		birdcircle=new Circle();
		back=new Texture("bg.png");
		birds =new Texture[2];
		birds[0]=new Texture("bird.png");
		birds[1]=new Texture("bird2.png");
		tube1=new Texture("toptube.png");
		tube2=new Texture("bottomtube.png");
		birdY=(Gdx.graphics.getHeight())/2-(birds[flag].getHeight())/2;
		ran=new Random();
		distancebetweentubes=Gdx.graphics.getWidth();
		rectangle1=new Rectangle[number];
		rectangle2=new Rectangle[number];
		for(int i=0;i<number;i++)
		{
			tubeoff[i]= (float) ((ran.nextFloat()-0.5)*(Gdx.graphics.getHeight()-200-gap));
			tubeX[i]=Gdx.graphics.getWidth()+i*distancebetweentubes;
			rectangle1[i]=new Rectangle();
			rectangle2[i]=new Rectangle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		//shaper.begin(ShapeRenderer.ShapeType.Filled);
		batch.draw(back,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight()) ;
		for(int i=0;i<number;i++) {
			if (Intersector.overlaps(birdcircle, rectangle1[i]) || Intersector.overlaps(birdcircle, rectangle2[i])) {

				gamestate=2;


			}
		}
		if(gamestate==1)
		{

			if(Gdx.input.justTouched())
			{
				velocity=-30;

			}


			for(int i=0;i<number;i++) {



				if(tubeX[i]<=-tube1.getWidth()) {


					tubeX[i] += number * distancebetweentubes;
					tubeoff[i]= (float) ((ran.nextFloat()-0.5)*(Gdx.graphics.getHeight()-200-gap));
				}
				else
					tubeX[i] = tubeX[i] - 4;
if(tube1.getHeight()<(Gdx.graphics.getHeight()-(Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i])))
	 {
	batch.draw(tube1, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i], tube1.getWidth(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i]));

	rectangle1[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i], tube1.getWidth(), Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i]));

}
else
{
	batch.draw(tube1, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i]);

	rectangle1[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoff[i], tube1.getWidth(), tube1.getHeight());

}
if(tube2.getHeight()<Gdx.graphics.getHeight() / 2 +  tubeoff[i])
{
	batch.draw(tube2, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - (Gdx.graphics.getHeight() / 2 +  tubeoff[i]) + tubeoff[i], tube2.getWidth(), Gdx.graphics.getHeight() / 2 +  tubeoff[i]);
	rectangle2[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - (Gdx.graphics.getHeight() / 2 +  tubeoff[i]) + tubeoff[i], tube2.getWidth(), Gdx.graphics.getHeight() / 2 +  tubeoff[i]);
}
else
{
	batch.draw(tube2, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - tube2.getHeight() + tubeoff[i]);
	rectangle2[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - tube2.getHeight() + tubeoff[i], tube2.getWidth(), tube2.getHeight());
}

				//shaper.rect(rectangle1.x, rectangle1.y, rectangle1.width, rectangle1.height);
				//shaper.rect(rectangle2.x, rectangle2.y, rectangle2.width, rectangle2.height);




			}


			if(birdY>0 || velocity<0)
			{
				velocity=velocity+gravity;
				birdY-=velocity;
			}

		}
		else if(gamestate==0)
		{
			if(Gdx.input.justTouched())
			{

				gamestate=1;
			}
		}
		else
		{
			batch.draw(gameover,Gdx.graphics.getWidth()/2-gameover.getWidth()/2,Gdx.graphics.getHeight()/2-gameover.getHeight()/2,500,500);
			if(Gdx.input.justTouched())
			{


				birdY=(Gdx.graphics.getHeight())/2-(birds[flag].getHeight())/2;
				gamestate=0;
				score=0;
				for(int i=0;i<number;i++)
				{
					tubeoff[i]= (float) ((ran.nextFloat()-0.5)*(Gdx.graphics.getHeight()-200-gap));
					tubeX[i]=Gdx.graphics.getWidth()+i*distancebetweentubes;
					rectangle1[i]=new Rectangle();
					rectangle2[i]=new Rectangle();
				}
				r=0;
				velocity=0;
			}
		}

		if(flag==0)
			flag=1;
		else
			flag=0;


		batch.draw(birds[flag],(Gdx.graphics.getWidth())/2-(birds[flag].getWidth())/2,birdY);


		birdcircle.set(Gdx.graphics.getWidth()/2,birdY+birds[flag].getHeight()/2,birds[flag].getWidth()/2);

		if(tubeX[r]+tube1.getWidth()<Gdx.graphics.getWidth()/2)
		{
			score++;
			Gdx.app.log("collision", Integer.toString(score));
			r++;
			if(r>=number)
				r=0;

		}
		font.draw(batch,Integer.toString(score),100,200);

		//shaper.setColor(Color.BLUE);
		//shaper.circle(birdcircle.x,birdcircle.y,birdcircle.radius);

		batch.end();
		//shaper.end();







	}
	
	@Override
	public void dispose () {
		batch.dispose();
		back.dispose();
	}
}
