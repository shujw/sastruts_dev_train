package jp.co.tutorial.form.event;

import java.io.Serializable;

import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.Maxlength;

/**
 * イベント情報のパラメータプロパティを持つ ActionForm.
 *
 * @author intra-mart
 */
public class EventForm implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** イベントID. */
    @Maxlength(maxlength = 20)
    public String eventId;

    /** イベント名. */
    @Maxlength(maxlength = 25)
    public String eventName;

    /** イベント内容. */
    @Maxlength(maxlength = 50)
    public String eventDetail;

    /** 開催日. */
    @DateType
    public String eventDate;

    /** 参加者. */
    @Maxlength(maxlength = 100)
    public String entryUser;

	/** フォーマット. */
	public String format;

    /** パス. */
    public String path;
}
