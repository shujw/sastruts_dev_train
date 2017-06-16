package jp.co.tutorial.workflow.form;

import java.io.Serializable;

/**
 * IM-Workflow の標準リクエストパラメータプロパティに持つ ActionForm.
 *
 * @author NTTデータ イントラマート
 */
public class ImWorkflowForm implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** ログイングループID. */
    public String imwGroupId;

    /** ログインユーザCD. */
    public String imwUserCode;

    /** 画面種別. */
    public String imwPageType;

    /** ユーザデータID. */
    public String imwUserDataId;

    /** システム案件ID. */
    public String imwSystemMatterId;

    /** ノードID. */
    public String imwNodeId;

    /** 到達種別. */
    public String imwArriveType;

    /** 権限者CD（代理処理の場合のみ）. */
    public String imwAuthUserCode;

    /** 申請基準日. */
    public String imwApplyBaseDate;

    /** フローID. */
    public String imwFlowId;

    /** 連続処理パラメータ. */
    public String imwSerialProcParams;

    /** 呼出元パラメータ. */
    public String imwCallOriginalParams;

    /** 呼出元ページパス. */
    public String imwCallOriginalPagePath;
}
