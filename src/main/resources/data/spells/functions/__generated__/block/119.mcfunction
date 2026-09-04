#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ^ ^ ^1 run summon marker ~ ~ ~ {Tags:["motionmarker"]}
execute store result score MotX value run data get entity @e[tag=motionmarker,limit=1] Pos[0] 1000
execute store result score MotY value run data get entity @e[tag=motionmarker,limit=1] Pos[1] 1000
execute store result score MotZ value run data get entity @e[tag=motionmarker,limit=1] Pos[2] 1000
execute store result score SubX value run data get entity @s Pos[0] 1000
execute store result score SubY value run data get entity @s Pos[1] 1000
execute store result score SubZ value run data get entity @s Pos[2] 1000
scoreboard players operation 0 temp = MotX value
scoreboard players operation 0 temp -= SubX value
scoreboard players operation MotX value = 0 temp
scoreboard players operation 0 temp = MotY value
scoreboard players operation 0 temp -= SubY value
scoreboard players operation MotY value = 0 temp
scoreboard players operation 0 temp = MotZ value
scoreboard players operation 0 temp -= SubZ value
scoreboard players operation MotZ value = 0 temp
execute store result entity @s Motion[0] double 0.001 run scoreboard players get MotX value
execute store result entity @s Motion[1] double 0.001 run scoreboard players get MotY value
execute store result entity @s Motion[2] double 0.001 run scoreboard players get MotZ value
scoreboard players reset MotX value
scoreboard players reset MotY value
scoreboard players reset MotZ value
scoreboard players reset SubX value
scoreboard players reset SubY value
scoreboard players reset SubZ value
kill @e[tag=motionmarker]