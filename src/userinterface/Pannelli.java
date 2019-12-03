
package userinterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javax.script.Bindings;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;
import utils.Tools;

public class Pannelli{



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


//TODO cambiare in modo che differenzi tra songqueue, tracklist e allSongs

	public static void contextMenuTrack(TableView<Track> table, TrackList tracklist) {

		ContextMenu menu = new ContextMenu();

		MenuItem delete = new MenuItem("Delete song");
		MenuItem remove = new MenuItem("Remove song from playlist");
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		Menu addToPlaylist = new Menu("Add song to");

		MainApp.savedPlaylists.forEach((String name)->{
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

			addToPlaylist.getItems().add(item);
		});

		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c->{
			addToPlaylist.getItems().removeIf(i->true);
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
				addToPlaylist.getItems().add(item);
			});
		});

		SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
		MenuItem info = new MenuItem("Information");


		delete.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				tracklist.remove(track);
				Tools.DeleteTrack((Track) track);
			}
		});

		remove.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track track = table.getSelectionModel().getSelectedItem();
				tracklist.remove(track);
				Tools.saveAsPlaylist(tracklist, tracklist.getPlaylistName());
			}
		});

		info.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Track item = table.getSelectionModel().getSelectedItem();
				item.setHasMetadata(false);
				item.setMetadata();
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



		menu.getItems().addAll(delete, remove, separatorMenuItem, addToPlaylist, separatorMenuItem1, info);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}



	public static void contextMenuTrack(TableView<Track> table, TrackList tracklist) {

		ObservableList<String> savedPlaylists = Tools.getNamesSavedPlaylists();

		ContextMenu menu = new ContextMenu();

		MenuItem item1 = new MenuItem("Delete song");
		MenuItem item2 = new MenuItem("Remove song from playlist");
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
				Object item = table.getSelectionModel().getSelectedItem();
				Tools.DeleteTrack((Track) item);
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



		menu.getItems().addAll(item1, item2, separatorMenuItem, parentMenu, separatorMenuItem1, item3);

		table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
			public void handle(ContextMenuEvent event) {
				menu.show(table, event.getScreenX(), event.getScreenY());
			}
		});

	}




}
