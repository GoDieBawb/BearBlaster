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
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;

/**
 *
 * @author Bob
 */
public class InteractionManager extends AbstractAppState implements ActionListener {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private boolean           inv, click = false;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    setUpKeys();
    }
  
  private void setUpKeys(){
    app.getInputManager().addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    app.getInputManager().addListener(this, "Click");
    
    app.getInputManager().addMapping("Inventory", new KeyTrigger(KeyInput.KEY_E));
    app.getInputManager().addListener(this, "Inventory");
    }
  
  public void onAction(String binding, boolean isPressed, float tpf) {
    
    if (binding.equals("Inventory")) {
      inv = isPressed;
      if (isPressed){
      app.getInputManager().setCursorVisible(true);
      stateManager.getState(CameraManager.class).cam.setDragToRotate(true);
      }
      else {
      app.getInputManager().setCursorVisible(false);
      stateManager.getState(CameraManager.class).cam.setDragToRotate(false);
      }
    }
      
    if (binding.equals("Click")){
        
      click = isPressed;
      
      if (click)
      stateManager.getState(CannonBallManager.class).createCannonBall();
      
      }  
      
    }
  
  }
