package userinterface;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
		playerV.setId("transaprent");


		return playerV;

	}

	static String grey = new String("-fx-background-color: white;");
	static String greyTrasp = new String("-fx-background-color: white; -fx-opacity:0.3");
	static String purple = new String("-fx-background-color: #6b0059;");
	static String purpleTrasp = new String("-fx-background-color: #6b0059; -fx-opacity:0.7");	
	static String black = new String("-fx-background-color: black;");
    static String blackTrasp = new String("-fx-background-color: black; -fx-opacity:0.4"); 

	public static HBox controlStuff(PlayerController pc, GridPane playerV, TextField findText) throws Exception{
		HBox playerButtons = new HBox(5);

		Double playerSmall = (double) Tools.DHEIGHTS[4]*0.28;
		Double playPause = (double) Tools.DHEIGHTS[4]*0.41;
		Double allV = playerSmall/3;
		Double allO = playerSmall/2;
		
		
		
		SVGPath playS = new SVGPath();
		playS.setContent("M371.7 238l-176-107c-15.8-8.8-35.7 2.5-35.7 21v208c0 18.4 19.8 29.8 35.7 21l176-101c16.4-9.1 16.4-32.8 0-42zM504 256C504 119 393 8 256 8S8 119 8 256s111 248 248 248 248-111 248-248zm-448 0c0-110.5 89.5-200 200-200s200 89.5 200 200-89.5 200-200 200S56 366.5 56 256z");
		SVGPath pause = new SVGPath();
		pause.setContent("M256 8C119 8 8 119 8 256s111 248 248 248 248-111 248-248S393 8 256 8zm0 448c-110.5 0-200-89.5-200-200S145.5 56 256 56s200 89.5 200 200-89.5 200-200 200zm96-280v160c0 8.8-7.2 16-16 16h-48c-8.8 0-16-7.2-16-16V176c0-8.8 7.2-16 16-16h48c8.8 0 16 7.2 16 16zm-112 0v160c0 8.8-7.2 16-16 16h-48c-8.8 0-16-7.2-16-16V176c0-8.8 7.2-16 16-16h48c8.8 0 16 7.2 16 16z");
		SVGPath next = new SVGPath();
		next.setContent("M384 44v424c0 6.6-5.4 12-12 12h-48c-6.6 0-12-5.4-12-12V291.6l-195.5 181C95.9 489.7 64 475.4 64 448V64c0-27.4 31.9-41.7 52.5-24.6L312 219.3V44c0-6.6 5.4-12 12-12h48c6.6 0 12 5.4 12 12z");
		SVGPath prev = new SVGPath();
		prev.setContent("M64 468V44c0-6.6 5.4-12 12-12h48c6.6 0 12 5.4 12 12v176.4l195.5-181C352.1 22.3 384 36.6 384 64v384c0 27.4-31.9 41.7-52.5 24.6L136 292.7V468c0 6.6-5.4 12-12 12H76c-6.6 0-12-5.4-12-12z");
		SVGPath shuffle = new SVGPath();
		shuffle.setContent("M504.971 359.029c9.373 9.373 9.373 24.569 0 33.941l-80 79.984c-15.01 15.01-40.971 4.49-40.971-16.971V416h-58.785a12.004 12.004 0 0 1-8.773-3.812l-70.556-75.596 53.333-57.143L352 336h32v-39.981c0-21.438 25.943-31.998 40.971-16.971l80 79.981zM12 176h84l52.781 56.551 53.333-57.143-70.556-75.596A11.999 11.999 0 0 0 122.785 96H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12zm372 0v39.984c0 21.46 25.961 31.98 40.971 16.971l80-79.984c9.373-9.373 9.373-24.569 0-33.941l-80-79.981C409.943 24.021 384 34.582 384 56.019V96h-58.785a12.004 12.004 0 0 0-8.773 3.812L96 336H12c-6.627 0-12 5.373-12 12v56c0 6.627 5.373 12 12 12h110.785c3.326 0 6.503-1.381 8.773-3.812L352 176h32z");
		
		final Region playShape = iconShape(playS, playPause, grey);
		final Region playShapeTrasp = iconShape(playS, playPause, greyTrasp);
		final Region pauseShape = iconShape(pause, playPause, grey);
		final Region pauseShapeTrasp = iconShape(pause, playPause, greyTrasp);
		final Region nextShape = iconShape(next, playerSmall, grey);
		final Region nextShapeTrasp = iconShape(next, playerSmall, greyTrasp);
		final Region prevShape = iconShape(prev, playerSmall, grey);
		final Region prevShapeTrasp = iconShape(prev, playerSmall, greyTrasp);
		final Region shuffleShape = iconShape(shuffle, playerSmall, grey);
		final Region shuffleShapeTrasp = iconShape(shuffle, playerSmall, greyTrasp);
		final Region shufflePShape = iconShape(shuffle, playerSmall, purple);
		final Region shufflePShapeTrasp = iconShape(shuffle, playerSmall, purpleTrasp);


		Button playButton = new Button();
		playButton.setGraphic(playShapeTrasp);
		playButton.setStyle("-fx-background-color: transparent");
		buttonMouseIconRegion(playButton, playShape, playShapeTrasp);
		Button prevButton = new Button();
		prevButton.setGraphic(prevShapeTrasp);
		prevButton.setStyle("-fx-background-color: transparent");
		buttonMouseIconRegion(prevButton, prevShape, prevShapeTrasp);
		Button nextButton = new Button();
		nextButton.setGraphic(nextShapeTrasp);
		nextButton.setStyle("-fx-background-color: transparent");
		buttonMouseIconRegion(nextButton, nextShape, nextShapeTrasp);
		Button shuffleButton = new Button("",shuffleShapeTrasp);
		shuffleButton.setStyle("-fx-background-color: transparent");
		buttonMouseIconRegion(shuffleButton, shuffleShape, shuffleShapeTrasp);
		
		SVGPath repeat = new SVGPath();
		repeat.setContent("M493.544 181.463c11.956 22.605 18.655 48.4 18.452 75.75C511.339 345.365 438.56 416 350.404 416H192v47.495c0 22.475-26.177 32.268-40.971 17.475l-80-80c-9.372-9.373-9.372-24.569 0-33.941l80-80C166.138 271.92 192 282.686 192 304v48h158.875c52.812 0 96.575-42.182 97.12-94.992.155-15.045-3.17-29.312-9.218-42.046-4.362-9.185-2.421-20.124 4.8-27.284 4.745-4.706 8.641-8.555 11.876-11.786 11.368-11.352 30.579-8.631 38.091 5.571zM64.005 254.992c.545-52.81 44.308-94.992 97.12-94.992H320v47.505c0 22.374 26.121 32.312 40.971 17.465l80-80c9.372-9.373 9.372-24.569 0-33.941l-80-80C346.014 16.077 320 26.256 320 48.545V96H161.596C73.44 96 .661 166.635.005 254.788c-.204 27.35 6.495 53.145 18.452 75.75 7.512 14.202 26.723 16.923 38.091 5.57 3.235-3.231 7.13-7.08 11.876-11.786 7.22-7.16 9.162-18.098 4.8-27.284-6.049-12.735-9.374-27.001-9.219-42.046z");
		SVGPath repeat1 = new SVGPath();
		repeat1.setContent("M493.544 181.463c11.956 22.605 18.655 48.4 18.452 75.75C511.339 345.365 438.56 416 350.404 416H192v47.495c0 22.475-26.177 32.268-40.971 17.475l-80-80c-9.372-9.373-9.372-24.569 0-33.941l80-80C166.138 271.92 192 282.686 192 304v48h158.875c52.812 0 96.575-42.182 97.12-94.992.155-15.045-3.17-29.312-9.218-42.046-4.362-9.185-2.421-20.124 4.8-27.284 4.745-4.706 8.641-8.555 11.876-11.786 11.368-11.352 30.579-8.631 38.091 5.571zM64.005 254.992c.545-52.81 44.308-94.992 97.12-94.992H320v47.505c0 22.374 26.121 32.312 40.971 17.465l80-80c9.372-9.373 9.372-24.569 0-33.941l-80-80C346.014 16.077 320 26.256 320 48.545V96H161.596C73.44 96 .661 166.635.005 254.788c-.204 27.35 6.495 53.145 18.452 75.75 7.512 14.202 26.723 16.923 38.091 5.57 3.235-3.231 7.13-7.08 11.876-11.786 7.22-7.16 9.162-18.098 4.8-27.284-6.049-12.735-9.374-27.001-9.219-42.046zm163.258 44.535c0-7.477 3.917-11.572 11.573-11.572h15.131v-39.878c0-5.163.534-10.503.534-10.503h-.356s-1.779 2.67-2.848 3.738c-4.451 4.273-10.504 4.451-15.666-1.068l-5.518-6.231c-5.342-5.341-4.984-11.216.534-16.379l21.72-19.939c4.449-4.095 8.366-5.697 14.42-5.697h12.105c7.656 0 11.749 3.916 11.749 11.572v84.384h15.488c7.655 0 11.572 4.094 11.572 11.572v8.901c0 7.477-3.917 11.572-11.572 11.572h-67.293c-7.656 0-11.573-4.095-11.573-11.572v-8.9z");
		
		final Region repeatShape = iconShape(repeat, playerSmall, grey);
        final Region repeatShapeTrasp = iconShape(repeat, playerSmall, greyTrasp);
        final Region repeat1Shape = iconShape(repeat1, playerSmall, purple);
        final Region repeat1ShapeTrasp = iconShape(repeat1, playerSmall, purpleTrasp);
        final Region repeatAllShape = iconShape(repeat, playerSmall, purple);
        final Region repeatAllShapeTrasp = iconShape(repeat, playerSmall, purpleTrasp);
       
        Text all = new Text("All");
        all.setFont(Font.font("Verdana", FontWeight.BOLD, Tools.DHEIGHTS[4]*0.28));
        
        final Region allShape = new Region();
        allShape.setShape(all);
        allShape.setMinSize(allO, allV);
        allShape.setPrefSize(allO, allV);
        allShape.setMaxSize(allO, allV);
        allShape.setStyle("-fx-background-color: #6b0059;");
        
        final Region allShapeTrasp = new Region();
        allShapeTrasp.setShape(all);
        allShapeTrasp.setMinSize(allO, allV);
        allShapeTrasp.setPrefSize(allO, allV);
        allShapeTrasp.setMaxSize(allO, allV);
        allShapeTrasp.setStyle("-fx-background-color: #6b0059; -fx-opacity: 0.4;");
        
        HBox repeatAllBox = new HBox(repeatAllShape, allShape);
        repeatAllBox.setAlignment(Pos.CENTER_LEFT);
        HBox repeatAllBoxTrasp = new HBox(repeatAllShapeTrasp, allShapeTrasp);
        repeatAllBoxTrasp.setAlignment(Pos.CENTER_LEFT);
		
		Button repeatButton = new Button();
		repeatButton.setStyle("-fx-background-color: transparent");
		repeatButton.setGraphic(repeatShapeTrasp);
		buttonMouseIconRegion(repeatButton, repeatShape, repeatShapeTrasp);
		
		

		shuffleButton.setOnAction(ev->{
		
			if (!MainApp.songQueueView.isVisible() && MainApp.pc.getCurrentTrack()!=null) {
			MainApp.shuffle = (MainApp.shuffle+1)%2;
			if (MainApp.shuffle == 1) {
				MainApp.pc.getTracklist().shuffle(pc.getCurrentTrack());
			    MainApp.pc.refreshCurrentInt();
			    shuffleButton.setGraphic(shufflePShape);
			    buttonMouseIconRegion(shuffleButton, shufflePShape, shufflePShapeTrasp);
			}
			if (MainApp.shuffle ==0) {
				MainApp.pc.setTracklist(MainApp.pc.previousTracklist);
				MainApp.pc.refreshCurrentInt();
				shuffleButton.setGraphic(shuffleShape);
				buttonMouseIconRegion(shuffleButton, shuffleShape, shuffleShapeTrasp);
			}}
			if (MainApp.songQueueView.isVisible() && MainApp.pc.getCurrentTrack()!=null) {
				MainApp.shuffle = (MainApp.shuffle+1)%2;
				MainApp.pc.getTracklist().shuffle(pc.getCurrentTrack());
			    MainApp.pc.refreshCurrentInt();
			    shuffleButton.setGraphic(shuffleShape);
				buttonMouseIconRegion(shuffleButton, shuffleShape, shuffleShapeTrasp);
			}
		});
	
	
		
		

		repeatButton.setOnAction(ev->{
			MainApp.repeat = (MainApp.repeat+1)%3;
				if(MainApp.repeat == 1) {     
					repeatButton.setGraphic(repeatAllBox);
					buttonMouseIconRegion(repeatButton, repeatAllBox, repeatAllBoxTrasp);
				}
				if(MainApp.repeat == 2) {     
					repeatButton.setGraphic(repeat1Shape);
					buttonMouseIconRegion(repeatButton, repeat1Shape, repeat1ShapeTrasp);
				}
				if(MainApp.repeat == 0)  {
	     			repeatButton.setGraphic(repeatShape);
					buttonMouseIconRegion(repeatButton, repeatShape, repeatShapeTrasp);
				}
			});
		

		AtomicBoolean play = new AtomicBoolean(false);
		playButton.setOnMouseClicked((e) -> {
			playPause(play, pc);
			setPlayingImageIconMouse(playButton, play, playShape, playShapeTrasp, pauseShape, pauseShapeTrasp);
		});
		pc.playingProperty().addListener((obs, oldv, newv)->{
			play.set(newv.booleanValue());
			setPlayingImageIcon(playButton, play, playShape, playShapeTrasp, pauseShape, pauseShapeTrasp);
		});

		prevButton.setOnMouseClicked((e) -> previousSong(pc));
		nextButton.setOnMouseClicked((e) -> nextSong(pc));


		

		playerButtons.getChildren().addAll(shuffleButton, prevButton, playButton, nextButton, repeatButton );

		

		// gestisco i keypresses
		GridPane root = MainApp.root;
		root.addEventFilter(KeyEvent.KEY_RELEASED, k -> {
			if(k.getTarget() != findText) {
				if ( k.getCode() == KeyCode.K){
					playPause(play, pc);
					setPlayingImageIcon(playButton, play, playShape, playShapeTrasp, pauseShape, pauseShapeTrasp);
				}
//				if ( k.getCode() == KeyCode.SPACE){
//					playPause(play, pc);
//					setPlayingImageIcon(playButton, play, playShape, playShapeTrasp, pauseShape, pauseShapeTrasp);
//				}
				
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
		
		Double volSize = (double) Tools.DHEIGHTS[4]*0.25;
		
		SVGPath volUp = new SVGPath();
		volUp.setContent("M215.03 71.05L126.06 160H24c-13.26 0-24 10.74-24 24v144c0 13.25 10.74 24 24 24h102.06l88.97 88.95c15.03 15.03 40.97 4.47 40.97-16.97V88.02c0-21.46-25.96-31.98-40.97-16.97zM480 256c0-63.53-32.06-121.94-85.77-156.24-11.19-7.14-26.03-3.82-33.12 7.46s-3.78 26.21 7.41 33.36C408.27 165.97 432 209.11 432 256s-23.73 90.03-63.48 115.42c-11.19 7.14-14.5 22.07-7.41 33.36 6.51 10.36 21.12 15.14 33.12 7.46C447.94 377.94 480 319.53 480 256zm-141.77-76.87c-11.58-6.33-26.19-2.16-32.61 9.45-6.39 11.61-2.16 26.2 9.45 32.61C327.98 228.28 336 241.63 336 256c0 14.38-8.02 27.72-20.92 34.81-11.61 6.41-15.84 21-9.45 32.61 6.43 11.66 21.05 15.8 32.61 9.45 28.23-15.55 45.77-45 45.77-76.88s-17.54-61.32-45.78-76.86z");
		SVGPath volDown = new SVGPath();
		volDown.setContent("M215.03 72.04L126.06 161H24c-13.26 0-24 10.74-24 24v144c0 13.25 10.74 24 24 24h102.06l88.97 88.95c15.03 15.03 40.97 4.47 40.97-16.97V89.02c0-21.47-25.96-31.98-40.97-16.98zm123.2 108.08c-11.58-6.33-26.19-2.16-32.61 9.45-6.39 11.61-2.16 26.2 9.45 32.61C327.98 229.28 336 242.62 336 257c0 14.38-8.02 27.72-20.92 34.81-11.61 6.41-15.84 21-9.45 32.61 6.43 11.66 21.05 15.8 32.61 9.45 28.23-15.55 45.77-45 45.77-76.88s-17.54-61.32-45.78-76.87z");
		SVGPath volMute = new SVGPath();
		volMute.setContent("M215.03 71.05L126.06 160H24c-13.26 0-24 10.74-24 24v144c0 13.25 10.74 24 24 24h102.06l88.97 88.95c15.03 15.03 40.97 4.47 40.97-16.97V88.02c0-21.46-25.96-31.98-40.97-16.97zM461.64 256l45.64-45.64c6.3-6.3 6.3-16.52 0-22.82l-22.82-22.82c-6.3-6.3-16.52-6.3-22.82 0L416 210.36l-45.64-45.64c-6.3-6.3-16.52-6.3-22.82 0l-22.82 22.82c-6.3 6.3-6.3 16.52 0 22.82L370.36 256l-45.63 45.63c-6.3 6.3-6.3 16.52 0 22.82l22.82 22.82c6.3 6.3 16.52 6.3 22.82 0L416 301.64l45.64 45.64c6.3 6.3 16.52 6.3 22.82 0l22.82-22.82c6.3-6.3 6.3-16.52 0-22.82L461.64 256z");
		
		final Region volUpShape = iconShape(volUp, volSize, grey);
		final Region volUpShapeTrasp = iconShape(volUp, volSize, greyTrasp);
		final Region volDownShape = iconShape(volDown, volSize, grey);
		final Region volDownShapeTrasp = iconShape(volDown, volSize, greyTrasp);
		final Region volMuteShape = iconShape(volMute, volSize, grey);
		final Region volMuteShapeTrasp = iconShape(volMute, volSize, greyTrasp);
				

		Slider volumeSlider = new Slider();
		volumeSlider.setMax(1);
		volumeSlider.setMin(0);
		volumeSlider.setValue(0.6);
		volumeSlider.setMaxWidth(Tools.DHEIGHTS[4]*1.15);
		volumeSlider.valueProperty().bindBidirectional(pc.volumeValueProperty());
	
		volBox.setOnScroll((ev)->{
			double volumeAmount = ev.getDeltaY();
			pc.setVolumeValue(pc.getVolumeValue() + volumeAmount/300);
		});
		
		
		AtomicBoolean muted = new AtomicBoolean(false);
		Button volumeButton = new Button("",volUpShapeTrasp);
		volumeButton.setStyle("-fx-background-color: transparent");
		buttonMouseIconRegion(volumeButton, volUpShape, volUpShapeTrasp);
		volumeButton.setOnMouseClicked((e) -> {
			if(muted.get()) {
				if(volumeSlider.getValue()>0.5) {
					volumeButton.setGraphic(volUpShape);
					buttonMouseIconRegion(volumeButton, volUpShape, volUpShapeTrasp);
				}
				else if(0.001<volumeSlider.getValue() || volumeSlider.getValue()<0.5) {
					volumeButton.setGraphic(volDownShape);
					buttonMouseIconRegion(volumeButton, volDownShape, volDownShapeTrasp);
				}
			}
			else {
				volumeButton.setGraphic(volMuteShape);
				buttonMouseIconRegion(volumeButton, volMuteShape, volMuteShapeTrasp);
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
						if(volumeSlider.getValue()>0.5) {
							volumeButton.setGraphic(volUpShapeTrasp);
							buttonMouseIconRegion(volumeButton, volUpShape, volUpShapeTrasp);
						}
						else if(0.001<volumeSlider.getValue() || volumeSlider.getValue()<0.5) {
							volumeButton.setGraphic(volDownShapeTrasp);
							buttonMouseIconRegion(volumeButton, volDownShape, volDownShapeTrasp);
						}
					}
					else {
						volumeButton.setGraphic(volMuteShapeTrasp);
						buttonMouseIconRegion(volumeButton, volMuteShape, volMuteShapeTrasp);
					}
					muted.set(!muted.get());
					volumeMute(muted, pc);
				}
			}
		});
		
		
		volumeSlider.valueProperty().addListener((obs, oldv, newv)->{
			if(volumeSlider.getValue()>0.5) {
				volumeButton.setGraphic(volUpShapeTrasp);
				buttonMouseIconRegion(volumeButton, volUpShape, volUpShapeTrasp);
			}
			else if(0.001<volumeSlider.getValue() || volumeSlider.getValue()<0.5) {
				volumeButton.setGraphic(volDownShapeTrasp);
				buttonMouseIconRegion(volumeButton, volDownShape, volDownShapeTrasp);
			}
			else {
				volumeButton.setGraphic(volMuteShapeTrasp);
				buttonMouseIconRegion(volumeButton, volMuteShape, volMuteShapeTrasp);
			}
		}
	);
		
		volBox.getChildren().addAll(volumeButton, volumeSlider);
		volBox.setAlignment(Pos.CENTER);
		
		return volBox;
	}

	public static void playPause(AtomicBoolean play, PlayerController pc) {
		try {
			if (play.get()) pc.pause();
			else pc.play();
		} catch (Exception e) {
		}
	
		

	}

	public static void nextSong(PlayerController pc) {
		try {
			pc.next();
		} catch (Exception e) {
		}
		
	}

	public static void previousSong(PlayerController pc) {
		try {
			pc.prev();
		} catch (Exception e) {
		}
	
	}


	public static void volumeMute(AtomicBoolean muted, PlayerController pc) {
		pc.setMuted(muted.get());
	}

	
	public static void setPlayingImageIcon(Button playButton, AtomicBoolean play, Region playView, Region playViewTrasp, Region pauseView, Region pauseViewTrasp) {
		if (!play.get()) {
			playButton.setGraphic(playViewTrasp);
			buttonMouseIconRegion(playButton, playView, playViewTrasp);
		}
		else {playButton.setGraphic(pauseViewTrasp);
	    	buttonMouseIconRegion(playButton, pauseView, pauseViewTrasp);
	}}
	
	public static void setPlayingImageIconMouse(Button playButton, AtomicBoolean play, Region playView, Region playViewTrasp, Region pauseView, Region pauseViewTrasp) {
		if (!play.get()) {
			playButton.setGraphic(playView);
			buttonMouseIconRegion(playButton, playView, playViewTrasp);
		}
		else {playButton.setGraphic(pauseView);
	    	buttonMouseIconRegion(playButton, pauseView, pauseViewTrasp);
	}}

	public static HBox songInfo (PlayerController pc) throws Exception {
		
		HBox songInfo = new HBox(10);
		
		Label songName = new Label();
		songName.setPadding(new Insets(0, 3, 0, 3));
		StringProperty songText = new SimpleStringProperty("");
		if (pc.getCurrentTrack() != null) {
			songText.set(pc.getCurrentTrack().getTitle());
		}
		else songText.set("Seleziona una canzone");
		songName.textProperty().bind(songText);
		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getCurrentTrack() != null) {
				songText.set(pc.getCurrentTrack().getTitle());
			}
			else songText.set("Seleziona una canzone");
		});
		songName.setFont(Font.font("nunito", FontWeight.BOLD, 0.32*Tools.DHEIGHTS[1]));

		songName.setAlignment(Pos.CENTER_LEFT);
		
		
		Label songArtist= new Label();
		songArtist.setPadding(new Insets(0, 3, 0, 3));
		StringProperty artistText = new SimpleStringProperty("");
		if (pc.getCurrentTrack() != null) {
			artistText.set(pc.getCurrentTrack().getArtist());
		}
		else artistText.set("");
		songArtist.textProperty().bind(artistText);
		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getCurrentTrack() != null) {
				artistText.set(pc.getCurrentTrack().getArtist());
			}
			else artistText.set("Seleziona una canzone");
		});
		songArtist.setAlignment(Pos.CENTER_LEFT);
		songArtist.setFont(Font.font(0.28*Tools.DHEIGHTS[1]));
		
		
		//richiamo l'immagine della canzone
		ImageView songView = new ImageView(Tools.DIMAGE);
		songView.setFitWidth(0.9*Tools.DHEIGHTS[4]);
		songView.setFitHeight(0.9*Tools.DHEIGHTS[4]);

		pc.currentIntProperty().addListener((obs, oldv, newv)->{
			if (pc.getCurrentTrack() != null) {
				try {
					pc.getCurrentTrack().setImageView(songView);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else songView.setImage(Tools.DIMAGE);
		});
		
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
	
	
	public static void buttonMouseIconRegion (Button b, Region v, Region t) {
		b.setOnMouseEntered((e) -> {
			b.setGraphic(v);
		});
		b.setOnMouseExited((e) -> {
			b.setGraphic(t);
		});
	
	}
	
	public static Region iconShape (SVGPath path, Double size, String color) {
		
		Region shape = new Region();
        shape.setShape(path);
        shape.setMinSize(size, size);
        shape.setPrefSize(size, size);
        shape.setMaxSize(size, size);
        shape.setStyle(color);
		
		return shape;
	}
	
	
	

	}



