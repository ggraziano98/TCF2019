package models;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;


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
 *
 * @see <a href="https://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm">Properties</a>
 */
public class Track{
	private Path path;
	private StringProperty title;
	private StringProperty artist;
	private StringProperty album;
	private StringProperty genre;
	private IntegerProperty year;
	private Duration duration;
	private Image image;


	/**
	 * Default constructor
	 */	
	public Track() {
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param path			oggetto Path 
	 */
	public Track(Path path) {
		this.setPath(path);
		this.handleMetadata();
	}
	
	
	public void handleMetadata() {
	}


	
	
	
	
	
	/**
	 * Setter e getter di ogni variabile
	 */
	
	public Path getPath() {
		return path;
	}
	
	
	public void setPath(Path path) {
		this.path = path;
	}
	
	public StringProperty getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = new SimpleStringProperty(title);
	}


	public StringProperty getArtist() {
		return artist;
	}


	public void setArtist(String artist) {
		this.artist = new SimpleStringProperty(artist);
	}


	public StringProperty getAlbum() {
		return album;
	}


	public void setAlbum(String album) {
		this.album = new SimpleStringProperty(album);
	}


	public StringProperty getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = new SimpleStringProperty(genre);
	}


	public IntegerProperty getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = new SimpleIntegerProperty(year);
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
}