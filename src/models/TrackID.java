package models;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


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
	protected Path path;
	protected IntegerProperty orderID;
	
	
	/**
	 * Default constructor
	 */	
	public TrackID() {
		this(null);
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param path
	 * @param fileName
	 */
	public TrackID(Path path) {
		this.path = path;
	}
	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public Path getPath() {
		return path;
	}


	public void setPath(Path path) {
		this.path = path;
	}

	
	public IntegerProperty getOrderID() {
		return orderID;
	}


	public void setOrderID(int orderID) {
		this.orderID = new SimpleIntegerProperty(orderID);
	}

}
