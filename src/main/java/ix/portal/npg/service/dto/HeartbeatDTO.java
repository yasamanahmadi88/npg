package ix.portal.npg.service.dto;

import ix.portal.npg.constant.HeartbeatResponseType;

public class HeartbeatDTO {

    HeartbeatResponseType status;
    String partyId;
    String service;

    public HeartbeatResponseType getStatus() {
        return status;
    }

    public HeartbeatDTO setStatus(HeartbeatResponseType status) {
        this.status = status;
        return this;
    }

    public String getPartyId() {
        return partyId;
    }

    public HeartbeatDTO setPartyId(String partyId) {
        this.partyId = partyId;
        return this;
    }

    public String getService() {
        return service;
    }

    public HeartbeatDTO setService(String service) {
        this.service = service;
        return this;
    }
}


