//package com.thecrunchycorner.runlog.processors;
//
//import com.thecrunchycorner.runlog.msgstore.Store;
//import com.thecrunchycorner.runlog.msgstore.enums.OpStatus;
//import com.thecrunchycorner.runlog.ringbufferaccess.Message;
//import com.thecrunchycorner.runlog.ringbufferaccess.PosController;
//import com.thecrunchycorner.runlog.ringbufferaccess.enums.MsgType;
//
//import java.util.Observable;
//import java.util.Observer;
//
//public abstract class ObserverProcessor implements Processor, Observer {
//    private Message msg = new Message(MsgType.NULL_MSG, 0);
//
//    public final Message getMsg() {
//        return msg;
//    }
//
//    public final void update(Observable o, Object arg) {
//        msg = (Message)arg;
//    }
//
//    public Message getMessage(Store store) {
//        return null;
//    }
//
//    public Message processMessage(Message msg) {
//        return null;
//    }
//
//    public OpStatus putMessage(Store store, Message msg) {
//        return null;
//    }
//
//    public boolean updatePos(PosController pCtrlr) {
//        return false;
//    }
//}
