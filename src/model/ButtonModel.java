package model;

import java.util.List;

/**
 * Created by kail on 2017/4/27.
 */
public class ButtonModel {


    private List<ButtonBean> button;

    public List<ButtonBean> getButton() {
        return button;
    }

    public void setButton(List<ButtonBean> button) {
        this.button = button;
    }

    public static class ButtonBean {

        public ButtonBean(String type, String name, String key) {
            this.type = type;
            this.name = name;
            this.key = key;
        }

        public ButtonBean(String type, String name, String key, List<SubButtonBean> sub_button) {
            this.type = type;
            this.name = name;
            this.key = key;
            this.sub_button = sub_button;
        }

        /**
         * type : click
         * name : 今日歌曲
         * key : V1001_TODAY_MUSIC
         * sub_button : [{"type":"view","name":"搜索","url":"http://www.soso.com/"},{"type":"view","name":"视频","url":"http://v.qq.com/"},{"type":"click","name":"赞一下我们","key":"V1001_GOOD"}]
         */

        private String type;
        private String name;
        private String key;
        private List<SubButtonBean> sub_button;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<SubButtonBean> getSub_button() {
            return sub_button;
        }

        public void setSub_button(List<SubButtonBean> sub_button) {
            this.sub_button = sub_button;
        }

        public static class SubButtonBean {
            /**
             * type : view
             * name : 搜索
             * url : http://www.soso.com/
             * key : V1001_GOOD
             */

            private String type;
            private String name;
            private String url;
            private String key;

            public SubButtonBean(String type, String name, String url, String key) {
                this.type = type;
                this.name = name;
                this.url = url;
                this.key = key;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }
    }
}
