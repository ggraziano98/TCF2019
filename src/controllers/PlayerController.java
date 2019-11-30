package controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import models.Track;
import models.TrackList;
import utils.Tools;

public class PlayerController {
	//TODO make sure songs are closed properly

	/**
	 * inizializzo i data member, mi dicono la canzone corrente e quella successiva
	 */
	private IntegerProperty currentTrack;
	private MediaPlayer player;
	private MediaPlayer loadingPlayer;
	private TrackList tracklist;
	private DoubleProperty volumeValue;
	private BooleanProperty muted;
	private DoubleProperty totalDuration;
	private DoubleProperty currentTime;
	private BooleanProperty playing;
	/**
	 * costruttore di default
	 */
	public PlayerController() {
	}


	public PlayerController(TrackList tracklist) {
		this.playing = new SimpleBooleanProperty(false);
		this.setTracklist(tracklist);
		this.setCurrentTrack(new SimpleIntegerProperty(0));
		this.muted = new SimpleBooleanProperty(false);
		this.volumeValue = new SimpleDoubleProperty(1);
		this.volumeValue.addListener((obs, oldv, newv)->{
			this.setVolume();
		});
		this.totalDuration = new SimpleDoubleProperty(0);
		this.currentTime = new SimpleDoubleProperty(0);
		this.refresh();
	}


	public PlayerController(TrackList tracklist, int firstSongPosition) {
		this.playing = new SimpleBooleanProperty(false);
		this.setTracklist(tracklist);
		this.setCurrentTrack(new SimpleIntegerProperty(firstSongPosition));
		this.muted = new SimpleBooleanProperty(false);
		this.volumeValue = new SimpleDoubleProperty(1);
		this.volumeValue.addListener((obs, oldv, newv)->{
			this.setVolume();
		});
		this.totalDuration = new SimpleDoubleProperty(0);
		this.currentTime = new SimpleDoubleProperty(0);
		this.refresh();
	}

	public void play() {
		if(!this.getPlayer().getStatus().equals(Status.UNKNOWN)) {
			this.setTotalDuration(this.getPlayer().getMedia().getDuration());
			this.getPlayer().play();
			this.getPlayer().setOnEndOfMedia(()->this.next());
		}
		else {
			this.getPlayer().setOnReady(()-> {
				this.getPlayer().play();
				this.setTotalDuration(this.getPlayer().getMedia().getDuration());
			});
			this.getPlayer().setOnEndOfMedia(()->this.next());
		}
		
		this.setPlaying(new SimpleBooleanProperty(true));

	}


	public void pause() {
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.getPlayer().pause();
		}
		this.setPlaying(new SimpleBooleanProperty(false));
	}


	public void next() {
		this.setCurrentTrack(new SimpleIntegerProperty(this.currentInt() + 1));
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.refresh();
			this.play();
		}
		else this.refresh();
	}


	public void prev() {
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
	
	
	
	
	/**
	 * sposta il player al time desiderato 
	 * 
	 * @param time (Duration) tempo in millisecondi
	 */
	public void seek(Duration time) {
		this.getPlayer().seek(time);
	}





	public void refresh() {
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
				this.currentTrack.set(this.getTracklist().getSize() -1);
			}
		} else {
			if(currentTrack.intValue()>= 0) {
				this.currentTrack = new SimpleIntegerProperty(currentTrack.intValue()%(this.getTracklist().getSize()));
			} else {
				this.currentTrack = new SimpleIntegerProperty(this.getTracklist().getSize() -1);
			}
		}
	}


	public MediaPlayer getPlayer() {
		return player;
	}


	public void setPlayer(MediaPlayer player) {
		this.player = player;
		this.setPlaying(new SimpleBooleanProperty(player.getStatus().equals(Status.PLAYING)));
		this.setVolume();
		this.player.currentTimeProperty().addListener((obs, oldv, newv)->{
			this.setCurrentTime(new SimpleDoubleProperty(newv.toSeconds()));
		});
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

	public int currentInt() {
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

	
	/**
	 * 
	 * @return duration in seconds
	 */
	public DoubleProperty getTotalDuration() {
		return totalDuration;
	}


	public void setTotalDuration(Duration totalDuration) {
		this.totalDuration.set(totalDuration.toSeconds());
	}

	/**
	 * 
	 * @return time in seconds
	 */
	public DoubleProperty getCurrentTime() {
		return currentTime;
	}


	public void setCurrentTime(DoubleProperty currentTime) {
		this.currentTime.set(currentTime.doubleValue());
	}


	
	public BooleanProperty getPlaying() {
		return playing;
	}
	
	
	
	public void setPlaying(BooleanProperty playing) {
		this.playing.set(playing.getValue());
	}
	
	

}

