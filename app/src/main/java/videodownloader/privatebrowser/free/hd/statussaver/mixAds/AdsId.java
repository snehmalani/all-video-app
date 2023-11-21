package videodownloader.privatebrowser.free.hd.statussaver.mixAds;

public class AdsId {

    int interType;
    int topNativeType;
    int middleNativeType;
    int bottomNativeType;
    String topGoogleNativeID;
    String middleGoogleNativeID;
    String bottomGoogleNativeID;
    String googleInterID;
    String googleBannerID;
    String topFBNativeID;
    String middleFBNativeID;
    String bottomFBNativeID;
    String fbInterID;
    String fbBannerID;

    public AdsId(int interType, int topNativeType, int middleNativeType, int bottomNativeType, String topGoogleNativeID, String middleGoogleNativeID, String bottomGoogleNativeID, String googleInterID, String googleBannerID, String topFBNativeID, String middleFBNativeID, String bottomFBNativeID, String fbInterID, String fbBannerID) {
        this.interType = interType;
        this.topNativeType = topNativeType;
        this.middleNativeType = middleNativeType;
        this.bottomNativeType = bottomNativeType;
        this.topGoogleNativeID = topGoogleNativeID;
        this.middleGoogleNativeID = middleGoogleNativeID;
        this.bottomGoogleNativeID = bottomGoogleNativeID;
        this.googleInterID = googleInterID;

        this.googleBannerID = googleBannerID;
        this.topFBNativeID = topFBNativeID;
        this.middleFBNativeID = middleFBNativeID;
        this.bottomFBNativeID = bottomFBNativeID;
        this.fbInterID = fbInterID;
        this.fbBannerID = fbBannerID;
    }

    public int getInterType() {
        return interType;
    }

    public int getTopNativeType() {
        return topNativeType;
    }

    public int getMiddleNativeType() {
        return middleNativeType;
    }

    public int getBottomNativeType() {
        return bottomNativeType;
    }

    public String getTopGoogleNativeID() {
        return topGoogleNativeID;
    }

    public String getMiddleGoogleNativeID() {
        return middleGoogleNativeID;
    }

    public String getBottomGoogleNativeID() {
        return bottomGoogleNativeID;
    }

    public String getGoogleInterID() {
        return googleInterID;
    }


    public String getGoogleBannerID() {
        return googleBannerID;
    }

    public String getTopFBNativeID() {
        return topFBNativeID;
    }

    public String getMiddleFBNativeID() {
        return middleFBNativeID;
    }

    public String getBottomFBNativeID() {
        return bottomFBNativeID;
    }

    public String getFbInterID() {
        return fbInterID;
    }

    public String getFbBannerID() {
        return fbBannerID;
    }
}
