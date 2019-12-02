package controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import models.Track;
import models.TrackList;
import utils.Tools;

/**
 * @author Umberto
 *
 */
public class FileController {

	public static final String[] extensions = {"mp3"};

	public static TrackList getFilesFromDir(List<String> dirList) {
		TrackList tracklist = new TrackList();
		
		dirList.forEach(dir->{
			getFilesFromDir(Paths.get(dir)).forEach(t->{
				tracklist.add(t);
			});;
		});
		
		return tracklist;
	}


	/**
	 * Function that returns all the Tracks in the directory through a TrackList
	 * 
	 * 
	 * @return 	TrackList of the songs in the main directory
	 */
	public static TrackList getFilesFromDir(Path path) {
		TrackList tracklist = new TrackList();
		List<Path> pathList;
		try {
			pathList = Tools.getFilesInDir(path);
		}
		catch (Exception e) {
			e.printStackTrace();
			pathList = new ArrayList<Path>();
		}
		try {
			pathList.removeIf(p -> !isViableExtension(p));
			/* uso il metodo removeIf dell'ArrayList per rimuovere i file che non sono dell'estensione corretta */

			pathList.forEach(p -> {
				tracklist.addTrack(p);
			});
		}
		catch (NullPointerException e) {
			System.out.println("Nullpointer exception handled: " + e.getMessage());
			return TrackList.emptyTrackList();
		}
		/* uso una lambda expression e il metodo forEach per iterare sulla pathList */
		return tracklist;
	}

	/**
	 * metodo per eliminare una canzone passando una track (futureproofing)
	 * @param track		Track
	 */
	public static void deleteTrack(Track track) {
		deleteTrack(track.getPath());
	}


	/**
	 * metodo per eliminare una canzone, passa un path della track(overloada removeTrack(Track)
	 * 
	 * @param path  Path assoluto alla canzone
	 */
	public static void deleteTrack(Path path) {
		File file = path.toFile();
		String fileName = path.getFileName().toString();

		//TODO controllare che funzioni

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert confirm = new Alert(AlertType.CONFIRMATION);
				confirm.setTitle("Conferma eliminazione");
				confirm.setHeaderText("Stai per eliminare \"" + fileName + "\"");
				confirm.setContentText("Confermi?");

				Optional<ButtonType> result = confirm.showAndWait();
				if (result.get() == ButtonType.OK){
					System.out.println(file.getPath());
					if (file.delete()) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Success!");
						alert.setContentText("Canzone eliminata correttamente");
						alert.showAndWait();
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Errore");
						alert.setHeaderText("Errore durante l'eliminazione della canzone");
						alert.setContentText("Non � stato possibile eliminare \"" + fileName + "\"");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Operazione annullata");
					alert.setContentText("Operazione annullata");
					alert.showAndWait();
				}
			}
		});
	}

	/*
	public void editInfo(Track track, String info) {
		// TODO get info screen

		// TODO input message

		Scanner input = new Scanner(System.in);

		switch(info) {
		case "artist":
			track.setArtist(input.toString());
		case "album":
			track.setAlbum(input.toString());
		case "title":
			track.setTitle(input.toString());
		case "genre":
			track.setGenre(input.toString());
		case "year":
			track.setYear(input);
		case "Duration":
			track.setDuration(input);
		case "image":
			track.setImage(input);
		}
	}
	 */

	/**
	 * Controllo se un file è della giusta estensione
	 * 
	 * @param path
	 * @return True se il l'estensione è contenuta in extensions
	 */
	public static boolean isViableExtension(Path path) {
		return Arrays.stream(extensions).parallel().anyMatch(path.getFileName().toString()::contains);
		/* uso uno stream per controllare in parallelo tutte le estensioni permesse, cercando se una di queste è contenuta nel fileName */
	}
}
