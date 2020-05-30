package userinterface;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import models.Track;
import models.TrackList;
import utils.Tools;


public class PlaylistStuff {


	/**
	 * Usato per definire lo scrollpane a sinistra in cui si vedono tutte le playlist salvate
	 *
	 * @return ScrollPane playlist
	 */
	
	
    
	public static VBox playlist() throws Exception{

		VBox playlistMain = new VBox();
		playlistMain.setId("transparent");


		HBox addBox = new HBox();
		addBox.setMinWidth(0.9*Tools.DWIDTHS[0]);
		addBox.setMaxWidth(0.9*Tools.DWIDTHS[0]);
		Text leTuePlaylists = new Text("  Le tue playlists");
		leTuePlaylists.setTextAlignment(TextAlignment.LEFT);
		leTuePlaylists.setFont(Font.font("", FontPosture.ITALIC, 0.45*Tools.DHEIGHTS[1]));
		leTuePlaylists.setFill(Color.WHITE);
		
		
		Double listSize = (double) Tools.DHEIGHTS[4]*0.25;		
		
		SVGPath plus = new SVGPath();
		plus.setContent("M400 32H48C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48V80c0-26.5-21.5-48-48-48zm-32 252c0 6.6-5.4 12-12 12h-92v92c0 6.6-5.4 12-12 12h-56c-6.6 0-12-5.4-12-12v-92H92c-6.6 0-12-5.4-12-12v-56c0-6.6 5.4-12 12-12h92v-92c0-6.6 5.4-12 12-12h56c6.6 0 12 5.4 12 12v92h92c6.6 0 12 5.4 12 12v56z");
		final Region plusShape = PlayerBuilder.iconShape(plus, listSize, PlayerBuilder.black);
		final Region plusShapeTrasp = PlayerBuilder.iconShape(plus, listSize, PlayerBuilder.blackTrasp);
		
		Button addButton = new Button("",plusShapeTrasp);
		addButton.setId("buttons");
		PlayerBuilder.buttonMouseIconRegion(addButton, plusShape, plusShapeTrasp);
		addButton.setTranslateX(Tools.DWIDTHS[0]/4-10);
		addButton.setOnMouseClicked((e) -> {
			Tools.newPlaylist();
		});
		
		
		SVGPath sortA = new SVGPath();
		sortA.setContent("M176 352h-48V48a16 16 0 0 0-16-16H80a16 16 0 0 0-16 16v304H16c-14.19 0-21.36 17.24-11.29 27.31l80 96a16 16 0 0 0 22.62 0l80-96C197.35 369.26 190.22 352 176 352zm240-64H288a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h56l-61.26 70.45A32 32 0 0 0 272 446.37V464a16 16 0 0 0 16 16h128a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16h-56l61.26-70.45A32 32 0 0 0 432 321.63V304a16 16 0 0 0-16-16zm31.06-85.38l-59.27-160A16 16 0 0 0 372.72 32h-41.44a16 16 0 0 0-15.07 10.62l-59.27 160A16 16 0 0 0 272 224h24.83a16 16 0 0 0 15.23-11.08l4.42-12.92h71l4.41 12.92A16 16 0 0 0 407.16 224H432a16 16 0 0 0 15.06-21.38zM335.61 144L352 96l16.39 48z");
		final Region sortAShape = PlayerBuilder.iconShape(sortA, listSize, PlayerBuilder.black);
		final Region sortAShapeTrasp = PlayerBuilder.iconShape(sortA, listSize, PlayerBuilder.blackTrasp);
		
		SVGPath sortZ = new SVGPath();
		sortZ.setContent("M176 352h-48V48a16 16 0 0 0-16-16H80a16 16 0 0 0-16 16v304H16c-14.19 0-21.36 17.24-11.29 27.31l80 96a16 16 0 0 0 22.62 0l80-96C197.35 369.26 190.22 352 176 352zm112-128h128a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16h-56l61.26-70.45A32 32 0 0 0 432 65.63V48a16 16 0 0 0-16-16H288a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h56l-61.26 70.45A32 32 0 0 0 272 190.37V208a16 16 0 0 0 16 16zm159.06 234.62l-59.27-160A16 16 0 0 0 372.72 288h-41.44a16 16 0 0 0-15.07 10.62l-59.27 160A16 16 0 0 0 272 480h24.83a16 16 0 0 0 15.23-11.08l4.42-12.92h71l4.41 12.92A16 16 0 0 0 407.16 480H432a16 16 0 0 0 15.06-21.38zM335.61 400L352 352l16.39 48z");
		final Region sortZShape = PlayerBuilder.iconShape(sortZ, listSize, PlayerBuilder.black);
		final Region sortZShapeTrasp = PlayerBuilder.iconShape(sortZ, listSize, PlayerBuilder.blackTrasp);
		
		
		Button sortByNameButton = new Button("",sortAShapeTrasp);	
		sortByNameButton.setId("buttons");
		PlayerBuilder.buttonMouseIconRegion(sortByNameButton, sortAShape, sortAShapeTrasp);
		sortByNameButton.setTranslateX(Tools.DWIDTHS[0]/4-10);
		AtomicBoolean sortNameValue = new AtomicBoolean(true);
		sortByNameButton.setOnMouseClicked((e) -> {
			PlaylistStuff.sortPlaylistsByName(sortNameValue.get());
			if (sortNameValue.get() == true) {
				sortByNameButton.setGraphic(sortAShape);
				PlayerBuilder.buttonMouseIconRegion(sortByNameButton, sortAShape, sortAShapeTrasp);
			}
			else {
				sortByNameButton.setGraphic(sortZShape);
				PlayerBuilder.buttonMouseIconRegion(sortByNameButton, sortZShape, sortZShapeTrasp);
			}
			sortNameValue.set(!sortNameValue.get());
		});		
		
		
		SVGPath sortS = new SVGPath();
		sortS.setContent("M304 416h-64a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h64a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16zm-128-64h-48V48a16 16 0 0 0-16-16H80a16 16 0 0 0-16 16v304H16c-14.19 0-21.37 17.24-11.29 27.31l80 96a16 16 0 0 0 22.62 0l80-96C197.35 369.26 190.22 352 176 352zm256-192H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h192a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16zm-64 128H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h128a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16zM496 32H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h256a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16z");
		final Region sortSShape = PlayerBuilder.iconShape(sortS, listSize, PlayerBuilder.black);
		final Region sortSShapeTrasp = PlayerBuilder.iconShape(sortS, listSize, PlayerBuilder.blackTrasp);
		
		SVGPath sortL = new SVGPath();
		sortL.setContent("M240 96h64a16 16 0 0 0 16-16V48a16 16 0 0 0-16-16h-64a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16zm0 128h128a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16zm256 192H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16h256a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16zm-256-64h192a16 16 0 0 0 16-16v-32a16 16 0 0 0-16-16H240a16 16 0 0 0-16 16v32a16 16 0 0 0 16 16zm-64 0h-48V48a16 16 0 0 0-16-16H80a16 16 0 0 0-16 16v304H16c-14.19 0-21.37 17.24-11.29 27.31l80 96a16 16 0 0 0 22.62 0l80-96C197.35 369.26 190.22 352 176 352z");
		final Region sortLShape = PlayerBuilder.iconShape(sortL, listSize, PlayerBuilder.black);
		final Region sortLShapeTrasp = PlayerBuilder.iconShape(sortL, listSize, PlayerBuilder.blackTrasp);
		
		
		Button sortByDurationButton = new Button("",sortSShapeTrasp);	
		sortByDurationButton.setId("buttons");
		PlayerBuilder.buttonMouseIconRegion(sortByDurationButton, sortSShape, sortSShapeTrasp);
		sortByDurationButton.setTranslateX(Tools.DWIDTHS[0]/4-10);
		AtomicBoolean sortDurationValue = new AtomicBoolean(true);
		sortByDurationButton.setOnMouseClicked((e) -> {
			PlaylistStuff.sortPlaylistsByDuration(sortDurationValue.get());
			if (sortDurationValue.get() == false) {
				sortByDurationButton.setGraphic(sortLShape);
				PlayerBuilder.buttonMouseIconRegion(sortByDurationButton, sortLShape, sortLShapeTrasp);
			}
			else {
				sortByDurationButton.setGraphic(sortSShape);
				PlayerBuilder.buttonMouseIconRegion(sortByDurationButton, sortSShape, sortSShapeTrasp);
			}
			sortDurationValue.set(!sortDurationValue.get());
		});


		addBox.getChildren().addAll(leTuePlaylists, addButton, sortByNameButton, sortByDurationButton);


		VBox playlistsVbox = MainApp.playlistsVbox;
		playlistsVbox.setStyle("-fx-background-color: transparent");

		ScrollPane scroll = new ScrollPane();
		scroll.setContent(playlistsVbox);
		scroll.setFitToHeight(true);


		MainApp.savedPlaylists.forEach((String name)->{
			createPlaylistView(name, playlistsVbox);

		});


		MainApp.savedPlaylists.addListener((ListChangeListener<String>) c-> {
			while(c.next()) {

				c.getAddedSubList().forEach(s->{
					createPlaylistView(s, playlistsVbox);
				});
				c.getRemoved().forEach(s->{
					RadioButton tbremoved = new RadioButton();

					for(int i=0; i<playlistsVbox.getChildren().size(); i++) {
						RadioButton playlist = (RadioButton) playlistsVbox.getChildren().get(i);
						if((playlist).getText() == s) {
							MainApp.root.getChildren().remove(playlist.getUserData());
							tbremoved = playlist;
						}
					}

					playlistsVbox.getChildren().remove(tbremoved);
					MainApp.playlistList.removeIf(tl-> tl.getPlaylistName()==s);

				});

			}
		});

		playlistMain.getChildren().addAll(addBox, scroll);
		playlistMain.setId("transparent");


		return playlistMain;
	}


