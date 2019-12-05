package userinterface;

import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;

public class SongQueueView {
	
	//TODO change so playing a song shows on other tables
	
	
	public static VBox songQueueView() {
		VBox songQueueView = new VBox();
		
		TableView<Track> table = TrackView.tableFromTracklist(MainApp.pc.getTracklist(), MainApp.pc, true);
		
		songQueueView.getChildren().add(table);
		VBox.setVgrow(table, Priority.ALWAYS);
		songQueueView.setStyle("-fx-background-color: red");
		
		return songQueueView;
	}

}
