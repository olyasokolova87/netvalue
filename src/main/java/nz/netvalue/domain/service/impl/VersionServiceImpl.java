package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.model.version.Version;
import nz.netvalue.domain.service.VersionService;
import org.springframework.stereotype.Service;

@Service
public class VersionServiceImpl implements VersionService {

    private final Version version;

    public VersionServiceImpl(Version version) {
        this.version = version;
    }

    @Override
    public Version get() {
        return version;
    }
}
