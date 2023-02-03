package nz.netvalue.controller.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Builder for POST request location header
 */
@Component
public class LocationHeaderBuilder {

    /**
     * Build location header
     *
     * @param entityId ID of entity
     * @return URI with location of creation entity
     */
    public URI build(Long entityId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entityId)
                .toUri();
    }
}
