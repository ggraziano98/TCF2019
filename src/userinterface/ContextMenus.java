
package userinterface;

import java.util.Collections;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import models.Track;
import models.TrackList;
import utils.Tools;



public class ContextMenus{

	/**
	 * Context menu delle playlist per area con radiobutton  newplaylist, deleteplaylist, sortbyduration, sortbyname, addallsongtoanothrplaylist
	 * 
	 * @param RadioButton 			button
	 */
	public static void contextMenuPlaylists(RadioButton button ) {

		//Creo il menu principale e inizializzo i suoi item
		ContextMenu menu = new ContextMenu();
		MenuItem newPlaylist = new MenuItem("New playlist");
		MenuItem deletePlaylist = new MenuItem("Delete playlist");
		MenuItem duration = new MenuItem("Total duration");

		//Creo il menu secondario e un suo item
		Menu addSongTo = new Menu("Add all the songs of this playlist to");		
		MenuItem songQueue = new MenuItem("Song queue");
		SeparatorMenuItem separator = new SeparatorMenuItem();


		//Implemento gli item del menu primario
		newPlaylist.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Tools.newPlaylist();
			}
		});

		deletePlaylist.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Tools.deletePlaylist(button.getText(), true);
			}
		});

		duration.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TrackList playlist = Tools.readPlaylist(button.getText());
				Alert informationDialog = new Alert(AlertType.NONE,
						"Duration of the playlist: " + PlayerBuilder.timeMinutes(playlist.totalDuration().toSeconds()),
						ButtonType.OK); 
				informationDialog.show(); 
			}
		});

		//Creo, implemento e aggiungo gli elementi del menu secondario
		songQueue.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TrackList tracklistToAdd = Tools.readPlaylist(button.getText());
				int l;

				if (MainApp.pc.getTracklist().getSize() == 0) {
					l=-1;
					for (Track track : tracklistToAdd) {
						MainApp.pc.getTracklist().addNew(track);
					}
				} else {
					int i = MainApp.pc.getCurrentInt();
					l=i;
					for (Track track : tracklistToAdd) {
						i++;
						MainApp.pc.getTracklist().addNew(track, i);
					}
				}
		
					MainApp.pc.previousTracklist=MainApp.pc.getTracklist();
			}
		});

		//menu che fa aggiungere tutte le canzoni in una playlist alla playlist selezionata
		MainApp.savedPlaylists.forEach((String playlistName)->{
			addSongTo.getItems().add(addPlaylistToPlaylist(playlistName, button));
		});


		//controllo che non siano state create nuove playlist, se sono state create le aggiungo al menu
		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			while(c.next()) {
				addSongTo.getItems().removeIf(i->!MainApp.savedPlaylists.contains(i.getText()));
				c.getAddedSubList().forEach(playlistName->{
					addSongTo.getItems().add(addPlaylistToPlaylist(playlistName, button));
				});
				addSongTo.getItems().addAll(separator,songQueue);
			}
		});

		//aggiungo gli items ai menu principale e secondario
		addSongTo.getItems().addAll(separator, songQueue);
		menu.getItems().addAll(addSongTo, duration, new SeparatorMenuItem(),
				newPlaylist, deletePlaylist);

		//event handler del menu
		button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				if(button != null) {
					menu.show(button, event.getScreenX(), event.getScreenY());
				}
			}
		});

		button.setOnMousePressed(ev->{
			menu.hide();
		});
	}

	/**
	 * MenuItem delle playlist che permette di aggiungere tutte le track di una playlist a un'altra playlist
	 * @param playlistName Nome della playlist a cui aggiungere
	 * @param button RadioButton a cui è collegata la playlist da aggiungere
	 * @return
	 */
	private static MenuItem addPlaylistToPlaylist(String playlistName, RadioButton button) {
		MenuItem playlist  = new MenuItem(playlistName);
		playlist.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TrackList tracklistToAdd = Tools.readPlaylist(button.getText());
				MainApp.playlistList.forEach(tl->{
					if(tl.getPlaylistName() == playlistName) {
						tl.addAll(tracklistToAdd);
						Tools.saveAsPlaylist(tl, playlistName);
					}
				});
			}
		});

		return playlist;
	}





	/**
	 * mi dà il contextmenu per le songs: delete song, add song to playlist, add song to queue
	 *
	 * @param TableView<Track> 		table
	 */
	public static void contextMenuSongs(TableView<Track> table) {

		//creo il menu principale e i suoi item
		ContextMenu menu = new ContextMenu();
		MenuItem deleteSong = new MenuItem("Delete song");
		MenuItem information = new MenuItem("Information");
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();


		//Creo il menu secondario e un suo item
		Menu addSongTo = new Menu("Add song to");
		MenuItem songQueue = new MenuItem("Song queue");

		//implemento gli item del menu principale
		deleteSong.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Tools.DeleteTrack((Track) item);
			}	
		});

		information.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				Alert informationDialog = ContextMenus.informationTrack(track);
				informationDialog.show(); 
			}
		});

		//creo e implemento gli item del menu secondario
		songQueue.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				if (MainApp.pc.getTracklist().getSize() == 0) {
					MainApp.pc.getTracklist().addNew(track);	
				} else {
					MainApp.pc.getTracklist().addNew(track,MainApp.pc.getCurrentInt() + 1);	
				}
				MainApp.pc.previousTracklist = MainApp.pc.getTracklist();
			}
			
		});

		MainApp.savedPlaylists.forEach((String playlistName)->{
			addSongTo.getItems().add(addTrackToPlaylist(playlistName, table));
		});

		//controllo che non siano state create altre playlist
		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			while(c.next()) {
				addSongTo.getItems().removeIf(i->!MainApp.savedPlaylists.contains(i.getText()));
				c.getAddedSubList().forEach(playlistName->{
					addSongTo.getItems().add(addTrackToPlaylist(playlistName, table));
				});
				addSongTo.getItems().addAll(separatorMenuItem,songQueue);
			}
		});

		//aggiungo tutto ai rispettivi menu
		addSongTo.getItems().addAll(separatorMenuItem,songQueue);
		menu.getItems().addAll(addSongTo, information, separatorMenuItem1, deleteSong);

		//event handler del menu
		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					menu.show(table, event.getScreenX(), event.getScreenY());
				}			}
		});
		table.setOnMousePressed(ev->{
			menu.hide();
		});
	}


	/**
	 * 
	 * @param playlistName
	 * @param table
	 * @return
	 */
	private static MenuItem addTrackToPlaylist(String playlistName, TableView<Track> table) {
		MenuItem playlist = new MenuItem(playlistName);	
		playlist.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object track = table.getSelectionModel().getSelectedItem();
				MainApp.playlistList.forEach(tl->{
					if(tl.getPlaylistName() == playlistName) {
						tl.addNew((Track) track);
						Tools.saveAsPlaylist(tl, playlistName);
					}
				});
			}	
		});
		return playlist;
	}




	/**
	 * contextmenu per queue, add to song queu, remove fromsong queue, information
	 * 
	 * @param table
	 * @param tracklist
	 */
	public static void contextMenuSongQueue(TableView<Track> table) {

		//inizializzo il menu principale e i suoi elementi
		ContextMenu menu = new ContextMenu();
		MenuItem add = new MenuItem("Add to song queue");
		MenuItem remove = new MenuItem("Remove from song queue");
		MenuItem clear = new MenuItem("Clear song queue");
		MenuItem information = new MenuItem("Information");

		//implemento gli items
		add.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().addNew(track);
				MainApp.pc.previousTracklist=MainApp.pc.getTracklist();
			}
		});

		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().remove((Track) item);
				MainApp.pc.getTracklist().refreshPositions();
			}
		});

		information.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				Alert informationDialog = ContextMenus.informationTrack(track);
				informationDialog.show(); 
			}
		});

		clear.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = MainApp.pc.getCurrentTrack();
				MainApp.pc.getTracklist().clear();
				MainApp.pc.getTracklist().addTrack(track);
				MainApp.pc.refreshCurrentInt();
			}
		});

		//aggiungo gli item al menu principale
		menu.getItems().addAll(remove, clear, add, information);

		//event handler del menu
		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					menu.show(table, event.getScreenX(), event.getScreenY());
				}			}
		});
		table.setOnMousePressed(ev->{
			menu.hide();
		});
	}




	/**
	 * contextmenu per le track all'interno delle playlist remove, addto, information
	 * 
	 * @param TableView<Ttack>			table
	 * @param TrackList					tracklist
	 */
	public static void contextMenuTrackPlaylist(TableView<Track> table, TrackList tracklist) {

		//inizializzo l menu principale e i suoi elementi
		ContextMenu menu = new ContextMenu();
		MenuItem remove = new MenuItem("Remove song from playlist");
		MenuItem information = new MenuItem("Information");
		SeparatorMenuItem separator = new SeparatorMenuItem();

		//inizializzo il menu secondari e un suo item
		Menu addSongTo = new Menu("Add song to");
		MenuItem songQueue = new MenuItem("Song queue");

		//implemento l'item del menu principale
		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				tracklist.removeTrack(track);
				Tools.saveAsPlaylist(tracklist, tracklist.getPlaylistName());
			}
		});

		information.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				Alert informationDialog = ContextMenus.informationTrack(track);
				informationDialog.show(); 
			}
		});

		//implemento e inizializzo gli elementi del menu secondario
		songQueue.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				if (MainApp.pc.getTracklist().getSize() == 0) {
					MainApp.pc.getTracklist().addNew(track);	
				} else {
					MainApp.pc.getTracklist().addNew(track, MainApp.pc.getCurrentInt() + 1);	
				}	
				MainApp.pc.previousTracklist = MainApp.pc.getTracklist();
			}
		});
		
		/*
		//controllo che non ci siano nuovi elementi playlist
		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			menu.getItems().remove(addSongTo);
			while(c.next()) {				
				addSongTo.getItems().clear();
				c.getList().forEach(playlistName->{
					addSongTo.getItems().add(addTrackToPlaylist(playlistName, table));
				});
				addSongTo.getItems().addAll(separator,songQueue);
			}
			menu.getItems().add(0, addSongTo);
		});
		*/

		//aggiungo gli item ai menu
		menu.getItems().addAll(addSongTo, information, separator, remove);

		//event handler del context menu
		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				if(table.getSelectionModel().getSelectedItem() != null) {
					addSongTo.getItems().clear();
					MainApp.savedPlaylists.forEach((String playlistName)->{
						addSongTo.getItems().add(addTrackToPlaylist(playlistName, table));
					});
					addSongTo.getItems().addAll(new SeparatorMenuItem(),songQueue);
					menu.show(table, event.getScreenX(), event.getScreenY());
				}
			}
		});
		table.setOnMousePressed(ev->{
			menu.hide();
		});
	}






	public static Alert informationTrack (Track track) {

		String info = new String();

		if(track.getArtist()!= Tools.DARTIST) {
			info = info + "Artist: " + track.getArtist() + "\n" ;
		}else {
			info = info +"Artist: Unknown";
		}

		info = info + "Title: " + track.getTitle() + "\n" ;

		if(track.getArtist()!= Tools.DALBUM) {
			info = info + "Album: " + track.getAlbum() + "\n" ;
		}else {
			info = info +"Alum: Unknown";
		}

		if(track.getArtist()!= Tools.DYEAR) {
			info = info + "Year: " + track.getYear() + "\n" ;
		}else {
			info = info +"Year: Unknown";
		}

		info = info + "Duration: " + PlayerBuilder.timeMinutes(track.getDuration().toSeconds());

		Alert informationDialog = new Alert(AlertType.NONE,	info, ButtonType.OK); 

		return informationDialog;
	}


}

