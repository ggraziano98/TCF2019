package userinterface;

import java.io.FileInputStream;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Track;
import models.TrackList;
import utils.Tools;

public class PlaylistStuff {


	/**
	 * Usato per definire lo scrollpane a sinistra in cui si vedono tutte le playlist salvate
	 *
	 * @return ScrollPane playlist
	 */
	public static VBox playlist() throws Exception{

		VBox playlistMain = new VBox();


		HBox addBox = new HBox();
		addBox.setMinWidth(0.9*Tools.DWIDTHS[0]);
		addBox.setMaxWidth(0.9*Tools.DWIDTHS[0]);
		Text leTuePlaylists = new Text("  Le tue playlists");
		leTuePlaylists.setTextAlignment(TextAlignment.LEFT);
		leTuePlaylists.setFont(Font.font("Cavolini", FontPosture.ITALIC, 15));


		FileInputStream addFile = new FileInputStream("files\\player\\add.png");
		Image addImage = new Image(addFile);
		ImageView addView = new ImageView(addImage);
		addView.setFitHeight(20);
		addView.setFitWidth(20);
		Button addButton = new Button("",addView);
		addBox.getChildren().addAll(leTuePlaylists, addButton);
		addButton.setTranslateX(Tools.DWIDTHS[0]/2-10);
		addButton.setOnMouseClicked((e) -> {
				Tools.newPlaylist();
			});






		VBox playlistsVbox = MainApp.playlistsVbox;;

		ScrollPane scroll = new ScrollPane();
		scroll.setContent(playlistsVbox);

		scroll.setFitToHeight(true);


		MainApp.savedPlaylists.forEach((String name)->{
			createPlaylistView(name, playlistsVbox);

		});


		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c-> {
			while(c.next()) {
				c.getAddedSubList().forEach(s->{
					createPlaylistView(s, playlistsVbox);
				});
				c.getRemoved().forEach(s->{
					RadioButton tbremoved = new RadioButton();

					for(int i=0; i<playlistsVbox.getChildren().size(); i++) {
					RadioButton playlist = (RadioButton) playlistsVbox.getChildren().get(i);
						if((playlist).getText() == s) {
							MainApp.root.getChildren().remove(playlist.getUserData());
							tbremoved = playlist;
						}
					}

				playlistsVbox.getChildren().remove(tbremoved);
				MainApp.playlistList.removeIf(tl-> tl.getPlaylistName()==s);

				});
			}
		});

		playlistMain.getChildren().addAll(addBox, scroll);

		//TODO al momento funziona male, quando davide mette a posto la grafica è da fixare
		ContextMenus.contextMenuPlaylists(playlistMain);

		return playlistMain;
	}


	/**
	 * Funzione che crea i bottoni da aggiungere allo scrollpane delle playlist
	 *
	 * @param string
	 * @param box
	 * @param mainPanel
	 * @param dataPane
	 */
	private static void playlistButton(String string, VBox box, ToggleGroup mainPanel, Node dataPane) {
		RadioButton playlistButton = new RadioButton(string);
		playlistButton.getStyleClass().remove("radio-button");
		playlistButton.getStyleClass().add("toggle-button");

		double width = Tools.DWIDTHS[0]*0.95;
		playlistButton.setMinWidth(width);
		playlistButton.setMaxWidth(width);
		playlistButton.setAlignment(Pos.CENTER_LEFT);
		playlistButton.setToggleGroup(mainPanel);
		playlistButton.setStyle(Tools.TRANSBUTT);
		playlistButton.setOnMouseMoved((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.BOLDBUTT);
		});
		playlistButton.setOnMouseExited((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		playlistButton.selectedProperty().addListener((e)->{
			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
			else playlistButton.setStyle(Tools.TRANSBUTT);
		});
		box.getChildren().add(playlistButton);
		dataPane.setVisible(false);
		playlistButton.setUserData(dataPane);

		ContextMenus.contextMenuPlaylists(playlistButton); //Add context menu




	}

	private static void createPlaylistView(String name, VBox playlistsVbox) {
		TrackList tracklist = Tools.readPlaylist(name);
		tracklist.setPlaylistName(name);
		TableView<Track> table = TrackView.tableFromTracklist(tracklist, MainApp.pc);
		ContextMenus.contextMenuTrackPlaylist(table, tracklist);
		VBox tableBox = new VBox(table);
		VBox.setVgrow(table, Priority.ALWAYS);
		MainApp.root.add(tableBox, 1, 2, 1, 2);
		playlistButton(name, playlistsVbox, MainApp.mainPanel, tableBox);

		MainApp.playlistList.add(tracklist);


		TrackView.setDragDrop(table, tracklist);
	}



	}
