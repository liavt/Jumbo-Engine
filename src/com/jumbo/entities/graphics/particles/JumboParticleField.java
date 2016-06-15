package com.jumbo.entities.graphics.particles;

import com.jumbo.components.Quad;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.Range;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.JumboGraphicsGroup;
import com.jumbo.entities.graphics.JumboAnimation;
import com.jumbo.entities.graphics.JumboAnimationFrame;
import com.jumbo.util.calculations.Dice;

public class JumboParticleField extends JumboEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JumboGraphicsGroup particles = new JumboGraphicsGroup();
	Range xoffset = new Range(0, 0), yoffset = new Range(0, 0);
	Range red = new Range(255, 255);
	Range green = new Range(255, 255);
	Range blue = new Range(255, 255);
	Range alpha = new Range(255, 255);
	Range speed = new Range(10, 10);
	Range delaytime = new Range(0, 250);
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
	public Range getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(Range speed) {
		this.speed = speed;
	}

	/**
	 * @return the xoffset
	 */
	public Range getXoffset() {
		return xoffset;
	}

	/**
	 * @param xoffset
	 *            the xoffset to set
	 */
	public void setXoffset(Range xoffset) {
		this.xoffset = xoffset;
	}

	/**
	 * @return the yoffset
	 */
	public Range getYoffset() {
		return yoffset;
	}

	/**
	 * @param yoffset
	 *            the yoffset to set
	 */
	public void setYoffset(Range yoffset) {
		this.yoffset = yoffset;
	}

	/**
	 * @return the red
	 */
	public Range getRed() {
		return red;
	}

	/**
	 * @param red
	 *            the red to set
	 */
	public void setRed(Range red) {
		this.red = red;
	}

	/**
	 * @return the green
	 */
	public Range getGreen() {
		return green;
	}

	/**
	 * @param green
	 *            the green to set
	 */
	public void setGreen(Range green) {
		this.green = green;
	}

	/**
	 * @return the blue
	 */
	public Range getBlue() {
		return blue;
	}

	/**
	 * @param blue
	 *            the blue to set
	 */
	public void setBlue(Range blue) {
		this.blue = blue;
	}

	/**
	 * @return the alpha
	 */
	public Range getAlpha() {
		return alpha;
	}

	/**
	 * @return the delaytime
	 */
	public Range getDelaytime() {
		return delaytime;
	}

	/**
	 * @param delaytime
	 *            the delaytime to set
	 */
	public void setDelaytime(Range delaytime) {
		this.delaytime = delaytime;
	}

	/**
	 * @param alpha
	 *            the alpha to set
	 */
	public void setAlpha(Range alpha) {
		this.alpha = alpha;
	}

	class Particle extends JumboAnimation {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Particle(JumboAnimation a, Quad fieldbounds) {
			super(new ArrayList<JumboAnimationFrame>(), new Quad(0, 0, a.getBounds().width, a.getBounds().height));
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
		public Quad additionalCalculations(Quad bounds) {
			resetPos();
			return bounds;
		}

		@Override
		public void customRender() {
			Quad pos = outbounds;
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
			Quad bounds = getBounds();
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

	public JumboParticleField(Quad fieldbounds, JumboAnimation a, int particlenum) {
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
