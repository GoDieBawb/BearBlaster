/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState {

  private SimpleApplication app;
  public  BulletAppState    physics;
  public  Node              scene;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.physics      = new BulletAppState();
    physics.setDebugEnabled(true);
    stateManager.attach(physics);
    initScene();
    }
  
  private void initScene(){
    scene                 = (Node) app.getAssetManager().loadModel("Scenes/Scene.j3o");
    RigidBodyControl phys = new RigidBodyControl(0f);
    scene.addControl(phys);
    physics.getPhysicsSpace().add(phys);
    app.getRootNode().attachChild(scene);
    makeUnshaded();
    }

  private void makeUnshaded(){
      
    SceneGraphVisitor sgv = new SceneGraphVisitor() {
 
    public void visit(Spatial spatial) {
 
      if (spatial instanceof Geometry) {
        
        Geometry geom = (Geometry) spatial;
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        if (geom.getMaterial().getTextureParam("DiffuseMap") != null)
        mat.setTexture("ColorMap", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
        geom.setMaterial(mat);
       
        }
      
      }
    };
    
    app.getRootNode().depthFirstTraversal(sgv);
    
  }
  
  }
