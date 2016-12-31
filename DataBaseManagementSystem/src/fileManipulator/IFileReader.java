package fileManipulator;

import java.io.File;
import java.sql.SQLException;

import accessories.TableValues;

public interface IFileReader {
	TableValues read(File source) throws SQLException;
}
