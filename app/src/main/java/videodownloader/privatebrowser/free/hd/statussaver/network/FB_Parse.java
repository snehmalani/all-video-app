package videodownloader.privatebrowser.free.hd.statussaver.network;

import android.text.TextUtils;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FB_Parse {
    private final String linkTitle;
    private final Def_Parse resultHandler;

    public FB_Parse(Def_Parse resultHandler, String str) {
        this.linkTitle = str;
        this.resultHandler = resultHandler;
    }

                                                                        public void findLinks(String str) throws UnsupportedEncodingException {
        String str2;
        String str3;
        boolean z;
        String group;
        String group2 = null;
        store_model_video_link.listVideos listvideos;
        String group3 = null;
        store_model_video_link.listVideos listvideos2;
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        if (!TextUtils.isEmpty(str)) {
            Matcher matcher = Pattern.compile("\"playable_url\":\"(.*?)\"").matcher(str);
            Matcher matcher2 = Pattern.compile("\"playable_url_quality_hd\":\"(.*?)\"").matcher(str);
            Matcher matcher3 = Pattern.compile("\"preferred_thumbnail\":\\{\"image\":\\{\"uri\":\"(.*?)\"").matcher(str);
            Matcher matcher4 = Pattern.compile("\"og:title\" content=\"(.*?)\"").matcher(str);
            Matcher matcher5 = Pattern.compile("\"og:image\" content=\"(.*?)\"").matcher(str);
            if (((Matcher) matcher3).find()) {
                str3 = matcher3.group(1);
                if (str3 != null) {

                    if (!str3.isEmpty()) {
                        str3 = str3.replace("\\", "").replace("&amp;", "&").replace("u0026", "&");
                    }
                }
                if (matcher4.find()) {
                    str2 = matcher4.group(1);
                    if (matcher.find() && (group3 = matcher.group(1)) != null && !group3.isEmpty()) {
                        listvideos2 = new store_model_video_link.listVideos();
                        listvideos2.setN_link_url(URLDecoder.decode(group3.replace("\\", ""), "UTF-8"));
                        if (str3 != null && !str3.isEmpty()) {
                            listvideos2.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                        }
                        if (str2 == null && !str2.isEmpty()) {
                            listvideos2.setN_link_title(str2);
                        } else {
                            listvideos2.setN_link_title(this.linkTitle);
                        }
                        listvideos2.setN_link_extension("mp4");
                        arrayList.add(listvideos2);
                        z = true;
                        if (matcher2.find() && (group2 = matcher2.group(1)) != null && !group2.isEmpty()) {
                            listvideos = new store_model_video_link.listVideos();
                            listvideos.setN_link_url(URLDecoder.decode(group2.replace("\\", ""), "UTF-8"));
                            if (str3 != null && !str3.isEmpty()) {
                                listvideos.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                            }
                            if (str2 == null && !str2.isEmpty()) {
                                listvideos.setN_link_title(str2);
                            } else {
                                listvideos.setN_link_title(this.linkTitle);
                            }
                            listvideos.setN_link_extension("mp4");
                            arrayList.add(listvideos);
                        }
                        if (!z) {
                            try {
                                Matcher matcher6 = Pattern.compile("\"og:video\" content=\"(.*?)\"").matcher(str);
                                if (matcher6.find() && (group = matcher6.group(1)) != null && !group.isEmpty() && !group.contains("fbsbx.com")) {
                                    store_model_video_link.listVideos listvideos3 = new store_model_video_link.listVideos();
                                    listvideos3.setN_link_url(group.replace("\\", "").replace("&amp;", "&"));
                                    if (str3 != null && !str3.isEmpty()) {
                                        listvideos3.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                                    }
                                    if (str2 != null && !str2.isEmpty()) {
                                        listvideos3.setN_link_title(str2);
                                    } else {
                                        listvideos3.setN_link_title(this.linkTitle);
                                    }
                                    listvideos3.setN_link_extension("mp4");
                                    arrayList.add(listvideos3);
                                    z = true;
                                }
                            } catch (Exception unused) {
                                throw new RuntimeException(unused);
                            }
                        }
                        if (!z) {
                            try {
                                Matcher matcher7 = Pattern.compile("\"contentUrl\":\"([^\"]*)\"").matcher(str);
                                if (matcher7.find()) {
                                    String group4 = matcher7.group(1);
                                    store_model_video_link.listVideos listvideos4 = new store_model_video_link.listVideos();
                                    if (group4 != null && !TextUtils.isEmpty(group4)) {
                                        listvideos4.setN_link_url(URLDecoder.decode(group4.replace("\\", ""), "UTF-8"));
                                        listvideos4.setN_link_extension("mp4");
                                        if (str3 !=null && !str3.isEmpty()) {
                                            listvideos4.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                                        }
                                        if (str2 != null && !str2.isEmpty()) {
                                            listvideos4.setN_link_title(str2);
                                        } else {
                                            listvideos4.setN_link_title(this.linkTitle);
                                        }
                                        arrayList.add(listvideos4);
                                        z = true;
                                    }
                                }
                            } catch (Exception unused2) {
                                throw new RuntimeException(unused2);
                            }
                        }
                        if (!z) {
                            try {
                                Matcher matcher8 = Pattern.compile("video_redirect/\\?src=([^\"]*)").matcher(str);
                                if (matcher8.find()) {
                                    String group5 = matcher8.group(1);
                                    store_model_video_link.listVideos listvideos5 = new store_model_video_link.listVideos();
                                    if (group5 != null && !TextUtils.isEmpty(group5)) {
                                        listvideos5.setN_link_url(URLDecoder.decode(group5.replace("\\", ""), "UTF-8"));
                                        listvideos5.setN_link_extension("mp4");
                                        if (str3 != null && !str3.isEmpty()) {
                                            listvideos5.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                                        }
                                        if (str2 != null && !str2.isEmpty()) {
                                            listvideos5.setN_link_title(str2);
                                        } else {
                                            listvideos5.setN_link_title(this.linkTitle);
                                        }
                                        arrayList.add(listvideos5);
                                    }
                                }
                            } catch (Exception unused3) {
                                throw new RuntimeException(unused3);
                            }
                        }
                    }
                    z = false;
                    if (matcher2.find()) {
                        listvideos = new store_model_video_link.listVideos();
                        listvideos.setN_link_url(URLDecoder.decode(Objects.requireNonNull(group2).replace("\\", ""), "UTF-8"));
                        if (str3 != null) {
                            listvideos.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                        }
                        listvideos.setN_link_title(this.linkTitle);
                        listvideos.setN_link_extension("mp4");
                        arrayList.add(listvideos);
                        z = true;
                    }
                }
                str2 = "";
                if (matcher.find()) {
                    listvideos2 = new store_model_video_link.listVideos();
                    listvideos2.setN_link_url(URLDecoder.decode(Objects.requireNonNull(group3).replace("\\", ""), "UTF-8"));
                    if (str3 != null) {
                        listvideos2.setN_link_image(URLDecoder.decode(str3, "UTF-8"));
                    }
                    listvideos2.setN_link_title(this.linkTitle);
                    listvideos2.setN_link_extension("mp4");
                    arrayList.add(listvideos2);
                    z = true;
                    matcher2.find();
                }
                z = false;
                matcher2.find();
            } else {
                if (matcher5.find()) {
                    str3 = matcher5.group(1);
                    if (str3 !=null) {
                        if (!str3.isEmpty()) {
                            str3 = str3.replace("\\", "").replace("&amp;", "&").replace("u0026", "&");
                        }
                    }
                    matcher4.find();
                    matcher.find();
                    z = false;
                    matcher2.find();
                }
                matcher4.find();
                matcher.find();
                z = false;
                matcher2.find();
            }
        }
        Def_Parse def_Parse = this.resultHandler;
        if (def_Parse != null) {
            def_Parse.parseData(arrayList);
        }
    }
}
