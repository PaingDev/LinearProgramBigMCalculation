package com.cumdy.entity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Business {
	String name;
	double initial;
	float retRate;
	double interest;

	public Business(String name) {
		this(name, 0.0, 0.0f, 0.0);
	}

	public Business(String name, double initial, float retRate, double interest) {
		this.name = name;
		this.initial = initial;
		this.retRate = retRate;
		this.interest = interest;
	}

	public String getName() {
		return name;
	}

	public double getInitial() {
		return initial;
	}

	public float getRetRate() {
		return retRate;
	}

	public double getInterest() {
		return interest;
	}

	public String getStringInitial() {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		df.setDecimalFormatSymbols(symbols);
		df.setMaximumFractionDigits(0);
		df.setMinimumFractionDigits(0);
		df.setRoundingMode(RoundingMode.HALF_UP);

		return df.format(initial);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInitial(double initial) {
		this.initial = initial;
	}

	public void setRetRate(float retRate) {
		this.retRate = retRate;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public String toString() {
		return name;
	}

}
