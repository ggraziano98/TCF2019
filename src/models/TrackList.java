package models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * TODO guardare se cambiare TrackList in modo che estenda SimpleListProperty
 * TODO cambiare Tracklist in List<Track>?
 * TODO errori
 * Class model for lists of tracks, sostanzialmente una ObservableList ma con qualche funzionalità aggiuntiva
 * Extended by SongQueue
 * 
 * @param songList		ObservableList of paths to the track 
 * 
 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html">ObservableList</a>
 */
/**
 * @author Umberto
 *
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
	 * @param path
	 */
	public void addTrackToPosition(int position, Path trackPath) {
		try {
			this.songList.add(position, trackPath);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
		
	/**
	 * remove a track to the end of the TrackList
	 * 
	 * @param trackpath
	 */
	public void removeTrack() {
		this.RemoveTrackToPosition(this.songList.size());
	}


	/**
	 * remove a track to the specified position. Will shift all successive tracks to the right
	 * 
	 * @param position
	 * 
	 */
	public void RemoveTrackToPosition(int position) {
		try {
			this.songList.remove(position);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void shuffleTrack () {
		Collections.shuffle(songList);
		
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
	
	
	
	/**
	 * overload di addTrack
	 * 
	 * @param position
	 * @param track
	 */
	
	public void addTrack(Track track) {
		this.addTrackToPosition(this.songList.size(), track.getPath());
	}

	/**
	 * overload di addTrackToPosition
	 * 
	 * @param position
	 * @param track
	 */
	public void addTrackToPosition(int position, Track track) {
		try {
			this.songList.add(position, track.getPath());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * overload di RemoveTrackToPosition
	 *
	 * @param path
	 * 
	 */
	public void RemoveTrackToPosition(Path path) {
		try {
			this.songList.remove(path);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * overload di RemoveTrackToPosition
	 *
	 * @param track
	 * 
	 */
	public void RemoveTrackToPosition(Track track) {
		try {
			this.songList.remove(track.getPath());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
}
