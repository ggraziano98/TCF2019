package testers;


import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Track;
import models.TrackList;
import utils.Tools;

public class randomTester extends Application{


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		TrackList playlist = Tools.readPlaylist("Playlist playerTester");
		TableView<Track> tableView = tableFromTracklist(playlist);
        VBox vbox = new VBox(tableView);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
	}
	
	
	
	public static TableView<Track> tableFromTracklist(TrackList tracklist) {
		TableView<Track> table = new TableView<>();

        TableColumn<Track, StringProperty> column1 = new TableColumn<>("Titolo");
        column1.setCellValueFactory(new PropertyValueFactory<>("title"));


        TableColumn<Track, StringProperty> column2 = new TableColumn<>("Artista");
        column2.setCellValueFactory(new PropertyValueFactory<>("artist"));
        
        TableColumn<Track, StringProperty> column3 = new TableColumn<>("Album");
        column2.setCellValueFactory(new PropertyValueFactory<>("album"));
        
        TableColumn<Track, StringProperty> column4 = new TableColumn<>("Genere");
        column2.setCellValueFactory(new PropertyValueFactory<>("genre"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);
        table.getColumns().add(column4);



        tracklist.forEach((Track t)->{
            table.getItems().add(t);
        });
		
		
		return table;
	}
}
