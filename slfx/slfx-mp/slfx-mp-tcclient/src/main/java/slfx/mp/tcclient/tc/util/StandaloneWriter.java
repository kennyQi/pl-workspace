package slfx.mp.tcclient.tc.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class StandaloneWriter extends XMLWriter {
	
	protected void writeDeclaration() throws IOException {
		OutputFormat format = getOutputFormat();
		String encoding = format.getEncoding();
		if (!format.isSuppressDeclaration()) {
			writer.write("<?xml version='1.0'");
			if (!format.isOmitEncoding()) {
				if (encoding.equals("utf-8"))
					writer.write(" encoding='utf-8'");
				else
					writer.write(" encoding='" + encoding + "'");
			}
			writer.write(" standalone='yes'");
			writer.write("?>");
			if (format.isNewLineAfterDeclaration()) { println(); }
		}
	}

	public StandaloneWriter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StandaloneWriter(OutputFormat format)
			throws UnsupportedEncodingException {
		super(format);
		// TODO Auto-generated constructor stub
	}

	public StandaloneWriter(OutputStream out, OutputFormat format)
			throws UnsupportedEncodingException {
		super(out, format);
		// TODO Auto-generated constructor stub
	}

	public StandaloneWriter(OutputStream out)
			throws UnsupportedEncodingException {
		super(out);
		// TODO Auto-generated constructor stub
	}

	public StandaloneWriter(Writer writer, OutputFormat format) {
		super(writer, format);
		// TODO Auto-generated constructor stub
	}

	public StandaloneWriter(Writer writer) {
		super(writer);
		// TODO Auto-generated constructor stub
	}
}