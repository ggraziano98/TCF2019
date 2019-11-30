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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import controllers.FileController;
import controllers.PlayerController;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
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

	@Override
	public void start(Stage primaryStage) throws Exception {

		//GRIDPANE è il pane di livello piu alto, contiene tutti gli altri
		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(20);
		root.setPadding(new Insets(5, 10, 5, 10));
		root.setGridLinesVisible(true);

		//imposto i constraints del gridpane per settare anche il comportamento quando riscalo
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(300);
		column1.setMaxWidth(300);
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
		GridPane playerV = new GridPane();
		HBox playerButtons = new HBox();

		RowConstraints row60 = new RowConstraints(35, 35, 35);
		row60.setValignment(VPos.CENTER);
		RowConstraints row120 = new RowConstraints(180, 180, 180);
		row60.setValignment(VPos.CENTER);
		RowConstraints row50 = new RowConstraints(50, 50, 50);
		row50.setValignment(VPos.CENTER);
		playerV.getRowConstraints().addAll(row60, row120, row60, row50);
		ColumnConstraints colConstraint = new ColumnConstraints(300, 300, 300);
		colConstraint.setHalignment(HPos.CENTER);
		playerV.getColumnConstraints().add(colConstraint);
		playerV.setGridLinesVisible(true);

		FileInputStream playFile = new FileInputStream("files\\Player\\play.png");
		Image playImage = new Image(playFile);
		ImageView playView = new ImageView(playImage);
		playView.setFitHeight(25);
		playView.setFitWidth(25);

		FileInputStream pauseFile = new FileInputStream("files\\Player\\pause.png");
		Image pauseImage = new Image(pauseFile);
		ImageView pauseView = new ImageView(pauseImage);
		pauseView.setFitHeight(25);
		pauseView.setFitWidth(25);

		FileInputStream prevFile = new FileInputStream("files\\Player\\ps.png");
		Image prevImage = new Image(prevFile);
		ImageView prevView = new ImageView(prevImage);
		prevView.setFitHeight(25);
		prevView.setFitWidth(25);

		FileInputStream nextFile = new FileInputStream("files\\Player\\ns.png");
		Image nextImage = new Image(nextFile);
		ImageView nextView = new ImageView(nextImage);
		nextView.setFitHeight(25);
		nextView.setFitWidth(25);

		Button playButton = new Button("",playView);
		Button prevButton = new Button("",prevView);
		Button nextButton = new Button("",nextView);


		AtomicBoolean play = new AtomicBoolean(false);
		playButton.setOnMouseClicked((e) -> {
			playPause(play);
			setPlayingImage(playButton, play, playView, pauseView);
		});
		pc.getPlaying().addListener((obs, oldv, newv)->{
			play.set(newv.booleanValue());
			setPlayingImage(playButton, play, playView, pauseView);
		});

		prevButton.setOnMouseClicked((e) -> previousSong());
		nextButton.setOnMouseClicked((e) -> nextSong());


		//PLAYER: volume, slider, titolo
		FileInputStream volumeFile = new FileInputStream("files\\Player\\volume.png");
		Image volumeImage = new Image(volumeFile);
		ImageView volumeView = new ImageView(volumeImage);
		volumeView.setFitHeight(25);
		volumeView.setFitWidth(25);

		FileInputStream muteFile = new FileInputStream("files\\Player\\mute.png");
		Image muteImage = new Image(muteFile);
		ImageView muteView = new ImageView(muteImage);
		muteView.setFitHeight(25);
		muteView.setFitWidth(25);

		AtomicBoolean muted = new AtomicBoolean(false);
		Button volumeButton = new Button("",volumeView);
		volumeButton.setOnMouseClicked((e) -> {
			if(muted.get()) {
				volumeButton.setGraphic(volumeView);
			}
			else {
				volumeButton.setGraphic(muteView);
			}
			muted.set(!muted.get());
			volumeMute(muted);

		});

		Slider volumeSlider = new Slider();
		volumeSlider.setMax(1);
		volumeSlider.setMin(0);
		volumeSlider.setValue(1);
		volumeSlider.setMaxWidth(80);
		volumeSlider.valueProperty().bindBidirectional(pc.getVolumeValue());

		playerButtons.getChildren().addAll(prevButton, playButton, nextButton, volumeButton, volumeSlider);




		Slider timeSlider = new Slider();
		timeSlider.setMax(100);
		timeSlider.setMin(0);
		timeSlider.setMaxWidth(460);
		final Label timeLabel = new Label();

		timeSlider.setOnMouseReleased((ev)->{
			double currentTime = pc.getCurrentTime().doubleValue()/pc.getTotalDuration().doubleValue()*100;
			if (Math.abs(currentTime - timeSlider.getValue()) > 0.1) {
				pc.seek(new Duration(pc.getTotalDuration().doubleValue()*(timeSlider.getValue()*10)));
			}
		});
		pc.getCurrentTime().addListener((obs, oldv, newv)->{
			if (!timeSlider.isValueChanging() && !timeSlider.isPressed()) {
				timeSlider.setValue(newv.doubleValue()/pc.getTotalDuration().doubleValue()*100);
			}
		});
		timeLabel.setTextFill(Color.WHITE);
		timeLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		// TODO cambiarla con una default image, fix height
		Image songImage = new Image(volumeFile);
		ImageView songView = new ImageView(songImage);
		songView.setFitWidth(170);
		songView.setFitHeight(170);

		//TODO songtext che gira (o almeno che ci sta dentro
		Label songName = new Label();
		songName.setPadding(new Insets(0, 3, 0, 3));
		StringProperty text = new SimpleStringProperty("");
		if (pc.getTracklist().getSize()>0) {
			text.set(pc.getTracklist().get(pc.getCurrentTrack().intValue()).getTitle().getValue());		
		}
		else text.set("Seleziona una canzone");
		songName.textProperty().bind(text);
		pc.getCurrentTrack().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {
				text.set(pc.getTracklist().get(newv.intValue()).getTitle().getValue());	
				songView.setImage(pc.getTracklist().get(pc.currentInt()).getImage());
			}
			else text.set("Seleziona una canzone");
		});
		/**unused
		Text songTime = new Text();
		StringProperty time = new SimpleStringProperty("");
		time.set(String.valueOf(pc.getTracklist().get(pc.currentInt()).getDuration().toMinutes()));
		songTime.textProperty().bind(time);
		pc.getTotalDuration().addListener((obs, oldv, newv)->{
			time.set(String.valueOf(pc.getTracklist().get(pc.currentInt()).getDuration().toMinutes()));
			songView.setImage(pc.getTracklist().get(pc.currentInt()).getImage());
		});
		 */

		GridPane.setHalignment(songName, HPos.LEFT);
		playerButtons.setAlignment(Pos.CENTER);
		playerV.add(songName, 0, 0);
		playerV.add(songView, 0, 1);
		playerV.add(timeSlider, 0, 2);
		playerV.add(playerButtons, 0, 3);






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




		//aggiungo i pane al gridpane
		root.add(playerV, 0, 2);
		root.add(findHBox, 0, 0);
		root.add(listsPane, 1, 0);
		root.add(scroll, 0, 1);

		root.add(albumsPane, 1, 1, 1, 2);
		root.add(artistsPane, 1, 1, 1, 2);
		root.add(songsPane, 1, 1, 1, 2);




		// gestisco i keypresses
		primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, k -> {
			if(k.getTarget() != findText) {
				if ( k.getCode() == KeyCode.SPACE){
					playPause(play);
					setPlayingImage(playButton, play, playView, pauseView);
				}
				if ( k.getCode() == KeyCode.K){
					playPause(play);
					setPlayingImage(playButton, play, playView, pauseView);
				}
				if ( k.getCode() == KeyCode.M) {
					if(muted.get()) volumeButton.setGraphic(volumeView);
					else volumeButton.setGraphic(muteView);
					muted.set(!muted.get());
					volumeMute(muted);
				}
				if ( k.getCode() == KeyCode.L) nextSong();
				if ( k.getCode() == KeyCode.J) previousSong();
			}

		});


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
		List<Track> list = pc.getTracklist().stream().filter(t->{
			return (
					t.getAlbum().getValue().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getArtist().getValue().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getTitle().getValue().toLowerCase().contains(keyWord.toLowerCase()) ||
					t.getGenre().getValue().toLowerCase().contains(keyWord.toLowerCase()));
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


	public static void playPause(AtomicBoolean play) {
		if (play.get()) pc.pause();
		else pc.play();

	}

	public static void nextSong() {
		pc.next();
	}

	public static void previousSong() {
		pc.prev();
	}


	public static void volumeMute(AtomicBoolean muted) {
		pc.setMuted(new SimpleBooleanProperty(muted.get()));
	}

	public static void setPlayingImage(Button playButton, AtomicBoolean play, ImageView playView, ImageView pauseView) {
		if (!play.get()) playButton.setGraphic(playView);
		else playButton.setGraphic(pauseView);
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
			if(br.readLine() != null) {
				mainDir = br.readLine();
			}	
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











