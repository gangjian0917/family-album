package com.vbeesoft.familyalbum.model;

import java.io.Serializable;
import java.util.List;

public class AlbumBean implements Serializable {

    public static long serialVersionUID = 0;

    /**
     * result_code : 501
     * result : [{"date":"2019-12-07","imgUrl":["http://203.195.217.253/localhost1","http://203.195.217.253/localhost2","http://203.195.217.253/localhost3"]},{"date":"2019-12-08","imgUrl":["http://203.195.217.253/localhost4"]},{"date":"2019-12-09","imgUrl":["http://203.195.217.253/localhost5","http://203.195.217.253/localhost6"]}]
     */

    private int result_code;
    private List<ResultBean> result;

    public AlbumBean() {
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }


}
