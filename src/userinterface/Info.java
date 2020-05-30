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
        Collection<CountryData> list = null;
		try {
			list = Files.readAllLines(file.toPath())
			                .stream()
			                .map(line -> {
			                    String[] details = line.split(",");
			                    CountryData cd = new CountryData();
			                    cd.setCountry(details[0]);
			                    cd.setCapital(details[1]);
//                            cd.setPopulation(details[2]);
//                            cd.setDemocracy(details[3]);
			                    return cd;
			                })
			                .collect(Collectors.toList());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ObservableList<CountryData> details = FXCollections.observableArrayList(list);

        TableView<CountryData> tableView = new TableView<>();
        TableColumn<CountryData, String> col1 = new TableColumn<>();
        TableColumn<CountryData, String> col2 = new TableColumn<>();
        TableColumn<CountryData, String> col3 = new TableColumn<>();
        TableColumn<CountryData, String> col4 = new TableColumn<>();

        tableView.getColumns().addAll(col1, col2, col3, col4);

        col1.setCellValueFactory(data -> data.getValue().countryProperty());
        col2.setCellValueFactory(data -> data.getValue().capitalProperty());
//        col3.setCellValueFactory(data -> data.getValue().populationProperty());
//        col4.setCellValueFactory(data -> data.getValue().democracyProperty());

        tableView.setItems(details);
        tableView.setSelectionModel(null);

        StackPane sp = new StackPane(tableView);
        Scene scene = new Scene(sp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




     private static class CountryData {
        StringProperty country = new SimpleStringProperty();
        StringProperty capital = new SimpleStringProperty();
        StringProperty population = new SimpleStringProperty();
        StringProperty democracy = new SimpleStringProperty();
        public final StringProperty countryProperty() {
            return this.country;
        }

        public final java.lang.String getCountry() {
            return this.countryProperty().get();
        }

        public final void setCountry(final java.lang.String country) {
            this.countryProperty().set(country);
        }

        public final StringProperty capitalProperty() {
            return this.capital;
        }

        public final java.lang.String getCapital() {
            return this.capitalProperty().get();
        }

        public final void setCapital(final java.lang.String capital) {
            this.capitalProperty().set(capital);
        }

//        public final StringProperty populationProperty() {
//            return this.population;
//        }
//
//        public final java.lang.String getPopulation() {
//            return this.populationProperty().get();
//        }
//
//        public final void setPopulation(final java.lang.String population) {
//            this.populationProperty().set(population);
//        }
//
//        public final StringProperty democracyProperty() {
//            return this.democracy;
//        }
//
//        public final java.lang.String getDemocracy() {
//            return this.democracyProperty().get();
//        }
//
//        public final void setDemocracy(final java.lang.String democracy) {
//            this.democracyProperty().set(democracy);
//        }



    }


}
