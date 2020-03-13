package userinterface;

import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;
import utils.Tools;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class FindStuff {
	
	public static FontAwesomeIconView searchButtonIcon = new FontAwesomeIconView();
	
	

	public static HBox findBox() {

		// SEARCHBAR per cercare all'interno della libreria
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		MainApp.findText.setMinWidth(Tools.DWIDTHS[0]*0.75);
		Button findButton = new Button();
		searchButtonIcon.setIcon(FontAwesomeIcon.SEARCH);
		searchButtonIcon.setStyleClass("cticon");
		findButton.setGraphic(searchButtonIcon);
		
		
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
		//TODO espandere se si fa ArtistsPane
		List<Track> list = MainApp.allSongs.stream().filter(t->{
			return (
					t.getAlbum().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getArtist().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getTitle().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getGenre().toLowerCase().contains(keyWord.toLowerCase()));
		}).collect(Collectors.toList());

		TrackList filtered = new TrackList();
		filtered.addAll(list);
		
		root.getChildren().remove(MainApp.findView);
		MainApp.findView = null;
				
		TableView<Track> table = TrackView.tableFromTracklist(filtered, MainApp.pc);
		MainApp.findView = new VBox(table);
		VBox.setVgrow(table, Priority.ALWAYS);

		root.add(MainApp.findView, 1, 2, 1, 2); 
	}


}
