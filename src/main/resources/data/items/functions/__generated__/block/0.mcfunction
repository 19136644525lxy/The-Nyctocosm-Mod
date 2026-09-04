#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s timer 1
kill @s[scores={timer=100..}]
particle minecraft:sculk_soul ~ ~ ~ 0.2 0.4 0.2 0.02 2 force
execute as @a[distance=..5] run function items:__generated__/block/1
execute if entity @a[tag=collect_soul] run kill @s
tag @a remove collect_soul