package com.group.game.dashdash;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.audio.Sound; // Added this import
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class AudioManager {

    private List<String> playlist = new ArrayList<>();
    private String lastPlayedSong = "";
    private double musicTimer = 0;
    private double currentSongDuration = 0;
    private boolean playlistStarted = false;

    private double fadeMultiplier = 0;
    private Music currentMusic;

    private boolean isInitialized = false;
    private double lastKnownSetting = -1;

    // --- NEW: Lists to store ALREADY LOADED sound objects ---
    private static final List<Sound> cachedJumpSounds = new ArrayList<>();
    private static final List<Sound> cachedCrashSounds = new ArrayList<>();

    public AudioManager() {
        playlist.add("TTEN.wav");
        playlist.add("LELN.wav");
        playlist.add("JANA.wav");

        // --- PRE-LOAD SOUNDS HERE ---
        // We load them once when the game starts so there is ZERO delay later
        try {
            cachedJumpSounds.add(getAssetLoader().loadSound("jump1.wav"));
            cachedJumpSounds.add(getAssetLoader().loadSound("jump2.wav"));

            cachedCrashSounds.add(getAssetLoader().loadSound("crash1.wav"));
            cachedCrashSounds.add(getAssetLoader().loadSound("crash2.wav"));
            cachedCrashSounds.add(getAssetLoader().loadSound("crash3.wav"));
            cachedCrashSounds.add(getAssetLoader().loadSound("crash4.wav"));
            cachedCrashSounds.add(getAssetLoader().loadSound("crash5.wav"));
        } catch (Exception e) {
            System.err.println("Error pre-loading sounds: " + e.getMessage());
        }
    }

    // ... (keep startPlaylist, forceNextSong, playNextSong, and onUpdate exactly as they are) ...

    public void startPlaylist() {
        if (playlistStarted) return;
        playlistStarted = true;
        playNextSong();
    }

    public void forceNextSong() {
        playNextSong();
    }

    private void playNextSong() {
        if (playlist.isEmpty()) return;

        if (currentMusic != null) {
            getAudioPlayer().stopMusic(currentMusic);
        }
        getAudioPlayer().stopAllMusic();

        List<String> availableSongs = new ArrayList<>(playlist);
        if (availableSongs.size() > 1 && !lastPlayedSong.isEmpty()) {
            availableSongs.remove(lastPlayedSong);
        }
        Collections.shuffle(availableSongs);
        String nextSong = availableSongs.get(0);
        lastPlayedSong = nextSong;

        try {
            currentMusic = getAssetLoader().loadMusic(nextSong);
            getAudioPlayer().playMusic(currentMusic);
        } catch (Exception e) {
            System.err.println("Could not load music: " + nextSong);
        }

        musicTimer = 0;
        fadeMultiplier = 0;
        lastKnownSetting = -1;

        currentSongDuration = switch (nextSong) {
            case "TTEN.wav" -> 95.0;
            case "LELN.wav" -> 80.0;
            case "JANA.wav" -> 93.0;
            default -> 100.0;
        };
    }

    public void onUpdate(double tpf) {
        if (!playlistStarted || currentMusic == null) return;

        if (!isInitialized) {
            double initialVol = getSettings().getGlobalMusicVolume();
            UserPrefs.setMasterVolume(initialVol);
            isInitialized = true;
            return;
        }

        musicTimer += tpf;
        double currentEngineVolume = getSettings().getGlobalMusicVolume();

        if (lastKnownSetting != -1 && Math.abs(currentEngineVolume - lastKnownSetting) > 0.001) {
            double newMaster = currentEngineVolume / Math.max(0.01, fadeMultiplier);
            UserPrefs.setMasterVolume(newMaster);
        }

        if (musicTimer <= 3.0) {
            fadeMultiplier = musicTimer / 3.0;
        } else if (musicTimer >= (currentSongDuration - 3.0)) {
            fadeMultiplier = (currentSongDuration - musicTimer) / 3.0;
        } else {
            fadeMultiplier = 1.0;
            UserPrefs.setMasterVolume(currentEngineVolume);
        }

        fadeMultiplier = Math.max(0, Math.min(1, fadeMultiplier));
        double targetVolume = UserPrefs.getMasterVolume() * fadeMultiplier;
        lastKnownSetting = targetVolume;
        getSettings().setGlobalMusicVolume(targetVolume);

        if (musicTimer >= currentSongDuration) {
            playNextSong();
        }
    }

    public static void playHoverSound() {
        getAudioPlayer().playSound(getAssetLoader().loadSound("hover.wav"));
    }

    public static void playJumpSound() {
        if (!cachedJumpSounds.isEmpty()) {
            List<Sound> copy = new ArrayList<>(cachedJumpSounds);
            Collections.shuffle(copy);
            getAudioPlayer().playSound(copy.get(0));
        }
    }

    public static void playCrashSound() {
        if (!cachedCrashSounds.isEmpty()) {
            List<Sound> copy = new ArrayList<>(cachedCrashSounds);
            Collections.shuffle(copy);
            getAudioPlayer().playSound(copy.get(0));
        }
    }
}