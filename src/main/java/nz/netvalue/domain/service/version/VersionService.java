package nz.netvalue.domain.service.version;

import nz.netvalue.domain.model.version.Version;

/**
 * Service that provides version information about an application
 */
public interface VersionService {
    Version get();
}
