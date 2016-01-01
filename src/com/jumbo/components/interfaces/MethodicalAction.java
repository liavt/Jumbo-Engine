package com.jumbo.components.interfaces;

@FunctionalInterface
public interface MethodicalAction<T> extends java.io.Serializable {
	public abstract T action(T obj);
}
