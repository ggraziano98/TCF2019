package models;

import java.io.IOError;
import java.nio.file.Path;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utils.DataChangeListener;
import utils.Tools;


/**
 * Class model for a Track 
 * 
 * TODO fix playercontroller
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
	private DoubleProperty duration;
	private Image image;
	private BooleanProperty playing;
	private IntegerProperty position;


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
		this.album = new SimpleStringProperty(Tools.DALBUM);
		this.artist = new SimpleStringProperty(Tools.DARTIST);
		this.year = new SimpleStringProperty(Tools.DYEAR);
		this.title = new SimpleStringProperty(path.getFileName().toString());
		this.genre = new SimpleStringProperty(Tools.DGENRE);
		this.playing = new SimpleBooleanProperty(false);
		this.setPath(path);
		this.setMetadata();
	}



	private void resetProperties() {
		setAlbum(Tools.DALBUM);
		setArtist(Tools.DARTIST);
		setGenre(Tools.DGENRE);
		setYear(Tools.DYEAR);
		setTitle(this.getPath().getFileName().toString());
	}

	/*
	 */
	public void setMetadata() {


		Path path = this.getPath();
		String cleanPathS = Tools.cleanURL(path.toString());

		this.resetProperties();
		try {
			Media media = new Media(cleanPathS);				

			DataChangeListener metadataChangeListener = new DataChangeListener(this);

			//QUESTION create listener class? yaaas
			media.getMetadata().addListener(metadataChangeListener);
			
			MediaPlayer mp = new MediaPlayer(media);
			mp.setOnReady(()->{
				this.setDuration(mp.getTotalDuration());
			});

			this.setDuration(media.getDuration());
			
		} catch (RuntimeException re) {
			System.out.println("path non leggibile");
			System.out.println(cleanPathS);
		}
	}


	public void handleMetadata(String key, Object value, Track track) {
		if(track.getPath().equals(this.getPath())) {
			if (key.equals("album") ) {
				if(value.toString() == "" || value == null) {
					this.setAlbum(Tools.DALBUM);
				}
				else {
					this.setAlbum(value.toString());
				}
			} else if (key.equals("artist") || key.equals("album artist") || key.equals("contributing artists")) {
				if(value.toString() == "" && this.getArtist() == "") this.setArtist(Tools.DARTIST);
				else this.setArtist(value.toString());

			} if (key.equals("title")) {
				if(value.toString() == "") this.setTitle(getPath().getFileName().toString());
				else this.setTitle(value.toString());

			} if (key.equals("year")) {
				if(value.toString() == "") this.setYear(Tools.DYEAR);
				else this.setYear(value.toString());

			} if (key.equals("image")) {
				this.setImage((Image) value);

			} if (key.equals("genre")) {
				if(value.toString() == "") this.setGenre(Tools.DGENRE);
				else this.setGenre(value.toString());
			}
		}
	}



	/**
	 * Setter e getter di ogni variabile
	 */

	public final Path getPath() {
		return path;
	}


	public final void setPath(Path path) {
		try {	
			this.path = path.toAbsolutePath();
		}
		catch(IOError e) {
			this.path = path;
			System.out.println("Path non convertito a absolutepath");
		}
	}

	
	
	public final String getTitle() {
		return title.get();
	}


	public StringProperty titleProperty() {
		return title;
	}


	public final void setTitle(String title) {
		this.title.set(title);
	}


	public final String getArtist() {
		return artist.get();
	}


	public StringProperty artistProperty() {
		return artist;
	}


	public final void setArtist(String artist) {
		this.artist.set(artist);
	}


	public final String getAlbum() {
		return album.get();
	}


	public StringProperty albumProperty() {
		return album;
	}


	public final void setAlbum(String album) {
		this.album.set(album);;
	}


	public final String getGenre() {
		return genre.get();
	}

	public StringProperty genreProperty() {
		return genre;
	}


	public final void setGenre(String genre) {
		this.genre.set(genre);
	}


	public final String getYear() {
		return year.get();
	}

	public StringProperty yearProperty() {
		return year;
	}


	public final void setYear(String year) {
		this.year.set(year);
	}


	public final Duration getDuration() {
		return Duration.seconds(duration.get());
	}
	
	
	public DoubleProperty durationProperty() {
		return duration;
	}


	public final void setDuration(Duration duration) {
		if (this.duration == null) this.duration = new SimpleDoubleProperty(0);
		this.duration.set(duration.toSeconds());
	}


	public final Image getImage() {
		return image;
	}


	public final void setImage(Image image) {
		this.image = image;
	}


	public final boolean getPlaying() {
		return playing.get();
	}
	
	public BooleanProperty playingProperty() {
		return playing;
	}


	public final void setPlaying(boolean playing) {
		this.playing.set(playing);
	}


	public final int getPosition() {
		return position.get();
	}


	public final void setPosition(int position) {
		if (this.position == null) this.position = new SimpleIntegerProperty(-1);
		this.position.set(position);;
	}
	
	
	public IntegerProperty positionProperty() {
		return position;
	}

}