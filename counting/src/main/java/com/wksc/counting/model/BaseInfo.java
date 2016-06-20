package com.wksc.counting.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by puhua on 2016/6/20.
 *
 * @
 */
public class BaseInfo {

    private int retCode;
    private String sessionId;
    private String retMsg;
    private RetObjBean retObj;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public RetObjBean getRetObj() {
        return retObj;
    }

    public void setRetObj(RetObjBean retObj) {
        this.retObj = retObj;
    }

    public static class RetObjBean {


        private List<RegionsBean> regions;

        private List<ChannelBean> channel;

        private List<GoodsClassBean> GoodsClass;

        public List<RegionsBean> getRegions() {
            return regions;
        }

        public void setRegions(List<RegionsBean> regions) {
            this.regions = regions;
        }

        public List<ChannelBean> getChannel() {
            return channel;
        }

        public void setChannel(List<ChannelBean> channel) {
            this.channel = channel;
        }

        public List<GoodsClassBean> getGoodsClass() {
            return GoodsClass;
        }

        public void setGoodsClass(List<GoodsClassBean> GoodsClass) {
            this.GoodsClass = GoodsClass;
        }

        public static class RegionsBean {
            private String name;
            private String code;

            private List<CityBean> city;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<CityBean> getCity() {
                return city;
            }

            public void setCity(List<CityBean> city) {
                this.city = city;
            }

            public static class CityBean {
                private String name;
                private String code;

                private List<CountyBean> county;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public List<CountyBean> getCounty() {
                    return county;
                }

                public void setCounty(List<CountyBean> county) {
                    this.county = county;
                }

                public static class CountyBean {
                    private String name;
                    private String code;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }
                }
            }
        }

        public static class ChannelBean {
            private String name;
            private String code;

            public String getMCU() {
                return MCU;
            }

            public void setMCU(String MCU) {
                this.MCU = MCU;
            }

            private String MCU;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }
        }

        public static class GoodsClassBean {
            private String name;
            private String code;

            @SerializedName("class")
            private List<ClassBean> classX;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<ClassBean> getClassX() {
                return classX;
            }

            public void setClassX(List<ClassBean> classX) {
                this.classX = classX;
            }

            public static class ClassBean {
                private String name;
                private String code;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
        }
    }
}
