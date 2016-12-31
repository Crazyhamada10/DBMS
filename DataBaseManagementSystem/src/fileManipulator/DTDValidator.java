
package fileManipulator;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DTDValidator {
	
	public DTDValidator(){
	}
	
	@SuppressWarnings("unused")
	public void checkforDTD(File F) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		builder.setErrorHandler(new ErrorHandler() {
		    @Override
		    public void error(SAXParseException exception) throws SAXException {
		        // do something more useful in each of these handlers
		        exception.printStackTrace();
		    }
		    @Override
		    public void fatalError(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }

		    @Override
		    public void warning(SAXParseException exception) throws SAXException {
		        exception.printStackTrace();
		    }
		});
		Document document = builder.parse(F);
	}
	
	/*public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException{
		File F = new File("C:\\Users\\abdo\\Desktop\\kiko.xml");
		DTDValidator check = new DTDValidator();
		check.checkXMLforDTD(F);
	}*/
	
}
