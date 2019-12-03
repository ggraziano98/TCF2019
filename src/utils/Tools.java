package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import models.Track;
import models.TrackList;
import userinterface.Root;


public class Tools {

	public static final String DALBUM = "Album Sconosciuto";
	public static final String DYEAR = "Anno Sconosciuto";
	public static final String DARTIST = "Artista Sconosciuto";
	public static final String DGENRE = "Genere Sconosciuto";


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


	public static final double[] DWIDTHS= {300, 600};

	public static final double[] DHEIGHTS = {45, 100, 300};


	public static Image DIMAGE;

	static {
		try(FileInputStream playFile = new FileInputStream("files\\Player\\play.png")) {
			DIMAGE = new Image(playFile);
		} catch (Exception e) {
		}
	}




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


	public static void newPlaylist() {

		TextInputDialog dialog = new TextInputDialog("New Playlist");
		String playlistName = new String();
		dialog.setTitle("New playlist");
		dialog.setHeaderText("Inserire nome playlist");
		dialog.setContentText("example: PLaylist");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()){
			playlistName = result.get();
		}

		Path filePath = Paths.get("playlists", playlistName + ".txt");
		try {
			Files.createFile(filePath);

		} catch (IOException e) {
			if (e instanceof FileAlreadyExistsException) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("error1");
				alert.setHeaderText("nome playlist giï¿½ usato");
				alert.setContentText("Usare un altro nome");
				alert.showAndWait();
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
				if(Files.exists(path)){
					tracklist.addTrack(path);;
				}
				else System.out.println(path.toString() + "\tNon Ã¨ un file corretto");
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

	public static void addTrackToPlaylist (String playlistName, Track track) {
		try {
			TrackList tracklist = readPlaylist(playlistName);
			tracklist.addTrack(track);
			deletePlaylist(playlistName, false);
			saveAsPlaylist(tracklist, playlistName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("error1");
			alert.setHeaderText("nome playlist già usato");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	
	public static void RemoveTrackFromPlaylist (String playlistName, Track track) {
		try {
			TrackList tracklist = readPlaylist(playlistName);
			tracklist.RemoveTrack(track);
			deletePlaylist(playlistName, false);
			saveAsPlaylist(tracklist, playlistName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("error1");
			alert.setHeaderText("nome playlist già usato");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	public static void deletePlaylist(String playlist) {
		deletePlaylist(playlist, false);
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

			if (showMessage == true) {
				Alert selection = new Alert(AlertType.CONFIRMATION);
				selection.setTitle("Delete Playlist");
				selection.setHeaderText("Warning");
				selection.setContentText("Sei sicuro di voler eliminare la playlist " + playlist);

				Optional<ButtonType> result = selection.showAndWait();
				if (result.get() == ButtonType.OK){
					if(Files.deleteIfExists(filePath)) {
						Alert yes = new Alert(AlertType.CONFIRMATION);
						yes.setTitle("Eliminazione Playlist");
						yes.setContentText("Playlist eliminata correttament");
						yes.showAndWait();
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("error2");
						alert.setHeaderText("Errore generico");
						alert.setContentText("Provare a vedere se la playlist selezionata Ã¨ giÃ  stata cancellata");
						alert.showAndWait();
					}
				}

			} else {
				if(Files.deleteIfExists(filePath)) {
				}
				else System.out.println("Non esiste la playlist selezionata");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void DeleteTrack(Track track) {
		Alert selection = new Alert(AlertType.CONFIRMATION);
		selection.setTitle("Delete song");
		selection.setHeaderText("Warning");
		selection.setContentText("Sei sicuro di voler eliminare la canzone " + track.getTitle() + " definitivamente");

		Optional<ButtonType> result = selection.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				if(Files.deleteIfExists(track.getPath())) {
					Alert yes = new Alert(AlertType.CONFIRMATION);
					yes.setTitle("Eliminazione canzone");
					yes.setContentText("Canzone eliminata correttamente");
					yes.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("error2");
					alert.setHeaderText("Errore generico");
					alert.setContentText("Provare a vedere se la canzone selezionata è già stata eliminata");
					alert.showAndWait();
				}
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("error2");
				alert.setHeaderText("Errore generico");
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
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
		}	// uso un loop for anzichÃ© foreach per avere l'indice delle canzoni
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
