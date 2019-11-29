package models;

import java.nio.file.Path;
import java.util.Collections;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
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

public class TrackList extends SimpleListProperty<Track> {
	/**
	 * Default constructor for the class
	 */
	public TrackList() {
		super(FXCollections.observableArrayList());
	}


	/**
	 * Constructor with initial data
	 * 
	 * @param songList		lista dei path delle canzoni
	 */
	public TrackList(ObservableList<Track> songList) {
		super(songList);
	}


	//metodi principali


	public int getIndex(Track track) {
		int i=0;
		while (this.get(i)==track.getPath()) {
			i++;
		}
		int position1 = i - 1;
		return position1;
	}



	/**
	 * add a track to the end of the TrackList
	 * 
	 * @param track
	 */
	public void addTrack(Track track) {
		this.addTrackToPosition(this.size(), track);
	}



	/**
	 * add a track to the specified position. Will shift all successive tracks to the right
	 * TODO implementare if sull'indice
	 * @param position
	 * @param track
	 */
	public void addTrackToPosition(int position, Track track) {
		try {
			this.add(position, track);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	/**
	 * remove a track to the end of the TrackList
	 */
	public void removeTrack() {
		this.removeTrackToPosition(this.size() - 1);		//ci va il meno uno, altrimenti non mi cancella l'ultimo
	}



	/**
	 * remove a track to the specified position. Will shift all successive tracks to the right
	 * TODO implementare if sull'indice
	 * @param position
	 * 
	 */
	public void removeTrackToPosition(int position) {
		try {
			this.remove(position);
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}



	/**
	 * Ordina a caso le canzoni della tracklist
	 */
	public void shuffleTrack () {
		Collections.shuffle(this);
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
		Track track = this.get(position1);
		removeTrackToPosition(position1);
		addTrackToPosition(position2, track);
	}



	/**
	 * mi da' la durata totale della tracklist
	 * 
	 * @return DoubleProperty duration in milliseconds (needs to be an observable)
	 */
	public DoubleProperty totalDuration() {
		DoubleProperty totalduration = new SimpleDoubleProperty(0);
		for (Track track : this) {
			track.getReady().addListener((obs, oldv, newv) ->{
				if (newv.booleanValue()) {
					totalduration.set(totalduration.get() + track.getDuration().toMillis());
				}
			});
		}
		return totalduration;
	}




	//overload dei metodi


	// iniziano gli overload



	/**
	 * overload di addTrack
	 * 
	 * @param position
	 * @param track
	 */
	public void addTrack(Path path) {
		this.addTrackToPosition(this.size(), path);
	}


	/**
	 * overload di addTrackToPosition
	 * 
	 * @param position
	 * @param track
	 */
	public void addTrackToPosition(int position, Path path) {
		try {
			this.add(position, new Track(path));
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println(path);
		}
	}



	/**
	 * overload di RemoveTrackToPosition
	 *
	 * @param path
	 * 
	 */
	public void RemoveTrack(Path path) {
		try {
			this.remove(path);
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
	public void RemoveTrack(Track track) {
		try {
			this.remove(track);
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
		while (this.get(i)==path1) {
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
		while (this.get(i)==track1.getPath()) {
			i++;
		}
		position1 = i - 1;
		removeTrackToPosition(position1);
		addTrackToPosition(position2, track1);
	}


}
