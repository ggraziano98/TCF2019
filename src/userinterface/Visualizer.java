package userinterface;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Visualizer {
	
	static List<Rectangle> rectangleList = new ArrayList<Rectangle>();
	public static int nbin = 64;
	static HBox visuaPane;

	
	static void visualize() {
		visuaPane = new HBox();
		visuaPane.setAlignment(Pos.CENTER);
		visuaPane.setPadding(new Insets(5, 5, 5, 5));
		visuaPane.setSpacing(3);
		visuaPane.setStyle("-fx-background-color: transparent");
		Scene visuaScene = new Scene(visuaPane, 200, 200);
		
		double space = visuaPane.getSpacing()*(nbin + 1) + nbin + 4.;
		
		double jump = 360.0 / (nbin*1.0);
		List<Color> colors = new ArrayList<Color>();
		for (int i = 0; i < nbin; i++) {
		    colors.add(Color.hsb(jump*i, 1.0f, 1.0f));
		}
		
		
		for(int i=0; i<nbin; i++) {
			Rectangle rec = new Rectangle((visuaPane.getWidth()-space)/nbin, 5);
			rec.setY(100);
			visuaPane.widthProperty().addListener((ev, oldv, newv)->{
				rec.setWidth((newv.doubleValue() - space)/nbin);
			});
			visuaPane.getChildren().add(rec);
			
			rec.setOpacity(0.2);;
			rec.setFill(colors.get(i));
			rectangleList.add(rec);
		}
	}
	
	public static void update(float[] magnitudes, float min) {
		int n = Math.min(magnitudes.length, rectangleList.size());
		
		for (int i = 0; i < n; i++) {
			rectangleList.get(i).setHeight(Math.max(Math.pow((magnitudes[i] - min)/Math.sqrt(nbin-i)/10, 3)*visuaPane.getHeight(), 5));
		}
	}

}
