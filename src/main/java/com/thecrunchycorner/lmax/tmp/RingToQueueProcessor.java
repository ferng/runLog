//package com.thecrunchycorner.lmax.processors;
//
//import com.thecrunchycorner.lmax.msgstore.QueueStore;
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.OpStatus;
//import com.thecrunchycorner.lmax.msgstore.Message;
//import com.thecrunchycorner.lmax.ringbufferaccess.BufferReader;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
//import com.thecrunchycorner.lmax.workflow.ProcessorWorkflow;
//
//public class RingToQueueProcessor extends Processor implements Runnable {
//    private QueueStore<Message> queue;
//    private volatile boolean interrupt = false;
//
//
//    public RingToQueueProcessor(RingBufferStore ring, QueueStore<Message> queue) {
//        ProcessorId readProcID;
//        ProcessorId readLeadProcID;
//        this.queue = queue;
//
//        readProcID = ProcessorId.OUT_Q_SEND;
//        readLeadProcID = ProcessorWorkflow.getLeadProc(readProcID);
//
//        ProcProperties procProps;
//        getPosCtrlr().setPos(readProcID, 0);
//        int leadProcHead = getPosCtrlr().getPos(readLeadProcID);
//
//        procProps = new ProcPropertiesBuilder()
//                .setBuffer(ring)
//                .setProcessor(readProcID)
//                .setLeadProc(readLeadProcID)
//                .setInitialHead(leadProcHead)
//                .createProcProperties();
//
//        initRingReader(procProps);
//    }
//
//
//    @Override
//    protected final void initRingWriter(ProcProperties procProps) {
//    }
//
//
//    @Override
//    protected final void initRingReader(ProcProperties procProps) {
//        setReader(new BufferReader(procProps));
//    }
//
//
//    @Override
//    protected final Message getMessage() {
//        return (Message) getReader().read();
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
//        return (queue.add(msg) ? OpStatus.WRITE_SUCCESS : OpStatus.ERROR);
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
