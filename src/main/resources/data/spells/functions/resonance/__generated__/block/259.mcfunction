#built using mc-build (https://github.com/mc-build/mc-build)

data modify entity @s data.UUID set from entity @e[tag=spell,tag=reflector,distance=..3,limit=1,sort=nearest] data.UUID
scoreboard players operation @s PUUID0 = @e[distance=..3,tag=spell,tag=reflector] PUUID0
scoreboard players operation @s PUUID1 = @e[distance=..3,tag=spell,tag=reflector] PUUID1
scoreboard players operation @s PUUID2 = @e[distance=..3,tag=spell,tag=reflector] PUUID2
scoreboard players operation @s PUUID3 = @e[distance=..3,tag=spell,tag=reflector] PUUID3