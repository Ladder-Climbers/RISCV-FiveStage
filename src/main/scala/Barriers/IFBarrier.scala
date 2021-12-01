package FiveStage

import chisel3._
import chisel3.experimental._

// IF 屏障的输入输出 Bundle，包含一条地址线和一条指令线
class IFBarrierBundle extends Bundle {
  val PC = UInt(32.W)
  val Inst = UInt(32.W)
}

// IF 屏障，PC 直接线网连结，指令需要寄存器缓冲一拍
class IFBarrier extends MultiIOModule {
  val io = IO(new Bundle {
    val in = Input(new IFBarrierBundle)
    val out = Output(new IFBarrierBundle)
  })

  // PC
  io.out.PC := io.in.PC
  // Inst
  val Inst = RegInit(0.U(32.W))
  Inst := io.in.Inst
  io.out.Inst := Inst

}
