package testers;

import java.nio.file.Path;
import java.nio.file.Paths;

import controllers.FileController;
import models.TrackList;
import utils.Tools;

public class FCTester {
	
	
	public static void main(String[] args) {
//		Path mainDir = Paths.get(System.getProperty("user.dir"), "FileController");
//		
//		tester(mainDir);
		
		Path path = Paths.get(".\\files\\TestSongs");
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
		
		/*
		try {
		fileC.deleteTrack(tracklist.getSongList().get(3));
		} 
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		Path notATrack = Paths.get("notATrack.mp3");
		fileC.deleteTrack(notATrack);
		*/
		
		
		System.out.println("\n \n \n provo la funzione shuffle");
		tracklist.shuffleTrack();
		cout(tracklist);
				
		System.out.println("\n \n \n provo la funzione addTrack");
		tracklist.addTrack(tracklist.getSongList().get(0));
		cout(tracklist);
				
		System.out.println("\n \n \n provo la funzione addTrackToPosition");
		tracklist.addTrackToPosition(0,tracklist.getSongList().get(0));
		cout(tracklist);
		
		System.out.println("\n \n \n provo la funzione remove track");
		tracklist.removeTrack();
		cout(tracklist);
		
		System.out.println("\n \n \n provo la funzione remove tracktoposition");
		tracklist.removeTrackToPosition(0);
		cout(tracklist);
		
		
		System.out.println("\n \n \n provo la funzione cambio ordine tra elemento 2 e 5");
		tracklist.changeOrder(2, 5);
		cout(tracklist);

		
		/**
		 * TODO il metodo funziona ma non funziona il handle metadata	
		 */
//		System.out.println("\n \n \n provo la funzione totalduration");
//		System.out.println(tracklist.totalDuration());
		
		System.out.println("\n \n \n provo la funzione saveasplaylist");
		Tools.saveAsPlaylist(tracklist, "Nuova Playlist");
		
		
		/**
		 * TODO trovare un modo per prendere il nome della playlist per esempio aggiungere un metadata alla classe tracklist
		 */
		TrackList playlist1 = new TrackList();
		playlist1 = Tools.readPlaylist("Nuova Playlist.txt");
		cout(playlist1);		
		
		Tools.deletePlaylist("Nuova Playlist");
		Tools.deletePlaylist("notAPlaylist");
	
		
		/**
		 * TODO implement metadata handling of Track
		tracklist.getSongList().forEach(tID -> {
			Track track = tID.toTrack();
			System.out.println(track.getTitle() + "\t\t" + track.getArtist() + "\t\t" + track.getAlbum());
		});
		*/
		
	}
	

	
	
	public static void cout(TrackList tracklist) {
		for (int i = 0; i < tracklist.getSongList().size(); i++) {
			System.out.println(tracklist.getSongList().get(i).getFileName() + "\t\t\t" + i);
		}	// uso un loop for anziché foreach per avere l'indice delle canzoni
		
	}
}
