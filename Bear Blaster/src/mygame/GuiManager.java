/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.core.Screen;

/**
 *
 * @author Bob
 */
public class GuiManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private ButtonAdapter     startButton;
  private ButtonAdapter     shootButton;
  private TextElement       scoreDisplay;
  private TextElement       crossHairs;
  private Screen            screen;
  private BitmapFont        font;
  private BitmapFont        font1;
  private Player            player;
  
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.font         = assetManager.loadFont("Interface/Fonts/Impact.fnt");
    this.font1        = assetManager.loadFont("Interface/Fonts1/Impact.fnt");
    this.player       = stateManager.getState(PlayerManager.class).player;
    
    screen = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    //screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    this.app.getInputManager().setSimulateMouse(true);
    
    initStartButton();
    initFireButton();
    initScoreDisplay();
    initCrosshairs();
    }
  
  private void initStartButton(){
    startButton  = new ButtonAdapter( screen, "StartButton", new Vector2f(15, 15) ) {
    
    @Override
    public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
      startButton.hide();
      showHud();
      player.score = 0;
      stateManager.getState(CannonBallManager.class).ballNode.detachAllChildren();
      stateManager.getState(BearManager.class).setEnabled(true);
      stateManager.getState(CannonBallManager.class).setEnabled(true);
      }
    
    };
    
    screen.addElement(startButton);
    startButton.setDimensions(screen.getWidth()/5, screen.getHeight()/10);
    startButton.setPosition(screen.getWidth() / 2 - startButton.getWidth()/2, screen.getHeight() / 2);
    startButton.setText("Start");
    //startButton.setFont("Interface/Fonts1/Impact.fnt");
  }
  
  private void initFireButton(){
    
    shootButton  = new ButtonAdapter( screen, "ShootButton", new Vector2f(15, 15) ) {
    
      @Override
      public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
        app.getStateManager().getState(CannonBallManager.class).createCannonBall();
        }
      };
      
    screen.addElement(shootButton);
    shootButton.setPosition(screen.getWidth() * .9f - shootButton.getWidth()/2, screen.getHeight() * .1f - shootButton.getHeight()/2);
    //shootButton.setFont("Interface/Fonts/Impact.fnt");
    shootButton.setText("Shoot");
    shootButton.hide();
    
    }
  
  private void initScoreDisplay() {
    
    scoreDisplay = new TextElement(screen, "ScoreDisplay", Vector2f.ZERO, new Vector2f(300,50), font) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    screen.addElement(scoreDisplay);
    scoreDisplay.setFontSize(screen.getWidth()/25);
    scoreDisplay.setPosition(screen.getWidth() / 1.1f - scoreDisplay.getWidth()/2, screen.getHeight() / 1.1f - scoreDisplay.getHeight());
    scoreDisplay.hide();
    }
  
  private void initCrosshairs(){

    crossHairs = new TextElement(screen, "CrossHairs", Vector2f.ZERO, new Vector2f(300,50), font) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    screen.addElement(crossHairs);
    crossHairs.setFontSize(screen.getWidth()/25);
    crossHairs.setText("+");
    crossHairs.setPosition(screen.getWidth()/2, screen.getHeight()/2 - crossHairs.getHeight()/2);      
    crossHairs.hide();
    }
  
  public void hideHud(){
    shootButton.hide();
    crossHairs.hide();
    }
  
  private void showHud(){
    scoreDisplay.show();
    shootButton.show();
    crossHairs.show();
    }
  
  public void showStartMenu(){
    startButton.show();
    shootButton.hide();
    }
  
  private void updateScoreDisplay(){
    scoreDisplay.setText("Bears Killed: " + player.score);
    }
  
  @Override
  public void update(float tpf) {
    updateScoreDisplay();
    }
  
  }
