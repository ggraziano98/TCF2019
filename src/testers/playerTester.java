package testers;

import controllers.PlayerController;
import models.TrackList;
import utils.Tools;

public class playerTester {

	public static void main(String[] args) {
		TrackList playlist = Tools.readPlaylist("Playlist playerTester");
		Tools.cout(playlist);
		
		
		PlayerController playerController = new PlayerController(playlist);
		playerController.play();
	}

}
