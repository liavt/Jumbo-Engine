package com.jumbo.components.interfaces;

import com.jumbo.core.JumboEntity;

@FunctionalInterface
public interface ArrayIteratorAction extends java.io.Serializable {
	public abstract void action(JumboEntity e);
}
