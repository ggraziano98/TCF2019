package models;

import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
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
	public TrackList() {
		this.length = new SimpleIntegerProperty(0);
		this.songList = FXCollections.observableArrayList(new ArrayList<TrackID>());
	}
	
	
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
	 * add a track to the end of the TrackList
	 * 
	 * @param track
	 */
	public void addTrack(TrackID track) {
		this.addTrackToPosition(this.getLength().getValue(), track);
	}
	
	
	/**
	 * add a track to the specified position. Will shift all successive tracks to the right
	 * 
	 * @param position
	 * @param track
	 */
	public void addTrackToPosition(int position, TrackID track) {
		try {
			this.songList.add(position, track);
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
	
	public IntegerProperty getLength() {
		return length;
	}


	public void setLength(int length) {
		this.length = new SimpleIntegerProperty(length);
	}


	public ObservableList<TrackID> getSongList() {
		return songList;
	}


	public void setSongList(ObservableList<TrackID> songList) {
		this.songList = songList;
	}
	
}
