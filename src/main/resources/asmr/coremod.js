function initializeCoreMod() {
    var ASM = Java.type("net.minecraftforge.coremod.api.ASMAPI");
    var Opcodes = Java.type("org.objectweb.asm.Opcodes");

    return {
        "fps-increase": {
            "target": {
                "type": "METHOD",
                "class": "net.minecraft.client.Minecraft",
                "methodName": "runGameLoop",
                "methodDesc": "(Z)V"
            },
            "transformer": function (method) {
                var call = ASM.buildMethodCall(
                    "eutros/wowmuchfps/WowMuchFPS",
                    "increaseFPS",
                    "(I)I",
                    ASM.MethodType.STATIC
                )

                var index = 0;

                var candidate = true;

                while(index < method.instructions.size() - 1) {
                    candidate = ASM.findFirstInstructionAfter(method, Opcodes.PUTSTATIC, index);
                    if(candidate == null) {
                        break;
                    }

                    if(candidate.name == ASM.mapField("field_71470_ab") // debugFPS
                        && candidate.owner == "net/minecraft/client/Minecraft"
                        && candidate.desc == "I") {
                        method.instructions.insertBefore(candidate, call);
                        ASM.log("INFO", "Successfully applied substantial performance improvements.")
                        return method;
                    }
                    index = method.instructions.indexOf(candidate) + 1;
                }

                ASM.log("FATAL", "Failed to multiply FPS. [Couldn't find suitable PUTSTATIC instruction.]")
                return method;
            }
        }
    }
}
