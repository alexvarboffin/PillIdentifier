package com.walhalla.lib;

import java.util.Arrays;

public class CircleArray {

    private static final int COUNT_OF_COLOR_MAX = 2;
    public String[] input;
    private int index;

    public CircleArray(int arraySize) {
        this.input = new String[(arraySize > 0) ? arraySize : COUNT_OF_COLOR_MAX];
        this.index = -1;
    }

    int index2 = -1;


    //0
    //1
    //0

    public void pull(String hello) {
//        if (!full(index)) {
//            selectedColors[++index] = hello;
//        } else {
//
//            if (!full(index2)) {
//                String[] raw = new String[selectedColors.length];
//                raw[++index2] = hello;
//                for (int i = index2+1; i < raw.length; i++) {
//                    raw[i] = selectedColors[i];
//                }
//                for (int i = 0; i < selectedColors.length; i++) {
//                    selectedColors[i] = raw[i];
//                }
//            }else {
//                index2=-1;
//            }
//        }

        input = ashift(input);
        input[0] = hello;
        //sout(Arrays.toString(input));
    }

    private String[] ashift(String[] input) {
        String[] raw = new String[input.length];
        raw[0] = input[input.length - 1];//Set last in first
        for (int i = 1; i < input.length; i++) {
            raw[i] = input[i - 1];
        }
        return raw;
    }

    private boolean full(int index) {
        return index + 1 > input.length - 1;
    }
}
