package com.sampler.common;

import com.sampler.Game;
import com.sampler.OrthographicCameraSample;
import com.sampler.ShapeRendererSample;
import com.sampler.SpriteBatchSample;
import com.sampler.ViewportSample;

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
            ShapeRendererSample.SAMPLE_INFO
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
