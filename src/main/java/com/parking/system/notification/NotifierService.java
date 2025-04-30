package com.parking.system.notification;

import com.parking.system.resident.data.Resident;

public interface NotifierService {

    /**
     * Notifies a resident that their parking spot reservation has been auto-released.
     *
     * @param resident      the resident to notify
     * @param spotNumber    the released parking spot number
     * @param communityName the name of the community
     */
    void notifyAutoRelease(Resident resident, String spotNumber, String communityName);
}
