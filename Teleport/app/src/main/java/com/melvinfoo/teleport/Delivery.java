package com.melvinfoo.teleport;

/**
 * Created by melvi on 22/9/2016.
 */
public class Delivery {
    private String addressToSend;
    private String addressSentFrom;

    public String getTitle() {
        return title;
    }

    public float getPackageWeight() {
        return packageWeight;
    }

    private String title;
    private int imageOfPackageId;
    //in kg
    private float packageWeight;


    public final static Delivery[] Deliveries = {
            new Delivery("Miband", "angmokio", "gedong camp", R.drawable.miband, 0.5f),
            new Delivery("Macbook pro", "kaki bukit", "ang mo kio", R.drawable.macbook, 1.5f)
    };

    private Delivery(String title, String sendTo, String fromWhere, int imageId, float weight) {
        this.title = title;
        this.addressToSend = sendTo;
        this.addressSentFrom = fromWhere;
        this.imageOfPackageId = imageId;
        this.packageWeight = weight;

    }

    String getAddressToSend() {
        return addressToSend;
    }

    String getAddressSentFrom() {
        return addressSentFrom;
    }

    int getImageOfPackageId() {
        return imageOfPackageId;
    }

    @Override
    public String toString() {
        return addressSentFrom + " to " + addressToSend;
    }
}