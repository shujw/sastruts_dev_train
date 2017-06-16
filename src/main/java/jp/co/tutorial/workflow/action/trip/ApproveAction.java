package jp.co.tutorial.workflow.action.trip;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.form.trip.ApproveForm;
import jp.co.tutorial.workflow.logic.trip.BusinessTripLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * Approveアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ApproveAction {

    /** 旅費承認画面JSP. */
    private static final String INDEX = "/trip/approve/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ApproveForm approveForm;

    /** 旅費情報. */
    public BusinessTripDto businessTripDto;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected BusinessTripLogic businessTripLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String index(){
        if (PageType.pageTyp_Proc == PageType.getEnum(approveForm.imwPageType)) {
            // 旅費情報取得
            businessTripDto = businessTripLogic.searchTripData(approveForm.imwUserDataId);
            // Formに旅費情報を設定
            approveForm = (ApproveForm) businessTripLogic.dtoToForm(businessTripDto, approveForm);
        } else {
            // TODO エラー処理を追加してください
        }

        return INDEX;
    }
}
