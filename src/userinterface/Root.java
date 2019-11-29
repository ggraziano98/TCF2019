package userinterface;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import controllers.PlayerController;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.TrackList;
import utils.Tools;





public class Root extends Application {

	// TODO chiudere le risorse una volta usate 
	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();

	static TrackList playlist = Tools.readPlaylist("Playlist playerTester");
	static PlayerController pc = new PlayerController(playlist);


	@Override
	public void start(Stage primaryStage) throws Exception {

		
		
		
		//gridpane è il pane di livello piu alto, contiene tutti gli altri
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
		column2.setMinWidth(400);
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

		
		
		
		// barra per cercare all'interno della libreria
		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		TextField findText = new TextField();
		findText.setMinWidth(250);
		Button findButton = new Button("cerca");
		
		findHBox.getChildren().add(findText);	
		findHBox.getChildren().add(findButton);
		
		

		
		//immagini per i bottoni del player: play, pause, next, previous
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
			if (play.get()) playButton.setGraphic(playView);
			else playButton.setGraphic(pauseView);
			play.set(!play.get());
			});
		playButton.setTranslateX(-100);
		playButton.setTranslateY(100);

		prevButton.setOnMouseClicked((e) -> previousSong());
		prevButton.setTranslateX(-150);
		prevButton.setTranslateY(100);

		nextButton.setOnMouseClicked((e) -> nextSong());
		nextButton.setTranslateY(100);
		nextButton.setTranslateX(-50);

		Slider timeSlider = new Slider();
		timeSlider.setMax(100);
		timeSlider.setMin(0);
		timeSlider.setTranslateY(50);
		timeSlider.setMaxWidth(460);
		final Label timeLabel = new Label();
		timeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue, 
					Number oldValue, 
					Number newValue) { 
				timeLabel.textProperty().setValue(
						String.valueOf(newValue.intValue()));
			}
		});
		timeLabel.setTextFill(Color.WHITE);
		timeLabel.setTranslateX(-200);
		timeLabel.setTranslateY(65);
		timeLabel.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));



		
		//bottoni del player e slider per il controllo del volume
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
		
		volumeButton.setTranslateX(100);
		volumeButton.setTranslateY(100);

		Slider volumeSlider = new Slider();
		volumeSlider.setMax(1);
		volumeSlider.setMin(0);
		volumeSlider.setValue(1);
		volumeSlider.setMaxWidth(80);
		volumeSlider.setTranslateX(165);
		volumeSlider.setTranslateY(100);
		volumeSlider.valueProperty().bindBidirectional(pc.getVolumeValue());


		
		
		//pane in cui sta il player
		StackPane player = new StackPane();
		player.getChildren().add(playButton);
		player.getChildren().add(prevButton);
		player.getChildren().add(nextButton);
		player.getChildren().add(timeSlider);
		player.getChildren().add(timeLabel);
		player.getChildren().add(volumeButton);
		player.getChildren().add(volumeSlider);
		player.setStyle("-fx-background-color: #FF0000");
		
		
		
		
		//TODO pane che fanno vedere le canzoni
		FileInputStream gucciniFile = new FileInputStream("files\\mainPane\\guccini.png");
		Image gucciniImage = new Image(gucciniFile);
		ImageView gucciniView = new ImageView(gucciniImage);
		FileInputStream radiciFile = new FileInputStream("files\\mainPane\\radici.png");
		Image radiciImage = new Image(radiciFile);
		ImageView radiciView = new ImageView(radiciImage);
		HBox listsPane = new HBox();
		listsPane.setAlignment(Pos.CENTER);
		ToggleButton songsButton = new ToggleButton("songs");
		Label songs_artistsLabel = new Label("  ");
		ToggleButton artistsButton = new ToggleButton("artists");
		FlowPane artistsPane = new FlowPane();
		artistsPane.getChildren().add(gucciniView);
		artistsPane.setStyle("-fx-base: lightgreen");
		artistsButton.setStyle("-fx-background-color:green");
		Label artists_albumsLabel = new Label("  ");
		ToggleButton albumsButton = new ToggleButton("albums");
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
		songsButton.setUserData("song");
		
		ToggleGroup mainPanel = new ToggleGroup();
		songsButton.setToggleGroup(mainPanel);
		artistsButton.setToggleGroup(mainPanel);
		albumsButton.setToggleGroup(mainPanel);
		
		albumsButton.setSelected(false);
		artistsButton.setSelected(true);
		
		mainPanel.selectedToggleProperty().addListener((obs, oldv, newv) ->{
			if(oldv != null) {
				((Node) oldv.getUserData()).setVisible(false);
			}
			((Node) newv.getUserData()).setVisible(true);
		});
		
		VBox playlistsVbox = new VBox();
		
		
		FileInputStream gioFile = new FileInputStream("files\\gio.png");
		Image gioImage = new Image(gioFile);
		ImageView gioView = new ImageView(gioImage);
		primaryStage.getIcons().add(gioImage);
		
		
		
		
		//aggiungo i pane al gridpane
		root.add(player, 0, 2);
		root.add(findHBox, 0, 0);
		root.add(listsPane, 1, 0);
		root.add(playlistsVbox, 0, 1);
		
		root.add(albumsPane, 1, 1, 1, 2);
		root.add(artistsPane, 1, 1, 1, 2);
		
		
		
		
		// gestisco i keypresses
		//TODO restringere gli shortcut quando si cerca
		primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, k -> {
	        if ( k.getCode() == KeyCode.SPACE){
				playPause(play);
				if (play.get()) playButton.setGraphic(playView);
				else playButton.setGraphic(pauseView);
				play.set(!play.get());
	        }
	        if ( k.getCode() == KeyCode.K){
				playPause(play);
				if (play.get()) playButton.setGraphic(playView);
				else playButton.setGraphic(pauseView);
				play.set(!play.get());
	        }
	        if ( k.getCode() == KeyCode.M) {
				if(muted.get()) volumeButton.setGraphic(volumeView);
				else volumeButton.setGraphic(muteView);
				muted.set(!muted.get());
				volumeMute(muted);
				}
	        if ( k.getCode() == KeyCode.L) nextSong();
	        if ( k.getCode() == KeyCode.J) previousSong();
		});
		
		
		findButton.setOnMouseClicked((e) -> {
			find(findText.getText());
		});
		findHBox.setOnKeyReleased((final KeyEvent KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.ENTER) {
				find(findText.getText());
			}
		});
		
		

		
		//aggiungo tutto alla window
		Scene scene = new Scene(root, 650, 600);
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(650);
		primaryStage.show();

		
		
		//TODO togliere e mettere le playlist giuste
		playlists("lel", playlistsVbox, mainPanel);
		playlists("lul", playlistsVbox, mainPanel);
		playlists("abracadabra", playlistsVbox, mainPanel);
		playlists("fofofofollo", playlistsVbox, mainPanel);
		playlists("best hits", playlistsVbox, mainPanel);

	}

	public static void main(String[] args) {
		launch(args);

		Tools.cout(playlist);
	}


	public static void find(String keyWord) {
		System.out.println(keyWord);		
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



	public static void playlists(String string, VBox box, ToggleGroup mainPanel) {
		ToggleButton playlist = new ToggleButton(string);
		playlist.setToggleGroup(mainPanel);
		playlist.setStyle("-fx-background-color:none");
		playlist.setOnMouseEntered((e) -> {
			playlist.setStyle("-fx-text-base-color: blue;"
					+"-fx-background-color:yellow");
		});
		playlist.setOnMouseExited((e) -> {
			playlist.setStyle("-fx-backgound-color:none");
		});
		box.getChildren().add(playlist);
		
	}



}










