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
	
	
	/**
	 * Setter e getter di ogni variabile
	 */
	
	public StringProperty getTitle() {
		return title;
	}


	public void setTitle(StringProperty title) {
		this.title = title;
	}


	public StringProperty getArtist() {
		return artist;
	}


	public void setArtist(StringProperty artist) {
		this.artist = artist;
	}


	public StringProperty getAlbum() {
		return album;
	}


	public void setAlbum(StringProperty album) {
		this.album = album;
	}


	public StringProperty getGenre() {
		return genre;
	}


	public void setGenre(StringProperty genre) {
		this.genre = genre;
	}


	public IntegerProperty getYear() {
		return year;
	}


	public void setYear(IntegerProperty year) {
		this.year = year;
	}


	public Duration getDuration() {
		return duration;
	}


	public void setDuration(Duration duration) {
		this.duration = duration;
	}


	public Image getImage() {
		return image;
	}


	public void setImage(Image image) {
		this.image = image;
	}


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
	
}
