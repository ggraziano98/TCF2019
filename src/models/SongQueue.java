package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;


/*
 * TODO implement functions to control queue:
 * 	
 *  
 */

/**
 * Class model for SongQueues, extends TrackList to add a currentSongID and a few functions
 * 
 * @param length
 * @param songList
 * @param currentSongID
 */
public class SongQueue extends TrackList{
	private IntegerProperty currentSongID;
	
	
	/**
	 * Default constructor for the class
	 */
	public SongQueue() {}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param length
	 * @param songList
	 * @param curentSongID
	 */
	public SongQueue(int length, ObservableList<TrackID> songList, int currentSongID) {
		this.length = new SimpleIntegerProperty(length);
		this.songList = songList;
		this.currentSongID = new SimpleIntegerProperty(currentSongID);
	}

	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public IntegerProperty getCurrentSongID() {
		return this.currentSongID;
	}
	
	
	void setCurrentSongID(int currentSongID) {
		this.currentSongID = new SimpleIntegerProperty(currentSongID);
	}
	
	
}
