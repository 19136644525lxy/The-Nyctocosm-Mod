#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.shardhailtimer 1
particle block ice ~ ~ ~ 0.1 0.1 0.1 0 10 force
particle block blue_ice ~ ~ ~ 0.2 0.2 0.2 0 10 force
particle item quartz{CustomModelData:5} ~ ~ ~ 0.2 0.2 0.2 0 20 force
tp @s ~ ~1 ~
execute as @s[scores={ns.shardhailtimer=30..}] at @s run function sorcerer:attacks/shardhail/__generated__/block/19