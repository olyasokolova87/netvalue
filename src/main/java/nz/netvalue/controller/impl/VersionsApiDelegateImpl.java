package nz.netvalue.controller.impl;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.VersionsApiDelegate;
import nz.netvalue.controller.mapper.VersionMapper;
import nz.netvalue.controller.model.VersionResponse;
import nz.netvalue.domain.service.version.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionsApiDelegateImpl implements VersionsApiDelegate {

    private final VersionService versionService;
    private final VersionMapper versionMapper;

    @Override
    @Timed(value = "get.version")
    public ResponseEntity<VersionResponse> getVersion() {
        VersionResponse dto = versionMapper.toResponse(versionService.get());
        return ResponseEntity.ok(dto);
    }
}
