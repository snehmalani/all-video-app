package videodownloader.privatebrowser.free.hd.statussaver.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import videodownloader.privatebrowser.free.hd.statussaver.mainapp.store_model_video_link;
import videodownloader.privatebrowser.free.hd.statussaver.tool.UtilsForApp;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public final class Insta_Parse {
    private final String linkTitle;
    private final Def_Parse resultHandler;

    public Insta_Parse(Def_Parse paserResultHandler, String str) {
        this.linkTitle = str;
        this.resultHandler = paserResultHandler;
    }

                                                public void findLinks(String str) throws UnsupportedEncodingException {
        String str2;
        String str3;
        boolean z = false;
        String group;
        String group2;
        Def_Parse def_Parse;
        ResponseModel responseModel;
        Model_EdgeSidecarToChildren edge_sidecar_to_children;
        ArrayList<store_model_video_link.listVideos> arrayList = new ArrayList<>();
        Matcher matcher = Pattern.compile("\"video_url\":\"([^\"]*)\"").matcher(str);
        Matcher matcher2 = Pattern.compile("thumbnail_src\":\"(.*?)\"").matcher(str);
        Matcher matcher3 = Pattern.compile("og:title\" content=\"(.*?)\"").matcher(str);
        if (matcher2.find()) {
            str2 = matcher2.group(1);
            if (str2 != null) {
                try {
                    if (!str2.isEmpty()) {
                        str2 = str2.replace("\\", "").replace("&amp;", "&").replace("u0026", "&");
                    }
                } catch (Exception ignored) {
                }
            }
            if (matcher3.find()) {
                str3 = matcher3.group(1);
                responseModel = (ResponseModel) new Gson().fromJson(str, new TypeToken<ResponseModel>() {
                }.getType());
                if (responseModel != null && responseModel.getGraphql() != null) {
                    edge_sidecar_to_children = responseModel.getGraphql().getShortcode_media().getEdge_sidecar_to_children();
                    if (edge_sidecar_to_children == null) {
                        List<Model_Edge> edges = edge_sidecar_to_children.getEdges();
                        z = false;
                        for (int i = 0; i < edges.size(); i++) {
                            try {
                                if (edges.get(i).getNode().isIs_video()) {
                                    String video_url = edges.get(i).getNode().getVideo_url();
                                    if (video_url != null && !video_url.isEmpty() && UtilsForApp.IsValidUrl(video_url)) {
                                        store_model_video_link.listVideos listvideos = new store_model_video_link.listVideos();
                                        listvideos.setN_link_url(video_url.replace("\\", "").replace("&amp;", "&"));
                                        if (str2 != null && !str2.isEmpty()) {
                                            listvideos.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                        }
                                        listvideos.setN_link_extension("mp4");
                                        if (str3 != null && !str3.isEmpty()) {
                                            listvideos.setN_link_title(str3);
                                        } else {
                                            listvideos.setN_link_title(this.linkTitle);
                                        }
                                        arrayList.add(listvideos);
                                        z = true;
                                    }
                                } else {
                                    String src = edges.get(i).getNode().getDisplay_resources().get(edges.get(i).getNode().getDisplay_resources().size() - 1).getSrc();
                                    if (src != null && !src.isEmpty() && UtilsForApp.IsValidUrl(src)) {
                                        store_model_video_link.listVideos listvideos2 = new store_model_video_link.listVideos();
                                        listvideos2.setN_link_url(src.replace("\\", "").replace("&amp;", "&"));
                                        if (str2 != null && !str2.isEmpty()) {
                                            listvideos2.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                        }
                                        listvideos2.setN_link_extension("jpg");
                                        if (str3 != null && !str3.isEmpty()) {
                                            listvideos2.setN_link_title(str3);
                                        } else {
                                            listvideos2.setN_link_title(this.linkTitle);
                                        }
                                        arrayList.add(listvideos2);
                                        z = true;
                                    }
                                }
                            } catch (Exception unused2) {
                                throw new RuntimeException(unused2);
                            }
                        }
                    } else {
                        if (responseModel.getGraphql().getShortcode_media().isIs_video()) {
                            String video_url2 = responseModel.getGraphql().getShortcode_media().getVideo_url();
                            String thumbnail_src = responseModel.getGraphql().getShortcode_media().getThumbnail_src();
                            String title = responseModel.getGraphql().getShortcode_media().getTitle();
                            if (video_url2 != null && !video_url2.isEmpty() && UtilsForApp.IsValidUrl(video_url2)) {
                                store_model_video_link.listVideos listvideos3 = new store_model_video_link.listVideos();
                                listvideos3.setN_link_url(video_url2.replace("\\", "").replace("&amp;", "&"));
                                if (thumbnail_src != null && !thumbnail_src.isEmpty() && UtilsForApp.IsValidUrl(thumbnail_src)) {
                                    listvideos3.setN_link_image(URLDecoder.decode(thumbnail_src, "UTF-8"));
                                } else if (str2 != null && !str2.isEmpty()) {
                                    listvideos3.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                }
                                listvideos3.setN_link_extension("mp4");
                                if (title != null && !title.isEmpty()) {
                                    listvideos3.setN_link_title(title);
                                } else if (str3 != null && !str3.isEmpty()) {
                                    listvideos3.setN_link_title(str3);
                                } else {
                                    listvideos3.setN_link_title(this.linkTitle);
                                }
                                arrayList.add(listvideos3);
                            }
                        } else {
                            String src2 = responseModel.getGraphql().getShortcode_media().getDisplay_resources().get(responseModel.getGraphql().getShortcode_media().getDisplay_resources().size() - 1).getSrc();
                            if (src2 != null && !src2.isEmpty() && UtilsForApp.IsValidUrl(src2)) {
                                store_model_video_link.listVideos listvideos4 = new store_model_video_link.listVideos();
                                listvideos4.setN_link_url(src2.replace("\\", "").replace("&amp;", "&"));
                                if (str2 != null && !str2.isEmpty()) {
                                    listvideos4.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                }
                                listvideos4.setN_link_extension("jpg");
                                if (str3 != null && !str3.isEmpty()) {
                                    listvideos4.setN_link_title(str3);
                                } else {
                                    listvideos4.setN_link_title(this.linkTitle);
                                }
                                arrayList.add(listvideos4);
                            }
                        }
                        z = true;
                    }
                    if (!z) {
                        try {
                            if (matcher.find() && (group = matcher.group(0)) != null && !group.isEmpty()) {
                                String optString = new JSONObject("{" + group + "}").optString("video_url");
                                store_model_video_link.listVideos listvideos5 = new store_model_video_link.listVideos();
                                listvideos5.setN_link_url(optString.replace("\\", "").replace("&amp;", "&"));
                                if (str2 != null && !str2.isEmpty()) {
                                    listvideos5.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                }
                                listvideos5.setN_link_extension("mp4");
                                if (str3 != null && !str3.isEmpty()) {
                                    listvideos5.setN_link_title(str3);
                                } else {
                                    listvideos5.setN_link_title(this.linkTitle);
                                }
                                arrayList.add(listvideos5);
                                z = true;
                            }
                        } catch (Exception unused3) {
                            throw new RuntimeException(unused3);
                        }
                    }
                    if (!z) {
                        try {
                            Matcher matcher4 = Pattern.compile("\"og:video\" content=\"(.*?)\"").matcher(str);
                            if (matcher4.find() && (group2 = matcher4.group(1)) != null && !group2.isEmpty() && !group2.contains("fbsbx.com")) {
                                store_model_video_link.listVideos listvideos6 = new store_model_video_link.listVideos();
                                listvideos6.setN_link_url(group2.replace("\\", "").replace("&amp;", "&"));
                                if (str2 != null && !str2.isEmpty()) {
                                    listvideos6.setN_link_image(URLDecoder.decode(str2, "UTF-8"));
                                }
                                if (str3 != null && !str3.isEmpty()) {
                                    listvideos6.setN_link_title(str3);
                                } else {
                                    listvideos6.setN_link_title(this.linkTitle);
                                }
                                listvideos6.setN_link_extension("mp4");
                                arrayList.add(listvideos6);
                            }
                        } catch (Exception unused4) {
                            throw new RuntimeException(unused4);
                        }
                    }
                    def_Parse = this.resultHandler;
                    if (def_Parse == null) {
                        return;
                    }
                    def_Parse.parseData(arrayList);
                    return;
                }
                z = false;
                def_Parse = this.resultHandler;
            }
            responseModel = (ResponseModel) new Gson().fromJson(str, new TypeToken<ResponseModel>() {
            }.getType());
            if (responseModel != null) {
                edge_sidecar_to_children = responseModel.getGraphql().getShortcode_media().getEdge_sidecar_to_children();
                def_Parse = this.resultHandler;
            }
            z = false;
            def_Parse = this.resultHandler;
        }
                                                    matcher3.find();
                                                    responseModel = (ResponseModel) new Gson().fromJson(str, new TypeToken<ResponseModel>() {
        }.getType());
                                                    z = false;
                                                    def_Parse = this.resultHandler;
                                                }
}
