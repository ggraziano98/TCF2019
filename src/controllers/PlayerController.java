package controllers;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Path;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import models.Track;
import models.TrackList;
import userinterface.Player;
import utils.Tools;

public class PlayerController {
	/**
	 * inizializzo i data member, mi dicono la canzone corrente e quella successiva
	 */
	private Track currentTrack;
	private Track prevTrack;
	private Track nextTrack;
	private MediaPlayer player;
	/**
	 * costruttore di default
	 */
	public PlayerController() {
	}

	public void playTrack(Track track, TrackList tracklist) {

		// Set next, current and previous track

		    currentTrack = track;
		//
		    int totalTracks = tracklist.getSize();
		    int currentTrackNumber = tracklist.getIndex(track);
		    int nextTrackNumber = currentTrackNumber+1;
		    int prevTrackNumber = currentTrackNumber-1;

		// Play track and set media info

		if (player != null) {

			player.stop();

			player = null;

		}

		try {
			String cleanPathS = Tools.cleanURL(track.getPath().toString());

			Media media = new Media(cleanPathS);
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			System.out.println(mediaPlayer.getStatus());
			mediaPlayer.play(); 
			mediaPlayer.setStopTime(track.getDuration());
			System.out.println(mediaPlayer.getStatus());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
