package com.goat.youtubeviewer.network;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.goat.youtubeviewer.utills.DebugLog;
import com.google.gson.annotations.SerializedName;

public class YouTubeSearchResponse {

    @SerializedName("kind")
    private String mKind;

    public String getKind() {
        return mKind;
    }

    @SerializedName("etag")
    private String mEtag;

    public String getEtag() {
        return mEtag;
    }

    @SerializedName("nextPageToken")
    private String mNextPageToken;

    @Nullable
    public String getNextPageToken() {
        return mNextPageToken;
    }

    @SerializedName("prevPageToken")
    private String mPrevPageToken;

    @Nullable
    public String getPrevPageToken() {
        return mPrevPageToken;
    }

    @SerializedName("regionCode")
    private String mRegionCode;

    public String getRegionCode() {
        return mRegionCode;
    }

    @SerializedName("pageInfo")
    private PageInfo mPageInfo;

    public PageInfo getPageInfo() {
        return mPageInfo;
    }

    public static class PageInfo {
        private static final String TAG = PageInfo.class.getSimpleName();
        @SerializedName("totalResults")
        private String mTotalResult;

        @SerializedName("resultsPerPage")
        private String mResultsPerPage;

        public int getTotalResult() {
            int totalResult = 0;
            try {
                totalResult = Integer.parseInt(mTotalResult);
            } catch (NumberFormatException e) {
                DebugLog.e(TAG, "[getTotalResult]", e);
            }
            return totalResult;
        }

        public int getResultPerPage() {
            int resultPerPage = 0;
            try {
                resultPerPage = Integer.parseInt(mResultsPerPage);
            } catch (NumberFormatException e) {
                DebugLog.e(TAG, "[getResultPerPage]", e);
            }
            return resultPerPage;
        }

    }

    @SerializedName("items")
    private Item[] mItemArray;

    public Item[] getItems() {
        return mItemArray;
    }

    public static class Item {

        @SerializedName("kind")
        private String mKind;

        public String getKind() {
            return mKind;
        }

        @SerializedName("etag")
        private String mEtag;

        public String getEtag() {
            return mEtag;
        }

        @SerializedName("id")
        private Id mId;

        public Id getId() {
            return mId;
        }

        public static class Id {
            @SerializedName("kind")
            private String mKind;

            @SerializedName("videoId")
            private String mVideoId;

            public String getKind() {
                return mKind;
            }

            public String getVideoId() {
                return mVideoId;
            }
        }

        @SerializedName("channelTitle")
        private String mChannelTitle;
        public String getChannelTitle () {
            return mChannelTitle;
        }

        @SerializedName("liveBroadcastContent")
        private String mLiveBroadCastContent;

        public boolean isLiveBroadCastContent () {
            return TextUtils.equals(mLiveBroadCastContent, "live");
        }

        @SerializedName("snippet")
        private Snippet mSnippet;

        public Snippet getSnippet() {
            return mSnippet;
        }
        public static class Snippet {

            //登録日
            @SerializedName("publishedAt")
            private String mPublishedAt;

            public String getPublishedAt() {
                return mPublishedAt;
            }

            @SerializedName("channelId")
            private String mChannelId;

            public String getChannelId() {
                return mChannelId;
            }

            @SerializedName("title")
            private String mTitle;

            public String getTitle() {
                return mTitle;
            }

            @SerializedName("description")
            private String mDescription;

            public String getDescription() {
                return mDescription;
            }

            @SerializedName("thumbnails")
            private Thumbnails mThumbnails;

            public Thumbnails getThumbnails() {
                return mThumbnails;
            }

            public static class Thumbnails {
                private static final String TAG = Thumbnails.class.getSimpleName();
                @SerializedName("default")
                private Default mDefault;

                @SerializedName("medium")
                private Medium mMedium;

                @SerializedName("high")
                private High mHigh;

                /**
                 * @return
                 */
                public ThumbnailSizeBase getDefaultThumbnail() {
                    return mDefault;
                }

                /**
                 * @return 中画質のサムネイル情報を返却する,無ければデフォルト画質の情報を返却
                 */
                public ThumbnailSizeBase getMediumThumbnail() {
                    if (isSuccessGetThumbnailInfo(mMedium)) {
                        return mMedium;
                    }
                    DebugLog.d(TAG, "[getMediumThumbnail], medium thumbnail is not exist");
                    return getDefaultThumbnail();
                }

                /**
                 * @return 高画質のサムネイル情報を返却する,無ければ中画質の情報を返却
                 */
                public ThumbnailSizeBase getHighThumbnail() {
                    if (isSuccessGetThumbnailInfo(mHigh)) {
                        return mHigh;
                    }
                    DebugLog.d(TAG, "[getHighThumbnail], high thumbnail is not exist");
                    return getMediumThumbnail();
                }

                private boolean isSuccessGetThumbnailInfo(@Nullable ThumbnailSizeBase thumbnailInfo) {
                    return thumbnailInfo != null && thumbnailInfo.getWidth() > 1 && thumbnailInfo.getHeight() > 1;
                }

                public class Default extends ThumbnailSizeBase {
                    @Override
                    String getTAG() {
                        return Default.class.getSimpleName();
                    }
                }

                public class Medium extends ThumbnailSizeBase {
                    @Override
                    String getTAG() {
                        return Medium.class.getSimpleName();
                    }
                }

                public class High extends ThumbnailSizeBase {
                    @Override
                    String getTAG() {
                        return High.class.getSimpleName();
                    }
                }

                abstract class ThumbnailSizeBase {
                    @SerializedName("url")
                    private String mUrl;
                    @SerializedName("width")
                    private String mWidth;
                    @SerializedName("height")
                    private String mHeight;

                    public String getUrl() {
                        return mUrl;
                    }

                    public int getWidth() {
                        int width = 0;
                        try {
                            width = Integer.parseInt(mWidth);
                        } catch (NumberFormatException e) {
                            DebugLog.e(getTAG(), "[getWidth] " + mWidth, e);
                        }
                        return width;
                    }

                    public int getHeight() {
                        int height = 0;
                        try {
                            height = Integer.parseInt(mHeight);
                        } catch (NumberFormatException e) {
                            DebugLog.e(getTAG(), "[getHeight] " + mHeight, e);
                        }
                        return height;
                    }

                    abstract String getTAG();
                }
            }
        }
    }
}
