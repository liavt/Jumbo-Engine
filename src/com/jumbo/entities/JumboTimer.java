package com.jumbo.entities;

import java.awt.Rectangle;

import com.jumbo.components.interfaces.TriggeredAction;
import com.jumbo.core.JumboEntity;

public class JumboTimer extends JumboEntity {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected int delay, startTime, timeslooped;
	protected TriggeredAction action;

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public TriggeredAction getAction() {
		return action;
	}

	public void setAction(TriggeredAction action) {
		this.action = action;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	protected int rate = 1;
	protected boolean running = true;

	public JumboTimer(int delay, TriggeredAction action) {
		super(new Rectangle(0, 0, 0, 0));
		this.delay = delay;
		this.action = action;
	}

	public void pause() {
		running = false;
	}

	public void stop() {
		running = false;
		startTime = 0;
		destroy();
	}

	public void start() {
		running = true;
		startTime = 0;
	}

	public void rewind() {
		startTime = 0;
		timeslooped = 0;
	}

	@Override
	public void customRender() {

	}

	/**
	 * @return the timeslooped
	 */
	public int getTimeslooped() {
		return timeslooped;
	}

	/**
	 * @param timeslooped
	 *            the timeslooped to set
	 */
	public void setTimeslooped(int timeslooped) {
		this.timeslooped = timeslooped;
	}

	@Override
	public void customTick() {
		if (running) {
			final int time = ((int) (System.currentTimeMillis()) - startTime);
			if (Math.abs(time) >= delay) {
				startTime = (int) System.currentTimeMillis();
				action.action();
				timeslooped++;
			}
		}
	}
}
