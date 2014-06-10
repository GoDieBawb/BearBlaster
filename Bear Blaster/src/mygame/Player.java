/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Player extends Node {

  public int score;
  
  public void die(AppStateManager stateManager){
    GuiManager gui          = stateManager.getState(GuiManager.class);
    BearManager bears       = stateManager.getState(BearManager.class);
    CannonBallManager balls = stateManager.getState(CannonBallManager.class);
    gui.showStartMenu();
    gui.hideHud();
    bears.setEnabled(false);
    bears.bearNode.detachAllChildren();
    balls.setEnabled(false);
    balls.ballNode.detachAllChildren();
    
    }
  
  }
