package userinterface;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import controllers.PlayerController;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.TrackList;
import utils.Initialize;
import utils.Tools;





public class MainApp extends Application{

	//TODO refreshDirFiles confrontare file salvati e file nella cartella per togliere quelli vecchi e aggiungere quelli nuovi GIO
	//TODO editMetadata in file GIO
	//TODO finish songqueue GIO
	//TODO Handle non existing song GIO might be done
	//TODO fix playlists special characters FO might be done
	//TODO contextmenu che funziona su tutto il box delle playlist FO
	//TODO fix playlist/tracklist/songqueue context menu FO
	//TODO fix contextmenu not closing FO
	//TODO player redesign DAVIDE
	//TODO loading screen DAVIDE
	//TODO canc non funziona al momento nel contextmenu DAVIDE
	//TODO repeat graphics DAVIDE
	//TODO per gli errori usare Tools.stackTrace


	public static List<String> mainDirList = new ArrayList<String>();
	public static TrackList allSongs;
	public static PlayerController pc;

	public static GridPane root;

	public static GridPane playerPane;
	public static HBox findBox;

	public static VBox playlistPane;
	public static ObservableList<String> savedPlaylists;

	public static HBox buttonBox;

	public static FlowPane albumsPane;
	public static FlowPane artistsPane;
	public static VBox songsPane;
	public static VBox findView;
	public static VBox songQueueView;

	public static TextField findText = new TextField();
	public static ToggleGroup mainPanel = new ToggleGroup();
	public static VBox playlistsVbox = new VBox();

	public static List<TrackList> playlistList = new ArrayList<TrackList>();

	public static int repeat = 0;

	public static List<TrackList> playlistList = new ArrayList<TrackList>();
	

	public void start(Stage primaryStage) throws Exception {




		root = Root.rootPane();

		Initialize.setMainDir();
		allSongs = Initialize.getAllSongs();
		savedPlaylists = Tools.getNamesSavedPlaylists();

		pc = new PlayerController();

		Scene scene = new Scene(root, 650, 600);


		// Set values for songsPane, artistsPane, albumsPane, songQueueView
		RightPanels.panels();

		//Set Icon
		FileInputStream gioFile = new FileInputStream("files\\gio.png");
		Image gioImage = new Image(gioFile);
		gioFile.close();
		primaryStage.getIcons().add(gioImage);

		//Set various panes and buttons
		playerPane = PlayerBuilder.playerBuilder();
		findBox = FindStuff.findBox();

		playlistPane = PlaylistStuff.playlist();

		buttonBox = RightPanels.buttonBox;

		albumsPane = RightPanels.albumsPane;
		artistsPane = RightPanels.artistsPane;
		songsPane = RightPanels.songsPane;


		root.add(songQueueView, 1, 2, 1, 2);


		//aggiungo i pane al gridpane
		root.add(playerPane, 0, 4, 2, 1);
		root.add(findBox, 0, 1);
		root.add(buttonBox, 1, 1);
		root.add(playlistPane, 0, 3);

		root.add(albumsPane, 1, 2, 1, 2);
		root.add(artistsPane, 1, 2, 1, 2);
		root.add(songsPane, 1, 2, 1, 2);

		//Set buttons to switch between songs, album and artist panes
		mainPanel.selectedToggleProperty().addListener((obs, oldv, newv) ->{
			if (findView != null) findView.setVisible(false);
			if(newv != null) {
				((Node) oldv.getUserData()).setVisible(false);
			}
			((Node) newv.getUserData()).setVisible(true);
		});



		//aggiungo tutto alla window
		primaryStage.setTitle("Best player ever");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(950);
		primaryStage.setMinHeight(650);
		primaryStage.show();





	}

	public static void main(String[] args) {
		launch(args);
	}




}
