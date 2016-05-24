package com.jumbo.entities.graphics;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.jumbo.components.JumboColor;
import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboEntity;
import com.jumbo.core.JumboGraphicsObject;
import com.jumbo.entities.JumboGraphicsGroup;
import com.jumbo.tools.input.console.JumboConsole;

public class JumboAnimation extends JumboImage {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected JumboGraphicsGroup frames = new JumboGraphicsGroup();
	protected int startTime = 0, delay = 0;
	protected float currentframe = 0;
	protected boolean looping = true, loopedonce = false, didaction = false;
	protected TriggeredAction action;
	protected float rate = 1;

	public int getCurrentframe() {
		return (int) currentframe;
	}

	public void setCurrentframe(int currentframe) {
		this.currentframe = currentframe;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public void reset() {
		currentframe = 0;
		loopedonce = false;
		didaction = false;
		dead = false;
	}

	public boolean hasLoopedOnce() {
		return loopedonce;
	}

	public void resetLoop() {
		this.loopedonce = false;
	}

	public JumboAnimation(JumboAnimationFrame[] frames, Rectangle rect) {
		super(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), rect);
		initFrames(frames);
		this.frames.addParent(this);
	}

	public JumboAnimation(ArrayList<JumboAnimationFrame> a, Rectangle rect) {
		super(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB), rect);
		initFrames(a);
		this.frames.addParent(this);
	}

	public JumboGraphicsGroup getFrames() {
		return frames;
	}

	public void setFrames(JumboAnimationFrame[] frames) {
		initFrames(frames);
	}

	public void setFrames(JumboGraphicsGroup g) {
		this.frames = g;
	}

	protected void initFrames(JumboAnimationFrame[] frames) {
		this.frames.array.clear();
		for (JumboAnimationFrame f : frames) {
			f.getBounds().width = getBounds().width;
			f.getBounds().height = getBounds().height;
			this.frames.array.add(f);
		}
	}

	protected void initFrames(ArrayList<JumboAnimationFrame> frames) {
		this.frames.array.clear();
		for (JumboAnimationFrame f : frames) {
			f.getBounds().width = getBounds().width;
			f.getBounds().height = getBounds().height;
			this.frames.array.add(f);
		}
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public boolean isLooping() {
		return looping;
	}

	public void setLooping(boolean looping) {
		this.looping = looping;
	}

	public boolean isDidaction() {
		return didaction;
	}

	public void setDidaction(boolean didaction) {
		this.didaction = didaction;
	}

	public TriggeredAction getAction() {
		return action;
	}

	public void setAction(TriggeredAction action) {
		this.action = action;
	}

	public void setColor(JumboColor c) {
		for (JumboEntity e : frames.array) {
			((JumboGraphicsObject) e).getTexture().setColor(c);
		}
	}

	@Override
	public void setRenderable(boolean bool) {
		for (JumboEntity e : frames.array) {
			((JumboGraphicsObject) e).setRenderable(bool);
		}
	}

	@Override
	public void customRender() {
		if (delay <= 0) {
			final JumboEntity f = frames.array.get((int) currentframe);
			if (f != null) {
				final ArrayList<JumboEntity> parents = f.getParents(), thisparents = getParents();
				if (parents != thisparents) {
					f.setParents(thisparents);
				}
				f.setOutbounds(getOutbounds());
				f.render();
				setRenderposition(((JumboGraphicsObject) f).getRenderposition());
			} else {
				JumboConsole.log("[" + this + "]: Error displaying animation frame!", 1);
			}
		}

	}

	@Override
	public void customTick() {
		if (delay > 0) {
			delay--;
		}
		if (delay <= 0 && frames.array != null && !frames.array.isEmpty()) {
			final int frame = (int) Math.floor(currentframe);
			final int time = ((int) (System.currentTimeMillis()) - startTime);
			if (currentframe >= frames.array.size()) {
				currentframe = 0;
				loopedonce = true;
			}
			if (Math.abs(time) >= ((JumboAnimationFrame) frames.array.get(frame)).getDelay()) {
				startTime = (int) System.currentTimeMillis();
				currentframe += rate;
			}
			if (currentframe >= frames.array.size()) {
				currentframe = 0;
				loopedonce = true;
			}
			if (!looping && loopedonce) {
				didaction = true;
				currentframe = frames.array.size() - 1;
				if (didaction) {
					this.dead = true;
				}
			}
		}
		if (delay <= 0) {
			final JumboEntity f = frames.array.get((int) currentframe);
			if (f != null) {
				f.tick();
			} else {
				JumboConsole.log("[" + this + "]: Error displaying animation frame!", 1);
			}
		}
	}
}
