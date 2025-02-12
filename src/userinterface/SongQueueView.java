package userinterface;

import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;

public class SongQueueView {
	
	//TODO change so playing a song shows on other tables
	
	
	public static VBox songQueueView() {
		VBox songQueueView = new VBox();
		
		TableView<Track> table = TrackView.tableFromTracklist(MainApp.pc.getTracklist(), MainApp.pc, true);
		
		table.getColumns().forEach(col->{
			col.setSortable(false);
		});
		
		songQueueView.getChildren().add(table);
		VBox.setVgrow(table, Priority.ALWAYS);
		ContextMenus.contextMenuSongQueue(table);
		TrackView.setDragDrop(table, MainApp.pc.getTracklist());
		return songQueueView;
	}


}
