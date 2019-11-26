package userinterface;

import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
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
		TextField findText = new TextField();
		findText.setMinWidth(250);
		Label cerca = new Label("cerca...");
		Button findButton = new Button("cerca");
		findHBox.getChildren().add(findText);	
		findHBox.getChildren().add(findButton);



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




		FileInputStream volumeFile = new FileInputStream("files\\Player\\volume.png");
		FileInputStream muteFile = new FileInputStream("files\\Player\\mute.png");
		Image volumeImage = new Image(volumeFile);
		ImageView volumeView = new ImageView(volumeImage);
		volumeView.setFitHeight(25);
		volumeView.setFitWidth(25);

		FileInputStream muteFIle = new FileInputStream("files\\Player\\mute.png");
		Image muteImage = new Image(muteFIle);
		ImageView muteView = new ImageView(muteImage);
		muteView.setFitHeight(25);
		muteView.setFitWidth(25);

		AtomicBoolean muted = new AtomicBoolean(false);
		Button volumeButton = new Button("",volumeView);
		volumeButton.setOnAction((e) -> {
			muted.set(!muted.get());
			volumeMute(muted);
			if(muted.get()) {
				volumeButton.setGraphic(muteView);
			}
			else {
				volumeButton.setGraphic(volumeView);
			}
			
		});
		
		volumeButton.setTranslateX(100);
		volumeButton.setTranslateY(100);

		Slider vsl = new Slider();
		vsl.setMax(30);
		vsl.setMin(0);
		vsl.setMaxWidth(80);
		vsl.setTranslateX(165);
		vsl.setTranslateY(100);


		StackPane player = new StackPane();
		player.getChildren().add(pp);
		player.getChildren().add(ps);
		player.getChildren().add(ns);
		player.getChildren().add(timesl);
		player.getChildren().add(timeLabel);
		player.getChildren().add(volumeButton);
		player.getChildren().add(vsl);

		player.setStyle("-fx-background-color: #FF0000");
		root.add(player, 0, 2);
		root.add(findHBox, 0, 0);
		GridPane.setValignment(findHBox, VPos.CENTER);


		Scene scene = new Scene(root, 650, 600);
		primaryStage.setTitle("Player");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(650);
		primaryStage.show();


	}

	public static void main(String[] args) {
		launch(args);
	}


	public static void find() {
		
	}

	
	
	public static void volumeMute(AtomicBoolean muted) {
		if(muted.get()) System.out.println("Let's Rock!");
		else System.out.println("booooooo");
	}







}










