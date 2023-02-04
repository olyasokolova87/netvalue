package nz.netvalue.domain.service.rfidtag;

import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.RfIdTag;

import java.util.UUID;

/**
 * Service works with charge connectors
 */
public interface RfidTagService {

    /**
     * Get RFID tag by number
     *
     * @param number RFID tag's number
     * @return RFID tag
     * If RFID tag not exists, throws {@link ResourceNotFoundException}
     */
    RfIdTag getByUUID(UUID number);
}
