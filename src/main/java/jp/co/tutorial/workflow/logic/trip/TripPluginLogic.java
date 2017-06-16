package jp.co.tutorial.workflow.logic.trip;

import static jp.co.intra_mart.common.aid.jdk.java.util.LocaleUtil.toLocale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.intra_mart.common.platform.log.Logger;
import jp.co.intra_mart.foundation.exception.BizApiException;
import jp.co.intra_mart.foundation.master.common.search.AppCmnSearchCondition;
import jp.co.intra_mart.foundation.master.company.CompanyManager;
import jp.co.intra_mart.foundation.master.company.model.CompanyPostBizKey;
import jp.co.intra_mart.foundation.master.company.model.DepartmentListNode;
import jp.co.intra_mart.foundation.master.user.model.UserBizKey;
import jp.co.intra_mart.foundation.master.user.model.UserListNode;
import jp.co.intra_mart.foundation.workflow.application.general.ActvMatter;
import jp.co.intra_mart.foundation.workflow.application.general.UserActvMatterPropertyValue;
import jp.co.intra_mart.foundation.workflow.application.model.MatterProcessHistoryModel;
import jp.co.intra_mart.foundation.workflow.application.model.UserMatterPropertyModel;
import jp.co.intra_mart.foundation.workflow.exception.WorkflowException;
import jp.co.intra_mart.foundation.workflow.listener.IWorkflowAuthorityExecEventListener;
import jp.co.intra_mart.foundation.workflow.listener.model.TargetUserModel;
import jp.co.intra_mart.foundation.workflow.listener.model.WorkflowSortCondition;
import jp.co.intra_mart.foundation.workflow.listener.param.WorkflowAuthorityParameter;
import jp.co.intra_mart.foundation.workflow.listener.param.WorkflowMatterParameter;
import jp.co.intra_mart.foundation.workflow.listener.param.WorkflowParameter;
import jp.co.intra_mart.foundation.workflow.plugin.authority.im_master.model.OrgzDataModel;
import jp.co.intra_mart.foundation.workflow.plugin.authority.im_master.model.UserDataModel;
import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;

/**
 * 処理対象社プラグイン.
 * @author intra-mart
 *
 */
public class TripPluginLogic implements IWorkflowAuthorityExecEventListener {

    /** LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(TripPluginLogic.class);

    /**
     * 処理対象者取得メソッド.
     * <p>
     *
     * @param workflowParam
     * @param matterParam
     * @see WorkflowAuthorityParameter
     * @return List ユーザ展開情報
     */
    public List<UserDataModel> execute(final WorkflowAuthorityParameter workflowParam,
            final WorkflowMatterParameter matterParam) {
        LOGGER.info("----- WorkflowAuthorityExecEventListener - execute -----");

        // ユーザ一覧を取得
        final UserListNode[] listUser = getListUser(workflowParam.getLocaleId(),
                workflowParam.getApplyBaseDate(),
                matterParam.getSystemMatterId(), matterParam.getUserDataId());
        if (listUser == null) {
            return Collections.emptyList();
        }

        final List<UserDataModel> useList = new ArrayList<UserDataModel>();
        try {
            // 各ユーザが所属する組織一覧を取得
            final CompanyManager companyManager = new CompanyManager();
            final UserBizKey bizKey = new UserBizKey();
            final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            final Date date = format.parse(workflowParam.getApplyBaseDate());
            for (int i = 0; i < listUser.length; i++) {
                bizKey.setUserCd(listUser[i].getUserCd());
                final DepartmentListNode[] departmentList = companyManager.listDepartmentWithUser(
                                bizKey,
                                new AppCmnSearchCondition(),
                                false,
                                date,
                                toLocale((String) workflowParam.getLocaleId()));

                final OrgzDataModel[] orgzData = new OrgzDataModel[departmentList.length];
                for (int j = 0; j < departmentList.length; j++) {
                    orgzData[j] = new OrgzDataModel();
                    orgzData[j].setCompanyName("");
                    orgzData[j].setOrgzName(departmentList[j].getDisplayName());
                    orgzData[j]
                            .setCompanyCode(departmentList[j].getCompanyCd());
                    orgzData[j].setOrgzSetCode(departmentList[j]
                            .getDepartmentSetCd());
                    orgzData[j]
                            .setOrgzCode(departmentList[j].getDepartmentCd());
                }

                final UserDataModel userData = new UserDataModel();
                userData.setUserCode(listUser[i].getUserCd());
                userData.setUserName(listUser[i].getDisplayName());
                userData.setLocaleId(workflowParam.getLocaleId());
                userData.setUserOrgzModels(orgzData);

                useList.add(userData);
            }
        } catch (final BizApiException e) {
            throw new ApplicationRuntimeException(e);
        } catch (final ParseException e) {
            throw new ApplicationRuntimeException(e);
        }

        return useList;
    }

