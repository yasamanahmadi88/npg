package ix.portal.npg.constant;

public enum HeartbeatResponseType {
    AVAILABLE,
    IX_UNAVAILABLE,
    ENDPOINT_UNAVAILABLE,
    IX_NOT_ANNOUNCED_YET,
    IX_NOT_DEFINED_IN_REGISTRY,
    IX_TARGET_PARTY_NOT_DEFINED,
    UNKNOWN_ERROR;

    private HeartbeatResponseType() {}
}


