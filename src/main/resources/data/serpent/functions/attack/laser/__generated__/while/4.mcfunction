#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add Range value 1
particle dust 1 0 0 1 ~ ~ ~ 0.5 0.5 0.5 0 2 force
particle dust 0 0 0 1 ~ ~ ~ 0.5 0.5 0.5 0 4 force
execute as @a[distance=..3] run function serpent:attack/laser/__generated__/block/208
execute if score Range value matches 150.. run tag @s add endraycast
execute unless block ~ ~ ~ #nyctocosm:air run function serpent:attack/laser/__generated__/block/215
scoreboard players set #WHILE_4 LANG_MC_INTERNAL 1
function serpent:attack/laser/__generated__/while/3