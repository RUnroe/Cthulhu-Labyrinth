package edu.neumont.cryptmakers.models;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioTrack {
    private AudioInputStream stream;
    private File track;
    private Clip clip;
    private long position;
    private FloatControl gainControl;

    public FloatControl getGainControl() {
        return gainControl;
    }

    public void setGainControl(FloatControl gainControl) {
        this.gainControl = gainControl;
    }

    public float getVolume() {
        return getGainControl().getValue();
    }

    public void setVolume(float volume) {
        getGainControl().setValue(volume);
    }

    private float getMinVolume() {
        return getGainControl().getMinimum();
    }

    private float getMaxVolume() {
        return getGainControl().getMaximum();
    }

    public void changeVolume(float deltaVolume) {
        float newVolume = getVolume() + deltaVolume;
        if (newVolume >= getMinVolume() && newVolume <= getMaxVolume()) {
            setVolume(newVolume);
        } else if (newVolume < getMinVolume()) {
            setVolume(getMinVolume());
        } else {
            setVolume(getMaxVolume());
        }
    }

    public AudioTrack(String filename) {
        try {
            setTrack(new File(filename));
            refresh(true);
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
        refresh(false);
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
        refresh(true);
        play();
    }

    //Raises volume
    public void increaseVolume() {
        changeVolume(+5.0f); // Increase volume by 5 decibels.
    }

    //Lowers volume
    public void decreaseVolume() {
        changeVolume(-5.0f); // Decrease volume by 5 decibels.
    }


    //Call to reset everything except the track file
    public void refresh(boolean startsAtZero) {
        try {
            setStream(AudioSystem.getAudioInputStream(getTrack()));
            setClip(AudioSystem.getClip());
            if (startsAtZero) setPosition(0L);
            getClip().open(getStream());
            setGainControl((FloatControl)getClip().getControl(FloatControl.Type.MASTER_GAIN));
            setVolume(-10.0f);

//            getClip().loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
