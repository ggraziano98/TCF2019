package userinterface;

import java.util.List;
import java.util.stream.Collectors;

import controllers.PlayerController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;

public class FindStuff {

	public static HBox findBox() {
		
		// SEARCHBAR per cercare all'interno della libreria
				HBox findHBox = new HBox();
				findHBox.setAlignment(Pos.CENTER);
				Root.findText.setMinWidth(220);
				Button findButton = new Button("cerca");

				findHBox.getChildren().add(Root.findText);	
				findHBox.getChildren().add(findButton);
		
				findButton.setOnMouseClicked((e) -> {
					find(Root.findText.getText(), Root.mainPanel, Root.root, Root.pc);
				});
				findHBox.setOnKeyReleased((final KeyEvent KeyEvent) -> {
					if (KeyEvent.getCode() == KeyCode.ENTER) {
						find(Root.findText.getText(), Root.mainPanel, Root.root, Root.pc);
					}
				});
				
				
		return findHBox;
		
	}
	
	public static void find(String keyWord, ToggleGroup mainPanel, GridPane root, PlayerController pc) {	
		// TODO espandere 
		List<Track> list = pc.getTracklist().stream().filter(t->{
			return (
					t.getAlbum().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getArtist().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getTitle().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getGenre().toLowerCase().contains(keyWord.toLowerCase()));
		}).collect(Collectors.toList());

		TrackList filtered = new TrackList();
		list.forEach(t->{
			filtered.add(t);
		});
		mainPanel.getToggles().forEach(panel ->{
			((Node) panel.getUserData()).setVisible(false);
		});

		VBox findView = TrackView.tableFromTracklist(filtered, pc);

		root.add(findView, 1, 1, 1, 2); 
	}

	
}
