package FiveStage
import chisel3._
import chisel3.util._
import chisel3.experimental.MultiIOModule


class MemoryFetch() extends MultiIOModule {


  // Don't touch the test harness
  val testHarness = IO(
    new Bundle {
      val DMEMsetup      = Input(new DMEMsetupSignals)
      val DMEMpeek       = Output(UInt(32.W))

      val testUpdates    = Output(new MemUpdates)
    })

  val io = IO(
    new Bundle {
    })


  val DMEM = Module(new DMEM)

  // Chiro: **WARNING** `DMEM` uses `SyncReadMem` as memory, 
  //        whose read operation needs another clock to get the data.
  //        Just like Xilinx® Block Memory IP.
  //        So you can move the clock to MEMBarrier (or you have better plan?)


  /**
    * Setup. You should not change this code
    */
  DMEM.testHarness.setup  := testHarness.DMEMsetup
  testHarness.DMEMpeek    := DMEM.io.dataOut
  testHarness.testUpdates := DMEM.testHarness.testUpdates


  /**
    * Your code here.
    */
  DMEM.io.dataIn      := 0.U
  DMEM.io.dataAddress := 0.U
  DMEM.io.writeEnable := false.B
}
