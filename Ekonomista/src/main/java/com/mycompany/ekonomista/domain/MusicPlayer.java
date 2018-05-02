/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ekonomista.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author samisaukkonen
 */
public class MusicPlayer {
    private String adress;
    
    public MusicPlayer(String adress) {
        this.adress = adress;
    }
    
    /**
     * Plays music on an infinite loop from the address that was given to this class when it was created.
     * 
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException 
     */
    public void playMusicOnLoop() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Media hit = new Media(new File(adress).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        
        mediaPlayer.play();
    }
}
