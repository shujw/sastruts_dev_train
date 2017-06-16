package jp.co.tutorial.service.event;

import static jp.co.tutorial.entity.event.MSampleEventNames.eventId;
import static org.seasar.extension.jdbc.operation.Operations.eq;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.entity.event.MSampleEvent;
import jp.co.tutorial.service.customer.AbstractService;

import org.seasar.extension.jdbc.exception.SNonUniqueResultException;

/**
 * {@link MSampleEvent}のサービスクラスです.
 *
 * @author intra-mart
 */
public class EventService extends AbstractService<MSampleEvent> {

    /**
     * イベントIDを条件にイベント情報を検索します.
     *
     * @param eventId
     *            イベントID
     * @return イベントエンティティ
     */
    public MSampleEvent findByEventId(final String eventId) {

        MSampleEvent mSampleEvent;
        try {
            mSampleEvent = select().where(eq(eventId(), eventId))
                    .getSingleResult();
        } catch (final SNonUniqueResultException e) {
            throw new ApplicationRuntimeException(e);
        }

        return mSampleEvent;
    }
}
