#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @e[tag=emissary,tag=spawntarget,limit=1] feet run tp @s ^ ^ ^1 ~ ~
particle flame ~ ~ ~ 1 1 1 0.2 20 force
particle lava ~ ~ ~ 1 1 1 0.4 10 force
particle campfire_cosy_smoke ~ ~ ~ 1 1 1 0.1 5 force
particle flash ~ ~ ~ 1 1 1 0 1 force
particle smoke ~ ~ ~ 1 1 1 0 10 force
particle wax_on ~ ~ ~ 1 1 1 0 5 force
particle end_rod ~ ~ ~ 1 1 1 0 5 force
scoreboard players add @s em.timer 1
execute if score @s em.timer matches 95.. run function emissary:__generated__/block/1