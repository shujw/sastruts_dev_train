package jp.co.tutorial.workflow.action.trip;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.http.HttpServletRequest;

import jp.co.intra_mart.foundation.service.client.information.Identifier;
import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.form.trip.ApiapplyForm;
import jp.co.tutorial.workflow.logic.trip.BusinessTripLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;
import org.seasar.util.exception.IORuntimeException;

/**
 * IM-Workflow 申請をAPIで行なうサンプル.
 *
 * @author intra-mart
 */
public class ApiapplyAction {

    /** リクエスト情報. */
    @Resource
    protected HttpServletRequest request;

    /** 旅費申請画面JSP. */
    private static final String INDEX = "/trip/apiapply/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ApiapplyForm apiApplyForm;

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
        if (PageType.pageTyp_App == PageType.getEnum(apiApplyForm.imwPageType)) {
            // 申請
            // ユーザデータID採番
            try {
            apiApplyForm.imwUserDataId = new Identifier().get();

            } catch (final IOException e) {
                // 採番に失敗したため例外
                throw new IORuntimeException(e);
            }
        } else {
            // 再申請
            search();
        }
        apiApplyForm.path = "/trip/apiapply/apply";
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
        businessTripDto = businessTripLogic.searchTripData(apiApplyForm.imwUserDataId);
        // Formに旅費情報設定
        apiApplyForm = (ApiapplyForm) businessTripLogic.dtoToForm(businessTripDto, apiApplyForm);
        
        apiApplyForm.path = "/trip/apiapply/apply";
        return INDEX;
    }

    /**
     * 申請処理.
     *
     * @return パス
     */
    @TransactionAttribute(TransactionAttributeType.NEVER)
    @Execute(validator = false)
    public String apply() {

        // 申請処理実行
        // ※ WorkflowExceptionがスローされなければ処理成功
        businessTripLogic.applyTrip(apiApplyForm);

        return "/trip/apiapply/apiApplyResult.jsp";
    }
}