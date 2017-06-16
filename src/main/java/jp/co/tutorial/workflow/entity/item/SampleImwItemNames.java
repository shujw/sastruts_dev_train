package jp.co.tutorial.workflow.entity.item;

import org.seasar.extension.jdbc.name.PropertyName;

/**
 * {@link SampleImwItem}のプロパティ名の集合です.
 *
 * @author intra-mart
 */
public class SampleImwItemNames {

    /**
     * userDataIdのプロパティ名を返します.
     *
     * @return userDataIdのプロパティ名
     */
    public static PropertyName<String> userDataId() {
        return new PropertyName<String>("userDataId");
    }

    /**
     * itemNameのプロパティ名を返します.
     *
     * @return itemNameのプロパティ名
     */
    public static PropertyName<String> itemName() {
        return new PropertyName<String>("itemName");
    }

    /**
     * itemPriceのプロパティ名を返します.
     *
     * @return itemPriceのプロパティ名
     */
    public static PropertyName<String> itemPrice() {
        return new PropertyName<String>("itemPrice");
    }

    /**
     * itemDateのプロパティ名を返します.
     *
     * @return itemDateのプロパティ名
     */
    public static PropertyName<String> itemDate() {
        return new PropertyName<String>("itemDate");
    }

    /**
     * itemReasonのプロパティ名を返します.
     *
     * @return itemReasonのプロパティ名
     */
    public static PropertyName<String> itemReason() {
        return new PropertyName<String>("itemReason");
    }

    /**
     * @author intra-mart
     */
    public static class _SampleImwItemNames extends PropertyName<SampleImwItem> {

        /**
         * インスタンスを構築します.
         */
        public _SampleImwItemNames() {
        }

        /**
         * インスタンスを構築します.
         *
         * @param name
         *            名前
         */
        public _SampleImwItemNames(final String name) {
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
        public _SampleImwItemNames(final PropertyName<?> parent,
                final String name) {
            super(parent, name);
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
         * itemNameのプロパティ名を返します.
         *
         * @return itemNameのプロパティ名
         */
        public PropertyName<String> itemName() {
            return new PropertyName<String>(this, "itemName");
        }

        /**
         * itemPriceのプロパティ名を返します.
         *
         * @return itemPriceのプロパティ名
         */
        public PropertyName<String> itemPrice() {
            return new PropertyName<String>(this, "itemPrice");
        }

        /**
         * itemDateのプロパティ名を返します.
         *
         * @return itemDateのプロパティ名
         */
        public PropertyName<String> itemDate() {
            return new PropertyName<String>(this, "itemDate");
        }

        /**
         * itemReasonのプロパティ名を返します.
         *
         * @return itemReasonのプロパティ名
         */
        public PropertyName<String> itemReason() {
            return new PropertyName<String>(this, "itemReason");
        }
    }
}
