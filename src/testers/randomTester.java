package testers;


import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import utils.Tools;

public class randomTester{
	
	
	private static final Object obj = new Object();

	public static void main(String[] args) {
		Path path = Paths.get("D:\\Music\\Dragonforce\\09 Heart of a Dragon.mp3");
		String cleanPathS = Tools.cleanURL(path.toString());
		

		//JFXPanel panel = new JFXPanel();
		try {
			Media media = new Media(cleanPathS);	
			
			MediaPlayer mp = new MediaPlayer(media);

			//QUESTION create listener class? 
			mp.setOnReady(()->{
			            String artistName=(String) mp.getMedia().getMetadata().get("artist");
			            System.out.println(artistName);
			            synchronized(obj){
			                obj.notify();
			        }
			});
			System.out.println("done");


		} catch (RuntimeException re) {
			System.out.println("path non leggibile");
			System.out.println(cleanPathS);
		}
	}

}
