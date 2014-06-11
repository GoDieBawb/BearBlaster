/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import java.util.Random;

/**
 *
 * @author Bob
 */
public class BearManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private BulletAppState    physics;
  private Node              rootNode;
  public  Node              bearNode;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.rootNode     = this.app.getRootNode();
    physics           = stateManager.getState(SceneManager.class).physics;
    bearNode          = new Node();
    rootNode.attachChild(bearNode);
    
    this.setEnabled(false);
    }
  
  private void createBear(){
    Bear bear        = new Bear();
    bear.model       = (Node) assetManager.loadModel("Models/bear.j3o");
    bear.phys        = new BetterCharacterControl(.3f, .3f, 100f);
    bear.animControl = bear.model.getControl(AnimControl.class);
    bear.animChannel = bear.animControl.createChannel();
    TextureKey key   = new TextureKey("Models/bear.png", true);
    Texture tex      = assetManager.loadTexture(key);
    Material mat     = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    
    mat.setTexture("ColorMap", tex);
    bear.model.setMaterial(mat);
    
    bear.attachChild(bear.model);
    bear.addControl(bear.phys);
    
    bear.animChannel.addAllBones();
    bear.animChannel.setAnim("run");
    
    physics.getPhysicsSpace().add(bear.phys);
    bear.phys.setGravity(new Vector3f(0, -50, 0));
    
    bearNode.attachChild(bear);
    placeBear(bear);
    }
  
  private void placeBear(Bear bear) {
    
    Random rand = new Random();
    int xSpot   = rand.nextInt(5) + 15;
    int zSpot   = rand.nextInt(5) - 30;
    bear.phys.warp(new Vector3f(xSpot, 3, zSpot));
    
    }
  
  @Override
  public void update(float tpf){
      
    if (bearNode.getQuantity() < 2) {
      createBear();
      }  
      
    for (int i = 0; i < bearNode.getQuantity(); i++){
      
      Bear currentBear = (Bear) bearNode.getChild(i);
      Vector3f playerDir = app.getCamera().getLocation().subtract(currentBear.model.getWorldTranslation());
      
      if (currentBear.model.getWorldTranslation().distance(stateManager.getState(PlayerManager.class).player.getWorldTranslation()) < 1.5f){
        currentBear.animChannel.setAnim("idle_stand");
        stateManager.getState(PlayerManager.class).player.die(stateManager);
        }
      
      if (!currentBear.isDead){
          
        currentBear.phys.setWalkDirection(playerDir.mult(.5f));
        currentBear.phys.setViewDirection(playerDir);
        
        }
        
      else {
          
        if (System.currentTimeMillis()/1000 - currentBear.deathTime > 1.9f)
        currentBear.removeFromParent();
        physics.getPhysicsSpace().remove(currentBear.phys);
        }
      
      }
      
    }
  
  }
