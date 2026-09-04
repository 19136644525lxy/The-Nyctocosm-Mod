#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove reflected
scoreboard players reset RANGE em.value
tag @s remove endraycast
playsound minecraft:entity.generic.explode master @a[distance=..30] ~ ~ ~ 1
particle flash ~ ~1 ~ 0 0 0 0 1 force
particle explosion ~ ~1 ~ 0.3 0.3 0.3 0 2 force
particle lava ~ ~1 ~ 0.3 0.3 0.3 0 2 force
execute as @a[gamemode=!spectator,tag=!emissarydebug,distance=..4] at @s run function emissary:attacks/phase2/wing_blast/__generated__/block/382