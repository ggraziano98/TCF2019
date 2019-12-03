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
import utils.Tools;

public class FindStuff {

	public static HBox findBox() {

		// SEARCHBAR per cercare all'interno della libreria
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		MainApp.findText.setMinWidth(Tools.DWIDTHS[0]*0.75);
		Button findButton = new Button("cerca");

		findHBox.getChildren().add(MainApp.findText);	
		findHBox.getChildren().add(findButton);

		findButton.setOnMouseClicked((e) -> {
			find(MainApp.findText.getText());
		});
		findHBox.setOnKeyReleased((final KeyEvent KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.ENTER) {
				find(MainApp.findText.getText());
			}
		});

		return findHBox;

	}

	public static void find(String keyWord) {	

		GridPane root = MainApp.root;
		PlayerController pc = MainApp.pc;
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
		
		root.getChildren().remove(MainApp.findView);
		MainApp.findView = null;
				
		MainApp.findView = TrackView.tableFromTracklist(filtered, pc);

		root.add(MainApp.findView, 1, 1, 1, 2); 
	}


}
