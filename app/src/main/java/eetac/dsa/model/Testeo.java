package eetac.dsa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Testeo
{
    @Expose
    @SerializedName("value")
    private String value;

    public Testeo(String value) { this.value = value; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    public Testeo() {
    }
}