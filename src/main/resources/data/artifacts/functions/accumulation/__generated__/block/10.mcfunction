#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation a.TEMP m.hypertrace.pid = @s m.hypertrace.pid
execute at @s as @e[tag=hypertrace,tag=aj.ajhypertrace.root,distance=..6] if score @s m.hypertrace.pid = a.TEMP m.hypertrace.pid at @s run function artifacts:accumulation/__generated__/block/11
execute store result score a.nearbyTraces a.value run execute if entity @e[tag=a.nearbyTrace]
tag @e remove a.nearbyTrace
function magic:calcstatsspell
tag @s add a.accumulation.active
scoreboard players set a.TEMP m.global.tracechance.modifier 5
scoreboard players operation a.fullPower a.value = @s m.global.power
scoreboard players operation a.fullPower a.value += @s m.resonance.power
scoreboard players operation a.TEMP m.global.tracechance.modifier *= a.fullPower a.value
scoreboard players operation a.TEMP m.global.tracechance.modifier += a.accumulation.prob a.value
scoreboard players operation a.TEMP m.global.tracechance.modifier *= a.nearbyTraces a.value
scoreboard players operation @s m.global.tracechance.modifier += a.TEMP m.global.tracechance.modifier
scoreboard players operation @s a.accumulation.probMod = a.TEMP m.global.tracechance.modifier