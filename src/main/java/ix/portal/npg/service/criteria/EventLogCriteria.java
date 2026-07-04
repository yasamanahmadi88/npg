package ix.portal.npg.service.criteria;

import ix.portal.npg.Filter.LocalDateTimeFilter;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ix.portal.npg.domain.EventLogEntity} entity. This class is used
 * in {@link ix.portal.npg.web.rest.EventLogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-logs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EventLogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter conversationId;

    private StringFilter sender;

    private StringFilter receiver;

    private StringFilter message;

    private StringFilter requestBody;

    private StringFilter responseBody;

    private IntegerFilter flg0Ordinary1Exception;

    private StringFilter eventSource;

    private StringFilter exceptionBody;

    private StringFilter httpStatus;

    private LocalDateTimeFilter insertTimestamp;

    public EventLogCriteria() {}

    public EventLogCriteria(EventLogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.conversationId = other.conversationId == null ? null : other.conversationId.copy();
        this.sender = other.sender == null ? null : other.sender.copy();
        this.receiver = other.receiver == null ? null : other.receiver.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.requestBody = other.requestBody == null ? null : other.requestBody.copy();
        this.responseBody = other.responseBody == null ? null : other.responseBody.copy();
        this.flg0Ordinary1Exception = other.flg0Ordinary1Exception == null ? null : other.flg0Ordinary1Exception.copy();
        this.eventSource = other.eventSource == null ? null : other.eventSource.copy();
        this.exceptionBody = other.exceptionBody == null ? null : other.exceptionBody.copy();
        this.httpStatus = other.httpStatus == null ? null : other.httpStatus.copy();
        this.insertTimestamp = other.insertTimestamp == null ? null : other.insertTimestamp.copy();
    }

    @Override
    public EventLogCriteria copy() {
        return new EventLogCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConversationId() {
        return conversationId;
    }

    public StringFilter conversationId() {
        if (conversationId == null) {
            conversationId = new StringFilter();
        }
        return conversationId;
    }

    public void setConversationId(StringFilter conversationId) {
        this.conversationId = conversationId;
    }

    public StringFilter getSender() {
        return sender;
    }

    public StringFilter sender() {
        if (sender == null) {
            sender = new StringFilter();
        }
        return sender;
    }

    public void setSender(StringFilter sender) {
        this.sender = sender;
    }

    public StringFilter getReceiver() {
        return receiver;
    }

    public StringFilter receiver() {
        if (receiver == null) {
            receiver = new StringFilter();
        }
        return receiver;
    }

    public void setReceiver(StringFilter receiver) {
        this.receiver = receiver;
    }

    public StringFilter getMessage() {
        return message;
    }

    public StringFilter message() {
        if (message == null) {
            message = new StringFilter();
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getRequestBody() {
        return requestBody;
    }

    public StringFilter requestBody() {
        if (requestBody == null) {
            requestBody = new StringFilter();
        }
        return requestBody;
    }

    public void setRequestBody(StringFilter requestBody) {
        this.requestBody = requestBody;
    }

    public StringFilter getResponseBody() {
        return responseBody;
    }

    public StringFilter responseBody() {
        if (responseBody == null) {
            responseBody = new StringFilter();
        }
        return responseBody;
    }

    public void setResponseBody(StringFilter responseBody) {
        this.responseBody = responseBody;
    }

    public IntegerFilter getFlg0Ordinary1Exception() {
        return flg0Ordinary1Exception;
    }

    public IntegerFilter flg0Ordinary1Exception() {
        if (flg0Ordinary1Exception == null) {
            flg0Ordinary1Exception = new IntegerFilter();
        }
        return flg0Ordinary1Exception;
    }

    public void setFlg0Ordinary1Exception(IntegerFilter flg0Ordinary1Exception) {
        this.flg0Ordinary1Exception = flg0Ordinary1Exception;
    }

    public StringFilter getEventSource() {
        return eventSource;
    }

    public StringFilter eventSource() {
        if (eventSource == null) {
            eventSource = new StringFilter();
        }
        return eventSource;
    }

    public void setEventSource(StringFilter eventSource) {
        this.eventSource = eventSource;
    }

    public StringFilter getExceptionBody() {
        return exceptionBody;
    }

    public StringFilter exceptionBody() {
        if (exceptionBody == null) {
            exceptionBody = new StringFilter();
        }
        return exceptionBody;
    }

    public void setExceptionBody(StringFilter exceptionBody) {
        this.exceptionBody = exceptionBody;
    }

    public StringFilter getHttpStatus() {
        return httpStatus;
    }

    public StringFilter httpStatus() {
        if (httpStatus == null) {
            httpStatus = new StringFilter();
        }
        return httpStatus;
    }

    public void setHttpStatus(StringFilter httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTimeFilter getInsertTimestamp() {
        return insertTimestamp;
    }

    public LocalDateTimeFilter insertTimestamp() {
        if (insertTimestamp == null) {
            insertTimestamp = new LocalDateTimeFilter();
        }
        return insertTimestamp;
    }

    public void setInsertTimestamp(LocalDateTimeFilter insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventLogCriteria that = (EventLogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(conversationId, that.conversationId) &&
            Objects.equals(sender, that.sender) &&
            Objects.equals(receiver, that.receiver) &&
            Objects.equals(message, that.message) &&
            Objects.equals(requestBody, that.requestBody) &&
            Objects.equals(responseBody, that.responseBody) &&
            Objects.equals(flg0Ordinary1Exception, that.flg0Ordinary1Exception) &&
            Objects.equals(eventSource, that.eventSource) &&
            Objects.equals(exceptionBody, that.exceptionBody) &&
            Objects.equals(httpStatus, that.httpStatus) &&
            Objects.equals(insertTimestamp, that.insertTimestamp)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            conversationId,
            sender,
            receiver,
            message,
            requestBody,
            responseBody,
            flg0Ordinary1Exception,
            eventSource,
            exceptionBody,
            httpStatus,
            insertTimestamp
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventLogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (conversationId != null ? "conversationId=" + conversationId + ", " : "") +
            (sender != null ? "sender=" + sender + ", " : "") +
            (receiver != null ? "receiver=" + receiver + ", " : "") +
            (message != null ? "message=" + message + ", " : "") +
            (requestBody != null ? "requestBody=" + requestBody + ", " : "") +
            (responseBody != null ? "responseBody=" + responseBody + ", " : "") +
            (flg0Ordinary1Exception != null ? "flg0Ordinary1Exception=" + flg0Ordinary1Exception + ", " : "") +
            (eventSource != null ? "eventSource=" + eventSource + ", " : "") +
            (exceptionBody != null ? "exceptionBody=" + exceptionBody + ", " : "") +
            (httpStatus != null ? "httpStatus=" + httpStatus + ", " : "") +
            (insertTimestamp != null ? "insertTimestamp=" + insertTimestamp + ", " : "") +
            "}";
    }
}


