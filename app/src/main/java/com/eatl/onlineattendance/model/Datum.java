package com.eatl.onlineattendance.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }
    };

    protected Datum(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((String) in.readValue((String.class.getClassLoader())));

    }

    /**
     * No args constructor for use in serialization
     */
    public Datum() {
    }

    /**
     *
     * @param id
     * @param name
     * @param address

     */
    public Datum(Integer id, String name, String address) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(address);

    }
}
