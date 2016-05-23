package com.jumbo.entities.graphics.particles;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.MinMaxVector;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.JumboTexture;
import com.jumbo.entities.JumboGraphicsGroup;
import com.jumbo.entities.graphics.JumboAnimation;
import com.jumbo.entities.graphics.JumboAnimationFrame;
import com.jumbo.tools.calculations.Dice;

public class JumboParticleField extends JumboEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JumboGraphicsGroup particles = new JumboGraphicsGroup();
	MinMaxVector xoffset = new MinMaxVector(0, 0), yoffset = new MinMaxVector(0, 0);
	MinMaxVector red = new MinMaxVector(255, 255);
	MinMaxVector green = new MinMaxVector(255, 255);
	MinMaxVector blue = new MinMaxVector(255, 255);
	MinMaxVector alpha = new MinMaxVector(255, 255);
	MinMaxVector speed = new MinMaxVector(10, 10);
	MinMaxVector delaytime = new MinMaxVector(0, 250);
	boolean spawner = true;
	boolean init = false;

	/**
	 * @return the spawner
	 */
	public boolean isSpawner() {
		return spawner;
	}

	/**
	 * @param spawner
	 *            the spawner to set
	 */
	public void setSpawner(boolean spawner) {
		this.spawner = spawner;
	}

	/**
	 * @return the speed
	 */
	public MinMaxVector getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(MinMaxVector speed) {
		this.speed = speed;
	}

	/**
	 * @return the xoffset
	 */
	public MinMaxVector getXoffset() {
		return xoffset;
	}

	/**
	 * @param xoffset
	 *            the xoffset to set
	 */
	public void setXoffset(MinMaxVector xoffset) {
		this.xoffset = xoffset;
	}

	/**
	 * @return the yoffset
	 */
	public MinMaxVector getYoffset() {
		return yoffset;
	}

	/**
	 * @param yoffset
	 *            the yoffset to set
	 */
	public void setYoffset(MinMaxVector yoffset) {
		this.yoffset = yoffset;
	}

	/**
	 * @return the red
	 */
	public MinMaxVector getRed() {
		return red;
	}

	/**
	 * @param red
	 *            the red to set
	 */
	public void setRed(MinMaxVector red) {
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public MinMaxVector getGreen() {
		return green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen(MinMaxVector green) {
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public MinMaxVector getBlue() {
		return blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(MinMaxVector blue) {
		this.blue = blue;
	}

	/**
	 * @return the alpha
	 */
	public MinMaxVector getAlpha() {
		return alpha;
	}

	/**
	 * @return the delaytime
	 */
	public MinMaxVector getDelaytime() {
		return delaytime;
	}

	/**
	 * @param delaytime
	 *            the delaytime to set
	 */
	public void setDelaytime(MinMaxVector delaytime) {
		this.delaytime = delaytime;
	}

	/**
	 * @param alpha
	 *            the alpha to set
	 */
	public void setAlpha(MinMaxVector alpha) {
		this.alpha = alpha;
	}

	class Particle extends JumboAnimation {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Particle(JumboAnimation a, Rectangle fieldbounds) {
			super(new ArrayList<JumboAnimationFrame>(), new Rectangle(0, 0, a.getBounds().width, a.getBounds().height));
			ArrayList<JumboEntity> array = a.getFrames().array;
			for (JumboEntity e : array) {
				JumboAnimationFrame f = (JumboAnimationFrame) e;
				frames.array.add(new JumboAnimationFrame(f.getDelay(), new JumboTexture(f.getTexture())));
			}
			w = fieldbounds.width;
			h = fieldbounds.height;
			resetPos();
			this.action = () -> {
				if (spawner) {
					resetPos();
				}
			};
		}

		public int x = 0, y = 0, w = 0, h = 0;

		@Override
		public Rectangle additionalCalculations(Rectangle bounds) {
			resetPos();
			return bounds;
		}

		@Override
		public void customRender() {
			Rectangle pos = outbounds;
			pos.x += x;
			pos.y += y;
			if ((pos.x < 0 || pos.x >= w || pos.y < 0 || pos.y >= h)) {
				if (spawner) {
					resetPos();
				} else {
					destroy();
				}
			}
			super.customRender();
		}

		public void resetPos() {
			Rectangle bounds = getBounds();
			bounds.x = Dice.roll(w);
			bounds.y = Dice.roll(h);
			x = xoffset.roll();
			y = yoffset.roll();
			setColor(new JumboColor(red.roll(), green.roll(), blue.roll(), alpha.roll()));
			this.rate = speed.roll() / 10.0f;
			reset();
			setDelay(delaytime.roll());
		}
	}

	public JumboParticleField(Rectangle fieldbounds, JumboAnimation a, int particlenum) {
		super(fieldbounds);
		particles.addParent(this);

		for (int i = 0; i < particlenum; i++) {
			Particle p = new Particle(a, fieldbounds);
			p.reset();
			p.resetPos();
			p.setLooping(false);
			p.setDelay(Dice.roll(500));
			// getBounds().x += p.x;
			// getBounds().y += p.y;
			// rot += p.rotchange;
			// if (getBounds().x < 0 || getBounds().x >= fieldbounds.width
			// || getBounds().x < 0
			// || getBounds().x >= fieldbounds.width) {
			// p.resetPos();
			// p.reset();
			// }
			particles.array.add(p);
		}
	}

	@Override
	public void customRender() {
		particles.render();
	}

	public void setRenderable(boolean bool) {
		for (JumboEntity e : particles.array) {
			((JumboGraphicsObject) e).setRenderable(bool);
		}
	}

	@Override
	public void customTick() {
		if (!init) {
			for (JumboEntity e : particles.array) {
				((Particle) e).resetPos();
			}
			init = true;
		}
		particles.tick();
		if (particles.array.isEmpty()) {
			destroy();
		}
	}

}
