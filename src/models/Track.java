package models;

import java.io.IOError;
import java.nio.file.Path;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utils.Tools;


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
	private StringProperty year;
	private Duration duration;
	private Image image;
	private Media media;


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
		this.setMetadata();

	}



	private void resetProperties() {
		setArtist(new SimpleStringProperty(""));
		setAlbum(new SimpleStringProperty(""));
		setTitle(new SimpleStringProperty(""));
		setYear(new SimpleStringProperty(""));
	}

	/*
	 * TODO fixare il problema
	 * dato che Media.getMetadata() funziona in modo asincrono, non va bene. Probabilmente bisogna usare un'altra libreria
	 */
	private void setMetadata() {


		Path path = this.getPath();
		String cleanPathS = Tools.cleanURL(path.toString());

		//TODO togliere questo?
		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel();


		this.resetProperties();
		try {
			final Media media = new Media(cleanPathS);		
			media.getMetadata().addListener(new MapChangeListener<String, Object>() {
						@Override
						public void onChanged(Change<? extends String, ? extends Object> ch) {
							if (ch.wasAdded()) {
								if(ch.getKey().toString() == "artist") {
								}
								handleMetadata(ch.getKey(), ch.getValueAdded());
							}
						}
			});

			this.setMedia(media);
			this.setDuration(media.getDuration());
		} catch (RuntimeException re) {
			//TODO error message
			re.printStackTrace();
		}

		if (this.getAlbum().getValueSafe() == "") {
			setAlbum(Tools.DALBUM);
		}
		if (this.getArtist().getValueSafe() == "") {
			setArtist(Tools.DARTIST);
		}
		if (this.getGenre() == null || this.getGenre().getValueSafe() == "") {
			setGenre(Tools.DGENRE);
		}
		if (this.getYear().getValueSafe() == "") {
			setYear(Tools.DYEAR);
		}
		if (this.getTitle().getValueSafe() == "") {
			setTitle(new SimpleStringProperty(this.getPath().getFileName().toString()));
		}
	}


	public void handleMetadata(String key, Object value) {
		if (key.equals("album") || key.equals("album artist")) {
			if(value.toString() == "" || value == null) {
				setAlbum(Tools.DALBUM);
			}
			else {
				setAlbum(new SimpleStringProperty(value.toString()));
			}
		} else if (key.equals("artist")) {
			if(value.toString() == "" && this.getArtist().getValueSafe() == "") setArtist(Tools.DARTIST);
			else setArtist(new SimpleStringProperty(value.toString()));

		} if (key.equals("title")) {
			if(value.toString() == "") setTitle(new SimpleStringProperty(this.getPath().getFileName().toString()));
			else setTitle(new SimpleStringProperty(value.toString()));

		} if (key.equals("year")) {
			if(value.toString() == "") setYear(Tools.DYEAR);
			else setYear(new SimpleStringProperty(value.toString()));

		} if (key.equals("image")) {
			setImage((Image) value);

		} if (key.equals("genre")) {
			if(value.toString() == "") setGenre(Tools.DGENRE);
			else setGenre(new SimpleStringProperty(value.toString()));
		}
	}









	/**
	 * Setter e getter di ogni variabile
	 */

	public Path getPath() {
		return path;
	}


	public void setPath(Path path) {
		try {	
			this.path = path.toAbsolutePath();
		}
		catch(IOError e) {
			this.path = path;
			System.out.println("Path non convertito a absolutepath");
		}
	}

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


	public StringProperty getYear() {
		return year;
	}


	public void setYear(StringProperty year) {
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

	public Media getMedia() {
		return media;
	}


	public void setMedia(Media media) {
		this.media = media;
	}



}