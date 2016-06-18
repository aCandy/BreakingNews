package com.example.tangyi.breakingnews.tools;

import java.util.ArrayList;

/**
 * Created by 在阳光下唱歌 on 2016/5/18.
 */
public class Result {
    private ArrayList<Data> data;
    public void  setData(ArrayList<Data> data){
        this.data=data;
    }
    public ArrayList<Data> getData(){
        return data;
    }

    public class Data{
        private String content;
        private String updatetime;
        public void setContent(String content){
            this.content=content;
        }
        public String getContent(){
            return content;
        }
        public void setUpdatetime(String updatetime){
            this.updatetime=updatetime;
        }
        public String getUpdatetime(){
            return updatetime;
        }


    }


}
