//package com.thecrunchycorner.lmax.processors;
//
//import com.thecrunchycorner.lmax.msgstore.QueueStore;
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.OpStatus;
//import com.thecrunchycorner.lmax.msgstore.Message;
//import com.thecrunchycorner.lmax.ringbufferaccess.BufferWriter;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
//import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
//
//public class QueueToRingProcessor extends Processor implements Runnable {
//    private QueueStore<Message> queue;
//
//    private volatile boolean interrupt = false;
//
//
//    public QueueToRingProcessor(QueueStore<Message> queue, RingBufferStore ring) {
//        ProcessorId writeProcID;
//        ProcessorId writeLeadProcID;
//
//        this.queue = queue;
//
//        writeProcID = ProcessorId.IN_Q_RECEIVE;
//        writeLeadProcID = ProcessorWorkflow.getLeadProc(writeProcID);
//
//        ProcProperties procProps;
//        getPosCtrlr().setPos(writeProcID, 0);
//        int leadProcHead = getPosCtrlr().getPos(writeLeadProcID);
//
//        procProps = new ProcPropertiesBuilder()
//                .setBuffer(ring)
//                .setProcessor(writeProcID)
//                .setLeadProc(writeLeadProcID)
//                .setInitialHead(leadProcHead)
//                .createProcProperties();
//
//        initRingWriter(procProps);
//    }
//
//
//    @Override
//    protected final void initRingWriter(ProcProperties procProps) {
//        setWriter(new BufferWriter(procProps));
//    }
//
//
//    @Override
//    protected final void initRingReader(ProcProperties procProps) {
//    }
//
//
//    @Override
//    protected final Message getMessage() {
//        return queue.take();
//    }
//
//
//    @Override
//    protected final Message processMessage(Message msg) {
//        return msg;
//    }
//
//
//    @Override
//    protected final OpStatus putMessage(Message msg) {
//        return getWriter().write(msg);
//    }
//
//
//    public final void run() {
//        while (!interrupt) {
//            getAndProcessMsg();
//        }
//    }
//
//
//    protected final void getAndProcessMsg() {
//        Message msg = processMessage(getMessage());
//        while (putMessage(msg) == OpStatus.HEADER_REACHED) {
//        }
//    }
//
//
//    public final void reqInterrupt() {
//        interrupt = true;
//    }
//}