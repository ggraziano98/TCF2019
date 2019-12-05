package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import models.Track;
import models.TrackList;
import userinterface.MainApp;


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
		try(FileInputStream nothingYetFile = new FileInputStream("files\\Player\\404.png")) {
			DIMAGE = new Image(nothingYetFile);
		} catch (Exception e) {
		}
	}
	
	
	public static Path DIRFILEPATH = Paths.get("files", "mainDir.txt");

	public static Path ALLSONGSFILEPATH = Paths.get("files", "allSongs.txt");




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
		} catch (IOException e) {
			if (e instanceof FileAlreadyExistsException) {
				try {
					Files.deleteIfExists(filePath);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				saveAsPlaylist(tracklist, playlistName);
			} else
				e.printStackTrace();
		}
		try (BufferedWriter bw= Files.newBufferedWriter(filePath)){
			tracklist.forEach(t->{
				try {
					bw.newLine();
					bw.append(t.getString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
			
	}


	public static void newPlaylist() {

		TextInputDialog dialog = new TextInputDialog("example: Playlist");
		String playlistName = new String();
		dialog.setTitle("Nuova playlist");
		dialog.setHeaderText("Inserire nome playlist");
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()){
			playlistName = result.get();
		}

		Path filePath = Paths.get("playlists", playlistName + ".txt");
		try {
			Files.createFile(filePath);
			MainApp.savedPlaylists.add(playlistName);

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
			String line;
			while ((line = br.readLine()) != null) {
				String[] arr = line.split("&tcf&");
				if (arr.length == 7) {
					Path path = Paths.get(arr[6]);
					if(Files.isRegularFile(path)){
						tracklist.addTrack(new Track(arr));;
					}
					else System.out.println(path.toString() + "\tNon è un file corretto");
				}
			}

			br.close();

		} catch (IOException e) {
			//TODO errore
			System.out.println("La playlist da leggere non esiste");
		}

		return tracklist;
	}

	/**invece di usare questi, si puo semplicemente riscrivere la playlist togliendo o aggiungendo track e poi salvando
	 * gli aggiornamenti in questo modo sono piu facili 
	 * 
	public static void addTrackToPlaylist (String playlistName, Track track) {
		try {
			TrackList tracklist = readPlaylist(playlistName);
			tracklist.addTrack(track);
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
	*/

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
		
		MainApp.savedPlaylists.remove(playlist);
	}


	//TODO handle tracklist errors
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
	
	
	public static void stackTrace(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An error occurred");

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
