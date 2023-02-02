package nz.netvalue.controller;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.dto.VersionResponse;
import nz.netvalue.controller.mapper.VersionMapper;
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
