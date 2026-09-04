#built using mc-build (https://github.com/mc-build/mc-build)

execute unless score @s m.hypertrace.maxcharge matches 0.. run scoreboard players set @s m.hypertrace.maxcharge 5
execute unless score @s m.global.tracechance.base matches 0.. run scoreboard players set @s m.global.tracechance.base 15
scoreboard players operation m.temp m.hypertrace.pid = @s m.hypertrace.pid
execute as @e[tag=hypertrace,tag=aj.ajhypertrace.root] if score @s m.hypertrace.pid = m.temp m.hypertrace.pid run tag @s add m.hypertraceMatched
tag @s remove m.hypertraceNear
execute if entity @e[tag=hypertrace,tag=aj.ajhypertrace.root,tag=m.hypertraceMatched,distance=..6] run tag @s add m.hypertraceNear
tag @e remove m.hypertraceMatched
execute if score @s m.hypertrace.maxcharge matches 15.. run scoreboard players set @s m.hypertrace.maxcharge 15
execute if score @s m.hypertrace.charge > @s m.hypertrace.maxcharge run scoreboard players operation @s m.hypertrace.charge = @s m.hypertrace.maxcharge
execute if score @s m.hypertrace.charge matches 1.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 2.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.06666666666666667 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 3.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.13333333333333333 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 4.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.2 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 5.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.26666666666666666 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 6.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.3333333333333333 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 7.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.4 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 8.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.4666666666666667 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 9.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.5333333333333333 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 10.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.6 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 11.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.6666666666666666 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 12.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.7333333333333333 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 13.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.8 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 14.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.8666666666666667 ^1.5 0 0 0 0 3 normal @s
execute if score @s m.hypertrace.charge matches 15.. positioned ~ ~1.625 ~ run particle dust 1.000 0.667 0.000 0.1 ^1.5 ^0.9333333333333333 ^1.5 0 0 0 0 3 normal @s
function magic:resettrackers