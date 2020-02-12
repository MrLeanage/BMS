package util.playAudio;

import javafx.scene.media.AudioClip;

public class Audio {
    public void AddItemPlay(){
        AudioClip note = new AudioClip(this.getClass().getResource("/lib/SoundTracks/beep-bit.wav").toString());
        note.play();
    }
}
