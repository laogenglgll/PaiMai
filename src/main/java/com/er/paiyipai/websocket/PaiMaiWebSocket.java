package com.er.paiyipai.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//@ServerEndpoint("/newprice/{aid}")
//@Component
public class PaiMaiWebSocket {

    static Map<Integer, List<Session>> map = new ConcurrentHashMap<>();

    /**
     * 连接前
     * @param session
     * @throws IOException
     */
    @OnOpen
    public  void openSocket(@PathParam("aid") Integer aid , Session session) throws IOException {
        //1.第一个围观者
        if(!map.containsKey(aid)){
            List<Session> list = new ArrayList<>();
            list.add(session);
            map.put(aid,list);
        }//2.第二个竞争者
        else{
            List<Session> list = map.get(aid);
            list.add(session);
        }
        System.out.println("连接成功");
        session.getBasicRemote().sendText("发送信息=======test");
    }

    /**
     * 连接上后
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("收到来着客户端的信息" + message);
    }

    /**
     * 断开时
     * @param session
     */
    @OnClose
    public void closeSocket(Session session){
        Set<Integer> keySet = map.keySet();
        Iterator<Integer> ite = keySet.iterator();
        while(ite.hasNext()){
            Integer aid = ite.next();
            List<Session> list = map.get(aid);
            if(list.contains(session)){
                list.remove(session);
                break;
            }
        }
        System.out.println("连接关闭");
    }

    public static void sendPrice(String hid,int nprice,int aid){
        List<Session> weiguanzhe = map.get(aid);
        if(weiguanzhe != null && weiguanzhe.size() != 0){
            for(Session session:weiguanzhe){
                session.getAsyncRemote().sendText(hid+"-"+nprice);
            }
        }
    }

    @OnError
    public void error(Throwable ex,Session session){
        ex.printStackTrace();
        System.out.println("连接有异常");
    }
}
