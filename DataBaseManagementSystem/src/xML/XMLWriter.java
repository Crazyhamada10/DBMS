package xML;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fileManipulator.IFileWriter;
import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;
import accessories.TableValues;

public class XMLWriter implements IFileWriter {

	private static XMLWriter instance;

	private XMLWriter() {

	}

	public static XMLWriter getInstance() {
		if (instance == null) {
			instance = new XMLWriter();	
		}
		return instance;
	}
	
	@Override
	public void write(TableValues data, File fileToUpdate) throws SQLException {
		int numCols = data.getNumCols();
		int numRows = data.getNumRows();
		ArrayList<String> colNames = data.getcolNames();
		ArrayList<Integer> colTypes = data.getColTypes();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			SQLExceptions.canNotParseFile();
		}
		Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(data.getTableName());
		rootElement.setAttribute(AttributeWords.NumberOfColumns.name(), String.valueOf(numCols));
		rootElement.setAttribute(AttributeWords.NumberOfRows.name(), String.valueOf(numRows));
		for (int i = 0; i < numCols; i++) {
			rootElement.setAttribute(AttributeWords.Column.name() + (i + 1), colNames.get(i));
		}
		for (int i = 0; i < numCols; i++) {
			rootElement.setAttribute(AttributeWords.Column.name() + (i + 1) + AttributeWords.Type.name(), colTypes.get(i).toString());
		}
		for (int i = 0; i < numRows; i++) {
			Element row = document.createElement(AttributeWords.Rows.name());
			row.setAttribute(AttributeWords.RowNumber.name(), String.valueOf(i));
			for (int j = 0; j < numCols; j++) {
				Element field = document.createElement(colNames.get(j));
				if (data.getValue(i, j) == null) {
					field.appendChild(document.createTextNode(AttributeWords.Null.name()));
				} else {
					field.appendChild(document.createTextNode(data.getValue(i, j).toString()));					
				}
				row.appendChild((Element) field);
			}
			rootElement.appendChild(row);
		}
		document.appendChild(rootElement);
		transform(fileToUpdate, document, data.getTableName());
	}

	private void transform(File fileToUpdate, Document document, String tableName) throws SQLException {
		DOMSource domSource = new DOMSource(((Document) document));
		StreamResult streamSource = new StreamResult(fileToUpdate);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			SQLExceptions.canNotParseFile();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, AttributeWords.yes.name());
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, tableName + ".dtd");
		try {
			transformer.transform(domSource, streamSource);
		} catch (TransformerException e) {
			SQLExceptions.canNotParseFile();
		}
	}
}
