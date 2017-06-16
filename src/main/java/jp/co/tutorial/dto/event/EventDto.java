package jp.co.tutorial.dto.event;

import java.util.Date;

/**
 * イベント情報DTOクラス.
 *
 * @author intra-mart
 */
public class EventDto {

    /** イベントID. */
    public String eventId;

    /** イベント名. */
    public String eventName;

    /** イベント内容. */
    public String eventDetail;

    /** 開催日. */
    public Date eventDate;

    /** 参加者. */
    public String entryUser;
}
