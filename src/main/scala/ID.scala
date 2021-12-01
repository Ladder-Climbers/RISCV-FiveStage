package FiveStage
import chisel3._
import chisel3.util.{ BitPat, MuxCase }
import chisel3.experimental.MultiIOModule

// 译码模块

class InstructionDecode extends MultiIOModule {

  // （勿动）测试夹具
  val testHarness = IO(
    new Bundle {
      val registerSetup = Input(new RegisterSetupSignals)
      val registerPeek  = Output(UInt(32.W))

      val testUpdates   = Output(new RegisterUpdates)
    })


  // 译码模块 IO
  val io = IO(
    new Bundle {
      val in = Input(new IFBarrierBundle) // 从 IFBarrier 传来的地址和指令
      val out = Output(new IDBarrierBundle) // 传向 IDBarrier
      // TODO: 读写 32 个寄存器的东西……
      // Chiro: The ID Module is to: 
      //  1. Decode the instruction (has been implemented in Decoder.scala)
      //  2. Arrange the data to calc in EX module (op1, op2, aluOp)
      //  3. Stall (pause) the flow line if necessary
      //  4. Judge whether to jump and tell IF module
      //  5. Solve data dependency problem
    }
  )

  val registers = Module(new Registers)
  val decoder   = Module(new Decoder).io


  /**
    * Setup. You should not change this code
    */
  registers.testHarness.setup := testHarness.registerSetup
  testHarness.registerPeek    := registers.io.readData1
  testHarness.testUpdates     := registers.testHarness.testUpdates


  /**
    * TODO: Your code here.
    */
  registers.io.readAddress1 := 0.U
  registers.io.readAddress2 := 0.U
  registers.io.writeEnable  := false.B
  registers.io.writeAddress := 0.U
  registers.io.writeData    := 0.U

  decoder.instruction := io.in.Inst // 将指令送入 Decoder 译码
  // Don't 寄了
  // 
}
