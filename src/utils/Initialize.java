package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.TextInputDialog;

public class Initialize {
	
	public static final List<String> getMainDir() {
		Path mainDirFile = Paths.get("files", "mainDir.txt");
		List<String> mainDirList = new ArrayList<String>() ;
		try (BufferedReader br= Files.newBufferedReader(mainDirFile)){
			br.lines().forEach(line->mainDirList.add(line));
		} catch (IOException e) { 
			System.out.println("mainDir non selezionata");
		}

		while(mainDirList.size() == 0 || mainDirList.get(0) == null || !Files.isDirectory(Paths.get(mainDirList.get(0)))) {
			TextInputDialog dialog = new TextInputDialog("example: C:\\Music");
			dialog.setTitle("Select Directory");
			dialog.setHeaderText("Inserire il path alla directory selezionata");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				mainDirList.add(result.get());
			}
			try {
				Files.createFile(mainDirFile);
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			try (BufferedWriter bw= Files.newBufferedWriter(mainDirFile)){
				mainDirList.forEach(dir->{
					try {
						bw.write(dir + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			} catch (IOException e) {
					e.printStackTrace();
			}
		}
		return mainDirList;
	}
	
//	public static final loadPlaylists() {
//		
//	}

}
