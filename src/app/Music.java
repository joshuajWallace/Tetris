package app;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music{	
	private AudioInputStream audioInputStream;
	private Clip tetrisMusic;
	public Music() throws UnsupportedAudioFileException, 
	IOException, LineUnavailableException {
		File tetrisOST = new File("G:\\Project\\java\\Tetris\\src\\files\\Tetris.wav");
		audioInputStream =  AudioSystem.getAudioInputStream(tetrisOST.getAbsoluteFile()); 
		tetrisMusic = AudioSystem.getClip();
		tetrisMusic.open(audioInputStream);

	}
	public void play() {
		tetrisMusic.start();
		tetrisMusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
