package controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import models.Track;
import models.TrackList;

public class PlayerController {
	//TODO make sure songs are closed properly

	/**
	 * inizializzo i data member, mi dicono la canzone corrente e quella successiva
	 */
	private IntegerProperty currentTrack;
	private MediaPlayer player;
	private MediaPlayer loadingPlayer;
	private TrackList tracklist;
	/**
	 * costruttore di default
	 */
	public PlayerController() {
	}


	public PlayerController(TrackList tracklist) {
		this.setTracklist(tracklist);
		this.setCurrentTrack(new SimpleIntegerProperty(0));
		this.refresh();
	}


	public PlayerController(TrackList tracklist, int firstSongPosition) {
		this.setTracklist(tracklist);
		this.setCurrentTrack(new SimpleIntegerProperty(firstSongPosition));
		this.refresh();
	}

	public void play() {
		if(!this.getPlayer().getStatus().equals(Status.UNKNOWN)) this.getPlayer().play();
		else {
			this.getPlayer().setOnReady(()-> {
				this.getPlayer().play();
			});
		}

	}


	public void pause() {
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.getPlayer().pause();
		}
	}


	public void next() {
		boolean playing = false;
		this.setCurrentTrack(new SimpleIntegerProperty(this.currentInt() + 1));
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.refresh();
			this.play();
		}
		else this.refresh();
	}


	public void prev() {
		boolean playing = false;
		this.setCurrentTrack(new SimpleIntegerProperty(this.currentInt() - 1));
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.refresh();
			this.play();
		}
		else this.refresh();
	}





	private void refresh() {
		if(this.getLoadingPlayer() != null && this.getLoadingPlayer().equals(this.getTracklist().get(this.currentInt()+1).getMediaPlayer())) {
			this.player = this.loadingPlayer;
			this.setLoadingPlayer(this.getTracklist().get(this.currentInt()+1).getMediaPlayer());
		}
		else {
			if(player != null) {
				player.stop();
				player = null;
			}
			try {
				this.setPlayer(this.getTracklist().get(this.getCurrentTrack().getValue()).getMediaPlayer());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}


	}




	public IntegerProperty getCurrentTrack() {
		return currentTrack;
	}


	public void setCurrentTrack(IntegerProperty currentTrack) {
		if(this.currentTrack != null) {
			if(currentTrack.intValue()>= 0) {
				this.currentTrack.set(currentTrack.intValue()%this.getTracklist().getSize());
			} else {
				//TODO better handling of this
				this.currentTrack.set(this.getTracklist().getSize() -1);
			}
		} else {
			if(currentTrack.intValue()>= 0) {
				this.currentTrack = new SimpleIntegerProperty(currentTrack.intValue()%(this.getTracklist().getSize()));
			} else {
				//TODO better handling of this
				this.currentTrack = new SimpleIntegerProperty(this.getTracklist().getSize() -1);
			}
		}
	}


	public MediaPlayer getPlayer() {
		return player;
	}


	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}


	public MediaPlayer getLoadingPlayer() {
		return loadingPlayer;
	}


	public void setLoadingPlayer(MediaPlayer loadingPlayer) {
		this.loadingPlayer = loadingPlayer;
	}


	public TrackList getTracklist() {
		return tracklist;
	}


	public void setTracklist(TrackList tracklist) {
		this.tracklist = tracklist;
	}

	private int currentInt() {
		return this.getCurrentTrack().getValue();
	}

}

