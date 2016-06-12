package com.jumbo.entities.graphics.particles;

import com.jumbo.components.Quad;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.Range;
import com.jumbo.components.Position;
import com.jumbo.core.Jumbo;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.core.texture.JumboTexture;
import com.jumbo.entities.JumboGraphicsGroup;
import com.jumbo.entities.graphics.JumboAnimation;
import com.jumbo.entities.graphics.JumboAnimationFrame;
import com.jumbo.tools.calculations.Dice;

public class JumboParticleEmitter extends JumboEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JumboGraphicsGroup particles = new JumboGraphicsGroup();
	Quad fieldbounds;
	private boolean init = false;
	Range xoffset = new Range(0, 0), yoffset = new Range(0, 0), red = new Range(255, 255),
			green = new Range(255, 255), blue = new Range(255, 255), alpha = new Range(255, 255),
			speed = new Range(10, 10), delaytime = new Range(0, 250);

	/**
	 * @return the delay
	 */
	public Range getDelaytime() {
		return delaytime;
	}

	/**
	 * @param delay
	 *            the delay to set
	 */
	public void setDelaytime(Range delay) {
		this.delaytime = delay;
	}

	boolean spawner = true;

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

		public Particle(JumboAnimation a) {
			super(new ArrayList<JumboAnimationFrame>(), new Quad(0, 0, a.getBounds().width, a.getBounds().height));
			ArrayList<JumboEntity> array = a.getFrames().array;
			for (JumboEntity e : array) {
				JumboAnimationFrame f = (JumboAnimationFrame) e;
				frames.array.add(new JumboAnimationFrame(f.getDelay(), new JumboTexture(f.getTexture())));
			}
			renderpos = new Position(fieldbounds.x, fieldbounds.y);
			resetPos();
			this.action = () -> {
				if (spawner) {
					resetPos();
				}
			};
		}

		public int x = 0, y = 0;

		@Override
		public void customRender() {
			increasePosition(x, y);
			Position pos = getRenderposition();
			if ((pos.x < 0 || pos.x > Jumbo.getFrameWidth() || pos.y < 0 || pos.y > Jumbo.getFrameHeight())) {
				if (spawner) {
					resetPos();
				} else {
					destroy();
				}
			}
			super.customRender();
			// JumboConsole.log(fieldbounds + " " + outbounds);
		}

		public void resetPos() {
			Quad bounds = this.getBounds();
			bounds.x = Dice.roll(fieldbounds.width) + fieldbounds.x;
			bounds.y = Dice.roll(fieldbounds.height) + fieldbounds.y;
			x = xoffset.roll();
			y = yoffset.roll();
			setColor(new JumboColor(red.roll(), green.roll(), blue.roll(), alpha.roll()));
			this.rate = speed.roll() / 10.0f;
			reset();
			setDelay(delaytime.roll());
			setBounds(bounds);
		}
	}

	public JumboParticleEmitter(Quad bounds, JumboAnimation a, int particlenum) {
		super(bounds);
		fieldbounds = bounds;

		for (int i = 0; i < particlenum; i++) {
			Particle p = new Particle(a);
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
		particles.setAction((JumboEntity e) -> {
			e.setMaintainx(maintainx);
			e.setMaintainy(maintainy);
			e.setMaintainwidth(maintainwidth);
			e.setMaintainheight(maintainheight);
		});
	}

	@Override
	public void customRender() {
		particles.render();

	}

	@Override
	public Quad additionalCalculations(Quad inbounds) {
		final Quad bounds = super.additionalCalculations(inbounds);
		fieldbounds = bounds;
		return bounds;
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
