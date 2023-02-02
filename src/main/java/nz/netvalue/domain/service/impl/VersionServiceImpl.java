package nz.netvalue.domain.service.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.model.version.Version;
import nz.netvalue.domain.service.VersionService;
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
