package userinterface;

import java.io.FileInputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import utils.Initialize;
import utils.Tools;

public class Root {

	public static final GridPane rootPane() throws Exception {

		GridPane root = new GridPane();
		//GRIDPANE è il pane di livello piu alto, contiene tutti gli altri
//		FileInputStream bgFile = new FileInputStream("files\\bg.png");
//		Image bgImage = new Image(bgFile, Tools.screenWidth, Tools.screenHeight, true, true);
//
//		 root.setBackground(new Background(new BackgroundImage(bgImage, BackgroundRepeat.REPEAT,
//                 BackgroundRepeat.REPEAT,
//                 BackgroundPosition.DEFAULT,
//                 BackgroundSize.DEFAULT)));


		root.setVgap(10);
		root.setHgap(10);
		root.setPadding(new Insets(5, 5, 5, 5));
		root.setGridLinesVisible(false);
		//root.setId("grad");

		double[] widths = Tools.DWIDTHS;
		double[] heights = Tools.DHEIGHTS;

		//imposto i constraints del gridpane per settare anche il comportamento quando riscalo
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(widths[0]);
		column1.setMaxWidth(widths[0]);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMinWidth(widths[1]);
		column2.setHgrow(Priority.ALWAYS);
		root.getColumnConstraints().addAll(column1, column2);

		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(heights[3]);
		row1.setMaxHeight(heights[3]);
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(heights[0]);
		row2.setMaxHeight(heights[0]);
		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(heights[1]);
		row3.setMaxHeight(heights[1]);
		RowConstraints row4 = new RowConstraints();
		row4.setMinHeight(heights[2]);
		row4.setVgrow(Priority.ALWAYS);

		root.getRowConstraints().addAll(row1, row2, row3,row4);

		Visualizer.visualize();
		root.add(Visualizer.visuaPane, 0, 0, 2, 4);

		MenuBar menuBar = new MenuBar();
	     Menu menuFile = new Menu("File directory");
	     Menu menuEdit = new Menu("Edit");
	     Menu menuInformation = new Menu("Information");

	     MenuItem item1 = new MenuItem("Set file directory");
	     item1.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					Initialize.addDirectory();
					}
			});


	     menuFile.getItems().add(item1);


	     menuBar.getMenus().addAll(menuFile, menuEdit, menuInformation);
	     menuBar.setMaxWidth(0.9*widths[0]);
	     HBox menuBox = new HBox();
	     menuBox.setId("transparent");
	     menuBox.getChildren().add(menuBar);
	     menuBox.setAlignment(Pos.CENTER);

	     root.add(menuBox, 0, 0);

		return root;
	}

}
