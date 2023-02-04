package nz.netvalue.domain.service.version.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.version.Version;
import nz.netvalue.domain.service.version.VersionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {

    private final Version version;

    @Override
    public Version get() {
        return version;
    }
}
