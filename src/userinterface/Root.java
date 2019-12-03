package userinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controllers.FileController;
import controllers.PlayerController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Track;
import models.TrackList;
import utils.Tools;





public class Root extends Application{

	// TODO chiudere le risorse una volta usate
	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();


	//TODO refactor
	static String mainDir = getMainDir();
	static TrackList mainTracklist = FileController.getFilesFromDir(Paths.get(mainDir));
	static PlayerController pc = new PlayerController(mainTracklist);
	static GridPane root = new GridPane();
	static TextField findText = new TextField();
	static ToggleGroup mainPanel = new ToggleGroup();


	public void start(Stage primaryStage) throws Exception {

		//GRIDPANE è il pane di livello piu alto, contiene tutti gli altri

		root.setVgap(10);
		root.setHgap(20);
		root.setPadding(new Insets(5, 10, 5, 10));
		root.setGridLinesVisible(true);

		//imposto i constraints del gridpane per settare anche il comportamento quando riscalo
		double columnOneWidht = 300;
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(columnOneWidht);
		column1.setMaxWidth(columnOneWidht);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMinWidth(600);
		column2.setHgrow(Priority.ALWAYS);
		root.getColumnConstraints().addAll(column1, column2);

		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(45);
		row1.setMaxHeight(45);
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(100);
		row2.setVgrow(Priority.ALWAYS);
		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(300);
		row3.setMaxHeight(300);
		root.getRowConstraints().addAll(row1, row2, row3);

		Scene scene = new Scene(root, 650, 600);













		RightPanels.panels();

		FileInputStream gioFile = new FileInputStream("files\\gio.png");
		Image gioImage = new Image(gioFile);
		primaryStage.getIcons().add(gioImage);

		GridPane pB = (GridPane) PlayerBuilder.playerBuilder(pc, findText, columnOneWidht);
		//aggiungo i pane al gridpane
		root.add(pB, 0, 2);
		root.add(FindStuff.findBox(), 0, 0);
		root.add(RightPanels.listsPane, 1, 0);
		root.add(PlaylistStuff.playlist(), 0, 1);

		root.add(RightPanels.albumsPane, 1, 1, 1, 2);
		root.add(RightPanels.artistsPane, 1, 1, 1, 2);
		root.add(RightPanels.songsPane, 1, 1, 1, 2);


		mainPanel.selectedToggleProperty().addListener((obs, oldv, newv) ->{
			if(newv != null) {
				((Node) oldv.getUserData()).setVisible(false);
			}
			((Node) newv.getUserData()).setVisible(true);
		});



		//aggiungo tutto alla window
		primaryStage.setTitle("Best player ever");
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(950);
		primaryStage.setMinHeight(650);
		primaryStage.show();





	}

	public static void main(String[] args) {
		launch(args);
	}








	public static String getMainDir() {
		Path mainDirFile = Paths.get("files", "mainDir.txt");
		String mainDir = "";
		try {
			BufferedReader br= Files.newBufferedReader(mainDirFile);
			mainDir = br.readLine();
			br.close();
		} catch (IOException e) {
			System.out.println("mainDir non selezionata");
		}

		while(mainDir == "" || mainDir == null || !Files.isDirectory(Paths.get(mainDir))) {
			TextInputDialog dialog = new TextInputDialog("Select Directory");
			dialog.setTitle("Enter main directory path");
			dialog.setContentText("example: C:\\Music");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				mainDir = result.get();
			}

			try {
				Files.createFile(mainDirFile);
				BufferedWriter bw= Files.newBufferedWriter(mainDirFile);
				bw.write(mainDir);
				bw.close();
			} catch (IOException e) {
				if (e instanceof FileAlreadyExistsException) {
					BufferedWriter bw;
					try {
						bw = Files.newBufferedWriter(mainDirFile);
						bw.write(mainDir);
						bw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else
					e.printStackTrace();
			}
		}
		System.out.println(mainDir);
		return mainDir;
	}



}
