package FiveStage

import chisel3._
import chisel3.experimental._

// Chiro: Barries are (mainly) some registers(reg).
//        ID, EX, and other modules are (mainly) combinational logic,
//        and to store the data to pass to next modules, we need these regs.
// ID 屏障的输入输出 Bundle
// Chiro: `class extends Bundle` is very useful.
class IDBarrierBundle extends Bundle {
  val PC = UInt(32.W) // 指令地址
  val Op1 = UInt(32.W) // 操作数 1
  val Op2 = UInt(32.W) // 操作数 2
  val ALUOp = UInt(4.W) // 运算标志
  val RegData = UInt(32.W)  // Chiro: I added this because 
                            // `sw`(STYPE) uses rs1 as op1 and imm as op2, 
                            // but it needs rs2 value to save to memory.
  val WriteEnable = Bool() // 写寄存器使能
  val WriteAddress = UInt(5.W) // 写寄存器地址
}

class IDBarrier extends MultiIOModule {
  val io = IO(new Bundle {
    in = Input(new IDBarrierBundle)
    out = Output(new IDBarrierBundle)
  })

  val reg = Reg(IDBarrierBundle)
  // or you can:
  // reg    <> io.in
  // io.out <> reg
  // It's to store current value of input to regs,
  // and connect output to these regs.
  // Like:
  //    reg a;
  //    assign a_out = a;
  //    always @ (posedge clk) a <= a_in;
  reg := io.in
  io.out := reg
}
