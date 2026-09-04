#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~ ~1.6 ~ rotated ~ -90 run function effects:shockwave/voidplane
scoreboard players operation TEMP s.circle_timer = 1
summon armor_stand ~ ~ ~ {Pose:{Head:[0.01f,0.01f,.01f]},HasVisualFire:1b,Marker:1b,Invisible:1b,Tags:["spellcircle","SorcererPlane","justsummoned"],ArmorItems:[{},{},{},{id:"minecraft:glowstone_dust",Count:1b,tag:{CustomModelData:19}}]}
execute as @e[tag=spellcircle,tag=justsummoned] run tp @s ~ ~ ~ ~ ~
execute as @e[tag=spellcircle,tag=justsummoned] at @s run function sorcerer:__generated__/block/6
scoreboard players reset TEMP s.circle_timer