package models;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;



/**
 * TODO guardare se cambiare TrackList in modo che estenda SimpleListProperty
 * TODO cambiare Tracklist in List<Track>?
 * TODO errori
 * TODO vedere se i metodi funzionano
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


	/**
	 * Datamember della classe, ObservableList di track
	 */
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


	//metodi principali


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



	/**
	 * Ordina a caso le canzoni della tracklist
	 */
	public void shuffleTrack () {
		Collections.shuffle(songList);
	}



	/**
	 * crea una tracklist vuota
	 * @return tracklist
	 */
	public static TrackList emptyTrackList() {
		return new TrackList();
	}



	/**
	 * switcha due canzoni date le posizioni
	 * 
	 * @param position1
	 * @param position2
	 */
	public void changeOrder(int position1, int position2) {
		Path path1 = songList.get(position1);
		Path path2 = songList.get(position2);
		RemoveTrackToPosition(position1);
		RemoveTrackToPosition(position2);
		addTrackToPosition(position2, path1);
		addTrackToPosition(position1, path2);
	}



	/**
	 * mi da' la durata totale della tracklist
	 * 
	 * @return Duration
	 */
	public Duration totalDuration() {
		Duration totalDuration = new Duration(0);
		for (Path path : songList) {
			Track track = new Track(path);
			Duration duration = track.getDuration();
			totalDuration.add(duration);
		}
		return totalDuration;
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



	//overload dei metodi


	// iniziano gli overload



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



	/**
	 * overload di changeOrder
	 * @param path1
	 * @param path2
	 */
	public void changeOrder(Path path1, Path path2) {
		int position1, position2;
		int i=0, l=0;
		do {
			i++;
		}while (this.songList.get(i)==path1);
		position1=i;
		do {
			l++;
		}while (this.songList.get(l)==path2);
		position2=l;
		RemoveTrackToPosition(position1);
		RemoveTrackToPosition(position2);
		addTrackToPosition(position2, path1);
		addTrackToPosition(position1, path2);
	}



	/**
	 * 
	 * overload di changeOrder
	 * 
	 * @param track1
	 * @param track2
	 */
	public void changeOrder(Track track1, Track track2) {
		int position1, position2;
		int i=0, l=0;
		do {
			i++;
		}while (this.songList.get(i)==track1.getPath());
		position1=i;
		do {
			l++;
		}while (this.songList.get(l)==track2.getPath());
		position2=l;
		RemoveTrackToPosition(position1);
		RemoveTrackToPosition(position2);
		addTrackToPosition(position2, track1);
		addTrackToPosition(position1, track2);
	}


}
