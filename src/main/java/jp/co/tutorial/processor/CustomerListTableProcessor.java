package jp.co.tutorial.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.intra_mart.foundation.ui.ajax.AjaxServiceException;
import jp.co.intra_mart.foundation.ui.ajax.component.listtable.ListTableProcessor;
import jp.co.intra_mart.foundation.ui.ajax.component.listtable.ListTableResult;
import jp.co.intra_mart.foundation.ui.ajax.component.listtable.ParameterBean;
import jp.co.tutorial.dto.customer.CustomerDto;
import jp.co.tutorial.logic.customer.CustomerLogic;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * 顧客一覧の取得プロセッサ.
 *
 * @author intra-mart
 *
 */
public class CustomerListTableProcessor implements ListTableProcessor {

    /** ロジックコンポーネントの定義. */
    protected CustomerLogic customerLogic;

    /** コンストラクタ. */
    public CustomerListTableProcessor() {
        // 自動バインディングは適用されないので、明示的に
        // ロジックコンポーネントを取得する
        this.customerLogic = (CustomerLogic) SingletonS2ContainerFactory
                .getContainer().getComponent(CustomerLogic.class);
    }

    /**
     * 顧客一覧取得.
     *
     * @param parameter ページング情報
     */
    @Override
    public ListTableResult getList(final ParameterBean parameter)
            throws AjaxServiceException {
        final ListTableResult result = new ListTableResult();

        try {
            // ページング情報
            final int page = parameter.getPage().intValue();
            final int rowNum = parameter.getRowNum().intValue();
            // ソート情報
            final String sortIndex = parameter.getSortIndex();
            final String sortOrder = parameter.getSortOrder();

            final int start = (page < 0 || rowNum < 0) ? 0 : (page - 1)
                    * rowNum;

            // 顧客件数の取得
            final int count = customerLogic.countCustomer();
            // 顧客情報の取得
            final List<CustomerDto> customerDtoList = customerLogic
                    .getCustomerList(sortIndex, sortOrder, start, rowNum);

            final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

            for (final CustomerDto customerDto : customerDtoList) {
                final HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("customerCd", customerDto.customerCd);
                map.put("customerName", customerDto.customerName);
                final String chargeStfName = customerLogic
                        .getChargeStfName(customerDto.chargeStfCd);
                map.put("chargeStf", chargeStfName);
                map.put("edit",
                        "<div class='im-ui-icon-common-16-update' width=\'20\' height=\'20\' />");

                data.add(map);
            }

            // 表示情報の設定
            result.setPage(page);
            result.setTotal(count);
            result.setData(data);
        } catch (final Exception e) {
            throw new AjaxServiceException(e.getMessage(), e);
        }

        return result;
    }
}
