package userinterface;

import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RightPanels {
	
	static HBox listsPane = new HBox();
	static FlowPane albumsPane = new FlowPane();
	static FlowPane artistsPane = new FlowPane();
	static VBox songsPane = new VBox();

	public static void panels() throws Exception{
		
		//TODO pane che fanno vedere le canzoni
				FileInputStream gucciniFile = new FileInputStream("files\\mainPane\\guccini.png");
				Image gucciniImage = new Image(gucciniFile);
				ImageView gucciniView = new ImageView(gucciniImage);

				FileInputStream radiciFile = new FileInputStream("files\\mainPane\\radici.png");
				Image radiciImage = new Image(radiciFile);
				ImageView radiciView = new ImageView(radiciImage);


				
				listsPane.setAlignment(Pos.CENTER);

				RadioButton songsButton = new RadioButton("songs");
				songsButton.getStyleClass().remove("radio-button");
				songsButton.getStyleClass().add("toggle-button");
				songsPane.getChildren().add(TrackView.tableFromTracklist(Root.mainTracklist, Root.pc));


				Label songs_artistsLabel = new Label("  ");

				RadioButton artistsButton = new RadioButton("artists");
				artistsButton.getStyleClass().remove("radio-button");
				artistsButton.getStyleClass().add("toggle-button");
				

				artistsPane.getChildren().add(gucciniView);
				artistsPane.setStyle("-fx-base: lightgreen");
				artistsButton.setStyle("-fx-background-color:green");

				Label artists_albumsLabel = new Label("  ");

				RadioButton albumsButton = new RadioButton("albums");
				albumsButton.getStyleClass().remove("radio-button");
				albumsButton.getStyleClass().add("toggle-button");
				

				albumsPane.getChildren().add(radiciView);
				albumsPane.setStyle("-fx-background-color:blue");
				albumsButton.setStyle("-fx-base: lightblue");


				listsPane.getChildren().add(songsButton);
				listsPane.getChildren().add(songs_artistsLabel);
				listsPane.getChildren().add(artistsButton);
				listsPane.getChildren().add(artists_albumsLabel);
				listsPane.getChildren().add(albumsButton);

				albumsButton.setUserData(albumsPane);
				artistsButton.setUserData(artistsPane);
				songsButton.setUserData(songsPane);

				
				songsButton.setToggleGroup(Root.mainPanel);
				artistsButton.setToggleGroup(Root.mainPanel);
				albumsButton.setToggleGroup(Root.mainPanel);

				songsButton.setSelected(true);
				albumsButton.setSelected(false);
				artistsButton.setSelected(false);

				songsPane.setVisible(true);
				albumsPane.setVisible(false);
				artistsPane.setVisible(false);
	}
}
