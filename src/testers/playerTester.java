package testers;

import controllers.PlayerController;
import javafx.util.Duration;
import models.Track;
import models.TrackList;
import utils.Tools;

public class playerTester {

	public static void main(String[] args) {
		TrackList playlist = Tools.readPlaylist("Playlist playerTester");
		Track track = playlist.get(2);
		Tools.cout(playlist);
		
		
		PlayerController playerController = new PlayerController(playlist);
		playerController.play();
	}

}
