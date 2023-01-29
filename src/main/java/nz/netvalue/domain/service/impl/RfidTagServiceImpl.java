package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.repository.RfidTagRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class RfidTagServiceImpl implements RfidTagService {

    private final RfidTagRepository repository;

    public RfidTagServiceImpl(RfidTagRepository repository) {
        this.repository = repository;
    }

    @Override
    public RfIdTag getByUUID(UUID number) {
        Optional<RfIdTag> optional = repository.findByTagNumber(number);
        if (optional.isEmpty()) {
            String message = format("RFID tag with number = [%s] not exists", number);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }
}
