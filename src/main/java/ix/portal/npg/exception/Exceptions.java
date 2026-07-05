package ix.portal.npg.exception;

/**
 * Developer: Hossein Sadeghi (hsadeghi78@gmail.com)
 * Project : admin-console- 8/1/2020
 */
public enum Exceptions {
    BZ_GOZARESH_SARBAZRES_AMEL_ID(
        "BZGS_BZGS_UK",
        "BAZRASI.GozareshSarbazres_amelId.duplicate",
        "Ú¯Ø²Ø§Ø±Ø´ Ø³Ø±Ø¨Ø§Ø²Ø±Ø³ Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ú©Ø§Ø±Ø¨Ø± Ù‚Ø¨Ù„Ø§ ÙˆØ§Ø±Ø¯ Ø´Ø¯Ù‡ Ø§Ø³Øª",
        "Gozaresh Sarbazres amel Id is duplicate"
    ),
    BZ_KARBARG_BAZRES_VIZHE_AMEL_ID(
        "BZKV_BZKV_UK",
        "BAZRASI.KarbargBazresVizhe_amelId.duplicate",
        "Ú©Ø§Ø±Ø¨Ø±Ú¯ Ø¨Ø§Ø²Ø±Ø³ ÙˆÛŒÚ˜Ù‡ Ø¨Ø±Ø§ÛŒ Ø§ÛŒÙ† Ú©Ø§Ø±Ø¨Ø± Ù‚Ø¨Ù„Ø§ Ø«Ø¨Øª Ø´Ø¯Ù‡ Ø§Ø³Øª",
        "Kararg Bazres Vizhe amel Id is duplicate"
    ),

    TBL_APPENDIX_BULK_FILE("TBL_APPENDIX_BULK_FILE", "npg.tblAppendixBulkFile.violation", "", ""),
    TBL_BUSINESS_CONFIG("TBL_BUSINESS_CONFIG", "npg.tblBusinessConfig.violation", "", ""),
    TBL_CHECK_STATUS("TBL_CHECK_STATUS", "npg.tblCheckStatus.violation", "", ""),
    TBL_CRM_TO_CRDB_RESPONSE_MAP("TBL_CRM_TO_CRDB_RESPONSE_MAP", "npg.tblCrmToCrdbResponseMap.violation", "", ""),
    TBL_DAY_OF_WEEK_TIME_FRAME("TBL_DAY_OF_WEEK_TIME_FRAME", "npg.dayOfWeekTimeFrame.violation", "", ""),
    TBL_DUPLICATE("TBL_DUPLICATE", "npg.tblDuplicate.violation", "", ""),
    TBL_EVENT_LOG("TBL_EVENT_LOG", "npg.tblEventLog.violation", "", ""),
    TBL_NOTIFICATION_TEMPLATE("TBL_NOTIFICATION_TEMPLATE", "npg.tblNotificationTemplate.violation", "", ""),
    TBL_OFF_DAY("TBL_OFF_DAY", "npg.tblOffDay.violation", "", ""),
    TBL_PORTABILITY_LOG("TBL_PORTABILITY_LOG", "npg.tblPortabilityLog.violation", "", ""),
    TBL_SISTEER_PORTABILITY("TBL_SISTEER_PORTABILITY", "npg.tblSisteerPortability.violation", "", ""),
    TBL_TIME_FRAME("TBL_TIME_FRAME", "npg.tblTimeFrame.violation", "", ""),
    TBL_UNDEFINED_STATUS("TBL_UNDEFINED_STATUS", "npg.tblUndefinedStatus.violation", "", ""),
    TBS_SISTEER_FAIL_STATUS("TBS_SISTEER_FAIL_STATUS", "npg.tbsSisteerFailStatus.violation", "", ""),
    TBS_SISTEER_POR_STATUS("TBS_SISTEER_POR_STATUS", "npg.tbsSisteerPorStatus.violation", "", ""),
    TBS_SISTEER_TECH_STATUS("TBS_SISTEER_TECH_STATUS", "npg.tbsSisteerTechStatus.violation", "", ""),
    TBL_PORTABILITY_ENTITY("TBL_PORTABILITY_ENTITY", "npg.tblPortabilityEntity.violation", "", "");

    private final Object[] values;

    Exceptions(Object... vals) {
        values = vals;
    }

    public String UKNAME() {
        return (String) values[0];
    }

    public String CODE() {
        return (String) values[1];
    }

    public String MESSAGE() {
        return (String) values[2];
    }

    public String TRANSLATE() {
        return (String) values[3];
    }
}


