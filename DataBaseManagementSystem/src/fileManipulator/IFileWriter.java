package fileManipulator;

import java.io.File;
import java.sql.SQLException;

import accessories.TableValues;

public interface IFileWriter {

	void write(TableValues data, File fileToUpdate) throws SQLException;
	
	
}
