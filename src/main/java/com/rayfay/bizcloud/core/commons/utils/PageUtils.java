package com.rayfay.bizcloud.core.commons.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by STZHANG on 2017/5/17.
 */
public class PageUtils {
    public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortDirection, String sortField){
        Sort.Direction direction = Sort.Direction.ASC;
        if("descend".equalsIgnoreCase(sortDirection)){
            sortDirection = "desc";
        }else if("ascend".equalsIgnoreCase(sortDirection)){
            sortDirection = "asc";
        }
        if(StringUtils.isNotBlank(sortDirection)) {
            direction = Sort.Direction.fromString(sortDirection);
        }
        PageRequest page = new PageRequest(pageNumber, pagzSize, direction, sortField);
        return page;
    }
    public static PageRequest buildPageRequest(int pageNumber, int pagzSize){
        PageRequest page = new PageRequest(pageNumber, pagzSize);
        return page;
    }

}
