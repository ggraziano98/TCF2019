package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Track;
import models.TrackList;


public class Tools {

	public static final StringProperty DALBUM = new SimpleStringProperty("Album Sconosciuto");
	public static final StringProperty DYEAR = new SimpleStringProperty("Anno Sconosciuto");
	public static final StringProperty DARTIST = new SimpleStringProperty("Artista Sconosciuto");
	public static final StringProperty DGENRE = new SimpleStringProperty("Genere Sconosciuto");
	
	
	public static final String TRANSBUTT = "    -fx-border-color: transparent;\n" + 
			"    -fx-border-width: 0;\n" + 
			"    -fx-background-radius: 0;\n" + 
			"    -fx-background-color: transparent;\n}";
	public static final String BOLDBUTT = "    -fx-border-color: transparent;\n" + 
			"    -fx-border-width: 0;\n" + 
			"    -fx-background-radius: 0;\n" + 
			"    -fx-background-color: transparent;\n" +
			"    -fx-font-weight: bold}";
	public static final String SELBUTT = "    -fx-border-color: red;\n" + 
			"    -fx-border-width: 0;\n" + 
			"    -fx-background-radius: 0;\n" + 
			"    -fx-background-color: #bebdbf;\n" +
			"    -fx-font-weight: bold;\n}";

	
	
	
	/**
	 * funzione che ritorna una lista di path ai file contenuti nella directory
	 * @param 		path
	 * @return 		List<Path>
	 */
	public static List<Path> getFilesInDir(Path path) {

		if (Files.exists(path)){

			try (Stream<Path> walk = Files.walk(path)) {

				List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
				/* filtro i risultati per avere solo i file e li aggiungo alla lista result */

				return result;

			} catch (IOException e) {
				e.printStackTrace();
				/* stampo la stacktrace in caso di errore */

				return Collections.emptyList();
			}
		}
		else {
			System.out.println("Il path specificato non esiste");
			return Collections.emptyList();
		}
	}

	/* uso try with per assicurarmi che lo stream sia chiuso */



	public static List<Path> getDirsInDir(Path path) {
		if (Files.exists(path)){
			try (Stream<Path> walk = Files.walk(path)) {

				List<Path> result = walk.filter(Files::isDirectory).collect(Collectors.toList());

				return result;

			} catch (IOException e) {
				e.printStackTrace();
				return Collections.emptyList();
			}
		}
		else {
			System.out.println("Il path specificato non esiste");
			return Collections.emptyList();
		}

	}



	/**TODO errore 
	 * funzione che salva la tracklist come file di testo
	 * 
	 * @param tracklist
	 */
	public static void saveAsPlaylist(TrackList tracklist, String playlistName) {
		Path filePath = Paths.get("playlists", playlistName + ".txt");
		try {
			Files.createFile(filePath);
			BufferedWriter bw= Files.newBufferedWriter(filePath);
			for (Track track : tracklist) {
				bw.write(track.getPath().toString());
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			if (e instanceof FileAlreadyExistsException) {
				deletePlaylist(playlistName, false);
				saveAsPlaylist(tracklist, playlistName);
			} else 
				e.printStackTrace();
		}

	}



	/**TODO errore
	 * 
	 * prende il nome del file di testo di una tracklist salvata e crea un oggetto tracklist con i path contenuti nel file di testo
	 * 
	 * @param String
	 * @return tracklist
	 */
	public static TrackList readPlaylist(String playlist) {
		TrackList tracklist = new TrackList();
		Path filePath = Paths.get("playlists", playlist + ".txt");
		try {

			BufferedReader br= Files.newBufferedReader(filePath);
			String line = "";

			while ((line = br.readLine()) != null) {
				Path path = Paths.get(line);
				tracklist.add(new Track(path));;
			}	

			br.close();	

		} catch (IOException e) { 
			System.out.println("La playlist da leggere non esiste");
		}

		/**
		 * TODO implementere il codice nel caso tracklist fosse vuota
		 */
		return tracklist;
	}



	public static void deletePlaylist(String playlist) {
		deletePlaylist(playlist, true);
	}


	/**
	 * 
	 * mi permette di eliminare una playlist in file di testo
	 * 
	 * TODO messaggio completamento operazione
	 * 
	 * @param playlist
	 */
	public static void deletePlaylist(String playlist, boolean showMessage) {
		Path filePath = Paths.get("playlists", playlist + ".txt");
		try {
			if(Files.deleteIfExists(filePath)) {
				if(showMessage) System.out.println("Playlist " + playlist + " eliminata");
			}
			else System.out.println("Non esiste la playlist selezionata");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**

	 * Dobbiamo pulire l'url altrimenti javafx non lo riconosce
	 *
	 * @param uri
	 */
	public static String cleanURL(String url) {
		url = url.replace("\\", "/");
		url = url.replaceAll(" ", "%20");
		url = url.replace("[", "%5B");
		url = url.replace("]", "%5D");
		url = "file:///" + url;
		return url;

	}



	/**
	 * tool per debugging, printa il contenuto di una playlist
	 * TODO togliere quando non serve piu
	 * 
	 * @param tracklist
	 */
	public static void cout(TrackList tracklist) {
		for (int i = 0; i < tracklist.size(); i++) {
			System.out.println(tracklist.get(i).getPath().getFileName() + "\t\t\t" + i);
		}	// uso un loop for anziché foreach per avere l'indice delle canzoni

	}
	
	
	public static ObservableList<String> getNamesSavedPlaylists(){
		List<String> namesarray = new ArrayList<String>();
		ObservableList<String> nameplaylists = FXCollections.observableList(namesarray);
		File directoryPath = new File("playlists");

		//list all txt files

		for (File file : directoryPath.listFiles()) {
			if (file.getName().endsWith(".txt")) {
				nameplaylists.add(file.getName().replace(".txt", ""));
			} else {
				System.out.println(file.getName() + " is not a readable playlist");
			}

		}

		return nameplaylists;


	}

}
