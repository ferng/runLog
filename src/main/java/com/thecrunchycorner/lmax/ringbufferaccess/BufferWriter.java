//package com.thecrunchycorner.lmax.ringbufferaccess;
//
//import com.thecrunchycorner.lmax.msgstore.Message;
//import com.thecrunchycorner.lmax.msgstore.RingBufferStore;
//import com.thecrunchycorner.lmax.msgstore.OpStatus;
//import com.thecrunchycorner.lmax.workflow.ProcessorId;
//import com.thecrunchycorner.lmax.processorproperties.ProcProperties;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
///**
// * Provides client classes with the means to write to a buffer.
// * Each writer is unique to the processor using it and keeps track of:
// * 1) Which buffer it's writing to
// * 2) where it has written up to
// * 3) where it can write up to
// */
//public class BufferWriter {
//    private static Logger logger = LogManager.getLogger(BufferWriter.class);
//
//    private PosController posController = PosControllerFactory.getController();
//
//    private RingBufferStore buffer;
//    private ProcessorId processor;
//    private ProcessorId myLead;
//    private int head;
//
//
//    /**
//     * Each BufferWriter is unique to a processor (although a processor can have multiple
//     * readers / writers)
//     *
//     * @param props - details for this wtriter:
//     * 1) Which buffer is to be accessed
//     * 2) ID for the processor that owns us and
//     * 3) ID for the processor we follow
//     */
//    public BufferWriter(ProcProperties props) {
//        buffer = props.getBuffer();
//        processor = props.getProc();
//        myLead = props.getLeadProc();
//        head = props.getInitialHead();
//
//        posController.setPos(processor, 0);
//    }
//
//
//    /**
//     * Writes an object to its buffer if there is room.
//     *
//     * @return OpStatus.WRITE_SUCCESS if object was successfully written to the buffer
//     * or, OpStatus.HEADER_REACHED if there was no room, it is up to the client to wait
//     * an appropriate amount of time before retrying.
//     */
//    public final OpStatus write(Message msg) {
//        int pos = posController.getPos(processor);
//
//        if (msg == null) {
//            logger.warn("attempting to insert null in buffer at pos {})", pos);
//            return OpStatus.ERROR;
//        }
//
//        if (pos == head) {
//            head = posController.getPos(myLead);
//            if (pos == head) {
//                return OpStatus.HEADER_REACHED;
//            }
//        }
//
//        buffer.set(pos, msg);
//        posController.incrPos(processor);
//
//        return OpStatus.WRITE_SUCCESS;
//    }
//
//}
