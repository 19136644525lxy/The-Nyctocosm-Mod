#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~ ~ 0.4 0.4 0.4 0 5 force
particle flame ~ ~ ~ 1 1 1 0.2 20 force
particle lava ~ ~ ~ 1 1 1 0 10 force
particle large_smoke ~ ~ ~ 1 1 1 0 15 force
execute as @a[distance=..5,gamemode=!spectator,tag=!emissarydebug] at @s run tag @s add impact_flame_geysers