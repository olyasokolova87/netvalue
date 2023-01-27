package nz.netvalue.controller.mapper;

import nz.netvalue.controller.dto.VersionResponseDto;
import nz.netvalue.domain.model.version.Version;
import org.mapstruct.Mapper;

/**
 * Map version information to version response
 */
@Mapper
public interface VersionMapper {
    VersionResponseDto toResponse(Version version);
}
