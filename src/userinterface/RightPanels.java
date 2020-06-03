package userinterface;


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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import models.Track;
import utils.Tools;

public class RightPanels {

	static HBox buttonBox = new HBox();
//	static FlowPane albumsPane = new FlowPane();
//  static FlowPane artistsPane = new FlowPane();
	static VBox songsPane = new VBox();


	public static void panels() throws Exception{

//		setArtistsPane();
//		setAlbumsPane();

		buttonBox.setAlignment(Pos.CENTER);

		RadioButton songsButton = new RadioButton();
		Text songs= new Text("songs");
		songs.setFont(Font.font("Verdana", FontWeight.BOLD, Tools.DHEIGHTS[1]*0.28));
		songs.setFill(Color.WHITE);
		songsButton.setGraphic(songs);
		songsButton.getStyleClass().remove("radio-button");
		songsButton.setId("rightbuttons");
		songsButton.setMinHeight(Tools.DHEIGHTS[1]*0.8);
		songsButton.setMaxHeight(Tools.DHEIGHTS[1]*0.8);
		songsButton.setMinWidth(Tools.DWIDTHS[1]*0.1);
		songsButton.setMaxWidth(Tools.DWIDTHS[1]*0.1);
		
		TableView<Track> table = TrackView.tableFromTracklist(MainApp.allSongs, MainApp.pc);
				
		songsPane.getChildren().add(table);
		ContextMenus.contextMenuTrackPlaylist(table, MainApp.allSongs);
		VBox.setVgrow(table, Priority.ALWAYS);

		Label songs_artistsLabel = new Label("  ");

//		RadioButton artistsButton = new RadioButton("artists");
//		artistsButton.getStyleClass().remove("radio-button");
//		artistsButton.setId("rightbuttons");
//
//		Label artists_albumsLabel = new Label("  ");
//
//		RadioButton albumsButton = new RadioButton("albums");
//		albumsButton.getStyleClass().remove("radio-button");
//		albumsButton.setId("rightbuttons");
//		
//		Label albums_queueLabel = new Label("  ");
		
		RadioButton queueButton = new RadioButton();
		Text queue= new Text("song queue");
		queue.setFont(Font.font("Verdana", FontWeight.BOLD, Tools.DHEIGHTS[1]*0.28));
		queue.setFill(Color.WHITE);
		queueButton.setGraphic(queue);
		queueButton.getStyleClass().remove("radio-button");
		queueButton.setId("rightbuttons");
		queueButton.setMinHeight(Tools.DHEIGHTS[1]*0.8);
		queueButton.setMaxHeight(Tools.DHEIGHTS[1]*0.8);
		queueButton.setMinWidth(Tools.DWIDTHS[1]*0.15);
		queueButton.setMaxWidth(Tools.DWIDTHS[1]*0.15);


		buttonBox.getChildren().add(songsButton);
		buttonBox.getChildren().add(songs_artistsLabel);
//		buttonBox.getChildren().add(artistsButton);
//		buttonBox.getChildren().add(artists_albumsLabel);
//		buttonBox.getChildren().add(albumsButton);
//		buttonBox.getChildren().add(albums_queueLabel);
		buttonBox.getChildren().add(queueButton);
		
		MainApp.songQueueView = SongQueueView.songQueueView();


//		albumsButton.setUserData(albumsPane);
//		artistsButton.setUserData(artistsPane);
		songsButton.setUserData(songsPane);
		queueButton.setUserData(MainApp.songQueueView);


		songsButton.setToggleGroup(MainApp.mainPanel);
//		artistsButton.setToggleGroup(MainApp.mainPanel);
//		albumsButton.setToggleGroup(MainApp.mainPanel);
		queueButton.setToggleGroup(MainApp.mainPanel);

		songsButton.setSelected(true);
//		albumsButton.setSelected(false);
//		artistsButton.setSelected(false);
		queueButton.setSelected(false);

		songsPane.setVisible(true);
//		albumsPane.setVisible(false);
//		artistsPane.setVisible(false);
		MainApp.songQueueView.setVisible(false);
		
		ContextMenus.contextMenuSongs(table);
	}


//	public static void setArtistsPane(){
//
//		//TODO implement
//		ImageView gucciniView = new ImageView();
//
//		try(FileInputStream gucciniFile = new FileInputStream("files\\mainPane\\guccini.png")){
//			Image gucciniImage = new Image(gucciniFile);
//			gucciniView.setImage(gucciniImage);
//		} catch (Exception e) {
//			Tools.stackTrace(e);
//		}
//
//		artistsPane.getChildren().add(gucciniView);
//		artistsPane.setStyle("-fx-base: lightgreen");
//	}


//	public static void setAlbumsPane(){
//
//		//TODO implement
//		ImageView radiciView = new ImageView();
//
//		try(FileInputStream radiciFile = new FileInputStream("files\\mainPane\\radici.png")){
//			Image radiciImage = new Image(radiciFile);
//			radiciView.setImage(radiciImage);
//		} catch (Exception e) {
//			Tools.stackTrace(e);
//		}
//
//		albumsPane.getChildren().add(radiciView);
//		albumsPane.setStyle("-fx-background-color:blue");
//	}
}
