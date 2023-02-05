package nz.netvalue.domain.service.session;

import nz.netvalue.controller.model.EndSessionRequest;

/**
 * Service ends charging session
 */
public interface EndSessionService {

    /**
     * End charging session
     *
     * @param sessionId charging session ID
     * @param request   end session request
     */
    void endSession(Long sessionId, EndSessionRequest request);
}
