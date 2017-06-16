package jp.co.tutorial.service.customer;

import static jp.co.tutorial.entity.customer.MSampleCustomerNames.attachmentFile;
import static jp.co.tutorial.entity.customer.MSampleCustomerNames.customerCd;
import static org.seasar.extension.jdbc.operation.Operations.eq;

import java.util.List;

import jp.co.intra_mart.framework.extension.seasar.struts.exception.ApplicationRuntimeException;
import jp.co.tutorial.entity.customer.MSampleCustomer;

import org.seasar.extension.jdbc.exception.SNonUniqueResultException;

/**
 * {@link MSampleCustomer}のサービスクラスです.
 *
 * @author intra-mart
 */
public class CustomerService extends AbstractService<MSampleCustomer> {

    /**
     * 顧客CDを条件に顧客情報を検索します.
     *
     * @param customerCd
     *            顧客CD
     * @return 顧客情報エンティティ
     */
    public MSampleCustomer findByCustomerCd(final String customerCd) {

        MSampleCustomer mSampleCustomer;
        try {
            mSampleCustomer = select().where(eq(customerCd(), customerCd))
                    .getSingleResult();
        } catch (final SNonUniqueResultException e) {
            throw new ApplicationRuntimeException(e);
        }

        return mSampleCustomer;
    }

    /**
     * 参考資料以外を更新します.
     *
     * @param mSampleCustomer
     *            顧客情報
     */
    public void updateExcludeAttachmentFile(
            final MSampleCustomer mSampleCustomer) {

        try {
            jdbcManager.update(mSampleCustomer).excludes(attachmentFile())
                    .execute();
        } catch (final SNonUniqueResultException e) {
            throw new ApplicationRuntimeException(e);
        }
        return;
    }

    /**
     * 顧客一覧を取得します.
     *
     * @param index ソート情報
     * @param offset 検索開始行
     * @param limit 取得件数
     * @return 顧客情報エンティティリスト
     */
    public List<MSampleCustomer> getCustomerData(final String index,
            final int offset, final int limit) {

        return jdbcManager.from(MSampleCustomer.class).limit(limit)
                .offset(offset).orderBy(index).getResultList();
    }
}
