package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.util.Duration;
import utils.Tools;


/**
 * Class model for a Track 
 * 
 * 
 * @param title
 * @param artist
 * @param album
 * @param genre
 * @param year
 * @param duration
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
	private BooleanProperty playing;
	private IntegerProperty position;
	private boolean hasMetadata;

	private static final ContentHandler handler = new DefaultHandler();
	private static final Metadata metadata = new Metadata();
	private static final Parser parser = new Mp3Parser();
	private static final ParseContext parseCtx = new ParseContext();


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
		this.setTitle(path.getFileName().toString().replace(".mp3", ""));
		this.setDuration(Duration.seconds(0));
		this.playing = new SimpleBooleanProperty(false);
		this.hasMetadata = false;
	}


	public Track(String[] l) {
		this.setPath(Paths.get(l[6]));
		this.setTitle(l[0]);
		this.setArtist(l[1]);
		this.setAlbum(l[2]);
		this.setGenre(l[3]);
		this.setYear(l[4]);
		this.setDuration(Duration.millis(Double.valueOf(l[5].replace(" ms", ""))));
		this.playing = new SimpleBooleanProperty(false);
		this.hasMetadata = true;
	}


	private void resetProperties() {
		this.album = new SimpleStringProperty(Tools.DALBUM);
		this.artist = new SimpleStringProperty(Tools.DARTIST);
		this.year = new SimpleStringProperty(Tools.DYEAR);
		this.title = new SimpleStringProperty(path.getFileName().toString());
		this.genre = new SimpleStringProperty(Tools.DGENRE);
	}

	/**
	 * sets Track metadata. Does not set image to avoid memory usage 
	 */
	public void setMetadata() {
		if(!this.getHasMetadata()) {
			resetProperties();
			String fileLocation = this.getPath().toString();
			
			try {

				InputStream input = new FileInputStream(new File(fileLocation));
				parser.parse(input, handler, metadata, parseCtx);
				input.close();

				// List all metadata
				String[] metadataNames = metadata.names();

				for(String name : metadataNames){
					handleMetadata(name, metadata.get(name));
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}
			this.hasMetadata = true;
		}
	}


	public void handleMetadata(String key, Object value) {
		if (key.equals("xmpDM:album") ) {
			if(value.toString() == "" || value == null) {
				this.setAlbum(Tools.DALBUM);
			}
			else {
				this.setAlbum(value.toString());
			}
		} else if (key.equals("xmpDM:artist") || key.equals("dc:creator") || key.equals("contributing artists")) {
			if(value.toString() == "" && this.getArtist() == "") this.setArtist(Tools.DARTIST);
			else this.setArtist(value.toString());

		} if (key.equals("title")) {
			if(value.toString() == "") this.setTitle(getPath().getFileName().toString());
			else this.setTitle(value.toString());

		} if (key.equals("year")) {
			if(value.toString() == "") this.setYear(Tools.DYEAR);
			else this.setYear(value.toString());

		} if (key.equals("xmpDM:genre")) {
			if(value.toString() == "") this.setGenre(Tools.DGENRE);
			else this.setGenre(value.toString());
		} if (key.equals("xmpDM:duration")){
			this.setDuration(Duration.millis(Double.valueOf((String) value)));
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
		if(this.title == null) this.title = new SimpleStringProperty();
		return title.get();
	}


	public StringProperty titleProperty() {
		return title;
	}


	public final void setTitle(String title) {
		if(this.title == null) this.title = new SimpleStringProperty(this.getPath().getFileName().toString().replace(".mp3", ""));
		this.title.set(title);
	}


	public final String getArtist() {
		if(this.artist == null) this.artist = new SimpleStringProperty(Tools.DARTIST);
		return artist.get();
	}


	public StringProperty artistProperty() {
		return artist;
	}


	public final void setArtist(String artist) {
		if(this.artist == null) this.artist = new SimpleStringProperty(Tools.DARTIST);
		this.artist.set(artist);
	}


	public final String getAlbum() {
		if(this.album == null) this.album = new SimpleStringProperty(Tools.DALBUM);
		return album.get();
	}


	public StringProperty albumProperty() {
		return album;
	}


	public final void setAlbum(String album) {
		if(this.album == null) this.album = new SimpleStringProperty(Tools.DALBUM);
		this.album.set(album);;
	}


	public final String getGenre() {
		if(this.genre == null) this.genre = new SimpleStringProperty(Tools.DGENRE);
		return genre.get();
	}

	public StringProperty genreProperty() {
		return genre;
	}


	public final void setGenre(String genre) {
		if(this.genre == null) this.genre = new SimpleStringProperty(Tools.DGENRE);
		this.genre.set(genre);
	}


	public final String getYear() {
		if(this.year == null) this.year = new SimpleStringProperty(Tools.DYEAR);
		return year.get();
	}

	public StringProperty yearProperty() {
		return year;
	}


	public final void setYear(String year) {
		if(this.year == null) this.year = new SimpleStringProperty(Tools.DYEAR);
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


	public boolean getHasMetadata() {
		return this.hasMetadata;
	}

	public void setHasMetadata(boolean b) {
		this.hasMetadata = b;
	}

	/**
	 * @return a string formatted to be written in playlists
	 */
	public String getString() {
		return this.getTitle()+"&tcf&"+this.getArtist()+"&tcf&"+this.getAlbum()+"&tcf&"
				+this.getGenre()+"&tcf&"+this.getYear()+"&tcf&"+this.getDuration()+"&tcf&"+
				this.getPath().toString();
	}
	
	
	public void setImageView(ImageView view) {
		view.setImage(Tools.DIMAGE);
		Path path = this.getPath();
		String cleanPathS = Tools.cleanURL(path.toString());
		Media media = new Media(cleanPathS);	
		media.getMetadata().addListener((MapChangeListener<String, Object>) change ->{
			if (change.getKey() == "image") {
				view.setImage((Image) change.getValueAdded());
			}
		});
	}

}