package jp.co.tutorial.workflow.logic.trip;

import static jp.co.tutorial.workflow.entity.trip.BusinessTripNames.arrivalDate;
import static jp.co.tutorial.workflow.entity.trip.BusinessTripNames.startDate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import jp.co.intra_mart.foundation.i18n.datetime.format.AccountDateTimeFormatter;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatIds;
import jp.co.intra_mart.foundation.i18n.datetime.format.DateTimeFormatterException;
import jp.co.intra_mart.foundation.security.exception.AccessSecurityException;
import jp.co.intra_mart.foundation.security.message.MessageManager;
import jp.co.intra_mart.foundation.workflow.application.general.UserActvMatterPropertyValue;
import jp.co.intra_mart.foundation.workflow.application.model.AuthUserOrgzModel;
import jp.co.intra_mart.foundation.workflow.application.model.UserMatterPropertyModel;
import jp.co.intra_mart.foundation.workflow.application.model.param.ApplyParam;
import jp.co.intra_mart.foundation.workflow.application.process.ApplyManager;
import jp.co.intra_mart.foundation.workflow.code.PageType;
import jp.co.intra_mart.foundation.workflow.exception.WorkflowException;
import jp.co.intra_mart.foundation.workflow.plugin.process.action.ActionProcessParameter;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.SystemRuntimeException;
import jp.co.intra_mart.system.workflow.util.WorkflowUtil;
import jp.co.tutorial.workflow.dto.trip.BusinessTripDto;
import jp.co.tutorial.workflow.entity.trip.BusinessTrip;
import jp.co.tutorial.workflow.form.trip.ApiapplyForm;
import jp.co.tutorial.workflow.form.trip.ApplyForm;
import jp.co.tutorial.workflow.form.trip.TripForm;
import jp.co.tutorial.workflow.service.trip.BusinessTripService;

import org.seasar.framework.beans.util.Beans;

/**
 * {@link BusinessTrip}のロジッククラス.
 * @author intra-mart
 */
public class BusinessTripLogic {

    /** サービスコンポーネントの定義. */
    @Resource
    protected BusinessTripService businessTripService;

    /**
     * ユーザデータIDを条件に旅費情報を検索します.
     *
     * @param userDataId
     *            ユーザデータID
     * @return 旅費情報DTO
     */
    public BusinessTripDto searchTripData(final String userDataId) {

        final BusinessTrip businessTrip = businessTripService.findByUserDataId(userDataId);
        if (businessTrip == null) {
            // 取得件数が0の場合
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.003"));
        }
        // 旅費情報をエンティティからDTOにコピーします。
        final BusinessTripDto businessTripDto = Beans.createAndCopy(
                BusinessTripDto.class, businessTrip).execute();
        return businessTripDto;
    }

