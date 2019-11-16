package testers;

import java.nio.file.Path;
import java.nio.file.Paths;

import controllers.FileController;
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
		
		for (int i = 0; i < tracklist.getSongList().size(); i++) {
			System.out.println(tracklist.getSongList().get(i).getFileName() + "\t\t\t" + i);
		}	// uso un loop for anziché foreach per avere l'indice delle canzoni
		
		try {
		fileC.removeTrack(tracklist.getSongList().get(3));
		} 
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		Path notATrack = Paths.get("notATrack.mp3");
		fileC.removeTrack(notATrack);
		
		
		/**
		 * TODO implement metadata handling of Track
		tracklist.getSongList().forEach(tID -> {
			Track track = tID.toTrack();
			System.out.println(track.getTitle() + "\t\t" + track.getArtist() + "\t\t" + track.getAlbum());
		});
		*/
		
	}
	
}
