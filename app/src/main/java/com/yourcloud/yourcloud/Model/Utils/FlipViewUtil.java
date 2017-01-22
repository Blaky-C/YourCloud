package com.yourcloud.yourcloud.Model.Utils;

import java.util.Map;

/**
 * Created by ritchie-huang on 17-1-21.
 */

public class FlipViewUtil {

    public String firstLetter;
    public Integer flipColor;

    public FlipViewUtil() {
    }

    public void setFlipColor(Map<String, Integer> map) {
        this.flipColor = map.get(firstLetter);
    }

    public Integer getFlipColor() {
        return flipColor;
    }


    public void setFirstLetter(String word) {
         firstLetter = word.substring(0, 1).toUpperCase();
    }

    public String getFirstLetter(){
        return firstLetter;
    }


}
