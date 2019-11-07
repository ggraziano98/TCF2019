package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Class model for TrackID, an object that identifies the track in the folder and its order in a tracklist, if any
 * 
 * @param path			string with file path to folder
 * @param fileName		string with file name
 * @param orderID		sets the position in queue, if any
 * 
 * @see <a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm">Properties</a>
 */
public class TrackID {
	protected StringProperty path;
	protected StringProperty fileName;
	protected IntegerProperty orderID;
	
	
	/**
	 * Default constructor
	 */	
	public TrackID() {
		this(null, null);
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param path
	 * @param fileName
	 */
	public TrackID(String path, String fileName) {
		this.path = new SimpleStringProperty(path);
		this.fileName = new SimpleStringProperty(fileName);
	}
	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public StringProperty getPath() {
		return path;
	}


	public void setPath(StringProperty path) {
		this.path = path;
	}


	public StringProperty getFileName() {
		return fileName;
	}


	public void setFileName(StringProperty fileName) {
		this.fileName = fileName;
	}
	
	public IntegerProperty getOrderID() {
		return orderID;
	}


	public void setOrderID(IntegerProperty orderID) {
		this.orderID = orderID;
	}

}
