package xML;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import accessories.SQLExceptions;
import accessories.TableValues;
import accessories.StaticData.AttributeWords;
import dataTypesController.DataTypeController;
import fileManipulator.IFileReader;

public class XMLReader implements IFileReader{

	private static XMLReader instance;
	
	private XMLReader(){
		
	}
	
	public static XMLReader getInstance(){
		if(instance == null) {
			instance =  new XMLReader();
		}
		return instance;
	}
	
	@Override
	public TableValues read(File source) throws SQLException{
		Document document = null;
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory
					.newDocumentBuilder();
			document = documentBuilder.parse(source.getPath());
		} catch (ParserConfigurationException e) {
			SQLExceptions.canNotReadFile();
		} catch (SAXException e) {
			SQLExceptions.canNotReadFile();
		} catch (IOException e) {
			SQLExceptions.canNotReadFile();
		}
		return transform(document);
	}

	private TableValues transform(Document data) throws SQLException {
		Element rootElement = ((Document) data).getDocumentElement();
		String tableName = rootElement.getTagName();
		int numCols = Integer.parseInt(rootElement.getAttribute(AttributeWords.NumberOfColumns.name()));
		ArrayList<String> colNames = new ArrayList<String>(getColNames(numCols, rootElement));
		ArrayList<Integer> colTypes = new ArrayList<Integer>(getColType(numCols, rootElement));
		ArrayList<ArrayList<Object>> raws = new ArrayList<>();
		NodeList nodeList = ((Document) data).getElementsByTagName(AttributeWords.Rows.name());
		int numRows = nodeList.getLength();
		for (int i = 0; i < numRows; i++) {
			Element raw = (org.w3c.dom.Element) nodeList.item(i);
			ArrayList<Object> rawItems = new ArrayList<>();
			for (int j = 0; j < numCols; j++) {
				String stringData = raw.getElementsByTagName(colNames.get(j)).item(0).getTextContent();
				Integer type = colTypes.get(j);
				rawItems.add(DataTypeController.convertData(type, stringData));
			}
			raws.add(rawItems);
		}
		return new TableValues(tableName, colTypes, colNames, raws, numCols, numRows);
	}

	private ArrayList<String> getColNames(int nomOfCols, Element rootElement) {
		ArrayList<String> colNames = new ArrayList<>();
		for (int i = 0; i < nomOfCols; i++) {
			colNames.add(rootElement.getAttribute(AttributeWords.Column.name() + (i + 1)));
		}
		return colNames;
	}

	private ArrayList<Integer> getColType(int nomOfCols, Element rootElement) {
		ArrayList<Integer> colTypes = new ArrayList<>();
		for (int i = 0; i < nomOfCols; i++) {
			int type = Integer.valueOf(rootElement.getAttribute(AttributeWords.Column.name() + (i + 1) + AttributeWords.Type.name()));
			colTypes.add(type);
		}
		return colTypes;
	}
	
}
