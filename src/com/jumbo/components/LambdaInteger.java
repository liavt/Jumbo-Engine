package com.jumbo.components;

/**
 * In a lambda expression, you must use final variables. This class stores an
 * int value, and that integer can be modified via methods, which allows for the
 * modification of an int in a lambda expression
 * 
 * @author Liav
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
	 * @param num starting value
	 **/
	public LambdaInteger(int num) {
		this.num = num;
	}

	/**
	 * Creates a new {@link LamdbaInteger} with value of 0 
	 * */
	public LambdaInteger() {
		this(0);
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	public void increment() {
		num++;
	}

	public void incrementDown() {
		num--;
	}

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
