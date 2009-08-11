/*
 * Copyright (c) 2009, Code Aurora Forum. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *        * Redistributions of source code must retain the above copyright
 *            notice, this list of conditions and the following disclaimer.
 *        * Redistributions in binary form must reproduce the above copyright
 *            notice, this list of conditions and the following disclaimer in the
 *            documentation and/or other materials provided with the distribution.
 *        * Neither the name of Code Aurora nor
 *            the names of its contributors may be used to endorse or promote
 *            products derived from this software without specific prior written
 *            permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT ARE DISCLAIMED.    IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.quic.fmradio;

import android.content.Context;
import android.preference.PreferenceCategory;

import java.util.ArrayList;
import java.util.List;

public class PresetList extends PreferenceCategory {
    private    List<PresetStation> mPresetList = new ArrayList<PresetStation>();
    private    int mCurrentStation = 0;
    private    String mListName = "";
    Context mContext;

    public PresetList(Context context, String name) {
        super(context);
        mContext = context;
        mListName = name;
    }

    public String getName(){
        return mListName;
    }

    public String toString(){
        return mListName;
    }

    public synchronized int getStationCount(){
        return mPresetList.size();
    }

    public synchronized String getStationName(int stationNum){
        String name = "";
        if (mPresetList.size() > stationNum){
            name = mPresetList.get(stationNum).getName();
        }
        return name;
    }

    public synchronized double getStationFrequency(int stationNum){
        double frequency = 100.0;
        if (mPresetList.size() > stationNum){
            frequency = mPresetList.get(stationNum).getFrequency();
        }
        return frequency;
    }

    public void setName(String name){
        mListName = name;
    }

    public synchronized void setStationFrequency(int stationNum, double frequency){
        PresetStation mStation = mPresetList.get(stationNum);
        mStation.setFrequency(frequency);
    }

    public synchronized void setStationName(int stationNum, String name){
        PresetStation mStation = mPresetList.get(stationNum);
        mStation.setName(name);
    }

    public synchronized PresetStation getStationFromIndex(int index){
        int totalPresets = mPresetList.size();
        PresetStation station = null;
        if (index < totalPresets) {
            station = mPresetList.get(index);
        }
        return station;
    }

    public synchronized PresetStation getStationFromFrequency(double frequency){
        int totalPresets = mPresetList.size();
        for (int presetNum = 0; presetNum < totalPresets; presetNum++ ) {
            PresetStation station = mPresetList.get(presetNum);
            if (station != null) {
                if(frequency == station.getFrequency()) {
                    return station;
                }
            }
        }
        return null;
    }

    public synchronized PresetStation addStation(String name, double freq){
        PresetStation addStation = new PresetStation(name, freq);
        if(addStation != null) {
            mPresetList.add(addStation);
        }
        return addStation;
    }

    public synchronized PresetStation addStation(PresetStation station){
        PresetStation addStation = null;
        if(station != null) {
            addStation = new PresetStation (station);
            mPresetList.add(addStation);
        }
        return addStation;
    }

    public synchronized void removeStation(int index){
        mPresetList.remove(index);
    }

    /* If a user selects a new station in this list, this routine will be called to
     * to update the list.
     */
    public synchronized boolean setSelectedStation(PresetStation selectStation){
        int totalPresets = mPresetList.size();
        if (selectStation != null) {
            for (int presetNum = 0; presetNum < totalPresets; presetNum++ ) {
                PresetStation station = mPresetList.get(presetNum);
                if (station != null) {
                    if(selectStation.getFrequency() == station.getFrequency()) {
                        if(selectStation.getName().equalsIgnoreCase(station.getName())) {
                            mCurrentStation = presetNum;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /* Check if the same station already exists in a List
     * to update the list.
     */
    public synchronized boolean sameStationExists(PresetStation compareStation){
        int totalPresets = mPresetList.size();
        if (compareStation != null) {
            for (int presetNum = 0; presetNum < totalPresets; presetNum++ ) {
                PresetStation station = mPresetList.get(presetNum);
                if (station != null) {
                    if(compareStation.getFrequency() == station.getFrequency()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* If a user selects a new station in this list, this routine will be called to
     * to update the list.
     */
    public synchronized boolean setSelectedStation(int stationIndex){
        boolean foundStation = false;
        int totalPresets = mPresetList.size();
        if (stationIndex < totalPresets) {
            mCurrentStation = stationIndex;
            foundStation = true;
        }
        return foundStation;
    }

    public synchronized PresetStation getSelectedStation(){
        int totalPresets = mPresetList.size();
        PresetStation station = null;
        if (mCurrentStation < totalPresets) {
            station = mPresetList.get(mCurrentStation);
        }
        return station;
    }

    public synchronized PresetStation selectNextStation(){
        int totalPresets = mPresetList.size();
        PresetStation station = null;
        if(totalPresets > 0) {
            mCurrentStation ++;
            if ( (mCurrentStation) >= totalPresets) {
                mCurrentStation =0;
            }
            station = mPresetList.get(mCurrentStation);
        }
        return station;
    }

    public synchronized PresetStation selectPrevStation(){
        int totalPresets = mPresetList.size();
        PresetStation station = null;
        if(totalPresets > 0) {
            mCurrentStation --;
            if ( mCurrentStation < 0) {
                mCurrentStation = totalPresets-1;
            }
            station = mPresetList.get(mCurrentStation);
        }
        return station;
    }

    public synchronized void selectStation(PresetStation selectStation){
        int totalPresets = mPresetList.size();
        if (selectStation != null) {
            for (int presetNum = 0; presetNum < totalPresets; presetNum++ ) {
                PresetStation station = mPresetList.get(presetNum);
                if (station != null) {
                    if(selectStation.getFrequency() == station.getFrequency()) {
                        mCurrentStation    = presetNum;
                        return;
                    }
                }
            }
        }
    }

    /* Test Code */
    public void addDummyStations() {
        PresetStation station;
        double freq;
        String name ;
        String pty ;

        mPresetList.clear();
        freq = 89.5;
        name = "KPBS";
        pty = "Public Radio";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(true);
        }

        freq = 96.5;
        name = "KYXY";
        pty = "Soft Rock";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(true);
        }

        freq = 98.1;
        name = "KIFM";
        pty = "Smooth Jazz";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(true);
        }

        freq = 101.5;
        name = "KGB";
        pty = "Classic Rock";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(true);
        }

        freq = 102.1;
        name = "KPRI";
        pty = "Rock";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(false);
        }

        freq = 105.3;
        name = "KIOZ";
        pty = "Rock";
        station = addStation(name, freq);
        if(station != null) {
            station.setPty(pty);
            station.setStationId(name);
            station.setRDSSupported(false);
        }

    }

}
