package app;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music{	
	private AudioInputStream audioInputStream;
	private Clip tetrisMusic;
	public Music()  {
		try {
		URL in = this.getClass().getResource("Tetris.wav");
		audioInputStream =  AudioSystem.getAudioInputStream(in); 
		tetrisMusic = AudioSystem.getClip();
		tetrisMusic.open(audioInputStream);
		}
		catch(Exception error){
			tetrisMusic.close();
		}

	}
	public void play() {
		tetrisMusic.start();
		tetrisMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
