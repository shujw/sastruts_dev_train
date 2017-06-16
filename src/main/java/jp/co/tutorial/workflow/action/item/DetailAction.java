package jp.co.tutorial.workflow.action.item;

import java.io.IOException;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.item.ItemPurchaseDto;
import jp.co.tutorial.workflow.form.item.DetailForm;
import jp.co.tutorial.workflow.logic.item.ItemPurchaseLogic;

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

    /** 物品購入詳細画面JSP. */
    private static final String INDEX = "/item/detail/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public DetailForm detailForm;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected ItemPurchaseLogic itemPurchaseLogic;

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
            final ItemPurchaseDto itemPurchaseDto = itemPurchaseLogic.searchItemPurchase(detailForm.imwUserDataId);

            // 画面に表示する項目をFormに設定します
            detailForm = (DetailForm) itemPurchaseLogic.dtoToForm(itemPurchaseDto, detailForm);
        } else {
            // TODO エラー処理を追加してください
        }
        return INDEX;
    }
}
