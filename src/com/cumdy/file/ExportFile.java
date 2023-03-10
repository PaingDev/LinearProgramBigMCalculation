package com.cumdy.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ExportFile {

	String path = System.getProperty("user.dir");

	public ExportFile(String value) {
		save(value);

	}

	public void save(String value) {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Export HTML File");
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File file) {
				if (file.getName().endsWith(".html") || file.isDirectory()) {
					return true;
				} else
					return false;
			}

			public String getDescription() {
				return "HTML File";
			}
		});
		chooser.setCurrentDirectory(new File(path));

		int option = chooser.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = null;
			if ((file = chooser.getSelectedFile()) != null) {
				if (!file.getName().endsWith(".html"))
					file = new File(file.getAbsolutePath() + ".html");
				try {
					writeFile(file, value);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			path = file.getPath();
		}
	}

	public void writeFile(File file, String value) throws IOException {
		OutputStreamWriter out;
		BufferedWriter writer;

		out = new OutputStreamWriter(new FileOutputStream(file),
				Charset.forName("UTF-8"));
		writer = new BufferedWriter(out);
		writer.write(value);

		writer.close();
		out.close();

	}

}
