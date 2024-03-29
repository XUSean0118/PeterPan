package game;

import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JFrame;

import org.json.JSONException;

import model.Model;
import processing.core.PImage;
import server.Client;

public class Game extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle bounds = new Rectangle(500,700);
	private Client client;
	private TopBar topBar;
	private Model model;
	private GameScene gameScene;
	private GameStart gameStart;
	private ChooseCharacter chooseCharacter;
	private Character character;
	private Shop shop;
	private GameOver endPanel;
	private ScoreBar scoreBar;
	public boolean winFlag;
	/**
	 * Constructor of a game 
	 */
	public Game(Client client){
		super("Peter Pan");
		this.client=client;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(bounds);
		try {
			this.model=new Model();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initGameStart();
		this.setVisible(true);
	}
	public void initGameStart(){
		this.gameStart=new GameStart(this,this.client);
		this.gameStart.init();
		this.gameStart.start();
		
		this.add(gameStart);
		
	}
	public void initChooseCharacter(){
		this.chooseCharacter=new ChooseCharacter(this,this.client,model);
		this.chooseCharacter.init();
		this.chooseCharacter.start();
		this.remove(gameStart);
		this.gameStart.stop();
		this.add(chooseCharacter);
	}
	public void initShop(){
		this.shop=new Shop(this,this.client,model);
		this.shop.init();
		this.shop.start();
		
		this.remove(gameStart);
		this.gameStart.stop();
		this.add(shop);
	}
	
	public void chooseCharacterGotoGameStart(){
		this.remove(chooseCharacter);
		this.chooseCharacter.stop();
		this.chooseCharacter.destroy();
		
		this.gameStart.start();
		this.add(gameStart);
		gameStart.requestFocus();
	}
	public void shopGotoGameStart(){
		this.remove(shop);
		this.shop.stop();
		this.shop.destroy();
		
		this.gameStart.start();
		this.add(gameStart);
		gameStart.requestFocus();
	}
	/**
	 * Initialize what a game contains, including a top bar and a game scene
	 * @throws IOException 
	 */
	public void start() throws IOException{
		this.winFlag = false;
		this.topBar = new TopBar(new Rectangle(bounds.width, 50));
		this.scoreBar=new ScoreBar();
		
		this.gameScene = new GameScene(this,this.client,this.model);
		this.gameScene.init();
		this.gameScene.start();
		this.gameScene.setTopBarDelegate(topBar);
		this.gameScene.setScoreBarDelegate(scoreBar);
		this.gameStart.destroy();
		this.remove(chooseCharacter);
		chooseCharacter.stop();
		chooseCharacter.destroy();
		this.add(topBar);
		this.add(scoreBar);
		this.add(gameScene);
		
	}

	public int getChosenCharacter(){
		return this.chooseCharacter.getChoseWhichCharacter();
	}
	public PImage[] getCharacterImages(){
		return this.chooseCharacter.characters;
	}
	/**
	 * A method invoked when plane crashed
	 * @throws JSONException 
	 */
	public void gameOver() throws JSONException {

		this.remove(this.gameScene);
		gameScene.exit();
		this.gameScene.destroy();
		this.remove(topBar);
		this.remove(scoreBar);
		this.client.sendOver();
		this.endPanel=new GameOver(this.client,this);
		this.endPanel.setIsWin(winFlag);
		endPanel.init();
		endPanel.start();
		this.add(endPanel);
		//this.setVisible(true);
	}
	public void restart(){
		remove(endPanel);
		endPanel.destroy();
		initGameStart();
	}
	public void exitGame() throws JSONException{
		this.client.sendGameOver();
		System.exit(0);
	}

	
	/**
	 * A method invoked when the plane successfully fly pass 20 rocks
	 */
	
}
