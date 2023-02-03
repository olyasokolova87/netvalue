package nz.netvalue.controller.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.VersionsApiDelegate;
import nz.netvalue.controller.mapper.VersionMapper;
import nz.netvalue.controller.model.VersionResponse;
import nz.netvalue.domain.service.VersionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionsApiDelegateImpl implements VersionsApiDelegate {

    private final VersionService versionService;
    private final VersionMapper versionMapper;

    @Override
    public ResponseEntity<VersionResponse> getVersion() {
        VersionResponse dto = versionMapper.toResponse(versionService.get());
        return ResponseEntity.ok(dto);
    }
}
