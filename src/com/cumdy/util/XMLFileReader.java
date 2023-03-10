package com.cumdy.util;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLFileReader {

	public static List<LookAndFeelHolder> readXml() {
		List<LookAndFeelHolder> list = new ArrayList<LookAndFeelHolder>();
		XMLDecoder decoder = null;
		try {
			decoder = new XMLDecoder(
					new BufferedInputStream(
							XMLFileReader.class
									.getResourceAsStream("LookAndFeels.xml")));
			while (true) {

				Object obj = decoder.readObject();
				if (obj == null) {
					break;
				} else {
					list.add((LookAndFeelHolder) obj);

				}

			}

		} catch (Exception e) {

		} finally {
			decoder.close();
		}

		return list;
	}

}
