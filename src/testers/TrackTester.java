package testers;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Track;
import models.TrackList;



/**
 * tester per la classe Track
 * @author giova
 *
 *
 * TODO use observables to communicate with stage, use actual mp3 file
 */
public class TrackTester extends Application{
	
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ImageView Experiment 1");
        
        Track track = new Track();
        System.out.println(System.getProperty("user.dir"));
        FileInputStream input = new FileInputStream("files\\image.png");
        Image image = new Image(input);
        track.setImage(image);
        ImageView imageView = new ImageView(track.getImage());

        HBox hbox = new HBox(imageView);

        Scene scene = new Scene(hbox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

	
	public static void main(String[] args) {
		 
		String artist = "artist"; 
		String album = "album"; 
		String genre = "genre"; 
		String year = "1998";
		Duration duration = Duration.seconds(165);

		Path path = Paths.get("path", "file.mp3");

		
		
		Track track = new Track(path);
		System.out.println("Created track " + track.getPath());
		
		track.setAlbum(album);
		track.setArtist(artist);
		track.setArtist(artist);
		track.setGenre(genre);
		track.setYear(year);
		track.setDuration(duration);

		
		track.setPath(Paths.get("newpath", "newfile.mp3"));
		
		System.out.println("Changed path of track to" + track.getPath()+
									"\n" + track.getArtist().getValue() + 
									"\n" + track.getAlbum().getValue() + 
									"\n" + track.getGenre().getValue() );
		
		System.out.println(track.getYear().getValue());
		System.out.println(track.getDuration().toMinutes() + " min");
		
//		Application.launch();
		Path path1 = Paths.get(".\\files\\TestSongs","Beethoven - The Very Best Of Beethoven (2CD) (naxos 2005) MP3 V0");
		Track track1 = new Track();
		track1.setPath(path1);
		System.out.println(track1.getPath());
		Media m = new Media(path1.toUri().toString());
//		System.out.println(m.getDuration());
//		System.out.println("finish");
	}

}
