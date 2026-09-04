#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~1 ~ run particle flash ~ ~ ~ 0.1 0.1 0.1 0 5 force
execute positioned ~ ~0.2 ~ run particle minecraft:block ice ~ ~ ~ 0.3 0.2 0.3 1 100
execute at @s positioned ^-0.7 ^ ^0.85 align y run function animated_java:ajnullpoint/summon
execute as @e[tag=aj.ajnullpoint.root] at @s run function sorcerer:__generated__/block/194
execute as @a[distance=..4] run function sorcerer:__generated__/block/195