package com.codegym.service;

import com.codegym.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicService implements IMusicService {
    List<Music> list = new ArrayList<>();

    @Override
    public List<Music> findAll() {
        return list;
    }

    @Override
    public void save(Music music) {
        list.add(music);
    }

    @Override
    public Music findById(int id) {
        return list.get(findIndexOf(id));
    }

    @Override
    public void update(int id, Music music) {
        list.set(findIndexOf(id), music);
    }

    @Override
    public void remove(int id) {
        list.remove(findIndexOf(id));
    }

    @Override
    public int findIndexOf(int id) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (id == list.get(i).getId()) {
                index = i;
            }
        }
        return index;
    }
}
