package userinterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.input.ContextMenuEvent;
import utils.Tools;

public class Pannelli{



	public static void contextMenuPlaylists(RadioButton button ) {

		ContextMenu menu = new ContextMenu();


		MenuItem item1 = new MenuItem("Add playlist");

		item1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Tools.newPlaylist();
				Root.refreshPlaylists();

			}
		});


		MenuItem item2 = new MenuItem("Delete playlist");
		item2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Tools.deletePlaylist(button.getText(), true);
				Root.refreshPlaylists();

			}

		});

		menu.getItems().addAll(item1, item2);
		button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

			@Override
			public void handle(ContextMenuEvent event) {

				menu.show(button, event.getScreenX(), event.getScreenY());
			}
		});
	}

}

