package jp.co.tutorial.entity.customer;

import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link MSampleCustomer}のプロパティ名の集合です.
 *
 * @author intra-mart
 */
public class MSampleCustomerNames {

    /**
     * customerCdのプロパティ名を返します.
     *
     * @return customerCdのプロパティ名
     */
    public static PropertyName<String> customerCd() {
        return new PropertyName<String>("customerCd");
    }

    /**
     * customerNameのプロパティ名を返します.
     *
     * @return customerNameのプロパティ名
     */
    public static PropertyName<String> customerName() {
        return new PropertyName<String>("customerName");
    }

    /**
     * customerTelnoのプロパティ名を返します.
     *
     * @return customerTelnoのプロパティ名
     */
    public static PropertyName<String> customerTelno() {
        return new PropertyName<String>("customerTelno");
    }

    /**
     * customerAddressのプロパティ名を返します.
     *
     * @return customerAddressのプロパティ名
     */
    public static PropertyName<String> customerAddress() {
        return new PropertyName<String>("customerAddress");
    }

    /**
     * customerSexのプロパティ名を返します.
     *
     * @return customerSexのプロパティ名
     */
    public static PropertyName<String> customerSex() {
        return new PropertyName<String>("customerSex");
    }

    /**
     * customerBirthdayのプロパティ名を返します.
     *
     * @return customerBirthdayのプロパティ名
     */
    public static PropertyName<String> customerBirthday() {
        return new PropertyName<String>("customerBirthday");
    }

    /**
     * attachmentFileのプロパティ名を返します.
     *
     * @return attachmentFileのプロパティ名
     */
    public static PropertyName<String> attachmentFile() {
        return new PropertyName<String>("attachmentFile");
    }

    /**
     * chargeStfCdのプロパティ名を返します.
     *
     * @return chargeStfCdのプロパティ名
     */
    public static PropertyName<String> chargeStfCd() {
        return new PropertyName<String>("chargeStfCd");
    }

    /**
     * updateDateのプロパティ名を返します.
     *
     * @return updateDateのプロパティ名
     */
    public static PropertyName<String> updateDate() {
        return new PropertyName<String>("updateDate");
    }

   /**
     * @author intra-mart
     */
    public static class _MSampleCustomerNames extends PropertyName<MSampleCustomer> {

        /**
         * インスタンスを構築します.
         */
        public _MSampleCustomerNames() {
        }

        /**
         * インスタンスを構築します.
         *
         * @param name
         *            名前
         */
        public _MSampleCustomerNames(final String name) {
            super(name);
        }

        /**
         * インスタンスを構築します.
         *
         * @param parent
         *            親
         * @param name
         *            名前
         */
        public _MSampleCustomerNames(final PropertyName<?> parent, final String name) {
            super(parent, name);
        }

        /**
         * customerCdのプロパティ名を返します.
         *
         * @return customerCdのプロパティ名
         */
        public PropertyName<String> customerCd() {
            return new PropertyName<String>(this, "customerCd");
        }

        /**
         * customerNameのプロパティ名を返します.
         *
         * @return customerNameのプロパティ名
         */
        public PropertyName<String> customerName() {
            return new PropertyName<String>(this, "customerName");
        }

        /**
         * customerTelnoのプロパティ名を返します.
         *
         * @return customerTelnoのプロパティ名
         */
        public PropertyName<String> customerTelno() {
            return new PropertyName<String>(this, "customerTelno");
        }

        /**
         * customerAddressのプロパティ名を返します.
         *
         * @return customerAddressのプロパティ名
         */
        public PropertyName<String> customerAddress() {
            return new PropertyName<String>(this, "customerAddress");
        }

        /**
         * customerSexのプロパティ名を返します.
         *
         * @return customerSexのプロパティ名
         */
        public PropertyName<String> customerSex() {
            return new PropertyName<String>(this, "customerSex");
        }

        /**
         * customerBirthdayのプロパティ名を返します.
         *
         * @return customerBirthdayのプロパティ名
         */
        public PropertyName<String> customerBirthday() {
            return new PropertyName<String>(this, "customerBirthday");
        }

        /**
         * attachmentFileのプロパティ名を返します.
         *
         * @return attachmentFileのプロパティ名
         */
        public PropertyName<String> attachmentFile() {
            return new PropertyName<String>(this, "attachmentFile");
        }

        /**
         * userDataIdのプロパティ名を返します.
         *
         * @return userDataIdのプロパティ名
         */
        public PropertyName<String> userDataId() {
            return new PropertyName<String>(this, "userDataId");
        }

        /**
         * endFlagのプロパティ名を返します.
         *
         * @return endFlagのプロパティ名
         */
        public PropertyName<String> endFlag() {
            return new PropertyName<String>(this, "endFlag");
        }
    }
}
