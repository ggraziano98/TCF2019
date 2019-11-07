package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;


/*
 * TODO find duration and image classes
 */


/**
 * Class model for a Track 
 * 
 * @param title
 * @param artist
 * @param album
 * @param genre
 * @param year
 * @param duration
 * @param image
 * @param path
 * @param fileName
 * 
 */
public class Track {
	private StringProperty title;
	private StringProperty artist;
	private StringProperty album;
	private StringProperty genre;
	private IntegerProperty year;
	private Duration duration;
	private Image image;
	private StringProperty path;
	private StringProperty fileName;

	
	/**
	 * Default constructor
	 */	
	public Track() {
		this(null, null);
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param path
	 * @param fileName
	 */
	public Track(String path, String fileName) {
		this.path = new SimpleStringProperty(path);
		this.fileName = new SimpleStringProperty(fileName);
	}
	
	
}























