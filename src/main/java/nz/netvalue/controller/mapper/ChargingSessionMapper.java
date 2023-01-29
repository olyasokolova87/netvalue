package nz.netvalue.controller.mapper;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.persistence.model.ChargingSession;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Map version information to version response
 */
@Mapper(uses = MapperUtils.class)
public interface ChargingSessionMapper {

    /**
     * Get list of charging session for response
     *
     * @param list - session list from db
     * @return list of charging session
     */
    List<ChargingSessionResponse> toResponseList(List<ChargingSession> list);
}
