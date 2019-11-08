package testers;

import java.nio.file.Path;
import java.nio.file.Paths;

import controllers.FileController;
import models.TrackID;
import models.TrackList;

public class FCTester {
	
	
	public static void main(String[] args) {
		Path mainDir = Paths.get(System.getProperty("user.dir"), "FileController");
		
		tester(mainDir);
		
		Path path = Paths.get("D:\\Programming\\Java\\TCF2019-progetto\\TCF2019\\files\\FIleController");
		tester(path);

	}
	
	
	public static void tester(Path path) {
		
		FileController fileC = new FileController(path);
		
		System.out.println("\nFileController loaded");
		
		TrackList tracklist = fileC.getFilesFromDir();
		
		System.out.println("Files retrieved");
		
		System.out.println(tracklist.getSongList().size());
		
		tracklist.getSongList().forEach(tID -> System.out.println(tID.getPath().getFileName() + "\t\t" + tID.getOrderID()));
		try {
		fileC.removeTrack(tracklist.getSongList().get(3));
		} 
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		TrackID notATrack = new TrackID(Paths.get("notATrack.mp3"));
		fileC.removeTrack(notATrack);

		
		System.out.println("Tracklist empty");
		
		
		/* TODO: implement TrackID.toTrack()
		 * implement metadata handling of Track
		tracklist.getSongList().forEach(tID -> {
			Track track = tID.toTrack();
			System.out.println(track.getTitle() + "\t\t" + track.getArtist() + "\t\t" + track.getAlbum());
		});
		*/
		
	}
	
}
