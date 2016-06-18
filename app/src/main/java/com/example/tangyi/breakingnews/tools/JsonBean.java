package com.example.tangyi.breakingnews.tools;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/6/17.
 */
public class JsonBean {
    private ShowApi_Res_Body showapi_res_body;

    public void setShowapi_res_body(ShowApi_Res_Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public ShowApi_Res_Body getShowapi_res_body() {

        return showapi_res_body;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "showapi_res_body=" + showapi_res_body +
                '}';
    }


    public class ShowApi_Res_Body{


        private PageBean pagebean;

        public void setPagebean(PageBean pagebean) {
            this.pagebean = pagebean;
        }

        public PageBean getPagebean() {

            return pagebean;
        }
        @Override
        public String toString() {
            return "ShowApi_Res_Body{" +
                    "pagebean=" + pagebean +
                    '}';
        }


        public class PageBean{
            private ArrayList<ContentList> contentlist;
            @Override
            public String toString() {
                return "PageBean{" +
                        "contentlist=" + contentlist +
                        '}';
            }

            public void setContentlist(ArrayList<ContentList> contentlist) {
                this.contentlist = contentlist;
            }

            public ArrayList<ContentList> getContentlist() {

                return contentlist;
            }




            public class ContentList{
                private String title;
                private String pubDate;
                public void setTitle(String title) {
                    this.title = title;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public String getTitle() {

                    return title;
                }

                public String getPubDate() {

                    return pubDate;
                }



                @Override
                public String toString() {
                    return "Lan{" +
                            "title='" + title + '\'' +
                            ", pubDate='" + pubDate + '\'' +
                            '}';
                }
            }
        }
    }
}
