package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;


/**
 * Class model for lists of tracks
 * Extended by Playlist and SongQueue
 * 
 * @param length
 * @param songList		ObservableList containing songs 
 * 
 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html">ObservableList</a>
 */
public class TrackList {
	protected IntegerProperty length;
	protected ObservableList<TrackID> songList;
	
	/**
	 * Default constructor for the class
	 */
	public TrackList() {}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param length
	 * @param songList
	 */
	public TrackList(int length, ObservableList<TrackID> songList) {
		this.length = new SimpleIntegerProperty(length);
		this.songList = songList;
	}

	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public IntegerProperty getLength() {
		return length;
	}


	public void setLength(IntegerProperty length) {
		this.length = length;
	}


	public ObservableList<TrackID> getSongList() {
		return songList;
	}


	public void setSongList(ObservableList<TrackID> songList) {
		this.songList = songList;
	}
	
}
