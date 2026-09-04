#built using mc-build (https://github.com/mc-build/mc-build)

tp @s ~ ~ ~ ~ ~
execute at @s run tp @e[tag=matchedid] ~ ~ ~ ~-90 ~
data modify entity @s data.UUID set from entity @e[tag=spell,tag=reflector,distance=..3,limit=1,sort=nearest] data.UUID
scoreboard players operation @s PUUID0 = @e[distance=..3,tag=spell,tag=reflector] PUUID0
scoreboard players operation @s PUUID1 = @e[distance=..3,tag=spell,tag=reflector] PUUID1
scoreboard players operation @s PUUID2 = @e[distance=..3,tag=spell,tag=reflector] PUUID2
scoreboard players operation @s PUUID3 = @e[distance=..3,tag=spell,tag=reflector] PUUID3
data modify entity @e[tag=matchedid,limit=1] data.UUID set from entity @s data.UUID
scoreboard players operation @e[tag=matchedid,limit=1] PUUID0 = @s PUUID0
scoreboard players operation @e[tag=matchedid,limit=1] PUUID1 = @s PUUID1
scoreboard players operation @e[tag=matchedid,limit=1] PUUID2 = @s PUUID2
scoreboard players operation @e[tag=matchedid,limit=1] PUUID3 = @s PUUID3