package testers;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Track;



/**
 * tester per la classe Track
 * @author giova
 *
 *
 */
public class TrackTester extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {

		Path pathS = Paths.get("D:\\Programming\\Java\\TCF2019-progetto\\TCF2019\\.\\files\\TestSongs\\Beethoven\\Beethoven - The Very Best Of Beethoven (2CD) (naxos 2005) MP3 V0\\CD1\\String Quartet Op. 59 No. 3 Razumovsky - Allegro molto.mp3");
		primaryStage.setTitle("ImageView Experiment 1");

		Track track = new Track(pathS);
		FileInputStream input = new FileInputStream("files\\image.png");
		Image image = new Image(input);
		track.setImage(image);
		ImageView imageView = new ImageView(track.getImage());
		imageView.resize(40, 40);
		
		HBox hbox = new HBox();
	    hbox.setPadding(new Insets(10));
	    hbox.setSpacing(8);

	    Text title = new Text();
	    title.textProperty().bind(track.titleProperty());
	    Text album = new Text();
	    album.textProperty().bindBidirectional(track.albumProperty());
	    track.albumProperty().addListener((a, b, c)-> System.out.println("change: " + b));
	    Text artist = new Text();
	    artist.accessibleTextProperty().bind(track.artistProperty());
	    Text duration = new Text();
	    duration.textProperty().bind(new SimpleStringProperty(track.getDuration().toString()));
	    Text genre = new Text();
	    genre.textProperty().bind(track.genreProperty());
	    
	    
	    hbox.getChildren().addAll(title, artist, album, genre, duration);

		VBox vbox = new VBox();
		vbox.getChildren().addAll( hbox);

		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
	
	
	
}	
