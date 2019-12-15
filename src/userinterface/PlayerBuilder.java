package userinterface;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import controllers.PlayerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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



		ColumnConstraints colA = new ColumnConstraints();
		colA.setPercentWidth(30);
		ColumnConstraints colB = new ColumnConstraints();
		colB.setPercentWidth(50);
		ColumnConstraints colC = new ColumnConstraints();
		colC.setPercentWidth(20);

		playerV.getColumnConstraints().addAll(colA, colB, colC);

		double col = Tools.DHEIGHTS[4];

		RowConstraints rowConstraint = new RowConstraints(col, col, col);
		rowConstraint.setValignment(VPos.CENTER);
		playerV.getRowConstraints().add(rowConstraint);

		// TODO togliere
		playerV.setGridLinesVisible(true);

		HBox playerButtons = controlStuff(pc, playerV, findText);
		HBox sliderBox = songTime(pc);
		sliderBox.setAlignment(Pos.CENTER);
		HBox songInfo = songInfo(pc);
		VBox centralBox = new VBox();
		centralBox.getChildren().addAll(playerButtons, sliderBox);
		HBox volumeBox = volumeBox(pc, findText);

		
		playerButtons.setAlignment(Pos.CENTER);
		//playerV.add(songName, 0, 0);
		playerV.add(songInfo, 0, 0);
		playerV.add(centralBox, 1, 0);
		playerV.add(volumeBox, 2, 0);
		
		playerV.setGridLinesVisible(false);
		playerV.setStyle("-fx-background-color: #F4D03F");


		return playerV;

	}



	public static HBox controlStuff(PlayerController pc, GridPane playerV, TextField findText) throws Exception{
		HBox playerButtons = new HBox(5);

		FileInputStream playFile = new FileInputStream("files\\Player\\play.png");
		Image playImage = new Image(playFile);
		playFile.close();
		ImageView playView = new ImageView(playImage);
		playView.setFitHeight(30);
		playView.setFitWidth(30);

		FileInputStream pauseFile = new FileInputStream("files\\Player\\pause.png");
		Image pauseImage = new Image(pauseFile);
		pauseFile.close();
		ImageView pauseView = new ImageView(pauseImage);
		pauseView.setFitHeight(30);
		pauseView.setFitWidth(30);

		FileInputStream prevFile = new FileInputStream("files\\Player\\ps.png");
		Image prevImage = new Image(prevFile);
		prevFile.close();
		ImageView prevView = new ImageView(prevImage);
		prevView.setFitHeight(18);
		prevView.setFitWidth(18);
		ImageView prevViewTrasp = new ImageView(prevImage);
		prevViewTrasp.setStyle("-fx-opacity: 0.7");
		prevViewTrasp.setFitHeight(18);
		prevViewTrasp.setFitWidth(18);

		FileInputStream nextFile = new FileInputStream("files\\Player\\ns.png");
		Image nextImage = new Image(nextFile);
		nextFile.close();
		ImageView nextView = new ImageView(nextImage);
		nextView.setFitHeight(18);
		nextView.setFitWidth(18);
		ImageView nextViewTrasp = new ImageView(nextImage);
		nextViewTrasp.setStyle("-fx-opacity: 0.7");
		nextViewTrasp.setFitHeight(18);
		nextViewTrasp.setFitWidth(18);

		FileInputStream shuffleFile = new FileInputStream("files\\Player\\shuffle.png");
		Image shuffleImage = new Image(shuffleFile);
		shuffleFile.close();
		ImageView shuffleView = new ImageView(shuffleImage);
		shuffleView.setFitHeight(18);
		shuffleView.setFitWidth(18);

		FileInputStream repeatFile = new FileInputStream("files\\Player\\repeat.png");
		Image repeatImage = new Image(repeatFile);
		repeatFile.close();
		ImageView repeatView = new ImageView(repeatImage);
		repeatView.setFitHeight(18);
		repeatView.setFitWidth(18);

		Button playButton = new Button("",playView);
		playButton.setStyle("-fx-background-color: transparent");
		Button prevButton = new Button("",prevViewTrasp);
		prevButton.setStyle("-fx-background-color: transparent");
		buttonMouse(prevButton, prevView, prevViewTrasp);
		Button nextButton = new Button("",nextViewTrasp);
		nextButton.setStyle("-fx-background-color: transparent");
		buttonMouse(nextButton, nextView, nextViewTrasp);
		Button shuffleButton = new Button("",shuffleView);
		shuffleButton.setStyle("-fx-background-color: transparent");
		Button repeatButton = new Button("",repeatView);
		repeatButton.setStyle("-fx-background-color: transparent");




		shuffleButton.setOnAction(ev->{
			MainApp.pc.getTracklist().shuffle(pc.getCurrentTrack());
			MainApp.pc.refreshCurrentInt();
		});

		repeatButton.setOnAction(ev->{
			MainApp.repeat = (MainApp.repeat+1)%3;
		});


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


		

		playerButtons.getChildren().addAll(shuffleButton, prevButton, playButton, nextButton, repeatButton );

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
				
				if ( k.getCode() == KeyCode.L) nextSong(pc);
				if ( k.getCode() == KeyCode.J) previousSong(pc);
				if ( k.getCode() == KeyCode.O) pc.seek(Duration.seconds(pc.getCurrentTime() + 10));
				if ( k.getCode() == KeyCode.I) pc.seek(Duration.seconds(pc.getCurrentTime() - 10));
			}

		});

		return playerButtons;

	}
	
	
	public static HBox volumeBox(PlayerController pc, TextField findText) throws Exception{
		
		HBox volBox = new HBox();
		
				FileInputStream volumeHighFile = new FileInputStream("files\\Player\\volumeHigh.png");
				Image volumeHighImage = new Image(volumeHighFile);
				volumeHighFile.close();
				ImageView volumeHighView = new ImageView(volumeHighImage);
				volumeHighView.setFitHeight(20);
				volumeHighView.setFitWidth(20);
				
				FileInputStream volumeLowFile = new FileInputStream("files\\Player\\volumeLow.png");
				Image volumeLowImage = new Image(volumeLowFile);
				volumeLowFile.close();
				ImageView volumeLowView = new ImageView(volumeLowImage);
				volumeLowView.setFitHeight(20);
				volumeLowView.setFitWidth(20);

				FileInputStream muteFile = new FileInputStream("files\\Player\\mute.png");
				Image muteImage = new Image(muteFile);
				muteFile.close();
				ImageView muteView = new ImageView(muteImage);
				muteView.setFitHeight(20);
				muteView.setFitWidth(20);
				

				

				Slider volumeSlider = new Slider();
				volumeSlider.setMax(1);
				volumeSlider.setMin(0);
				volumeSlider.setValue(0.6);
				volumeSlider.setMaxWidth(80);
				volumeSlider.valueProperty().bindBidirectional(pc.volumeValueProperty());

				volBox.setOnScroll((ev)->{
					double volumeAmount = ev.getDeltaY();
					pc.setVolumeValue(pc.getVolumeValue() + volumeAmount/300);
				});
				
				
				AtomicBoolean muted = new AtomicBoolean(false);
				Button volumeButton = new Button("",volumeHighView);
				volumeButton.setStyle("-fx-background-color: transparent");
				volumeButton.setOnMouseClicked((e) -> {
					if(muted.get()) {
						if(volumeSlider.getValue()>0.5) volumeButton.setGraphic(volumeHighView);
						else if(0<volumeSlider.getValue() || volumeSlider.getValue()<0.5) volumeButton.setGraphic(volumeLowView);
					}
					else {
						volumeButton.setGraphic(muteView);
					}
					muted.set(!muted.get());
					volumeMute(muted, pc);

				});
				
				//keypress per il muto
				GridPane root = MainApp.root;
				root.addEventFilter(KeyEvent.KEY_RELEASED, k -> {
					if(k.getTarget() != findText) {
						if ( k.getCode() == KeyCode.M) {
							if(muted.get()) {
								if(volumeSlider.getValue()>0.5) volumeButton.setGraphic(volumeHighView);
								else if(0<volumeSlider.getValue() || volumeSlider.getValue()<0.5) volumeButton.setGraphic(volumeLowView);
							}
							else {
								volumeButton.setGraphic(muteView);
							}
							muted.set(!muted.get());
							volumeMute(muted, pc);
						}
				}
					});
				
				
				volumeSlider.valueProperty().addListener((obs, oldv, newv)->{
					if(volumeSlider.getValue()>0.5) volumeButton.setGraphic(volumeHighView);
					else if(0<volumeSlider.getValue() || volumeSlider.getValue()<0.5) volumeButton.setGraphic(volumeLowView);
					else if(volumeSlider.getValue() == 0) volumeButton.setGraphic(muteView);
				}
			);
				
				
				volBox.getChildren().addAll(volumeButton, volumeSlider);
				volBox.setAlignment(Pos.CENTER);
				
				return volBox;
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

	public static HBox songInfo (PlayerController pc) throws Exception {
		
		HBox songInfo = new HBox(10);
		
		//TODO songtext che gira (o almeno che ci sta dentro
		Label songName = new Label();
		songName.setPadding(new Insets(0, 3, 0, 3));
		StringProperty songText = new SimpleStringProperty("");
		if (pc.getTracklist().getSize()>0) {
			songText.set(pc.getCurrentTrack().getTitle());
		}
		else songText.set("Seleziona una canzone");
		songName.textProperty().bind(songText);
		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {
				songText.set(pc.getCurrentTrack().getTitle());
			}
			else songText.set("Seleziona una canzone");
		});
		songName.setFont(Font.font("", FontWeight.BOLD, 12));
		songName.setAlignment(Pos.CENTER_LEFT);
		
		
		Label songArtist= new Label();
		songArtist.setPadding(new Insets(0, 3, 0, 3));
		StringProperty artistText = new SimpleStringProperty("");
		if (pc.getTracklist().getSize()>0) {
			artistText.set(pc.getCurrentTrack().getArtist());
		}
		else artistText.set("");
		songArtist.textProperty().bind(artistText);
		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {
				artistText.set(pc.getCurrentTrack().getArtist());
			}
			else artistText.set("Seleziona una canzone");
		});
		songArtist.setAlignment(Pos.CENTER_LEFT);
		songArtist.setFont(Font.font(12));
		
		
		//richiamo l'immagine della canzone
		ImageView songView = new ImageView(Tools.DIMAGE);
		songView.setFitWidth(0.9*Tools.DHEIGHTS[4]);
		songView.setFitHeight(0.9*Tools.DHEIGHTS[4]);

		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getTracklist().getSize()>0) {
				try {
					pc.getCurrentTrack().setImageView(songView);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else songView.setImage(Tools.DIMAGE);});
		
		VBox songStrings = new VBox();
		songStrings.setAlignment(Pos.CENTER);
		songStrings.getChildren().addAll(songName, songArtist);
		
		Label space = new Label(" ");
		songInfo.getChildren().addAll(space, songView, songStrings);
		songInfo.setAlignment(Pos.CENTER_LEFT);
		
		return songInfo;
	}

	


	public static HBox songTime(PlayerController pc) {
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
		sliderBox.setMinWidth(Tools.DWIDTHS[0]);
		//sliderBox.setMaxWidth(Tools.DWIDTHS[0]);
		sliderBox.setPrefWidth(0.3*MainApp.root.getWidth());
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		HBox.setHgrow(timeLabel, Priority.ALWAYS);
		sliderBox.setAlignment(Pos.CENTER);

		return sliderBox;
	}


	/**
	 * A formatted string for time
	 * @param time in seconds
	 * @return
	 */
	public static String timeMinutes(double time) {
		
		String mS = "";
		
		int hours = (int) time/3600;
		if(time/3600 > 1) mS+=Integer.toString(hours)+":";
		String m = Integer.toString((int) (time-hours*3600)/60);
		String s = Integer.toString((int)time%60);
		if(s.length() == 1) {
			s = "0"+s;
		}

		mS += m + ":" +s;

		return mS;

	}



	public static void buttonMouse (Button b, ImageView v, ImageView t) {
		b.setOnMouseEntered((e) -> {
			b.setGraphic(v);
		});
		b.setOnMouseExited((e) -> {
			b.setGraphic(t);
		});
	}


}
