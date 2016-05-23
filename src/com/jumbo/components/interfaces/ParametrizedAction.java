package com.jumbo.components.interfaces;

@FunctionalInterface
public interface ParametrizedAction<T> extends java.io.Serializable {
	public abstract void action(T obj);
}
