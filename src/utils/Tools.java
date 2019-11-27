package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.TrackList;


public class Tools {

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



	/**
	 * 
	 * funzione che salva la tracklist come file di testo
	 * 
	 * @param tracklist
	 */
	public static void saveAsPlaylist(TrackList tracklist) {
		Path filePath = Paths.get("playlists", tracklist.toString() + ".txt");
		try {
			Files.createFile(filePath);
			BufferedWriter bw= Files.newBufferedWriter(filePath);
			for (Path path : tracklist.getSongList()) {
				bw.write(path.toString());
				bw.write("\n");
			}
			bw.close();
		} catch (IOException e) {
			if (e instanceof FileAlreadyExistsException) {
				System.out.println("file già esistente in " + filePath.toString() );
			} else 
				e.printStackTrace();
		}

	}



	/**
	 * 
	 * prende il nome del file di testo di una tracklist salvata e crea un oggetto tracklist con i path contenuti nel file di testo
	 * 
	 * @param String
	 * @return tracklist
	 */
	public static TrackList readPlaylist(String playlist) {
		TrackList tracklist = new TrackList();
		Path filePath = Paths.get("playlists", playlist );
		List<Path> list = new ArrayList<Path>();
		ObservableList<Path> songList = FXCollections.observableList(list);
		try {

			BufferedReader br= Files.newBufferedReader(filePath);
			String line = "";

			while ((line = br.readLine()) != null) {
				System.out.println(line);
				Path path = Paths.get(line);
				songList.add(path);;
			}	

			br.close();
			tracklist.setSongList(songList);		

		} catch (IOException e) { 
			e.printStackTrace();
		}

		/**
		 * TODO implementere il codice nel caso tracklist fosse vuota
		 */
		return tracklist;
	}



	/**
	 * 
	 * mi permette di eliminare una playlist in file di testo
	 * 
	 * @param playlist
	 */
	public static void deletePlaylist(String playlist) {
		Path filePath = Paths.get("playlists", playlist);
		try {
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
