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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;

/**
 *
 * @author Bob
 */
public class CannonBallManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private BulletAppState    physics;
  private Sphere            sphere;
  private Material          mat;
  public  Node              ballNode;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    this.physics      = this.stateManager.getState(SceneManager.class).physics;
    ballNode          = new Node();
    sphere            = new Sphere(32, 32, 0.4f, true, false);
    mat               = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    mat.setColor("Color", ColorRGBA.DarkGray);
    
    this.app.getRootNode().attachChild(ballNode);
    this.setEnabled(false);
    }
  
  public void createCannonBall() {
  
  if (ballNode.getQuantity() < 5)   {
    CannonBall ball   = new CannonBall();  
    Geometry ball_geo = new Geometry("Ball", sphere);
    ball.shootTime    = System.currentTimeMillis() / 1000;
    
    ball_geo.scale(.3f);
    
    ball.attachChild(ball_geo);
    ballNode.attachChild(ball);
    
    ball_geo.setMaterial(mat);
    /** Position the cannon ball  */
    ball_geo.setLocalTranslation(app.getCamera().getLocation());
    /** Make the ball physcial with a mass > 0.0f */
    RigidBodyControl ball_phy = new RigidBodyControl(1f);
    
    /** Add physical ball to physics space. */
    ball_geo.addControl(ball_phy);
    physics.getPhysicsSpace().add(ball_phy);
    /** Accelerate the physcial ball to shoot it. */
    ball_phy.setLinearVelocity(app.getCamera().getDirection().mult(25));
    }   
  }
  
  @Override
  public void update(float tpf){
    
    for (int i = 0; i < ballNode.getQuantity(); i++) {
      
      CannonBall currentBall = (CannonBall) ballNode.getChild(i);
      
      CollisionResults results = new CollisionResults();
      stateManager.getState(BearManager.class).bearNode.collideWith(currentBall.getChild("Ball").getWorldBound(), results);
      
      if (results.size() > 0){
          
        Bear hitBear = (Bear) results.getCollision(0).getGeometry().getParent().getParent();
        
        if (!hitBear.isDead){
          hitBear.die();
          stateManager.getState(PlayerManager.class).player.score++;
          }
        
        }
      
      if (System.currentTimeMillis() / 1000 - currentBall.shootTime > 2) {
        currentBall.removeFromParent();
        }
        
      } 
      
    }
  
  }