	/**
	 * Funzione che crea i bottoni da aggiungere allo scrollpane delle playlist
	 *
	 * @param string
	 * @param box
	 * @param mainPanel
	 * @param dataPane
	 */
	private static void playlistButton(String string, VBox box, ToggleGroup mainPanel, VBox dataPane) {
		RadioButton playlistButton = new RadioButton(string);
		playlistButton.getStyleClass().remove("radio-button");
		playlistButton.getStyleClass().add("toggle-button");

		double width = Tools.DWIDTHS[0]*0.95;
		playlistButton.setMinWidth(width);
		playlistButton.setMaxWidth(width);
		playlistButton.setAlignment(Pos.CENTER_LEFT);
		playlistButton.setToggleGroup(mainPanel);
//		playlistButton.setStyle(Tools.TRANSBUTT);
//		playlistButton.setOnMouseMoved((e)->{
//			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
//			else playlistButton.setStyle(Tools.BOLDBUTT);
//		});
//		playlistButton.setOnMouseExited((e)->{
//			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
//			else playlistButton.setStyle(Tools.TRANSBUTT);
//		});
//		playlistButton.selectedProperty().addListener((e)->{
//			if(playlistButton.isSelected()) playlistButton.setStyle(Tools.SELBUTT);
//			else playlistButton.setStyle(Tools.TRANSBUTT);
//		});
		box.getChildren().add(playlistButton);
		dataPane.setVisible(false);
		playlistButton.setUserData(dataPane);

		ContextMenus.contextMenuPlaylists(playlistButton); //Add context menu




	}

