package controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import models.TrackList;

public class PlayerController {
	//TODO make sure songs are closed properly
	// TODO handle media duration

	/**
	 * inizializzo i data member, mi dicono la canzone corrente e quella successiva
	 */
	private IntegerProperty currentTrack;
	private MediaPlayer player;
	private MediaPlayer loadingPlayer;
	private TrackList tracklist;
	private DoubleProperty volumeValue;
	private BooleanProperty muted;
	/**
	 * costruttore di default
	 */
	public PlayerController() {
	}


	public PlayerController(TrackList tracklist) {
		this.setTracklist(tracklist);
		this.setCurrentTrack(new SimpleIntegerProperty(0));
		this.muted = new SimpleBooleanProperty(false);
		this.volumeValue = new SimpleDoubleProperty(100);
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
	
	
	
	public void setVolumeValue(double volume) {
		this.setVolumeValue(new SimpleDoubleProperty((int) volume));
	}





	private void refresh() {
		if(this.getLoadingPlayer() != null && this.getLoadingPlayer().equals(this.getTracklist().get(this.currentInt()+1).getMediaPlayer())) {
			this.setPlayer(this.getLoadingPlayer());
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
		this.setVolume();
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


	public  DoubleProperty getVolumeValue() {
		return volumeValue;
	}


	public  void setVolumeValue(DoubleProperty volumeValue) {
		this.volumeValue.set(volumeValue.intValue());
		this.setVolume();
	}


	public  BooleanProperty getMuted() {
		return muted;
	}


	public void setMuted(BooleanProperty muted) {
		this.muted.set(muted.getValue());
		this.setVolume();
	}
	
	private void setVolume() {
		this.player.setVolume(this.getVolumeValue().doubleValue());
		this.player.setMute(this.getMuted().getValue());
	}

}

