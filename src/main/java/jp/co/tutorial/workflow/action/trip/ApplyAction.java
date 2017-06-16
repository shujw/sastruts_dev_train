package jp.co.tutorial.workflow.action.trip;

import java.io.IOException;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.service.client.information.Identifier;
import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.form.trip.ApplyForm;
import jp.co.tutorial.workflow.logic.trip.BusinessTripLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.util.exception.IORuntimeException;

/**
 * Applyアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class ApplyAction {

    /** 旅費申請画面JSP. */
    private static final String INDEX = "/trip/apply/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ApplyForm applyForm;

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
    public String index() {

        if (PageType.pageTyp_App == PageType.getEnum(applyForm.imwPageType)) {
            // 申請
            // ユーザデータID採番
            try {
                applyForm.imwUserDataId = new Identifier().get();
            } catch (final IOException e) {
                // 採番に失敗したため例外
                throw new IORuntimeException(e);
            }
        } else {
            // 再申請・一時保存
            search();
        }
        return INDEX;
    }

    /**
     * searchメソッド.
     *
     * @return 遷移先
     */
    @Execute(validator = false)
    public String search() {
        // 旅費情報を取得する
        businessTripDto = businessTripLogic.searchTripData(applyForm.imwUserDataId);
        // Formに旅費情報設定
        applyForm = (ApplyForm) businessTripLogic.dtoToForm(
                businessTripDto, applyForm);
        return INDEX;
    }
}
