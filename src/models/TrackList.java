package models;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;



/**
 * TODO errori
 * TODO vedere se i metodi funzionano
 * Class model for lists of tracks, sostanzialmente una ObservableList ma con qualche funzionalit� aggiuntiva
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
	private String playlistName;
	private int[] loadedData = {0, 0};
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
		this.refreshPositions();
	}


	//metodi principali



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
			this.refreshPositions();
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
			this.refreshPositions();
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
		this.refreshPositions();

		// TODO check
		this.fireValueChangedEvent();
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
		this.refreshPositions();
	}



	/**
	 * mi da' la durata totale della tracklist
	 *
	 * @return DoubleProperty duration in milliseconds (needs to be an observable)
	 */
	public DoubleProperty totalDuration() {
		DoubleProperty totalduration = new SimpleDoubleProperty(0);
		for (Track track : this) {
			track.durationProperty().addListener((obs, oldv, newv) ->{
				totalduration.set(totalduration.get() + track.getDuration().toSeconds());
			});
			track.setMetadata();
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
			this.addTrackToPosition(position, new Track(path));
		}
		catch (Exception e) {
			e.printStackTrace();
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
			this.remove(new Track(path));
			this.refreshPositions();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}




	/**
	 * overload di RemoveTrackToPosition
	 * TODO vedere se funziona
	 *
	 * @param track
	 *
	 */
	public void RemoveTrack(Track track) {
		Alert selection = new Alert(AlertType.CONFIRMATION);
		selection.setTitle("Delete song");
		selection.setHeaderText("Warning");
		selection.setContentText("Sei sicuro di voler eliminare la canzone " + track.getTitle() + " da questa playlist definitivamente");
		Optional<ButtonType> result = selection.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				System.out.println("sto cercando di rimuovere");
				
				this.remove(track);
				
				this.refreshPositions();
			}
			catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("error2");
				alert.setHeaderText("Errore nella rimozione di " + track.getTitle());
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
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


	private void refreshPositions() {
		for (int i = 0; i < this.getSize(); i++) {
			this.get(i).setPosition(i);
		}
	}

	/**
	 * sets metadata for the whole tracklist
	 */
	public void setMetadata() {
		this.setMetadata(0, this.getSize()-1);
	}


	/**
	 *
	 * @param lower
	 * @param upper
	 */
	public void setMetadata(int lower, int upper) {
		lower = Math.min(lower, this.getSize()-1);
		upper = Math.min(upper, this.getSize() -1);
		if(lower != upper) {
			this.subList(lower, upper).forEach(t->{
				t.setMetadata();
			});
		}
	}


	public void unload() {
		this.forEach(t->{
			t.unload();
		});
	}


	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	
	
	
	/**
	 * adds a new object to the end of the tracklist by creating a new refrence
	 *
	 * @param t
	 */
	public void addNew(Track t) {
		addNew(t, this.getSize());
	}


	public void addNew(Track t, int index) {
		Track track = new Track(t.getPath());
		track.setMetadata();
		this.add(index, track);
		this.refreshPositions();
	}

}
