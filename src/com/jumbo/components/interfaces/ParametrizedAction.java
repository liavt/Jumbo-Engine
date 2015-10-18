package com.jumbo.components.interfaces;

public interface ParametrizedAction<T> extends java.io.Serializable {
	public void action(T obj);
}
