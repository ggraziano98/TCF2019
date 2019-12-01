package userinterface;

import controllers.PlayerController;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Track;
import models.TrackList;

public class TrackView {
	public static VBox tableFromTracklist(TrackList tracklist, PlayerController pc) {
		return tableFromTracklist(tracklist, pc, false);
	}	


	public static VBox tableFromTracklist(TrackList tracklist, PlayerController pc, boolean showOrder) {
		TableView<Track> table = new TableView<>();

		TableColumn<Track, StringProperty> column1 = new TableColumn<>("Titolo");
		column1.setCellValueFactory(new PropertyValueFactory<>("title"));

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

		table.getColumns().add(column1);
		table.getColumns().add(column2);
		table.getColumns().add(column3);
		table.getColumns().add(column4);

		table.setRowFactory(new Callback<TableView<Track>, TableRow<Track>>() {
			@Override
			public TableRow<Track> call(TableView<Track> tableView) {
				final TableRow<Track> row = new TableRow<Track>() {
					@Override
					protected void updateItem(Track track, boolean empty){
						super.updateItem(track, empty);
						if (track!=null) {
							if(track.getPlaying()) setStyle("-fx-background-color: green;");
							track.playingProperty().addListener((obs, oldv, newv)->{
								if (newv) {
									setStyle("-fx-background-color: green;");

								}
								else {
									setStyle("");
								}
							});
						}
					}
				};

				return row;
			}
		});
		
		 table.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
		        @Override
		        public void handle(ScrollEvent scrollEvent) {
			           table.refresh();
		        }
		 });

		tracklist.forEach((Track t)->{
			table.getItems().add(t);
		});

		table.setOnMouseClicked((MouseEvent click) -> {
			if (click.getClickCount() == 2) {
				// Use ListView's getSelected Item
				Track selectedTrack = table.getSelectionModel().getSelectedItem();
				if (selectedTrack != null) {
					if(!pc.getTracklist().equals(tracklist)) {
						pc.setTracklist(tracklist);
						System.out.println("changing tracklist");
					}
					pc.setCurrentTrack(selectedTrack);
					pc.play();
				}
			}
		});

		table.setMinHeight(400);

		VBox vbox = new VBox(table);
		VBox.setVgrow(table, Priority.ALWAYS);

		return vbox;
	}


//	protected static void tableRefresh(TableView<Track> table) {
//		table.getColumns().forEach(col->{
//			col.setVisible(false);
//			col.setVisible(true);
//		});
//		
//	}
	

	/* continua a dare errori
	 * 
	private static void customFactory(TableColumn<Track, StringProperty> calltypel) {
		calltypel.setCellFactory(column -> {
            return new TableCell<Track, StringProperty>() {
                @Override
                protected void updateItem(StringProperty item, boolean empty) {
                    super.updateItem(item, empty);

                    TableRow<Track> row = getTableRow();

                    setText(null);
                    setGraphic(null);

                    if (!isEmpty()) {
                    	setText(item.get());

                        if(row.getItem().getPlaying()) 
                            row.setStyle("-fx-background-color:lightcoral");
                        else
                            row.setStyle("-fx-background-color:lightgreen");
                    }
                }
            };
        });
	}
	 */

}
