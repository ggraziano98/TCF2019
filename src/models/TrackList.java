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
	 * TODO implementare if sull'indice
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
		this.removeTrackToPosition(this.songList.size() - 1);		//ci va il meno uno, altrimenti non mi cancella l'ultimo
	}



	/**
	 * remove a track to the specified position. Will shift all successive tracks to the right
	 * TODO implementare if sull'indice
	 * @param position
	 * 
	 */
	public void removeTrackToPosition(int position) {
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
	 * sposta una canzone dalla posizione position1 a quella position2 (posizione parte da 0)
	 * @param position1
	 * @param position2
	 */
	public void changeOrder(int position1, int position2) {
		Path path1 = songList.get(position1);
		removeTrackToPosition(position1);
		addTrackToPosition(position2, path1);
	}



	/**
	 * mi da' la durata totale della tracklist
	 * 
	 * @return Duration
	 */
	public double totalDuration() {
		Duration totalduration = new Duration(0);
		Track track = new Track();
		for (Path path : songList) {
			track.setPath(path);
			Duration duration = track.getDuration();
			System.out.println(duration);
			totalduration.add(duration);
		}
		return totalduration.toMinutes();

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
	 *  sposta una canzone alla position2 (posizione parte da 0)
	 * @param position2
	 * @param path1
	 */
	public void changeOrder(int position2 ,Path path1) {
		int position1;
		int i=0;
		while (this.songList.get(i)==path1) {
			i++;
		}
		position1 = i - 1;
		removeTrackToPosition(position1);
		addTrackToPosition(position2, path1);
	}



	/**
	 * 
	 * overload di changeOrder
 	 *  sposta una canzone alla position2 (posizione parte da 0)
	 * @param position2
	 * @param track1
	 */
	public void changeOrder(int position2, Track track1) {
		int position1;
		int i=0;
		while (this.songList.get(i)==track1.getPath()) {
			i++;
		}
		position1 = i - 1;
		removeTrackToPosition(position1);
		addTrackToPosition(position2, track1);
	}


}
