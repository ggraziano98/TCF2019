
package userinterface;

import java.nio.file.Path;

import javafx.collections.ListChangeListener;
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



	public static void contextMenuPlaylists(VBox emptyBox) {
				
		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("New playlist");
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

		emptyBox.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(emptyBox, event.getScreenX(), event.getScreenY());
			}
		});

	}



	public static void contextMenuPlaylists(RadioButton button ) {
		
		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();
		
		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("New playlist");
		MenuItem item2 = new MenuItem("Delete playlist");
		Menu parentmenu1 = new Menu("Add all the songs of this playlist to");
		savedPlaylists.forEach((String name)->{
			MenuItem item = new MenuItem(name);	
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					TrackList tracklisttoadd = Tools.readPlaylist(button.getText());
					for (Track track : tracklisttoadd) {
						Tools.addTrackToPlaylist(name, track);
					}					
					
				}	
			});

			parentmenu1.getItems().add(item);
		});
		
		MenuItem item3 = new MenuItem("Song queue");
		item3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				TrackList tracklisttoadd = Tools.readPlaylist(button.getText());
				for (Track track : tracklisttoadd) {
					MainApp.pc.getTracklist().addTrack((Track) track);
				}			
			}
		});
		parentmenu1.getItems().add(item3);
		
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

		menu.getItems().addAll(item1, item2,parentmenu1);

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

		MainApp.savedPlaylists.forEach((String name)->{
			MenuItem item = new MenuItem(name);	
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Object track = table.getSelectionModel().getSelectedItem();
					MainApp.playlistList.forEach(tl->{
						if(tl.getPlaylistName() == name) {
							tl.addNew((Track) track);
							Tools.saveAsPlaylist(tl, name);
						}
					});
				}	
			});

			parentMenu.getItems().add(item);
		});
		MenuItem item2 = new MenuItem("Song queue");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().addTrack((Track) item);	
			}
		});
		
		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			parentMenu.getItems().removeIf(i->true);
			c.getList().forEach(name->{
				MenuItem item = new MenuItem(name);
				item.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Track track = table.getSelectionModel().getSelectedItem();
						MainApp.playlistList.forEach(tl->{
							if(tl.getPlaylistName() == name) {
								tl.addNew(track);
								Tools.saveAsPlaylist(tl, name);
							}
						});
					}
				});
				parentMenu.getItems().add(item);
			});
			parentMenu.getItems().addAll(separatorMenuItem,item2);
		});
		
		

		SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();

		MenuItem item3 = new MenuItem("Information");


		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Tools.DeleteTrack((Track) item);
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

		parentMenu.getItems().addAll(separatorMenuItem,item2);

		menu.getItems().addAll(parentMenu, item3, separatorMenuItem1, item1);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}



	
	
	
	
	
	/**
	 * contextmenu per queue
	 * @param table
	 * @param tracklist
	 */
	public static void contextMenuSongQueue(TableView<Track> table) {

		ContextMenu menu = new ContextMenu();
		MenuItem item1 = new MenuItem("Add to song queue");
		MenuItem item2 = new MenuItem("Remove from song queue");
		MenuItem item3 = new MenuItem("Information");

		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object track = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().addTrack((Track) track);
			}
		});

		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().RemoveTrack((Track) item);
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

		menu.getItems().addAll(item1, item2, item3);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}

	
	
	
	
	public static void contextMenuPlaylist(TableView<Track> table, TrackList tracklist) {

		ContextMenu menu = new ContextMenu();
		MenuItem item1 = new MenuItem("Song queue");
		MenuItem item2 = new MenuItem("Remove song from playlist");
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		Menu parentMenu = new Menu("Add song to");

		MainApp.savedPlaylists.forEach((String name)->{
			MenuItem item = new MenuItem(name);	
			item.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Object track = table.getSelectionModel().getSelectedItem();
					MainApp.playlistList.forEach(tl->{
						if(tl.getPlaylistName() == name) {
							tl.addNew((Track) track);
							Tools.saveAsPlaylist(tl, name);
						}
					});
				}	
			});

			parentMenu.getItems().add(item);
		});
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				MainApp.pc.getTracklist().addTrack((Track) item);	
			}
		});
		
		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			parentMenu.getItems().removeIf(i->true);
			c.getList().forEach(name->{
				MenuItem item = new MenuItem(name);
				item.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						Track track = table.getSelectionModel().getSelectedItem();
						MainApp.playlistList.forEach(tl->{
							if(tl.getPlaylistName() == name) {
								tl.addNew(track);
								Tools.saveAsPlaylist(tl, name);
							}
						});
					}
				});
				parentMenu.getItems().add(item);
			});
			parentMenu.getItems().addAll(separatorMenuItem,item1);
		});
		


		MenuItem item3 = new MenuItem("Information");


		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Object item = table.getSelectionModel().getSelectedItem();
				Tools.RemoveTrackFromPlaylist(tracklist.getPlaylistName(), (Track) item);
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

		menu.getItems().addAll(item2, separatorMenuItem, parentMenu, item3);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}

}

