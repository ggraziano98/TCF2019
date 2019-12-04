package userinterface;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import controllers.PlayerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import utils.Tools;


public class PlayerBuilder {

	/**
	 * Used to define the player UI. 
	 * 
	 * Defines all the buttons, images and sliders inside the player, and the corresponding logic
	 *
	 *@return GridPane player che contiene tutta la UI
	 */
	public static GridPane playerBuilder() throws Exception {
		
		PlayerController pc = MainApp.pc;
		TextField findText = MainApp.findText;

		//PlayerController pcPlayer = Root.pc;
		GridPane playerV = new GridPane();


		double columnOneWidth = Tools.DWIDTHS[0];



		RowConstraints row60 = new RowConstraints(35, 35, 35);
		row60.setValignment(VPos.CENTER);
		RowConstraints row120 = new RowConstraints(180, 180, 180);
		row60.setValignment(VPos.CENTER);
		RowConstraints row50 = new RowConstraints(50, 50, 50);
		row50.setValignment(VPos.CENTER);
		playerV.getRowConstraints().addAll(row60, row120, row60, row50);
		
		double col = Tools.DWIDTHS[0];
		
		ColumnConstraints colConstraint = new ColumnConstraints(col, col, col);
		colConstraint.setHalignment(HPos.CENTER);
		playerV.getColumnConstraints().add(colConstraint);
		
		// TODO togliere
		playerV.setGridLinesVisible(true);

		HBox playerButtons = controlStuff(pc, playerV, findText);
		HBox sliderBox = songTime(pc, columnOneWidth);
		ImageView songView = songImage(pc);
		Label songName = songTitle(pc);

		GridPane.setHalignment(songName, HPos.LEFT);
		playerButtons.setAlignment(Pos.CENTER);
		playerV.add(songName, 0, 0);
		playerV.add(songView, 0, 1);
		playerV.add(sliderBox, 0, 2);
		playerV.add(playerButtons, 0, 3);

		return playerV;

	}



