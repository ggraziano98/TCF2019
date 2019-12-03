package userinterface;

import controllers.PlayerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Track;
import models.TrackList;

public class TrackView {

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

		/*
		 * no longer needed but keeping here as refrence
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
		 */


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

		return table;

	}

	public static TableView<Track> tableFromTracklist(TrackList tracklist, PlayerController pc) {
		return tableFromTracklist(tracklist, pc, false);
	}


}
