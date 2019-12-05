package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import controllers.FileController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import models.Track;
import models.TrackList;
import userinterface.MainApp;

public class Initialize {

	public static final void setMainDir() {
		try (BufferedReader br= Files.newBufferedReader(Tools.DIRFILEPATH)){
			br.lines().forEach(line->{
				if(!line.isEmpty() && Files.isDirectory(Paths.get(line))) {
					MainApp.mainDirList.add(line);
				}

			});
		} catch (IOException e) { 
			System.out.println("mainDir non selezionata");
		}

		while(MainApp.mainDirList.size() == 0) {
			if(!addDirectory()) {
				Platform.exit();
				break;
			}
		}
	}

	public static boolean addDirectory() {
		boolean setAttempt = false;

		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Select Directory");
		dialog.setHeaderText("Inserire il path alla directory selezionata");
		dialog.setContentText("L'operazione porebbe richiedere del tempo");

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == ButtonType.OK) {
				return dialog.getEditor().getText();
			}
			return null;
		});

		Optional<String> result = dialog.showAndWait();

		if (result.isPresent() && result.get() != null){
			setAttempt = true;
			if(Files.isDirectory(Paths.get(result.get())) && !result.get().isEmpty()){
				MainApp.mainDirList.add(result.get());
				setDirSongs(result.get());
				try (BufferedWriter bw= Files.newBufferedWriter(Tools.DIRFILEPATH)){
					MainApp.mainDirList.forEach(dir->{
						try {
							bw.write(dir);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Il path indicato non è una directory");
				alert.showAndWait();
				return addDirectory();
			}
		}
		return setAttempt;



	}


	public static TrackList getAllSongs() {
		TrackList tracklist = new TrackList();
		try {

			BufferedReader br= Files.newBufferedReader(Tools.ALLSONGSFILEPATH);
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
			System.out.println("Non è stato possibile caricare le canzoni");
		}

		return tracklist;
	}


	public static void setDirSongs(String dir) {
		Path dirPath = Paths.get(dir);

		TrackList tl = FileController.getFilesFromDir(dirPath);
		tl.setMetadata();

		try (BufferedWriter bw= Files.newBufferedWriter(Tools.ALLSONGSFILEPATH)){
			bw.newLine();
			bw.append(dir);
			tl.forEach(t->{
				try {
					bw.newLine();
					bw.append(t.getString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Completato");
		alert.setHeaderText("Canzoni impostate correttamente");
		alert.showAndWait();
	}

}
