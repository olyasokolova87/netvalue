package nz.netvalue.controller.mapper;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Utility methods that used in mappers
 */
@Component
public class MapperUtils {
    String map(UUID uuid) {
        return uuid.toString();
    }
}
