package userinterface;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;
import utils.Tools;

public class PlaylistStuff {
	
	
	/**
	 * Usato per definire lo scrollpane a sinistra in cui si vedono tutte le playlist salvate
	 * 
	 * @return ScrollPane playlist
	 */
	public static ScrollPane playlist() {
		
		VBox playlistsVbox = new VBox();
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(playlistsVbox);
		
		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		//TODO reload tracklist instead of setting visible(true)??
		savedPlaylists.forEach((String name)->{
			TrackList tracklist = Tools.readPlaylist(name);
			
			TableView<Track> table = TrackView.tableFromTracklist(tracklist, MainApp.pc);
			VBox tableBox = new VBox(table);
			VBox.setVgrow(table, Priority.ALWAYS);
			MainApp.root.add(tableBox, 1, 1, 1, 2);
			playlistButton(name, playlistsVbox, MainApp.mainPanel, tableBox);
			
		});
		
		return scroll;
	}
	
	/**
	 * Funzione che crea i bottoni da aggiungere allo scrollpane delle playlist
	 * 
	 * @param string
	 * @param box
	 * @param mainPanel
	 * @param dataPane
	 */
	private static void playlistButton(String string, VBox box, ToggleGroup mainPanel, Node dataPane) {
		RadioButton playlistButton = new RadioButton(string);
		playlistButton.getStyleClass().remove("radio-button");
		playlistButton.getStyleClass().add("toggle-button");
		
		double width = Tools.DWIDTHS[0]*0.95;
		playlistButton.setMinWidth(width);
		playlistButton.setMaxWidth(width);
		playlistButton.setAlignment(Pos.CENTER_LEFT);
		playlistButton.setToggleGroup(mainPanel);
		playlistButton.setStyle(Tools.TRANSBUTT);
		playlistButton.setOnMouseMoved((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.BOLDBUTT);
		});
		playlistButton.setOnMouseExited((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		playlistButton.selectedProperty().addListener((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		box.getChildren().add(playlistButton);

		dataPane.setVisible(false);
		playlistButton.setUserData(dataPane);;

	}

}
