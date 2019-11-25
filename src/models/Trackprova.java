package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ContentHandler;
import java.nio.file.Path;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tools.ant.taskdefs.Input;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.healthmarketscience.jackcess.impl.expr.Expressionator.ParseContext;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
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
		String fileLocation = "C:\\Users\\Umberto\\Desktop\\song";
		try {
//			InputStream input = new FileInputStream(new File(fileLocation));
			InputStream input = new FileInputStream("G:\\Music\\acdc\\T.N.T. acdc");
	        org.xml.sax.ContentHandler handler = new DefaultHandler();
	        Metadata metadata = new Metadata();
//	        Parser parser = new Mp3Parser();
//	        ParseContext parseCtx = new ParseContext();				//non so a cosa serva ma non funziona
//	        parser.parse(input, handler, metadata, parseCtx);		//non so a cosa serva ma non funziona
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
//	        catch (SAXException e) {
//	        e.printStackTrace();
//	        } catch (TikaException e) {
//	        e.printStackTrace();
//	        }
	        }
			
			
		
	}


