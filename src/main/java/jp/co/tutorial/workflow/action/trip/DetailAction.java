package jp.co.tutorial.workflow.action.trip;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.form.trip.DetailForm;
import jp.co.tutorial.workflow.logic.trip.BusinessTripLogic;

import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

/**
 * Detailアクションクラス.
 *
 * @version $Revision$
 * @author intra-mart
 * @since 1.0
 */
public class DetailAction {

    /** 旅費詳細画面JSP. */
    private static final String INDEX = "/trip/detail/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public DetailForm detailForm;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected BusinessTripLogic businessTripLogic;

    /**
     * indexメソッド.
     *
     * @return 遷移先
     * @throws IOException
     */
    @Execute(validator = false)
    public String index() {
        if (PageType.pageTyp_Cnfmdetail == PageType.getEnum(detailForm.imwPageType)
                || PageType.pageTyp_Procdetail == PageType.getEnum(detailForm.imwPageType)
                || PageType.pageTyp_Refdetail == PageType.getEnum(detailForm.imwPageType)) {

            // 登録されている旅費データを取得します
            final BusinessTripDto businessTripDto = businessTripLogic.searchTripData(detailForm.imwUserDataId);

            // 画面に表示する項目をFormに設定します
            detailForm = (DetailForm) businessTripLogic.dtoToForm(businessTripDto, detailForm);
        } else {
            // TODO エラー処理を追加してください
        }
        return INDEX;
    }
}
