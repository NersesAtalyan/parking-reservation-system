package com.parking.system.notification;

import com.parking.system.resident.data.Resident;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotifierService implements NotifierService {

    @Async
    @Override
    public void notifyAutoRelease(Resident resident, String spotNumber, String communityName) {
        log.info("📧 [Email Notification] To: {} | Spot '{}' in community '{}' was auto-released.",
                resident.getEmail(), spotNumber, communityName);

    }
}
