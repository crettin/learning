package com.dsl.testbuild.common;

import com.dsl.testbuild.BitmapFontSample;
import com.dsl.testbuild.Game;
import com.dsl.testbuild.OrthographicCameraSample;
import com.dsl.testbuild.ShapeRendererSample;
import com.dsl.testbuild.SpriteBatchSample;
import com.dsl.testbuild.ViewportSample;
import com.dsl.testbuild.utils.PoolingSample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SampleInfos {

    public static final List<SampleInfo> ALL = Arrays.asList(
            Game.SAMPLE_INFO,
            OrthographicCameraSample.SAMPLE_INFO,
            ViewportSample.SAMPLE_INFO,
            SpriteBatchSample.SAMPLE_INFO,
            ShapeRendererSample.SAMPLE_INFO,
            BitmapFontSample.SAMPLE_INFO,
            PoolingSample.SAMPLE_INFO
    );

    public static List<String> getSampleNames() {
        List<String> ret = new ArrayList<String>();

        for (SampleInfo info : ALL) {
            ret.add(info.getName());
        }

        Collections.sort(ret);
        return ret;
    }

    public static SampleInfo find(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Name Argument Must Have A Value");


        SampleInfo ret = null;

        for (SampleInfo info : ALL) {
            if (info.getName().equals(name)) {
                ret = info;
                break;
            }
        }

        if (ret == null) {
            System.out.println("Sample Was Not Found");
        }

        return ret;
    }

    private SampleInfos() {
    }
}
