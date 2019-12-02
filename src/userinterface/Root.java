package userinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controllers.FileController;
import controllers.PlayerController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Track;
import models.TrackList;
import utils.Tools;





public class Root extends Application {

	// TODO chiudere le risorse una volta usate 
	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();

	static String mainDir = getMainDir();
	static FileController fc = new FileController(Paths.get(mainDir));
	static TrackList mainTracklist = fc.getFilesFromDir();
	static PlayerController pc = new PlayerController(mainTracklist);
	static GridPane root = new GridPane();

	@Override
	public void start(Stage primaryStage) throws Exception {

		//GRIDPANE è il pane di livello piu alto, contiene tutti gli altri
		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(20);
		root.setPadding(new Insets(5, 10, 5, 10));
		root.setGridLinesVisible(true);

		//imposto i constraints del gridpane per settare anche il comportamento quando riscalo
		double columnOneWidht = 300;
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(columnOneWidht);
		column1.setMaxWidth(columnOneWidht);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMinWidth(600);
		column2.setHgrow(Priority.ALWAYS);
		root.getColumnConstraints().addAll(column1, column2); 

		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(45);
		row1.setMaxHeight(45);
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(100);
		row2.setVgrow(Priority.ALWAYS);
		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(300);
		row3.setMaxHeight(300);
		root.getRowConstraints().addAll(row1, row2, row3); 

		Scene scene = new Scene(root, 650, 600);




		// SEARCHBAR per cercare all'interno della libreria
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		TextField findText = new TextField();
		findText.setMinWidth(220);
		Button findButton = new Button("cerca");

		findHBox.getChildren().add(findText);	
		findHBox.getChildren().add(findButton);




		//PLAYER play, pause, next, previous
		





		//TODO pane che fanno vedere le canzoni
		FileInputStream gucciniFile = new FileInputStream("files\\mainPane\\guccini.png");
		Image gucciniImage = new Image(gucciniFile);
		ImageView gucciniView = new ImageView(gucciniImage);

		FileInputStream radiciFile = new FileInputStream("files\\mainPane\\radici.png");
		Image radiciImage = new Image(radiciFile);
		ImageView radiciView = new ImageView(radiciImage);


		HBox listsPane = new HBox();
		listsPane.setAlignment(Pos.CENTER);

		RadioButton songsButton = new RadioButton("songs");
		songsButton.getStyleClass().remove("radio-button");
		songsButton.getStyleClass().add("toggle-button");
		VBox songsPane = TrackView.tableFromTracklist(mainTracklist, pc);


		Label songs_artistsLabel = new Label("  ");

		RadioButton artistsButton = new RadioButton("artists");
		artistsButton.getStyleClass().remove("radio-button");
		artistsButton.getStyleClass().add("toggle-button");
		FlowPane artistsPane = new FlowPane();

		artistsPane.getChildren().add(gucciniView);
		artistsPane.setStyle("-fx-base: lightgreen");
		artistsButton.setStyle("-fx-background-color:green");

		Label artists_albumsLabel = new Label("  ");

		RadioButton albumsButton = new RadioButton("albums");
		albumsButton.getStyleClass().remove("radio-button");
		albumsButton.getStyleClass().add("toggle-button");
		FlowPane albumsPane = new FlowPane();

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

		ToggleGroup mainPanel = new ToggleGroup();
		songsButton.setToggleGroup(mainPanel);
		artistsButton.setToggleGroup(mainPanel);
		albumsButton.setToggleGroup(mainPanel);

		songsButton.setSelected(true);
		albumsButton.setSelected(false);
		artistsButton.setSelected(false);

		songsPane.setVisible(true);
		albumsPane.setVisible(false);
		artistsPane.setVisible(false);

		mainPanel.selectedToggleProperty().addListener((obs, oldv, newv) ->{
			if(newv != null) {
				((Node) oldv.getUserData()).setVisible(false);
			}
			((Node) newv.getUserData()).setVisible(true);
		});

		VBox playlistsVbox = new VBox();
		ScrollPane scroll = new ScrollPane();
		scroll.setContent(playlistsVbox);



		FileInputStream gioFile = new FileInputStream("files\\gio.png");
		Image gioImage = new Image(gioFile);
		primaryStage.getIcons().add(gioImage);

		GridPane pB = (GridPane) PlayerBuilder.playerBuilder(pc, primaryStage, findText, columnOneWidht);
		//aggiungo i pane al gridpane
		root.add(pB, 0, 2);
		root.add(findHBox, 0, 0);
		root.add(listsPane, 1, 0);
		root.add(scroll, 0, 1);

		root.add(albumsPane, 1, 1, 1, 2);
		root.add(artistsPane, 1, 1, 1, 2);
		root.add(songsPane, 1, 1, 1, 2);




		


		findButton.setOnMouseClicked((e) -> {
			find(findText.getText(), mainPanel, root, pc);
		});
		findHBox.setOnKeyReleased((final KeyEvent KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.ENTER) {
				find(findText.getText(), mainPanel, root, pc);
			}
		});




		//aggiungo tutto alla window
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(950);
		primaryStage.setMinHeight(650);
		primaryStage.show();



		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		savedPlaylists.forEach((String name)->{
			TrackList tracklist = Tools.readPlaylist(name);
			VBox table = TrackView.tableFromTracklist(tracklist, pc);
			root.add(table, 1, 1, 1, 2);
			playlists(name, playlistsVbox, mainPanel, table);
		});




	}

	public static void main(String[] args) {
		launch(args);
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


	


	public static void playlists(String string, VBox box, ToggleGroup mainPanel, Node dataPane) {
		RadioButton playlistButton = new RadioButton(string);
		playlistButton.getStyleClass().remove("radio-button");
		playlistButton.getStyleClass().add("toggle-button");
		playlistButton.setMinWidth(280);
		playlistButton.setMaxWidth(280);
		playlistButton.setAlignment(Pos.CENTER_LEFT);
		playlistButton.setToggleGroup(mainPanel);
		playlistButton.setStyle(Tools.TRANSBUTT);
		playlistButton.setOnMouseMoved((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.BOLDBUTT);
		});
		playlistButton.setOnMouseExited((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		playlistButton.selectedProperty().addListener((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		box.getChildren().add(playlistButton);

		dataPane.setVisible(false);
		playlistButton.setUserData(dataPane);;

	}


	public static String getMainDir() {
		Path mainDirFile = Paths.get("files", "mainDir.txt");
		String mainDir = "";
		try {
			BufferedReader br= Files.newBufferedReader(mainDirFile);
			mainDir = br.readLine();
			br.close();	
		} catch (IOException e) { 
			System.out.println("mainDir non selezionata");
		}

		while(mainDir == "" || mainDir == null || !Files.isDirectory(Paths.get(mainDir))) {
			TextInputDialog dialog = new TextInputDialog("Select Directory");
			dialog.setTitle("Enter main directory path");
			dialog.setContentText("example: C:\\Music");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				mainDir = result.get();
			}

			try {
				Files.createFile(mainDirFile);
				BufferedWriter bw= Files.newBufferedWriter(mainDirFile);
				bw.write(mainDir);
				bw.close();
			} catch (IOException e) {
				if (e instanceof FileAlreadyExistsException) {
					BufferedWriter bw;
					try {
						bw = Files.newBufferedWriter(mainDirFile);
						bw.write(mainDir);
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else 
					e.printStackTrace();
			}
		}
		System.out.println(mainDir);
		return mainDir;
	}


	
}











