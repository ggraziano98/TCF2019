package models;

import java.nio.file.Path;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * TODO guardare se cambiare TrackList in modo che estenda SimpleListProperty
 * 
 * Class model for lists of tracks, sostanzialmente una ObservableList ma con qualche funzionalità aggiuntiva
 * Extended by SongQueue
 * 
 * @param songList		ObservableList of paths to the track 
 * 
 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html">ObservableList</a>
 */
public class TrackList {
	protected ObservableList<Path> songList;
	
	/**
	 * Default constructor for the class
	 */
	public TrackList() {
		this.setSongList(FXCollections.observableArrayList(new ArrayList<Path>()));
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param songList		lista dei path delle canzoni
	 */
	public TrackList(ObservableList<Path> songList) {
		this.setSongList(songList);
	}
	
	
	/**
	 * add a track to the end of the TrackList
	 * 
	 * @param track
	 */
	public void addTrack(Path trackPath) {
		this.addTrackToPosition(this.songList.size(), trackPath);
	}
	
	
	/**
	 * add a track to the specified position. Will shift all successive tracks to the right
	 * 
	 * @param position
	 * @param track
	 */
	public void addTrackToPosition(int position, Path trackPath) {
		try {
			this.songList.add(position, trackPath);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public static TrackList emptyTrackList() {
		return new TrackList();
	}
	
	
	
	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public ObservableList<Path> getSongList() {
		return songList;
	}


	public void setSongList(ObservableList<Path> songList) {
		this.songList = songList;
	}
	
}
