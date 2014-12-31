package game;

import java.io.IOException;

import model.Model;
import processing.core.PApplet;
import processing.core.PImage;
import server.Client;

public class ChooseCharacter extends PApplet{
	PImage background;
	public PImage[] characters;
	private boolean[] boughtCharacter;
	private int nowWhichCharacterChosed = 1;
	private int characterNum;
	private Game game;
	private Client client;
	private String userName;
	private int money ;
	
	public ChooseCharacter(Game game,Client client, Model model){
		//load background
		this.client=client;
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Shop/grass.jpg").getPath());
		
		this.game=game;
		this.money=this.client.coin;
		this.userName=this.client.name;
		//load characters' image & go_back_sign image
		this.characterNum=model.getCharatorNum();
		characters = new PImage[characterNum+2];
		for(int i=0;i<this.characterNum;i++){
			this.characters[i+1]=loadImage(this.getClass().getResource(model.getCharacterImg(i)).getPath());
		}
		this.characters[11] = loadImage(this.getClass().getResource("/res/Shop/go_back_sign_2.png").getPath());
		
		//identify which characters are bought
		boughtCharacter = new boolean[13];
		for(int i=1;i<11;i++){
			boughtCharacter[i]=this.client.haveCharacterFlag[i-1];
		}
		

	}
	public void setup(){
		size(500, 700);
		frameRate(15);
	}
	public void draw(){
		//set background
		image(this.background, -500, 0);
		
		//boughtCharacter[11] must be set true because it is the "go_back_sign"
		boughtCharacter[11] = true;
		
		if(wantToGoBack() && keyPressed && key==ENTER){
			game.chooseCharacterGotoGameStart();
		}
		else if (keyPressed && keyCode == UP){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp--;
				if(temp==0)
					temp=11;
				if(boughtCharacter[temp]==true){
					nowWhichCharacterChosed=temp;
					break;
				}
					
			}
		}
		else if (keyPressed && keyCode == DOWN){
			int temp = nowWhichCharacterChosed;
			while(true){
				temp++;
				if(temp==12)
					temp=1;
				if(boughtCharacter[temp]==true){
					nowWhichCharacterChosed=temp;
					break;
				}
					
			}
		}
		else if (keyPressed && keyCode == LEFT){
			int temp = nowWhichCharacterChosed;
			while(true){
				if(temp==4)
					temp=11;
				else if(temp==11)
					temp=8;
				else
					temp=(temp+8)%12;
				if(boughtCharacter[temp]==true){
					nowWhichCharacterChosed=temp;
					break;
				}
			}
		}
		else if (keyPressed && keyCode == RIGHT){
			int temp = nowWhichCharacterChosed;
			while(true){
				if(temp==8)
					temp=11;
				else if(temp==11)
					temp=4;
				else
					temp=(temp+4)%12;
				if(boughtCharacter[temp]==true){
					nowWhichCharacterChosed=temp;
					break;
				}
			}
		}
		else if (!wantToGoBack()&&keyPressed && key == ENTER){
			try {
				game.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=1;i<11;i++){
			if (boughtCharacter[i] == true){
				tint(120);
				
				//see if the character is now selected and then set as the brightest
				if (nowWhichCharacterChosed == i)
					tint(255);
				image(characters[i], width/13*(1+((i-1)/4)*4), height/15+150*((i%4+3)%4), 100, 150);
			}
			tint(0);
		}
		
		//show the "go_back_sign" image
		if (nowWhichCharacterChosed == 11){
			tint(255);
			image(characters[11], width/13*9, height/15*12, 150, 100);
		}
		else {
			tint(120);
			image(characters[11], width/13*9, height/15*12, 150, 100);
		}
		
		tint(255);
		

		//text "~Your Character~"
		fill(255, 100, 0);
		text("~Your Characters~", 60, 40);
		//text username
		text(userName, width/13*9, height/15*9);
		//text money
		text("Money\n"+money, width/13*9, height/15*10);
		textSize(40);
	}
	public int getChoseWhichCharacter(){
		//if return 11 means want to go back 
		return nowWhichCharacterChosed;
	}
	public boolean wantToGoBack(){
		if (nowWhichCharacterChosed == 11){
			return true;
		}
		else return false;
	}
}
