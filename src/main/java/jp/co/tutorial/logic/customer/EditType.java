package jp.co.tutorial.logic.customer;

/**
 * 画面種別.
 *
 * @author intra-mart
 */
public enum EditType {

    /** 登録. */
    editTyp_Create("0", "登録"),
    /** 編集. */
    editTyp_Update("1", "編集"),
    /** 削除. */
    editTyp_Delete("2", "削除");

    /** code. */
    private final String code;

    /** messageId. */
    private final String messageId;

    /**
     * 編集種別.
     * @param code
     * @param messageId
     */
    private EditType(final String code, final String messageId) {
        this.code = code;
        this.messageId = messageId;
    }

    /**
     * 宣言に含まれるenum定数のコード値を取得します.
     *
     * @return String 宣言に含まれるenum定数のコード値
     */
    @Override
    public String toString() {
        return this.code;
    }

    /**
     * EditTypeを返却します.
     *
     * @param code
     * @return EditType 編集種別enum定数
     */
    public static EditType getEnum(final String code) {
        if (editTyp_Create.toString().equals(code)) {
            return editTyp_Create;
        } else if (editTyp_Update.toString().equals(code)) {
            return editTyp_Update;
        } else if (editTyp_Delete.toString().equals(code)) {
            return editTyp_Delete;
        } else {
            return null;
        }
    }

    /**
     * メッセージIDを返却します.
     * メッセージIDはメッセージプロパティファイルに設定を行う値です。<br>
     *
     * @return String メッセージID
     */
    public String getMessageId() {
        return this.messageId;
    }
}
