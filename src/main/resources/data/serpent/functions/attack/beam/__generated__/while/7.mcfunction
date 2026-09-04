#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Range value 1
particle dust 1 0 0 4 ~ ~ ~ 1 1 1 0 1 force
particle dust 0 0 0 4 ~ ~ ~ 1 1 1 0 2 force
execute as @a[distance=..5] run function serpent:attack/beam/__generated__/block/219
execute if score Range value matches 80.. run tag @s add endraycast
scoreboard players set #WHILE_5 LANG_MC_INTERNAL 1
function serpent:attack/beam/__generated__/while/6