package userinterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import utils.ResourceLoader;
import utils.Tools;



public class Info {

//    public static void main(String[] args){
//    	launch(args);
//    }


	
    @SuppressWarnings("unchecked")
	public static void start() throws Exception{
    	Stage primaryStage = new Stage();
    	InputStream stream = ResourceLoader.load("comandi.txt");
		File file = File.createTempFile("tempinfo", ".txt");
		FileUtils.copyInputStreamToFile(stream, file);
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
        tableView.setFixedCellSize(30);
        tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(tableView.getFixedCellSize()));
        tableView.minHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(tableView.getFixedCellSize()));
        
        TextArea intro = new TextArea("Di seguito trovate alcune azioni che possono essere eseguite direttamente tramite comandi da tastiera:"); 
        intro.setWrapText(true);
        intro.setEditable(false);
        

          
        TextArea text = new TextArea();
        initialize(text);
        text.setWrapText(true);
        text.setEditable(false);
        
        TextArea autori = new TextArea("Gli autori: Graziano Giovanni, Follo Umberto, Dionese Davide");
        autori.setWrapText(true);
        autori.setEditable(false);

        
        GridPane gd = new GridPane();
        ColumnConstraints col = new ColumnConstraints();
        col.setMinWidth(Tools.DWIDTHS[1]*0.8);
        col.setMaxWidth(Tools.DWIDTHS[1]*0.8);
        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(Tools.DHEIGHTS[0]);
        row1.setMaxHeight(Tools.DHEIGHTS[0]);
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        row3.setMinHeight(Tools.DHEIGHTS[0]);
        row3.setMaxHeight(Tools.DHEIGHTS[0]);
        RowConstraints row4 = new RowConstraints();
        RowConstraints row5 = new RowConstraints();
        row5.setMinHeight(Tools.DHEIGHTS[0]*1.5);
        row5.setMaxHeight(Tools.DHEIGHTS[0]*1.5);
        gd.getColumnConstraints().add(col);
        gd.getRowConstraints().addAll(row1, row2, row3, row4, row5);
        gd.add(intro, 0, 0);
        gd.add(tableView, 0, 1);
        gd.add(text, 0, 3);
        gd.add(autori, 0, 4);
        Scene scene = new Scene(gd);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(Tools.DWIDTHS[1]*0.8);
        primaryStage.setMaxWidth(Tools.DWIDTHS[1]*0.8);
        primaryStage.setMinHeight(Tools.DHEIGHTS[2]*1.4);
        primaryStage.setMaxHeight(Tools.DHEIGHTS[2]*1.4);
        primaryStage.setTitle("Informazioni");
        InputStream gioFile = ResourceLoader.load("p.png");
		Image gioImage = new Image(gioFile);
		gioFile.close();
		primaryStage.getIcons().add(gioImage);
        primaryStage.show();
    }
    
	




     private static class Data {
        StringProperty country = new SimpleStringProperty();
        StringProperty capital = new SimpleStringProperty();
        public final StringProperty commandProperty() {
            return this.country;
        }

//        public final java.lang.String getCommand() {
//            return this.commandProperty().get();
//        }

        public final void setCommand(final java.lang.String country) {
            this.commandProperty().set(country);
        }

        public final StringProperty actionProperty() {
            return this.capital;
        }

//        public final java.lang.String getAction() {
//            return this.actionProperty().get();
//        }

        public final void setAction(final java.lang.String capital) {
            this.actionProperty().set(capital);
        }





    }
     
     
     public static void initialize(TextArea a) throws IOException{
         try {
        	 InputStream stream = ResourceLoader.load("info.txt");
     		File file = File.createTempFile("tempinfo1", ".txt");
     		FileUtils.copyInputStreamToFile(stream, file);
             Scanner s = new Scanner(file);
             while (s.hasNext()) {
                 if (s.hasNextInt()) { // check if next token is an int
                     a.appendText(s.nextInt() + " "); // display the found integer
                 } else {
                     a.appendText(s.next() + " "); // else read the next token
                 }
             }
         } catch (FileNotFoundException ex) {
             System.err.println(ex);
         }
     }



//	@Override
//	public void start(Stage primaryStage) throws Exception {		
//	}
     
}

