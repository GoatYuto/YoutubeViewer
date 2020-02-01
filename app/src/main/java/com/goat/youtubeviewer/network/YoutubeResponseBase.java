package com.goat.youtubeviewer.network;

import com.google.gson.annotations.SerializedName;

public class YoutubeResponseBase extends ApiResponseBase {

    @Override
    public boolean isSuccess () {
        return mError == null;
    }

    @SerializedName("error")
    private Error mError;

    public Error getError() {
        return mError;
    }

    public static class Error {
        @SerializedName("errors")
        private Errors[] mErrors;

        public Errors[] getErrorsArray() {
            return mErrors;
        }
        @SerializedName("code")
        private String mCode;

        public String getCode() {
            return mCode;
        }

        @SerializedName("message")
        private String mMassage;


        public String getMassage() {
            return mMassage;
        }

        public static class Errors {
            @SerializedName("domain")
            private String mDomain;
            @SerializedName("reason")
            private String mReason;
            @SerializedName("message")
            private String mMassage;
            @SerializedName("extendedHelp")
            private String mExtendedHelp;

            public String getDomain() {
                return mDomain;
            }

            public String getReason() {
                return mReason;
            }

            public String getMassage() {
                return mMassage;
            }

            public String getExtendedHelp() {
                return mExtendedHelp;
            }
        }
    }

}
