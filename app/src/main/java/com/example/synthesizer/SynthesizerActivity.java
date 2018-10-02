package com.example.synthesizer;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SynthesizerActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private SoundPool soundPool;
    private int noteA;
    private int noteBb;
    private int noteB;
    private int noteCs;
    private int noteC;
    private int noteDs;
    private int noteD;
    private int noteE;
    private int noteFs;
    private int noteF;
    private int noteG;
    private int noteGs;

    private int highNoteA;
    private int highNoteBb;
    private int highNoteB;
    private int highNoteCs;
    private int highNoteC;
    private int highNoteDs;
    private int highNoteD;
    private int highNoteE;
    private int highNoteFs;
    private int highNoteF;
    private int highNoteG;
    private int highNoteGs;


    private Button buttonA;
    private Button buttonBb;
    private Button buttonB;
    private Button buttonCs;
    private Button buttonC;
    private Button buttonDs;
    private Button buttonD;
    private Button buttonE;
    private Button buttonFs;
    private Button buttonF;
    private Button buttonG;
    private Button buttonGs;
    private Button buttonScale;
    private Button buttonChallenge1;
    private Button buttonChallenge2;
    private Button buttonPRecording;

    private Map<Integer, Integer> noteMap;

    private Switch recordSwitch;
    List<Integer> recordNotes = new ArrayList<>();

    public static final float DEFAULT_VOLUME = 1.0f;
    public static final int DEFAULT_PRIORITY = 1;
    public static final float DEFAULT_RATE = 1.0f;
    public static final int WHOLE_NOTE = 1000; //in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesizer);

        wireWidgets();
        setListeners();
        initializeSoundPool();
        initializeNoteMap();
    }

    private void initializeNoteMap() {
        noteMap = new HashMap<>();
        //store key:value pair in map
        //key us buttonId, value is noteId
        noteMap.put(R.id.button_synth_a, noteA);
        noteMap.put(R.id.button_synth_bb,noteBb);
        noteMap.put(R.id.button_synth_b,noteB);
        noteMap.put(R.id.button_synth_cs,noteCs);
        noteMap.put(R.id.button_synth_c,noteC);
        noteMap.put(R.id.button_synth_ds,noteDs);
        noteMap.put(R.id.button_synth_d,noteD);
        noteMap.put(R.id.button_synth_e,noteE);
        noteMap.put(R.id.button_synth_fs,noteFs);
        noteMap.put(R.id.button_synth_f,noteF);
        noteMap.put(R.id.button_synth_g,noteG);
        noteMap.put(R.id.button_synth_gs, noteGs);
    }

    private void initializeSoundPool() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        noteA = soundPool.load(this, R.raw.scalea, 1);
        noteBb = soundPool.load(this, R.raw.scalebb, 1);
        noteB = soundPool.load(this, R.raw.scaleb, 1);
        noteCs = soundPool.load(this, R.raw.scalecs, 1);
        noteC = soundPool.load(this, R.raw.scalec,1);
        noteD = soundPool.load(this, R.raw.scaled, 1);
        noteDs = soundPool.load(this, R.raw.scaleds, 1);
        noteE = soundPool.load(this, R.raw.scalee, 1);
        noteFs = soundPool.load(this, R.raw.scalefs,1);
        noteF = soundPool.load(this, R.raw.scalef,1);
        noteG = soundPool.load(this, R.raw.scaleg,1);
        noteGs = soundPool.load(this, R.raw.scalegs, 1);

        highNoteA = soundPool.load(this, R.raw.scalehigha,1);
        highNoteBb = soundPool.load(this, R.raw.scalehighbb,1);
        highNoteB = soundPool.load(this, R.raw.scalehighb,1);
        highNoteCs = soundPool.load(this, R.raw.scalehighcs,1);
        highNoteC = soundPool.load(this, R.raw.scalehighc,1);
        highNoteDs = soundPool.load(this, R.raw.scalehighds,1);
        highNoteD = soundPool.load(this, R.raw.scalehighd,1);
        highNoteE = soundPool.load(this, R.raw.scalehighe,1);
        highNoteFs = soundPool.load(this, R.raw.scalehighfs,1);
        highNoteF = soundPool.load(this, R.raw.scalehighf,1);
        highNoteGs = soundPool.load(this, R.raw.scalehighgs,1);
        highNoteG = soundPool.load(this, R.raw.scalehighg,1);
    }

    private void setListeners() {
        KeyboardNoteListener noteListener = new KeyboardNoteListener();

        buttonA.setOnClickListener(noteListener);
        buttonBb.setOnClickListener(noteListener);
        buttonB.setOnClickListener(noteListener);
        buttonCs.setOnClickListener(noteListener);
        buttonC.setOnClickListener(noteListener);
        buttonDs.setOnClickListener(noteListener);
        buttonD.setOnClickListener(noteListener);
        buttonE.setOnClickListener(noteListener);
        buttonFs.setOnClickListener(noteListener);
        buttonF.setOnClickListener(noteListener);
        buttonG.setOnClickListener(noteListener);
        buttonGs.setOnClickListener(noteListener);

        buttonScale.setOnClickListener(this);
        buttonChallenge1.setOnClickListener(this);
        buttonChallenge2.setOnClickListener(this);

        buttonA.setOnKeyListener(this);

        buttonPRecording.setOnClickListener(this);
        recordSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //recordNotes.clear();
                    recordNotes = new ArrayList<>();
                    Toast.makeText(SynthesizerActivity.this, "Recording", Toast.LENGTH_SHORT).show();
                }
                else if(!isChecked){
                    Toast.makeText(SynthesizerActivity.this, "Recording stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void wireWidgets() {
        buttonA = findViewById(R.id.button_synth_a);
        buttonBb = findViewById(R.id.button_synth_bb);
        buttonB = findViewById(R.id.button_synth_b);
        buttonCs = findViewById(R.id.button_synth_cs);
        buttonC = findViewById(R.id.button_synth_c);
        buttonDs = findViewById(R.id.button_synth_ds);
        buttonD = findViewById(R.id.button_synth_d);
        buttonE = findViewById(R.id.button_synth_e);
        buttonFs = findViewById(R.id.button_synth_fs);
        buttonF = findViewById(R.id.button_synth_f);
        buttonG = findViewById(R.id.button_synth_g);
        buttonGs = findViewById(R.id.button_synth_gs);
        buttonScale = findViewById(R.id.button_synth_scale);
        buttonChallenge1 = findViewById(R.id.button_synth_challenge1);
        buttonChallenge2 = findViewById(R.id.button_synth_challenge2);

        recordSwitch = findViewById(R.id.switch_main_record);
        buttonPRecording = findViewById(R.id.button_main_playRecording);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_synth_scale:
                playScale();
                break;
            case R.id.button_synth_challenge1:
                playChallenge1();
                break;
            case R.id.button_main_playRecording:
                playRecording();
                break;
        }
    }

    private void playChallenge1() {
        playNote(noteE);
        delay(WHOLE_NOTE);
        playNote(noteFs);
        delay(WHOLE_NOTE);
        playNote(noteG);
        delay(WHOLE_NOTE);
        playNote(highNoteA);
        delay(WHOLE_NOTE);
        playNote(highNoteB);
        delay(WHOLE_NOTE);
        playNote(highNoteCs);
        delay(WHOLE_NOTE);
        playNote(highNoteD);
        delay(WHOLE_NOTE);
        playNote(highNoteE);
    }

    private void playScale() {
        Song scale = new Song();

        scale.add(new Note(noteA));
        scale.add(new Note(noteBb));
        scale.add(new Note(noteB));

        playSong(scale);
    }

    private void playSong(Song scale) {
        for(Note note : scale.getNotes()) {
            playNote(note);
            delay(note.getDelay());
        }
    }

    private void delay(long duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playNote(int note, int loop) {
        soundPool.play(note, DEFAULT_VOLUME, DEFAULT_VOLUME, DEFAULT_PRIORITY, loop, DEFAULT_RATE);
    }

    private void playNote(int note){
        playNote(note, 0);
    }

    private void playNote(Note note){
        playNote(note.getNoteId(), 0);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_Q:
                Log.d("PRESS:", "one");
                check(noteA);
                return true;
        }
        if(keyCode == 9){
            check(noteBb);
        }
        if(keyCode == 51){
            check(noteB);
        }
        if(keyCode == 10){
            check(noteCs);
        }
        if(keyCode == 33){
            check(noteC);
        }
        if(keyCode == 11){
            check(noteDs);
        }
        if(keyCode == 46){
            check(noteD);
        }
        if(keyCode == 48){
            check(noteE);
        }
        if(keyCode == 13){
            check(noteFs);
        }
        if(keyCode == 53){
            check(noteF);
        }
        if(keyCode == 49){
            check(noteG);
        }
        if(keyCode == 14) {
            check(noteGs);
        }
        if(keyCode == 54) {
            check(highNoteA);
        }
        if(keyCode == 47) {
            check(highNoteBb);
        }
        if(keyCode == 52) {
            check(highNoteB);
        }
        if(keyCode == 32) {
            check(highNoteCs);
        }
        if(keyCode == 31) {
            check(highNoteC);
        }
        if(keyCode == 34) {
            check(highNoteDs);
        }
        if(keyCode == 50) {
            check(highNoteD);
        }
        if(keyCode == 30) {
            check(highNoteE);
        }
        if(keyCode == 36) {
            check(highNoteFs);
        }
        if(keyCode == 42) {
            check(highNoteF);
        }
        if(keyCode == 38) {
            check(highNoteGs);
        }
        if(keyCode == 41) {
            check(highNoteG);
        }

        return false;
    }

    private void check(int note) {
        if(!recordSwitch.isChecked()) {
            playNote(note);
        }
        else {
            playNote(note);
            recordNotes.add(note);
            Log.d("RECORDING:", recordNotes.toString());
        }
    }

    private void playRecording() {
        if(!recordSwitch.isChecked()){

            for(int notes:recordNotes){
                playNote(notes);
                delay(500);
            }
        }
        else {
            Toast.makeText(this, "Recording is still in progress >:(", Toast.LENGTH_SHORT).show();
        }
    }


    //make an inner class to handle button clicks for each note
    private class KeyboardNoteListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //get id of clicked button. use map to know what note to play, play note, profit
            int id = v.getId();
            int note = noteMap.get(id);
            playNote(note);
        }
    }

}
