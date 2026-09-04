#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players remove @s spelltimer 1
particle dust 0 0 0 2 ~ ~ ~ 0.2 0.2 0.2 0 10 force
function effects:void_swirl/single_reverse_xflat
execute if block ~ ~ ~ #nyctocosm:passthrough run tp @s ^ ^ ^0.3
execute store result score UUID0 value run scoreboard players get @s PUUID0
execute store result score UUID1 value run scoreboard players get @s PUUID1
execute store result score UUID2 value run scoreboard players get @s PUUID2
execute store result score UUID3 value run scoreboard players get @s PUUID3
execute at @s as @e[distance=..3,tag=spell,tag=reflector] run function spells:__generated__/block/68
execute at @s if entity @e[tag=reflector,tag=!matchedid,tag=spell,distance=..3] run function spells:__generated__/block/70
tag @e[tag=spell,tag=reflector] remove matchedid
scoreboard players add @s gravitancehum 1
execute if score @s gravitancehum matches 2.. run function spells:__generated__/block/73
execute if score @s spelltimer matches ..0 run function spells:__generated__/block/74
execute store result score UUID0 value run data get entity @s data.UUID[0]
execute store result score UUID1 value run data get entity @s data.UUID[1]
execute store result score UUID2 value run data get entity @s data.UUID[2]
execute store result score UUID3 value run data get entity @s data.UUID[3]
function damage:playermarkertag
execute at @s as @e[distance=..20,type=#nyctocosm:manipulable,tag=!custom] run function spells:__generated__/block/75
tag @a remove damagetemp