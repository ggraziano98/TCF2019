package userinterface;

import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;

public class RightPanels {

	static HBox buttonBox = new HBox();
	static FlowPane albumsPane = new FlowPane();
	static FlowPane artistsPane = new FlowPane();
	static VBox songsPane = new VBox();


	public static void panels() throws Exception{

		//TODO make buttons into function? Would be cleaner 
		setArtistsPane();
		setAlbumsPane();

		buttonBox.setAlignment(Pos.CENTER);

		RadioButton songsButton = new RadioButton("songs");
		songsButton.getStyleClass().remove("radio-button");
		songsButton.getStyleClass().add("toggle-button");
		
		TableView<Track> table = TrackView.tableFromTracklist(MainApp.allSongs, MainApp.pc);
		
		songsPane.getChildren().add(table);
		Pannelli.contextMenuTrack(table, MainApp.allSongs);
		VBox.setVgrow(table, Priority.ALWAYS);

		Label songs_artistsLabel = new Label("  ");

		RadioButton artistsButton = new RadioButton("artists");
		artistsButton.getStyleClass().remove("radio-button");
		artistsButton.getStyleClass().add("toggle-button");
		artistsButton.setStyle("-fx-background-color:green");

		Label artists_albumsLabel = new Label("  ");

		RadioButton albumsButton = new RadioButton("albums");
		albumsButton.getStyleClass().remove("radio-button");
		albumsButton.getStyleClass().add("toggle-button");
		albumsButton.setStyle("-fx-base: lightblue");
		
		Label albums_queueLabel = new Label("  ");
		
		RadioButton queueButton = new RadioButton("Song Queue");
		queueButton.getStyleClass().remove("radio-button");
		queueButton.getStyleClass().add("toggle-button");


		buttonBox.getChildren().add(songsButton);
		buttonBox.getChildren().add(songs_artistsLabel);
		buttonBox.getChildren().add(artistsButton);
		buttonBox.getChildren().add(artists_albumsLabel);
		buttonBox.getChildren().add(albumsButton);
		buttonBox.getChildren().add(albums_queueLabel);
		buttonBox.getChildren().add(queueButton);
		
		MainApp.songQueueView = SongQueueView.songQueueView();


		albumsButton.setUserData(albumsPane);
		artistsButton.setUserData(artistsPane);
		songsButton.setUserData(songsPane);
		queueButton.setUserData(MainApp.songQueueView);


		songsButton.setToggleGroup(MainApp.mainPanel);
		artistsButton.setToggleGroup(MainApp.mainPanel);
		albumsButton.setToggleGroup(MainApp.mainPanel);
		queueButton.setToggleGroup(MainApp.mainPanel);

		songsButton.setSelected(true);
		albumsButton.setSelected(false);
		artistsButton.setSelected(false);
		queueButton.setSelected(false);

		songsPane.setVisible(true);
		albumsPane.setVisible(false);
		artistsPane.setVisible(false);
		MainApp.songQueueView.setVisible(false);
	}


	public static void setArtistsPane(){

		//TODO implement
		ImageView gucciniView = new ImageView();

		try(FileInputStream gucciniFile = new FileInputStream("files\\mainPane\\guccini.png")){
			Image gucciniImage = new Image(gucciniFile);
			gucciniView.setImage(gucciniImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		artistsPane.getChildren().add(gucciniView);
		artistsPane.setStyle("-fx-base: lightgreen");
	}


	public static void setAlbumsPane(){

		//TODO implement
		ImageView radiciView = new ImageView();

		try(FileInputStream radiciFile = new FileInputStream("files\\mainPane\\radici.png")){
			Image radiciImage = new Image(radiciFile);
			radiciView.setImage(radiciImage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		albumsPane.getChildren().add(radiciView);
		albumsPane.setStyle("-fx-background-color:blue");
	}
}
