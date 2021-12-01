package FiveStage
import chisel3._
import chisel3.experimental.MultiIOModule

// 取指模块

class InstructionFetch extends MultiIOModule {

  // （不能动）测试夹具相关
  val testHarness = IO(
    new Bundle {
      val IMEMsetup = Input(new IMEMsetupSignals)
      val PC        = Output(UInt())
    }
  )


  // 取指模块的 IO
  val io = IO(
    new Bundle {
      val out = Output(new IFBarrierBundle)
    })

  val IMEM = Module(new IMEM)
  val PC   = RegInit(UInt(32.W), 0.U)


  // （不能动）测试夹具初始化
  IMEM.testHarness.setupSignals := testHarness.IMEMsetup
  testHarness.PC := IMEM.testHarness.requestedAddress


  // 取指令
  io.out.PC := PC
  IMEM.io.instructionAddress := PC

  // PC 自增 4，这样就可以取到下一条指令了。
  PC := PC + 4.U

  // Chiro says TODO: add ios to control jumps

  // 指令送入 io.out 这个 Bundle
  io.out.Inst := IMEM.io.instruction

  // （不能动）测试夹具初始化
  val instruction = Wire(new Instruction)
  instruction := IMEM.io.instruction.asTypeOf(new Instruction)
  when(testHarness.IMEMsetup.setup) {
    PC := 0.U
    instruction := Instruction.NOP
  }
}
