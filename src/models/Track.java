package models;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
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
	private StringProperty year;
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
//		this.setMetadata(path);
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


	public StringProperty getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = new SimpleStringProperty(year);
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
	
	
	private void resetProperties() {
	    setArtist("");
	    setAlbum("");
	    setTitle("");
	    setYear("");
	    setImage(image);
	  }
	
	final JFXPanel fxPanel = new JFXPanel();
	
	String dir = System.getProperty("user.dir");

	Path testSongPath = Paths.get("files\\TestSongs\\Beethoven - The Very Best Of Beethoven (2CD) (naxos 2005) MP3 V0\\CD1\\Egmont Overture.mp3");
	
	 private void setMetadata (Path path) {
		    
		 
//		 String url = cleanURL(urlnotclean);
		 resetProperties();
		    try {
		      final Media media = new Media(path.toUri().toString());
		      media.getMetadata().addListener(new MapChangeListener<String, Object>() {
		        @Override
		        public void onChanged(Change<? extends String, ? extends Object> ch) {
		          if (ch.wasAdded()) {
		            handleMetadata(ch.getKey(), ch.getValueAdded());
		          }
		        }
		      });
		    } catch (RuntimeException re) {
		      //TODO error message
		      System.out.println("Caught Exception: " + re.getMessage());
		    }
		  }
         
         
         public void handleMetadata(String key, Object value) {
        	    if (key.equals("album")) {
        	      setAlbum(value.toString());
        	    } else if (key.equals("artist")) {
        	      setArtist(value.toString());
        	    } if (key.equals("title")) {
        	      setTitle(value.toString());
        	    } if (key.equals("year")) {
        	      setYear(value.toString());
        	    } if (key.equals("image")) {
        	      setImage((Image)value);
        	    }
        	  }
        	
         
         /**

          * Dobbiamo pulire l'url altrimenti javafx non lo riconosce
          *
          * @param uri
          */
         private static String cleanURL(String url) {
             url = url.replace("\\", "/");
             url = url.replaceAll(" ", "%20");
             url = url.replace("[", "%5B");
             url = url.replace("]", "%5D");
             return url;

         }
	

}