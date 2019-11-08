package controllers;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import models.Track;
import models.TrackID;
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
		List<Path> pathList = Tools.getFilesInDir(path);
		pathList.removeIf(p -> !this.isViableExtension(p));
		/* uso il metodo removeIf dell'ArrayList per rimuovere i file che non sono dell'estensione corretta */
		pathList.forEach(p -> tracklist.addTrack(new TrackID(p)));
		/* uso una lambda expression e il metodo forEach per iterare sulla pathList */
		return tracklist;
	}
	
	
	/**
	 * metodo per eliminare una canzone
	 * @param track
	 */
	public void removeTrack(Track track) {
		File file = track.getPath().toFile();
		
		// TODO UI.confirmation()
		
		if(true) {
			if (file.delete()) System.out.println("File cancellato correttamente");
			else System.out.println("Non è stato possibile cancellare il file"); // TODO errorMessage
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
}
