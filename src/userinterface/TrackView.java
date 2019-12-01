package userinterface;

import controllers.PlayerController;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import models.Track;
import models.TrackList;

public class TrackView {
	
	public static VBox tableFromTracklist(TrackList tracklist, PlayerController pc) {
		TableView<Track> table = new TableView<>();

        TableColumn<Track, StringProperty> column1 = new TableColumn<>("Titolo");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));
        customFactory(column1);

        TableColumn<Track, StringProperty> column2 = new TableColumn<>("Artista");
        column2.setCellValueFactory(new PropertyValueFactory<>("artist"));
        
        TableColumn<Track, StringProperty> column3 = new TableColumn<>("Album");
        column3.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        TableColumn<Track, StringProperty> column4 = new TableColumn<>("Genere");
        column4.setCellValueFactory(new PropertyValueFactory<>("genre"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);

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

}
