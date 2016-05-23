package com.jumbo.components.interfaces;

@FunctionalInterface
public interface ReturnableAction<T> extends java.io.Serializable {
	public abstract T action();
}
