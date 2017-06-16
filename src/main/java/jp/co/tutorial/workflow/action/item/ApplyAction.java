package jp.co.tutorial.workflow.action.item;

import java.io.IOException;

import javax.annotation.Resource;

import jp.co.intra_mart.foundation.service.client.information.Identifier;
import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.tutorial.workflow.dto.item.ItemPurchaseDto;
import jp.co.tutorial.workflow.form.item.ApplyForm;
import jp.co.tutorial.workflow.logic.item.ItemPurchaseLogic;

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

    /** 物品購入申請画面JSP. */
    private static final String INDEX = "/item/apply/index.jsp";

    /** アクションに対するFormの定義. */
    @ActionForm
    @Resource
    public ApplyForm applyForm;

    /** 物品購入情報. */
    public ItemPurchaseDto itemPurchaseDto;

    /** ロジックコンポーネントの定義. */
    @Resource
    protected ItemPurchaseLogic itemPurchaseLogic;

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
        itemPurchaseDto = itemPurchaseLogic.searchItemPurchase(applyForm.imwUserDataId);
        // Formに旅費情報設定
        applyForm = (ApplyForm) itemPurchaseLogic.dtoToForm(
                itemPurchaseDto, applyForm);
        return INDEX;
    }
}
