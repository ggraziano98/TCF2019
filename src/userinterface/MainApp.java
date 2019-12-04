package userinterface;

import java.io.FileInputStream;
import java.util.List;

import controllers.FileController;
import controllers.PlayerController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
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
import utils.Tools;





public class MainApp extends Application{
	
	//TODO fix images
	//TODO fix playlists: expand content, fix name convention
	//TODO shuffle, repeat
	//TODO finish songqueue
	//TODO initialize all songs
	//TODO non si capisce quale playlist si punti con il contextmenu
	//TODO contextmenu che funziona su tutto il box delle playlist
	//TODO canc non funziona al momento nel contextmenu
	//TODO fix information context menu
	//TODO fix playlist/tracklist/songqueue context menu
	//TODO fix songqueue view
	//TODO fix songImage
	//TODO fix contextmenu not closing

	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();

	public static List<String> mainDirList;
	public static TrackList allTracksList;
	public static PlayerController pc;

	public static GridPane root;

	public static GridPane playerPane;
	public static HBox findBox;
	
	public static ScrollPane playlistPane;
	public static ObservableList<String> savedPlaylists;
	
	public static HBox buttonBox;

	public static FlowPane albumsPane;
	public static FlowPane artistsPane;
	public static VBox songsPane;
	public static VBox findView;
	public static VBox songQueueView;
	
	public static TextField findText = new TextField();
	public static ToggleGroup mainPanel = new ToggleGroup();


	public void start(Stage primaryStage) throws Exception {

		root = Root.rootPane();
		
		mainDirList = Initialize.getMainDir();
		allTracksList = FileController.getFilesFromDir(mainDirList);
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
		
		
		root.add(songQueueView, 1, 1, 1, 2);


		//aggiungo i pane al gridpane
		root.add(playerPane, 0, 2);
		root.add(findBox, 0, 0);
		root.add(buttonBox, 1, 0);
		root.add(playlistPane, 0, 1);

		root.add(albumsPane, 1, 1, 1, 2);
		root.add(artistsPane, 1, 1, 1, 2);
		root.add(songsPane, 1, 1, 1, 2);

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





