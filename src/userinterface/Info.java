package userinterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Info {

//    public static void main(String[] args){
//    	launch(args);
//    }


	
    public static void start() {
    	Stage primaryStage = new Stage();
		File file = new File("src\\userinterface\\info.txt");
        Collection<Data> list = null;
		try {
			list = Files.readAllLines(file.toPath())
			                .stream()
			                .map(line -> {
			                    String[] details = line.split(",");
			                    Data cd = new Data();
			                    cd.setAction(details[0]);
			                    cd.setCommand(details[1]);
//                            cd.setPopulation(details[2]);
//                            cd.setDemocracy(details[3]);
			                    return cd;
			                })
			                .collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ObservableList<Data> details = FXCollections.observableArrayList(list);

        TableView<Data> tableView = new TableView<>();
        TableColumn<Data, String> col1 = new TableColumn<>("Azione");
        TableColumn<Data, String> col2 = new TableColumn<>("Comando");

        tableView.getColumns().addAll(col1, col2);

        col1.setCellValueFactory(data -> data.getValue().actionProperty());
        col2.setCellValueFactory(data -> data.getValue().commandProperty());


        tableView.setItems(details);
        tableView.setSelectionModel(null);

        StackPane sp = new StackPane(tableView);
        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




     private static class Data {
        StringProperty country = new SimpleStringProperty();
        StringProperty capital = new SimpleStringProperty();
        public final StringProperty commandProperty() {
            return this.country;
        }

        public final java.lang.String getCommand() {
            return this.commandProperty().get();
        }

        public final void setCommand(final java.lang.String country) {
            this.commandProperty().set(country);
        }

        public final StringProperty actionProperty() {
            return this.capital;
        }

        public final java.lang.String getAction() {
            return this.actionProperty().get();
        }

        public final void setAction(final java.lang.String capital) {
            this.actionProperty().set(capital);
        }





    }


}
