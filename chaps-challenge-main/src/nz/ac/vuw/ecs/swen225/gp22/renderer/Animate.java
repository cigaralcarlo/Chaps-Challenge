package nz.ac.vuw.ecs.swen225.gp22.renderer;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;

/** 
 * Responsible for the animation of entities/actors within
 * the game itself.
 * 
 * @author Benjamin McEvoy - 300579954
 * */
public class Animate{
	
	//Fields
	private int toX, toY, fromX, fromY;
	private ChapTile chap;
	private EnemyTile entity;
	private Tile ent;
	
	/**
	 * Constructor for making a new animation for actor object
	 * 
	 * @param fromX
	 * @param fromY
	 * @param toX
	 * @param toY
	 * @param ent
	 */
	public Animate(int fromX, int fromY, int toX, int toY, Tile ent) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		if(ent instanceof ChapTile) {
			this.chap = (ChapTile) ent;
		} else if (ent instanceof EnemyTile) {
			this.entity = (EnemyTile) ent;
		}
		this.ent = ent;
	}

	/**
	 * Responsible for the animation of the sprites within the game
	 * */
	public void animation(){
		if(toX == fromX-1){
			if(ent instanceof ChapTile) {
				chap.getFLeft();
			} else if (ent instanceof EnemyTile) {
				entity.getFLeft();
			}
		} else if(toX == fromX+1){
			if(ent instanceof ChapTile) {
				chap.getFRight();
			} else if (ent instanceof EnemyTile) {
				entity.getFRight();
			}
		} else if(toY == fromY-1){
			if(ent instanceof ChapTile) {
				chap.getFUp();
			} else if (ent instanceof EnemyTile) {
				return;
			}
		} else if(toY == fromY+1){
			if(ent instanceof ChapTile) {
				chap.getFDown();
			} else if (ent instanceof EnemyTile) {
				return;
			}
		} else{
			if(ent instanceof ChapTile) {
				chap.getFIdle();
			} else if (ent instanceof EnemyTile) {
				return;
			}
		}
	}	
	
}
