package com.cczcrv.jack.cczcrv.Tools.okHttp.utils;

import com.beust.jcommander.internal.Lists;

import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jack on 2018/1/25.
 */

public class DozerHelper extends DozerBeanMapper {
    public <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List destinationList = Lists.newArrayList();
        for (Iterator i$ = sourceList.iterator(); i$.hasNext(); ) {
            Object sourceObject = i$.next();
            Object destinationObject = map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

}
