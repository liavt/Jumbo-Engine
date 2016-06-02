package com.jumbo.components;

/**
 * In a lambda expression, you must use final variables. This class stores an
 * int value, and that integer can be modified via methods, which allows for the
 * modification of an int in a lambda expression
 * 
 *
 */
public class LambdaInteger implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int num = 0;

	/**
	 * Creates a new {@link LambdaInteger} with a specified value
	 * 
	 * @param num
	 *            starting value
	 **/
	public LambdaInteger(int num) {
		this.num = num;
	}

	/**
	 * Creates a new {@link LambdaInteger} with value of 0
	 */
	public LambdaInteger() {
		this(0);
	}

	/**
	 * @return the current number represented by this {@link LambdaInteger}.
	 * 
	 * @see #setNum(int num)
	 * @see #increment()
	 * @see #add(int num)
	 * @see #reduce()
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set this {@link LambdaInteger}
	 * 
	 * @see #setNum(int num)
	 * @see #increment()
	 * @see #add(int num)
	 * @see #reduce()
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Increments the current number stored by this {@link LambdaInteger} by 1.
	 * <p>
	 * Same as calling {@link #getNum()}++.
	 * 
	 * @see #reduce
	 * @see #getNum()
	 * @see #setNum(int num)
	 */
	public void increment() {
		num++;
	}

	/**
	 * Reduces the current number stored by this {@link LambdaInteger} by 1.
	 * <p>
	 * Same as calling {@link #getNum()}--.
	 * 
	 * @see #increment()
	 * @see #getNum()
	 * @see #setNum(int num)
	 */
	public void reduce() {
		num--;
	}

	/**
	 * Adds a specified integer to the number represented by this
	 * {@link LambdaInteger}.
	 * <p>
	 * Same as {@link #getNum()}+=parameter.
	 * 
	 * @param num
	 *            the number to add
	 * @see #increment()
	 * @see #getNum()
	 * @see #setNum(int num)
	 * @see #reduce()
	 */
	public void add(int num) {
		this.num += num;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LambaInteger [num=" + num + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + num;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LambdaInteger other = (LambdaInteger) obj;
		return num == other.num;
	}
}
