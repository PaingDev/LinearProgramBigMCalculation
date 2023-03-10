package com.cumdy.view;

import javax.swing.JTextField;

public class CustomJTextField extends JTextField {

	public CustomJTextField() {
		super();
	}

	public CustomJTextField(int arg) {
		super(arg);
	}

	public String getText() {
		if (super.getText().length() == 0) {
			return "0";
		}
		return super.getText();
	}

}
