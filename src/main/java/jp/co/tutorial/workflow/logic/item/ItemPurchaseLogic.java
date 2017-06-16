package jp.co.tutorial.workflow.logic.item;

import static jp.co.tutorial.workflow.entity.item.SampleImwItemNames.itemDate;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;

import jp.co.intra_mart.foundation.i18n.datetime.format.AccountDateTimeFormatter;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatIds;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatterException;
import jp.co.intra_mart.foundation.security.exception.AccessSecurityException;
import jp.co.intra_mart.foundation.security.message.MessageManager;
import jp.co.intra_mart.foundation.workflow.plugin.process.action.ActionProcessParameter;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.SystemRuntimeException;
import jp.co.tutorial.workflow.dto.item.ItemPurchaseDto;
import jp.co.tutorial.workflow.entity.item.SampleImwItem;
import jp.co.tutorial.workflow.form.item.ApplyForm;
import jp.co.tutorial.workflow.form.item.ItemPurchaseForm;
import jp.co.tutorial.workflow.service.item.ItemPurchaseService;

import org.seasar.framework.beans.util.Beans;

/**
 * {@link SampleImwItem}のロジッククラス.
 * @author intra-mart
 */
public class ItemPurchaseLogic {

    /** サービスコンポーネントの定義. */
    @Resource
    protected ItemPurchaseService itemPurchaseService;

    /**
     * ユーザデータIDを条件に物品購入情報を検索します.
     *
     * @param userDataId
     *            ユーザデータID
     * @return 物品購入情報DTO
     */
    public ItemPurchaseDto searchItemPurchase(final String userDataId) {

        final SampleImwItem sampleImwItem = itemPurchaseService.findByUserDataId(userDataId);
        if (sampleImwItem == null) {
            // 取得件数が0の場合
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.003"));
        }
        // 旅費情報をエンティティからDTOにコピーします。
        final ItemPurchaseDto itemPurchaseDto = Beans.createAndCopy(
                ItemPurchaseDto.class, sampleImwItem).execute();
        return itemPurchaseDto;
    }

    /**
     * 物品購入情報登録.
     * @param parameter
     * @param userParameter
     * @param pageType
     * @see ActionProcessParameter
     */
    public void createItemPurchase(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        // 申請・再申請共通処理
        final SampleImwItem sampleImwItem = paramToEntity(parameter, userParameter);
        try {
            itemPurchaseService.insert(sampleImwItem);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
    }

    /**
     * 物品購入情報更新.
     * @param parameter
     * @param userParameter
     * @param pageType
     * @see ActionProcessParameter
     */
    public void updateItemPurchase(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        // 申請・再申請共通処理
        final SampleImwItem sampleImwItem = paramToEntity(parameter, userParameter);

        try {
            itemPurchaseService.update(sampleImwItem);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
    }

    /**
     * 物品購入情報削除.
     * @param userDataId
     */
    public void deleteItemPurchase(final String userDataId) {

        final SampleImwItem sampleImwItem = new SampleImwItem();
        sampleImwItem.userDataId = userDataId;
        // 旅費削除
        itemPurchaseService.delete(sampleImwItem);
    }

    /**
     * 物品購入情報をDTOからFormに詰め替えます.
     *
     * @param itemPurchaseDto
     *            物品購入情報DTO
     * @param itemPurchaseForm
     *            物品購入情報form
     * @return 物品購入情報form
     */
    public ItemPurchaseForm dtoToForm(final ItemPurchaseDto itemPurchaseDto, final ItemPurchaseForm itemPurchaseForm) {

        if (itemPurchaseDto != null) {
            Beans.copy(itemPurchaseDto, itemPurchaseForm)
                    .excludes(itemDate()).execute();

            // itemPurchaseFormがapplyFormだった場合、入力フォーマットでコピー。
            // それ以外だった場合、標準フォーマットでコピー
            if (itemPurchaseForm instanceof ApplyForm) {
                if (itemPurchaseDto.itemDate != null) {
                    itemPurchaseForm.itemDate = AccountDateTimeFormatter.format(
                            itemPurchaseDto.itemDate,
                            DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
                }
            } else {
                itemPurchaseForm.itemDate = AccountDateTimeFormatter.format(
                        itemPurchaseDto.itemDate,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
            }
        }
        return itemPurchaseForm;
    }

    /**
     * 申請共通処理.
     * エンティティに物品購入情報を詰め替えます
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @return 物品購入情報エンティティ
     */
    private SampleImwItem paramToEntity(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        final SampleImwItem sampleImwItem = new SampleImwItem();
        // ユーザデータID
        sampleImwItem.userDataId = parameter.getUserDataId();

        // 品名
        sampleImwItem.itemName = (String)userParameter.get("itemName");

        // 金額
        String itemPrice = (String) userParameter.get("itemPrice");
        if (itemPrice.equals("")) {
            itemPrice = "0";
        }
        sampleImwItem.itemPrice = Long.parseLong(itemPrice, 10);

        // 納入日
        final String itemDate = (String) userParameter.get("itemDate");
        try {
            if (!itemDate.equals("")) {
                sampleImwItem.itemDate = AccountDateTimeFormatter.parse(
                        itemDate, Date.class,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
            }
        } catch (final DateTimeFormatterException e) {
            throw new ApplicationRuntimeException(e);
        }

        // 理由
        sampleImwItem.itemReason = (String)userParameter.get("itemReason");
        return sampleImwItem;
    }

    /**
     * メッセージを取得します.
     *
     * @param key
     *            メッセージキー
     * @return メッセージ
     */
    protected String getMessage(final String key) {

        try {
            return MessageManager.getInstance().getMessage(key);
        } catch (final AccessSecurityException e) {
            throw new SystemRuntimeException(e);
        }
    }
}
