package FiveStage

import chisel3._
import chisel3.experimental._

// ID 屏障的输入输出 Bundle
class IDBarrierBundle extends Bundle {
  val PC = UInt(32.W) // 指令地址
  val Op1 = UInt(32.W) // 操作数 1
  val Op2 = UInt(32.W) // 操作数 2
  val ALUOp = UInt(4.W) // 运算标志
  val RegData = UInt(32.W) //
  val WriteEnable = Bool() // 写寄存器使能
  val WriteAddress = UInt(5.W) // 写寄存器地址
}

class IDBarrier extends MultiIOModule {
  val io = IO(new Bundle {
    in = Input(new IDBarrierBundle)
    out = Output(new IDBarrierBundle)
  })

  val reg = Reg(IDBarrierBundle)
  reg := io.in
  io.out := reg
}
