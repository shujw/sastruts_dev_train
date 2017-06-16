package jp.co.tutorial.workflow.action.trip;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.form.trip.ConfirmForm;
import jp.co.tutorial.workflow.logic.trip.BusinessTripLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * Confirmアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ConfirmAction {

    /** 旅費確認画面JSP. */
    private static final String INDEX = "/trip/confirm/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ConfirmForm confirmForm;

     /** ロジックコンポーネントの定義. */
    @Resource
    protected BusinessTripLogic businessTripLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index() {
        if (PageType.pageTyp_Cnfm == PageType.getEnum(confirmForm.imwPageType)) {
            // 登録されている旅費データを取得します
            final BusinessTripDto businessTripDto = businessTripLogic.searchTripData(confirmForm.imwUserDataId);

            // 画面に表示する項目をFormに設定します
            confirmForm = (ConfirmForm) businessTripLogic.dtoToForm(businessTripDto, confirmForm);
        } else {
            // TODO エラー処理を追加してください
        }
        return INDEX;
    }

}