    /**
     * 処理対象ユーザリスト取得メソッド.
     * <p>
     *
     * @param workflowParam
     * @param matterParam
     * @param sort
     *
     * @return Map ロケールIDをキーにした処理対象ユーザリスト
     */
    public Map<String, TargetUserModel[]> getTargetUserList(final WorkflowAuthorityParameter workflowParam,
            final WorkflowMatterParameter matterParam,
            final WorkflowSortCondition[] sort) {
        LOGGER.info("----- WorkflowAuthorityExecEventListener - getTargetUserList -----");

        // ユーザ一覧を取得
        final UserListNode[] listUser = getListUser(workflowParam.getLocaleId(),
                workflowParam.getApplyBaseDate(),
                matterParam.getSystemMatterId(), matterParam.getUserDataId());
        if (listUser == null) {
            return null;
        }

        final TargetUserModel[] userlist = new TargetUserModel[listUser.length];
        for (int i = 0; i < listUser.length; i++) {
            userlist[i] = new TargetUserModel();
            userlist[i].setUserCode(listUser[i].getUserCd());
            userlist[i].setUserName(listUser[i].getDisplayName());
            userlist[i].setLocaleId(workflowParam.getLocaleId());
        }

        final Map<String, TargetUserModel[]> map = new HashMap<String, TargetUserModel[]>();
        map.put(workflowParam.getLocaleId(), userlist);

        return map;
    }

    /**
     * 処理対象者プラグイン設定情報表示名取得メソッド.
     * <p>
     *
     * @param workflowParam
     *
     * @return Map ロケールIDをキーにした処理対象ユーザリスト
     *
     */
    public Map<String, String> getDisplayName(final WorkflowParameter workflowParam) {
        LOGGER.info("----- WorkflowAuthorityExecEventListener - getDisplayName -----");

        final Map<String, String> map = new HashMap<String, String>();
        map.put(workflowParam.getLocaleId(), workflowParam.getParameter());

        return map;
    }

    /**
     * 処理対象者の一覧を取得するメソッド.
     * <p>
     *
     * [合計金額]により、処理対象の役職を変更する 3万円以上 ⇒ 部長 3万円未満 ⇒ 課長
     *
     * @param localeId
     * @param applyBaseDate
     * @param systemMatterId
     * @param userDataId
     *
     * @return UserListNode[] ユーザリスト情報
     */
    private UserListNode[] getListUser(final String localeId, final String applyBaseDate,
            final String systemMatterId, final String userDataId) {
        final int maximum = 30000;

        final String post1 = "ps002"; // 役職CD：部長
        final String post2 = "ps003"; // 役職CD：課長
        UserListNode[] listUser = null;

        try {
            // 案件プロパティから[合計金額]を取得
            final UserActvMatterPropertyValue matterProperty = new UserActvMatterPropertyValue();
            final UserMatterPropertyModel matterPropertyModel = matterProperty
                    .getMatterProperty(userDataId, "trip_total");
            final int itemTotal = Integer.valueOf(
                    matterPropertyModel.getMatterPropertyValue()).intValue();

            // 役職に所属するユーザ一覧を取得するための条件を取得
            final CompanyPostBizKey bizKey = new CompanyPostBizKey();
            final ActvMatter actvMatter = new ActvMatter(localeId.toString(),
                    systemMatterId);
            final MatterProcessHistoryModel[] historyModel = actvMatter
                    .getProcessHistoryLatestList();
            for (int i = 0; i < historyModel.length; i++) {
                // 申請者が選択した組織から、「会社CD」「組織セットCD」を取得
                if (historyModel[i].getNodeType().equals("2")) {
                    bizKey.setCompanyCd(historyModel[i].getAuthCompanyCode());
                    bizKey.setDepartmentSetCd(historyModel[i].getAuthOrgzSetCode());
                    break;
                }
            }
            if (itemTotal >= maximum) {
                // 3万円以上 ⇒ 部長
                bizKey.setPostCd(post1);
            } else {
                // 3万円未満 ⇒ 課長
                bizKey.setPostCd(post2);
            }

        // 役職に所属するユーザ一覧を取得
            final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            final Date date = format.parse(applyBaseDate);

            final CompanyManager companyManager = new CompanyManager();
            listUser = companyManager.listUserWithCompanyPost(bizKey,
                    new AppCmnSearchCondition(), date,
                    toLocale((String) localeId));
        } catch (WorkflowException e) {
            throw new ApplicationRuntimeException(e);
        } catch (final BizApiException e) {
            throw new ApplicationRuntimeException(e);
        } catch (final ParseException e) {
            throw new ApplicationRuntimeException(e);
        }

        return listUser;
    }
}
