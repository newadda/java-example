package org.onecell;

import java.util.LinkedList;
import java.util.List;

public class DatasDto<T> {
    List<T> datas = null;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
