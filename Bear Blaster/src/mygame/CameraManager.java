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
import com.jme3.input.ChaseCamera;

/**
 *
 * @author Bob
 */
public class CameraManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Player            player;
  public  ChaseCamera       cam;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    initCamera();
    }
  
  public void initCamera(){
    //Creates a new chase cam and attached it to the player.model for the game
    cam = new ChaseCamera(this.app.getCamera(), player, this.app.getInputManager());
    cam.setMinDistance(1f);
    cam.setMaxDistance(1f);
    cam.setDragToRotate(false);
    cam.setRotationSpeed(5f);
    cam.setInvertVerticalAxis(true);
    }
  
  }
