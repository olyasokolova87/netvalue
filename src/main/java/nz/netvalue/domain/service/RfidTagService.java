package nz.netvalue.domain.service;

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
     * If RFID tag not exists, throws {@link nz.netvalue.domain.exception.ResourceNotFoundException}
     */
    RfIdTag getByUUID(UUID number);
}
