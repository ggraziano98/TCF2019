
package userinterface;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;
import net.sf.ehcache.search.impl.OrderComparator;
import utils.Tools;

public class Pannelli{



	public static void contextMenuPlaylists(ScrollPane scrollpane) {

		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("Add playlist");
		MenuItem item2 = new MenuItem("Delete playlist");
		Menu parentmenu = new Menu("Sort by");

		MenuItem itemchild1 = new MenuItem("Duration");
		MenuItem itemchild2 = new MenuItem("Name");

		parentmenu.getItems().addAll(itemchild1,itemchild2);

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Tools.newPlaylist();

				//TODO cancellare
				//				Root.refreshPlaylists();
			}
		});

		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

			}
		});

		itemchild1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//TODO da sviluppare order playlist by duration
				Alert informationDialog = new Alert(AlertType.NONE,
						"da sviluppare"
						,ButtonType.OK); 
				informationDialog.show(); 
			}
		});

		//TODO implementare il sort by duration
		itemchild2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert informationDialog = new Alert(AlertType.NONE,
						"da sviluppare"
						,ButtonType.OK); 
				informationDialog.show(); 
			}
		});



		menu.getItems().addAll(item1, item2, parentmenu);

		scrollpane.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(scrollpane, event.getScreenX(), event.getScreenY());
			}
		});

	}



	public static void contextMenuPlaylists(RadioButton button ) {

		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("Add playlist");
		MenuItem item2 = new MenuItem("Delete playlist");

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Tools.newPlaylist();
			}
		});

		item2.setAccelerator(KeyCombination.keyCombination("Canc"));
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Tools.deletePlaylist(button.getText(), true);

			}
		});

		menu.getItems().addAll(item1, item2);

		button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(button, event.getScreenX(), event.getScreenY());
			}
		});

	}









	//TODO fixare removesong e songqueue


	/**
	 * mi dà il contextmenu per le songs: delete song, add song to playlist, add song to queue
	 * @param table
	 */
	public static void contextMenuSongs(TableView<Track> table) {

		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("Delete song");

		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

		Menu parentMenu = new Menu("Add song to");

		savedPlaylists.forEach((String name)->{
			MenuItem item = new MenuItem(name);	
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Object track = table.getSelectionModel().getSelectedItem();
					Tools.addTrackToPlaylist(name,(Track) track);
				}	
			});

			parentMenu.getItems().add(item);
		});

		MenuItem item2 = new MenuItem("song queue");

		SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();

		MenuItem item3 = new MenuItem("Information");


		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Tools.DeleteTrack((Track) item);
			}	
		});

		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				//TODO rimando alla songqueue
				Alert informationDialog = new Alert(AlertType.NONE,
						"da sviluppare"
						,ButtonType.OK); 
				informationDialog.show(); 
			}
		});

		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Alert informationDialog = new Alert(AlertType.NONE,
						"Artist: " + ((Track) item).getArtist() + "\n" +
								"Title: " + ((Track) item).getTitle() +"\n" +
								"Album: " + ((Track) item).getAlbum() +"\n" +
								"Year: " + ((Track) item).getYear() +"\n" +
								"Duration " + ((Track) item).getDuration()
								,ButtonType.OK); 
				informationDialog.show(); 
			}
		});

		parentMenu.getItems().add(item2);

		menu.getItems().addAll(item1, separatorMenuItem, parentMenu, separatorMenuItem1, item3);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}



	/**
	 * contextmenu per queue e playlist
	 * @param table
	 * @param tracklist
	 */
	public static void contextMenuPlaylist(TableView<Track> table, TrackList tracklist) {

		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		ContextMenu menu = new ContextMenu();
		MenuItem item1 = new MenuItem("Add to song queue");
		MenuItem item2 = new MenuItem("Remove song");
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		Menu parentMenu = new Menu("Add song to");

		savedPlaylists.forEach((String name)->{
			MenuItem item = new MenuItem(name);	
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Object track = table.getSelectionModel().getSelectedItem();
					Tools.addTrackToPlaylist(name,(Track) track);
				}	
			});

			parentMenu.getItems().add(item);
		});

		SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();

		MenuItem item3 = new MenuItem("Information");

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object track = table.getSelectionModel().getSelectedItem();
				//TODO fixare
				Tools.addTrackToPlaylist(tracklist.getName(),(Track) track); 
			}
		});

		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				//TODO cercare di capire come passare il nome della playlist che sto vedendo
				//				Tools.RemoveTrackFromPlaylist(tracklist.toString(),(Track) item);
				Alert informationDialog = new Alert(AlertType.NONE,
						"da sviluppare"
						,ButtonType.OK); 
				informationDialog.show(); 
			}
		});

		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Alert informationDialog = new Alert(AlertType.NONE,
						"Artist: " + ((Track) item).getArtist() + "\n" +
								"Title: " + ((Track) item).getTitle() +"\n" +
								"Album: " + ((Track) item).getAlbum() +"\n" +
								"Year: " + ((Track) item).getYear() +"\n" +
								"Duration " + ((Track) item).getDuration()
								,ButtonType.OK); 
				informationDialog.show(); 
			}
		});
		
		parentMenu.getItems().add(item1);

		menu.getItems().addAll(item2, separatorMenuItem, parentMenu, separatorMenuItem1, item3);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}


}

