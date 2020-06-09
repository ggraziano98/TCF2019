package models;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import userinterface.MainApp;
import utils.Initialize;
import utils.Tools;



/**
 * 
 * Class model for lists of tracks, sostanzialmente una ObservableList ma con qualche funzionalit� aggiuntiva
 * per associare alle track la loro posizione nella tracklist
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
		super(FXCollections.observableArrayList(songList));
		this.refreshPositions();
	}


	//metodi principali



	/**
	 * add a track to the end of the TrackList
	 *
	 * @param track
	 * @return 
	 */
	@Override
	public void add(int pos, Track track) {
		super.add(pos, track);
		this.refreshPositions();
	}
	
	@Override
	public boolean add(Track track) {
		this.add(this.size(), track);
		return true;
	}
	
	public boolean addAll(TrackList tl) {
		super.addAll(tl);
		this.refreshPositions();
		return true;
	}


	/**
	 * remove a track to the end of the TrackList
	 */
	public Track remove() {
		return this.remove(this.size() - 1);		//ci va il meno uno, altrimenti non mi cancella l'ultimo
	}



	/**
	 * remove a track to the specified position. Will shift all successive tracks to the right
	 *
	 * @param position
	 * @return Track removed
	 *
	 */
	@Override
	public Track remove(int position) {
		Track r = super.remove(position);
		this.refreshPositions();
		return r;
	}



	/**
	 * Ordina a caso le canzoni della tracklist, la track che sta suonando viene messa per prima
	 */
	public void shuffle (Track track) {
		this.refreshPositions();
		this.remove(track.getPosition());
		Collections.shuffle(this);
		this.add(0, track);
		this.refreshPositions();

		this.fireValueChangedEvent();
	}


	/**
	 * sposta una canzone dalla posizione position1 a quella position2 (posizione parte da 0)
	 * @param position1
	 * @param position2
	 */
	public void changeOrder(int position1, int position2) {
		Track track = this.get(position1);
		remove(position1);
		add(position2, track);
		this.refreshPositions();
	}



	/**
	 * mi da' la durata totale della tracklist
	 *
	 * @return Duration duration in milliseconds
	 */
	public Duration totalDuration() {
		double totalduration = 0;
		for (Track track : this) {
			track.setMetadata();
			totalduration += track.getDuration().toSeconds();			
		}
		return Duration.seconds(totalduration);
	}




	//overload dei metodi nel caso servissero



	/**
	 * overload di addTrack
	 *
	 * @param position
	 * @param track
	 */
//	public void addTrack(Path path) {
//		this.addTrack(this.size(), path);
//	}


	/**
	 * overload di addTrackToPosition
	 *
	 * @param position
	 * @param track
	 */
//	public void addTrack(int position, Path path) {
//		try {
//			this.addTrack(position, new Track(path));
//		}
//		catch (Exception e) {
//			//			e.printStackTrace();
//			Tools.stackTrace(e);
//		}
//	}



	/**
	 * overload di RemoveTrackToPosition
	 *
	 * @param path
	 *
	 */
//	public void removeTrack(Path path) {
//		try {
//			this.remove(new Track(path));
//			this.refreshPositions();
//		}
//		catch (Exception e) {
//			//			System.out.println(e.getMessage());
//			Tools.stackTrace(e);
//		}
//	}




	/**
	 * overload di RemoveTrackToPosition
	 *
	 * @param track
	 *
	 */
	public void removeTrack(Track track) {
		Alert selection = new Alert(AlertType.CONFIRMATION);
		selection.setTitle("Delete song");
		selection.setHeaderText("Warning");
		selection.setContentText("Sei sicuro di voler eliminare la canzone " + track.getTitle() + " da questa playlist definitivamente");
		Optional<ButtonType> result = selection.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				this.remove(track);
			}
			catch (Exception e) {
				Tools.stackTrace(e);
			}
		}
	}



	/**
	 * overload di changeOrder
	 *  sposta una canzone alla position2 (posizione parte da 0)
	 * @param position2
	 * @param path1
	 */
//	public void changeOrder(int position2 ,Path path1) {
//		int position1;
//		int i=0;
//		while (this.get(i)==path1) {
//			i++;
//		}
//		position1 = i - 1;
//		removeTrack(position1);
//		addTrack(position2, path1);
//	}



	/**
	 *
	 * overload di changeOrder
	 *  sposta una canzone alla position2 (posizione parte da 0)
	 * @param position2
	 * @param track1
	 */
//	public void changeOrder(int position2, Track track1) {
//		int position1;
//		int i=0;
//		while (this.get(i)==track1.getPath()) {
//			i++;
//		}
//		position1 = i - 1;
//		removeTrack(position1);
//		addTrack(position2, track1);
//	}


	public void refreshPositions() {
		for (int i = 0; i < this.getSize(); i++) {
			this.get(i).setPosition(i);
		}
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


	public String getPlaylistName() {
		return playlistName;
	}


	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

}