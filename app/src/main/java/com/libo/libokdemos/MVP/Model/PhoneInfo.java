package com.libo.libokdemos.MVP.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by libok on 2018-01-10.
 */

public class PhoneInfo {

    /**
     * meta : {"result":"0","result_info":"","jump_url":""}
     * data : {"operator":"移动","area":"陕西","area_operator":"陕西移动","support_price":{"100":"115","500":"507","1000":"1000","2000":"2000","3000":"2995","5000":"4993","10000":"9988","20000":"19978","30000":"29968","50000":"49967"},"promotion_info":[]}
     */

    private MetaBean meta;
    private DataBean data;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class MetaBean {
        /**
         * result : 0
         * result_info :
         * jump_url :
         */

        private String result;
        private String result_info;
        private String jump_url;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult_info() {
            return result_info;
        }

        public void setResult_info(String result_info) {
            this.result_info = result_info;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }

    public static class DataBean {
        /**
         * operator : 移动
         * area : 陕西
         * area_operator : 陕西移动
         * support_price : {"100":"115","500":"507","1000":"1000","2000":"2000","3000":"2995","5000":"4993","10000":"9988","20000":"19978","30000":"29968","50000":"49967"}
         * promotion_info : []
         */

        private String operator;
        private String area;
        private String area_operator;
        private SupportPriceBean support_price;
        private List<?> promotion_info;

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea_operator() {
            return area_operator;
        }

        public void setArea_operator(String area_operator) {
            this.area_operator = area_operator;
        }

        public SupportPriceBean getSupport_price() {
            return support_price;
        }

        public void setSupport_price(SupportPriceBean support_price) {
            this.support_price = support_price;
        }

        public List<?> getPromotion_info() {
            return promotion_info;
        }

        public void setPromotion_info(List<?> promotion_info) {
            this.promotion_info = promotion_info;
        }

        public static class SupportPriceBean {
            /**
             * 100 : 115
             * 500 : 507
             * 1000 : 1000
             * 2000 : 2000
             * 3000 : 2995
             * 5000 : 4993
             * 10000 : 9988
             * 20000 : 19978
             * 30000 : 29968
             * 50000 : 49967
             */

            @SerializedName("100")
            private String _$100;
            @SerializedName("500")
            private String _$500;
            @SerializedName("1000")
            private String _$1000;
            @SerializedName("2000")
            private String _$2000;
            @SerializedName("3000")
            private String _$3000;
            @SerializedName("5000")
            private String _$5000;
            @SerializedName("10000")
            private String _$10000;
            @SerializedName("20000")
            private String _$20000;
            @SerializedName("30000")
            private String _$30000;
            @SerializedName("50000")
            private String _$50000;

            public String get_$100() {
                return _$100;
            }

            public void set_$100(String _$100) {
                this._$100 = _$100;
            }

            public String get_$500() {
                return _$500;
            }

            public void set_$500(String _$500) {
                this._$500 = _$500;
            }

            public String get_$1000() {
                return _$1000;
            }

            public void set_$1000(String _$1000) {
                this._$1000 = _$1000;
            }

            public String get_$2000() {
                return _$2000;
            }

            public void set_$2000(String _$2000) {
                this._$2000 = _$2000;
            }

            public String get_$3000() {
                return _$3000;
            }

            public void set_$3000(String _$3000) {
                this._$3000 = _$3000;
            }

            public String get_$5000() {
                return _$5000;
            }

            public void set_$5000(String _$5000) {
                this._$5000 = _$5000;
            }

            public String get_$10000() {
                return _$10000;
            }

            public void set_$10000(String _$10000) {
                this._$10000 = _$10000;
            }

            public String get_$20000() {
                return _$20000;
            }

            public void set_$20000(String _$20000) {
                this._$20000 = _$20000;
            }

            public String get_$30000() {
                return _$30000;
            }

            public void set_$30000(String _$30000) {
                this._$30000 = _$30000;
            }

            public String get_$50000() {
                return _$50000;
            }

            public void set_$50000(String _$50000) {
                this._$50000 = _$50000;
            }
        }
    }
}
