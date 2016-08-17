package com.example.szhh.mainapplication.entity;

import java.util.List;

/**
 * @ Created by szhh on 2016/8/14.
 * @ Date   : 2016/8/14.
 * @ Auther    : suzhiheng
 * @ Description    :TODDO
 */

public class ShopEntity {

    /**
     * response : topic
     * topic : [{"id":"1","name":"背奶妈妈带爱回家","pic":"http://101.200.183.103:8888/shop/images/home/topic/1.png"},{"id":"2","name":"把爱和玩具带回家 全场2.3折起","pic":"http://101.200.183.103:8888/shop/images/home/topic/2.png"},{"id":"3","name":"小白熊感恩母亲节，夏季特卖","pic":"http://101.200.183.103:8888/shop/images/home/topic/3.png"},{"id":"4","name":"奶瓶大牌秀场 贝亲、nuk、新安怡、comotomo、Lifefactory、智高联合大促","pic":"http://101.200.183.103:8888/shop/images/home/topic/4.png"},{"id":"5","name":"汽车儿童安全座椅全场满499立减120元，可累计，特价不参加","pic":"http://101.200.183.103:8888/shop/images/home/topic/5.png"},{"id":"6","name":"儿童节，全场5折起","pic":"http://101.200.183.103:8888/shop/images/home/topic/6.png"},{"id":"7","name":"格朗满188减88","pic":"http://101.200.183.103:8888/shop/images/home/topic/7.png"},{"id":"8","name":"冰价来袭 折扣巨献","pic":"http://101.200.183.103:8888/shop/images/home/topic/8.png"}]
     */

    private String response;
    /**
     * id : 1
     * name : 背奶妈妈带爱回家
     * pic : http://101.200.183.103:8888/shop/images/home/topic/1.png
     */

    private List<TopicBean> topic;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<TopicBean> getTopic() {
        return topic;
    }

    public void setTopic(List<TopicBean> topic) {
        this.topic = topic;
    }

    public static class TopicBean {
        private String id;
        private String name;
        private String pic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        @Override
        public String toString() {
            return "TopicBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", pic='" + pic + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ShopEntity{" +
                "response='" + response + '\'' +
                ", topic=" + topic +
                '}';
    }
}
