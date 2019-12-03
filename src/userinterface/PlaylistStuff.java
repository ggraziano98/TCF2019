package userinterface;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import models.TrackList;
import utils.Tools;

public class PlaylistStuff {
	
	public static ScrollPane playlist() {
		
		VBox playlistsVbox = new VBox();
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(playlistsVbox);
		
		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		savedPlaylists.forEach((String name)->{
			TrackList tracklist = Tools.readPlaylist(name);
			VBox table = TrackView.tableFromTracklist(tracklist, Root.pc);
			Root.root.add(table, 1, 1, 1, 2);
			playlists(name, playlistsVbox, Root.mainPanel, table);
			
		});
		
		return scroll;
	}

	public static void playlists(String string, VBox box, ToggleGroup mainPanel, Node dataPane) {
		RadioButton playlistButton = new RadioButton(string);
		playlistButton.getStyleClass().remove("radio-button");
		playlistButton.getStyleClass().add("toggle-button");
		playlistButton.setMinWidth(280);
		playlistButton.setMaxWidth(280);
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
