package nz.netvalue.domain.service.session;

import nz.netvalue.controller.model.EndSessionRequest;

/**
 * Service ends charging session
 */
public interface EndSessionService {

    /**
     * End charging session
     *
     * @param request end session request
     */
    void endSession(EndSessionRequest request);
}
