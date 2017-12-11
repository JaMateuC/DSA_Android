package eetac.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeyLog
{
    @Expose
    @SerializedName("key")
    private int key;

    public KeyLog() { }

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }
}
