#built using mc-build (https://github.com/mc-build/mc-build)

stopsound @s master nyctocosm:spells.hum.magic_plane
execute at @e[tag=SPAWNMARKER,limit=1] run summon marker ~ ~ ~ {Tags:["spell","Evanescence","evanescencemarker","justsummoned"]}
execute store result score TempX value run data get entity @s SpawnX
execute store result score TempY value run data get entity @s SpawnY
execute store result score TempZ value run data get entity @s SpawnZ
tag @s add caster
execute as @e[tag=justsummoned,tag=evanescencemarker] at @s run function spells:evanescence/__generated__/block/279
execute at @s run particle minecraft:portal ~ ~1 ~ 0.1 0.2 0.1 2 1000 force
tag @s remove caster