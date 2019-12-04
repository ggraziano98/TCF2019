package userinterface;

import com.rometools.rome.feed.atom.Person;

import controllers.PlayerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import models.Track;
import models.TrackList;
import utils.Tools;

public class TrackView {

	 private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");	
	
	public static TableView<Track> tableFromTracklist(TrackList tracklist, PlayerController pc, boolean showOrder){
		TableView<Track> table = new TableView<>();


		TableColumn<Track, String> columnPlaying = new TableColumn<>(" ");
		columnPlaying.setCellValueFactory(new Callback<CellDataFeatures<Track, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Track, String> t) {
				// t.getValue() returns the Track instance for a particular TableView row
				SimpleStringProperty result = new SimpleStringProperty(" ");
				if (t.getValue().getPlaying()) result = new SimpleStringProperty("♪");
				return result;
			}
		});

		TableColumn<Track, String> column1 = new TableColumn<>("Titolo");
		column1.setCellValueFactory(new Callback<CellDataFeatures<Track, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Track, String> t) {
				// p.getValue() returns the Person instance for a particular TableView row
				return new SimpleStringProperty(t.getValue().getTitle());
			}
		});


		TableColumn<Track, StringProperty> column2 = new TableColumn<>("Artista");
		column2.setCellValueFactory(new PropertyValueFactory<>("artist"));

		TableColumn<Track, StringProperty> column3 = new TableColumn<>("Album");
		column3.setCellValueFactory(new PropertyValueFactory<>("album"));

		TableColumn<Track, StringProperty> column4 = new TableColumn<>("Genere");
		column4.setCellValueFactory(new PropertyValueFactory<>("genre"));

		if(showOrder) {
			TableColumn<Track, StringProperty> column0 = new TableColumn<>("#");
			column0.setCellValueFactory(new PropertyValueFactory<>("position"));
			table.getColumns().add(column0);
		}

		table.getColumns().add(columnPlaying);
		table.getColumns().add(column1);
		table.getColumns().add(column2);
		table.getColumns().add(column3);
		table.getColumns().add(column4);
		
		
		


		table.setItems(tracklist);

		pc.currentIntProperty().addListener(listener->{
			table.refresh();
		});

		table.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2) {
				// Use ListView's getSelected Item
				Track selectedTrack = table.getSelectionModel().getSelectedItem();
				if (selectedTrack != null) {
					if(!pc.getTracklist().equals(tracklist)) {
						pc.setTracklist(tracklist);
					}
					pc.setCurrentTrack(selectedTrack);
					pc.play();
				}
				table.refresh();
			}
		});

		table.setMinHeight(400);
		Pannelli.contextMenuTrack(table, tracklist);

		return table;

	}

	public static TableView<Track> tableFromTracklist(TrackList tracklist, PlayerController pc) {
		return tableFromTracklist(tracklist, pc, false);
	}

	
	public static void setDragDrop(TableView<Track> table, TrackList tracklist) {
		 table.setRowFactory(tv -> {
	            TableRow<Track> row = new TableRow<>();

	            row.setOnDragDetected(event -> {
	                if (! row.isEmpty()) {
	                    Integer index = row.getIndex();
	                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
	                    db.setDragView(row.snapshot(null, null));
	                    ClipboardContent cc = new ClipboardContent();
	                    cc.put(SERIALIZED_MIME_TYPE, index);
	                    db.setContent(cc);
	                    event.consume();
	                }
	            });

	            row.setOnDragOver(event -> {
	                Dragboard db = event.getDragboard();
	                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
	                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
	                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
	                        event.consume();
	                    }
	                }
	            });

	            row.setOnDragDropped(event -> {
	                Dragboard db = event.getDragboard();
	                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
	                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
	                    Track draggedTrack = table.getItems().remove(draggedIndex);

	                    int dropIndex ; 

	                    if (row.isEmpty()) {
	                        dropIndex = table.getItems().size() ;
	                    } else {
	                        dropIndex = row.getIndex();
	                    }

	                    table.getItems().add(dropIndex, draggedTrack);

	                    event.setDropCompleted(true);
	                    table.getSelectionModel().select(dropIndex);
	                    
	                     event.consume();
	                }
	                System.out.println(tracklist.getName() + 1);
	                Tools.saveAsPlaylist(tracklist, tracklist.getPlaylistName());
	            });

	            return row ;
	        });
	}

}
