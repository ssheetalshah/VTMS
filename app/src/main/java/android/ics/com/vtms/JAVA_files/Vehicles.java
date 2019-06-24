package android.ics.com.vtms.JAVA_files;

import java.io.Serializable;

public class Vehicles implements Serializable {

    private String id;
    private String vehicleDescription;

    public Vehicles(String id, String vehicleDescription) {
        this.id = id;
        this.vehicleDescription = vehicleDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }

}