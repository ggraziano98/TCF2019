<<<<<<< HEAD
package userinterface;

import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import utils.Tools;

public class Root {

	public static final GridPane rootPane() {

		GridPane root = new GridPane();
		//GRIDPANE è il pane di livello piu alto, contiene tutti gli altri

		root.setVgap(10);
		root.setHgap(20);
		root.setPadding(new Insets(5, 10, 5, 10));
		root.setGridLinesVisible(true);

		double[] widths = Tools.DWIDTHS;
		double[] heights = Tools.DHEIGHTS;

		//imposto i constraints del gridpane per settare anche il comportamento quando riscalo
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(widths[0]);
		column1.setMaxWidth(widths[0]);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setMinWidth(600);
		column2.setHgrow(Priority.ALWAYS);
		root.getColumnConstraints().addAll(column1, column2);

		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(heights[0]);
		row1.setMaxHeight(heights[0]);
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(heights[1]);
		row2.setVgrow(Priority.ALWAYS);
		RowConstraints row3 = new RowConstraints();
		row3.setMinHeight(heights[2]);
		row3.setMaxHeight(heights[2]);
		root.getRowConstraints().addAll(row1, row2, row3);

		return root;
	}

}
