/*

import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

*/




/* PLAYER VECCHIO

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
	
	// TODO cambiarla con una default image, fix height
	Image songImage = new Image(volumeFile);
	ImageView songView = new ImageView(songImage);
	songView.setFitWidth(100);
	songView.setFitHeight(100);
	songView.setTranslateY(-10);
			
	//TODO songtext che gira (o almeno che ci sta dentro
	Text songName = new Text();
	StringProperty text = new SimpleStringProperty("");
	text.set(pc.getTracklist().get(pc.getCurrentTrack().intValue()).getTitle().getValue());		
	songName.textProperty().bind(text);
	pc.getCurrentTrack().addListener((obs, oldv, newv)->{
		text.set(pc.getTracklist().get(newv.intValue()).getTitle().getValue());
		songView.setImage(pc.getTracklist().get(pc.currentInt()).getImage());
	});
	songName.setTranslateX(0);
	songName.setTranslateY(0);
	
	Text songTime = new Text();
	StringProperty time = new SimpleStringProperty("");
	time.set(String.valueOf(pc.getTracklist().get(pc.currentInt()).getDuration().toMinutes()));
	songTime.textProperty().bind(time);
	pc.getTotalDuration().addListener((obs, oldv, newv)->{
		time.set(String.valueOf(pc.getTracklist().get(pc.currentInt()).getDuration().toMinutes()));
		songView.setImage(pc.getTracklist().get(pc.currentInt()).getImage());
	});
	songTime.setTranslateX(0);
	songTime.setTranslateY(20);
	
	//pane in cui sta il player
	StackPane player = new StackPane();
	player.getChildren().add(playButton);
	player.getChildren().add(prevButton);
	player.getChildren().add(nextButton);
	player.getChildren().add(timeSlider);
	player.getChildren().add(timeLabel);
	player.getChildren().add(volumeButton);
	player.getChildren().add(volumeSlider);
	player.getChildren().add(songName);
	player.getChildren().add(songTime);
	player.getChildren().add(songView);
	player.setStyle("-fx-background-color: #FF0000");
	






	TOGGLE VECCHI
	
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
*/
