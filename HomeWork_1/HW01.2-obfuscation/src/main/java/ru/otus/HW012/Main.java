package ru.otus.HW012;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList(Splitter.on(',')
                .trimResults(CharMatcher.is('|'))
                .omitEmptyStrings()
                .split("|1abc,|2|,,4cd|,|5 c d e |,def6")
        );
        list.forEach(System.out::println);
    }
}
