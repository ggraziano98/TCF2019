package controllers;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.TrackList;
import utils.Tools;

public class FileController {
	private Path dirPath;
	public final String[] extensions = {"mp3"};


	/**
	 * Default constructor 
	 */
	public FileController() {
		this.dirPath = null;
	}


	/**
	 * Constructor with parameters for the class
	 * Can retrieve files with a specific extension, and modify their information
	 * Supported extensions: mp3
	 * 
	 * @param dirPath 		path alla directory in cui sono contenuti i file mp3
	 */
	public FileController(Path dirPath) {
		this.dirPath = dirPath;
	}



	/**
	 * setters and getters
	 */

	public Path getDirPath() {
		return this.dirPath;
	}


	public void setDirPath(Path dirPath) {
		this.dirPath = dirPath;
	}


	/**
	 * Function that returns all the TrackIDs in the directory through a TrackList
	 * 
	 * @return 		TrackList of the songs in the main directory
	 */
	public TrackList getFilesFromDir(Path path) {
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
			pathList.removeIf(p -> !this.isViableExtension(p));
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
	 * overloading di getFilesFromDir per avere this.dirPath come default directory 
	 * 
	 * @return 		TrackList of the songs in the main directory
	 */
	public TrackList getFilesFromDir() {
		return this.getFilesFromDir(this.dirPath);
	}


	/**
	 * metodo per eliminare una canzone
	 * 
	 * @param track		Track
	 */
	/*
	public void removeTrack(Track track) {
		this.removeTrack(track.toTrackID());
	}
	 */

	/**
	 * metodo per eliminare una canzone, passa un TrackID(overloada removeTrack(Track)
	 * 
	 * @param track 		TrackID
	 */
	public void removeTrack(Path path) {
		File file = path.toFile();

		// TODO UI.confirmation()

		String fileName = path.getFileName().toString();
		if(true) {
			if (file.delete()) System.out.println(fileName + ": file cancellato correttamente");
			else System.out.println("Non è stato possibile cancellare il file " + fileName); // TODO errorMessage
		}
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
	 * @return True se il l'estensione è contenuta in this.extensions
	 */
	public boolean isViableExtension(Path path) {
		return Arrays.stream(this.extensions).parallel().anyMatch(path.getFileName().toString()::contains);
		/* uso uno stream per controllare in parallelo tutte le estensioni permesse, cercando se una di queste è contenuta nel fileName */
	}
	public void foo() {}
}