	public static HBox controlStuff(PlayerController pc, GridPane playerV, TextField findText) throws Exception{
		HBox playerButtons = new HBox();

		FileInputStream playFile = new FileInputStream("files\\Player\\play.png");
		Image playImage = new Image(playFile);
		playFile.close();		
		ImageView playView = new ImageView(playImage);
		playView.setFitHeight(25);
		playView.setFitWidth(25);

		FileInputStream pauseFile = new FileInputStream("files\\Player\\pause.png");
		Image pauseImage = new Image(pauseFile);
		pauseFile.close();
		ImageView pauseView = new ImageView(pauseImage);
		pauseView.setFitHeight(25);
		pauseView.setFitWidth(25);

		FileInputStream prevFile = new FileInputStream("files\\Player\\ps.png");
		Image prevImage = new Image(prevFile);
		prevFile.close();
		ImageView prevView = new ImageView(prevImage);
		prevView.setFitHeight(25);
		prevView.setFitWidth(25);

		FileInputStream nextFile = new FileInputStream("files\\Player\\ns.png");
		Image nextImage = new Image(nextFile);
		nextFile.close();
		ImageView nextView = new ImageView(nextImage);
		nextView.setFitHeight(25);
		nextView.setFitWidth(25);

		FileInputStream shuffleFile = new FileInputStream("files\\Player\\shuffle.png");
		Image shuffleImage = new Image(shuffleFile);
		shuffleFile.close();
		ImageView shuffleView = new ImageView(shuffleImage);
		shuffleView.setFitHeight(25);
		shuffleView.setFitWidth(25);

		FileInputStream repeatFile = new FileInputStream("files\\Player\\repeat.png");
		Image repeatImage = new Image(repeatFile);
		repeatFile.close();
		ImageView repeatView = new ImageView(repeatImage);
		repeatView.setFitHeight(25);
		repeatView.setFitWidth(25);

		Button playButton = new Button("",playView);
		Button prevButton = new Button("",prevView);
		Button nextButton = new Button("",nextView);
		Button shuffleButton = new Button("",shuffleView);
		Button repeatButton = new Button("",repeatView);


		AtomicBoolean play = new AtomicBoolean(false);
		playButton.setOnMouseClicked((e) -> {
			playPause(play, pc);
			setPlayingImage(playButton, play, playView, pauseView);
		});
		pc.playingProperty().addListener((obs, oldv, newv)->{
			play.set(newv.booleanValue());
			setPlayingImage(playButton, play, playView, pauseView);
		});

		prevButton.setOnMouseClicked((e) -> previousSong(pc));
		nextButton.setOnMouseClicked((e) -> nextSong(pc));


		//PLAYER: volume, slider, titolo
		FileInputStream volumeFile = new FileInputStream("files\\Player\\volume.png");
		Image volumeImage = new Image(volumeFile);
		volumeFile.close();
		ImageView volumeView = new ImageView(volumeImage);
		volumeView.setFitHeight(25);
		volumeView.setFitWidth(25);

		FileInputStream muteFile = new FileInputStream("files\\Player\\mute.png");
		Image muteImage = new Image(muteFile);
		muteFile.close();
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
			volumeMute(muted, pc);

		});

		Slider volumeSlider = new Slider();
		volumeSlider.setMax(1);
		volumeSlider.setMin(0);
		volumeSlider.setValue(1);
		volumeSlider.setMaxWidth(80);
		volumeSlider.valueProperty().bindBidirectional(pc.volumeValueProperty());

		playerV.setOnScroll((ev)->{
			double volumeAmount = ev.getDeltaY();
			pc.setVolumeValue(pc.getVolumeValue() + volumeAmount/300);
		});

		playerButtons.getChildren().addAll(prevButton, playButton, nextButton, volumeButton, volumeSlider, shuffleButton, repeatButton );

		// gestisco i keypresses
		GridPane root = MainApp.root;
		root.addEventFilter(KeyEvent.KEY_RELEASED, k -> {
			if(k.getTarget() != findText) {
				if ( k.getCode() == KeyCode.SPACE){
					playPause(play, pc);
					setPlayingImage(playButton, play, playView, pauseView);
				}
				if ( k.getCode() == KeyCode.K){
					playPause(play, pc);
					setPlayingImage(playButton, play, playView, pauseView);
				}
				if ( k.getCode() == KeyCode.M) {
					if(muted.get()) volumeButton.setGraphic(volumeView);
					else volumeButton.setGraphic(muteView);
					muted.set(!muted.get());
					volumeMute(muted, pc);
				}
				if ( k.getCode() == KeyCode.L) nextSong(pc);
				if ( k.getCode() == KeyCode.J) previousSong(pc);
				if ( k.getCode() == KeyCode.O) pc.seek(Duration.seconds(pc.getCurrentTime() + 10));
				if ( k.getCode() == KeyCode.I) pc.seek(Duration.seconds(pc.getCurrentTime() - 10));
			}

		});

		return playerButtons;

	}

	public static void playPause(AtomicBoolean play, PlayerController pc) {
		if (play.get()) pc.pause();
		else pc.play();

	}

	public static void nextSong(PlayerController pc) {
		pc.next();
	}

	public static void previousSong(PlayerController pc) {
		pc.prev();
	}


	public static void volumeMute(AtomicBoolean muted, PlayerController pc) {
		pc.setMuted(muted.get());
	}

	public static void setPlayingImage(Button playButton, AtomicBoolean play, ImageView playView, ImageView pauseView) {
		if (!play.get()) playButton.setGraphic(playView);
		else playButton.setGraphic(pauseView);
	}

	public static Label songTitle (PlayerController pc) {
		//TODO songtext che gira (o almeno che ci sta dentro
		Label songName = new Label();
		songName.setPadding(new Insets(0, 3, 0, 3));
		StringProperty text = new SimpleStringProperty("");
		if (pc.getTracklist().getSize()>0) {
			text.set(pc.getTracklist().get(pc.getCurrentInt()).getTitle());		
		}
		else text.set("Seleziona una canzone");
		songName.textProperty().bind(text);
		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {
				text.set(pc.getTracklist().get(newv.intValue()).getTitle());	
			}
			else text.set("Seleziona una canzone");
		});
		return songName;
	}

	public static ImageView songImage(PlayerController pc) throws Exception {
		ImageView songView = new ImageView(Tools.DIMAGE);
		songView.setFitWidth(170);
		songView.setFitHeight(170);

		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {	
				songView.setImage(pc.getTracklist().get(pc.getCurrentInt()).getImage());
			}
			else songView.setImage(Tools.DIMAGE);});
		return songView;
	}

	public static HBox songTime(PlayerController pc, Double columnOneWidht) {
		HBox sliderBox = new HBox();
		Slider timeSlider = new Slider();
		timeSlider.setMax(100);
		timeSlider.setMin(0);
		timeSlider.setMaxWidth(460);


		Label timeLabel = new Label();
		timeLabel.setText(timeMinutes(pc.getCurrentTime()) + " / " +timeMinutes(pc.getTotalDuration()));

		timeSlider.setOnMouseReleased((ev)->{
			double currentTime = pc.getCurrentTime()/pc.getTotalDuration()*100;
			if (Math.abs(currentTime - timeSlider.getValue()) > 0.1) {
				pc.seek(new Duration(pc.getTotalDuration()*(timeSlider.getValue()*10)));
			}
		});
		pc.currentTimeProperty().addListener((obs, oldv, newv)->{
			if (!timeSlider.isValueChanging() && !timeSlider.isPressed()) {
				timeSlider.setValue(newv.doubleValue()/pc.getTotalDuration()*100);
			}
			timeLabel.setText(timeMinutes(pc.getCurrentTime()) + " / " +timeMinutes(pc.getTotalDuration()));
		});



		sliderBox.getChildren().addAll(timeSlider, timeLabel);
		sliderBox.setMaxWidth(0.9*columnOneWidht);
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		HBox.setHgrow(timeLabel, Priority.ALWAYS);
		sliderBox.setAlignment(Pos.CENTER);

		return sliderBox;
	}



	private static String timeMinutes(double time) {

		String mS = "";
		String m = Integer.toString((int) time/60);
		String s = Integer.toString((int)time%60);
		if(s.length() == 1) {
			s = "0"+s;
		}

		mS = m + ":" +s;

		return mS;

	}





}