	private static void createPlaylistView(String name, VBox playlistsVbox) {
		TrackList tracklist = Tools.readPlaylist(name);
		tracklist.setPlaylistName(name);
		TableView<Track> table = TrackView.tableFromTracklist(tracklist, MainApp.pc);
		ContextMenus.contextMenuTrackPlaylist(table, tracklist);
		VBox tableBox = new VBox(table);
		VBox.setVgrow(table, Priority.ALWAYS);
		MainApp.root.add(tableBox, 1, 2, 1, 2);
		playlistButton(name, playlistsVbox, MainApp.mainPanel, tableBox);

		MainApp.playlistList.add(tracklist);


		TrackView.setDragDrop(table, tracklist);

	}

	/**
	 * ordina la playlist in base al nome
	 * 
	 * @param aToZ true if A->Z, false if Z->A
	 */
	public static void sortPlaylistsByName(boolean aToZ) {
		List<Node> list = new ArrayList<Node>(MainApp.playlistsVbox.getChildren());

		list.sort(new NameComparator());
		if(!aToZ) Collections.reverse(list);
		MainApp.playlistsVbox.getChildren().clear();
		MainApp.playlistsVbox.getChildren().addAll(list);
	}


	/**
	 * ordina le playlist in base alla durata
	 * 
	 * @param smallerFirst true if smaller goes first
	 */
	public static void sortPlaylistsByDuration(boolean smallerFirst) {
		List<Node> list = new ArrayList<Node>(MainApp.playlistsVbox.getChildren());

		list.sort(new DurationComparator());
		if(smallerFirst) Collections.reverse(list);
		MainApp.playlistsVbox.getChildren().clear();
		MainApp.playlistsVbox.getChildren().addAll(list);

		/*UNUSED
		 * 
		HashMap<String, Double> map = new HashMap<String, Double>();

		for (TrackList playlist : MainApp.playlistList) {
			map.put(playlist.getPlaylistName(), playlist.totalDuration().toSeconds());
		}

		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String, Double> sortedmap = new TreeMap<String, Double>(bvc); 
		sortedmap.putAll(map);

		MainApp.savedPlaylists.clear();

		for (String playlistName : sortedmap.keySet()) {
			MainApp.savedPlaylists.add(playlistName);
		}
		 */

	}

}

class NameComparator implements Comparator<Node> {
	@Override
	public int compare(Node a, Node b) {
		return ((Labeled) a).getText().compareToIgnoreCase(((Labeled) b).getText());
	}
}


class DurationComparator implements Comparator<Node> {

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Node a, Node b) {
		TrackList ta = (TrackList) ((TableView<Track>) ((VBox) a.getUserData()).getChildren().get(0)).getUserData();
		TrackList tb = (TrackList) ((TableView<Track>) ((VBox) b.getUserData()).getChildren().get(0)).getUserData();
		if (tb.totalDuration().greaterThan(ta.totalDuration())) return 1;
		else {
			if(tb.totalDuration().lessThan(ta.totalDuration())) return -1;
			return ta.getPlaylistName().compareToIgnoreCase(tb.getPlaylistName());
		}
	}


	/*UNUSED
	 * 
	Map<String, Double> base;

	public ValueComparator(Map<String, Double> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) <= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
	 */
}