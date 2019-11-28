package userinterface;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;




public class Root extends Application {

	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();


	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		GridPane root = new GridPane();
		root.setVgap(10);
		root.setHgap(20);
		root.setPadding(new Insets(5, 10, 5, 10));
		root.setGridLinesVisible(true);

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


		HBox findHBox = new HBox();
		findHBox.setAlignment(Pos.CENTER);
		TextField findText = new TextField();
		findText.setMinWidth(250);
		Button findButton = new Button("cerca");
		findButton.setOnMouseClicked((e) -> {
			System.out.println(findText.getText());
		});
		findHBox.setOnKeyReleased((final KeyEvent KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.ENTER) System.out.println(findText.getText());
		});
		findHBox.getChildren().add(findText);	
		findHBox.getChildren().add(findButton);
		



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



		prevButton.setOnMouseClicked((e) -> System.out.println("previous song"));
		prevButton.setTranslateX(-150);
		prevButton.setTranslateY(100);

		nextButton.setOnMouseClicked((e) -> System.out.println("next song"));
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
			volumeMute(muted);
			if(muted.get()) {
				volumeButton.setGraphic(volumeView);
			}
			else {
				volumeButton.setGraphic(muteView);
			}
			muted.set(!muted.get());
			
		});
		
		
		
		
		volumeButton.setTranslateX(100);
		volumeButton.setTranslateY(100);

		Slider volumeSlider = new Slider();
		volumeSlider.setMax(30);
		volumeSlider.setMin(0);
		volumeSlider.setValue(15);
		volumeSlider.setMaxWidth(80);
		volumeSlider.setTranslateX(165);
		volumeSlider.setTranslateY(100);


		StackPane player = new StackPane();
		player.getChildren().add(playButton);
		player.getChildren().add(prevButton);
		player.getChildren().add(nextButton);
		player.getChildren().add(timeSlider);
		player.getChildren().add(timeLabel);
		player.getChildren().add(volumeButton);
		player.getChildren().add(volumeSlider);
		player.setStyle("-fx-background-color: #FF0000");
		
		
		HBox listsPane = new HBox();
		listsPane.setAlignment(Pos.CENTER);
		Button songsButton = new Button("songs");
		Label songs_artistsLabel = new Label("  ");
		Button artistsButton = new Button("artists");
		Label artists_albumsLabel = new Label("  ");
		Button albumsButton = new Button("albums");
		listsPane.getChildren().add(songsButton);
		listsPane.getChildren().add(songs_artistsLabel);
		listsPane.getChildren().add(artistsButton);
		listsPane.getChildren().add(artists_albumsLabel);
		listsPane.getChildren().add(albumsButton);
		
		
		VBox playlistsVbox = new VBox();
		Hyperlink playlist1 = new Hyperlink("playlist 1");
		Hyperlink playlist2 = new Hyperlink("playlist 2");
		playlistsVbox.getChildren().add(playlist1);
		playlistsVbox.getChildren().add(playlist2);
		
		
		
		root.add(player, 0, 2);
		root.add(findHBox, 0, 0);
		root.add(listsPane, 1, 0);
		root.add(playlistsVbox, 0, 1);
		
		
		


		Scene scene = new Scene(root, 650, 600);
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(650);
		primaryStage.show();

		
		scene.setOnKeyReleased((final KeyEvent KeyEvent) -> {
			if (KeyEvent.getCode() == KeyCode.M) {
				volumeMute(muted);
				if(muted.get()) volumeButton.setGraphic(volumeView);
				else volumeButton.setGraphic(muteView);
				muted.set(!muted.get());
				};
			if (KeyEvent.getCode() == KeyCode.P) {
				playPause(play);
				if (play.get()) playButton.setGraphic(playView);
				else playButton.setGraphic(pauseView);
				play.set(!play.get());
				};
		});
		

	}

	public static void main(String[] args) {
		launch(args);
	}


	public static void find() {
		
	}
	
	
	public static void playPause(AtomicBoolean play) {
		if (play.get()) System.out.println("Shut up!");
		else System.out.println("sing: che schifo i coefficienti di clebsch gordan");
		
	}

	
	
	public static void volumeMute(AtomicBoolean muted) {
		if(muted.get()) System.out.println("Let's Rock!");
		else System.out.println("booooooo");
	}







}










