package jp.co.tutorial.action.event;

import javax.annotation.Resource;

import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.dto.event.EventDto;
import jp.co.tutorial.form.event.EventForm;
import jp.co.tutorial.logic.event.EventLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * イベント情報の参照画面のアクションクラス.
 *
 * @author intra-mart
 */
public class ConfirmAction {

    /** イベント参照画面JSP. */
    private static final String CONFIRM = "/event/confirm/index.jsp";


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
    @Execute(validator = false, urlPattern = "{eventId}")
    public String index() {
        // 顧客情報を取得する
        final EventDto eventDto = eventLogic
                .searchEvent(eventForm.eventId);
        if (eventDto == null) {
            // 取得件数が0の場合
            throw new ApplicationRuntimeException(
                    eventLogic.getMessage("IM.CST.ERR.003"));
        }
        // Formに顧客情報設定
        eventForm = eventLogic.dtoToForm(eventDto);

        return CONFIRM;
    }

}
