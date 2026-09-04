#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~1.625 ~ {Tags:["spell","Repellance","gust","gathering","justsummoned"]}
execute positioned ~ ~1.625 ~ rotated as @a[distance=..2,limit=1,sort=nearest] run tp @e[tag=gust,tag=justsummoned] ^ ^ ^3 ~ ~
scoreboard players operation @e[tag=Repellance,tag=gust,tag=justsummoned] m.spell.power = @s m.spell.power
execute store result score TEMP UUID0 run scoreboard players get @s PUUID0
execute store result score TEMP UUID1 run scoreboard players get @s PUUID1
execute store result score TEMP UUID2 run scoreboard players get @s PUUID2
execute store result score TEMP UUID3 run scoreboard players get @s PUUID3
execute as @e[tag=Repellance,tag=gust,tag=justsummoned] run function spells:repellance/__generated__/block/513
playsound nyctocosm:spells.transmutate.repellance master @a[distance=..50] ~ ~ ~ 10