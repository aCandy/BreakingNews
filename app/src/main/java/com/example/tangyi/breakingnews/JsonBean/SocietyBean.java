package com.example.tangyi.breakingnews.JsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class SocietyBean {
    private ShowApi_Res_Body showapi_res_body;

    public void setShowapi_res_body(ShowApi_Res_Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public ShowApi_Res_Body getShowapi_res_body() {

        return showapi_res_body;
    }



    public class ShowApi_Res_Body{


        private PageBean pagebean;

        public void setPagebean(PageBean pagebean) {
            this.pagebean = pagebean;
        }

        public PageBean getPagebean() {

            return pagebean;
        }



        public class PageBean {
            private ArrayList<ContentList> contentlist;


            public void setContentlist(ArrayList<ContentList> contentlist) {
                this.contentlist = contentlist;
            }

            public ArrayList<ContentList> getContentlist() {

                return contentlist;
            }


            public class ContentList {
                private String title;
                private String source;
                private String link;
                private String pubDate;

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public String getPubDate() {
                    return pubDate;
                }





                public void setLink(String link) {
                    this.link = link;
                }

                public String getLink() {
                    return link;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getTitle() {
                    return title;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public String getSource() {
                    return source;
                }
            }
        }
    }
}
