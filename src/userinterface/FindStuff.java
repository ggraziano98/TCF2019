package userinterface;

import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import models.Track;
import models.TrackList;
import utils.Tools;

public class FindStuff {
	
	
	
	

	public static HBox findBox() {

		// SEARCHBAR per cercare all'interno della libreria
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		MainApp.findText.setMinWidth(Tools.DWIDTHS[0]*0.75);
		Button findButton = new Button();
		findButton.setPrefSize(MainApp.findText.getHeight(), MainApp.findText.getHeight());
	
		Double searchSize = (double) Tools.DHEIGHTS[4]*0.20;
		
		SVGPath search = new SVGPath();
		search.setContent("M 21 3 C 11.601563 3 4 10.601563 4 20 C 4 29.398438 11.601563 37 21 37 C 24.355469 37 27.460938 36.015625 30.09375 34.34375 L 42.375 46.625 L 46.625 42.375 L 34.5 30.28125 C 36.679688 27.421875 38 23.878906 38 20 C 38 10.601563 30.398438 3 21 3 Z M 21 7 C 28.199219 7 34 12.800781 34 20 C 34 27.199219 28.199219 33 21 33 C 13.800781 33 8 27.199219 8 20 C 8 12.800781 13.800781 7 21 7 Z");
		final Region searchShape = PlayerBuilder.iconShape(search, searchSize, PlayerBuilder.black);
		final Region searchShapeTrasp = PlayerBuilder.iconShape(search, searchSize, PlayerBuilder.blackTrasp);
		
		findButton.setGraphic(searchShapeTrasp);
		findButton.setId("searchButton");
		PlayerBuilder.buttonMouseIconRegion(findButton, searchShape, searchShapeTrasp);
		
		
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
		ContextMenus.contextMenuSongs(table);
		MainApp.findView = new VBox(table);
		VBox.setVgrow(table, Priority.ALWAYS);
		((Node) MainApp.mainPanel.getSelectedToggle().getUserData()).setVisible(false);
		
		

		root.add(MainApp.findView, 1, 2, 1, 2); 
	}


}
