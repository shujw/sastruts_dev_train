package jp.co.tutorial.action.event;

import java.util.Map;
import java.io.IOException;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.context.Contexts;
import jp.co.intra_mart.foundation.context.model.AccountContext;
import jp.co.intra_mart.foundation.service.client.information.Identifier;
import jp.co.tutorial.dto.event.EventDto;
import jp.co.tutorial.form.event.EventForm;
import jp.co.tutorial.logic.event.EventLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.util.exception.IORuntimeException;

/**
 * イベント情報の登録画面のアクションクラス.
 *
 * @author intra-mart
 */
public class EditAction {

    /** イベント登録画面JSP. */
    private static final String EDIT = "/event/edit/index.jsp";

    /** イベント参照. */
    private static final String CONFIRM = "/event/confirm";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public EventForm eventForm;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected EventLogic eventLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index() {
        eventForm.path = "/event/edit/create";
	//日付フォーマットを設定
	AccountContext accountContext = Contexts.get(AccountContext.class);
	final Map<String,String> format = accountContext.getDateTimeFormats();
	eventForm.format = format.get("IM_DATETIME_FORMAT_DATE_STANDARD");
        return EDIT;
    }

    /**
     * createメソッド.
     * イベント情報の登録
     * @return 遷移先
     */
    @Execute(validator = false)
    public String create() {

        try {
            // イベントID採番
            eventForm.eventId = new Identifier().get();
        } catch (final IOException e) {
            // 採番に失敗したため例外
            throw new IORuntimeException(e);
        }
        // 顧客情報のコピー
        final EventDto eventDto = eventLogic.formToDto(eventForm);

        eventLogic.createEvent(eventDto);
        return CONFIRM;
    }

}
