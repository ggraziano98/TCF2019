package controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import models.Track;
import models.TrackList;
import userinterface.MainApp;
import userinterface.Visualizer;
import utils.Tools;

// Classe che si occupa della gestione delle track da far suonare attraverso una tracklist interna
public class PlayerController {

	/**
	 * inizializzo i data member, mi dicono la canzone corrente e quella successiva
	 */
	private Track currentTrack;
	private MediaPlayer player;
	private TrackList tracklist;
	private IntegerProperty currentInt;

	private DoubleProperty volumeValue;
	private DoubleProperty totalDuration;
	private DoubleProperty currentTime;

	private BooleanProperty playing;
	private BooleanProperty muted;
	public TrackList previousTracklist;
	/**
	 * costruttore di default
	 */
	public PlayerController() {
		this.playing = new SimpleBooleanProperty(false);
		this.muted = new SimpleBooleanProperty(false);
		this.volumeValue = new SimpleDoubleProperty(1);
		this.volumeValue.addListener((obs, oldv, newv)->{
			this.setVolume();
		});
		this.totalDuration = new SimpleDoubleProperty(0);
		this.currentTime = new SimpleDoubleProperty(0);
		this.tracklist = new TrackList();
		this.setCurrentInt(0);
	}


	public PlayerController(TrackList tracklist) {
		this.setTracklist(tracklist);

		this.playing = new SimpleBooleanProperty(false);
		this.setTracklist(tracklist);
		this.muted = new SimpleBooleanProperty(false);
		this.volumeValue = new SimpleDoubleProperty(1);
		this.volumeValue.addListener((obs, oldv, newv)->{
			this.setVolume();
		});
		this.totalDuration = new SimpleDoubleProperty(0);
		this.currentTime = new SimpleDoubleProperty(0);
		this.setCurrentInt(0);
	}

	public void play() {
		MediaPlayer player = this.getPlayer();
		player.setOnReady(()-> {
			player.play();
			this.setTotalDuration(player.getMedia().getDuration());
		});
		this.setTotalDuration(player.getMedia().getDuration());
		player.play();
		this.getCurrentTrack().setPlaying(true);
		
		player.setAudioSpectrumInterval(0.05);
		player.setAudioSpectrumNumBands(Visualizer.nbin);
		player.setAudioSpectrumThreshold(-90);
		player.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
			Visualizer.update(magnitudes, player.getAudioSpectrumThreshold());
		});


		player.setOnEndOfMedia(()->this.next());
		this.setPlaying(true);

	}


	public void pause() {
		if(this.getPlayer().getStatus().equals(Status.PLAYING)) {
			this.getPlayer().pause();
		}
		this.setPlaying(false);
	}


	public void next() {
		int newPos = this.getCurrentInt() + 1;
		if (MainApp.repeat == 2) newPos = this.getCurrentInt(); //repeat current


		if(this.getCurrentInt() == this.getTracklist().getSize() - 1) {
			if(MainApp.repeat == 1) newPos = 0; //repeat all		
			else {
				this.setPlaying(false);
				newPos = 0;
			}
		}

		this.setCurrentTrack(this.getTracklist().get(newPos));
		if(this.getPlaying()) {
			this.play();
		}
	}


	public void prev() {
		this.setCurrentTrack(this.getTracklist().get(Math.max(this.getCurrentInt() - 1, 0)));
		if(this.getPlaying()) {
			this.play();
		}
	}


	/**
	 * sposta il player al time desiderato
	 *
	 * @param time (Duration) tempo in millisecondi
	 */
	public void seek(Duration time) {
		this.getPlayer().seek(time);
	}





	public void refreshPlayer() {
//		if (this.getCurrentTrack() != null) this.getCurrentTrack().setPlaying(false);

		this.getCurrentTrack().setPlaying(true);

		this.setCurrentInt(this.getCurrentTrack().getPosition());

		try {
			MediaPlayer mp = new MediaPlayer(new Media(Tools.cleanURL(this.getCurrentTrack().getPath().toString())));
			this.setPlayer(mp);
		} catch (Exception e) {
			Tools.stackTrace(e);
		}
		this.setTotalDuration(this.getCurrentTrack().getDuration());
	}




	public final Track getCurrentTrack() {
		return currentTrack;
	}


	public final void setCurrentTrack(Track currentTrack) {
		if(this.currentTrack != null) {
			this.currentTrack.setPlaying(false);
		}
		this.currentTrack = currentTrack;
		this.refreshPlayer();
	}


	public final MediaPlayer getPlayer() {
		return player;
	}


	private final void setPlayer(MediaPlayer newPlayer) {
		if(player != null) {
			player.stop();
			player.dispose();
			player = null;
		}
		this.player = newPlayer;
		this.setVolume();
		this.player.currentTimeProperty().addListener((obs, oldv, newv)->{
			this.setCurrentTime(newv.toSeconds());
		});
	}


	public final TrackList getTracklist() {
		return tracklist;
	}


	public final void setTracklist(TrackList tracklist) {
		this.tracklist.set(new TrackList(tracklist));
		this.setCurrentInt(-1);
	}


	public final int getCurrentInt() {
		return currentInt.get();
	}


	public IntegerProperty currentIntProperty() {
		return currentInt;
	}


	private final void setCurrentInt(int currentInt) {	
		if(this.currentInt == null) this.currentInt = new SimpleIntegerProperty(0);
		this.currentInt.set(currentInt);
	}


	public final double getVolumeValue() {
		return volumeValue.get();
	}


	public DoubleProperty volumeValueProperty() {
		return volumeValue;
	}


	public final void setVolumeValue(double volumeValue) {
		if(this.volumeValue==null) this.setVolumeValue(volumeValue);
		this.volumeValue.set(volumeValue);
		this.setVolume();
	}


	public final boolean getMuted() {
		return muted.get();
	}


	public BooleanProperty mutedProperty() {
		return muted;
	}


	public final void setMuted(boolean muted) {
		if (this.muted == null) this.muted = new SimpleBooleanProperty(false);
		this.muted.set(muted);
		this.setVolume();
	}



	private void setVolume() {
		if(this.player != null) {
			this.player.setVolume(this.getVolumeValue());
			this.player.setMute(this.getMuted());
		}
	}


	/**
	 *
	 * @return duration in seconds
	 */
	public final double getTotalDuration() {
		return totalDuration.get();
	}


	public DoubleProperty totalDurationProperty() {
		return totalDuration;
	}


	private final void setTotalDuration(Duration totalDuration) {
		if(this.totalDuration == null) this.totalDuration = new SimpleDoubleProperty(totalDuration.toSeconds());
		this.totalDuration.set(totalDuration.toSeconds());
	}

	/**
	 *
	 * @return time in seconds
	 */
	public final double getCurrentTime() {
		return currentTime.get();
	}


	public DoubleProperty currentTimeProperty() {
		return currentTime;
	}


	private final void setCurrentTime(double currentTime) {
		if(this.currentTime == null) this.currentTime = new SimpleDoubleProperty(0);
		this.currentTime.set(currentTime);
	}



	public final boolean getPlaying() {
		return playing.get();
	}


	public BooleanProperty playingProperty() {
		return playing;
	}


	public final void setPlaying(boolean playing) {
		if(this.playing == null) this.playing = new SimpleBooleanProperty(false);
		this.playing.set(playing);

	}

	public void refreshCurrentInt() {
		this.currentInt.set(this.currentTrack.getPosition());
	}

}
