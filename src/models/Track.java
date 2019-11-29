package models;

import java.io.IOError;
import java.nio.file.Path;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
	private MediaPlayer mediaPlayer;
	private BooleanProperty ready;


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
		this.album = Tools.DALBUM;
		this.artist = Tools.DARTIST;
		this.year = Tools.DYEAR;
		this.title = new SimpleStringProperty(path.getFileName().toString());
		this.genre = Tools.DGENRE;
		this.ready = new SimpleBooleanProperty(false);
		this.setPath(path);
		this.setMetadata();
	}



	private void resetProperties() {
		setAlbum(Tools.DALBUM);
		setArtist(Tools.DARTIST);
		setGenre(Tools.DGENRE);
		setYear(Tools.DYEAR);
		setTitle(new SimpleStringProperty(this.getPath().getFileName().toString()));
	}

	/*
	 */
	private void setMetadata() {


		Path path = this.getPath();
		String cleanPathS = Tools.cleanURL(path.toString());

		@SuppressWarnings("unused")
		final JFXPanel fxPanel = new JFXPanel();


		this.resetProperties();
		try {
			final Media media = new Media(cleanPathS);
		    MediaPlayer mediaPlayer = new MediaPlayer(media);
		    this.setMediaPlayer(mediaPlayer);
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
			
		    mediaPlayer.setOnReady(()-> {
		    	this.setDuration(media.getDuration());
		    	this.setReady(true);
		    });

			this.setDuration(media.getDuration());
		} catch (RuntimeException re) {
			re.printStackTrace();
		}
	}


	public void handleMetadata(String key, Object value) {
		if (key.equals("album") ) {
			if(value.toString() == "" || value == null) {
				setAlbum(Tools.DALBUM);
			}
			else {
				setAlbum(new SimpleStringProperty(value.toString()));
			}
		} else if (key.equals("artist")|| key.equals("album artist")) {
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
			System.out.println(path.toString());
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
		this.title.set(title.getValue());
	}


	public StringProperty getArtist() {
		return artist;
	}


	public void setArtist(StringProperty artist) {
		this.artist.set(artist.getValue());
	}


	public StringProperty getAlbum() {
		return album;
	}


	public void setAlbum(StringProperty album) {
		this.album.set(album.getValue());
	}


	public StringProperty getGenre() {
		return genre;
	}


	public void setGenre(StringProperty genre) {
		this.genre.set(genre.getValue());;
	}


	public StringProperty getYear() {
		return year;
	}


	public void setYear(StringProperty year) {
		this.year.set(year.getValue());
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

	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}


	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}


	public BooleanProperty getReady() {
		return ready;
	}


	public void setReady(boolean ready) {
		this.ready.set(ready);
	}



}