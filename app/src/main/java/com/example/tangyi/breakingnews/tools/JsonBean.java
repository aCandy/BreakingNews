package com.example.tangyi.breakingnews.tools;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class JsonBean {
    private ShowApi_Res_Body showApi_res_body;

    public void setShowApi_res_body(ShowApi_Res_Body showApi_res_body) {
        this.showApi_res_body = showApi_res_body;
    }

    public ShowApi_Res_Body getShowApi_res_body() {

        return showApi_res_body;
    }
    public class ShowApi_Res_Body{
        private PageBean pagebean;

        public PageBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PageBean pagebean) {

            this.pagebean = pagebean;
        }
        public class PageBean{
            private ArrayList<ContentList> contentlist;

            public void setContentlist(ArrayList<ContentList> contentlist) {
                this.contentlist = contentlist;
            }

            public ArrayList<ContentList> getContentlist() {

                return contentlist;
            }
            public class ContentList{
                private String title;
                private String pubDate;
                private String link;

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getTitle() {

                    return title;
                }

                public String getPubDate() {
                    return pubDate;
                }

                public String getLink() {
                    return link;
                }
            }
        }
    }
}
