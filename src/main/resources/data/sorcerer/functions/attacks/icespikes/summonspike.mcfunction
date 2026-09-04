#built using mc-build (https://github.com/mc-build/mc-build)

summon armor_stand ~ ~-2 ~ {Tags:["icespikes","sorcererattack","spike","justsummoned"],Invisible:1b,Invulnerable:1b,Silent:1b,Marker:1b,ArmorItems:[{},{},{},{id:"minecraft:quartz",Count:1b,tag:{CustomModelData:11}}]}
execute as @e[tag=justsummoned,tag=spike] at @s run function sorcerer:attacks/icespikes/__generated__/block/61