    /**
     * 旅費情報登録.
     * @param parameter
     * @param userParameter
     * @param pageType
     * @see ActionProcessParameter
     */
    public void createTrip(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter, final PageType pageType) {

        // 申請・再申請共通処理
        final BusinessTrip businessTrip = paramToEntity(parameter, userParameter);
        if (pageType.equals(PageType.pageTyp_TempSave)) {
            businessTrip.tripId = businessTrip.userDataId;
        }
        try {
            businessTripService.insert(businessTrip);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
    }

    /**
     * 旅費情報更新.
     * @param parameter
     * @param userParameter
     * @param pageType
     * @see ActionProcessParameter
     */
    public void updateTrip(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter, final PageType pageType) {

        // 申請・再申請共通処理
        final BusinessTrip businessTrip = paramToEntity(parameter, userParameter);
        if (pageType.equals(PageType.pageTyp_TempSave)) {
            businessTrip.tripId = businessTrip.userDataId;
        }

        try {
            businessTripService.update(businessTrip);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }
    }

    /**
     * 旅費情報削除.
     * @param userDataId
     */
    public void deleteTrip(final String userDataId) {

        final BusinessTrip businessTrip = new BusinessTrip();
        businessTrip.tripId = userDataId;
        // 旅費削除
        businessTripService.delete(businessTrip);
    }

    /**
     * 旅費情報の更新・申請.
     * @param parameter
     * @param userParameter
     * @see ActionProcessParameter
     */
    public void updateAndApplyTrip(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        // 申請・再申請共通処理
        final BusinessTrip businessTrip = paramToEntity(parameter, userParameter);

        final Map<String, Object> param = new HashMap<String, Object>();
        param.put("tripId", businessTrip.tripId);
        param.put("startDate", businessTrip.startDate);
        param.put("arrivalDate", businessTrip.arrivalDate);
        param.put("destination", businessTrip.destination);
        param.put("reason", businessTrip.reason);
        param.put("tExpenses", businessTrip.tExpenses);
        param.put("lExpenses", businessTrip.lExpenses);
        param.put("userDataId", businessTrip.userDataId);
        param.put("endFlag", businessTrip.endFlag);
        int count = 0;
        try {
            count = businessTripService.updateTripData(param);
        } catch (final EntityExistsException e) {
            // 一意制約違反
            throw new ApplicationRuntimeException(e);
        }

        if (count == 0) {
            // 更新件数が0の場合
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.001"));
        }
        return;
    }

    /**
     * 終了フラグを更新します.
     * @param systemMatterId
     */
    public void updateFlg(final String systemMatterId) {

        // 更新データ生成
        final BusinessTrip businessTrip = new BusinessTrip();
        businessTrip.tripId = systemMatterId;
        businessTrip.endFlag = "1";

        final int count = businessTripService.updateFlg(businessTrip);

        if (count == 0) {
            // 更新件数が0の場合
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.001"));
        }
    }

    /**
     * 旅費申請.
     * @param apiApplyForm
     * @see ApiapplyForm
     */

    @TransactionAttribute(TransactionAttributeType.NEVER)
    // ※ ApplyManager.apply 等の API を使用するときは、トランザクションを
    // 張ってはならないため、↑のアノテーションを付ける。
    public void applyTrip(final ApiapplyForm apiApplyForm) {

        // 申請マネージャー
        final ApplyManager applyManager = new ApplyManager();

        try {
            // 申請パラメータ
            final ApplyParam applyParam = createApplyParam(apiApplyForm);

            // ユーザパラメータ
            final Map<String, Object> userParam = createUserParam(apiApplyForm);

            // 申請処理実行
            // ※ WorkflowExceptionがスローされなければ処理成功
            applyManager.apply(applyParam, userParam);
        } catch (final WorkflowException e) {
            // 申請処理に失敗しました
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.000"), e);
        }

    }

    /**
     * 案件プロパティから合計金額を取得.
     * @param userDataId
     * @return 合計金額
     */
    public int getMatterProperty(final String userDataId) {
        int itemTotal = 0;
        try {
            // 合計金額取得
            final UserActvMatterPropertyValue matterProperty = new UserActvMatterPropertyValue();
            final UserMatterPropertyModel matterPropertyModel = matterProperty
                    .getMatterProperty(userDataId, "trip_total");
            itemTotal = Integer.valueOf(
                    matterPropertyModel.getMatterPropertyValue()).intValue();
        } catch (final WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }

        return itemTotal;
    }

    /**
     * 案件プロパティへ合計金額を登録.
     * @param parameter
     * @param userParameter
     */
    public void createMatterProperty(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        final UserMatterPropertyModel matterPropertyModel = setMatterProperty(
                parameter, userParameter);

        try {
            // 合計金額登録
            final UserActvMatterPropertyValue property = new UserActvMatterPropertyValue();
            final UserMatterPropertyModel[] matterProperty = new UserMatterPropertyModel[1];
            matterProperty[0] = matterPropertyModel;
            property.createMatterProperty(matterProperty);
        } catch (final WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }
        return;
    }

    /**
     * 案件プロパティへ合計金額を更新.
     * @param parameter
     * @param userParameter
     */
    public void updateMatterProperty(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        final UserMatterPropertyModel matterPropertyModel = setMatterProperty(
                parameter, userParameter);

        try {
            // 合計金額更新
            final UserActvMatterPropertyValue property = new UserActvMatterPropertyValue();
            final UserMatterPropertyModel[] matterProperty = new UserMatterPropertyModel[1];
            matterProperty[0] = matterPropertyModel;
            property.updateMatterProperty(matterProperty);
        } catch (final WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }
        return;
    }

    /**
     * 合計金額の案件プロパティ削除.
     * @param userDataId
     */
    public void deleteMatterProperty(final String userDataId) {

        final UserMatterPropertyModel matterPropertyModel = new UserMatterPropertyModel();
        matterPropertyModel.setUserDataId(userDataId);
        matterPropertyModel.setMatterPropertyKey("trip_total");

        try {
            // 合計金額の案件プロパティ削除
            final UserActvMatterPropertyValue property = new UserActvMatterPropertyValue();
            final UserMatterPropertyModel[] matterProperty = new UserMatterPropertyModel[1];
            matterProperty[0] = matterPropertyModel;
            property.deleteMatterProperty(matterProperty);
        } catch (final WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        }
        return;
    }

    /**
     * 旅費情報をDTOからFormに詰め替えます.
     *
     * @param businessTripDto
     *            旅費DTO
     * @param tripForm
     *            旅費form
     * @return 旅費情報form
     */
    public TripForm dtoToForm(final BusinessTripDto businessTripDto, final TripForm tripForm) {

        if (businessTripDto != null) {
            Beans.copy(businessTripDto, tripForm)
                    .excludes(startDate(), arrivalDate()).execute();

            // tripformがapplyFormだった場合、入力フォーマットでコピー。
            // それ以外だった場合、標準フォーマットでコピー
            if (tripForm instanceof ApplyForm) {
                if (businessTripDto.startDate != null) {
                    tripForm.startDate = AccountDateTimeFormatter.format(
                            businessTripDto.startDate,
                            DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
                }
                if (businessTripDto.arrivalDate != null) {
                    tripForm.arrivalDate = AccountDateTimeFormatter.format(
                            businessTripDto.arrivalDate,
                            DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
                }
            } else {
                tripForm.startDate = AccountDateTimeFormatter.format(
                        businessTripDto.startDate,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
                tripForm.arrivalDate = AccountDateTimeFormatter.format(
                        businessTripDto.arrivalDate,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_STANDARD);
            }
        }
        return tripForm;
    }

    /**
     * 申請共通処理.
     * エンティティに旅費情報を詰め替えます
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @return 旅費情報エンティティ
     */
    private BusinessTrip paramToEntity(final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {

        String tExpenses = (String) userParameter.get("tExpenses");
        String lExpenses = (String) userParameter.get("lExpenses");
        if (tExpenses.equals("")) {
            tExpenses = "0";
        }
        if (lExpenses.equals("")) {
            lExpenses = "0";
        }

        final String startDate = (String) userParameter.get("startDate");
        final String arrivalDate = (String) userParameter.get("arrivalDate");

        final BusinessTrip businessTrip = new BusinessTrip();
        businessTrip.tripId = parameter.getSystemMatterId();
        try {
            // 出発日
            if (!startDate.equals("")) {
                businessTrip.startDate = AccountDateTimeFormatter.parse(
                        startDate, Date.class,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
            }
            // 到着日
            if (!arrivalDate.equals("")) {
                businessTrip.arrivalDate = AccountDateTimeFormatter.parse(
                        arrivalDate, Date.class,
                        DateTimeFormatIds.IM_DATETIME_FORMAT_DATE_INPUT);
            }
        } catch (final DateTimeFormatterException e) {
            throw new ApplicationRuntimeException(e);
        }
        // 出張先
        businessTrip.destination = (String)userParameter.get("destination");
        // 出張理由
        businessTrip.reason = (String)userParameter.get("reason");
        // 交通費
        businessTrip.tExpenses = Long.parseLong(tExpenses, 10);
        // 宿泊費
        businessTrip.lExpenses = Long.parseLong(lExpenses, 10);
        // ユーザデータID
        businessTrip.userDataId = parameter.getUserDataId();
        //終了フラグ
        businessTrip.endFlag = "0";
        return businessTrip;
    }

    /**
     * ユーザパラメータ（業務データ）を生成する.
     *
     * @param apiApplyForm
     *            旅費情報
     * @return　userParam
     */
    private Map<String, Object> createUserParam(final ApiapplyForm apiApplyForm) {

        // ここで設定した値が、申請アクション処理の引数
        // （ActionProcessEventListener#applyの第2引数）に受け渡され、
        // 登録処理が行われる。

        final Map<String, Object> userParam = new HashMap<String, Object>();

        // 申請対象旅費ID格納
        userParam.put("startDate", apiApplyForm.startDate);
        userParam.put("arrivalDate", apiApplyForm.arrivalDate);
        userParam.put("destination", apiApplyForm.destination);
        userParam.put("reason", apiApplyForm.reason);
        userParam.put("tExpenses", apiApplyForm.tExpenses);
        userParam.put("lExpenses", apiApplyForm.lExpenses);
        userParam.put("userDataId", apiApplyForm.imwUserDataId);
        userParam.put("endFlag", "0");
        return userParam;
    }

    /**
     * 申請パラメータを生成する.
     *
     * @param apiApplyForm
     * @return　申請パラメータ
     */
    private ApplyParam createApplyParam(final ApiapplyForm apiApplyForm) {

        // 申請パラメータ
        final ApplyParam applyParam = new ApplyParam();
        // フローID（API申請）
        applyParam.setFlowId(apiApplyForm.imwFlowId);
        // 案件名
        applyParam.setMatterName(apiApplyForm.mattername);
        // 処理コメント
        applyParam.setProcessComment(apiApplyForm.comment);
        // 申請基準日
        applyParam.setApplyBaseDate(apiApplyForm.imwApplyBaseDate);
        // 申請実行者コード
        applyParam.setApplyExecuteUserCode(apiApplyForm.imwUserCode);
        // 申請権限者コード
        applyParam.setApplyAuthUserCode(apiApplyForm.imwUserCode);
        // ユーザデータID
        applyParam.setUserDataId(apiApplyForm.imwUserDataId);
        // 権限者会社コード
        try {
            final ApplyManager applyManager = new ApplyManager();

            final AuthUserOrgzModel[] org = applyManager.getAuthUserOrgz(
                    apiApplyForm.imwFlowId, WorkflowUtil.getCurrentDateStr(),
                    apiApplyForm.imwUserCode);

            if (org.length > 0) {
                // 権限者会社コード格納
                applyParam.setApplyAuthCompanyCode(org[0].getCompanyCode());
                // 権限者組織セットコード格納
                applyParam.setApplyAuthOrgzSetCode(org[0].getOrgzSetCode());
                // 権限者組織コード格納
                applyParam.setApplyAuthOrgzCode(org[0].getOrgzCode());
            }
        } catch (final WorkflowException e) {
            // 権限者コードの取得に失敗しました
            throw new ApplicationRuntimeException(getMessage("IMW.TRP.ERR.003"), e);
        }

        return applyParam;

    }

    /**
     * 案件プロパティをセットします.
     *
     * @param parameter
     *            ワークフローパラメータ情報
     * @param userParameter
     *            リクエストパラメータ
     * @return 案件プロパティ
     */
    private UserMatterPropertyModel setMatterProperty(
            final ActionProcessParameter parameter,
            final Map<String, Object> userParameter) {
        final UserMatterPropertyModel matterPropertyModel = new UserMatterPropertyModel();

        // 案件プロパティ情報のセット
        matterPropertyModel.setUserDataId(parameter.getUserDataId());
        matterPropertyModel.setMatterPropertyKey("trip_total");
        String tExpenses = (String) userParameter.get("tExpenses");
        String lExpenses = (String) userParameter.get("lExpenses");
        if (tExpenses.equals("")) {
            tExpenses = "0";
        }
        if (lExpenses.equals("")) {
            lExpenses = "0";
        }
        matterPropertyModel.setMatterPropertyValue(Long.valueOf(tExpenses)
                .longValue() + Long.valueOf(lExpenses).longValue() + "");

        return matterPropertyModel;
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