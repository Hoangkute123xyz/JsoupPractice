package com.hoangpro.jsouppractice.morefunc;

import android.text.Html;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyFunct {
    public static String htlm2Furigana(String html) {
        html = html.replaceAll("<ruby>", "{");
        html = html.replaceAll("<rt>", ";");
        html = html.replaceAll("</rt></ruby>", "}");
        return Html.fromHtml(html).toString();
    }
    public static String toFurigana(String s1,String s2){
        String result = "";
        List<String> lg1 = new ArrayList<>(), lg2 = new ArrayList<>();
        Collections.addAll(lg1, s1.split(""));
        Collections.addAll(lg2, s2.split(""));
        while (lg1.size() > 0) {
            Log.e("Result", lg1.size()+"");
            String sup = "", sub = "";
            if (lg1.size()==1){
                if (lg1.get(0).equals(lg2.get(0))){
                    result+=lg1.get(0);
                    lg1.remove(0);
                    lg2.remove(0);
                } else {
                    while (lg2.size()>0){
                        sub+=lg2.get(0);
                        lg2.remove(0);
                    }
                    result+="<ruby>"+lg1.get(0)+"<rt>"+sub+"</rt>"+"</ruby>";
                    lg1.remove(0);
                }
            } else if (lg1.get(0).equals(lg2.get(0))) {
                result += lg1.get(0);
                lg1.remove(0);
                lg2.remove(0);
            } else {
                int f1 = -1, f2 = -1;
                for (int i = 0; i < lg1.size(); i++) {
                    boolean isStop = false;
                    for (int j = 0; j < lg2.size(); j++) {
                        if (lg1.get(i).equals(lg2.get(j))) {
                            f1 = i;
                            f2 = j;
                            isStop = true;
                            break;
                        }
                    }
                    if (isStop) {
                        break;
                    }
                }
                if (f1 == -1){
                    f1=lg1.size()-1;
                    f2=lg2.size()-1;
                }
                while (f1>0){
                    sup += lg1.get(0);
                    lg1.remove(0);
                    f1--;
                }

                while(f2>0) {
                    sub += lg2.get(0);
                    lg2.remove(0);
                    f2--;
                }
                sub = "<rt>" + sub + "</rt>";
                sup = "<ruby>" + sup.trim() + sub.trim() + "</ruby>";
                result += sup;
            }

        }
        return result;
    }
}
