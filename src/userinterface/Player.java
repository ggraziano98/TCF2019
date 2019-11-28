package userinterface;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;

public class Player extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		
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
		
		playButton.setOnAction((e) -> System.out.println("play/pause"));
		playButton.setTranslateX(-100);
		playButton.setTranslateY(100);
		
		
		
		prevButton.setOnAction((e) -> System.out.println("previous song"));
		prevButton.setTranslateX(-150);
		prevButton.setTranslateY(100);
		
		nextButton.setOnAction((e) -> System.out.println("next song"));
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
		timeLabel.setTextFill(Color.RED);
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
		
		Button volumeButton = new Button("",volumeView);
		volumeButton.setOnAction((e) -> System.out.println("sound/mute"));
		volumeButton.setTranslateX(100);
		volumeButton.setTranslateY(100);
		
		Slider volumeSlider = new Slider();
		volumeSlider.setMax(30);
		volumeSlider.setMin(0);
		volumeSlider.setMaxWidth(80);
		volumeSlider.setTranslateX(165);
		volumeSlider.setTranslateY(100);

		
		StackPane root = new StackPane();
		root.getChildren().add(playButton);
		root.getChildren().add(prevButton);
		root.getChildren().add(nextButton);
		root.getChildren().add(timeSlider);
		root.getChildren().add(timeLabel);
		root.getChildren().add(volumeButton);
		root.getChildren().add(volumeSlider);

		
		Scene scene = new Scene(root, 500, 300);
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
