package ch.epfl.cs107.play.game.tutos.actor;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.awt.Color;

public class SimpleGhost extends Entity{
	
	private float hp;
	private TextGraphics hpText;
	private Sprite sprite;
	private float delta;
	
	public SimpleGhost(Vector position, String spriteName) {
		super(position);
		
		this.delta = 0.05f;
		
		this.hp = 10;
		this.sprite = new Sprite(spriteName, 1, 1.f, this);
		
		this.hpText = new TextGraphics(Integer.toString((int)hp), 0.4f, Color.BLUE);
		this.hpText.setParent(this);
		this.hpText.setAnchor(new Vector(-0.3f, 0.1f));
	}
	
	public boolean isWeak() {
		if (this.hp <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void strengthen() {
		this.hp = 10;
	}
	
	public void moveUp() {
		setCurrentPosition(getPosition().add(0.f, delta));
	}
	
	public void moveDown() {
		setCurrentPosition(getPosition().add(0.f, -delta));
	}
	
	public void moveRight() {
		setCurrentPosition(getPosition().add(delta, 0.f));
	}
	
	public void moveLeft() {
		setCurrentPosition(getPosition().add(-delta, 0.f));
	}
	
	@Override
	public void draw(Canvas canvas) {
		this.sprite.draw(canvas);
		this.hpText.draw(canvas);
	}
	
	@Override
	public void update(float deltaTime) {
		//dealing with hp
		if (hp > deltaTime) {
			hp -= deltaTime;
		}
		else {
			hp = 0;
		}
		hpText.setText(Integer.toString((int)hp));
	}
}
