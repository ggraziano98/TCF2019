package utils;

import javafx.collections.MapChangeListener;
import models.Track;

public class DataChangeListener implements MapChangeListener<String, Object> {
	
	Track track;
	
	public DataChangeListener(Track track) {
		this.track = track;
	}

	@Override
	public void onChanged(Change<? extends String, ?> change) {
		if (change.wasAdded()) {
      	  track.handleMetadata(change.getKey(), change.getValueAdded(), this.track);
        }
	}
	
}
