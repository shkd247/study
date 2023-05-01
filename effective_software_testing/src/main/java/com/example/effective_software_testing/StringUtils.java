package com.example.effective_software_testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String[] substringsBetween(final String str, final String open, final String close) {
        if(str == null || open.isEmpty() || close.isEmpty())
            return null;

        int strLen = str.length();
        int closeLen = close.length();
        int openLen = open.length();

        if(strLen == 0)
            return EMPTY_STRING_ARRAY;

        ArrayList<String> list = new ArrayList<>();
        int pos = 0; // 문자열에서 검색 중인 위치를 나타내는 포인터
        while (pos < strLen - closeLen) {
            int start = str.indexOf(open, pos); // 다음 open 태그 위치를 찾는다

            if(start<0) // 문자열에 더이상 open 태그가 없으면 반복문을 빠져나간다
                break;

            start += openLen;
            int end = str.indexOf(close, start); // end 태그 위치를 찾는다
            if(end<0) // 문자열에 더이상 end 태그가 없으면 반복문을 빠져나간다
                break;

            list.add(str.substring(start, end)); // open - end 사이의 문자열을 리스트에 추가한다
            pos = end + closeLen; // 포인터의 위치를 end 태그 바로 뒤로 바꾼다
        }

        if(list.isEmpty())
            return null;

        return list.toArray(EMPTY_STRING_ARRAY);
    }

}
