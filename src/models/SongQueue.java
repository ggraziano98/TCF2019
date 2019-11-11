package models;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;


/*
 * TODO implement functions to control queue:
 * 		move song to position (shift everything down)
 * 		move song to top
 * 		remove song
 * 		add song 
 *  
 */

/**
 * Class model for SongQueues, extends TrackList to add a currentSongID and a few functions
 * 
 * @param songList
 * @param currentSongID
 */
public class SongQueue extends TrackList{
	private IntegerProperty currentSongID;
	
	
	/**
	 * Default constructor for the class
	 */
	public SongQueue() {
		super();
	}
	
	
	/**
	 * Constructor with initial data
	 * 
	 * @param songList
	 * @param curentSongID
	 */
	public SongQueue(ObservableList<Path> songList, int currentSongID) {
		super(songList);
		this.setCurrentSongID(currentSongID);
	}

	
	
	/**
	 * setters and getters for class parameters
	 */
	
	public IntegerProperty getCurrentSongID() {
		return this.currentSongID;
	}
	
	
	public void setCurrentSongID(int currentSongID) {
		this.currentSongID = new SimpleIntegerProperty(currentSongID);
	}
	
	public void foo() {}
}
