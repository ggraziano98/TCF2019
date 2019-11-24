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
		// TODO Auto-generated method stub
		
		FileInputStream img = new FileInputStream("files\\Player\\play.png");
		Image image = new Image(img);
		ImageView Img = new ImageView(image);
		Img.setFitHeight(25);
		Img.setFitWidth(25);
		
		FileInputStream paimg = new FileInputStream("files\\Player\\pause.png");
		Image paimage = new Image(paimg);
		ImageView paImg = new ImageView(paimage);
		paImg.setFitHeight(25);
		paImg.setFitWidth(25);
		
		FileInputStream pimg = new FileInputStream("files\\Player\\ps.png");
		Image pimage = new Image(pimg);
		ImageView pImg = new ImageView(pimage);
		pImg.setFitHeight(25);
		pImg.setFitWidth(25);
		
		FileInputStream nimg = new FileInputStream("files\\Player\\ns.png");
		Image nimage = new Image(nimg);
		ImageView nImg = new ImageView(nimage);
		nImg.setFitHeight(25);
		nImg.setFitWidth(25);
		
	
		
		Button pp = new Button("",Img);
		Button ps = new Button("",pImg);
		Button ns = new Button("",nImg);
		
		pp.setOnAction((e) -> System.out.println("play/pause"));
		pp.setTranslateX(-100);
		pp.setTranslateY(100);
		
		
		
		ps.setOnAction((e) -> System.out.println("previous song"));
		ps.setTranslateX(-150);
		ps.setTranslateY(100);
		
		ns.setOnAction((e) -> System.out.println("next song"));
		ns.setTranslateY(100);
		ns.setTranslateX(-50);
		
		Slider timesl = new Slider();
		timesl.setMax(100);
		timesl.setMin(0);
		timesl.setTranslateY(50);
		timesl.setMaxWidth(460);
		final Label timeLabel = new Label();
		timesl.valueProperty().addListener(new ChangeListener<Number>() {

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
		
		
		
		
		FileInputStream vimg = new FileInputStream("files\\Player\\volume.png");
		Image vimage = new Image(vimg);
		ImageView vImg = new ImageView(vimage);
		vImg.setFitHeight(25);
		vImg.setFitWidth(25);
		
		FileInputStream mimg = new FileInputStream("files\\Player\\mute.png");
		Image mimage = new Image(mimg);
		ImageView mImg = new ImageView(mimage);
		mImg.setFitHeight(25);
		mImg.setFitWidth(25);
		
		Button volume = new Button("",vImg);
		volume.setOnAction((e) -> System.out.println("sound/mute"));
		volume.setTranslateX(100);
		volume.setTranslateY(100);
		
		Slider vsl = new Slider();
		vsl.setMax(30);
		vsl.setMin(0);
		vsl.setMaxWidth(80);
		vsl.setTranslateX(165);
		vsl.setTranslateY(100);

		
		StackPane root = new StackPane();
		root.getChildren().add(pp);
		root.getChildren().add(ps);
		root.getChildren().add(ns);
		root.getChildren().add(timesl);
		root.getChildren().add(timeLabel);
		root.getChildren().add(volume);
		root.getChildren().add(vsl);

		
		Scene scene = new Scene(root, 500, 300);
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	

}
