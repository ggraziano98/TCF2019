package testers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import controllers.FileController;
import controllers.PlayerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Track;
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
		
		System.out.println(tracklist.size());
		
		for (int i = 0; i < tracklist.size(); i++) {
			System.out.println(tracklist.get(i).getPath().getFileName() + "\t\t\t" + i);
		}	// uso un loop for anzich� foreach per avere l'indice delle canzoni
		
		
		
		/*funziona ma non vogliamo cancellare una canzone a ogni test
		 * 
		try {
		fileC.deleteTrack(tracklist.getSongList().get(3));
		} 
		catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

		Path notATrack = Paths.get("notATrack.mp3");
		fileC.deleteTrack(notATrack);
		*/
		
		
//		System.out.println("\n \n \n provo la funzione shuffle");
//		tracklist.shuffleTrack();
//		cout(tracklist);
				
//		System.out.println("\n \n \n provo la funzione addTrack (aggiungo la track 0 al fondo della tracklist");
//		tracklist.addTrack(tracklist.get(0));
//		cout(tracklist);
//				
//		System.out.println("\n \n \n provo la funzione addTrackToPosition (aggiungo la track 0 all'inizio della tracklist");
//		tracklist.addTrackToPosition(0,tracklist.get(0));
//		cout(tracklist);
//		
//		System.out.println("\n \n \n provo la funzione remove track (rimuovo l'ultima track dalla tracklist)");
//		tracklist.removeTrack();
//		cout(tracklist);
//		
//		System.out.println("\n \n \n provo la funzione remove tracktoposition (rimuovo la prima track)");
//		tracklist.removeTrackToPosition(0);
//		cout(tracklist);
//		
//		System.out.println("\n \n \n provo la funzione cambio ordine tra elemento 2 e 5");
//		tracklist.changeOrder(2, 5);
//		cout(tracklist);
//
//		
//		/**
//		 * TODO il metodo funziona ma non funziona il handle metadata	
//		 */
//		System.out.println("\n \n \n provo la funzione totalduration");
//		System.out.println(tracklist.totalDuration());
		
		
		System.out.println("\n \n \n provo la funzione saveasplaylist");
		Tools.saveAsPlaylist(tracklist, "Nuova Playlist");
		
//		
		/**
		 * TODO trovare un modo per prendere il nome della playlist per esempio aggiungere un metadata alla classe tracklist
		 */
		TrackList playlist1 = new TrackList();
		playlist1 = Tools.readPlaylist("Nuova Playlist");
		System.out.println("\n\n\nNuova playlist:");
		Tools.cout(playlist1);
		
		System.out.println("\n\n\n");
//		Tools.deletePlaylist("Nuova Playlist");
		Tools.deletePlaylist("notAPlaylist");
		
		System.out.println("\n\n\n");
		System.out.println("Duration test");
		playlist1.totalDuration().addListener((obs, oldv, newv) -> {
			System.out.println("Duration: " + newv);
		});
		
		System.out.println("\n\n\n");
		System.out.println("Info sulla track n.3 della playlist: ");
		/*
		Field[] fields = playlist1.get(3).getClass().getDeclaredFields();
		for(Field f : fields){
		   try {
			   f.setAccessible(true);
			System.out.println(f.getName() + ": " + ((StringProperty) f.get(playlist1.get(3))).getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}}
		*/
		Track track = playlist1.get(3);
		System.out.println("album " + track.getAlbum().getValue() + "\n" +
				"artist " + track.getArtist().getValue() + "\n" +
				"genre " + track.getGenre().getValue() + "\n"+
				"year " + track.getYear().getValue() + "\n"+
				"duration " + track.getDuration().toMinutes() + "\n"+
				"image " + track.getImage() + "\n");		
		
		//provo il metodo getNamesSavedPlaylists
		ObservableList<String> provaplaylists = Tools.getNamesSavedPlaylists();
		for (String string : provaplaylists) {
			System.out.println(string);
		}
		
	}
}
