package userinterface;


import controllers.PlayerController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import javafx.util.Duration;
import models.Track;
import models.TrackList;
import utils.Tools;

public class TrackView {

	 private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");
	 private static double scrollVelocity = 0;
	 private static Timeline scrolltimeline = new Timeline(0);
	 static int speed = 4000;
	 static boolean dropped; 

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
				// t.getValue() returns the Track instance for a particular TableView row
				return new SimpleStringProperty(t.getValue().getTitle());
			}
		});


		TableColumn<Track, StringProperty> column2 = new TableColumn<>("Artista");
		column2.setCellValueFactory(new PropertyValueFactory<>("artist"));

		TableColumn<Track, StringProperty> column3 = new TableColumn<>("Album");
		column3.setCellValueFactory(new PropertyValueFactory<>("album"));

		TableColumn<Track, StringProperty> column4 = new TableColumn<>("Genere");
		column4.setCellValueFactory(new PropertyValueFactory<>("genre"));

		TableColumn<Track, StringProperty> column5 = new TableColumn<>("Anno");
		column5.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Track, String> column6 = new TableColumn<>("Durata");
		column6.setCellValueFactory(new Callback<CellDataFeatures<Track, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Track, String> t) {
				return new SimpleStringProperty(PlayerBuilder.timeMinutes(t.getValue().getDuration().toSeconds()));
			}
		});

		table.getColumns().add(columnPlaying);
		table.getColumns().add(column1);
		table.getColumns().add(column2);
		table.getColumns().add(column3);
		table.getColumns().add(column4);
		table.getColumns().add(column5);
		table.getColumns().add(column6);

		columnPlaying.setPrefWidth(20);
		column1.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
		column2.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
		column3.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		column4.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		column5.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		column6.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

		if(showOrder) {
			TableColumn<Track, StringProperty> column0 = new TableColumn<>("#");
			column0.setCellValueFactory(new PropertyValueFactory<>("position"));
			table.getColumns().add(1, column0);
			column0.setPrefWidth(30);
		}

		table.setItems(tracklist);
		table.setId("transparent");

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
					MainApp.pc.previousTracklist = tracklist;
				}
				table.refresh();
			}
		});

		table.setMinHeight(400);
		table.setUserData(tracklist);

		return table;

	}

	public static TableView<Track> tableFromTracklist(TrackList tracklist, PlayerController pc) {
		return tableFromTracklist(tracklist, pc, false);
	}


/*	public static void setDragDrop(TableView<Track> table, TrackList tracklist) {
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
	                    tracklist.refreshPositions();
	                    MainApp.pc.refreshCurrentInt();
	                    event.setDropCompleted(true);
	                    table.getSelectionModel().select(dropIndex);

	                     event.consume();
	                }
	                Tools.saveAsPlaylist(tracklist, tracklist.getPlaylistName());
	            });

	            return row ;
	        });
	}*/
	
	public static void setDragDrop(TableView<Track> table, TrackList tracklist) {
		table.setRowFactory(tv -> {
			TableRow<Track> row = new TableRow<>();
			scrolltimeline.setCycleCount(Timeline.INDEFINITE);
			scrolltimeline.getKeyFrames().add(new KeyFrame(Duration.millis(20), (ActionEvent) -> {
				dragScroll(table);
			}));

			row.setOnDragDetected(event -> {
				if (!row.isEmpty()) {
					Integer index = row.getIndex();
					Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
					db.setDragView(row.snapshot(null, null));
					ClipboardContent cc = new ClipboardContent();
					cc.put(SERIALIZED_MIME_TYPE, index);
					db.setContent(cc);
					event.consume();
					dropped = false;
				}
			});

			row.setOnDragOver(event -> {
				Dragboard db = event.getDragboard();
				if (db.hasContent(SERIALIZED_MIME_TYPE)) {
					if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
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

					int dropIndex;

					if (row.isEmpty()) {
						dropIndex = table.getItems().size();
					} else {
						dropIndex = row.getIndex();
					}

					table.getItems().add(dropIndex, draggedTrack);
					tracklist.refreshPositions();
					MainApp.pc.refreshCurrentInt();
					event.setDropCompleted(true);
					table.getSelectionModel().select(dropIndex);
					event.consume();
				}
				Tools.saveAsPlaylist(tracklist, tracklist.getPlaylistName());
			});

			row.setOnDragExited((DragEvent event) -> {
				if (event.getSceneY() > Tools.DHEIGHTS[2]*1.2) {
					scrolltimeline.play();
					scrollVelocity = 1.0 / speed;
				}
				if (event.getSceneY() < Tools.DHEIGHTS[2]*0.4) {
					scrolltimeline.play();
					scrollVelocity = -1.0 / speed;
				}

			});

			row.setOnDragEntered((DragEvent event) -> {
				scrolltimeline.stop();
			});

			row.setOnScroll((ScrollEvent event) -> {
				scrolltimeline.stop();
			});

			row.setOnDragDone(event -> {
				scrolltimeline.stop();
				dropped = true;
			});
			
		
			return row;
		});
	}

	public static void dragScroll(TableView<Track> table) {
		ScrollBar sb = getVerticalScrollbar(table);
		if (sb != null && !dropped) {
			double newValue = sb.getValue() + scrollVelocity;
			newValue = Math.min(newValue, 1.0);
			newValue = Math.max(newValue, 0.0);
			sb.setValue(newValue);
		}
	}

	public static ScrollBar getVerticalScrollbar(TableView<Track> table) {
		ScrollBar result = null;
		for (Node n : table.lookupAll(".scroll-bar")) {
			if (n instanceof ScrollBar) {
				ScrollBar bar = (ScrollBar) n;
				if (bar.getOrientation().equals(Orientation.VERTICAL)) {
					result = bar;
				}
			}
		}
		return result;
	}


}
