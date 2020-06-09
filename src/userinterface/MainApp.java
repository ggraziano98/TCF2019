package userinterface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import controllers.PlayerController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.TrackList;
import utils.Initialize;
import utils.ResourceLoader;
import utils.Tools;





public class MainApp extends Application{	

	public static ObservableList<String> mainDirList = FXCollections.observableArrayList();
	public static TrackList allSongs;
	public static PlayerController pc;

	public static GridPane root;

	public static GridPane playerPane;
	public static HBox findBox;

	public static VBox playlistPane;
	public static ObservableMap<String, TrackList> playlistMap = FXCollections.emptyObservableMap();

	public static HBox buttonBox;

	public static FlowPane albumsPane;
	public static FlowPane artistsPane;
	public static VBox songsPane;
	public static VBox findView;
	public static VBox songQueueView;

	public static TextField findText = new TextField();
	public static ToggleGroup mainPanel = new ToggleGroup();
	public static VBox playlistsVbox = new VBox();
	
	public static MenuItem themeItem = new MenuItem();


	public static int repeat = 0;
	public static int shuffle = 0;
	
	
	// Da qui si imposta tutta la UI
	public void start(Stage primaryStage) throws Exception {
		
		// Inizializzo il playercontroller
		pc = new PlayerController();

		// Controllo che ci siano i file necessari
		Initialize.checkMainFiles();

		// Inizializzo il pannello che conterrà ogni parte dell'applicazione
		root = Root.rootPane();

		// inizializzo le directory selezionate
		Initialize.setMainDir();
		// ottengo la tracklist che contiene tutte le canzoni presenti nella main directory
		allSongs = Initialize.getAllSongs();
		
		Tools.getNamesSavedPlaylists().forEach(s ->{
			TrackList tracklist = Tools.readPlaylist(s);
			tracklist.setPlaylistName(s);
			playlistMap.put(s, tracklist);
		});;

		// inizializzo la scene e aggiungo il css per lo styling
		Scene scene = new Scene(root, Tools.DWIDTHS[1]*1.5, Tools.DHEIGHTS[2]*1.8);
		scene.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());

		// Set values for songsPane, artistsPane, albumsPane, songQueueView
		RightPanels.panels();

		//Set Icon
		InputStream gioFile = ResourceLoader.load("p.png");
		Image gioImage = new Image(gioFile);
		gioFile.close();
		primaryStage.getIcons().add(gioImage);

		//Set various panes and buttons
		playerPane = PlayerBuilder.playerBuilder();
		findBox = FindStuff.findBox();

		playlistPane = PlaylistStuff.playlist();

		buttonBox = RightPanels.buttonBox;

		songsPane = RightPanels.songsPane;


		root.add(songQueueView, 1, 2, 1, 2);


		//aggiungo i pane al gridpane
		root.add(playerPane, 0, 4, 2, 1);
		root.add(findBox, 0, 1);
		root.add(buttonBox, 1, 1);
		root.add(playlistPane, 0, 3);

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
		primaryStage.setTitle("P per player");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(Tools.DWIDTHS[1]*1.55);
		primaryStage.setMinHeight(Tools.DHEIGHTS[2]*1.9);
		primaryStage.show();
		
	}




	public static void main(String[] args) {
		launch(args);
	}




}
