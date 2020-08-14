package edu.neumont.cryptmakers.models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioTrack {
    private AudioInputStream stream;
    private File track;
    private Clip clip;
    private long position;

    public AudioTrack(String filename) {
        try {
            setTrack(new File(filename));
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AudioInputStream getStream() {
        return stream;
    }

    public void setStream(AudioInputStream stream) {
        this.stream = stream;
    }

    public File getTrack() {
        return track;
    }

    public void setTrack(File track) {
        this.track = track;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    //Plays audio
    public void play() {
        clip.start();
    }

    //Stores the current position in the clip before stopping
    public void pause() {
        setPosition(getClip().getMicrosecondPosition());
        System.out.println("Pos: " + getPosition());
        getClip().stop();
        System.out.println("Clip stopped..");
        getClip().close();
        System.out.println("Clip closed");
    }

    //Refreshes track with stored clip position and plays
    public void resume() {
        refresh();
        System.out.println("Track refreshed..");
        getClip().setMicrosecondPosition(getPosition());
        System.out.println("Pos: " + getPosition());
        play();
        System.out.println("Audio resumed");
    }

    //Stops playback and sets position back to zero
    public void stop() {
        getClip().stop();
        System.out.println("Clip stopped");
        getClip().close();
        System.out.println("Clip closed");
        setPosition(0L);
        System.out.println("Position reset");
    }

    //Uses stop method and automatically starts playing again from the beginning
    public void restart() {
        stop();
        refresh();
        play();
    }


    //Call to reset everything except the track file
    public void refresh() {
        try {
            setStream(AudioSystem.getAudioInputStream(getTrack()));
            setClip(AudioSystem.getClip());
            setPosition(0L);
            getClip().open(getStream());
//            getClip().loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
