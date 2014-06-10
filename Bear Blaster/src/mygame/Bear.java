/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Bear extends Node {
    
  public AnimControl animControl;
  public AnimChannel animChannel;
  public Node        model;
  public BetterCharacterControl phys;
  public boolean     isDead;
  public long        deathTime;
  
  public void die(){
    isDead    = true;
    deathTime = System.currentTimeMillis() / 1000;
    animChannel.setAnim("die");
    animChannel.setLoopMode(LoopMode.DontLoop);
    phys.setWalkDirection(new Vector3f(0, 0, 0));
    }
  
}
