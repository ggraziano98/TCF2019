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
import utils.Initialize;
import utils.Tools;





public class MainApp extends Application{

	// TODO chiudere le risorse una volta usate
	//TODO questo serve per adattare la dimensione della finestra alla definizione del display del pc
	//	GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//	int width = gd.getDisplayMode().getWidth();
	//	int height = gd.getDisplayMode().getHeight();

	static List<String> mainDirList = Initialize.getMainDir();
	static TrackList mainTracklist = FileController.getFilesFromDir(mainDirList);
	static PlayerController pc = new PlayerController(mainTracklist);

	static GridPane root;

	static GridPane playerPane;
	static HBox findBox;
	static ScrollPane playlistPane;
	static HBox buttonBox;

	static FlowPane albumsPane;
	static FlowPane artistsPane;
	static VBox songsPane;
	static VBox findView;
	static VBox songQueue;

	static TextField findText = new TextField();
	static ToggleGroup mainPanel = new ToggleGroup();


	public void start(Stage primaryStage) throws Exception {

		root = Root.rootPane();


		Scene scene = new Scene(root, 650, 600);


		RightPanels.panels();

		FileInputStream gioFile = new FileInputStream("files\\gio.png");
		Image gioImage = new Image(gioFile);
		gioFile.close();
		primaryStage.getIcons().add(gioImage);

		playerPane = PlayerBuilder.playerBuilder();
		findBox = FindStuff.findBox();
		playlistPane = PlaylistStuff.playlist();

		buttonBox = RightPanels.buttonBox;

		albumsPane = RightPanels.albumsPane;
		artistsPane = RightPanels.artistsPane;
		songsPane = RightPanels.songsPane;


		//aggiungo i pane al gridpane
		root.add(playerPane, 0, 2);
		root.add(findBox, 0, 0);
		root.add(buttonBox, 1, 0);
		root.add(playlistPane, 0, 1);

		root.add(albumsPane, 1, 1, 1, 2);
		root.add(artistsPane, 1, 1, 1, 2);
		root.add(songsPane, 1, 1, 1, 2);


		mainPanel.selectedToggleProperty().addListener((obs, oldv, newv) ->{
			if (findView != null) findView.setVisible(false);
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







	//TODO change into initialize
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
