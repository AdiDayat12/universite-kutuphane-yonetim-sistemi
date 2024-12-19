package com.grup31.universite_kutuphane_yonetim_sistemi.book;

import com.grup31.universite_kutuphane_yonetim_sistemi.user.StudentObserver;

public interface BookSubject {
    void addObserver (StudentObserver studentObserver);
    void removeObserver (StudentObserver studentObserver);
    void notifyObserver ();
}
