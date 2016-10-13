package com.dandy.searchapp.imp;



import com.dandy.searchapp.entity.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by Dandy on 2016/10/10.
 */

public interface SearchResultImp {
    void searchSuccess(Map<Integer, List<Result>> resultMap);
}
