package models;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener.Change;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.util.Duration;

public class Trackprova {

	public Trackprova() {
		// TODO Auto-generated constructor stub
	}
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



	/**
	 * Constructor with initial data
	 * 
	 * @param path			oggetto Path 
	 */
	public Trackprova (Path path) {
		this.setPath(path);
		//this.handleMetadata();
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

	public static void main(String[] args) throws NotFound {
		// TODO Auto-generated method stub
		System.out.println("ciao");


		//		Track t = new Track();
		//		File g= new File("C:\\Users\\Umberto\\Desktop\\song");
		//		
		//		
		//		try {
		////			f = new MP3File(t.getPath().toFile());
		////			FileInputStream f = new FileInputStream("C:\\Users\\Umberto\\Desktop\\li.mp3");
		//			MP3File l  = (MP3File) AudioFileIO.read(g);
		//			MP3AudioHeader audioHeader = (MP3AudioHeader) l.getAudioHeader();
		//			
		//			System.out.println(audioHeader.getTrackLength());
		//			
		//			
		//			Tag tag = l.getTag();
		//			ID3v1Tag v1Tag  = (ID3v1Tag)tag;
		//			
		//			v1Tag.getFirstArtist();
		//			v1Tag.getFirstAlbum();
		//			v1Tag.getFirstTitle();
		//			v1Tag.getFirstComment();
		//			v1Tag.getFirstYear();
		//			v1Tag.getFirstTrack();	
		//		}catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}catch (TagException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (ReadOnlyFileException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (CannotReadException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (InvalidAudioFrameException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}

		//		File g= new File("C:\\Users\\Umberto\\Desktop\\song");
		//		
		//		try {
		//			AudioFile h = AudioFileIO.read(g);
		//			Tag tag = h.getTag();
		//			ID3v1Tag v1Tag  = (ID3v1Tag)tag;
		//			
		//			v1Tag.getFirstArtist();
		//			v1Tag.getFirstAlbum();
		//			v1Tag.getFirstTitle();
		//			v1Tag.getFirstComment();
		//			v1Tag.getFirstYear();
		//			v1Tag.getFirstTrack();	
		//		} catch (CannotReadException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (TagException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (ReadOnlyFileException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (InvalidAudioFrameException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		
		
		/* probabilmente una versione migliore perche usa solo javafx e fra l'altro ci da anche delle osservabili
		 * 
		 */
		
		final JFXPanel fxPanel = new JFXPanel();
		
		String dir = System.getProperty("user.dir");
		Path testSongPath = Paths.get("files\\TestSongs\\Beethoven - The Very Best Of Beethoven (2CD) (naxos 2005) MP3 V0\\CD1\\Egmont Overture.mp3");

		Media m = new Media("file:///" + cleanURL(dir + "\\" + testSongPath.toString()));
		m.getMetadata().addListener((Change<? extends String,? extends Object> ch) -> {
             if (ch.wasAdded()) {
                 System.out.println(ch.getKey() + ": " + ch.getValueAdded());
             }
             
		}
		);
		/* funziona. ma magari si puo fare con javafx?
		 * 
			String songDir = ".\\files\\TestSongs";
			Path testSongPath = Paths.get("files\\TestSongs\\Beethoven - The Very Best Of Beethoven (2CD) (naxos 2005) MP3 V0\\CD1\\Egmont Overture.mp3");
			//			InputStream input = new FileInputStream(new File(fileLocation));
			File file = testSongPath.toFile();
			try {
				InputStream input = new FileInputStream(file);
				org.xml.sax.ContentHandler handler = new DefaultHandler();
				Parser parser = new Mp3Parser();
				Metadata metadata = new Metadata();
				ParseContext parseCtx = new ParseContext();				//non so a cosa serva ma non funziona
				parser.parse(input, handler, metadata, parseCtx);		//non so a cosa serva ma non funziona
				input.close();
	
	
	
				// List all metadata
				String[] metadataNames = metadata.names();
	
				for(String name : metadataNames){
					System.out.println(name + ": " + metadata.get(name));
				}
	
				// Retrieve the necessary info from metadata
				// Names - title, xmpDM:artist etc. - mentioned below may differ based
				System.out.println("----------------------------------------------");
				System.out.println("Title: " + metadata.get("title"));
				System.out.println("Artists: " + metadata.get("xmpDM:artist"));
				System.out.println("Composer : "+metadata.get("xmpDM:composer"));
				System.out.println("Genre : "+metadata.get("xmpDM:genre"));
				System.out.println("Album : "+metadata.get("xmpDM:album"));
	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}
		}

	*/
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


