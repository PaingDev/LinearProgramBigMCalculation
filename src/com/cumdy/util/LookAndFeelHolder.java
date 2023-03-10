package com.cumdy.util;

public class LookAndFeelHolder {
	String name = new String();
	String className = new String();

	public LookAndFeelHolder() {
	}

	public LookAndFeelHolder(String n, String cN) {
		name = n;
		className = cN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return name;
	}
}
